package forzaquattro.view;

import forzaquattro.controller.Controller;

/**
 * Implementations of the Menu Manager.
 * This class has the reference of all the menus except for 
 * the stats menu that is created every time new to prevent bugs.
 */
public final class MenuManagerImpl implements MenuManager {


    private final Controller controller;
    private StartMenu startMenu;
    private NewGameMenu newGameMenu;


    /**
     * Constructor to get the controller reference and initialize variables.
     * @param c
     *          The controller to which communicate the settings of the game.
     */
    public MenuManagerImpl(final Controller c) {
        this.controller = c;
        this.startMenu = new StartMenu(this);
        this.newGameMenu = new NewGameMenu(this.controller);

    }
    @Override
    public void showMenu() {
       this.startMenu.show();
    }

    @Override
    public void showNewGameMenu() {

        this.newGameMenu.show();
    }

    @Override
    public void showStats() {
            new StatsMenu(this.controller).show();
    }
}
