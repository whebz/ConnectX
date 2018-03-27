/**
 * 
 */
package forzaquattro.model;

/**
 *
 */
public interface Player {
    /**
     * @return if the current player is either human or computer.
     */
    PlayerType getType();

    /**
     * @return color of player piece.
     */
    PieceColor getPlayerColorPiece();

    /**
     * @return player name.
     */
    String playerName();

    /**
     * Not implemented for human player.
     * @param current - current game state.
     * @return - best possible move.
     */
    Move generatemove(Game current);
}