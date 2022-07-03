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

    private static final String TOKEN = "BQAZf857yIyd3C3JE7Ji_Kw0msWXJXGW0q4Pp1rZ0SuMOUiipLJt7XAvkSsUGxQ_FiMfY87wbvMZD4ZH49hjxjp4c23ID3VFqmAg1cq60QN5YU7n_rZEwcEnGrUQFaPQhswO-kL2muPtIGzb9xntvCUGwyDHhHkfaX-7rdrQcF0ILpy7vL97GLaxqL2h8bbaLKFukHmzn9Z0SxGVVDIBno_LdV4";
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
        PlaylistCreator.create(TOKEN);
    }
}
