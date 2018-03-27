package forzaquattro.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import forzaquattro.controller.Controller;
import forzaquattro.controller.Controller.Difficult;
import forzaquattro.controller.StatsManagerImpl;
import forzaquattro.model.GameType;
import forzaquattro.model.GameVariant;
import forzaquattro.model.Stats;

/**
 * This class is the implementation of the menu that shows the statistics of the previous played games.
 *
 */
public class StatsMenu implements Menu {

    private static final double MENU_PERC_WIDTH = 0.3;
    private static final double MENU_PERC_HEIGHT = 0.8;
    private static final int TABLES_NUM = 5;

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Optional<JFrame> frame;
    private StatsManagerImpl sm;

    /**
     * Constructor for the Stats Menu.
     * Initializes the frame 
     * @param c
     *              the controller to get reference of stats manager
     */
    public StatsMenu(final Controller c) {
        this.sm = c.getStatsManager();

        System.out.println("Creating StatsMenu..."); // log
        this.frame = Optional.of(new JFrame("Forza4-5  -  Statistics"));
        this.frame.get().setSize((int) (screenSize.getWidth() * MENU_PERC_WIDTH), (int) (screenSize.getHeight() * MENU_PERC_HEIGHT));
        this.frame.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Stats s = sm.getStats();
        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        JPanel panelPvsC = new JPanel(new GridLayout(TABLES_NUM, 1));
        panelPvsC.setBorder(new TitledBorder("Player vs Computer"));

        // creating tables for PvsComputer stats
        for (GameVariant v: GameVariant.values()) {

            List<StatsTableLine> lines = new ArrayList<>();
            for (Difficult d: Controller.Difficult.values()) {
                lines.add(new StatsTableLine(d.toString(), s.getWons(v, GameType.PvsC, d), 
                                s.getLosts(v, GameType.PvsC, d), s.getDraws(v, GameType.PvsC, d), s.getWonPerc(v, GameType.PvsC, d)));
            }

            StatsTableModel tableModel = new StatsTableModel(lines, GameType.PvsC);
            JTable tablePvsC1 = new JTable(tableModel);
            JScrollPane scrc1 = new JScrollPane(tablePvsC1);
            scrc1.setBorder(new TitledBorder(v.toString())); 
            panelPvsC.add(scrc1);

        }

        // creating table for PvsPlayer stats
        List<StatsTableLine> lines = new ArrayList<>();
        for (GameVariant v: GameVariant.values()) {
            Difficult d = Difficult.EASY; // only for compatibility (to use same methods for PvsP and PvsC)
            lines.add(new StatsTableLine(v.toString(), s.getWons(v, GameType.PvsP, d), 
                                s.getLosts(v, GameType.PvsP, d), s.getDraws(v, GameType.PvsP, d), s.getWonPerc(v, GameType.PvsP, d)));
        }
        StatsTableModel tableModel = new StatsTableModel(lines, GameType.PvsP);
        JTable tablePvsC1 = new JTable(tableModel);
        JScrollPane scrc1 = new JScrollPane(tablePvsC1);
        scrc1.setBorder(new TitledBorder("Player1 vs Player2")); 
        panelPvsC.add(scrc1);

        mainPanel.add(panelPvsC);

        this.frame.get().getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.frame.get().setResizable(true);
        this.frame.get().setLocationRelativeTo(null);
        this.frame.get().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }



    @Override
    public void show() {
        if (this.frame.isPresent()) {
            this.frame.get().setVisible(true);
            System.out.println("Showing StatsMenu..."); // log
        }
    }
}
