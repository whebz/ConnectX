package forzaquattro.model;
import java.util.List;
import forzaquattro.utils.MoveException;;
/**
 * Board Interface.
 */
public interface Board extends Cloneable {

    /** 
     * Place player piece to given column.
     * @param col - column where to draw player piece [0 ; size()[1] - 1]
     * assertEquals(state, BoardState.REDWON);assertEquals(state, BoardState.REDWON);.
     * @param piece - player piece to draw.
     * @return - Move[ contains row index and column index].
     * @throws MoveException - .
     */
    Move droppiece(int col, PieceColor piece) throws MoveException;

    /** 
     * Place player piece to given column.
     * @param row - row where to draw player piece [0 ; size()[0] - 1] 
     * assertEquals(state, BoardState.REDWON);assertEquals(state, BoardState.REDWON);.
     * @param col - column where to draw player piece [0 ; size()[1] - 1]
     * assertEquals(state, BoardState.REDWON);assertEquals(state, BoardState.REDWON);.
     * @param piece - player piece to draw.
     * @return - Move[ contains row index and column index].
     * @throws MoveException - .
     */
    Move droppiece(int row, int col, PieceColor piece) throws MoveException;

    /**
     * @param checkThis - if not 0 boardState will check only for this {checkThis}.
     * @return - <code>STILL_PLAYING</code> if the current board is still playable.
     *           <code>REDWON</code> if the player playing with red piece won the game.
     *           <code>YELLOWWON</code> if the player playing with yellow piece won the game.
     *           <code>DRAW</code> if the current board is full and has no winner or
     *           given the current state, all remaining moves will only lead to draw.
     */
    BoardState getState(int checkThis);

    /**
     *
     * Clear all the moves done so far.
     */
    void reset();

    /**
     * @param color - color to get.
     * @return - last column where the color piece is set/drop.
     */
    Move getLastMoveByColor(PieceColor color);
    /**
     * 
     * @return - last column where the piece is set/drop.
     */
    Move getLastMove();
    /**
     * 
     * @return - total moves done.
     */
    int moveCount();

    /**
     * 
     * @return - list of all moves done.
     */
    List<Move> getMovesMade();
    /**
     * tells if current board is already full.
     * @return - <code>true</code> if full otherwise <code>false</code>.
     */
    Boolean isFull();

    /**
     * for test only.
     * @return - .
     */
    Boolean isEmpty();

    /**
     * Holds indexes of winning piece combination.
     * @return - collections.
     */
    List<Move> winningPositions();

    /**
     * draw board console.
     */
    void drawBoard();

    /**
     * make a copy of current board object.
     * @return - A new Board.
     */
    Board clone();

    /**
     * 
     * @return - how many pieces needs to win the game.
     */
    int numWinPieces();

    /**
     * Given row and column index return it's content.
     * @param r - row index.
     * @param c - column index.
     * @return - current value of matrix [r][c].
     */
    int boardCellValue(int r, int c);

    /**
     * delete last move.
     */
    void undoLastMove();

    /**
     * @return - new int [] { rowsize, columnsize }.
     */
    int[] size();

    /**
     * .
     * @return - .
     */
    List<Move> getPossibleMoves();
}