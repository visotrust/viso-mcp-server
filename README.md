# VISO MCP Server

A Model Context Protocol (MCP) server for integrating VISO Trust API capabilities with AI assistants.

## Requirements

- Java 21+
- Gradle
- MCP Inspector (optional for testing)

## Configuration
```json
{
    "mcpServers": {
        "viso-mcp": {
            "command": "java",
            "args": [
                "-jar",
                "viso-mcp-server-1.0.0.jar",
                "--port",
                "8080",
                "--host",
                "localhost"
            ],
            "env": {
                "JAVA_TOOL_OPTIONS": "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005",
                "VISOTRUST_API_TOKEN": "<your-api-token>"
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

1. Build MCP Server Jar File
```bash
./gradlew bootJar
```

2. Run MCP Inspector
```bash
npx @modelcontextprotocol/inspector \
    -e JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=\*:5005 \
    -e VISOTRUST_API_TOKEN=<your-api-token> \
    java -jar build/libs/viso-mcp-server-1.0.0.jar \
    --port 8080 --host localhost
```