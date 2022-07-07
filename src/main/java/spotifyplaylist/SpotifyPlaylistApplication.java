package spotifyplaylist;

import SpotifyAPI.Search.Search;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class SpotifyPlaylistApplication {

    private static final String TOKEN = "BQAAG8mU9Jd2q2kmXvFdMOp8JyJfsqPMpuqgTS0MCy5PtGSfpDu_9xhhunhTJvqb0BA0yJEj4f_LzLtVQTnX8AX6hxqVOCO-GlSy8x1fZFPCJkh5zEci9BXKGHniHN5yuBdSjtFz8AHGNV-sU2hkTfgKzDu26QX7gSGw35_bIQILjVLkn_eqpny_XFpeSyK3nCgqUGq-ztRdamLyIfg";
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPlaylistApplication.class, args);
        Search.search("Hey", TOKEN);
    }


}
