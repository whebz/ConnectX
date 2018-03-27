package forzaquattro.test;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;

import forzaquattro.model.BoardState;
import forzaquattro.model.ComputerPlayer;
import forzaquattro.model.Game;
import forzaquattro.model.GameImpl;
import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;
import forzaquattro.model.Move;
import forzaquattro.model.Player;
import forzaquattro.model.PieceColor;
import forzaquattro.utils.MoveException;

public class AIImplementation {
    
    @org.junit.Test
    public void testwin() {
        Game game = new GameImpl(GameType.PvsC, GameVariant.Forza4);
        Player c1 = new ComputerPlayer(PieceColor.YELLOW, 6);
        Move m = null;
        try {
            game.addpiece(0, PieceColor.RED);
            game.addpiece(3, c1.getPlayerColorPiece());
            game.addpiece(0, PieceColor.RED);
            game.addpiece(4, c1.getPlayerColorPiece());
            game.addpiece(1, PieceColor.RED);
            game.addpiece(5, c1.getPlayerColorPiece());
            game.addpiece(0, PieceColor.RED);
            m = c1.generatemove(game);
            System.out.println(m.getColIndex());
            assertEquals(2, m.getColIndex());
            game.addpiece(m.getColIndex(), c1.getPlayerColorPiece());
        } catch (MoveException e) {
            e.printStackTrace();
        }
        assertEquals(BoardState.YELLOWWON, game.getCurrentGameState());
    }
    @org.junit.Test
    public void teststopopfromwinning() {
        Game game = new GameImpl(GameType.PvsC, GameVariant.Forza4);
        Player c1 = new ComputerPlayer(PieceColor.YELLOW, 6);
        Move m = null;
        try {
            game.addpiece(0, PieceColor.RED);
            game.addpiece(3, c1.getPlayerColorPiece());
            game.addpiece(0, PieceColor.RED);
            game.addpiece(4, c1.getPlayerColorPiece());
            game.addpiece(1, PieceColor.RED);
            game.addpiece(1, c1.getPlayerColorPiece());
            game.addpiece(0, PieceColor.RED);
            m = c1.generatemove(game);
            System.out.println(m.getColIndex());
            assertEquals(0, m.getColIndex());
            game.addpiece(m.getColIndex(), c1.getPlayerColorPiece());
        } catch (MoveException e) {
            e.printStackTrace();
        }
        assertEquals(BoardState.STILL_PLAYING, game.getCurrentGameState());
    }
}
