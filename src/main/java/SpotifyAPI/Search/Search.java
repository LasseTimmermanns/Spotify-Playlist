package SpotifyAPI.Search;

import SpotifyAPI.Requests;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static spotifyplaylist.SpotifyPlaylistApplication.LOGGER;

public class Search {

    public static JSONArray search(String query, String bearerToken){
        LOGGER.info("New Search Request with query " + query);
        Map<String, String> params = Map.of(
                "q", query,
                "type", "track",
                "market", "DE",
                "limit", "10"
        );

        JSONObject obj = new JSONObject(Requests.sendRequest("https://api.spotify.com/v1/search", params, bearerToken).get(0));
        JSONArray arr = obj.getJSONObject("tracks").getJSONArray("items");

        return arr;
    }

}
