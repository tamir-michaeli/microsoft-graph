# Microsoft Graph API Integration

Logzio-MSGraph is an integration that allows Logz.io users to receive Microsoft Graph APIs data related to their organization.
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

Add a description to this secret, like 'secret for Logzio-MSGraph integration'.

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

Create a yaml file which contains the configuration parameters for Logzio-MSGraph, check out our [sample config file](https://github.com/logzio/microsoft-graph/blob/master/config.yaml).

For a complete list of options, see the configuration parameters below.👇

#### Azure AD client parameters

| Parameter | Description |
|---|---|
| **azureADClient.tenantId** | **Required**. Azure AD Tenant ID. <br /> Can be found in the `Overview` section of the app you registered in step 1. |
| **azureADClient.clientId** | **Required**. Application Client ID. <br /> Can be found in the `Overview` section of the app you registered in step 1. |
| **azureADClient.clientSecret** | **Required**.  The Application Client Secret you created in step 2.  |
| **azureADClient.pullInterval** | Time interval, in seconds, to pull the logs with the Graph API. <br /> **Default**: `300` |

#### Logz.io Sender parameters

| Parameter | Description |
|---|---|
| **logzioSenderParameters.listenerUrl** | Listener URL. <br /> If you are not from the US see [Account region](https://docs.logz.io/user-guide/accounts/account-region.html#regions-and-urls) to get your Listner host.<br /> **Default**: `listener.logz.io` |
| **logzioSenderParameters.accountToken** | **Required**. Your Logz.io [account token](https://app.logz.io/#/dashboard/settings/manage-accounts) |
| **logzioSenderParameters.debug** | If `true` the sender will omit debug messages to the logger  |
| **logzioSenderParameters.fromDisk** | If `true`, logs are stored on disk until they're shipped (see [If from-disk=true](#if-fromdisk-true)). If `false`, metrics persist in memory until they're shipped (see [If from-disk=false](#if-fromdisk-false)). <br /> **Default**: `true` |
| **logzioSenderParameters.senderDrainIntervals** | How often the sender should drain the queue (in seconds). <br /> **Default**: `5` |

<h4 id="if-fromdisk-true">If from-disk=true</h4>

| Parameter | Description |
|---|---|
| **logzioSenderParameters.fileSystemFullPercentThreshold** | Threshold percentage of disk space at which to stop queueing. If this threshold is reached, all new logs are dropped until used space drops below the threshold. Set to `-1` to ignore threshold. <br /> **Default**: `98` |
| **logzioSenderParameters.gcPersistedQueueFilesIntervalSeconds** | Time interval, in seconds, to clean sent logs from the disk <br /> **Default**: `30` |
| **logzioSenderParameters.diskSpaceCheckInterval** | Time interval, in milliseconds, to check for disk space <br /> **Default**: `1000` |

<h4 id="if-fromdisk-false">If from-disk=false</h4>

| Parameter | Description |
|---|---|
| **logzioSenderParameters.inMemoryQueueCapacityInBytes** | The amount of memory, in bytes, Logzio-MSGraph can use for the memory queue. Set to `-1` for unlimited bytes. <br /> **Default**: `1024 * 1024 * 100` |
| **logzioSenderParameters.logsCountLimit** | The number of logs in the memory queue before dropping new logs. Default value is -1 (the sender will not limit the queue by logs count) <br /> **Default:** `-1` |


### 4. Download and run Logzio-MSGraph

Logzio-MSGraph can run in a docker or as a Java app.

<h4 id="in-docker">Run as a Docker</h4>
Run the docker with the config file you created at the previous step:

```
docker run -d -v myConfig.yaml:config.yaml logzio/logzio-msgraph
```

**Note**: the config file at the docker end must be named `config.yaml`.

<h4 id="as-java-app">Run as a Java app</h4>

Download the latest version of Logzio-MSGraph from the [release page](https://github.com/logzio/microsoft-graph/releases).

Run Logzio-MSGraph with the config file from the previous step:

```
java -jar logzio-msgraph.jar myConfig.yaml
```

Logs collected by this integration will have the type Microsoft-Graph
