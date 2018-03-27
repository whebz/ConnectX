package forzaquattro.model;

import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Iterator;

import forzaquattro.utils.Utility;
import forzaquattro.utils.MoveException;

/**
 * Board Implementation.
 */
public class BoardImpl implements Board {

    private List<List<Integer>> board;
    private int maxRow;
    private int maxCol;
    private List<Move> winPos;
    private GameVariant variant;
    private int winningPoints;
    private MoveCareTaker savedStates;

    /**
     * BoardImpl constructor. 
     * @param game - define what rules to use base on game variant.
     */
    public BoardImpl(final GameVariant game) {
        this.variant = game;
        this.setBoardSize();
    }

    /**
     * set board size base on game variant.
     */
    //CHECKSTYLE:OFF: checkstyle:magicnumber
    private void setBoardSize() {
        this.winningPoints = 4;
        if (this.variant == GameVariant.Viviani) {
            this.maxCol = 8;
            this.maxRow = 8;
        } else if (this.variant == GameVariant.Forza5) {
            this.maxRow = 7;
            this.maxCol = 9;
            this.winningPoints = 5;
        } else {
            this.maxCol = 7;
            this.maxRow = 6;
        }
        this.createBoard();
    }

    /**
     * create board array and setting 0 to each cell.
     */
    private void createBoard() {
        this.board = new ArrayList<List<Integer>>(this.maxRow);
        for (int i = 0; i < this.maxRow; i++) { 
            List<Integer> subList = new ArrayList<Integer>(this.maxCol);
            for (int j = 0; j < this.maxCol; j++) {
                subList.add(0);
            }
            this.board.add(subList);
        }
        savedStates = new MoveCareTaker();
        this.winPos = new ArrayList<Move>();
    }

    @Override
    public BoardState getState(int checkThis) {
        for (int i = 0; i < this.maxRow; i++) {
            for (int j = 0; j < this.maxCol; j++) {
                if (checkThis == 0 || boardCellValue(i, j) == checkThis) {
                    BoardState state = this.checkHorizontal(i, j);
                    if (state != BoardState.STILL_PLAYING) {
                        return state;
                    }
                    state = this.checkVertical(i, j);
                    if (state != BoardState.STILL_PLAYING) {
                        return state;
                    }
                    state = this.checkDiagonal(i, j);
                    if (state != BoardState.STILL_PLAYING) {
                        return state;
                    }
                }
            }
        }
        if (this.isFull()) {
            return BoardState.DRAW;
        }
        return BoardState.STILL_PLAYING; 
    }

    @Override
    public void reset() {
        this.setBoardSize();
    }
    
    @Override
    public Move getLastMoveByColor(PieceColor color) {
        Move move;
        int colorInNum = Utility.getColorPieceEquivalentInInt(color);
        int moveCount = this.moveCount();
        MoveMemento state = this.savedStates.getLastState();
        if (state != null) {
            move = state.getMove();
            if (move.getValue() == colorInNum) {
                return move;
            } else if (moveCount >= 2) {
                return this.savedStates.get(moveCount - 2).getMove();
            }
        }
        return null;
    }

    @Override
    public Move getLastMove() {
        MoveMemento state = this.savedStates.getLastState();
        if (state != null) {
            return state.getMove();
        }        
        return null;
    }

    @Override
    public int moveCount() {
        return this.savedStates.savedStatesCount();
    }

    @Override
    public List<Move> getMovesMade() {
        int i = 0;
        int count = this.savedStates.savedStatesCount();
        List<Move> moves = new ArrayList<Move>();
        while (i < count) {
            moves.add(this.savedStates.get(i).getMove());
        }
        return moves;
    }

    @Override
    public Boolean isFull() {
        return this.maxCol * this.maxRow == this.moveCount();
    }

    /**
     * @return
     */
    private BoardState checkHorizontal(final int row, final int col) {
        BoardState state = BoardState.STILL_PLAYING;
        int j = (this.maxCol - col);
        int c = col;
        int v = this.boardCellValue(row, col);
        int count;
        // from left to right;
        if (j >= this.winningPoints) {
            count = IntStream.range(col, col + this.winningPoints)
                            .map(i -> this.board.get(row).get(i))
                            .sum();
            if (Math.abs(count) == this.winningPoints) {
                // save winning positions.
                for (int ii = 0; ii < this.winningPoints; ii++) {
                    this.winPos.add(new Move(row, c, v));
                    c++;
                }
                PieceColor p = Utility.convertCountToPlayerPiece(count);
                state = (p == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
            }
        }
        return state;
    }

    /**
     * @return
     */
    private BoardState checkVertical(final int row, final int col) {
        BoardState state = BoardState.STILL_PLAYING;
        int j = (this.maxRow - row);
        int count;
        int r = row;
        int v = this.boardCellValue(row, col);
        if (j >= this.winningPoints) {
            count = IntStream.range(row, row + this.winningPoints)
                           .map(i -> this.board.get(i).get(col))
                           .sum();
            if (Math.abs(count) == this.winningPoints) {
                // save winning positions.
                for (int ii = 0; ii < this.winningPoints; ii++) {
                    this.winPos.add(new Move(r, col, v));
                    r++;
                }
                PieceColor p = Utility.convertCountToPlayerPiece(count);
                state = (p == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
            }
        }
        return state;
    }

    /**
     * @return
     */
    private BoardState checkDiagonal(final int row, final int col) {
        this.winPos.clear();
        BoardState state = BoardState.STILL_PLAYING;
        int count = 0;
        int i = 0; // loop counter
        int r = (this.maxRow - this.winningPoints );
        int c = (this.maxCol - this.winningPoints );
        int v = this.boardCellValue(row, col);
        // diagonal forward
        if (row <= r && col <= c) {
            c = col;
            r = row;
            for (i = 0; i < this.winningPoints; i++) {
                this.winPos.add(new Move(r, c, v));
                count += this.board.get(r).get(c);
                c++;
                r++;
            }

            if (Math.abs(count) == this.winningPoints) {
                PieceColor p = Utility.convertCountToPlayerPiece(count);
                state = (p == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
            } else {
                this.winPos.clear();
            }
        }

        if (col >= (this.winningPoints -1) && (row + this.winningPoints -1) < this.maxRow
                        && state == BoardState.STILL_PLAYING) {
            c = col;
            r = row;
            count = 0;
            for (i = 0; i < this.winningPoints; i++) {
                //System.out.println(r + " " + c);
                this.winPos.add(new Move(r, c, v));
                count += this.board.get(r).get(c);
                c--;
                r++;
            }
            if (Math.abs(count) == this.winningPoints) {
                PieceColor p = Utility.convertCountToPlayerPiece(count);
                state = (p == PieceColor.RED ? BoardState.REDWON : BoardState.YELLOWWON);
            } else {
                this.winPos.clear();
            }
        }
        return state;
    }
    
    @Override
    public Boolean isEmpty() {
        Boolean empty = true;
        for (Iterator<List<Integer>> i = this.board.iterator(); i.hasNext();) {
            List<Integer> item = i.next();
            empty = !item.stream().anyMatch(c -> c != 0);
            if (!empty) {
                break;
            }
        }
        return empty;
    }

    @Override
    public void drawBoard() {
        String p;
        Boolean pr = false;
        for (int k = 0; k < this.maxCol; k++) {
            System.out.print("[" + k + "]");
        }
        System.out.println();
        for (int i = this.maxRow - 1; i >= 0; i--) {
            for (int j = 0; j < this.maxCol; j++) {
                p = Utility.intPlayerPieceToString(this.board.get(i).get(j));
                if (this.winPos.size() > 0) {
                    for (int k = 0, l = this.winPos.size(); k < l; k++) {
                        Move pos = this.winPos.get(k);
                        if (pos.getColIndex() == j && pos.getRowIndex() == i) {
                            System.out.print("[W]");
                            pr = true;
                        }
                    }
                }
                if (!pr) {
                    System.out.print("[" + p + "]");
                }
                pr = false;
            }
            System.out.println("[" + i + "]");
        }
    }

    @Override
    public List<Move> winningPositions() {
        return this.winPos;
    }

    @Override
    public Move droppiece(int col, PieceColor piece) throws MoveException {
        String validate = this.validMove(col, piece);
        if (validate.length() > 0) {
            throw new MoveException(validate);
        }

        for (int i = 0; i < this.maxRow; i++) {
            List<Integer> subList = this.board.get(i);
            if (subList.get(col) == 0) {
                int p = Utility.getColorPieceEquivalentInInt(piece);
                Move m = new Move(i, col, p);
                subList.set(col, p);
                this.saveMove(m);                
                return m;
            }
        }
        throw new MoveException("Riga/Colonna piena!");
    }
    
    @Override
    public Move droppiece(int row, int col, PieceColor piece) throws MoveException {
        String validate = this.validMove(col, piece);
        if (validate.length() > 0) {
            throw new MoveException(validate);
        }

        if (row < 0 || row >= this.maxRow) {
            throw new MoveException("Riga non valida! Impostare riga con valore tra 0 e " + (this.maxRow - 1));
        }
        List<Integer> subList = this.board.get(row);
        if (subList.get(col) == 0) {
            int p = Utility.getColorPieceEquivalentInInt(piece);
            Move m = new Move(row, col, p);
            subList.set(col, p);
            this.saveMove(m);                
            return m;
        }
        throw new MoveException("Riga/Colonna piena!");
    }

    private String validMove(final int col, final PieceColor piece) {
        if (this.lastPiece() == piece) {
            return "Un giocatore non puo' giocare 2 volte di seguito!";
        }
        if (col < 0 || col >= this.maxCol) {
            return "Colonna non valida! Impostare colonna con valore tra 0 e " + (this.maxCol - 1);
        }
        return "";
    }
    
    @Override
    public Board clone() {
        BoardImpl clone = new BoardImpl(this.variant);
        // copy board
        for (int i = 0; i < clone.maxRow; i++) {
            for (int j = 0; j < clone.maxCol; j++) {
                clone.board.get(i).set(j, this.boardCellValue(i, j));
            }
        }
        // copy saved states.
        for (int k = 0, l = this.savedStates.savedStatesCount(); k < l; k++) {
            clone.savedStates.add(this.savedStates.get(k));
        }
        // copy winning moves if exists.
        this.winPos.stream().forEach(o -> 
                clone.winPos.add(new Move(o.getRowIndex(), o.getColIndex(), o.getValue())));       
        return clone;
    }
    
    @Override
    public int numWinPieces() {
        return this.winningPoints;
    }
    
    @Override
    public int boardCellValue(final int r, final int c) {
        return this.board.get(r).get(c);
    }

    @Override
    public void undoLastMove() {
        MoveMemento m = this.savedStates.getLastState();
        if (m != null) {
            Move move = m.getMove();
            this.board.get(move.getRowIndex()).set(move.getColIndex(), 0);
            this.savedStates.delete(m);
        }
    }
    
    private PieceColor lastPiece() {
        Move move = this.getLastMove();
        if (move != null) { 
            return Utility.convertCountToPlayerPiece(move.getValue());
        }
        return null;
    }

    /**
     * save MoveMemento state.
     */
    private void saveMove(final Move m) {
        this.savedStates.add(new MoveMemento(m));
    }

    @Override
    public int[] size() {
        return new int [] {this.maxRow, this.maxCol};
    }
    @Override
    public List<Move> getPossibleMoves() {
        //if (this.variant != GameVariant.Attenta) {
            return this.possibleMovesStandardVariant();
        //} else {
            //return this.possibleMovesGoMokuVariant();
        //}
    }
    
    private List<Move> possibleMovesStandardVariant() {
        List<Move> moves = new ArrayList<Move>();
        for (int c = 0; c < this.maxCol; c++) {
            for (int r = 0; r < this.maxRow; r++) {
                if (this.board.get(r).get(c) == 0){
                    moves.add(new Move(r, c, 0));
                    break;
                }
            }
        }
        return moves;
    }
    
    /* remove for now. will be used if gomoku variant will be added.
    private List<Move> possibleMovesGoMokuVariant() {
        List<Move> moves = new ArrayList<Move>(); 
        for (int r = 0; r < this.maxRow; r++) {
            for (int c = 0; c < this.maxCol; c++) {
                if (this.board.get(r).get(c) == 0){
                    moves.add(new Move(r, c, 0));
                }
            }
        }
        return moves;
    }
    */
}
