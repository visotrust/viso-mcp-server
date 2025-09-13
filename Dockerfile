# Multi-stage build for VISO MCP Server

# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy gradle files first for better layer caching
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src/ src/

# Build the application (uses version from build.gradle)
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Set environment variables
ENV SPRING_AI_MCP_SERVER_STDIO=true
ENV SPRING_MAIN_WEB_APPLICATION_TYPE=none
ENV SPRING_MAIN_BANNER_MODE=off

# Copy the built jar file from the build stage (uses version from build.gradle)
COPY --from=build /app/build/libs/viso-mcp-server-*.jar ./viso-mcp-server.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "viso-mcp-server.jar"]
