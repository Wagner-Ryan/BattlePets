package gameobjects;

import types.*;
import java.util.Scanner;
import java.util.Random;

/**
 * Class that handles the creation and handling of Pet variables and attributes.
 * @author Eli Brown
 */
public class Pet implements Playable {
    private int uid;
    private PetTypes type;
    private String petName;

    private double currentHP;
    private double startingHP;
    private boolean awake;

    public Skills currentSkill;
    private Skills previousSkill;

    private Skills skillPrediction;

    private int rockRechargeTime;
    private int scissorsRechargeTime;
    private int paperRechargeTime;
    private int shootTheMoonTime;
    private int reversalOfFortuneTime;

    private int indexOfPrevAttackedPet;

    private boolean isAI;
    private Random random;

    /**
     * Default constructor for Pet.
     * @param uid unique id of the pet
     * @param type Type of the pet
     * @param startingHP starting HP of pet as a double
     */
    public Pet(int uid, String name, PetTypes type, double startingHP, boolean isAI) {
        this.uid = uid;
        this.petName = name;
        this.type = type;
        this.startingHP = startingHP;
        this.currentHP = startingHP;
        this.awake = true;
        this.isAI = isAI;
        this.random = new Random();
    }

    /**
     * @return Returns the unique id for this playable. The id is set during construction.
     */
    @Override
    public int getPlayableUid() {
        return uid;
    }

    /**
     * Sets the playableUid to the id provided. Implemented but not called.
     * @param playableUid The unique id to assign to the current playable object
     */
    @Override
    public void setPlayableUid(int playableUid) {
        this.uid = playableUid;
    }

    /**
     * @return Returns the id of the playable. This is assumed as the index it is stored in within the list.
     */
    @Override
    public int getPlayableId() {
        return 0;
    }

    /**
     * @return returns the player's name.
     */
    @Override
    public String getPlayerName() {
        // Need to figure out yet

        /*if(uid == 1){
            return Settings.getInstance().getPlayer1().getName();
        }
        if(uid == 2){
            return Settings.getInstance().getPlayer2().getName();
        }*/
        return null;
    }

    /**
     * @return Returns the pet's name.
     */
    @Override
    public String getPetName() {
        return petName;
    }

    /**
     * @return returns the player's name.
     */
    @Override
    public PlayerTypes getPlayerType() {
        // To be built in future assignments
        return null;
    }

    /**
     * @return returns the pet's type.
     */
    @Override
    public PetTypes getPetType() {
        return type;
    }

    /**
     * @return returns the pet's current HP.
     */
    @Override
    public double getCurrentHp() {
        return currentHP;
    }

    /**
     * The pet will execute logic or prompt the user to choose a skill depending on the pet type.
     * @return returns the chosen skill
     */
    @Override
    public Skills chooseSkill() {
        if (isAI) {
            decrementRechargeTimes();
            int variableRechargeStart = 0;

            if (currentSkill == Skills.ROCK_THROW || currentSkill == Skills.PAPER_CUT || currentSkill == Skills.SCISSORS_POKE)
                variableRechargeStart = 1;
            else if (currentSkill == Skills.REVERSAL_OF_FORTUNE || currentSkill == Skills.SHOOT_THE_MOON)
                variableRechargeStart = 6;

            setRechargeTime(currentSkill, variableRechargeStart);

            //choose random skill
            boolean validSkill = false;
            Skills[] skills = Skills.values();
            //Skills chosenSkill = null;
            while(!validSkill) {
                currentSkill = skills[random.nextInt(skills.length)];
                if (currentSkill == Skills.ROCK_THROW && rockRechargeTime == 0)
                    validSkill = true;
                else if (currentSkill == Skills.SCISSORS_POKE && scissorsRechargeTime == 0)
                    validSkill = true;
                else if (currentSkill == Skills.PAPER_CUT && paperRechargeTime == 0)
                    validSkill = true;
                else if (currentSkill == Skills.SHOOT_THE_MOON && shootTheMoonTime == 0)
                    validSkill = true;
                else if (currentSkill == Skills.REVERSAL_OF_FORTUNE && reversalOfFortuneTime == 0)
                    validSkill = true;
            }
            return currentSkill;
        }
        else{
            decrementRechargeTimes();
            int variableRechargeStart = 0;

            if (currentSkill == Skills.ROCK_THROW || currentSkill == Skills.PAPER_CUT || currentSkill == Skills.SCISSORS_POKE)
                variableRechargeStart = 1;
            else if (currentSkill == Skills.REVERSAL_OF_FORTUNE || currentSkill == Skills.SHOOT_THE_MOON)
                variableRechargeStart = 6;

            setRechargeTime(currentSkill, variableRechargeStart);

            Scanner scanner = new Scanner(System.in);
            boolean valid = false;
            while (!valid) {
                System.out.println(this.petName + " Choose a Skill:");
                System.out.println("1. Rock Throw");
                System.out.println("2. Scissors Poke");
                System.out.println("3. Paper Cut");
                System.out.println("4. Shoot the Moon");
                System.out.println("5. Reversal of Fortune");
                if (scanner.hasNextInt()) {
                    int skillChoice = scanner.nextInt();

                    if (skillChoice == 1 && getSkillRechargeTime(Skills.ROCK_THROW) == 0) {
                        currentSkill = Skills.ROCK_THROW;
                        valid = true;
                    } else if (skillChoice == 2 && getSkillRechargeTime(Skills.SCISSORS_POKE) == 0) {
                        currentSkill = Skills.SCISSORS_POKE;
                        valid = true;
                    } else if (skillChoice == 3 && getSkillRechargeTime(Skills.PAPER_CUT) == 0) {
                        currentSkill = Skills.PAPER_CUT;
                        valid = true;
                    } else if (skillChoice == 4 && getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0) {
                        currentSkill = Skills.SHOOT_THE_MOON;
                        System.out.println("Enter Opponent Skill Prediction: ");
                        int prediction = 0;
                        boolean bool = false;
                        while (!bool) {
                            if (scanner.hasNextInt()) {
                                prediction = scanner.nextInt();
                                if (prediction != 1 && prediction != 2 && prediction != 3 && prediction != 4 && prediction != 5) {
                                    System.out.println("Invalid Input (1, 2, 3, 4, 5)");
                                    System.out.println("Enter Opponent Skill Prediction: ");
                                    scanner.nextLine();
                                } else {
                                    bool = true;
                                }
                            } else {
                                scanner.nextLine();
                                System.out.println("Invalid Input, Please enter an integer (1, 2, 3, 4, 5) for your skill prediction");
                                System.out.println("Enter Opponent Skill Prediction: ");
                            }
                        }

                        Skills skill;
                        if (prediction == 1) {
                            skill = Skills.ROCK_THROW;
                        } else if (prediction == 2) {
                            skill = Skills.SCISSORS_POKE;
                        } else if (prediction == 3) {
                            skill = Skills.PAPER_CUT;
                        } else if (prediction == 4) {
                            skill = Skills.SHOOT_THE_MOON;
                        } else {
                            skill = Skills.REVERSAL_OF_FORTUNE;
                        }

                        skillPrediction = skill;
                        valid = true;
                    } else if (skillChoice == 5 && getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0) {
                        currentSkill = Skills.REVERSAL_OF_FORTUNE;
                        valid = true;
                    } else System.out.println("Skill still recharging");
                } else {
                    scanner.nextLine();
                    System.out.println("Invalid choice or Skill still recharging");
                }
            }
        }

        return currentSkill;
    }

    /**
     * Method is called by the game's controllng classes to update their pet's HP based on the damage inflicted.
     * @param hp The hp which will be subtracted from the current hp
     */
    @Override
    public void updateHp(double hp) {
        currentHP -= hp;
        if (currentHP <= 0) {
            awake = false;
        }
    }

    /**
     * Resets the pet's current HP to its starting HP
     */
    @Override
    public void resetHp() {
        currentHP = startingHP;
        awake = true;
    }

    /**
     * @return returns true if the pet's hp > 0, false otherwise.
     */
    @Override
    public boolean isAwake() {
        return awake;
    }

    /**
     * @return returns the predicted skill
     */
    @Override
    public Skills getSkillPrediction() {
        if (isAI) {
            Skills[] skills = Skills.values();
            Skills chosenSkill = skills[random.nextInt(skills.length)];
            return chosenSkill;
        }
        return skillPrediction;
    }

    /**
     * @param skill current skill of attacking pet
     * @return returns the current recharge time for the provided skill enumeration.
     */
    @Override
    public int getSkillRechargeTime(Skills skill) {
        if(currentHP == startingHP){
            rockRechargeTime = 0;
            paperRechargeTime = 0;
            scissorsRechargeTime = 0;
            shootTheMoonTime = 0;
            reversalOfFortuneTime = 0;
        }

        int rechargeTime = 0;
        switch (skill) {
            case ROCK_THROW -> rechargeTime = rockRechargeTime;
            case PAPER_CUT -> rechargeTime = paperRechargeTime;
            case SCISSORS_POKE -> rechargeTime = scissorsRechargeTime;
            case SHOOT_THE_MOON -> rechargeTime = shootTheMoonTime;
            case REVERSAL_OF_FORTUNE -> rechargeTime = reversalOfFortuneTime;
        }
        return rechargeTime;
    }

    /**
     * Calculates current health percentage of a pet.
     * @return returns the pet's current percent of HP.
     */
    @Override
    public double calculateHpPercent() {
        return (currentHP / startingHP) * 100;
    }

    /**
     * @return returns the pet's starting HP.
     */
    @Override
    public double getStartingHp() {
        return startingHP;
    }

    /**
     * called by the game controlling classes.
     * Resets the pet's HP to its starting HP.
     * Resets all skills to what they were at the start of the fight.
     */
    @Override
    public void reset() {
        resetHp();
        awake = true;
    }

    /**
     * Decrements the recharge times of all recharging skills.
     */
    @Override
    public void decrementRechargeTimes() {
        if (rockRechargeTime > 0) {
            rockRechargeTime--;
        }
        if (paperRechargeTime > 0) {
            paperRechargeTime--;
        }
        if (scissorsRechargeTime > 0) {
            scissorsRechargeTime--;
        }
        if (shootTheMoonTime > 0) {
            shootTheMoonTime--;
        }
        if (reversalOfFortuneTime > 0) {
            reversalOfFortuneTime--;
        }
    }

    /**
     * Sets the recharge time for the given skill.
     */
    @Override
    public void setRechargeTime(Skills skill, int rechargeStart) {
        if(skill != null) {
            switch (skill) {
                case SCISSORS_POKE -> scissorsRechargeTime = rechargeStart;
                case PAPER_CUT -> paperRechargeTime = rechargeStart;
                case ROCK_THROW -> rockRechargeTime = rechargeStart;
                case SHOOT_THE_MOON -> shootTheMoonTime = rechargeStart;
                case REVERSAL_OF_FORTUNE -> reversalOfFortuneTime = rechargeStart;
            }
        }
    }

    /**
     * @return returns the formatted output of the pet's name and type.
     */
    @Override
    public String toString(){
        return "Name(" + getPetName() + ") Type(" + getPetType() + ")";
    }

}