package com.lolrewqsda.zahlenraten.backend.computerplayers;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.RandomGenerator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ComputerPlayer {
    int minValue = 0;
    int maxValue;
    int randomNumber;
    int guessedValue = 0;
    ArrayList<ComputerTry> guessedValues = new ArrayList<>();
    int lastGuess;
    boolean firstGuess = true;
    Difficulty difficulty;
    int triesCounter;
    public ComputerPlayer(Difficulty difficulty, int randomNumber){
        this.difficulty = difficulty;
        this.randomNumber = randomNumber;
        this.maxValue = difficulty.numbers;
    }

    private int getRandomNumber(InfluenceFlags influenceFlags){
        RandomGenerator randomGenerator = new RandomGenerator();
        if (influenceFlags == InfluenceFlags.toLarge){
            if (triesCounter >= 4) {
                int searchedGuess = guessedValues(lastGuess, influenceFlags);
                return randomGenerator.generateRandomInt(searchedGuess + 1, lastGuess - 1);
            }
            else {
                triesCounter++;
                guessedValues.add(new ComputerTry(lastGuess, influenceFlags));
                return randomGenerator.generateRandomInt(minValue, lastGuess);
            }
        }
        else if (influenceFlags == InfluenceFlags.toSmall) {
            if (triesCounter >= 4) {
                int searchedGuess = guessedValues(lastGuess, influenceFlags);
                return randomGenerator.generateRandomInt(lastGuess + 1, searchedGuess - 1);
            }
            else {
                triesCounter++;
                guessedValues.add(new ComputerTry(lastGuess, influenceFlags));
                return  randomGenerator.generateRandomInt(lastGuess, maxValue);
            }
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
    public int guessedValues(int lastGuess, InfluenceFlags influenceFlags){
        guessedValues.add(new ComputerTry(lastGuess, influenceFlags));
        if (influenceFlags == InfluenceFlags.toSmall) {
            try {
                return guessedValues.stream().filter(i -> i.influenceFlag == InfluenceFlags.toLarge).mapToInt(ComputerTry::getNumber).min().orElseThrow();
            }
            catch (NoSuchElementException e){
                System.out.println("Stream could not find any element matching given rules");
                return -1;
            }
            catch (IllegalArgumentException e){
                System.out.println("error at too small");
                return -1;
            }
        }
        else if (influenceFlags == InfluenceFlags.toLarge){
            try {
                return guessedValues.stream().filter(i -> i.influenceFlag == InfluenceFlags.toSmall).mapToInt(ComputerTry::getNumber).max().orElseThrow();
            }
            catch (NoSuchElementException e){
                System.out.println("stream could not find any element matching given rules");
                return -1;
            }
            catch (IllegalArgumentException e){
                System.out.println("error at too big");
                return -1;
            }
        }
        else {
            return -1;
        }
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
