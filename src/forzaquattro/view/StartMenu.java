package forzaquattro.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * This class is the implementation of the starting menu (the first that appears on app starting).
 *
 */
public class StartMenu implements Menu {

    private static final double MENU_PERC_WIDTH = 0.22;
    private static final double MENU_PERC_HEIGHT = 0.22;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Optional<JFrame> frame;


    /**
     * Constructor for the Start Menu.
     * Initializes the frame 
     * @param m
     *          reference to Main Menu to be able to navigate through the other menus
     */
    public StartMenu(final MenuManager m) {
        System.out.println("Creating StartMenu..."); // log for testing
        this.frame = Optional.of(new JFrame("Forza4-5"));
        this.frame.get().setSize((int) (screenSize.getWidth() * MENU_PERC_WIDTH), (int) (screenSize.getHeight() * MENU_PERC_HEIGHT));
        this.frame.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        final JButton newGame = new JButton("New Game");
        final JButton stats = new JButton("Statistics");
        final JButton exit = new JButton("Exit");

        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                StartMenu.this.frame.get().setVisible(false);
                m.showNewGameMenu();
            }
        });

        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                m.showStats();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("Exiting application...");
                System.exit(0);
            }
        });

        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder("Game Menu")); 
        final GridBagConstraints cnst = new GridBagConstraints(); 
        cnst.gridy = 0; // riga 1
        cnst.insets = new Insets(3, 3, 3, 3);
        cnst.fill = GridBagConstraints.HORIZONTAL; 
        panel.add(newGame, cnst); 
        cnst.gridy++; // riga 2
        panel.add(stats, cnst);
        cnst.gridy++; // riga 3
        panel.add(exit, cnst);

        this.frame.get().setResizable(false);
        this.frame.get().getContentPane().add(panel);
        this.frame.get().setLocationRelativeTo(null);

    }

    @Override
    public void show() {
        if (this.frame.isPresent()) {
            this.frame.get().setVisible(true);
            System.out.println("Showing StartMenu..."); // log
            return;
        }
    }
}
