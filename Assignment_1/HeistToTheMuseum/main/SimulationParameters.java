package main;

/**
 * Definition of simulation parameters
 */

public class SimulationParameters {
    /**
     * Number of Rooms
     */
    public static final int N = 5;

    /**
     * Number of Thieves
     */
    public static final int M = 6;

    /**
     * Number of Parties
     */
    public static final int G = 2;

    /**
     * Number of Thieves per party
     */
    public static final int K = 3;

    /**
     * Max separation distance between thieves
     */
    public static final int S = 3;

    //Max Displacement MD = random(2 to 6)
    //Distance from out to room Di = random(15 to 30)
    //Amount of paintings in each room Qi = random(8 to 16) 
    private SimulationParameters () {}
}
