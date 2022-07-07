package SpotifyAPI.Search;

import SpotifyAPI.Requests;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class Search {

    public static JSONArray search(String query, String bearerToken){
        Map<String, String> params = Map.of(
                "q", query,
                "type", "track",
                "market", "DE",
                "limit", "10"
        );

        JSONObject obj = new JSONObject(Requests.sendRequest("https://api.spotify.com/v1/search", params, bearerToken));
        System.out.println(arr);
        return arr;
    }

}
