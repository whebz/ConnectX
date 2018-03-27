package forzaquattro.controller;

import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;
import forzaquattro.model.PieceColor;
import forzaquattro.view.MenuManager;
import forzaquattro.view.View;

/**
 * This is the interface of the controller, it can be used by any user interface
 * (textual, graphical...).
 *
 */
public interface Controller {

    /**
     * Method called by the View to set the Settings.
     * @param gv
     *                  Forza4 or Forza5
     * @param gt
     *                  Player vs Player or Player vs Computer
     * @param d
     *                  Difficult (Easy, Medium, Hard)
     */
    void setSettings(GameVariant gv, GameType gt, Difficult d);
    /**
     * 
     * @return
     *          The current player
     */
    PieceColor getCurrentPlayer();

    /**
     * getter for menu manager.
     * @return
     *          the reference to the menu
     */
    MenuManager getMenuManager();
    /**
     * Called by the view.
     * Communicates a new move to the model.
     * 
     * @param row the row clicked
     * @param col the column clicked
     */
    void newMove(int row, int col);


    /**
     * Adds the view at the collection of views of the application.
     * 
     * @param v
     *          the view to be added.
     */
    void registerView(View v);

    /**
     * Manipulates the statistics of the game.
     * @return the class to manipulate stats
     */
    StatsManagerImpl getStatsManager();

    /**
     * Called by the view, requests the cancellation of the last move.
     * if GameType is set to Player vs CPU the last two moves will deleted.
     */
    void notifyUndoMove();

    /**
     * Settings for the Difficult.
     *
     */
    enum Difficult {
        EASY,
        MEDIUM,
        HARD
    }

}
