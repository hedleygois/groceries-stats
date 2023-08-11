FROM openjdk:20
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/groceries-stats-be.jar
ENTRYPOINT ["java","-jar","/app/groceries-stats-be.jar"]