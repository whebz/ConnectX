package forzaquattro.model;

import java.util.ArrayList;
import java.util.List;
import forzaquattro.utils.MoveException;
import forzaquattro.utils.Utility;

/**
 * 
 */
public class ComputerPlayer implements Player {

    private PieceColor piece;
    private PieceColor opPiece;
    private int aiLevel;
    private Board board;

    /**
     * 
     * @param p - Computer Piece.
     * @param compLevel - define how strong the computer level is.
     */
    public ComputerPlayer(final PieceColor p, final int compLevel) {
        this.piece = p;
        this.aiLevel = compLevel;
        this.opPiece = Utility.getOpponentPiece(p);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.COMPUTER;
    }

    @Override
    public PieceColor getPlayerColorPiece() {
        return this.piece;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Computer [c=" + this.piece + "]";
    }

    @Override
    public String playerName() {
        return " Computer (AI) ";
    }

    private Boolean finished(final Board node) {
        if (node.getState(0) != BoardState.STILL_PLAYING) {
            return true;
        }
        return false;
    }

    @Override
    public Move generatemove(final Game current) {
        int bestScore = Integer.MIN_VALUE;
        int bestColumn = 0;
        int bestRow = 0;
        int bestDepth = this.aiLevel;
        List<Move> moves;
        this.board = (Board) current.getCurrentBoard().clone(); // copy board
        moves = this.board.getPossibleMoves();
        for (Move mv : moves) {
            try {
                this.board.droppiece(mv.getRowIndex(), mv.getColIndex(), this.piece);
                int[] moveval = this.minimize(1, Integer.MIN_VALUE, Integer.MIN_VALUE);
                if (moveval[1] >= bestScore) {
                    bestScore = moveval[1];
                    bestColumn = mv.getColIndex();
                    bestRow = mv.getRowIndex();
                    bestDepth = moveval[2];
                    if (moveval[2] == 1 || moveval[2] < bestDepth) {
                        break;
                    }
                }
                this.board.undoLastMove();
            } catch (MoveException e) {
                // ignore and continue
            }
        }
        return new Move(bestRow, bestColumn, Utility.getColorPieceEquivalentInInt(this.piece));
    }

    private int[] maximize(final int depth, final int alpha, final int beta) {
        int score = this.score(this.board);
        int a = alpha;
        int[] max = { -1, alpha, this.aiLevel };
        List<Move> moves = this.board.getPossibleMoves();

        if (depth == this.aiLevel || finished(this.board)) {
            /* for debug purpose only
            this.board.drawBoard();
            System.out.println("this board score: " + score);
            System.out.println("depth: " + depth);
            System.out.println("state: " + finished(this.board));
            */
            return new int [] {-1, score, depth };
        }

        for (Move mv : moves) {
            try {
                this.board.droppiece(mv.getRowIndex(), mv.getColIndex(), this.piece);
                int[] next = minimize(depth + 1, a, beta);
                if (max[0] == -1 || next[1] > max[1]) {
                    max[0] = mv.getColIndex();
                    max[1] = next[1];
                    max[2] = next[2];
                    a = next[1];
                }
                this.board.undoLastMove();
                if (a > beta) {
                    return max;
                }
            } catch (MoveException e) {
                // do nothing
            }
        }
        return max;
    }

    private int[] minimize(final int depth, final int alpha, final int beta) {
        int score = this.score(this.board);
        int[] min = { -1, beta, this.aiLevel };
        int b = beta;
        List<Move> moves = this.board.getPossibleMoves();

        if (depth == this.aiLevel || finished(this.board)) {
            /* debug purpose only
            this.board.drawBoard();
            System.out.println("this board score [minimize]: " + score);
            System.out.println("depth: " + depth);
            System.out.println("state: " + finished(this.board));
            */
            return new int [] {-1, score, depth };
        }

        for (Move mv : moves) {
            try {
                this.board.droppiece(mv.getRowIndex(), mv.getColIndex(), this.opPiece);
                int[] next = maximize(depth + 1, alpha, b);
                if (min[0] == -1 || next[1] < min[1]) {
                    min[0] = mv.getColIndex();
                    min[1] = next[1];
                    b = next[1];
                }
                this.board.undoLastMove();
                if (alpha >= b) {
                    return min;
                }
            } catch (MoveException e) {
                // do nothing
            }
        }
        return min;
    }

    private int scorePosition(final Board node, final int row, final int col, final int deltaR, final int deltaC) {
        int playerPoints = 0;
        int opponentPoints = 0;
        int r = row;
        int c = col;
        int p = Utility.getColorPieceEquivalentInInt(this.piece);
        int o = p == 1 ? -1 : 1;
        List<Integer> playerWinningPieces = new ArrayList<Integer>();
        List<Integer> opponentWinningPieces = new ArrayList<Integer>();
        int maxPoints = node.numWinPieces();

        for (int i = 0; i < maxPoints; i++) {
            int v = node.boardCellValue(r, c);
            if (v == p) {
                playerPoints++;
                playerWinningPieces.add(v);
            } else if (v == o) {
                opponentPoints++;
                opponentWinningPieces.add(v);
            }
            r += deltaR;
            c += deltaC;
        }
        if (playerPoints == maxPoints) {
          return Integer.MAX_VALUE;
        } else if (opponentPoints == maxPoints) {
            return Integer.MIN_VALUE;
        }
        return playerPoints;
    }

    private int score(final Board node) {
        int totalscore = 0;
        int score;
        int[] boardsize = node.size();
        int maxRow = boardsize[0];
        int maxCol = boardsize[1];
        int mPieces = node.numWinPieces() - 1;

        // vertical check for possible winning combination
        for (int row = 0; row < (maxRow - mPieces); row++) {
            for (int column = 0; column < maxCol; column++) {
                score = this.scorePosition(node, row, column, 1, 0);
                if (Math.abs(score) == Integer.MAX_VALUE) {
                    return score;
                }
                totalscore += score;
            }
        }

        // horizontal check for possible winning combination
        for (int row = 0; row < maxRow; row++) {
            for (int column = 0; column < (maxCol - mPieces); column++) {
                score = this.scorePosition(node, row, column, 0, 1);
                if (Math.abs(score) == Integer.MAX_VALUE) {
                    return score;
                }
                totalscore += score;
            }
        }

        // forwar diagonal check for possible winning combination
        for (int row = 0; row < (maxRow - mPieces); row++) {
            for (int column = 0; column < (maxCol - mPieces); column++) {
                score = this.scorePosition(node, row, column, 1, 1);
                if (Math.abs(score) == Integer.MAX_VALUE) {
                    return score;
                }
                totalscore += score;
            }
        }

        // backward diagonal check for possible winning combination
        for (int row = mPieces; row < maxRow; row++) {
            for (int column = 0; column <= (maxCol - (mPieces + 1)); column++) {
                score = this.scorePosition(node, row, column, -1, 1);
                if (Math.abs(score) == Integer.MAX_VALUE) {
                    return score;
                }
                totalscore += score;
            }
        }
        return totalscore;
    }

}
