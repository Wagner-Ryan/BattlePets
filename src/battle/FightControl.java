package battle;

import gameobjects.*;

import java.util.ArrayList;

/**
 * Constructs FightControl and begins a Fight.
 */
public class FightControl {
    private Fight fight;
    private int fightWinner;

    /**
     * Constructs Fight and calls to start a Fight for a Battle.
     * @param pets ArrayList of pets for the game.
     */
    public FightControl(int alternate, ArrayList<Playable> pets) {
        startFight(pets);
    }

    /**
     * Constructs a Fight for an Exhibition.
     * @param pets ArrayList of pets for the game.
     */
    public FightControl(ArrayList<Playable> pets) {
        System.out.println("Exhibition Fight Begins!");
        startFight(pets);
        for(int i = 0; i < pets.size(); i++) {
            if(getFightWinner() == pets.get(i).getPlayableUid())
                System.out.println("Exhibition Fight Winner: " + pets.get(i).getPetName());
        }
    }

    /**
     * Starts a fight and constructs Fight.
     * @param pets ArrayList of pets for the game.
     */
    private void startFight(ArrayList<Playable> pets) {
        for (Playable pet : pets)
            pet.resetHp();
        fight = new Fight(pets);
        fightWinner = fight.getFightWinner();
    }

    /**
     * Returns Uid of winner of the fight.
     * @return Uid of winner of the fight
     */
    public int getFightWinner() {
        return fightWinner;
    }
}