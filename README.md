To build and run the application, run the following PowerShell:

```PowerShell
$env:CLIENT_SECRET="Application client secret"
$env:CLIENT_ID="Application client ID"
$env:TENANT_NAME="Azure AD B2C tenant name"
./mvnw spring-boot:run
```

Or the following Bash:

```bash
export CLIENT_SECRET= "Application client secret"
export CLIENT_ID= "Application client ID"
export TENANT_NAME= "Azure AD B2C tenant name"
./mvnw spring-boot:run 
```