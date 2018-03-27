package forzaquattro.utils;

import forzaquattro.model.BoardState;
import forzaquattro.model.PieceColor;

/**
 * holds method that can be shared with other methods/class.
 */
public final class Utility {

    private Utility() {
        super();
    }
    /**
     * 
     * @param piece - current player piece.
     * @return - opponent piece.
     */
    public static PieceColor getOpponentPiece(final PieceColor piece) {
        return (piece == PieceColor.RED ? PieceColor.YELLOW : PieceColor.RED);
    }

    /**
     * 
     * @param piece - current Piece.
     * @return - <code>Y</code> if playerpiece is equal to Yellow otherwise <code>R</code>.
     */
    public static int getColorPieceEquivalentInInt(final PieceColor piece) {
        return (piece == PieceColor.RED ? 1 : -1);
    }

    /**
     * Convert 1 or -1 to String.
     * @param p - <code>Number</code> 1 := "R" (PlayerPiece.RED), -1 := "Y" (PlayerPiece.YELLOW).
     * @return - PlayerPiece.RED:= R, PlayerPiece.YELLOW := Y.
     */
    public static String intPlayerPieceToString(final int p) {
        return (p == 1 ? "R" : p == -1 ? "Y" : "-");
    }

    /**
     * Convert number to PlayerPiece (ENUM).
     * @param c - <code>number</code> > 0 := PlayerPiece.RED ortherwise PlayerPiece.YELLOW.
     * @return - PlayerPiece.
     */
    public static PieceColor convertCountToPlayerPiece(final int c) {
        return (c > 0 ? PieceColor.RED : c < 0 ? PieceColor.YELLOW : null);
    }
    /**
     * 
     * @param piece - current player piece.
     * @return - equivalent BoardState for player piece.
     */
    public static BoardState playerBoardState(final PieceColor piece) {
        return (piece == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
    }
}
