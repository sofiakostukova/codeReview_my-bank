FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar my-bank.jar
ENTRYPOINT ["java","-jar","/my-bank.jar"]