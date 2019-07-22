import main.MSClient;
import objects.MSGraphConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ConfigurationTests {

    @Test
    public void loadConfigurationTest() {
        String testFileString = new File(getClass().getClassLoader().getResource("testConfig.yaml").getFile()).getAbsolutePath();
        MSGraphConfiguration configuration = (new MSClient(testFileString)).getConfiguration();
        Assert.assertNotEquals(null, configuration);

        Assert.assertEquals(300 ,configuration.getAzureADClient().getPullInterval());
        Assert.assertEquals("sample-client-id",configuration.getAzureADClient().getClientId());
        Assert.assertEquals("sample-tenant-id",configuration.getAzureADClient().getTenantId());
        Assert.assertEquals("sample+client:secret*",configuration.getAzureADClient().getClientSecret());

//        Assert
    }


}
