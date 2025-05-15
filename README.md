# VISO TRUST MCP Server

A Model Context Protocol (MCP) server for integrating VISO TRUST API capabilities with AI assistants.

## Requirements

- Java 21+
- Gradle
- Docker (optional for containerized deployment)
- MCP Inspector (optional for testing)

## Configuration

### VISO TRUST API Configuration

The following properties can be configured for the VISO TRUST API:

- `visotrust.api.base-url`: The base URL for the VISO TRUST API (default: http://localhost:8080)
- `visotrust.api.token`: Your API token from the VISO TRUST platform (required)
- `visotrust.api.timeout`: API request timeout in milliseconds (default: 30000)
- `visotrust.api.connect-timeout`: API connection timeout in milliseconds (default: 5000)

For information on how to generate an API token for the `visotrust.api.token` environment variable, see the [VISO TRUST support documentation](https://support.visotrust.com/article/olo26aapun-generateaccesstoken).

## Installation

### Quick Install
Click one of the buttons below to install the VISO MCP Server in VS Code:

<a href="https://insiders.vscode.dev/redirect/mcp/install?name=viso-mcp&inputs=%5B%7B%22id%22%3A%22viso_baseurl%22%2C%22type%22%3A%22promptString%22%2C%22description%22%3A%22VISO%20TRUST%20API%20Base%20URL%22%2C%22default%22%3A%22https%3A%2F%2Fapp.visotrust.com%22%7D%2C%7B%22id%22%3A%22viso_token%22%2C%22type%22%3A%22promptString%22%2C%22description%22%3A%22VISO%20TRUST%20API%20Token%22%2C%22password%22%3Atrue%7D%5D&config=%7B%22command%22%3A%22docker%22%2C%22args%22%3A%5B%22run%22%2C%22-i%22%2C%22--rm%22%2C%22-e%22%2C%22VISOTRUST_API_TOKEN%22%2C%22-e%22%2C%22VISOTRUST_API_BASEURL%22%2C%22viso-mcp-server%22%5D%2C%22env%22%3A%7B%22VISOTRUST_API_BASEURL%22%3A%22%24%7Binput%3Aviso_baseurl%7D%22%2C%22VISOTRUST_API_TOKEN%22%3A%22%24%7Binput%3Aviso_token%7D%22%7D%7D" rel="nofollow"><img src="https://img.shields.io/badge/VS_Code-Install_Server-0098FF?style=flat-square&logo=visualstudiocode&logoColor=white" alt="Install with Docker in VS Code" style="max-width: 100%;"></a>
<a href="https://insiders.vscode.dev/redirect/mcp/install?name=viso-mcp&inputs=%5B%7B%22id%22%3A%22viso_baseurl%22%2C%22type%22%3A%22promptString%22%2C%22description%22%3A%22VISO%20TRUST%20API%20Base%20URL%22%2C%22default%22%3A%22https%3A%2F%2Fapp.visotrust.com%22%7D%2C%7B%22id%22%3A%22viso_token%22%2C%22type%22%3A%22promptString%22%2C%22description%22%3A%22VISO%20TRUST%20API%20Token%22%2C%22password%22%3Atrue%7D%5D&config=%7B%22command%22%3A%22docker%22%2C%22args%22%3A%5B%22run%22%2C%22-i%22%2C%22--rm%22%2C%22-e%22%2C%22VISOTRUST_API_TOKEN%22%2C%22-e%22%2C%22VISOTRUST_API_BASEURL%22%2C%22viso-mcp-server%22%5D%2C%22env%22%3A%7B%22VISOTRUST_API_BASEURL%22%3A%22%24%7Binput%3Aviso_baseurl%7D%22%2C%22VISOTRUST_API_TOKEN%22%3A%22%24%7Binput%3Aviso_token%7D%22%7D%7D&quality=insiders" rel="nofollow"><img src="https://img.shields.io/badge/VS_Code_Insiders-Install_Server-24bfa5?style=flat-square&logo=visualstudiocode&logoColor=white" alt="Install with Docker in VS Code Insiders" style="max-width: 100%;"></a>

### Manual Setup with VS Code
Add the following JSON block to your User Settings (JSON) file in VS Code. You can do this by pressing Ctrl + Shift + P and typing Preferences: Open User Settings (JSON).
```json
{
  "mcp": {
    "inputs": [
      {
        "type": "promptString",
        "id": "viso_baseurl",
        "description": "VISO TRUST API Base URL",
        "default": "https://app.visotrust.com"
      },
      {
        "type": "promptString",
        "id": "viso_token",
        "description": "VISO TRUST API Token",
        "password": true
      }
    ],
    "servers": {
      "viso-mcp": {
        "command": "docker",
        "args": [
          "run",
          "-i",
          "--rm",
          "-e",
          "VISOTRUST_API_TOKEN",
          "-e",
          "VISOTRUST_API_BASEURL",
          "visotrustai/viso-mcp-server:latest"
        ],
        "env": {
          "VISOTRUST_API_BASEURL": "${input:viso_baseurl}",
          "VISOTRUST_API_TOKEN": "${input:viso_token}"
        }
      }
    }
  }
}
```

Optionally, you can add a similar example (i.e. without the mcp key) to a file called .vscode/mcp.json in your workspace. This will allow you to share the configuration with others.
```json
{
  "inputs": [
    {
      "type": "promptString",
      "id": "viso_baseurl",
      "description": "VISO TRUST API Base URL",
      "default": "https://app.visotrust.com"
    },
    {
      "type": "promptString",
      "id": "viso_token",
      "description": "VISO TRUST API Token",
      "password": true
    }
  ],
  "servers": {
    "viso-mcp": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "VISOTRUST_API_TOKEN",
        "-e",
        "VISOTRUST_API_BASEURL",
        "visotrustai/viso-mcp-server:latest"
      ],
      "env": {
        "VISOTRUST_API_BASEURL": "${input:viso_baseurl}",
        "VISOTRUST_API_TOKEN": "${input:viso_token}"
      }
    }
  }
}
```
### Usage with Claude Desktop and other MCP Clients
#### Docker Configuration
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
                "visotrustai/viso-mcp-server:latest"
            ],
            "env": {
                "VISOTRUST_API_TOKEN": "<your-api-token>",
                "VISOTRUST_API_BASEURL": "https://app.visotrust.com"
            }
        }
    }
}
```

#### Java Configuration
```json
{
    "mcpServers": {
        "viso-mcp": {
            "command": "java",
            "args": [
                "-jar",
                "viso-mcp-server-<version>.jar",
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
    java -jar build/libs/viso-mcp-server-<version>.jar \
    --port 8080 --host localhost
```

Replace `<version>` with the current version of the project (e.g., `1.0.0` or the version from the latest release).

### CI/CD Pipeline

This project uses GitHub Actions for continuous integration and deployment. The workflow includes the following jobs:

#### Lint

Checks code formatting using Spotless:
```bash
./gradlew spotlessCheck
```

#### Build

Builds the application and creates a JAR file:
```bash
./gradlew build
```

#### Publish

When a new release is created:
1. Updates the project version in build.gradle to match the release tag
2. Uploads the JAR file to the GitHub release with the version from the release tag
3. Builds and pushes the Docker image to Docker Hub with tags:
   - `latest`
   - The release tag (e.g., `v1.0.0`)

##### Required Secrets for Publishing

To enable Docker Hub publishing, add these secrets to your GitHub repository:
- `DOCKERHUB_USERNAME`: Your Docker Hub username
- `DOCKERHUB_TOKEN`: Your Docker Hub access token

## 🛠️ Tools

This section provides documentation for the tools exposed by the VISO MCP Server. Each tool has a specific purpose, input parameters, and output format.

### Assessments

#### `get_assessment` - Get an assessment by its ID
- id: Assessment ID (number, required)

Returns detailed information about a specific assessment.

#### `create_assessment` - Start an Assessment
- relationshipId: The ID of the relationship for which to create an assessment (number, required)
- recipientEmail: The email of the recipient (string, required)
- recipientFirstName: The first name of the recipient (string, required)
- recipientLastName: The last name of the recipient (string, required)
- publicDocumentUrls: URLs of public documents (string[], optional)
- followupType: The type of followup (string, required)
- followupRiskThreshold: The risk threshold for followup (string, optional)
- aiProcessingOnly: Whether to use AI processing only (boolean, optional)
- files: Files to include with the assessment (byte[][], optional)

Returns the created assessment details.

### Audit Logs

#### `get_user_audit_log_events` - Get the audit log events for your organization
- request: Audit log request parameters (object, required)
  - startDate: Start date for the audit log events (string, required)
  - endDate: End date for the audit log events (string, required)
  - auditLogType: Type of audit log events to retrieve (string, optional)

Returns a list of user audit log events, limited to 500 records.

### Business Cases

#### `get_all_business_cases` - Get all available business cases for your organization
No parameters required.

Returns a list of all business cases available for your organization.

### Data Types

#### `get_all_datatypes` - Get all available data types for your organization
No parameters required.

Returns a list of all data types available for your organization.

### IQR (Intelligent Query Response)

#### `ask_trust_center` - Ask questions about your AI Trust Center
- request: Trust center query parameters (object, required)
  - query: The question to ask (string, required)

Returns AI-generated responses to questions about your AI Trust Center.

#### `ask_relationship` - Ask questions about a specific relationship
- request: Relationship query parameters (object, required)
  - relationshipId: The ID of the relationship to query (number, required)
  - query: The question to ask (string, required)

Returns AI-generated responses to questions about a specific relationship.

### Relationships

#### `get_all_relationships` - Get a list of all relationships and their assessment details
No parameters required.

Returns information about third-party vendors including their assessment status, risk levels, and contact details.

#### `get_relationship_by_id` - Get a specific relationship and its assessment details by ID
- id: Relationship ID (number, required)

Returns detailed information about a third-party vendor including assessment status, risk levels, and contact details.

#### `create_relationship` - Create a new relationship with a third-party vendor
- request: Relationship creation parameters (object, required)
  - vendorName: Name of the vendor (string, required)
  - businessOwnerEmail: Email of the business owner (string, required)
  - homepage: Vendor's homepage URL (string, optional)
  - businessContextIds: IDs of business contexts (number[], optional)
  - dataTypeIds: IDs of data types (number[], optional)
  - tags: Tags to apply to the relationship (string[], optional)

Returns the created relationship details.

#### `update_relationship` - Update an existing relationship with a third-party vendor
- request: Relationship update parameters (object, required)
  - id: Relationship ID (number, required)
  - vendorName: Name of the vendor (string, optional)
  - homepage: Vendor's homepage URL (string, optional)
  - businessContextIds: IDs of business contexts (number[], optional)
  - dataTypeIds: IDs of data types (number[], optional)
  - businessOwnerEmail: Email of the business owner (string, optional)
  - tags: Tags to apply to the relationship (string[], optional)

Returns the updated relationship details.

#### `partially_update_relationship` - Partially update an existing relationship
- request: Partial relationship update parameters (object, required)
  - id: Relationship ID (number, required)
  - [Any fields from update_relationship that need to be changed]

Returns the updated relationship details with only the specified fields changed.

#### `search_relationships` - Search for relationships by domain name or vendor name
- request: Search parameters (object, required)
  - query: Search query (string, required)

Returns a list of matching relationships with their assessment details.

#### `create_tags` - Create new tags for categorizing relationships
- request: Tag creation parameters (object, required)
  - tags: List of tags to create (string[], required)

Returns a list of all tags including the newly created ones.

#### `update_third_party_contact` - Update the contact details for a third-party vendor
- request: Contact update parameters (object, required)
  - relationshipId: Relationship ID (number, required)
  - email: Contact email (string, required)
  - firstName: Contact first name (string, required)
  - lastName: Contact last name (string, required)

Returns the updated relationship details.

#### `get_suggested_contacts` - Get suggested contacts for a relationship
- relationshipId: Relationship ID (number, required)

Returns a list of potential contacts at the vendor organization.

### Webhooks

#### `get_all_webhooks` - Get all webhooks
No parameters required.

Returns a list of all webhook configurations.

#### `get_webhook` - Get a webhook configuration by id
- id: Webhook ID (number, required)

Returns details of a specific webhook configuration.

#### `create_webhook_configuration` - Create a webhook configuration
- request: Webhook creation parameters (object, required)
  - url: Webhook URL (string, required)
  - secret: Webhook secret (string, required)
  - eventTypes: Types of events to trigger the webhook (string[], required)
  - serviceType: Type of service for the webhook (string, required)

Returns the created webhook configuration.

#### `update_webhook_configuration` - Update a webhook configuration
- request: Webhook update parameters (object, required)
  - id: Webhook ID (number, required)
  - url: Webhook URL (string, optional)
  - secret: Webhook secret (string, optional)
  - eventTypes: Types of events to trigger the webhook (string[], optional)
  - serviceType: Type of service for the webhook (string, optional)

Returns the updated webhook configuration.

#### `delete_webhook_configuration` - Delete a webhook configuration
- id: Webhook ID (number, required)

Deletes the specified webhook configuration.

## Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) with Google Java Format for code formatting. A pre-commit hook is automatically set up to ensure consistent code style.

### Setup

After cloning the repository, the pre-commit hook will be automatically set up when you run any Gradle command.

### Manual Formatting

To manually format all files:

```bash
./gradlew spotlessApply
```

To check if files are formatted correctly:

```bash
./gradlew spotlessCheck
```

If the pre-commit hook rejects your commit due to formatting issues, simply run `./gradlew spotlessApply` to fix the formatting and then try committing again.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
