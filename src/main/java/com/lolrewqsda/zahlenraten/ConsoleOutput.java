package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.*;
import com.lolrewqsda.zahlenraten.backend.computerplayers.ComputerPlayer;
import com.lolrewqsda.zahlenraten.backend.computerplayers.DiceRoll;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.util.ArrayList;

public abstract class ConsoleOutput{

    Difficulty difficulty;
    int userInput;
    int randomInt;
    int minValue = 0;
    int maxValue;
    int tryCounter;
    DiceRoll currentDiceRoll;
    RandomGenerator randomGenerator = new RandomGenerator();

    protected void setData(VBox scoreVBox, File file){
        ArrayList<XMLEntry> entryArrayList = new ArrayList<>();
            try {
                CreateScoreEntry createScoreEntry = new CreateScoreEntry(scoreVBox, file);
                XMLEntry lastRound;
                if (file.exists()) {
                    XMLLoader xmlLoader = new XMLLoader(file);
                    lastRound = xmlLoader.getLastRound(file);
                }
                else {
                    lastRound = new XMLEntry(0, tryCounter);
                }
                TextFlow textFlow = createScoreEntry.createTextFlow(tryCounter, lastRound.getRound()+1, lastRound.getDate());
                XMLEntry xmlEntry = new XMLEntry(lastRound.getRound() + 1, tryCounter); // +1 is needed because we only get the last round
                if (file.exists()){
                    XMLLoader xmlLoader = new XMLLoader(file);
                    entryArrayList = xmlLoader.loadXML();
                }
                entryArrayList.add(xmlEntry);
                XMLWriter.writeXML(file, entryArrayList);
                createScoreEntry.getBestScore();
                scoreVBox.getChildren().add(textFlow);
                setCounter(0);
                difficulty(difficulty);
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    public void numberBetweenZeroAndHundred(int min, int max){
        randomInt = randomGenerator.generateRandomInt(min, max);
    }

    public void difficulty(Difficulty difficulty){
        numberBetweenZeroAndHundred(minValue, difficulty.numbers - 1);
        this.difficulty = difficulty;
    }

    public void tryCounter(){
        tryCounter++;
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
        return tryCounter;
    }

    public void setCounter(int counter){
        this.tryCounter = counter;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public DiceRoll getCurrentDiceRoll(){
        return currentDiceRoll;
    }
}
