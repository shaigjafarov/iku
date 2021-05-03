FROM openjdk:11-jre-slim AS main
COPY /target/etaskify-0.0.1-SNAPSHOT.jar /app/application.jar
WORKDIR /app
USER 1000
ENTRYPOINT exec java  -XX:InitialRAMPercentage=50.0 -XX:MaxRAMPercentage=75.0 -XX:MinRAMPercentage=25.0 -XX:+HeapDumpOnOutOfMemoryError -jar application.jar