package forzaquattro.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import forzaquattro.controller.Controller;
import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;


/**
 * This class is the implementation of the new game menu making user able to select game options.
 *
 */
public class NewGameMenu implements Menu {

    private static final double NEWGAME_PERC_WIDTH = 0.3;
    private static final double NEWGAME_PERC_HEIGHT = 0.3;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Optional<JFrame> newGameFrame;
    private JPanel difficultPanel;
    private Controller controller;
    private GameVariant selectedGameVariant;
    private GameType selectedGameType;
    private Controller.Difficult selectedDifficult;

    /**
     * Constructor for the New Game menu.
     * Initializes the frame.
     * @param c
     *          reference to the controller to be able to start a new game
     */
    public NewGameMenu(final Controller c) {
        this.controller = c;
        System.out.println("Creating New Game menu...");
        this.newGameFrame = Optional.of(new JFrame("Forza4-5  -  New Game"));
        this.newGameFrame.get().setSize((int) (screenSize.getWidth() * NEWGAME_PERC_WIDTH), 
                        (int) (screenSize.getHeight() * NEWGAME_PERC_HEIGHT));
        this.newGameFrame.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel mainPanel = new JPanel();
        final JPanel variantPanel = new JPanel();   // Forza4 or Forza5 or Viviani or Attenta
        final JPanel vsPanel = new JPanel();        // PvsP or PvsC
        this.difficultPanel = new JPanel(); // Easy, Medium or Hard

        final JButton startButton = new JButton("Start Game");
        final JButton backButton = new JButton("Back");

        final JRadioButton forza4Button = new JRadioButton("Forza 4");
        forza4Button.setName(GameVariant.Forza4.toString());

        final JRadioButton forza5Button = new JRadioButton("Forza 5");
        forza5Button.setName(GameVariant.Forza5.toString());

        final JRadioButton forza4VivianiButton = new JRadioButton("Viviani");
        forza4VivianiButton.setName(GameVariant.Viviani.toString());

        final JRadioButton forza4AttentaButton = new JRadioButton("Attenta");
        forza4AttentaButton.setName(GameVariant.Attenta.toString());

        final JRadioButton playerVsComputer = new JRadioButton("Player vs Computer");
        playerVsComputer.setName(GameType.PvsC.toString());

        final JRadioButton playerVsPlayer = new JRadioButton("Player vs Player");
        playerVsComputer.setName(GameType.PvsP.toString());

        final JRadioButton easyButton = new JRadioButton("EASY");
        easyButton.setName(Controller.Difficult.EASY.toString());

        final JRadioButton mediumButton = new JRadioButton("MEDIUM");
        mediumButton.setName(Controller.Difficult.MEDIUM.toString());

        final JRadioButton hardButton = new JRadioButton("HARD");
        hardButton.setName(Controller.Difficult.HARD.toString());

        final ButtonGroup bgVariant = new ButtonGroup();
        final ButtonGroup bgVs = new ButtonGroup();
        final ButtonGroup bgDiffucult = new ButtonGroup();

        bgVariant.add(forza4Button);
        bgVariant.add(forza5Button);
        bgVariant.add(forza4VivianiButton);
        bgVariant.add(forza4AttentaButton);

        bgVs.add(playerVsPlayer);
        bgVs.add(playerVsComputer);

        bgDiffucult.add(easyButton);
        bgDiffucult.add(mediumButton);
        bgDiffucult.add(hardButton);

        mainPanel.setBorder(new TitledBorder("Game Options")); 
        mainPanel.setLayout(new BorderLayout());

        variantPanel.setBorder(new TitledBorder("Variant"));
        variantPanel.setLayout(new GridBagLayout());
        vsPanel.setBorder(new TitledBorder("VS"));
        vsPanel.setLayout(new GridBagLayout());
        difficultPanel.setBorder(new TitledBorder("Difficult"));
        difficultPanel.setLayout(new GridBagLayout());

        final GridBagConstraints cnst = new GridBagConstraints(); 
        cnst.gridy = 0; // row1
        cnst.insets = new Insets(3, 3, 3, 3);
        cnst.fill = GridBagConstraints.HORIZONTAL; 
        variantPanel.add(forza4Button, cnst); 
        vsPanel.add(playerVsComputer, cnst);
        difficultPanel.add(easyButton, cnst);

        cnst.gridy++; // row 2
        variantPanel.add(forza5Button, cnst);
        vsPanel.add(playerVsPlayer, cnst);
        difficultPanel.add(mediumButton, cnst);

        cnst.gridy++; // row 3
        variantPanel.add(forza4VivianiButton, cnst);
        difficultPanel.add(hardButton, cnst);

        cnst.gridy++; // row 4
        variantPanel.add(forza4AttentaButton, cnst);

        mainPanel.add(variantPanel, BorderLayout.WEST);
        mainPanel.add(vsPanel, BorderLayout.CENTER);
        mainPanel.add(difficultPanel, BorderLayout.EAST);
        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(backButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        forza4Button.doClick();     // Default config: forza4, Player vs Computer, difficult Hard
        playerVsComputer.doClick();
        hardButton.doClick();
        this.selectedGameVariant = GameVariant.Forza4;
        this.selectedGameType = GameType.PvsC;
        this.selectedDifficult = Controller.Difficult.HARD;

        forza4Button.addActionListener(e -> NewGameMenu.this.selectedGameVariant = GameVariant.Forza4);
        forza5Button.addActionListener(e -> NewGameMenu.this.selectedGameVariant = GameVariant.Forza5);
        forza4VivianiButton.addActionListener(e -> NewGameMenu.this.selectedGameVariant = GameVariant.Viviani);
        forza4AttentaButton.addActionListener(e -> NewGameMenu.this.selectedGameVariant = GameVariant.Attenta);

        playerVsPlayer.addActionListener(e -> { 
            NewGameMenu.this.selectedGameType = GameType.PvsP;
            NewGameMenu.this.setDifficultPanelStatus(false);
        });

        playerVsComputer.addActionListener(e -> {
            NewGameMenu.this.selectedGameType = GameType.PvsC;
            NewGameMenu.this.setDifficultPanelStatus(true);
        });

        easyButton.addActionListener(e -> NewGameMenu.this.selectedDifficult = Controller.Difficult.EASY);
        mediumButton.addActionListener(e -> NewGameMenu.this.selectedDifficult = Controller.Difficult.MEDIUM);
        hardButton.addActionListener(e -> NewGameMenu.this.selectedDifficult = Controller.Difficult.HARD);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                // log
                System.out.println("[Game Settings]\nVariant: " + NewGameMenu.this.selectedGameVariant + "\nGameType: "
                                + NewGameMenu.this.selectedGameType);
                if (NewGameMenu.this.selectedGameType.equals(GameType.PvsC)) {
                    System.out.println("Difficult: " +  NewGameMenu.this.selectedDifficult);
                }

                NewGameMenu.this.newGameFrame.get().setVisible(false);
                NewGameMenu.this.controller.setSettings(NewGameMenu.this.selectedGameVariant, 
                                NewGameMenu.this.selectedGameType, NewGameMenu.this.selectedDifficult);
            }
        });

        backButton.addActionListener(e -> {
            NewGameMenu.this.newGameFrame.get().setVisible(false); 
            NewGameMenu.this.controller.getMenuManager().showMenu();
        });

        this.newGameFrame.get().getContentPane().add(mainPanel);
        this.newGameFrame.get().setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        if (this.newGameFrame.isPresent()) {
            this.newGameFrame.get().setVisible(true);
            System.out.println("Showing New Game menu..."); // log
            return;
        }
    }

    private void setDifficultPanelStatus(final boolean status) {
        NewGameMenu.this.difficultPanel.setEnabled(status);
        for (Component c: NewGameMenu.this.difficultPanel.getComponents()) {
            c.setEnabled(status);
        }
    }
}
