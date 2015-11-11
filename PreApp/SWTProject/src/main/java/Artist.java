import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Mats on 10/11/15.
 */
public class Artist {

    private String name;
    private double familiarity;

    public Artist(String name) {
        this.name = name;
    }

    public double getFamiliarity() throws IOException {
        String requestURL = "http://developer.echonest.com/api/v4/artist/familiarity?api_key=MN9EYDKKLH6QBGHBH&name=" + this.name +"&format=json";
        JSONReader reader = new JSONReader();
        JSONObject obj = reader.readJsonFromUrl(requestURL);
        this.familiarity = obj.getJSONObject("response").getJSONObject("artist").getDouble("familiarity");
        this.name = obj.getJSONObject("response").getJSONObject("artist").getString("name");
        return this.familiarity;
    }
}
