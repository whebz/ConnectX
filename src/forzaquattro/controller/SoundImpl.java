package forzaquattro.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A simple implementation of the Sound interface. 
 * With this class the controller can play short sounds on each move and at the end.
 *
 */
public class SoundImpl implements Sound {

    private static final String DIR = "sounds/";
    private static final List<String> SONGNAMES = Arrays.asList("move1.wav", "move2.wav", "finish.wav");
    private final Map<Song, AudioClip> songs = new HashMap<>();

    /**
     * Initializes the songs map.
     */
    public SoundImpl() {

        Map<Song, String> audioNames = new HashMap<>();
        audioNames.put(Song.MOVE1, SONGNAMES.get(0));
        audioNames.put(Song.MOVE2, SONGNAMES.get(1));
        audioNames.put(Song.FINISH, SONGNAMES.get(2));

        for (Entry<Song, String> s: audioNames.entrySet()) {
            URL u = this.getClass().getClassLoader().getResource(DIR + s.getValue());
            this.songs.put(s.getKey(), Applet.newAudioClip(u));
        }
    }

    @Override
    public void play(final Song s) {
        stopSongs();
        this.songs.get(s).play();
    }

    private void stopSongs() {
        this.songs.values().forEach(e -> e.stop());
    }
}
