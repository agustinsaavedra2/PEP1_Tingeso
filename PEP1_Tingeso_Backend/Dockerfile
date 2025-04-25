FROM openjdk:21
ARG JAR_FILE=target/pep1_tingeso_backend.jar
COPY ${JAR_FILE} pep1_tingeso_backend.jar
ENTRYPOINT ["java", "-jar", "/pep1_tingeso_backend.jar"]