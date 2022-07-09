package spotifyplaylist;

import SpotifyAPI.Search.Search;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@SpringBootApplication
public class SpotifyPlaylistApplication {

    //private static final String TOKEN = "BQCA_xIJGfJB_JZzuYsd48iuSbJxdSwYElXg_v7p1jLEX9F7eEKh9ZbOVwjW67A5d043pYrZO-sE7VGu3MgFyOJCduVQF68zFuNTvL2ab7hMkqiJC7cSueMXUD7TmmccqRQ6jJhzhQwmD2GpHub4rB2P_iUDLzgZr87YJ4ouhXLfq17XhEHjIdS32EUg2TzHJjnP4CUkLY6XYdkzSL8";
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
       // QueryController.simulateRequest(TOKEN, "Hey");
    }
}
