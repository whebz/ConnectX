package forzaquattro.model;

import java.util.List;

import forzaquattro.utils.MoveException;

/**
 * Game Logic Interface.
 */
public interface Game {
    /**
     * get the game type.
     * @return - GameType [player vs player or player vs computer]
     */
    GameType getCurrentGameType();

    /**
     * Get the current game board state.
     * @return - Board.
     */
    Board getCurrentBoard();

    /**
     * @return - current game state.
     */
    BoardState getCurrentGameState();

    /**
     * add move to gameboard.
     * @param col - board column where to add piece.
     * @param piece - piece to add on gameboard.
     * @return - current move.
     * @throws MoveException - error thrown for invalid move.
     */
    Move addpiece(final int col, final PieceColor piece) throws MoveException;

    /**
     * @return - List of winning moves.
     */
    List<Move> winningPiecesPositions();

    /**
     * @return - new int [] { boardRowSize, boardColumnSize }.
     */
    int[] boardSize();

}
