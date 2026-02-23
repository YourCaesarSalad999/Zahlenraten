package com.lolrewqsda.zahlenraten.backend;


import com.lolrewqsda.zahlenraten.CreateScoreEntry;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainLogic {
    private final RandomGenerator randGen = new RandomGenerator();
    private int randomInt = 0;
    private int userInput = 0;
    private int counter;
    public enum Difficulty{
       Easy, Medium, Hard
    }
    Difficulty difficulty;

    public MainLogic(){
    }
    public void numberBetweenZeroAndHundred(int min, int max){
        randomInt = randGen.generateRandomInt(min, max);
    }


    public void consoleOutput(TextField textField, VBox scoreVBox, File file) throws Exception {
        ArrayList<XMLEntry> entryArrayList = new ArrayList<>();

        if (userInput < randomInt){
            System.out.println("zu klein");
            textField.setText("zu klein");
        }
        else if (userInput > randomInt){
            System.out.println("zu groß");
            textField.setText("zu groß");
        }
        else {
            try {
                CreateScoreEntry createScoreEntry = new CreateScoreEntry(scoreVBox, file);
                XMLEntry lastRound;
                if (file.exists()) {
                    XMLLoader xmlLoader = new XMLLoader(file);
                    lastRound = xmlLoader.getLastRound(file); // +1 is needed because we only get the last round
                }
                else {
                    lastRound = new XMLEntry(1, counter);
                }
                System.out.println("Gratulation du hast " + counter + " Versuche gebraucht!");
                textField.setText("Gratulation du hast " + counter + " Versuche gebraucht.");
                TextFlow textFlow = createScoreEntry.createTextFlow(counter, lastRound.getRound()+1, lastRound.getDate());
                XMLEntry xmlEntry = new XMLEntry(lastRound.getRound() + 1, counter);
                if (file.exists()){
                    XMLLoader xmlLoader = new XMLLoader(file);
                    entryArrayList = xmlLoader.loadXML();
                }
                entryArrayList.add(xmlEntry);
                XMLWriter.writeXML(file, entryArrayList);
                createScoreEntry.getBestScore();
                scoreVBox.getChildren().add(textFlow);
                counter = 0;
                difficulty(difficulty);
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            catch (Exception e){
                CreateScoreEntry createScoreEntry = new CreateScoreEntry(scoreVBox, file);
                long lastRound = 1;
                System.out.println("Gratulation du hast" + counter + " Versuche gebraucht!");
                TextFlow textFlow = createScoreEntry.createTextFlow(counter, lastRound, new Date());
            }


        }
    }
    public void tryCounter(){
        counter++;
    }

    public void difficulty(Difficulty difficulty){
        if (difficulty == Difficulty.Easy){
            numberBetweenZeroAndHundred(0, 49);
        }
        else if (difficulty == Difficulty.Medium){
            numberBetweenZeroAndHundred(0, 99);
        }
        else{
            numberBetweenZeroAndHundred(0, 499);
        }

        this.difficulty = difficulty;
    }
    public int giveOutUserInput(){
        return userInput;
    }

    public void setUserInput(int userInput){
        this.userInput = userInput;
    }

}
