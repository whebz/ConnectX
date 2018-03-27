package forzaquattro.view;

import java.util.List;
import forzaquattro.utils.Pair;

/**
 * This is the interface for the GUI.
 *
 */
public interface View {

    /**
     * Method called by the controller to draw the current table.
     * @param col
     *              Columns
     * @param row
     *              Rows
     * @param name
     *              The name of the game's variant
     */
    void drawTable(int col, int row, String name);

    /**
     * Method called by the controller when a player moves a pawn.
     * @param row
     *          the row of the current move
     * @param col
     *          the column of the current move
     */
    void drawMove(int col, int row);

    /**
     * Method called by the controller when the match is finished.
     * @param isDraw
     *             a boolean used to communicate to the view if the game is draw
     */
    void matchFinished(boolean isDraw);

    /**
     * Method called by the controller to get the option menu.
     * @return
     *          the option menu
     */
    MenuManager getMenu();

    /**
     * Method called by the View to set the option menu.
     * @param menu
     *              the option menu
     */
    void setMenu(MenuManager menu);

    /**
     * Method called by the controller when the user choose a not available column.
     */
    void showWrongMoveMessage();

    /**
     * Method used to tell the View that is the computer turn.
     */
    void computerTurn();

    /**
     * Method used to show the winning sequence.
     * 
     * @param winningSequence
     *                         a List with the coordinates of the winning sequence
     */
    void showWinningPieces(List<Pair<Integer, Integer>> winningSequence);

    /**
     * Method used to remove a pawn from the grid.
     * @param row 
     *              the row
     * @param col
     *              the column
     * @param lastMove
     *              the column of the last move
     */
    void undoLastMove(int row, int col, int lastMove);
}
