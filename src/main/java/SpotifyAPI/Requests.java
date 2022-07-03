package SpotifyAPI;

import org.json.JSONObject;
import spotifyplaylist.SpotifyPlaylistApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Requests {


    private static final Logger LOGGER = SpotifyPlaylistApplication.LOGGER;
    private static final String TOKEN = "BQAZf857yIyd3C3JE7Ji_Kw0msWXJXGW0q4Pp1rZ0SuMOUiipLJt7XAvkSsUGxQ_FiMfY87wbvMZD4ZH49hjxjp4c23ID3VFqmAg1cq60QN5YU7n_rZEwcEnGrUQFaPQhswO-kL2muPtIGzb9xntvCUGwyDHhHkfaX-7rdrQcF0ILpy7vL97GLaxqL2h8bbaLKFukHmzn9Z0SxGVVDIBno_LdV4";

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
        String next = getNextItemsFromAllEntries(currentJson);

        if (next != null) return sendRequest(next, json);
        return json;
    }

    private static String getNextItemsFromAllEntries(String json) {
        JSONObject object = new JSONObject(json);

        if (object.isNull("next")) return null;
        return object.getString("next");
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
