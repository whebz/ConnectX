package forzaquattro.controller;

import forzaquattro.model.Stats;

/**
 * This interface models a manager to save Stats object to a file in the home directory of the user.
 *  It tries to read a Stats object from user home directory, if doesn't exist 
 *  or is corrupted it creates a new one and saves it.
 */
public interface StatsManager {

    /**
     * This method simply returns the local stats object.
     * @return the stats object
     */
    Stats getStats();

    /**
     * Writes the stats object passed as argument to user home directory.
     * @param s the stats to write to file
     */
    void writeStats(final Stats s);
}
