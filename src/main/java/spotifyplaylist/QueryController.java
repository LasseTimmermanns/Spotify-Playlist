package spotifyplaylist;

import SpotifyAPI.Search.Search;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import static spotifyplaylist.SpotifyPlaylistApplication.LOGGER;

@RestController
@RequestMapping(value = "/api")
public class QueryController {

    @GetMapping("/query" )
    public String getSearchSuggestions(@RequestParam("bearer") String token, @RequestParam("query") String query){
        //Returning title, imgurl, artist, songid, link

        return simulateRequest(token, query).toString();
    }

    public static JSONArray simulateRequest(String token, String query){
        String[] filter = {"name", "album>[9images>url", "[0artists>name", "id", "external_urls>spotify"};
        String[] newNames = {"title", "imgurl", "artist", "songid", "link"};

        JSONArray unfiltered = Search.search(query, token);
        JSONArray filtered = filterJsonArray(unfiltered, filter, newNames);

        return filtered;
    }

    public static JSONArray filterJsonArray(JSONArray in, String[] filter, String[] newNames){
        JSONArray newArr = new JSONArray();

        for(int i = 0; i < in.length(); i++){
            JSONObject obj = (JSONObject) in.get(i);
            newArr.put(filterJsonObject(obj, filter, newNames));
        }

        return newArr;
    }

    //artists>name
    //> means next child element
    //[0artists>name
    //[0 = its an JSONArray and take the first bzw last (9) (ONLY 0 OR 9 ALLOWED)
    public static JSONObject filterJsonObject(JSONObject in, String[] attributes, String[] newNames){
        JSONObject out = new JSONObject();

        int index = 0;
        for(String attr : attributes){
            String[] query = attr.split(">");

            JSONObject current = in;
            for(int i = 0; i < query.length - 1; i++){
                if(query[i].startsWith("[0")){
                    current = (JSONObject) current.getJSONArray(query[i].replace("[0", "")).get(0);
                }else if(query[i].startsWith("[9")){
                    JSONArray arr = current.getJSONArray(query[i].replace("[9", ""));
                    current = (JSONObject) arr.get(arr.length() - 1);
                }else{
                    current = current.getJSONObject(query[i]);
                }
            }

            out.accumulate(newNames[index], current.get(query[query.length - 1]));
            index++;
        }

        return out;
    }
}
