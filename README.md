To build and run the application, run the following PowerShell:

```PowerShell
$env:CLIENT_SECRET="Application client secret"
$env:CLIENT_ID="Application client ID"
$env:TENANT_ID="Azure AD tenant ID"
$env:CALENDAR_SCOPE="The Calendar API ReadCalendar scope e.g. api://2e6853d4-90f2-40d9-a97a-3c40d4f7bf58/ReadCalendar"
$env:AZURE_SCOPE="The Azure API SaveFile scope e.g. api://06bab64f-dc26-4156-9412-720e351259ab/SaveFile "
.\mvnw spring-boot:run 
```

Or the following Bash:

```bash
export CLIENT_SECRET="Application client secret" 
export CLIENT_ID="Application client ID" 
export TENANT_ID="Azure AD tenant ID" 
export CALENDAR_SCOPE="The Calendar API ReadCalendar scope e.g. api://2e6853d4-90f2-40d9-a97a-3c40d4f7bf58/ReadCalendar" 
export AZURE_SCOPE="The Azure API SaveFile scope e.g. api://06bab64f-dc26-4156-9412-720e351259ab/SaveFile" 

./mvnw spring-boot:run 
```
