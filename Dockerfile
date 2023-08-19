FROM openjdk:17
COPY rest/target/storage-rest.jar storage.jar
EXPOSE 8081
CMD ["java", "-jar", "storage.jar"]