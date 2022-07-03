package SpotifyAPI.Search;

import SpotifyAPI.Requests;
import org.json.JSONArray;

import java.util.Map;

public class Search {

    public void search(String query, String bearerToken){
        Map<String, String> params = Map.of(
                "q", query,
                "type", "track",
                "market", "DE",
                "limit", "10"
        );
        JSONArray result = Requests.mergeItems(Requests.sendRequest("https://api.spotify.com/v1/search", params, bearerToken));
    }

}