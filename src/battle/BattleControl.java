package battle;

import gameobjects.*;

import java.util.ArrayList;

/**
 * Constructs BattleControl and holds functionalities for management of a Battle.
 */
public class BattleControl {
    private ArrayList <Integer> battleWinners;
    private ArrayList <Integer> fightWinners;
    private Battle battle;

    private ArrayList<Playable> pets;

    public BattleControl() {
        battleWinners = new ArrayList<>();
        fightWinners = new ArrayList<>();
        battleWinners.add(0);
        fightWinners.add(0);
    }

    /**
     * Constructs BattleControl and calls to start a Battle.
     * @param numFights integer of number of fights input by the user.
     * @param pets ArrayList of pets for the game.
     */
    public BattleControl (int numFights, ArrayList<Playable> pets) {
        startBattle(numFights, pets);
    }

    /**
     * Starts and constructs a battle and enters BattleControl class.
     * Calls to display winner after battle has concluded.
     * @param numFights integer of number of fights input by the user.
     * @param pets ArrayList of pets for the game.
     */
    private void startBattle(int numFights, ArrayList<Playable> pets) {
        System.out.println("Battle Begins!😫");
        battle = new Battle(numFights, pets);
        battleWinners = battle.getBattleWinners();
        fightWinners = battle.getFightWinners();
        //Out statement of winning pet
        displayWinner(pets);
    }

    /**
     * Displays winner of Battle.
     * @param pets ArrayList of pets for the game.
     */
    private void displayWinner(ArrayList<Playable> pets) {
        if (battleWinners.size() == 1) {
            for (int winner : battleWinners) {
                for (Playable pet : pets) {
                    if (pet.getPlayableUid() == winner)
                        System.out.println(pet.getPetName() + " Wins the Battle!");
                }
            }
        }
        else {
            for (int winner : battleWinners) {
                int count = 1;
                for (Playable pet : pets) {
                    if (pet.getPlayableUid() == winner) {
                        if (count != battleWinners.size())
                            System.out.print(pet.getPetName() + ", ");
                        else
                            System.out.print(pet.getPetName() + " ");
                        count++;
                    }
                }
            }
            System.out.println("Have Tied in the Battle!");
        }
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