# Multi-stage build for VISO MCP Server

# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Define build argument for version
ARG VERSION=1.0.0

# Copy gradle files first for better layer caching
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src/ src/

# Update version in build.gradle
RUN sed -i "s/version = '.*'/version = '${VERSION}'/g" build.gradle

# Update version in application.properties
RUN sed -i "s/spring.ai.mcp.server.version=.*/spring.ai.mcp.server.version=${VERSION}/g" src/main/resources/application.properties

# Build the application
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Redefine the VERSION argument in the runtime stage
ARG VERSION=1.0.0

# Set environment variables
ENV SPRING_AI_MCP_SERVER_STDIO=true
ENV SPRING_MAIN_WEB_APPLICATION_TYPE=none
ENV SPRING_MAIN_BANNER_MODE=off

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/viso-mcp-server-${VERSION}.jar ./viso-mcp-server.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "viso-mcp-server.jar"]
