package com.example.mybank.user.service;

import com.example.mybank.account.service.AccountService;
import com.example.mybank.core.exception.exceptions.*;
import com.example.mybank.user.dto.*;
import com.example.mybank.user.model.User;
import com.example.mybank.user.repository.UserRepository;
import jakarta.persistence.criteria.Expression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.mybank.user.dto.UserMapper.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(RegisterUserDto dto) {
        validateUserDtoForCreation(dto);
        User user = toUserFromRequest(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }

        user = userRepository.save(user);
        accountService.save(dto.getInitBalance(), user);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UserNotFoundException("Пользователь с id " + id + " не найден.")
        );

        return UserDto.builder()
            .fullName(user.getFullName())
            .phones(user.getPhones())
            .emails(user.getEmails())
            .login(user.getLogin())
            .birthday(user.getBirthday())
            .id(user.getId())
            .build();
    }

    @Override
    @Transactional
    public UserDto updateEmailOrPhone(Long userId, UpdateUserRequest dto) {
        User user = getAuthenticatedUser();

        updateEmail(user, dto);
        updatePhone(user, dto);

        return toUserDtoFromUser(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteEmailOrPhone(Long userId, DeleteUserRequest dto) {
        User user = getAuthenticatedUser();

        deleteValue(dto.getEmail(), user.getEmails());
        deleteValue(dto.getPhone(), user.getPhones());

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findUsers(String fullName, String email, String phone, LocalDateTime birthday,
        Integer from, Integer size, HttpServletRequest request) {
        getAuthenticatedUser();

        PageRequest page = PageRequest.of(from / size, size);
        Specification<User> specification = Specification.where(null);

        if (fullName != null) {
            specification = specification.and(((root, query, criteriaBuilder) ->
                root.get("fullName").as(String.class).in(fullName)));
        }

        if (email != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Expression<Collection<String>> emails = root.get("emails");
                return criteriaBuilder.isMember(email, emails);
            });
        }

        if (phone != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Expression<Collection<String>> phones = root.get("phones");
                return criteriaBuilder.isMember(phone, phones);
            });
        }

        if (birthday != null) {
            specification = specification.and(((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), birthday)));
        }

        return getUsersBySpecification(specification, page);
    }

    public User getByUsername(String login) {
        return userRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private void validatePhone(String phone) {
        if (userRepository.existsByPhones(phone)) {
            throw new UserBadRequestException("Номер телефона уже занят другим пользователем.");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmails(email)) {
            throw new UserBadRequestException("Электронная почта уже занята другим пользователем.");
        }
    }

    private void validateUserDtoForCreation(RegisterUserDto dto) {
        validateEmail(dto.getEmail());
        validatePhone(dto.getPhone());

        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new UserBadRequestException("Логин уже занят другим пользователем.");
        }
    }

    private List<UserDto> getUsersBySpecification(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable).stream()
            .map(UserMapper::toUserDtoFromUser)
            .collect(Collectors.toList());
    }

    private void deleteValue(String removeValue, Set<String> emailsOrPhones) {
        if (removeValue != null) {
            validateMinimalSize(emailsOrPhones);
            emailsOrPhones.remove(removeValue);
        }
    }

    private void validateMinimalSize(Set<String> emailsOrPhones) {
        if (emailsOrPhones.size() == 1) {
            throw new UserBadRequestException("Нельзя удалить последний адрес электронной почты/телефон.");
        }
    }

    private void updateEmail(User user, UpdateUserRequest dto) {
        if (dto.getEmail() != null) {
            validateEmail(dto.getEmail());
            user.getEmails().add(dto.getEmail());
        }
    }

    private void updatePhone(User user, UpdateUserRequest dto) {
        if (dto.getPhone() != null) {
            validatePhone(dto.getPhone());
            user.getPhones().add(dto.getPhone());
        }
    }
}