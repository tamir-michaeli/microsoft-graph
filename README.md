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

Select `Microsoft Graph` >> `Application permissions`.

Check `AuditLog.Read.All` under `AuditLog`.

Click on `Add permissions`.

Click on `Grant admin consent for Default Directory` >> `Yes`

**Note**: if the `Grant admin consent for Default Directory` button is grayed out, 
you'll have to contact an Administrator in your organization.

### 4. Create a config file

Create a yaml file which contains the configuration parameters for this integration, check out our [sample config file](https://github.com/logzio/microsoft-graph/blob/master/config.yaml).

For a complete list of options, see the configuration parameters below the code block.👇

| Parameter | Description |
|---|---|
| **logzioSenderParameters.url** | Listener URL. <br /> If your login URL is app.logz.io, use `listener.logz.io`. If your login URL is app-eu.logz.io, use `listener-eu.logz.io`. <br /> **Default**: `https://listener.logz.io:8071` |
| **logzioSenderParameters.accountToken** | **Required**. Your Logz.io [account token](https://app.logz.io/#/dashboard/settings/manage-accounts) |
| **logzioSenderParameters.from-disk** | If `true`, metrics are stored on disk until they're shipped (see [If from-disk=true](#jolokia-if-fromdisk-true)). If `false`, metrics persist in memory until they're shipped (see [If from-disk=false](#jolokia-if-fromdisk-false)). <br /> **Default**: `true` |

<h4 id="jolokia-if-fromdisk-true">If from-disk=true</h4>

| Parameter | Description |
|---|---|
| **logzioSenderParameters.queue-dir** | Path to store the queue. Must be a path to an existing folder. <br /> **Default** `metrics` |
| **logzioSenderParameters.file-system-full-percent-threshold** | Threshold percentage of disk space at which to stop queueing. If this threshold is reached, all new metrics are dropped until used space drops below the threshold. Set to `-1` to ignore threshold. <br /> **Default**: `98` |
| **logzioSenderParameters.clean-sent-metrics-interval** | Time interval, in seconds, to clean sent metrics from the disk <br /> **Default**: `30` |
| **logzioSenderParameters.disk-space-checks-interval** | Time interval, in milliseconds, to check for disk space <br /> **Default**: `1000` |

<h4 id="jolokia-if-fromdisk-false">If from-disk=false</h4>

| Parameter | Description |
|---|---|
| **logzioSenderParameters.in-memory-queue-capacity** | The amount of memory, in bytes, jmx2logzio can use for the memory queue. Set to `-1` for unlimited bytes. <br /> **Default**: `1024 * 1024 * 100` |
| **logzioSenderParameters.log-count-limit** | The number of logs in the memory queue before dropping new logs. Default value is -1 (the sender will not limit the queue by logs count) <br /> **Default:** `-1` |


Logs collected by this integration will have the type Microsoft-Graph
