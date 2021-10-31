FROM openjdk:11.0.5-jdk-slim
COPY . .
RUN /gradlew build
ENTRYPOINT ["java","-jar","/build/libs/customer-api-application.jar"]
EXPOSE 8888
