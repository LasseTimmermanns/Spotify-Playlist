package spotifyplaylist;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {


    @GetMapping("/foo")
    public ResponseEntity<JSONObject> getFoooooo(){
        System.out.println("FOOOOOOOOOOOOOOOOOOOOOOOOO");
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", "http://localhost:4200");
        JSONObject obj = new JSONObject();
        obj.put("foo", "bar");
        return new ResponseEntity<JSONObject>(obj, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/query" )
    public ResponseEntity<String> getSearchSuggestions(@RequestParam("bearer") String token, @RequestParam("query") String query, @RequestParam("limit") String limit){
        //Returning title, imgurl, artist, songid, link

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        return new ResponseEntity<String>(QueryHandler.simulateRequest(token, query, limit).toString(), responseHeaders, HttpStatus.OK);
    }

}
