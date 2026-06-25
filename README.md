# VISO TRUST MCP Server

A Model Context Protocol (MCP) server for integrating VISO TRUST API capabilities with AI assistants.

## Hosted Remote Server

VISO TRUST hosts a managed instance of this MCP server, so you don't need to build, run, or self-host anything to get started:

```
https://mcp.visotrust.com/mcp
```

Connect any MCP client to this endpoint using the **Streamable HTTP** transport and authenticate with your VISO TRUST API token. For information on how to generate an API token, see the [VISO TRUST support documentation](https://support.visotrust.com/article/olo26aapun-generateaccesstoken).

Example client configuration:
```json
{
    "mcpServers": {
        "viso-mcp": {
            "type": "streamable-http",
            "url": "https://mcp.visotrust.com/mcp",
            "headers": {
                "Authorization": "Bearer <your-api-token>"
            }
        }
    }
}
```

To test the hosted endpoint with MCP Inspector, run `npx @modelcontextprotocol/inspector`, set **Transport Type** to **Streamable HTTP**, and use the URL above.

The remainder of this document covers running the server yourself (locally or in your own infrastructure).

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

### Application Profiles

This application supports Spring Boot profiles to enable different configurations for different deployment scenarios.

#### Remote Profile

The `remote` profile is designed for **remote MCP support over the Streamable HTTP transport**. This profile runs the server as a web application so it can communicate with remote clients over HTTP. (Streamable HTTP is the Spring AI 2.0 default and replaces the deprecated SSE transport.)

**Key differences in the remote profile:**
- Runs as a servlet web application (Tomcat) on port `3001` instead of standard I/O
- Exposes the MCP endpoint at `POST /mcp` (Streamable HTTP)
- Optimized server settings for remote client connections
- Enhanced logging for distributed debugging

> **Note:** Clients connect to the single Streamable HTTP endpoint `…/mcp`. The legacy SSE endpoints (`/sse` + `/mcp/message`) are no longer served.

**How to activate the remote profile:**

When running with Java directly:
```bash
java -jar viso-mcp-server-<version>.jar --spring.profiles.active=remote
```

When running with Gradle:
```bash
./gradlew bootRun --args="--spring.profiles.active=remote"
```

When using Docker:
```bash
docker run -i --rm \
  -e VISOTRUST_API_TOKEN=<your-api-token> \
  -e SPRING_PROFILES_ACTIVE=remote \
  viso-mcp-server
```

**When to use the remote profile:**
- When deploying the MCP server to a remote server or cloud environment
- When clients will connect via Streamable HTTP rather than direct stdio
- When you need enhanced logging and monitoring for distributed deployments
- When integrating with web-based AI assistants that connect over Streamable HTTP

For local development and direct stdio communication, use the default profile (no profile specification needed).

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

#### `get_assessment_summary` - Get the summary for an assessment by its ID
- id: Assessment ID (number, required)

Returns the summary details for a specific assessment.

#### `create_assessment` - Start an assessment for an existing relationship
- relationshipId: The ID of the relationship for which to create an assessment (number, required)
- recipientEmail: Email address of the assessment recipient (string, optional)
- recipientFirstName: First name of the assessment recipient (string, optional)
- recipientLastName: Last name of the assessment recipient (string, optional)
- publicDocumentUrls: URLs of public documents to include in the assessment (string[], optional)
- followupType: Type of follow-up (string enum, optional)
- followupRiskThreshold: Risk level threshold that triggers follow-up (string enum, optional)
- followupTimeline: Timeline for follow-up actions (string enum, optional)
- collectionTimeline: Timeline for vendor to complete assessment submission (string enum, optional)
- noVendorResponseAction: Action to take when the vendor does not respond (string enum, optional)
- aiProcessingOnly: Whether to only process using AI without human review (boolean, optional)
- requestedAuditTypes: Types of audits requested for this assessment (string[], optional)

Returns the created assessment details.

#### `update_assessment_expiration_date` - Update the deadline by which the vendor must submit their assessment response
- id: Assessment ID (number, required)
- expirationDate: New expiration date/time, ISO-8601 with offset; must be in the future (string, required)

Returns a confirmation message.

#### `update_assessment_followup` - Update the follow-up configuration for an assessment
- id: Assessment ID (number, required)
- followupType: Follow-up type (string enum, required)
- followupRiskThreshold: Risk threshold at or above which a follow-up assessment should be triggered (string enum, optional)
- followupTimeline: Follow-up timeline (string enum, optional)

Returns a confirmation message.

### Audit Logs

#### `get_user_audit_log_events` - Get user-scoped audit log events for your organization
- start: Start date/time of the query, ISO-8601 with offset (string, required)
- end: End date/time of the query, ISO-8601 with offset (string, required)
- eventTypes: Optional set of event types to filter (e.g. `USER_LOGGED_IN`); leave empty for all (string[], optional)

Returns a list of user audit log events, limited to 500 records.

#### `get_audit_log_events` - Get filtered audit log events (user, org, assessment, and relationship events)
- start: Start date/time of the query, ISO-8601 with offset (string, required)
- end: End date/time of the query, ISO-8601 with offset (string, required)
- eventTypes: Optional set of event types to filter (e.g. `ASSESSMENT_COMPLETED`, `RELATIONSHIP_CREATED`); leave empty for all (string[], optional)

Returns polymorphic audit log event records. Each item has at least `auditEventType` and `dateTime`.

### Business Cases

#### `get_all_business_cases` - Get all available business cases for your organization
No parameters required.

Returns a list of all business cases available for your organization.

### Data Types

#### `get_all_datatypes` - Get all available data types for your organization
No parameters required.

Returns a list of all data types available for your organization.

### Vendor Directory

#### `search_vendor_directory` - Look up a vendor in the VISO TRUST vendor directory by URL or domain
- urlOrDomain: The URL or domain name to search for, e.g. `example.com` (string, required)

Returns basic vendor metadata (name, homepage, description, favicon, known domains).

### Relationships

#### `get_all_relationships` - Get a list of all relationships and their assessment details
No parameters required.

Returns information about third-party vendors including their assessment status, risk levels, and contact details.

#### `get_relationship_by_id` - Get a specific relationship and its assessment details by ID
- id: Relationship ID (number, required)

Returns detailed information about a third-party vendor including assessment status, risk levels, and contact details.

#### `get_relationship_assessment_history` - Get the assessment history for a relationship
- id: Relationship ID (number, required)

Returns a list of assessments associated with the specified relationship.

#### `create_relationship` - Create a new relationship with a third-party vendor
- name: Name of the relationship/vendor (string, required)
- homepage: Homepage URL of the vendor (string, required)
- businessOwnerEmail: Email address of the business owner (string, required)
- businessOwnerFirstName: First name of the business owner (string, optional)
- businessOwnerLastName: Last name of the business owner (string, optional)
- description: Description of the relationship/vendor (string, optional)
- contextTypes: List of business context types for this relationship (object[], optional)
- dataTypes: List of data types handled in this relationship (object[], optional)
- tags: List of tags to categorize this relationship (string[], optional)
- thirdPartyContact: Contact details of the third-party vendor representative (object, optional)

Returns the created relationship details.

#### `create_relationship_by_domain` - Create a new relationship using only the vendor domain
- domain: Domain of the vendor, e.g. `visotrust.com` (string, required)
- vendorName: Name of the vendor (string, required)
- product: Product offered by the vendor (string, optional)
- description: Description of the vendor relationship (string, optional)

Returns the created relationship details.

#### `update_relationship` - Update an existing relationship with a third-party vendor
- id: Relationship ID (number, required)
- name: Name of the relationship/vendor (string, required)
- homepage: Homepage URL of the vendor (string, optional)
- description: Description of the relationship/vendor (string, optional)
- contextTypes: List of business context types (object[], optional)
- dataTypes: List of data types handled in this relationship (object[], optional)
- businessOwnerEmail: Email address of the business owner (string, optional)
- businessOwnerFirstName: First name of the business owner (string, optional)
- businessOwnerLastName: Last name of the business owner (string, optional)
- tags: List of tags (string[], optional)

Returns the updated relationship details.

#### `partially_update_relationship` - Partially update an existing relationship
Accepts the same fields as `update_relationship`. Only fields provided in the request are changed; other fields are left untouched.

Returns the updated relationship details.

#### `search_relationships` - Search for relationships by domain name or vendor name
- domains: List of domain names to search for (string[], required)
- name: Name of the vendor/relationship to search for (string, required)

Returns a list of matching relationships with their assessment details.

#### `create_tags` - Create new tags for categorizing relationships
- tags: List of tag names to create (string[], required)

Returns a list of all tags including the newly created ones.

#### `update_third_party_contact` - Update the contact details for a third-party vendor
- relationshipId: Relationship ID (number, required)
- email: Contact email (string, required)
- firstName: Contact first name (string, required)
- lastName: Contact last name (string, required)

Returns the updated relationship details.

#### `onboard_relationship` - Onboard a relationship, optionally with approval summary and lifecycle management settings
- id: Relationship ID (number, required)
- approvalSummary: Optional approval summary recorded at onboarding (string, optional)
- lifecycleManagementUpdateRequest: Optional lifecycle management settings (object, optional)
  - artifactUpdateSettings.artifactUpdateType: Artifact update type (string enum)
  - recertificationSettings.recertificationType: Recertification type (string enum)
  - recertificationSettings.recertificationDate: Date/time of next recertification, ISO-8601 with offset (string)
  - recertificationSettings.reviewFrequency: `THREE_YEARS`, `TWO_YEARS`, `ANNUAL`, `SEMIANNUAL`, or `QUARTERLY` (string enum)

Returns the onboarded relationship details.

#### `offboard_relationship` - Offboard a relationship
- id: Relationship ID (number, required)

Returns the offboarded relationship details.

#### `archive_relationship` - Archive a relationship
- id: Relationship ID (number, required)

Returns the archived relationship details.

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

### Intelligence Reports

#### `create_bitsight_intelligence_report` - Create a new BitSight intelligence report
- request: BitSight report parameters (object, required)
  - vendorDomain: The vendor's primary domain name (string, required)
  - reportDate: The date/time the report was generated (ISO 8601 string, required)
  - link: Optional link to the provider's UI (string, optional)
  - guid: BitSight GUID for the entity (string, required)
  - customId: Custom identifier from BitSight (string, optional)
  - name: Display name of the BitSight entity (string, optional)
  - description: Description of the BitSight entity (string, optional)
  - primaryDomain: Primary domain for the BitSight entity (string, optional)
  - ratingRange: BitSight rating range (string, optional)
  - ratingColor: BitSight rating color (string, optional)
  - confidence: Confidence level of the BitSight rating (string, optional)

Returns the created intelligence report.

#### `create_security_scorecard_intelligence_report` - Create a new SecurityScorecard intelligence report
- request: SecurityScorecard report parameters (object, required)
  - vendorDomain: The vendor's primary domain name (string, required)
  - reportDate: The date/time the report was generated (ISO 8601 string, required)
  - link: Optional link to the provider's UI (string, optional)
  - grade: SecurityScorecard letter grade (string, required)
  - domain: Domain associated with the scorecard entity (string, optional)
  - score: Numeric score from SecurityScorecard (number, optional)

Returns the created intelligence report.

#### `create_recorded_future_intelligence_report` - Create a new Recorded Future intelligence report
- request: Recorded Future report parameters (object, required)
  - vendorDomain: The vendor's primary domain name (string, required)
  - reportDate: The date/time the report was generated (ISO 8601 string, required)
  - entityType: Recorded Future entity type, e.g. `Company` (string, required)
  - entity: Recorded Future entity identifier (string, required)
  - riskScore: Numeric risk score (number, required)
  - riskLevel: Risk level label, e.g. `Critical`/`High`/`Medium`/`Low` (string, required)
  - link: Optional link to the report in the provider's UI (string, optional)
  - firstSeen: Earliest observed date for the entity, ISO 8601 (string, optional)
  - lastSeen: Most recent observed date for the entity, ISO 8601 (string, optional)
  - triggeredRuleCount: Number of Recorded Future rules that have triggered (number, optional)
  - maxRuleCount: Maximum number of Recorded Future rules evaluated (number, optional)
  - summary: Optional summary text from Recorded Future (string, optional)
  - criticalityLabel: Recorded Future criticality label for the entity (string, optional)

Returns the created intelligence report.

#### `get_intelligence_reports_by_vendor` - Get all intelligence reports for a vendor
- vendorDomain: The vendor's primary domain name (string, required)

Returns a list of intelligence reports for the specified vendor.

#### `get_latest_intelligence_report` - Get the latest intelligence report for a vendor from a specific source
- vendorDomain: The vendor's primary domain name (string, required)
- source: Intelligence provider (string enum: `BITSIGHT`, `SECURITY_SCORECARD`, or `RECORDED_FUTURE`, required)

Returns the latest intelligence report for the specified vendor and source.

### Users

#### `get_all_users` - Get all users in your organization
- page: Results page to retrieve (number, optional; default 0)
- size: Number of records per page (number, optional; default 20)
- sort: Sorting criteria in the format: property(,asc|desc) (string, optional)

Returns a paginated list of users.

#### `get_user_by_email` - Get a user by email
- email: User's email address (string, required)

Returns the user details.

#### `create_user` - Create a new user
- request: User creation parameters (object, required)
  - email: Email address of the new user (string, required)
  - firstName: First name of the new user (string, required)
  - lastName: Last name of the new user (string, required)

Returns the created user.

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
