import main.FetchSendManager;

import objects.JsonArrayRequest;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FetchSendTests {

    @Test
    public void FetchSendTest() {
        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(new JsonArrayRequest() {
            @Override
            public JSONArray getData() {
                File signinsFile = new File(getClass().getClassLoader().getResource("sampleSignins.json").getFile());

                try {
                    String content = FileUtils.readFileToString(signinsFile, "utf-8");
                    JSONTokener tokener = new JSONTokener(content);
                    JSONObject object = new JSONObject(tokener);
                    return object.getJSONArray("value"); //(JSONArray) obj.get("value");
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        System.out.println(requests.get(0).getData().toString());
//        FetchSendManager manager = new FetchSendManager();
    }
}
