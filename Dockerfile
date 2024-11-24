# Use a base image with Java (specifically OpenJDK)
FROM eclipse-temurin:22-jdk

# Set the working directory inside the container
WORKDIR /app/taller

# Copy the Spring Boot JAR file to the container
COPY target/Taller-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on
EXPOSE 8081


# Database connection details
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres.pubugucnogvxporcdrys
ENV SPRING_DATASOURCE_PASSWORD=TallerMecanico

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
