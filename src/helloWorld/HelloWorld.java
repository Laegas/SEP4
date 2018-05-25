package helloWorld;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kenneth on 17/05/2018.
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        /*
        JSONArray features = new JSONArray();

        String[] array1 = new String[]{"A", "B", "C"};
        String[] array2 = new String[]{"D", "E", "F"};

        features.put(new JSONObject()
            .put("type", "Feature")
            .put("properties", new JSONObject())
            .put("geometry", new JSONObject()
                    .put("type", "Polygon")
                )
            );


        JSONObject properties = features.getJSONObject(0).getJSONObject("properties");
        for(int i = 0; i < array1.length; i++) {
            properties.put(array1[i], array2[i]);
        }
        System.out.println(properties.toString());

        JSONObject object = new JSONObject()
                .put("features", features);

        System.out.println(object.toString());
        */
    }
}
