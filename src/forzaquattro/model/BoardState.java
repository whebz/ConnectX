package forzaquattro.model;

/**
 * 
 *
 */
public enum BoardState {
    /**
     * the current game still playable.
     */
    STILL_PLAYING,

    /**
     * Player red piece won.
     */
    REDWON,

    /**
     * Yellow piece won the game. 
     */
    YELLOWWON,

    /**
     * Player with red piece won.
     */
    DRAW
}