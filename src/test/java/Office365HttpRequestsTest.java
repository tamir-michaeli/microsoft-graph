public class Office365HttpRequestsTest {

//    @Test
//    public void createHttpUrlConnectionTest() throws IOException {
//        HttpURLConnection con = MSGraphRequestExecutor.createHttpConnection("http://localhost:8080/");
//        Assert.assertEquals(con.getRequestMethod(), "POST", "the request method isn't POST");
//    }
//
//    @Test
//    public void connectionTest() throws Exception {
//        MockWebServer server = new MockWebServer();
//        server.enqueue(new MockResponse().setResponseCode(200));
//        server.start(8080);
//        String url = "http://localhost:8080/";
//        HttpURLConnection con = MSGraphRequestExecutor.createHttpConnection(url);
//        MSGraphRequestExecutor.getAccessToken(con, "0000", "0000");
//
//        RecordedRequest recordedRequest = server.takeRequest();
//        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), url);
//        Assert.assertEquals(recordedRequest.getMethod(), "POST", "the request method isn't POST");
//        Assert.assertEquals(recordedRequest.getHeader("Content-Type"), "application/x-www-form-urlencoded", "the value of 'Content-Type' isn't as expected");
//        Assert.assertEquals(recordedRequest.getHeader("User-Agent"), "Java client", "the value of 'User-Agent' isn't as expected");
//    }
}