package gameobjects;

import types.*;

/**
 * Class that handles the creation and handling of Player variables and attributes.
 * @author Eli Brown
 */
public class Player {
    private Playable pet;
    private PlayerTypes type;
    private String name;

    /**
     * Default constructor for Player class.
     * @param name
     * @param type
     */
    public Player(String name, PlayerTypes type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Getter that returns pet of type Playable.
     * @return returns the player's pet.
     */
    public Playable getPlayer() {
        return pet;
    }

    /**
     * Setter to set current pet.
     */
    public void setPlayable(Playable pet) {
        this.pet = pet;
    }

    /**
     * Getter to get the Player's type.
     * @return returns the type of the Player.
     */
    public PlayerTypes getType() {
        return type;
    }

    /**
     * Setter that sets the player type.
     * @return returns player's type
     */
    public PlayerTypes setType(PlayerTypes type) {
        this.type = type;
        return type;
    }

    /**
     * Getter that returns name of type String.
     * @return returns the Player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Setter that sets the player name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns the formatted version of the player's name and type.
     */
    @Override
    public String toString(){
        return "Name(" + getName() + ") Type(" + getType() + ")";
    }
}
