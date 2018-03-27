package forzaquattro.controller;

/**
 * This is a simple interface that makes it possible to play short
 * songs on a new move of each player and at the end of the match.
 *
 */
public interface Sound {

    /**
     * 
     * @param s The song that should be played.
     */
    void play(Song s);

    /**
     * Determinates each song type.
     *
     */
    enum Song {
        MOVE1, MOVE2, FINISH
    }
}
