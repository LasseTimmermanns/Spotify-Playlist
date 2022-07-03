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


@SpringBootApplication
public class SpotifyPlaylistApplication {

    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String TOKEN = "BQAZf857yIyd3C3JE7Ji_Kw0msWXJXGW0q4Pp1rZ0SuMOUiipLJt7XAvkSsUGxQ_FiMfY87wbvMZD4ZH49hjxjp4c23ID3VFqmAg1cq60QN5YU7n_rZEwcEnGrUQFaPQhswO-kL2muPtIGzb9xntvCUGwyDHhHkfaX-7rdrQcF0ILpy7vL97GLaxqL2h8bbaLKFukHmzn9Z0SxGVVDIBno_LdV4";

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
        ArrayList<String> s = sendRequest("https://api.spotify.com/v1/me/playlists");
        JSONArray arr = mergeItems(s);
        createPlaylists(arr);
    }

    public static JSONArray mergeItems(ArrayList<String> jsons) {
        if (jsons == null) throw new NullPointerException("JSONS cant be null");
        if (jsons.size() == 0) try {
            throw new Exception("Size of jsons must be at least 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JSONArray arr = new JSONObject(jsons.get(0)).getJSONArray("items");
        for (int i = 1; i < jsons.size(); i++) {
            JSONArray current = new JSONObject(jsons.get(i)).getJSONArray("items");
            arr.putAll(current);
        }

        return arr;
    }

    public static ArrayList<String> sendRequest(String url) {
        return sendRequest(url, new ArrayList<String>());
    }

    public static ArrayList<String> sendRequest(String url, ArrayList<String> json) {
        InputStream response = getResponse(applyDefaultRequestProperties(createConnection(url)));

        String currentJson = "";

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            currentJson += responseBody;
        }

        json.add(currentJson);
        String next = getNext(currentJson);

        if (next != null) return sendRequest(next, json);
        return json;
    }


    private static String getNext(String json) {
        JSONObject object = new JSONObject(json);


        if (object.isNull("next")) return null;
        return object.getString("next");
    }

    public static void createPlaylists(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            JSONObject current = items.getJSONObject(i);
            String url = current.getString("href");
            String name = current.getString("name");
            LOGGER.info("Creating Playlist " + name);
            JSONObject tracksObject = current.getJSONObject("tracks");
            Track[] tracks = tracks = getTracks(tracksObject.getString("href"), tracksObject.getInt("total"), name);
            new Playlist(url, name, tracks);
        }
    }

    public static Track[] getTracks(String url, int total, String playlistName) {

        Track[] tracks = new Track[total];
        JSONArray arr = mergeItems(sendRequest(url));

        for (int i = 0; i < arr.length(); i++) {
            JSONObject track = arr.getJSONObject(i).getJSONObject("track");
            String name = track.getString("name");

            try {
                String id = track.getString("id");
                tracks[i] = new Track(id, name);
            } catch (JSONException e) {
                LOGGER.info("Song " + name + " from Playlist " + playlistName + " keeps unhandled cause it's offline track");
            }
        }

        return tracks;
    }

    public static HttpURLConnection createConnection(String url) {
        try {
            return (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpURLConnection applyDefaultRequestProperties(HttpURLConnection connection) {
        String authorization = "Bearer " + TOKEN;
        String contentType = "application/json";

        Map<String, String> properties = Map.of("Authorization", authorization, "Content-Type", contentType);

        return applyRequestProperties(connection, properties);
    }

    public static HttpURLConnection applyRequestProperties(HttpURLConnection connection, Map<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return connection;
    }

    public static InputStream getResponse(HttpURLConnection connection) {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            InputStream response = connection.getErrorStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                LOGGER.log(Level.SEVERE, responseBody);
            }
            throw new RuntimeException(e);
        }
    }

}
