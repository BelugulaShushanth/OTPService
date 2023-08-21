FROM amazoncorretto:11-alpine3.17-jdk
COPY build/libs/OTPService-0.0.1-SNAPSHOT.jar /opt/apps/OTPService-1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/opt/apps/OTPService-1-SNAPSHOT.jar"]