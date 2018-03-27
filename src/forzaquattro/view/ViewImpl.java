package forzaquattro.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import forzaquattro.controller.Controller;
import forzaquattro.model.PieceColor;
import forzaquattro.utils.Pair;

/**
 * Implements the View interface.
 * 
 * The View is used to show the GUI to the user.
 *
 */
public class ViewImpl implements View {
    private final Controller controller;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final double WIDTH_PERC = 0.5;
    private static final double HEIGHT_PERC = 0.8;
    private PieceColor possibleWinner;
    private static final String PLAYER1 = "Player1";
    private static final String PLAYER2 = "Player2";
    private int nOfTotMove; //number of totMove 
    private int lastRedMove = 0; //last redMove
    private int lastYellowMove = 0; // last yellowMove
    private boolean playerTurn = true;

    private MenuManager menu;
    private JFrame frame;
    private JLabel[][] slots;
    private JTextField playerTurnField;
    private JLabel whosNextField;
    private JTextField totMoveDoneField;
    private JTextField redMoveDoneField;
    private JTextField yellowMoveDoneField;
    private JButton undoLastMove;
    private JOptionPane option;
    private JDialog dialog;

    //The image URL and declarations
    private final URL winnerURL = ViewImpl.class.getResource("/images/winner.png");
    private final URL redURL = ViewImpl.class.getResource("/images/red.png");
    private final URL yellowURL = ViewImpl.class.getResource("/images/yellow.png");
    private final ImageIcon winner = new ImageIcon(winnerURL);
    private final ImageIcon red = new ImageIcon(redURL);
    private final ImageIcon yellow = new ImageIcon(yellowURL);

    /**
     * This is only to get the controller reference.
     * @param ctrl
     *          The controller to which communicate the settings of the game.
     */
    public ViewImpl(final Controller ctrl) {
        this.controller = ctrl;
        this.setMenu((new MenuManagerImpl(ctrl)));
    }

    @Override
    public void drawTable(final int col, final int row, final String name) {
        this.frame = new JFrame(name);
        GridLayout grid = new GridLayout(row, col);
        final JPanel panel = new JPanel();
        panel.setLayout(grid);
        final JPanel statPanel = new JPanel();
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.LINE_AXIS));
        playerTurnField = new JTextField("Tocca al giocatore: ");
        playerTurnField.setEditable(false);
        statPanel.add(playerTurnField); //add the textfield to the panel
        if (this.controller.getCurrentPlayer() == PieceColor.RED) { //check who start
            whosNextField = new JLabel("" + PLAYER1);
            whosNextField.setOpaque(true);
            whosNextField.setBackground(Color.RED);
        } else {
            whosNextField = new JLabel("" + PLAYER2);
            whosNextField.setOpaque(true);
            whosNextField.setBackground(Color.YELLOW);
        }
        statPanel.add(whosNextField);
        final JPanel numberOfMovePanel = new JPanel();
        numberOfMovePanel.setLayout(new BoxLayout(numberOfMovePanel, BoxLayout.Y_AXIS));
        this.nOfTotMove = 0;
        totMoveDoneField = new JTextField("Numero di mosse eseguite in totale: " + this.nOfTotMove);
        totMoveDoneField.setEditable(false);
        redMoveDoneField = new JTextField("Il giocatore 1 deve ancora fare la prima mossa.");
        redMoveDoneField.setEditable(false);
        yellowMoveDoneField = new JTextField("Il giocatore 2 deve ancora fare la prima mossa.");
        yellowMoveDoneField.setEditable(false);
        undoLastMove = new JButton("Undo the last move");
        undoLastMove.addActionListener(e -> controller.notifyUndoMove());
        numberOfMovePanel.add(redMoveDoneField);
        numberOfMovePanel.add(yellowMoveDoneField);
        numberOfMovePanel.add(totMoveDoneField);
        statPanel.add(numberOfMovePanel);
        statPanel.add(undoLastMove);
        slots = new JLabel[row][col];
        for (int r = row - 1; r >= 0; r--) {
            for (int c = 0; c < col; c++) {
                slots[r][c] = new JLabel();
                slots[r][c].setHorizontalAlignment(SwingConstants.CENTER);
                slots[r][c].setBorder(new LineBorder(Color.black));
                slots[r][c].setOpaque(true);
                slots[r][c].setBackground(Color.WHITE);
                final int choosenCol = c; //the choosenColumn
                final int choosenRow = r; //the choosenRow
                slots[r][c].addMouseListener(new MouseAdapter() { // JLabel mouse listener
                    public void mouseClicked(final MouseEvent e) {
                        if (playerTurn) {
                            System.out.print("\n Colonna scelta: " + choosenCol);
                            controller.newMove(choosenRow, choosenCol); // communicate the newMove to the controller
                        }
                    }
                });
                panel.add(slots[r][c]);
            }
        }
        // settings of the frame
        this.frame.getContentPane().add(panel);
        this.frame.getContentPane().add(statPanel, BorderLayout.SOUTH);
        this.frame.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void drawMove(final int col, final int row) {
        if (this.controller.getCurrentPlayer() == PieceColor.RED) {
            slots[row][col].setIcon(red);
            updateTextField(col);
            this.possibleWinner = this.controller.getCurrentPlayer();
            this.whosNextField.setText("" + PLAYER2);
            this.whosNextField.setBackground(Color.YELLOW);
            this.playerTurn = true;
        } else {
            slots[row][col].setIcon(yellow);
            updateTextField(col);
            this.possibleWinner = this.controller.getCurrentPlayer();
            this.whosNextField.setText("" + PLAYER1);
            this.whosNextField.setBackground(Color.RED);
            this.playerTurn = true;
        }
    }

    @Override
    public void matchFinished(final boolean isDraw) {
        option = new JOptionPane("Partita terminata, iniziare una nuova partita?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (!isDraw) {
            setWhoWon(this.possibleWinner);
        } else {
            dialog = option.createDialog(this.frame, "Game Over - La partita e' finita in parita'");
            this.playerTurnField.setText("La partita è finita in parita'");
            this.whosNextField.setText("");
        }
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.pack(); // set the dialog panel to the minimum size
        dialog.setVisible(true);
        int n = ((Integer) option.getValue());
        if (n == JOptionPane.YES_OPTION) {
            this.frame.dispose(); // close the frame of the ended game
            this.getMenu().showNewGameMenu(); // show the newGameMenu
        } else if (n == JOptionPane.NO_OPTION) {
            System.out.println("Exit Forza4 application..");
            System.exit(0); //close the game
        } 
    }

    private void updateTextField(final int col) {
        if (this.controller.getCurrentPlayer() == PieceColor.RED) {
            this.lastRedMove = col + 1;
            this.redMoveDoneField.setText("L'ultima mossa fatta dal giocatore 1 e' sulla colonna: " + this.lastRedMove);
        } else {
            this.lastYellowMove = col + 1;
            this.yellowMoveDoneField.setText("L'ultima mossa fatta dal giocatore 2 e' sulla colonna: " + this.lastYellowMove);
        }
        this.nOfTotMove += 1;
        this.totMoveDoneField.setText("Numero di mosse eseguite in totale: " + this.nOfTotMove);
    }

    @Override
    public MenuManager getMenu() {
        return this.menu;
    }

    @Override
    public void setMenu(final MenuManager menu) {
        this.menu = menu;
    }

    @Override
    public void showWrongMoveMessage() {
        JOptionPane.showMessageDialog(this.frame, "Mossa non valida. La colonna selezionata potrebbe essere gia' piena.", "Warning Message", JOptionPane.WARNING_MESSAGE);
    }

    private void setWhoWon(final PieceColor color) {
        if (color == PieceColor.RED) {
            dialog = option.createDialog(this.frame, "Game Over - Il vincitore e' il giocatore: " + PLAYER1);
            this.playerTurnField.setText("Il vincitore e' il giocatore: ");
            this.whosNextField.setText("" + PLAYER1);
            this.whosNextField.setBackground(Color.RED);
        } else {
            dialog = option.createDialog(this.frame, "Game Over - Il vincitore e' il giocatore: " + PLAYER2);
            this.playerTurnField.setText("Il vincitore e' il giocatore: ");
            this.whosNextField.setText("" + PLAYER2);
            this.whosNextField.setBackground(Color.YELLOW);
        }
    }

    @Override
    public void computerTurn() {
        this.playerTurn = false;
    }

    @Override
    public void showWinningPieces(final List<Pair<Integer, Integer>> winningSequence) {
            for (Pair<Integer, Integer> p: winningSequence) {
                int row = p.getX();
                int col = p.getY();
                slots[row][col].setIcon(winner);
        }
    }

    @Override
    public void undoLastMove(final int row, final int col, final int lastMove) {
        this.slots[row][col].setIcon(null);
        if (this.controller.getCurrentPlayer() == PieceColor.RED) {
            this.whosNextField.setText("" + PLAYER1);
            this.whosNextField.setBackground(Color.RED);
            if (lastMove == 0) {
                this.redMoveDoneField.setText("Il giocatore 1 deve ancora fare la prima mossa.");
            } else {
                this.redMoveDoneField.setText("L'ultima mossa fatta dal giocatore 1 e' sulla colonna: " + lastMove);
            }
        } else {
            this.whosNextField.setText("" + PLAYER2);
            this.whosNextField.setBackground(Color.YELLOW);
            if (lastMove == 0) {
                this.yellowMoveDoneField.setText("Il giocatore 2 deve ancora fare la prima mossa.");
            } else {
                this.yellowMoveDoneField.setText("L'ultima mossa fatta dal giocatore 2 e' sulla colonna: " + lastMove);
            }
        }
        this.nOfTotMove -= 1;
        this.totMoveDoneField.setText("Numero di mosse eseguite in totale: " + this.nOfTotMove);
    }
}
