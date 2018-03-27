package forzaquattro.test;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;

import forzaquattro.model.Game;
import forzaquattro.model.GameImpl;
import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;
import forzaquattro.model.Move;
import forzaquattro.model.PieceColor;
import forzaquattro.utils.MoveException;

public class GameObject {
        
    @org.junit.Test
    public void attentavariant() {
        Game game = new GameImpl(GameType.PvsP, GameVariant.Attenta);
        Move move; 
        /*
         * - - - - - - -
         * - - - - - - -
         * - - - - - - -
         * Y - - - - - -
         * Y - - - - - -
         * R Y R R R - -
         */
        try {
            game.addpiece(0, PieceColor.RED);
            game.addpiece(0, PieceColor.YELLOW);
            game.addpiece(2, PieceColor.RED);
            game.addpiece(1, PieceColor.YELLOW);
            game.addpiece(3, PieceColor.RED);
            game.addpiece(0, PieceColor.YELLOW);
            game.addpiece(4, PieceColor.RED);
            /*
             * Move theorically will produce this board.
             * 
             * [0][1][2][3][4][5][6]
             * [-][-][-][-][-][-][-][5]
             * [-][-][-][-][-][-][-][4]
             * [-][-][-][-][-][-][-][3]
             * [Y][-][-][-][-][-][-][2]
             * [Y][Y][-][-][-][-][-][1]
             * [R][Y][R][R][R][-][-][0]
             */
            move = game.addpiece(1, PieceColor.YELLOW);

            /*
             * But Variant Attenta will force player yellow to this move:
             * row = 0
             * column = 5
             * 
             * [0][1][2][3][4][5][6]
             * [-][-][-][-][-][-][-][5]
             * [-][-][-][-][-][-][-][4]
             * [-][-][-][-][-][-][-][3]
             * [Y][-][-][-][-][-][-][2]
             * [Y][-][-][-][-][-][-][1]
             * [R][Y][R][R][R][Y][-][0]
             */
            game.getCurrentBoard().drawBoard();
            assertEquals(5, move.getColIndex());
            assertEquals(0, move.getRowIndex());

        }  catch (MoveException e) {
            e.printStackTrace();
        }
    }
}
