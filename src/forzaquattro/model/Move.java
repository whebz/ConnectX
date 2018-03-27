package forzaquattro.model;

/**
 * Object to indicate where Piece is set.
 */
public class Move {
    /**
     * Piece row index position.
     */
    private int row;
    /**
     * Piece col index position.
     */
    private int col;
    /**
     * value of array[row][col].
     */
    private int val;
    /**
     * 
     * @param r - row index where Piece is set.
     * @param c - col index where piece is set.
     * @param v - array value.
     */
    public Move(final int r, final int c, final int v) {
        this.row = r;
        this.col = c;
        this.val = v;
    }

    /**
     * @return - row index where piece is set.
     */
    public int getRowIndex() {
        return this.row;
    }

    /**
     * @return - column index where piece is set.
     */
    public int getColIndex() {
        return this.col;
    }
    /**
     * 
     * @return - array value.
     */
    public int getValue() {
        return this.val;
    }
}
