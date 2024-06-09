package gamesettings;

import java.util.Random;

public class Settings {

    private int randomSeed;

    public static Random random;

    /**
     * gets the randomSeed from this class
     * @return random seed
     */
    public int getRandomSeed() {
        return randomSeed;
    }

    /**
     * set randomSeed to the given new randomSeed
     * @param randomSeed the new randomSeed to be set
     */
    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
        random = new Random(randomSeed);
    }
}
