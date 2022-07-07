package spotifyplaylist;

import SpotifyAPI.Search.Search;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class QueryController {

    @GetMapping("query" )
    public JSONArray getFeedback(@RequestParam("bearer") String token, @RequestParam("query") String query){
        System.out.println("Bearer: " + token);
        System.out.println("query: " + query);

        return Search.search(query, token);
    }

}
