package spotifyplaylist;

import SpotifyAPI.UserPlaylists.PlaylistCreator;
import org.json.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.logging.Logger;

import static SpotifyAPI.Requests.sendRequest;
import static SpotifyAPI.UserPlaylists.PlaylistCreator.*;


@SpringBootApplication
public class SpotifyPlaylistApplication {

    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
        PlaylistCreator.create();
    }
}
