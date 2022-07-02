package spotifyplaylist;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
public class SpotifyPlaylistApplication {

	public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		SpringApplication.run(SpotifyPlaylistApplication.class, args);
		String s = sendRequest("https://api.spotify.com/v1/me/playlists");
		handleJSON(s);
	}

	public static String sendRequest(String url){
		InputStream response = getResponse(applyDefaultRequestProperties(createConnection(url)));
		String json = "";

		try (Scanner scanner = new Scanner(response)) {
			String responseBody = scanner.useDelimiter("\\A").next();
			json += responseBody;
		}

		return json;
	}



	public static void handleJSON(String jsonString){
		JSONObject object = new JSONObject(jsonString);
		JSONArray arr = object.getJSONArray("items");
		for(int i = 0; i < arr.length(); i++) {
			JSONObject current = arr.getJSONObject(i);
			String url = current.getString("href");
			String id = current.getString("id");
			String name = current.getString("name");
			String imageUrl = current.getJSONArray("images").getJSONObject(0).getString("url");

			JSONObject tracksObject = current.getJSONObject("tracks");

			Track[] tracks = getTracks(tracksObject.getString("href"), tracksObject.getInt("total"));

			//LOGGER.info("Creating Playlist with Attributes: " + "url: " + url + " id: " + id + " name: " + name + " image: " + imageUrl);
			LOGGER.info("Creating Playlist " + name);

			new Playlist(url, id, name, imageUrl, tracks);
		}
	}

	public static Track[] getTracks(String url, int total){

		Track[] tracks = new Track[total];
		String response = sendRequest(url);
		JSONObject object = new JSONObject(response);
		JSONArray arr = object.getJSONArray("items");

		for(int i = 0; i < arr.length(); i++){
			JSONObject track = arr.getJSONObject(i).getJSONObject("track");
			String id = track.getString("id");
			String name = track.getString("name");

			tracks[i] = new Track(id, name);
		}

		return tracks;
	}

	public static HttpURLConnection createConnection(String url){
		try {
			return (HttpURLConnection) new URL(url).openConnection();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpURLConnection applyDefaultRequestProperties(HttpURLConnection connection){
		String authorization = "Bearer BQBqXC5xJsxkoCWYJ-7ja4Y9NgXvb6bY83S3N9OFa5vhca6bxDhv-16gFrTjPUY2MquWDKnf4_n7DG9_RVHuoy_lrpMUUKIf7TfzoAjBSsCM5XIgyccbQJWchG-IdhMSvbYYT0u6gxVasI8OQvKcaRRUjUlzGV1Ch0C8uEYZ8yMQnQE8gjMVjclnhgNW8mHuBp8y1B-wSaJWQD6YOWZzlKZjpDY";
		String contentType = "application/json";

		Map<String, String> properties = Map.of(
				"Authorization", authorization,
				"Content-Type", contentType
		);

		return applyRequestProperties(connection, properties);
	}

	public static HttpURLConnection applyRequestProperties(HttpURLConnection connection, Map<String, String> properties){
		for(Map.Entry<String, String> entry : properties.entrySet()){
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		return connection;
	}

	public static InputStream getResponse(HttpURLConnection connection){
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
