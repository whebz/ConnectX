package forzaquattro.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import forzaquattro.controller.Sound.Song;
import forzaquattro.model.BoardState;
import forzaquattro.model.ComputerPlayer;
import forzaquattro.model.Game;
import forzaquattro.model.GameImpl;
import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;
import forzaquattro.model.Move;
import forzaquattro.model.PieceColor;
import forzaquattro.utils.MoveException;
import forzaquattro.utils.Pair;
import forzaquattro.utils.Utility;
import forzaquattro.view.MenuManager;
import forzaquattro.view.View;

/**
 * Implements the Controller interface.
 * The Controller of the application takes care of the communication between view and model.
 * Uses pattern Singleton.
 */
public final class ControllerImpl implements Controller {

    private static final ControllerImpl SINGLETON = new ControllerImpl();
    private static final int EASY_INT = 3;
    private static final int MEDIUM_INT = 4;
    private static final int HARD_INT = 6;
    private final Map<Difficult, Integer> difficultMap;
    private Game model;
    private Collection<View> views;
    private PieceColor currentPlayer;
    private Difficult currentDifficult;
    private GameType currentGameType;
    private GameVariant currentGameVariant;
    private StatsManagerImpl sm;
    private MenuManager mm;
    private final Sound sound;
    private ComputerPlayer computerPlayer;

    /**
     * The constructor of the Controller.
     * Initializes the fields.
     */
    private ControllerImpl() {
        this.views = Collections.synchronizedCollection(new LinkedList<>());
        this.currentPlayer = PieceColor.RED; // Player1 starts the game and is always red
        this.sm = new StatsManagerImpl();
        this.sound = new SoundImpl();
        this.difficultMap = new HashMap<>();
        this.difficultMap.put(Difficult.EASY, EASY_INT);
        this.difficultMap.put(Difficult.MEDIUM, MEDIUM_INT);
        this.difficultMap.put(Difficult.HARD, HARD_INT);
    }

    /**
     * Method to get the unique ControllerImpl instance of the application.
     * @return the unique instance of the controller
     */
    public static ControllerImpl getControllerImpl() {
        return SINGLETON;
    }

    @Override
    public void setSettings(final GameVariant gv, final GameType gt, final Difficult d) {
        this.currentDifficult = d;
        this.currentGameType = gt;
        this.currentGameVariant = gv;

        this.model = new GameImpl(gt, gv);
        int[] size = this.model.boardSize();
        this.views.forEach(v -> v.drawTable(size[1], size[0], gv.toString()));

        if (gt.equals(GameType.PvsC)) {
            this.computerPlayer = new ComputerPlayer(PieceColor.YELLOW, this.difficultMap.get(d));
        }
    }

    @Override
    public PieceColor getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public MenuManager getMenuManager() {
        for (View v: this.views) {
            this.mm = v.getMenu();
            if (this.mm != null) {
                break;
            }
        }
        return this.mm;
    }

    @Override
    public void newMove(final int row, final int col) {
        try {
            Move newMove = this.model.addpiece(col, this.getCurrentPlayer()); // exception may be thrown here
            int newRow = newMove.getRowIndex();
            int newCol = newMove.getColIndex();
            this.views.forEach(e -> e.drawMove(newCol, newRow));
            this.currentPlayer = Utility.getOpponentPiece(this.currentPlayer);
            this.sound.play(this.currentPlayer.equals(PieceColor.RED) ? Song.MOVE1 : Song.MOVE2);

            if (!this.getMatchResult().equals(BoardState.STILL_PLAYING)) { // match is over
                BoardState b = this.getMatchResult();
                System.out.println("Game finished. Result: " + b); // log
                this.sound.play(Song.FINISH);
                this.currentPlayer = PieceColor.RED; // reset first player to RED

                SwingUtilities.invokeLater(() -> {
                    this.sm.writeStats(this.sm.getStats());
                });
                switch (b) {
                    case REDWON:
                            this.sm.getStats().addWon(this.currentGameVariant, this.currentGameType,  this.currentDifficult);
                            this.showWinPositions();
                            this.views.forEach(e -> e.matchFinished(false));
                            break;
                    case YELLOWWON:
                            this.sm.getStats().addLost(this.currentGameVariant, this.currentGameType,  this.currentDifficult);
                            this.showWinPositions();
                            this.views.forEach(e -> e.matchFinished(false));
                            break;
                    default:
                            this.sm.getStats().addDraw(this.currentGameVariant, this.currentGameType,  this.currentDifficult);
                            this.views.forEach(e -> e.matchFinished(true));
                }
            }
            if (this.currentGameType.equals(GameType.PvsC) && this.currentPlayer.equals(PieceColor.YELLOW)) {  // tocca al computerplayer
                this.views.forEach(e -> e.computerTurn());
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Move m = ControllerImpl.this.computerPlayer.generatemove(ControllerImpl.this.model);
                        ControllerImpl.this.newMove(m.getRowIndex(), m.getColIndex());
                    }
                });
                t.start();
            }

        } catch (MoveException me) {
            this.views.forEach(e -> e.showWrongMoveMessage());
        }
    }

    @Override
    public void registerView(final View v) {
       views.add(v);
    }

    @Override
    public StatsManagerImpl getStatsManager() {
        return this.sm;
    }

    @Override
    public void notifyUndoMove() {
        Move m = this.model.getCurrentBoard().getLastMove();
        if (m != null) {
            this.currentPlayer = Utility.getOpponentPiece(this.currentPlayer);
            this.model.getCurrentBoard().undoLastMove();
            Move tmp = this.model.getCurrentBoard().getLastMoveByColor(this.currentPlayer);
            int lastCol;
            if (tmp != null) {
                lastCol = tmp.getColIndex() + 1;
            } else {
                lastCol = 0;
            }
            this.views.forEach(e -> e.undoLastMove(m.getRowIndex(), m.getColIndex(), lastCol));

            if (this.currentGameType.equals(GameType.PvsC)) {
                Move m2 = this.model.getCurrentBoard().getLastMove();
                if (m2 != null) {
                    this.currentPlayer = Utility.getOpponentPiece(this.currentPlayer);
                    this.model.getCurrentBoard().undoLastMove();
                    Move tmp2 = this.model.getCurrentBoard().getLastMoveByColor(this.currentPlayer);
                    int lastCol2;
                    if (tmp2 != null) {
                        lastCol2 = tmp2.getColIndex() + 1;
                    } else {
                        lastCol2 = 0;
                    }
                    this.views.forEach(e -> e.undoLastMove(m2.getRowIndex(), m2.getColIndex(), lastCol2));
                }
            }
        }
    }

    private BoardState getMatchResult() {
        return this.model.getCurrentGameState();
    }

    private void showWinPositions() {
        List<Pair<Integer, Integer>> tmp = new ArrayList<>();
        for (Move m: this.model.winningPiecesPositions()) {
            tmp.add(new Pair<>(m.getRowIndex(), m.getColIndex()));
        }
        this.views.forEach(e -> e.showWinningPieces(tmp));
    }
}
