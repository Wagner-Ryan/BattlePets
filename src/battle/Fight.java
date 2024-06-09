package battle;

import gameobjects.*;

import java.util.ArrayList;

/**
 * Constructs a Fight and begins the Rounds.
 */
public class Fight {
    private RoundControl currentRound;
    private int fightWinner;

    /**
     * Constructs Fight and calls to begin rounds.
     * @param pets ArrayList of pets for the game.
     */
    public Fight(ArrayList<Playable> pets) {
        beginRounds(pets);
        fightWinner = currentRound.getWinner();
    }

    /**
     * Constructs RoundControl.
     * @param pets ArrayList of pets for the game.
     */
    public void beginRounds(ArrayList<Playable> pets) {
        currentRound = new RoundControl(pets);
    }

    /**
     * Returns Uid of winner of the fight.
     * @return Uid of winner of the fight.
     */
    public int getFightWinner() {
        return fightWinner;
    }
}