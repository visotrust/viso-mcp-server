# VISO MCP Server

A Model Context Protocol (MCP) server for integrating VISO Trust API capabilities with AI assistants.

## Requirements

- Java 21+
- Gradle
- MCP Inspector (optional for testing)

## Configuration for e.g. Claude Desktop

```json
{
  "mcpServers": {
    "viso-mcp": {
      "command": "/path/to/java/bin/java",
      "args": [
        "-jar",
        "/path/to/viso-mcp-server/build/libs/viso-mcp-server-1.0.0.jar",
        "--port",
        "1222",
        "--host",
        "localhost"
      ],
      "env": {
        "JAVA_TOOL_OPTIONS": "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
      }
    }
  }
}
```

Note: The JAVA_TOOL_OPTIONS environment variable is used to set the JVM options for remote debugging. The address and port can be changed as needed.

## 💻 Development

### Debugging

#### Install MCP Inspector
```bash
npm -g install @modelcontextprotocol/inspector
```

#### Run MCP Inspector for Testing

1. Build MCP Server
```bash
./gradlew clean build
```

2. Run MCP Inspector
```bash
npx @modelcontextprotocol/inspector \
    -e JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=\*:5006 \
    -e VISOTRUST_API_TOKEN=<your-api-token-here> \
    java -jar build/libs/viso-mcp-server-1.0.0.jar \
    --port 8087 --host localhost
```

Note: The JAVA_TOOL_OPTIONS environment variable is used to set the JVM options for remote debugging. The address and port can be changed as needed. Now you can use the MCP Inspector to test and debug your Spring Boot MCP server. The inspector provides a user-friendly interface (Browser) to interact with the server and visualize the data being exchanged. The inspector can be used to send requests to the server, view the responses, and analyze the data flow between the client and server. To debug the server, you can set breakpoints in your code and use the debugger in your IDE to step through the code and inspect variables. Attach the debugger to the running server using the remote debugging options specified in the JAVA_TOOL_OPTIONS environment variable.

Spring AI extends the MCP Java SDK with productivity enhancements that make it straightforward to build MCP servers.

## 🔧 Using This Server with Claude Desktop

This server can be integrated with Claude Desktop or other AI assistants that support MCP. After configuring the server in your AI assistant:

1. Start this Spring Boot MCP server
2. Open Claude Desktop and enable the server in settings
3. Ask Claude questions that require the tools provided by this server (like creating relationships, getting vendor information, etc.)
4. Watch as Claude seamlessly uses the server to access VISO Trust data and perform actions

## Configuration Properties

The server uses the following configuration properties (in `application.properties`):

```properties
spring.application.name=viso-mcp

# VISO TRUST API Configuration
visotrust.api.base-url=http://localhost:8080
visotrust.api.token=your-api-token
visotrust.api.timeout=30000
visotrust.api.connect-timeout=5000

# Spring AI MCP Server Configuration
spring.ai.mcp.server.enabled=true
spring.ai.mcp.server.name=viso-mcp-server
spring.ai.mcp.server.version=1.0.0
spring.ai.mcp.server.type=sync
spring.ai.mcp.server.stdio=true
```