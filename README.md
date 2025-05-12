# VISO MCP Server

A Model Context Protocol (MCP) server for integrating VISO Trust API capabilities with AI assistants.

## Requirements

- Java 21+
- Gradle
- Docker (optional for containerized deployment)
- MCP Inspector (optional for testing)

## Configuration

### VISO Trust API Configuration

The following properties can be configured for the VISO Trust API:

- `visotrust.api.base-url`: The base URL for the VISO Trust API (default: http://localhost:8080)
- `visotrust.api.token`: Your API token from the VISO Trust platform (required)
- `visotrust.api.timeout`: API request timeout in milliseconds (default: 30000)
- `visotrust.api.connect-timeout`: API connection timeout in milliseconds (default: 5000)

For information on how to generate an API token for the `visotrust.api.token` environment variable, see the [VISO Trust support documentation](https://support.visotrust.com/article/olo26aapun-generateaccesstoken).

### Java Configuration
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
                "VISOTRUST_API_TOKEN": "<your-api-token>",
                "VISOTRUST_API_BASEURL": "https://app.visotrust.com"
            }
        }
    }
}
```
Note: The JAVA_TOOL_OPTIONS environment variable is used to set the JVM options for remote debugging. The address and port can be changed as needed.

### Docker Configuration
```json
{
    "mcpServers": {
        "viso-mcp": {
            "command": "docker",
            "args": [
                "run",
                "-i",
                "--rm",
                "-e", "VISOTRUST_API_TOKEN",
                "-e", "VISOTRUST_API_BASEURL",
                "viso-mcp-server"
            ],
            "env": {
                "VISOTRUST_API_TOKEN": "<your-api-token>",
                "VISOTRUST_API_BASEURL": "https://app.visotrust.com"
            }
        }
    }
}
```

## 💻 Development

### Docker Setup

#### Build Docker Image
```bash
docker build -t viso-mcp-server .
```

#### Run Docker Container
```bash
docker run -i --rm -e VISOTRUST_API_TOKEN=<your-api-token> viso-mcp-server
```

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
