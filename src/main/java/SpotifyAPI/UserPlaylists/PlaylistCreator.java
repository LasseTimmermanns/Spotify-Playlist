package SpotifyAPI.UserPlaylists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spotifyplaylist.Playlist;
import spotifyplaylist.SpotifyPlaylistApplication;
import spotifyplaylist.Track;

import java.util.ArrayList;
import java.util.logging.Logger;

import static SpotifyAPI.Requests.sendRequest;

public class PlaylistCreator {
    private static final Logger LOGGER = SpotifyPlaylistApplication.LOGGER;

    public static void create(String bearerToken){
        createPlaylists(mergeItems(sendRequest("https://api.spotify.com/v1/me/playlists", bearerToken)), bearerToken);
    }

    private static JSONArray mergeItems(ArrayList<String> jsons) {
        if (jsons == null) throw new NullPointerException("JSONS cant be null");
        if (jsons.size() == 0) try {
            throw new Exception("Size of jsons must be at least 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JSONArray arr = new JSONObject(jsons.get(0)).getJSONArray("items");
        for (int i = 1; i < jsons.size(); i++) {
            JSONArray current = new JSONObject(jsons.get(i)).getJSONArray("items");
            arr.putAll(current);
        }

        return arr;
    }

    private static void createPlaylists(JSONArray items, String bearerToken) {
        for (int i = 0; i < items.length(); i++) {
            JSONObject current = items.getJSONObject(i);
            String url = current.getString("href");
            String name = current.getString("name");
            Playlist playlist = new Playlist(url, name);
            LOGGER.info("Creating Playlist " + name);
            JSONObject tracksObject = current.getJSONObject("tracks");
            Track[] tracks = tracks = getTracks(tracksObject.getString("href"), tracksObject.getInt("total"), name, playlist, bearerToken);
            playlist.addTracks(tracks);
        }
    }

    private static Track[] getTracks(String url, int total, String playlistName, Playlist playlist, String bearerToken) {

        Track[] tracks = new Track[total];
        JSONArray arr = mergeItems(sendRequest(url, bearerToken));

        for (int i = 0; i < arr.length(); i++) {
            JSONObject track = arr.getJSONObject(i).getJSONObject("track");
            String name = track.getString("name");

            try {
                String id = track.getString("id");
                tracks[i] = new Track(id, name, playlist);
            } catch (JSONException e) {
                LOGGER.info("Song " + name + " from Playlist " + playlistName + " keeps unhandled cause it's offline track");
            }
        }

        return tracks;
    }


}
