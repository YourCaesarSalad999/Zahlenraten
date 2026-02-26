package com.lolrewqsda.zahlenraten.backend.computerplayers;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.RandomGenerator;

public class ComputerPlayer {
    int minValue = 0;
    int maxValue;
    int randomNumber;
    int guessedValue = 0;
    int lastGuess;
    boolean firstGuess = true;
    Difficulty difficulty;
    public ComputerPlayer(Difficulty difficulty, int randomNumber){
        this.difficulty = difficulty;
        this.randomNumber = randomNumber;
        this.maxValue = difficulty.numbers;
    };
    private  enum InfluenceFlags{
        none, toLarge, toSmall, firstGuess
    }

    private int getRandomNumber(InfluenceFlags influenceFlags){
        RandomGenerator randomGenerator = new RandomGenerator();
        if (influenceFlags == InfluenceFlags.toLarge){
            return randomGenerator.generateRandomInt(minValue, lastGuess - 1);
        }
        else if (influenceFlags == InfluenceFlags.toSmall) {
            return randomGenerator.generateRandomInt(lastGuess + 1, maxValue);
        }
        else if (influenceFlags == InfluenceFlags.none){
            return  randomGenerator.generateRandomInt(minValue, maxValue);
        }
        else if (influenceFlags == InfluenceFlags.firstGuess){
            firstGuess = false;
            return maxValue / 2;
        }
        else {
            return  randomGenerator.generateRandomInt(minValue, maxValue);
        }
    }

    public int binarySearch(){
        lastGuess = guessedValue;
        if (firstGuess) {
            guessedValue = getRandomNumber(InfluenceFlags.firstGuess);
        }
        else if (guessedValue < randomNumber){
            guessedValue = getRandomNumber(InfluenceFlags.toSmall);
        }
        else if (guessedValue > randomNumber){
            guessedValue = getRandomNumber(InfluenceFlags.toLarge);
        }
        if (guessedValue == randomNumber) {
            System.out.println("Computer found the correct number!");
            return guessedValue;
        }
        return guessedValue;
    }
    public static DiceRoll rollDice(){
        RandomGenerator randomGenerator = new RandomGenerator();
        if (randomGenerator.generateRandomInt(0, 1) == 1){
            return DiceRoll.Player;
        }
        else{
            return DiceRoll.AI;
        }
    }
}
