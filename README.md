# Microsoft Graph API Integration

This integration allows Logz.io users to receive Microsoft Graph APIs data related to their organization.
The Supported APIs are:
   - Azure AD audit logs
   - Azure AD sign-in logs

## To integrate MS Graph and Logz.io   

### 1. Register an app in your Azure AD
Login to Azure portal and go to [App registration](https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationsListBlade).

Select `New registration` from the top menu.

Name the app as you see fit and click on `Register`.

### 2. Create a Client Secret
At the app you created in the previous step, choose `Certificates & secrets` from the side menu.

Click on `New client secret`.

Add a description to this secret, like 'secret for Graph integration with logz.io'.

Choose `Never` under `Expires` and click on `Add`.

Copy the value of the generated secret and save it somewhere.

**Note**: You won't be able to retrieve the secret's value after you leave this page.

### 3. Set the necessary permissions
At the app you created in step 1, choose `API permissions` from the side menu.

Click on `Add a permission`.




Logs collected by this integration will have the type Microsoft-Graph
