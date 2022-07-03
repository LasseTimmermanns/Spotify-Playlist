package spotifyplaylist;

import java.util.ArrayList;

public class Playlist {

    public static ArrayList<Playlist> all = new ArrayList<Playlist>();
    private String url, name;
    private Track[] tracks;

    public Playlist(String url, String name, Track[] tracks){
        this.url = url;
        this.name = name;
        this.tracks = tracks;

        all.add(this);
    }

}
