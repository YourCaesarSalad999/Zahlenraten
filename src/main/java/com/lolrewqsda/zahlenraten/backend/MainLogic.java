package com.lolrewqsda.zahlenraten.backend;

import com.lolrewqsda.zahlenraten.backend.computerplayers.DiceRoll;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainLogic {
    protected final RandomGenerator randGen = new RandomGenerator();
    protected int randomInt = 0;
    protected int userInput = 0;
    protected int counter;
    protected Difficulty difficulty;
    protected int minValue = 0;
    protected int maxValue;
    protected DiceRoll currentDiceRoll;

    public MainLogic(){
    }
    public void numberBetweenZeroAndHundred(int min, int max){
        randomInt = randGen.generateRandomInt(min, max);
    }

    public void tryCounter(){
        counter++;
    }

    public void difficulty(Difficulty difficulty){
        numberBetweenZeroAndHundred(minValue, difficulty.numbers - 1);
        this.difficulty = difficulty;
    }

    public int getUserInput(){
        return userInput;
    }

    public void setUserInput(int userInput){
        this.userInput = userInput;
    }

    public int getRandomInt(){
        return randomInt;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

}
