package spotifyplaylist;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Track {

    private String id, name;
    private Playlist playlist;

    public Track(String id, String name, Playlist playlist){
        this.id = id;
        this.name = name;
        this.playlist = playlist;
    }

}
