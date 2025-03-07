FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 5081 5081
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]