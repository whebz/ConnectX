package forzaquattro.view;

/**
 * This is the interface for the Manager of the game menus.
 *
 */
public interface MenuManager {

    /**
     * Shows the first menu.
     *      Options: New Game, Statistics, Exit.
     */
    void showMenu();

    /**
     * Shows the new game menu.
     *      Options: Forza4 / Forza5 / Viviani / Attenta , PvsP, PvsC  , Difficult (EASY, MEDIUM, HARD)
     */
    void showNewGameMenu();


    /**
     * Shows a window with the statistics.
     */
    void showStats();
}
