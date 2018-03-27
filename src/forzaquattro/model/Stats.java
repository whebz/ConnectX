package forzaquattro.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import forzaquattro.controller.Controller;
import forzaquattro.controller.Controller.Difficult;

/**
 * This Class maintains the game statistics.
 * It has two Hashmaps, one to maintain Player vs Computer statistics, and one for Player vs Player.
 *
 */
public class Stats implements Serializable {
    private static final long serialVersionUID = 4407387783050169006L;
    private static final int WON_INDEX = 0;
    private static final int LOST_INDEX = 1;
    private static final int DRAW_INDEX = 2;
    private Map<GameVariant, Map<Controller.Difficult, List<Integer>>> mapPvsC = new HashMap<>();
    private Map<GameVariant, List<Integer>> mapPvsP = new HashMap<>();

    /** 
     * Constructs the stats map.
     *
     */
    public Stats() {
        for (GameVariant g: GameVariant.values()) {
            this.mapPvsC.put(g, new HashMap<>());

            this.mapPvsC.get(g).put(Difficult.EASY, Arrays.asList(0, 0, 0));
            this.mapPvsC.get(g).put(Difficult.MEDIUM, Arrays.asList(0, 0, 0));
            this.mapPvsC.get(g).put(Difficult.HARD, Arrays.asList(0, 0, 0));

            this.mapPvsP.put(g, Arrays.asList(0, 0, 0));
        }

    }

    /**
     * Method to get how many matches player1 won vs Computer.
     * @param d
     *          The difficult 
     * @param v
     *          The variant
     * @param gt
     *          The game type
     * @return
     *          Number of the won matches
     */
    public int getWons(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        return this.getFromMap(v, gt,  d, WON_INDEX);
    }

    /**
     * Method to get how many matches player1 lost.
     * @param d
     *          The difficult 
     * @param v
     *          The variant 
     * @param gt
     *          The game type
     * @return
     *          Number of the lost matches
     */
    public int getLosts(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        return this.getFromMap(v, gt, d, LOST_INDEX);
    }

    /**
     * Method to get how many matches finished draw.
     * @param d
     *          The difficult 
     * @param v
     *          The variant 
     * @param gt
     *          The game type
     * @return
     *          Number of the lost matches
     */
    public int getDraws(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        return this.getFromMap(v, gt, d, DRAW_INDEX);
    }

    /**
     * Adds a won match to stats.
     * @param v
     *          The variant 
     * @param gt
     *          The game type
     * @param d 
     *          the current difficult of the game
     */
    public void addWon(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        switch (gt) {
            case PvsC:
                this.mapPvsC.get(v).get(d).set(WON_INDEX, this.mapPvsC.get(v).get(d).get(WON_INDEX) + 1);
                break;
            case PvsP:
                this.mapPvsP.get(v).set(WON_INDEX, this.mapPvsP.get(v).get(WON_INDEX) + 1);
                break;
            default:
        }
    }

    /**
     * Adds a lost match to stats.
     * @param d the current difficult of the game
     * @param v
     *          The variant 
     * @param gt
     *          The game type
     */
    public void addLost(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        switch (gt) {
            case PvsC:
                this.mapPvsC.get(v).get(d).set(LOST_INDEX, this.mapPvsC.get(v).get(d).get(LOST_INDEX) + 1);
                break;
            case PvsP:
                this.mapPvsP.get(v).set(LOST_INDEX, this.mapPvsP.get(v).get(LOST_INDEX) + 1);
                break;
            default:
        }
    }

    /**
     * Adds a draw match to stats.
     * @param d the current difficult of the game
     * @param v
     *          The variant 
     * @param gt
     *          The game type
     */
    public void addDraw(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        switch (gt) {
            case PvsC:
                this.mapPvsC.get(v).get(d).set(DRAW_INDEX, this.mapPvsC.get(v).get(d).get(DRAW_INDEX) + 1);
                break;
            case PvsP:
                this.mapPvsP.get(v).set(DRAW_INDEX, this.mapPvsP.get(v).get(DRAW_INDEX) + 1);
                break;
            default:
        }
    }

    /**
     * 
     * @param d the difficult
     * @return total number of matches played vs Computer
     * @param v The variant 
     * @param gt the game type
     */
    public int getTotalMatches(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        return this.getWons(v, gt, d) + this.getLosts(v, gt, d) + this.getDraws(v, gt, d);
    }

    /**
     * 
     * @param d the difficult
     * @param v The variant 
     * @param gt the gametype
     * @return the percentage of wons
     */
    public double getWonPerc(final GameVariant v, final GameType gt, final Controller.Difficult d) {
        if (this.getWons(v, gt, d) == 0) {
            return 0;
        }
        return this.getWons(v, gt, d) * 100.0 / this.getTotalMatches(v, gt, d);
    }
    @Override
    public String toString() {
        return "Stats PvsComputer: " + this.mapPvsC + "\n Stats PvsPlayer: " + this.mapPvsP;
    }

    private int getFromMap(final GameVariant v, final GameType gt, final Controller.Difficult d, final int index) {
        switch(gt) {
        case PvsC:
            return this.mapPvsC.get(v).get(d).get(index);
        case PvsP:
            return this.mapPvsP.get(v).get(index);
        default:
            break;
        }
        return 0;
    }
}
