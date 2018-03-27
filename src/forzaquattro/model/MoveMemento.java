package forzaquattro.model;

/**
 * State to be save on MoveCareTaker.
 */
public class MoveMemento {
    private final Move move;

    /**
     * 
     * @param m - state Move to save.
     */
    public MoveMemento(final Move m) {
        this.move = m;
    }

    /**
     * get saved Move.
     * @return - Move object.
     */
    public Move getMove() {
        return this.move;
    }
}
