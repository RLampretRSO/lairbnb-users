FROM openjdk:8-jre-alpine

WORKDIR /app

ADD ./api/target/users-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "users-api-1.0-SNAPSHOT.jar"]