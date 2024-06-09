package battle;

import gameobjects.*;

import java.util.ArrayList;

/**
 * Constructs a Battle and holds all functionalities to manage Fights.
 */
public class Battle {
    private FightControl currentFight;
    private final int NEG_PLACEHOLDER = -100000;
    private ArrayList<Integer> fightWinners = new ArrayList<>();
    private ArrayList<Integer> battleWinners;
    private final int BEGIN_COUNT = 0;
    ArrayList<Integer> petIds = new ArrayList<>();

    /**
     * Constructs Battle, initializes petIds ArrayList, calls to begin Fights and calculateBattleWinner.
     * @param numFights Integer of number of desired fights input by user.
     * @param pets ArrayList of pets for the game.
     */
    public Battle(int numFights, ArrayList<Playable> pets) {
        for (int i = 0; i < pets.size(); i++)
            petIds.add(pets.get(i).getPlayableUid());
        beginFights(numFights, pets);
        battleWinners = calculateBattleWinner();
    }

    /**
     * Begins the Fights for the input number of Fights, displays fight number,
     * enters FightControl class, and tracks winner of Fight in fightWinners ArrayList.
     * @param numFights Integer of number of desired fights input by user.
     * @param pets ArrayList of pets for the game.
     */
    //Only allows one fight even when multiple are entered, to be fixed when all other code is working.
    public void beginFights(int numFights, ArrayList<Playable> pets) {
        for(int i = 0; i < numFights; i++) {
            System.out.println("Fight: #" + (i+1));
            currentFight = new FightControl(numFights, pets);
            fightWinners.add(currentFight.getFightWinner());
            for(int j = 0; j < pets.size(); j++) {
                if(currentFight.getFightWinner() == pets.get(j).getPlayableUid())
                    System.out.println("Fight #" + (i+1) + " Winner: " + pets.get(j).getPetName());
            }
        }
    }

    /**
     * Calculates the Battle winners, returns the set of pet Uid's that have won the Battle
     * @returns ArrayList of Uid's of winners of the battle
     */
    public ArrayList<Integer> calculateBattleWinner() {
        int maxWins = NEG_PLACEHOLDER;
        ArrayList<Integer> petWins = new ArrayList<>();
        ArrayList<Integer> winners = new ArrayList<>();
        for(int i = 0; i < petIds.size(); i++)
            petWins.add(0);
        for(int i = 0; i < petIds.size(); i++) {
            for (int j = 0; j < fightWinners.size(); j++) {
                if (petIds.get(i).equals(fightWinners.get(j))){
                    int winCount = petWins.get(i);
                    winCount++;
                    petWins.set(i, winCount);
                }
            }
        }
        for (int wins : petWins) {
            if (wins > maxWins)
                maxWins = wins;
        }
        for (int i = 0; i < petWins.size(); i++) {
            if (maxWins == petWins.get(i))
                winners.add(petIds.get(i));
        }
        return winners;
    }

    /**
     * Returns ArrayList of Uid's for winners of Battle.
     * @return set of winners of the battle.
     */
    public ArrayList<Integer> getBattleWinners() {
        return battleWinners;
    }

    /**
     * Returns ArrayList of Uid's for winners of the fights.
     * @return set of winners of the fights.
     */
    public ArrayList<Integer> getFightWinners() {
        return fightWinners;
    }
}