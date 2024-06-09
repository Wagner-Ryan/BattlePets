package main;

import battle.*;
import gameobjects.*;
import gamesettings.*;
import types.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * Main methods that starts and runs the game
     * @param args default parameter for main method
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** BATTLE PETS ***\n");
        System.out.println("Provided are a series of instructions throughout the game:");
        instructions();

        System.out.println("\nYou may start providing input into the game");

        boolean keepPlaying = true;
        while(keepPlaying) {
            String input = scanner.nextLine();
            if(input.equals("start")){
                int gameType = gameOptions(scanner);
                if(gameType == 1){
                    startFight(scanner);
                }
                else if(gameType == 2){
                    startBattle(scanner);
                }
                else if(gameType == 3){
                    startSeason(scanner);
                }
            }
            //else if(input.equals("reset")){
            //    startBattle(scanner);
            //}
            else if(input.equals("quit")){
                quit();
                keepPlaying = false;
            }
            instructions();
        }

        scanner.close();
    }

    /**
     * Prompt the valid game options and
     * get user to input a corresponding integer
     * @param scanner to receive user input
     * @return the corresponding integer to game option
     */
    private static int gameOptions(Scanner scanner) {
        int gameOption = 0;
        boolean validGameOption = false;
        while (!validGameOption) {
            System.out.println("Enter the type of game you want to play");
            System.out.println("1(Exhibition Fight), 2(Battle), 3(Season): ");
            if (scanner.hasNextInt()) {
                gameOption = scanner.nextInt();
                if (gameOption == 1 || gameOption == 2 || gameOption == 3) {
                    validGameOption = true;
                } else {
                    System.out.println("Invalid input. Please enter 1, 2, or 3");
                    scanner.nextLine();
                }
            }
            else {
                checkForGameCommands(scanner);
                System.out.println("Invalid input. Please enter an integer that correlates with the game type to play");
            }
        }
        return gameOption;
    }

    /**
     * Quits the program when called
     */
    private static void quit() {
        System.out.println("Too-da-loo, Please play again soon!");
        System.exit(0);
    }

    /**
     * receives pet info from the user and then
     * creates a fight control object to start a fight
     * @param scanner scanner to retrieve user input
     */
    private static void startFight(Scanner scanner) {
        GameSetUp gameSetUp = new GameSetUp();
        System.out.println("Please provide input for settings for your fight!");

        ArrayList<Playable> pets = gameSetUp(scanner, gameSetUp);

        FightControl fightControl = new FightControl(pets);
    }


    /**
     * receives pet info and number of fights from user and then
     * creates a battle control object to start the battle
     * @param scanner scanner to retrieve user input
     */
    private static void startBattle(Scanner scanner) {
        GameSetUp gameSetUp = new GameSetUp();
        System.out.println("Please provide input for settings for your battle!");

        ArrayList<Playable> pets = gameSetUp(scanner, gameSetUp);
        int numOfFights = getNumOfFights(scanner);

        BattleControl battleControl = new BattleControl(numOfFights, pets);
    }

    /**
     * receives pet info and number of fights from user and then
     * creates a season and calls setBattles() to start season
     * @param scanner scanner to retrieve user input
     */
    private static void startSeason(Scanner scanner) {
        GameSetUp gameSetUp = new GameSetUp();
        System.out.println("Please provide input for settings for your battle!");

        ArrayList<Playable> pets = gameSetUp(scanner, gameSetUp);
        int numOfFights = getNumOfFights(scanner);

        Season season = new Season();
        season.setBattles(pets, numOfFights);
        season.startBattles();
    }


    /**
     * Output certain commands the user can use throughout the game
     */
    private static void instructions() {
        System.out.println(" - Type \"quit\" to quit the game");
        System.out.println(" - Type \"start\" to start the game");
        //System.out.println(" - Type \"reset\" to reset your current game");
    }

    /**
     * Gets info needs to start the game. First gets the random seed to be used
     * then number of players, and the prompts the user to create the players and pets
     * @param scanner scanner to retrieve user input
     * @param gameSetUp object used to set seed, and create players and pets
     * @return an ArrayList of Playable objects (pets) to be used in a battle
     */
    public static ArrayList<Playable> gameSetUp(Scanner scanner, GameSetUp gameSetUp) {
        /**
         * Set random seed for game
         */
        int seed = 0;
        boolean validSeed = false;
        while (!validSeed) {
            System.out.println("Enter the random seed to be used for the game: ");
            if (scanner.hasNextInt()) {
                seed = scanner.nextInt();
                validSeed = true;
            }
            else {
                checkForGameCommands(scanner);
                System.out.println("Invalid input. Please enter an integer for the random seed");
            }
        }
        System.out.println("Random Seed set to: " + gameSetUp.setRandomSeed(seed));

        // Clear nextLine
        scanner.nextLine();

        /**
         * Get how many players in game
         */
        int players = 0;
        boolean validPlayers = false;
        while (!validPlayers) {
            System.out.println("Enter the how many players will be in the game: ");
            if (scanner.hasNextInt()) {
                players = scanner.nextInt();
                if (players > 1) {
                    validPlayers = true;
                } else {
                    System.out.println("Invalid input. Please enter an integer greater than 1");
                }
            } else {
                checkForGameCommands(scanner);
                System.out.println("Invalid input. Please enter an integer for the number of players");
            }
        }
        System.out.println("Number of players in the game: " + players);

        // Clear nextLine
        scanner.nextLine();

        /**
         * Create players
         */
        ArrayList<Player> playerList = new ArrayList<>();
        for (int i = 1; i <= players; i++) {
            System.out.println("Enter the name for Player " + i + ": ");
            String playerName = scanner.nextLine();
            String playerType = "";
            boolean validPlayerType = false;
            while (!validPlayerType) {
                int type = 0;
                System.out.println("Enter the number for the type of Player 1; 1(HUMAN), 2(COMPUTER): ");
                if (scanner.hasNextInt()) {
                    type = scanner.nextInt();
                    if (type == 1 || type == 2) {
                        switch (type) {
                            case 1:
                                playerType = "HUMAN";
                                break;
                            case 2:
                                playerType = "COMPUTER";
                                break;
                        }
                        validPlayerType = true;
                    } else {
                        System.out.println("Invalid input. Please enter 1 or 2 for the type");
                        scanner.nextLine();
                    }
                }
                else {
                    checkForGameCommands(scanner);
                    System.out.println("Invalid input. Please enter an integer that correlates with the type");
                }
            }
            Player temp = gameSetUp.createNewPlayer(playerName, PlayerTypes.valueOf(playerType));
            playerList.add(temp);
            System.out.println("Player " + i + " created as: " + temp);

            scanner.nextLine();
        }

        /**
         * Create petList
         */
        ArrayList<Playable> petList = new ArrayList<>();
        for (int i = 1; i <= players; i++) {
            System.out.println("Player " + i + ", enter the name for Pet " + i + ": ");
            String petName = scanner.nextLine();
            String petType = "";
            boolean validPetType = false;
            while (!validPetType) {
                int type = 0;
                System.out.println("Enter the number for the type of Pet " + i + "; 1(POWER), 2(SPEED), or 3(INTELLIGENCE): ");
                if (scanner.hasNextInt()) {
                    type = scanner.nextInt();
                    if (type >= 1 && type <= 3) {
                        switch (type) {
                            case 1:
                                petType = "POWER";
                                break;
                            case 2:
                                petType = "SPEED";
                                break;
                            case 3:
                                petType = "INTELLIGENCE";
                                break;
                        }
                        validPetType = true;
                    } else {
                        System.out.println("Invalid input. Please enter 1, 2, or 3 for the type");
                        scanner.nextLine();
                    }
                }
                else {
                    checkForGameCommands(scanner);
                    System.out.println("Invalid input. Please enter an integer that correlates with the type");
                }
            }
            double petHp = 0;
            boolean validPetHp = false;
            while (!validPetHp) {
                System.out.println("Enter the starting HP of Pet " + i + ": ");
                if (scanner.hasNextDouble()) {
                    petHp = scanner.nextDouble();
                    validPetHp = true;
                } else {
                    checkForGameCommands(scanner);
                    System.out.println("Invalid input. Please enter a double for the hp");
                    scanner.next();
                }
            }
            scanner.nextLine();
            boolean ai;
            if(playerList.get(i-1).getType() == PlayerTypes.COMPUTER) ai = true;
            else ai = false;
            Playable temp = gameSetUp.createNewPet(petName, PetTypes.valueOf(petType), i, petHp, ai);
            petList.add(temp);
            System.out.println("Pet " + i + " created as: " + temp);
        }
        return petList;
    }

    /**
     * gets the number of fights in a battle from user input
     * @param scanner scanner to retrieve user input
     * @return the number of fights to be in a battle
     */
    public static int getNumOfFights(Scanner scanner){
        int numOfFights = 0;
        boolean validFights = false;
        while (!validFights) {
            System.out.println("Enter the how many fights will be in the battle: ");
            if (scanner.hasNextInt()) {
                numOfFights = scanner.nextInt();
                if(numOfFights > 0) {
                    validFights = true;
                }
                else {
                    System.out.println("Invalid input. Please enter an integer greater than 0");
                    scanner.next();
                }
            }
            else {
                checkForGameCommands(scanner);
                System.out.println("Invalid input. Please enter an integer for the number of fights");
                scanner.next();
            }
        }
        return numOfFights;
    }

    /**
     * Checks the input to determine if it matches any game commands
     * @param scanner retrieves user input
     */
    public static void checkForGameCommands(Scanner scanner){
        String input = scanner.nextLine();
        if(input.equals("quit")) quit();
        if(input.equals("start")) startBattle(scanner);
        if(input.equals("reset")) startBattle(scanner);
    }
}
