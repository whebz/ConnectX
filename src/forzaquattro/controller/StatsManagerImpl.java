package forzaquattro.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import forzaquattro.model.Stats;

/**
 * This class is meant to save Stats object to a file in the home directory of the user.
 * It can also, of course, read the previous Stats object from the eventually existing file.
 */
public class StatsManagerImpl implements StatsManager {

    private static final String STATS_FILENAME = System.getProperty("user.home") + System.getProperty("file.separator") + "forza4-5_stats.dat";
    private Stats localStats;

    /**
     * Checks if a Stats file exists.
     * if yes loads it to memory.
     * if no creates a new file.
     */
    public StatsManagerImpl() {
        System.out.println("[StatsManager] Looking at stats file " + STATS_FILENAME);

        try (ObjectInputStream os = new ObjectInputStream(new BufferedInputStream(new FileInputStream(STATS_FILENAME)))) {

            Stats s = (Stats) os.readObject();
            this.localStats = s;
            System.out.println("[StatsManager] Stats file found.");

        } catch (FileNotFoundException e) {
            System.out.println("[StatsManager] Stats file not found. Trying to create a new one...");
            this.localStats = new Stats();
            this.writeNewStatsFile(this.localStats);
            System.out.println("[StatsManager] Stats file created.");
        } catch (InvalidClassException e) {
            System.out.println("[StatsManager] Old stats file found.");
            this.resetInvalidStats();
        } catch (InvalidObjectException e) {
            System.out.println("[StatsManager] Invalid stats file found.");
            this.resetInvalidStats();
        } catch (ClassNotFoundException e) {
            System.out.println("[StatsManager] Invalid stats file found.");
            this.resetInvalidStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return the stats object
     */
    public Stats getStats() {
        return this.localStats;
    }

    /**
     * 
     * @param s the stats to write to file
     */
    public void writeStats(final Stats s) {
        try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(STATS_FILENAME)))) {
            os.writeObject(s);
            System.out.println("[StatsManager] Writing Stats object to " + STATS_FILENAME);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeNewStatsFile(final Stats s) {
        try {
            File f = new File(STATS_FILENAME);
            f.createNewFile();
            System.out.println("[StatsManager] Created new Stats file in " + STATS_FILENAME);
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        this.writeStats(s);
    }

    private void resetInvalidStats() {
        this.deleteOldStatsFile();
        this.localStats = new Stats();
        this.writeNewStatsFile(this.localStats);
    }

    private void deleteOldStatsFile() {
        File f = new File(STATS_FILENAME);
        f.delete();
        System.out.println("[StatsManager] Old stats file deleted.");
    }
}
