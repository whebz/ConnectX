package forzaquattro.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that handle all saved states.
 */
public class MoveCareTaker {
    private List<MoveMemento> mementoList = new ArrayList<MoveMemento>();

    /**
     * add state on the current list.
     * @param state - MoveMemento state to save.
     */
    public void add(final MoveMemento state) {
        this.mementoList.add(state);
    }

    /**
     * delete saved state.
     * @param state - state to delete.
     */
    public void delete(final MoveMemento state) {
        this.mementoList.remove(state);
    }

    /**
     * get a saved MoveMemento from the given index.
     * If index is lower than 0 or bigger than total state save return null.
     * @param index - where state is found.
     * @return - saved MoveMemento.
     */
    public MoveMemento get(final int index) {
        int size = this.mementoList.size();
        if (index >= 0 && index <= size) {
            return mementoList.get(index);
        }
        return null;
    }

    /**
     * get the last saved state.
     * If the collection of state is empty return null.
     * @return - save MoveMemento.
     */
    public MoveMemento getLastState() {
        int size = this.mementoList.size();
        if (size > 0) {
            return mementoList.get(size - 1);
        }
        return null;
    }

    /**
     * get total number of move states saved.
     * @return - number.
     */
    public int savedStatesCount() {
        return this.mementoList.size();
    }
}
