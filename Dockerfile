FROM openjdk:17
ADD target/poc-spring-boot-genai.jar poc-spring-boot-genai.jar
ENTRYPOINT ["java", "-Dserver.port=8085", "-jar", "poc-spring-boot-genai.jar"]