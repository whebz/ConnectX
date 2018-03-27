package forzaquattro.model;

import forzaquattro.utils.MoveException;
import forzaquattro.utils.Utility;

/**
 * Connect 4 variant (Attenta) implementation.
 */
public class VariantAttenta {

    private Board board;

    /**
     * 
     * @param original - .
     */
    public VariantAttenta(final Board original) {
        this.board = original.clone();
    }
    /**
     * Simulate next opponet move, if any move done by opponent will bring victory, 
     * player will use that move and undo his move.
     * @return - move that winning.
     */
    public Move validateLastMove() {
        Move move = board.getLastMove();
        int movevalue = move.getValue() == 1 ? -1 : 1;
        PieceColor nextplayer = Utility.convertCountToPlayerPiece(movevalue);
        BoardState playerState = (nextplayer == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
        BoardState state;
        Move newmove;
        // simulate opponent next move 
        for (int column = 0, colSize = board.size()[1]; column < colSize; column++)  {
            if (move.getColIndex() != column) {
                try {
                    newmove = board.droppiece(column, nextplayer);
                    state = board.getState(movevalue);
                    board.undoLastMove();
                    if (state != BoardState.STILL_PLAYING && playerState == state) {
                        return newmove;
                    }
                } catch (MoveException e) {
                    //ignore
                }
            }
        }
        return null;
    }
}
