package gameobjects;

import types.*;

/**
 * Enum that contains all the potential Skills that a pet can perform during a fight.
 * @author Eli Brown
 */
public enum Skills
{
	ROCK_THROW,
	SCISSORS_POKE,
	PAPER_CUT,
	SHOOT_THE_MOON,
	REVERSAL_OF_FORTUNE;

	/**
	 * @return returns the String formatted version of the Skill.
	 */
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}

}
