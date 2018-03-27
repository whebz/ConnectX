package forzaquattro.utils;

/**
 * Defines what error is being thrown by board addmove.
 */
public class MoveException extends Exception {

    /**
     * serial.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * MoveException constructor.
     * @param s - Error message.
     */
    public MoveException(final String s) {
        super(s);
    }

}
