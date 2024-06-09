package gamesettings;

import gameobjects.*;
import types.*;

public class GameSetUp {

    private Settings settings;

    /**
     * Initialize GameSetUp class
     * initialize a settings object to be used for a battle
     */
    public GameSetUp(){
        settings = new Settings();
    }


    /**
     * Creates and returns a new player object
     * @param name the name of the player
     * @param type the type of the player
     * @return
     */
    public Player createNewPlayer(String name, PlayerTypes type){
        return new Player(name, type);
    }

    /**
     * Creates and returns new pet object
     * @param name the name of the pet
     * @param type the type of the pet
     * @param petNum the uid of the pet
     * @param hp the hp of the pet
     * @param ai true if player is a computer & false if human
     * @return the new playable created
     */
    public Playable createNewPet(String name, PetTypes type, int petNum, double hp, boolean ai){
        return new Pet(petNum, name, type, hp, ai);
    }

    /**
     * Sets and returns new seed from random number generator
     * @param seed new seed to be set
     * @return the seed that is now set
     */
    public int setRandomSeed(int seed){
        settings.setRandomSeed(seed);
        return settings.getRandomSeed();
    }

}
