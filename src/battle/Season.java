package battle;

import gameobjects.*;

import java.util.*;

/**
 * This class creates and runs a season, which is a set of battles where every pet will battle every other pet
 * exactly once. Uses an iterator as this class is an iterable.
 */
public class Season implements Iterable<BattleControl>{

    ArrayList<Integer> petIds;
    private ArrayList<BattleControl> battleList;
    private ArrayList<Playable> petList;
    private ArrayList<Integer> battleWins;
    private ArrayList<Integer> fightWins;
    private ArrayList<List<Playable>> listOfPlayables;
    private int numFights;

    /**
     * Default constructor that makes a new season.
     */
    public Season() {
        //petArray = new Playable[][];
        battleList = new ArrayList<>();
        petList = new ArrayList<>();
        battleWins = new ArrayList<>();
        fightWins = new ArrayList<>();
        listOfPlayables = new ArrayList<>();
        petIds = new ArrayList<>();
    }

    /**
     * Takes in the pets that are going to participate in the season, makes arrays for all the pairs of pets
     * for battles so each pet plays each other pet exactly once in a round-robin style. This method also takes
     * in the number of fights per battle and sets an attribute to be used when battles are iterated.
     * @param pets the list of pets participating in the season as an ArrayList.
     * @param fightsPerBattle the amount of fights that occur during each battle of the season as an int.
     */
    public void setBattles(ArrayList<Playable> pets, int fightsPerBattle) {
        numFights = fightsPerBattle;
        for (int i = 0; i < pets.size(); i++) {
            petIds.add(pets.get(i).getPlayableUid());
            battleWins.add(0);
            fightWins.add(0);
        }
        petList.addAll(pets);
        if (petList.size() % 2 != 0)
        {
            petList.add(null);
        }

        int rounds = petList.size() - 1;
        int loops = 0;
        while (loops < rounds)
        {
            int opponentIndex = petList.size() - 1;
            for (int i = 0; i < petList.size() / 2; i++)
            {
                ArrayList<Playable> playables = new ArrayList<>(2);
                playables.add(petList.get(i));
                playables.add(petList.get(opponentIndex));
                listOfPlayables.add(playables);
                opponentIndex--;
            }
            // changes position of each pet for round-robin
            Playable tempPet;
            for(int i = petList.size() - 1; i > 1; i--){
                tempPet = petList.get(i);
                petList.set(i, petList.get(i-1));
                petList.set(i-1, tempPet);
            }
            loops++;
        }

    }

    /**
     * Calls to the Iterator in order to sequence through all battles,
     * Counts each pets fight wins and battle wins,
     * and then prints all season results in descending order by battle wins and fight wins.
     */
    public void startBattles() {
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            BattleControl currentBattle = (BattleControl) iterator.next();
            ArrayList<Integer> tempFightWinners = currentBattle.getFightWinners();
            ArrayList<Integer> tempBattleWinners = currentBattle.getBattleWinners();
            for(int i = 0; i < petIds.size(); i++) {
                for (int j = 0; j < tempFightWinners.size(); j++) {
                    if (petIds.get(i).equals(tempFightWinners.get(j))){
                        int winCount = fightWins.get(i);
                        winCount++;
                        fightWins.set(i, winCount);
                    }
                }
            }
            for(int i = 0; i < petIds.size(); i++) {
                for (int j = 0; j < tempBattleWinners.size(); j++) {
                    if (petIds.get(i).equals(tempBattleWinners.get(j))){
                        int winCount = battleWins.get(i);
                        winCount++;
                        battleWins.set(i, winCount);
                    }
                }
            }
        }
        //Print Winners
        ArrayList<ArrayList<Integer>> petBattleInfo = new ArrayList<>();
        for(int i = 0; i < petIds.size(); i++) {
            ArrayList<Integer> petBattleInfoTemp = new ArrayList<>();
            petBattleInfoTemp.add(battleWins.get(i));
            petBattleInfoTemp.add(fightWins.get(i));
            petBattleInfoTemp.add(petIds.get(i));
            petBattleInfo.add(petBattleInfoTemp);
        }
        ArrayList<Integer> battleSort = new ArrayList<>();
        for(int i = 0; i < battleWins.size(); i++)
            battleSort.add(battleWins.get(i));

        //Sorts by fight wins and then battle wins.
        Collections.sort(petBattleInfo, Comparator.comparingInt(list -> list.get(1)));
        Collections.sort(petBattleInfo, Comparator.comparingInt(list -> list.get(0)));

        System.out.println("Season Results:");

        for (int i = petBattleInfo.size() - 1; i >= 0; i--) {
            String petName = "";
            String petBattleWins = "";
            String petFightWins = "";
            for (int j = 0; j < petIds.size(); j++) {
                if (petList.get(j).getPlayableUid() == (petBattleInfo.get(i).get(2)))
                    petName = petList.get(j).getPetName();
            }
            petBattleWins = petBattleInfo.get(i).get(0).toString();
            petFightWins = petBattleInfo.get(i).get(1).toString();
            System.out.println("Pet Name: " + petName + " # Of Battle Wins: " + petBattleWins + " # of Fight Wins: " + petFightWins);
        }
    }

    /**
     * Creates a new SeasonIterator to iterate through the BattleControls.
     * @return a new SeasonIterator that iterates BattleControls.
     */
    @Override
    public Iterator<BattleControl> iterator() {
        return new SeasonIterator();
    }


    /**
     * A nested iterator that iterates through all the Battles in a Season.
     */
    private class SeasonIterator implements Iterator<BattleControl>
    {

        private int battleIndex = 0;

        /**
         * Checks to see if there is a next set of pets to start a battle with.
         * @return true if there is a next set of pets to battle, false if else.
         */
        @Override
        public boolean hasNext() {
            return battleIndex < Season.this.listOfPlayables.size();
        }

        /**
         * Creates a battle with the next two pets that are iterated through. If one pet is null, then creates
         * a BattleControl with the default BattleControl constructor, otherwise uses the
         * BattleControl (int numFights, ArrayList<Playable> pets) constructor.
         * @return a BattleControl for the battle between two pets.
         */
        @Override
        public BattleControl next() {
            BattleControl battle;
            if (Season.this.listOfPlayables.get(battleIndex).get(0) != null && Season.this.listOfPlayables.get(battleIndex).get(1) != null) {
                battle = new BattleControl(numFights, (ArrayList<Playable>) Season.this.listOfPlayables.get(battleIndex++));
            }
            else {
                battle = new BattleControl();
                battleIndex++;
            }
            return battle;
        }
    }
}
