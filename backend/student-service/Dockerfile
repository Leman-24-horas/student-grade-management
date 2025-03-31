FROM openjdk:17
WORKDIR /app

COPY . .

RUN ./mvnw package -Dmaven.test.skip=true

# Remove unnecessary JAR file aka week6-0.0.1-SNAPSHOT.jar.original
RUN rm -f target/*.original

# Copy the final JAR separately for clarity
COPY target/student-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]