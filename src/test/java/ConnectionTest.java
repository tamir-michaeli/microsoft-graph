import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

    @Test
    public void connectionTest() throws Exception {
        Connection connection = new Connection( "c96a62e5-1e49-4187-b394-08b694e8bb0d", "323a95a2-22eb-46c7-a80b-1ebeadc88ca8", "dsvSLXZdaKJAJ5GrSS29bd1h7k89u4sDMOiglihZkzE=");
        String response = connection.connect();
        Assert.assertNotNull(response);
    }
}
