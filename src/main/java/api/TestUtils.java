package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;

import java.io.IOException;
import java.util.Properties;

public class TestUtils {

    public static String getJsonPath(APIResponse response, String key){
        String resp = response.text();
        JsonObject jsonObject = JsonParser.parseString(resp).getAsJsonObject();
        String[] keys = key.split("\\.");
        JsonObject current = jsonObject;
        for (int i = 0; i < keys.length - 1; i++) {
            current = current.getAsJsonObject(keys[i]);
        }
        return current.get(keys[keys.length - 1]).toString().replace("\"", "").trim();
    }

    public static String getGlobalValue(String key) {
        try(var input = TestUtils.class.getClassLoader().getResourceAsStream("global.properties")){
            if(input == null){
                throw new IOException("File not found");
            }
            var prop = new Properties();
            prop.load(input);
            return prop.getProperty(key);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
