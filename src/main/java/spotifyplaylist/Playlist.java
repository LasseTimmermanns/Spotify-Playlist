package spotifyplaylist;

import java.util.ArrayList;

public class Playlist {

    public static ArrayList<Playlist> all = new ArrayList<Playlist>();
    private String url, id, name, imageUrl;
    private Track[] tracks;

    public Playlist(String url, String id, String name, String imageUrl, Track[] tracks){
        this.url = url;
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.tracks = tracks;

        all.add(this);
    }

}
