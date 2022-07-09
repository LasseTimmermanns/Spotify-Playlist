package SpotifyAPI.Search;

import SpotifyAPI.Requests;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static spotifyplaylist.SpotifyPlaylistApplication.LOGGER;

public class Search {

    public static JSONArray search(String query, String bearerToken, String limit){
        query = URLEncoder.encode(query, StandardCharsets.UTF_8);
        LOGGER.info("New Search Request with query " + query + " and limit " + limit);
        Map<String, String> params = Map.of(
                "q", query,
                "type", "track",
                "market", "DE",
                "limit", limit
        );

        JSONObject obj = new JSONObject(Requests.sendRequest("https://api.spotify.com/v1/search", params, bearerToken).get(0));
        JSONArray arr = obj.getJSONObject("tracks").getJSONArray("items");

        return arr;
    }
}
