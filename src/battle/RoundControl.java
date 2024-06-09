package battle;

import gameobjects.*;

import java.util.ArrayList;

/**
 * Constructs RoundControl and acts as the management of Rounds.
 */
public class RoundControl {
    private Round round = new Round();
    private int winner;

    /**
     * Constructs RoundControl and calls to start rounds.
     * @param pets ArrayList of pets for the game.
     */
    public RoundControl(ArrayList<Playable> pets) {
        startRounds(pets);
        winner = round.getWinner();
    }

    /**
     * Starts Rounds.
     * @param pets ArrayList of pets for the game.
     */
    public void startRounds(ArrayList<Playable> pets) {
        round.startRounds(pets);
    }

    /**
     * Returns Uid of winner.
     * @return Uid of winner
     */
    public int getWinner() {
        return winner;
    }
}