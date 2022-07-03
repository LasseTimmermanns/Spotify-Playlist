package spotifyplaylist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static SpotifyAPI.Requests.sendRequest;
import static SpotifyAPI.SpotifyApiHandler.*;


@SpringBootApplication
public class SpotifyPlaylistApplication {

    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
        ArrayList<String> s = sendRequest("https://api.spotify.com/v1/me/playlists");
        JSONArray arr = mergeItems(s);
        createPlaylists(arr);
    }
}
