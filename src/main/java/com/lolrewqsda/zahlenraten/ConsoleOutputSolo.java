package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.WrongInputException;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;

public class ConsoleOutputSolo extends ConsoleOutput {

    public ConsoleOutputSolo(Difficulty difficulty){
        this.difficulty = difficulty;
        this.maxValue = difficulty.numbers;
    }

    public void consoleOutput(TextField textField, VBox scoreVBox, File file) {

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
            System.out.println("Gratulation du hast " + tryCounter + " Versuche gebraucht!");
            textField.setText("Gratulation du hast " + tryCounter + " Versuche gebraucht.");
        }
    }
}
