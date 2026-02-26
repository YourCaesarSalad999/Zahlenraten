package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.*;
import com.lolrewqsda.zahlenraten.backend.computerplayers.ComputerPlayer;
import com.lolrewqsda.zahlenraten.backend.computerplayers.DiceRoll;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ConsoleOutput extends MainLogic{

    public ConsoleOutput(MainLogic mainLogic){
        this.difficulty = mainLogic.getDifficulty();
        this.userInput = mainLogic.getUserInput();
        this.randomInt = mainLogic.getRandomInt();
        this.counter = mainLogic.getCounter();
        this.minValue = 0;
        this.maxValue = difficulty.numbers;
    }

    //ToDo: consoleOutput function should be split into multiple smaller functions so code reuse is possible

    public void consoleOutputSolo(TextField textField, VBox scoreVBox, File file) {

        if ((userInput > maxValue || userInput < minValue)){
            throw new WrongInputException("Number must be between " + minValue + " and " + maxValue);
        }
        if (userInput < randomInt){
            System.out.println("zu klein");
            textField.setText("zu klein");
        }
        else if (userInput > randomInt){
            System.out.println("zu groß");
            textField.setText("zu groß");
        }
        else {
            setData(scoreVBox, file);
            System.out.println("Gratulation du hast " + counter + " Versuche gebraucht!");
            textField.setText("Gratulation du hast " + counter + " Versuche gebraucht.");
        }
    }
    public void consoleOutputCPU(TextField textField, VBox scoreVBox, File file, DiceRoll diceRoll){
        if ((userInput > maxValue || userInput < minValue)){
            throw new WrongInputException("Number must be between " + minValue + " and " + maxValue);
        }
        if (userInput < randomInt){
            if (diceRoll == DiceRoll.Player) {
                System.out.println("zu klein");
                textField.setText("zu klein");
                this.currentDiceRoll = DiceRoll.AI;
            }
            else {
                System.out.println("AI guessed an too small number");
                textField.setText("AI guessed an too small number.");
                this.currentDiceRoll = DiceRoll.Player;
            }
            ComputerPlayer.rollDice();
        }
        else if (userInput > randomInt){
            if (diceRoll == DiceRoll.Player) {
                System.out.println("zu groß");
                textField.setText("zu groß");
                this.currentDiceRoll = DiceRoll.AI;
            }
            else {
                System.out.println("AI guessed an too big number");
                textField.setText("AI guessed an too big number.");
                this.currentDiceRoll = DiceRoll.Player;
            }
        }
        else {
            if (diceRoll == DiceRoll.Player) {
                setData(scoreVBox, file);
                System.out.println("Gratulation du hast " + counter + " Versuche gebraucht!");
                textField.setText("Gratulation du hast " + counter + " Versuche gebraucht.");
            }
            else {
                System.out.println("AI guessed the right number and won");
                textField.setText("AI guessed the right number and won");
                this.currentDiceRoll = DiceRoll.GameOver;
            }
        }
    }
    private void setData(VBox scoreVBox, File file){
        ArrayList<XMLEntry> entryArrayList = new ArrayList<>();
            try {
                CreateScoreEntry createScoreEntry = new CreateScoreEntry(scoreVBox, file);
                XMLEntry lastRound;
                if (file.exists()) {
                    XMLLoader xmlLoader = new XMLLoader(file);
                    lastRound = xmlLoader.getLastRound(file);
                }
                else {
                    lastRound = new XMLEntry(0, counter);
                }
                TextFlow textFlow = createScoreEntry.createTextFlow(counter, lastRound.getRound()+1, lastRound.getDate());
                XMLEntry xmlEntry = new XMLEntry(lastRound.getRound() + 1, counter); // +1 is needed because we only get the last round
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
    public DiceRoll getCurrentDiceRoll(){
        return currentDiceRoll;
    }
}
