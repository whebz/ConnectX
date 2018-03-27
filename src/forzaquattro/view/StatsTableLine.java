package forzaquattro.view;

/**
 * A simple class to manage an entry of the stats table.
 *
 */
public class StatsTableLine {

    private String difficult;
    private int wons;
    private int losts;
    private int draws;
    private double perc;

    /**
     * Constructor to initialize variables.
     * @param difficult     -the difficult of the entry
     * @param wons          -number of won matches
     * @param losts         -number of lost matches
     * @param draws         -number of draw matches
     * @param perc          -won matches' percentage
     */
    public StatsTableLine(final String difficult, final int wons, final int losts, final int draws, final double perc) {
        this.difficult = difficult;
        this.wons = wons;
        this.losts = losts;
        this.draws = draws;
        this.perc = perc;
    }

    /**
     * 
     * @return the difficult string of this line
     */
    public String getDifficult() {
        return this.difficult;
    }

    /**
     * 
     * @return the number of won matches in this line
     */
    public int getWons() {
        return this.wons; 
    }

    /**
     * 
     * @return the number of lost matches in this line
     */
    public int getLosts() {
        return this.losts;
    }

    /**
     * 
     * @return the number of draw matches in this line
     */
    public int getDraws() {
        return this.draws;
    }

    /**
     * 
     * @return the percentage of won matches
     */
    public double getPerc() {
        return this.perc;
    }
}