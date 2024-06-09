package battle;

import damage.*;
import gameobjects.*;

import java.util.ArrayList;

public class Round {
    private int winner;
    private int roundNumber = 1;
    private int numPetsAwake = 0;
    private final int POS_PLACEHOLDER = 100000;
    private final int NEG_PLACEHOLDER = -100000;
    private ArrayList<DamageControl> petDamages = new ArrayList<>();
    private ArrayList<Integer> indexesOfPrevAttackedPets = new ArrayList<>();
    private ArrayList<Integer> previousRoundAwake = new ArrayList<>();
    private ArrayList<Skills> currentSkills = new ArrayList<>();

    /**
     * Constructs Round and holds all executions and functionalities for each Round.
     */
    public Round() {
    }

    /**
     * Starts the Round, controls progression of the Round, and repeats all Rounds necessary for Fight to conclude.
     * @param pets ArrayList of pets for the game.
     */
    public void startRounds(ArrayList<Playable> pets) {
        createArrayLists(pets);
        determineNumPetsAwake(pets);
        while(numPetsAwake > 1) {
            displayRoundNumber();
            for(int i = 0; i < pets.size(); i++) {
                if (pets.get(i).isAwake())
                    currentSkills.set(i, petChooseSkillChoice(pets.get(i)));
            }
            calculatePetDamages(pets);
            for(int i = 0; i < pets.size(); i++) {
                if (pets.get(i).isAwake())
                    displayPetSkillChoice(i, pets.get(i));
            }
            updateHP(pets, petDamages);
            displayPetDamages(pets, petDamages);
            displayPetHP(pets);
            incrementRoundNumber();
            determineNumPetsAwake(pets);
            for(int i = 0; i < indexesOfPrevAttackedPets.size(); i++)
                indexesOfPrevAttackedPets.set(i, POS_PLACEHOLDER);
        }
        roundNumber--;
        winner = determineWinner(pets);
    }

    /**
     * Determines the number of pets awake at the end of each round.
     * @param pets ArrayList of pets for the game.
     */
    public void determineNumPetsAwake(ArrayList<Playable> pets) {
        numPetsAwake = 0;
        for (Playable pet : pets) {
            if(pet.isAwake())
                numPetsAwake++;
        }
    }

    /**
     * Calls to reset petHP.
     * @param pet ArrayList of pets for the game.
     */
    public void resetPetHPs(Playable pet) {
        pet.resetHp();
    }

    /**
     * Displays current Round number.
     */
    public void displayRoundNumber() {
        System.out.println("Round: #" + roundNumber);
    }

    /**
     * Increments Round number to current Round number.
     */
    public void incrementRoundNumber() {
        roundNumber++;
    }

    /**
     * Calls for Player to choose Pet Skill.
     * @param pet ArrayList of pets for the game.
     * @return Pet's chosen skill.
     */
    public Skills petChooseSkillChoice(Playable pet) {
        Skills skill = pet.chooseSkill();
        return skill;
    }

    /**
     * Displays Pet's Skill choice.
     * @param index index of Pet location in the array.
     * @param pet ArrayList of pets for the game.
     */
    public void displayPetSkillChoice(int index, Playable pet) {
        System.out.println(pet.getPetName() + " Skill Choice: " + currentSkills.get(index));
    }

    /**
     * If pet is alive during current round, it calls to determine the attacked pet,
     * calls to set the current skills of the pets in DamageControl,
     * updates the indexes of previously attacked pets (that the pet has attacked),
     * then calls to calculate the total damage for the desired pet.
     * @param pets ArrayList of pets for the game.
     */
    public void calculatePetDamages(ArrayList<Playable> pets) {
        int indexOfAttackedPet;
        for(int indexOfAttackingPet = 0; indexOfAttackingPet < pets.size(); indexOfAttackingPet++) {
            if(pets.get(indexOfAttackingPet).isAwake()) {
                indexOfAttackedPet = determineAttackedPet(indexOfAttackingPet, pets);
                petDamages.get(indexOfAttackingPet).setCurrentSkill(currentSkills.get(indexOfAttackingPet), currentSkills.get(indexOfAttackedPet));
                indexesOfPrevAttackedPets.set(indexOfAttackingPet, indexOfAttackedPet);
                petDamages.get(indexOfAttackingPet).calculateTotalDamage(pets.get(indexOfAttackingPet), pets.get(indexOfAttackedPet));
            }
        }
    }

    /**
     * Determines if pet was attacked in previous round,
     * determines the which attacking pet attacked pet,
     * calls to updateHP of attacked pet by the attacking pet,
     * calls to set the random damage taken by the attacked pet from the attacking pet,
     * then sets pets previous round awake to current round.
     * @param pets ArrayList of pets for the game.
     * @param petDamages ArrayList of pet damages for the round.
     */
    public void updateHP(ArrayList<Playable> pets, ArrayList<DamageControl> petDamages) {
        int index = 0;
        for (Playable pet : pets) {
            if (indexesOfPrevAttackedPets.get(index) != POS_PLACEHOLDER) {
                int indexOfPetThatAttacked = POS_PLACEHOLDER;
                for(int i = 0; i < indexesOfPrevAttackedPets.size(); i++) {
                    if (indexesOfPrevAttackedPets.get(i) == index)
                        indexOfPetThatAttacked = i;
                }
                pet.updateHp(petDamages.get(indexOfPetThatAttacked).getTotalDamage());
                petDamages.get(index).setRandomDamageTaken(petDamages.get(indexOfPetThatAttacked).getRandomDamage());
                previousRoundAwake.set(indexOfPetThatAttacked, roundNumber);
            }
            //previousRoundAwake.set(index, roundNumber);
            index++;
        }
    }

    /**
     * Displays Pet's Random Damage, Difference in Random Damage, Conditional Damage, and Total Damage for the Round.
     * @param pets ArrayList of pets for the game.
     * @param petDamages ArrayList of pet damages for the round.
     */
    public void displayPetDamages(ArrayList<Playable> pets, ArrayList<DamageControl> petDamages) {
        int index = 0;
        for (Playable pet : pets) {
            if (indexesOfPrevAttackedPets.get(index) != POS_PLACEHOLDER) {
                System.out.println(pet.getPetName() + " Random Damage: " + petDamages.get(index).getRandomDamage());
                System.out.println(pet.getPetName() + " Conditional Damage: " + petDamages.get(index).getConditionalDamage());
                System.out.println(pet.getPetName() + " Total Damage: " + petDamages.get(index).getTotalDamage());
                System.out.println(pet.getPetName() + " Difference in Random Damage: " + petDamages.get(index).getRandomDamageDifference());
            }
            index++;
        }
    }

    /**
     * Displays Pet's HP at the end of the Round.
     * @param pets ArrayList of pets for the game.
     */
    public void displayPetHP(ArrayList<Playable> pets) {
        for (Playable pet : pets)
            System.out.println(pet.getPetName() + " Current HP: " + pet.getCurrentHp());
    }

    /**
     * Determines the winner in event of a singular pet that is awake and event that multiple pets are awake.
     * @param pets ArrayList of pets for the game.
     * @returns Pet's Uid that has won.
     */
    public int determineWinner(ArrayList<Playable> pets) {
        int count = 0;
        int index = POS_PLACEHOLDER;
        int winnerID = POS_PLACEHOLDER;
        double maxHP = NEG_PLACEHOLDER;
        ArrayList<Playable> petsAwakeLastRound = new ArrayList<>();
        //Determines winner when only one pet is awake
        for (Playable pet : pets) {
            if (pet.isAwake()) {
                count++;
                winnerID = pet.getPlayableUid();
            }
        }
        //Determines winner if no pets are awake
        if (count == 0) {
            for (int i = 0; i < previousRoundAwake.size(); i++) {
                if (previousRoundAwake.get(i) == roundNumber)
                    petsAwakeLastRound.add(pets.get(i));
            }
            for (Playable pet : petsAwakeLastRound) {
                if (pet.getCurrentHp() > maxHP) {
                    maxHP = pet.getCurrentHp();
                    winnerID = pet.getPlayableUid();
                }
            }

        }
        return winnerID;
    }

    /**
     * Returns Uid of winner.
     * @return Uid of winner.
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Determines the index of attacked pet for the given index of the attacking pet,
     * enumerates from left to right in the ArrayList,
     * if the index of the pet is at the end of the ArrayList, then it loops to the beginning of the ArrayList.
     * @param indexOfAttackingPet index of the pet that is attacking in the ArrayList.
     * @param pets ArrayList of pets for the game.
     * @return the index of the attacked pet in the ArrayList.
     */
    public int determineAttackedPet(int indexOfAttackingPet, ArrayList<Playable> pets) {
        int indexOfAttackedPet = POS_PLACEHOLDER;
        //Checks if index is at the beginning of the ArrayList
        if (indexOfAttackingPet == 0) {
            for (int i = indexOfAttackingPet + 1; i < pets.size(); i++) {
                if (pets.get(i).isAwake() && indexOfAttackedPet == POS_PLACEHOLDER)
                    indexOfAttackedPet = i;
            }
        }
        //Checks if index is at the end of the arraylist
        else if (indexOfAttackingPet != pets.size() - 1) {
            //Part of the ArrayList after indexOfAttackingPet
            for (int i = indexOfAttackingPet + 1; i < pets.size(); i++) {
                if (pets.get(i).isAwake() && indexOfAttackedPet == POS_PLACEHOLDER)
                    indexOfAttackedPet = i;
            }
            //Part of the ArrayList before indexOfAttackingPet
            for (int i = 0; i < indexOfAttackingPet; i++) {
                if (pets.get(i).isAwake() && indexOfAttackedPet == POS_PLACEHOLDER)
                    indexOfAttackedPet = i;
            }
        }
        //Executed if index is at the end of the arraylist
        else {
            //Part of the arraylist before indexOfAttackingPet
            for (int i = 0; i < indexOfAttackingPet; i++) {
                if (pets.get(i).isAwake() && indexOfAttackedPet == POS_PLACEHOLDER)
                    indexOfAttackedPet = i;
            }
        }
        return indexOfAttackedPet;
    }

    /**
     * Instantiates ArrayLists that are used to aid in tracking and calculations of petDamages,
     * indexes of previously attacked pets,
     * previous round that a pet was awake,
     * and current skill choices of each pet.
     * @param pets ArrayList of pets for the game.
     */
    private void createArrayLists(ArrayList<Playable> pets) {
        for (int i = 0; i < pets.size(); i++) {
            petDamages.add(new DamageControl());
            indexesOfPrevAttackedPets.add(POS_PLACEHOLDER);
            previousRoundAwake.add(roundNumber);
            currentSkills.add(null);
        }
    }
}