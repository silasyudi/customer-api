FROM openjdk:11.0.5-jdk-slim
COPY build/libs/customer-api-application.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8888
