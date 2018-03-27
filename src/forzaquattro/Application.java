package forzaquattro;

import forzaquattro.controller.Controller;
import forzaquattro.controller.ControllerImpl;
import forzaquattro.view.View;
import forzaquattro.view.ViewImpl;

/**
 * This Class contains the main method that initializes the Forza4-5 application.
 *
 */
public final class Application {

    private Application() {
    }

    /**
     * The static Main method that initializes controller and view and configures them.
     * @param args
     *              unused
     */
    public static void main(final String[] args) {
        System.out.println("Starting Forza4-5 application...");   // log

        Controller c = ControllerImpl.getControllerImpl();
        View v = new ViewImpl(c);
        c.registerView(v);
        v.getMenu().showMenu();
    }

}
