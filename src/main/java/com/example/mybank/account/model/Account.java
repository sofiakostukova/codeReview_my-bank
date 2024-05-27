package com.example.mybank.account.model;

import com.example.mybank.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id")
    private User user;
    @Id
    @Column(name = "account_id", nullable = false)
    private Long id;
    @PositiveOrZero
    @Column(name = "init_balance")
    private BigDecimal initBalance;
    @PositiveOrZero
    @Column
    private BigDecimal balance;
}