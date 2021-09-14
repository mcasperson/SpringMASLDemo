To build and run the application, run the following PowerShell:

```PowerShell
$env:CLIENT_SECRET="Application client secret"
$env:CLIENT_ID="Application client ID"
$env:TENANT_ID="Azure AD tenant ID"
./mvnw spring-boot:run
```

Or the following Bash:

```bash
export CLIENT_SECRET="Application client secret"
export CLIENT_ID="Application client ID"
export TENANT_ID="Azure AD tenant ID"
./mvnw spring-boot:run 
```
