package forzaquattro.test;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;

import forzaquattro.model.Board;
import forzaquattro.model.BoardImpl;
import forzaquattro.model.BoardState;
import forzaquattro.model.GameVariant;
import forzaquattro.model.Move;
import forzaquattro.model.PieceColor;
import forzaquattro.utils.MoveException;
import forzaquattro.utils.Utility;

/**
 * .
 *
 */
public class BoardObject {
    /**
     * test for horizontal piece winning positions.
     */
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void horizontalcheck() {
        Board b = new BoardImpl(GameVariant.Forza4);
        Move mov;
        /* 
         * - - - - - - - 
         * - - - - - - - 
         * - - - - - - -
         * Y - - - - - -
         * Y Y - - - - -
         * R Y R R R R -
         */
        System.out.println("horizontalTest");
        try {
            mov = b.droppiece(0, PieceColor.RED);
            mov = b.droppiece(0, PieceColor.YELLOW);
            mov = b.droppiece(2, PieceColor.RED);
            // piece board indexes.
            mov = b.droppiece(1, PieceColor.YELLOW);
            assertEquals(0, mov.getRowIndex());
            assertEquals(1, mov.getColIndex());

            mov = b.droppiece(3, PieceColor.RED);
            mov = b.droppiece(0, PieceColor.YELLOW);
            mov = b.droppiece(4, PieceColor.RED);
            mov = b.droppiece(1, PieceColor.YELLOW);
            mov = b.droppiece(5, PieceColor.RED);
        } catch (MoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // horizontal check state test
        assertEquals(BoardState.REDWON, b.getState(0));
        b.drawBoard();
        b.reset();
    }

    /**
     * use for testing vertical winning pieces.
     */
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void verticalcheck() {
        Board b = new BoardImpl(GameVariant.Forza4);
        assertEquals(true, b.isEmpty());
        /*
         * - - - - - - - 
         * Y - - - - - - 
         * Y - - - - - -
         * Y - - - R - -
         * Y Y - - R - -
         * R Y R R R - -
         */
        System.out.println("verticalTest");
        try {
            b.droppiece(0, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(2, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(3, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(4, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(4, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(4, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
        } catch (MoveException e) {
            e.printStackTrace();
        }

        // horizontal check state test
        assertEquals(BoardState.YELLOWWON, b.getState(0));
        b.drawBoard();
        b.reset();
    }

    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void diagonalcheckBackward () {
        Board b = new BoardImpl(GameVariant.Forza4);
        /*
         * - - - - - - - 
         * - - - - - - - 
         * Y - - - - - -
         * R Y - - - - -
         * R Y Y - - - -
         * R R R Y - - -
         */
        System.out.println("diagonalTestBackward");
        try {
            b.droppiece(0, PieceColor.RED);
            b.droppiece(3, PieceColor.YELLOW);
            b.droppiece(2, PieceColor.RED);
            b.droppiece(2, PieceColor.YELLOW);
            b.droppiece(1, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(0, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(0, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
        } catch (MoveException e) {
            e.printStackTrace();
        }
        b.drawBoard();
        assertEquals(BoardState.YELLOWWON, b.getState(0));
        
    }
    
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void diagonalcheckForward () {
        Board b = new BoardImpl(GameVariant.Forza4);
        /*
         * - - - - - - - 
         * - - - - - - - 
         * - - - R - - -
         * - R R Y - - -
         * - R Y R - - -
         * R Y Y Y - - -
         */
        System.out.println("diagonalTestForward");
        try {
            b.droppiece(0, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(1, PieceColor.RED);
            b.droppiece(2, PieceColor.YELLOW);
            b.droppiece(1, PieceColor.RED);
            b.droppiece(2, PieceColor.YELLOW);
            b.droppiece(2, PieceColor.RED);
            b.droppiece(3, PieceColor.YELLOW);
            b.droppiece(3, PieceColor.RED);
            b.droppiece(3, PieceColor.YELLOW);
            b.droppiece(3, PieceColor.RED);
        } catch (MoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(BoardState.REDWON, b.getState(0));
        b.drawBoard();
    }
    
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void diagonalcheck2Forward () {
        Board b = new BoardImpl(GameVariant.Forza4);
        /*
         * - - - - - - - 
         * - - - R - Y - 
         * - - - Y Y Y - 
         * - - - Y Y R - 
         * - - Y Y R R R 
         * Y R R R Y R R 
         */
        System.out.println("diagonalcheck2Forward");
        try {
            b.droppiece(3, PieceColor.RED);    // 1
            b.droppiece(3, PieceColor.YELLOW); // 2
            b.droppiece(5, PieceColor.RED);    // 3
            b.droppiece(4, PieceColor.YELLOW); // 4
            b.droppiece(4, PieceColor.RED);    // 5
            b.droppiece(3, PieceColor.YELLOW); // 6
            b.droppiece(5, PieceColor.RED);    // 7
            b.droppiece(3, PieceColor.YELLOW); // 8
            b.droppiece(3, PieceColor.RED);    // 9
            b.droppiece(4, PieceColor.YELLOW); // 10
            b.droppiece(5, PieceColor.RED);    // 11
            b.droppiece(5, PieceColor.YELLOW); // 12
            b.droppiece(1, PieceColor.RED);    // 13
            b.droppiece(4, PieceColor.YELLOW); // 14
            b.droppiece(2, PieceColor.RED);    // 15
            b.droppiece(0, PieceColor.YELLOW); // 16
            b.droppiece(6, PieceColor.RED);    // 17
            b.droppiece(2, PieceColor.YELLOW); // 18
            b.droppiece(6, PieceColor.RED);    // 19
            b.droppiece(5, PieceColor.YELLOW); // 20
            
        } catch (MoveException e) {
            System.out.println(e.getMessage());
        }
        b.drawBoard();
        assertEquals(BoardState.YELLOWWON, b.getState(0));
        
    }
    
    @org.junit.Test
    public void diagonalC5TestForward () {
        Board b = new BoardImpl(GameVariant.Forza5);
        /*
         * - - - - - - - - -
         * - - - - - - - - -
         * - - - - - - - - R
         * - - - - - - - R R
         * - - - - Y Y R R Y
         * - - - - Y R R R R
         * Y - - - R Y Y Y Y
         * 0 1 2 3 4 5 6 7 8
         */
        System.out.println("diagonalTestForward");
        try {
            b.droppiece(4, PieceColor.RED);
            b.droppiece(5, PieceColor.YELLOW);
            b.droppiece(5, PieceColor.RED);
            b.droppiece(6, PieceColor.YELLOW);
            b.droppiece(6, PieceColor.RED);
            b.droppiece(7, PieceColor.YELLOW);
            b.droppiece(7, PieceColor.RED);
            b.droppiece(8, PieceColor.YELLOW);
            b.droppiece(8, PieceColor.RED);
            b.droppiece(4, PieceColor.YELLOW);
            
            b.droppiece(7, PieceColor.RED);
            b.droppiece(4, PieceColor.YELLOW);
            b.droppiece(7, PieceColor.RED);
            b.droppiece(8, PieceColor.YELLOW);
            
            b.droppiece(8, PieceColor.RED);
            b.droppiece(5, PieceColor.YELLOW);
            b.droppiece(8, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(6, PieceColor.RED);
        } catch (MoveException e) {
            e.printStackTrace();
        }
        b.drawBoard();
        assertEquals(BoardState.REDWON, b.getState(0));
    }
    
    
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @org.junit.Test
    public void exceptionTests() {
        Board b = new BoardImpl(GameVariant.Forza4);
        PieceColor p = PieceColor.RED;

        try {
            b.droppiece(-1, p);
        } catch (MoveException e) {
            assertEquals("Colonna non valida! Impostare colonna con valore tra 0 e " + 6, e.getMessage());
        }
        try {
            b.droppiece(8, p);
        } catch (MoveException e) {
            assertEquals("Colonna non valida! Impostare colonna con valore tra 0 e " + 6, e.getMessage());
        }
        try {
            b.droppiece(0, p);
            b.droppiece(0, p);
        } catch (MoveException e) {
            assertEquals("Un giocatore non puo' giocare 2 volte di seguito!", e.getMessage());
        }

        // reset test
        b.reset();
        assertEquals(b.getLastMove(), null);
        assertEquals(b.moveCount(), 0);
        // test if board columns are resetted.
        assertEquals(true, b.isEmpty());
        
        try {
            for (int i = 0; i < b.size()[0]; i++) {
                for (int j = 0; j < b.size()[1]; j++) {
                    b.droppiece(j, p);
                    p = Utility.getOpponentPiece(p);
                }
            }
            assertEquals(true, b.isFull());
            b.droppiece(0, p);
        } catch (MoveException e) {
            assertEquals("Riga/Colonna piena!", e.getMessage());
        }

        b.drawBoard();
        
    }
    
   
    
    @org.junit.Test
    public void cloningTest() {
        Board b = new BoardImpl(GameVariant.Viviani);
        Board bcopy = (Board) b.clone();
        Board bcopy1;
        Move m = new Move(0, 0, 1);
        assertEquals(null, b.getLastMove());
        try {
            bcopy.droppiece(0, PieceColor.RED);
        }  catch (MoveException e) {
            e.printStackTrace();
        }
        assertEquals(null, b.getLastMove());
        assertEquals(m.getRowIndex(), bcopy.getLastMove().getRowIndex());
        assertEquals(m.getColIndex(), bcopy.getLastMove().getColIndex());
        assertEquals(0, b.moveCount());
        assertEquals(1, bcopy.moveCount());
        
        bcopy1 = (Board) bcopy.clone();
        assertEquals(bcopy.moveCount(), bcopy.moveCount());
        assertEquals(bcopy1.getLastMove().getRowIndex(), bcopy.getLastMove().getRowIndex());
        assertEquals(bcopy1.getLastMove().getColIndex(), bcopy.getLastMove().getColIndex());
    }
    
    @org.junit.Test
    public void c5Test () {
        Board b = new BoardImpl(GameVariant.Forza5);
        /*
         * - - - - - - - - -
         * - - - - - - - - -
         * Y - - - - - - - -
         * Y Y - - - - - - -
         * R R Y - - - - - -
         * Y R Y Y - - - - -
         * R R R R Y R - - -
         * 0 1 2 3 4 5 6 7 8
         */
        System.out.println("diagonalTestBackward");
        try {
            b.droppiece(1, PieceColor.RED);
            b.droppiece(4, PieceColor.YELLOW);
            b.droppiece(3, PieceColor.RED);
            b.droppiece(3, PieceColor.YELLOW);
            b.droppiece(2, PieceColor.RED);
            b.droppiece(2, PieceColor.YELLOW);
            b.droppiece(1, PieceColor.RED);
            b.droppiece(2, PieceColor.YELLOW);
            b.droppiece(1, PieceColor.RED);
            b.droppiece(1, PieceColor.YELLOW);
            b.droppiece(0, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(0, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
            b.droppiece(5, PieceColor.RED);
            b.droppiece(0, PieceColor.YELLOW);
        } catch (MoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(BoardState.YELLOWWON, b.getState(0));
        b.drawBoard();
    }
    
    @org.junit.Test
    public void otherTests() {
        Board b = new BoardImpl(GameVariant.Forza5);
        assertEquals(7, b.size()[0]);
        assertEquals(9, b.size()[1]);
        
        b = new BoardImpl(GameVariant.Forza4);
        assertEquals(6, b.size()[0]);
        assertEquals(7, b.size()[1]);
        
        b = new BoardImpl(GameVariant.Viviani);
        assertEquals(8, b.size()[0]);
        assertEquals(8, b.size()[1]);
        
        b = new BoardImpl(GameVariant.Attenta);
        assertEquals(6, b.size()[0]);
        assertEquals(7, b.size()[1]);
    }
}
