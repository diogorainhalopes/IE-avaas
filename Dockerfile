# Use a base image with Java installed
FROM maven:3.11.0-openjdk-11 AS build

# Use a Maven image as the base


# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the project files to the working directory
COPY pom.xml .
COPY src ./src

# Build the project with Maven
RUN mvn clean package

# Expose the port on which your application will run (change the port if needed)
EXPOSE 8088

# Set the command to run your application
CMD ["./mwnw", "quarkus:dev"]