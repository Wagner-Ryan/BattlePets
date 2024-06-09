package gameobjects;

import types.*;

public interface Playable
{
	/**
	 * @return Returns the unique id for this playable.  The id is set during construction (in the constructor of the playable).
	 */
	int getPlayableUid();
	
	/**
	 * Sets the playableUid to the id provided.  This should be implemented but not called.
	 * It has special use for Doug's own code.
	 * @param playableUid The unique id to assign to the current playable object
	 */
	void setPlayableUid(int playableUid);

	/**
	 * @return Returns the id of the playable.  This can be assumed to the the index it is stored in in the list.
	 */
	int getPlayableId();

	/**
	 * @return Returns the player's name
	 */
	String getPlayerName();

	/**
	 * @return Returns the pets name
	 */
	String getPetName();

	/**
	 * @return Returns the player's type
	 */
	PlayerTypes getPlayerType();

	/**
	 * @return Returns the pet's type
	 */
	PetTypes getPetType();

	/**
	 * @return Returns the pet's current HP
	 */
	double getCurrentHp();

	/**
	 * The pet will execute logic or prompt the user to choose a skill depending on the pet type
	 * @return Returns the chosen skill
	 */
	Skills chooseSkill();

	/**
	 * This method is called by the game controlling classes to update ther pet's hp based on the damage inflicted
	 * @param hp The hp which will be subtracted from the current hp
	 */
	void updateHp(double hp);

	/**
	 * Resets the pet's current hp to its starting hp
	 */
	void resetHp();


	/**
	 * @return Returns true if the pet's hp > 0, false otherwise
	 */
	boolean isAwake();

	/**
	 * This is a mystery for now...
	 */
	Skills getSkillPrediction();

	/**
	 * @return Returns the current recharge time for the provided skill enumeration
	 */
	int getSkillRechargeTime(Skills skill);

	/**
	 * This is somewhat of a convenience method, since there are methods to get the starting hp and current hp
	 * @return Returns the pet's current percent of hp
	 */
	double calculateHpPercent();

	/**
	 * @return Returns the pet's starting hp
	 */
	double getStartingHp();


	/**
	 * Called by the game controlling classes.
	 * Resets the pet's hp to its starting hp
	 * Resets all skills to what they were at the start of the fight
	 */
	void reset();

	/**
	 * Decrements the recharge times for all recharging skills
	 */
	void decrementRechargeTimes();

	/**
	 * Sets the recharge time for the given skill
	 */
	void setRechargeTime(Skills skill, int rechargeTime);
}
