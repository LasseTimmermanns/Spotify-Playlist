package spotifyplaylist;

import java.util.ArrayList;

public class Playlist {

    public static ArrayList<Playlist> all = new ArrayList<Playlist>();
    private String url, name;
    private Track[] tracks;

    public Playlist(String url, String name){
        this.url = url;
        this.name = name;

        all.add(this);
    }

    public void addTracks(Track[] tracks){
        this.tracks = tracks;
    }

}
