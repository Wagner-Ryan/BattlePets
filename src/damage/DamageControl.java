package damage;

import gameobjects.*;
import gamesettings.*;
import types.*;

/**
 * The DamageControl Class contains all damage calculations.
 */
public class DamageControl
{
    private final Damage damage;
    private final double SHOOT_MOON_DAM = 20.0;
    private double totalRandomDamageDealt;
    private double tempTRDD;
    private double totalRandomDamageTaken;
    private Skills currentSkillPet1 = null;
    private Skills currentSkillPet2 = null;


    /**
     * Default constructor for the DamageControl class.
     * Crates a new Damage Object.
     */
    public DamageControl() {
        damage = new Damage(0.0, 0.0);
    }

    /**
     * This setter stores the current skill choices of the attacking and attacked pet.
     * @param currentSkillPet1 current skill of attacking pet
     * @param currentSkillPet2 current skill of attacked pet
     */
    public void setCurrentSkill(Skills currentSkillPet1, Skills currentSkillPet2) {
        this.currentSkillPet1 = currentSkillPet1;
        this.currentSkillPet2 = currentSkillPet2;
    }

    /**
     * Getter that accesses the damage object and returns randomDamage of type double.
     * @return randomDamage random damage that attacking pet deals
     */
    public double getRandomDamage()
    {
        return damage.getRandomDamage();
    }

    /**
     * Getter that accesses the damage object and returns conditionalDamage of type double.
     * @return conditionalDamage conditional damage that the attacking pet deals
     */
    public double getConditionalDamage()
    {
        return damage.getConditionalDamage();
    }

    /**
     * Getter that accesses the damage object and returns the sum of the random and conditional
     * damages by calling calculateTotalDamage.
     * @return totalDamage the sum of the random and conditional damage that the attacking pet deals
     */
    public double getTotalDamage()
    {
        return damage.calculateTotalDamage();
    }

    /**
     * Setter that keeps track of the total amount of random damage that a pet has received.
     * @param randomDamageTaken the amount of random damage a pet has received on a given turn
     */
    public void setRandomDamageTaken(double randomDamageTaken)
    {
        totalRandomDamageTaken += randomDamageTaken;
    }

    /**
     * Getter that returns the random damage taken - the random damage dealt.
     * @return the total randomDamage taken - the total randomDamage dealt by a pet as a double.
     */
    public double getRandomDamageDifference()
    {
        return totalRandomDamageTaken - totalRandomDamageDealt;
    }

    /**
     * Takes two Playable objects and calculates the randomDamage
     * and the conditionalDamage of the attacking pet
     * @param pet1 the attacking pet
     * @param pet2 the pet being attacked
     */
    public void calculateTotalDamage(Playable pet1, Playable pet2)
    {
        calculateRandomDamage();
        calculateConditionalDamage(pet1, pet2);
    }

    /**
     * Calculates the amount of random damage that a given pet will deal this turn.
     * The random damage a double between 0.0 and 5.0 that is randomized by the seed
     * that the user inputs.
     */
    public void calculateRandomDamage()
    {
        double randomDamage;
        double maxRand = 5.0;
        double minRand = 0.0;

        if (currentSkillPet1 == Skills.REVERSAL_OF_FORTUNE)
        {
            tempTRDD = totalRandomDamageDealt;
            double rofDamage = reversalOfFortune();
            randomDamage = minRand + (maxRand - minRand) * Settings.random.nextDouble();
            randomDamage += rofDamage;
        }
        else{
            randomDamage = minRand + (maxRand - minRand) * Settings.random.nextDouble();
        }
        totalRandomDamageDealt += randomDamage;
        damage.setRandomDamage(randomDamage);

    }


    /**
     * Takes two Pet objects and calls a method to calculate the conditionalDamage
     * based on the PetType. Then sets the conditionalDamage to the value
     * retrieved from the method.
     * @param pet1 the attacking pet
     * @param pet2 the attacked pet
     */
    public void calculateConditionalDamage(Playable pet1, Playable pet2)
    {
        double conditionDamage = 0.0;
        if (pet1.getPetType() == PetTypes.POWER)
            conditionDamage = powerDamage(pet1);
        else if (pet1.getPetType() == PetTypes.SPEED)
            conditionDamage = speedDamage(pet1, pet2);
        else if (pet1.getPetType() == PetTypes.INTELLIGENCE)
            conditionDamage = intelligenceDamage(pet1, pet2);
        damage.setConditionalDamage(conditionDamage);
    }

    /**
     * Calculates the difference in random damage. Takes the total random
     * damage a pet has received - the total random damage a pet has dealt.
     * @return the difference between a pet's random damage received and given in type double
     */
    public double reversalOfFortune()
    {
        return totalRandomDamageTaken - tempTRDD;
    }

    /**
     * Takes in a Playable object and calculates the conditionalDamage that
     * would occur if it is of Type POWER.
     * @param pet1 the attacking pet
     * @return conditionalDamage conditional damage that the attacking pet does as a double
     */
    public double powerDamage(Playable pet1)
    {
        double condDam = 0.0;
        double powerMultiplier = 5.0;
        if (currentSkillPet1 == Skills.ROCK_THROW)
        {
            if (currentSkillPet2 == Skills.SCISSORS_POKE)
            {
                condDam = damage.getRandomDamage() * powerMultiplier;
            }
        }
        else if (currentSkillPet1 == Skills.PAPER_CUT)
        {
            if (currentSkillPet2 == Skills.ROCK_THROW)
            {
                condDam = damage.getRandomDamage() * powerMultiplier;
            }
        }
        else if (currentSkillPet1 == Skills.SCISSORS_POKE)
        {
            if (currentSkillPet2 == Skills.PAPER_CUT)
            {
                condDam = damage.getRandomDamage() * powerMultiplier;
            }
        }
        else if (currentSkillPet1 == Skills.SHOOT_THE_MOON)
        {
            Skills skill = pet1.getSkillPrediction();
            if (skill == currentSkillPet2)
            {
                condDam = SHOOT_MOON_DAM;
            }
        }
        else if (currentSkillPet1 == Skills.REVERSAL_OF_FORTUNE)
        {
            condDam = reversalOfFortune();
        }
        return condDam;
    }

    /**
     * Takes in two Playable objects and calculates the conditionalDamage that
     * would occur if the first inputted Playable is of Type SPEED.
     * @param pet1 the attacking pet
     * @param pet2 the attacked pet
     * @return conditionalDamage conditional damage that the attacking pet does as a double
     */
    public double speedDamage(Playable pet1, Playable pet2)
    {
        double condDam = 0.0;
        double speedDam = 12.5;
        int highPercent = 75;
        int lowPercent = 25;
        if (currentSkillPet1 == Skills.ROCK_THROW)
        {
            if ((currentSkillPet2 == Skills.SCISSORS_POKE) || (currentSkillPet2 == Skills.PAPER_CUT))
            {
                if (pet2.calculateHpPercent() >= highPercent)
                {
                    condDam = speedDam;
                }
            }
        }
        else if (currentSkillPet1 == Skills.PAPER_CUT)
        {
            if ((currentSkillPet2 == Skills.SCISSORS_POKE) || (currentSkillPet2 == Skills.ROCK_THROW))
            {
                if ((pet2.calculateHpPercent() < lowPercent))
                {
                    condDam = speedDam;
                }
            }
        }
        else if (currentSkillPet1 ==
                Skills.SCISSORS_POKE)
        {
            if ((currentSkillPet2 == Skills.PAPER_CUT) || (currentSkillPet2 == Skills.ROCK_THROW))
            {
                if ((pet2.calculateHpPercent() < highPercent) && (pet2.calculateHpPercent() >= lowPercent))
                {
                    condDam = speedDam;
                }
            }
        }
        else if (currentSkillPet1 == Skills.SHOOT_THE_MOON)
        {
            Skills skill = pet1.getSkillPrediction();
            if (skill == currentSkillPet2)
            {
                condDam = SHOOT_MOON_DAM;
            }
        }
        else if (currentSkillPet1 == Skills.REVERSAL_OF_FORTUNE)
        {
            condDam = reversalOfFortune();
        }
        return condDam;
    }

    /**
     * Takes in two Playable objects and calculates the conditionalDamage that
     * would occur if the first inputted Playable is of Type INTELLIGENCE.
     * @param pet1 the attacking pet
     * @param pet2 the attacked pet
     * @return conditionalDamage conditional damage that the attacking pet does as a double
     */
    public double intelligenceDamage(Playable pet1, Playable pet2)
    {
        double condDam = 0.0;
        double intelDamLow = 2.0;
        double intelDamHigh = 3.0;
        if (currentSkillPet1 == Skills.ROCK_THROW)
        {
            if (pet2.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
            {
                condDam = intelDamHigh;
            }
            else if (pet2.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
            {
                condDam = intelDamLow;
            }
            if (pet2.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
            {
                condDam =  condDam + intelDamLow;
            }
        }
        else if (currentSkillPet1 == Skills.PAPER_CUT)
        {
            if (pet2.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
            {
                condDam = intelDamHigh;
            }
            else if (pet2.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
            {
                condDam = intelDamLow;
            }
            if (pet2.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
            {
                condDam =  condDam + intelDamLow;
            }
        }
        else if (currentSkillPet1 == Skills.SCISSORS_POKE)
        {
            if (pet2.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
            {
                condDam = intelDamHigh;
            }
            else if (pet2.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
            {
                condDam = intelDamLow;
            }
            if (pet2.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
            {
                condDam =  condDam + intelDamLow;
            }
        }
        else if (currentSkillPet1 == Skills.SHOOT_THE_MOON)
        {
            Skills skill = pet1.getSkillPrediction();
            if (skill == currentSkillPet2)
            {
                condDam = SHOOT_MOON_DAM;
            }
        }
        else if (currentSkillPet1 == Skills.REVERSAL_OF_FORTUNE)
        {
            condDam = reversalOfFortune();
        }
        return condDam;
    }
}



