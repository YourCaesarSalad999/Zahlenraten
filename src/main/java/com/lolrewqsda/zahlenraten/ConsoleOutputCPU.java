package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.WrongInputException;
import com.lolrewqsda.zahlenraten.backend.computerplayers.ComputerPlayer;
import com.lolrewqsda.zahlenraten.backend.computerplayers.DiceRoll;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;

public class ConsoleOutputCPU extends ConsoleOutput{

    int tryCountCPU;

    public ConsoleOutputCPU(Difficulty difficulty){
        this.difficulty = difficulty;
        this.maxValue = difficulty.numbers;
    }

    public void consoleOutput(TextField textField, VBox scoreVBox, File file, DiceRoll diceRoll){
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
                System.out.println("Gratulation du hast " + tryCounter + " Versuche gebraucht!");
                textField.setText("Gratulation du hast " + tryCounter + " Versuche gebraucht.");
            }
            else {
                System.out.println("AI guessed the right number and won, AI needed " + tryCountCPU + " turns.");
                textField.setText("AI guessed the right number and won, Ai needed " + tryCountCPU + " turns.");
                this.currentDiceRoll = DiceRoll.GameOver;
            }
        }
    }

    public void tryCounter(DiceRoll currentPlayer){
        if (currentPlayer == DiceRoll.Player){
            tryCounter();
        }
        else {
            tryCountCPU++;
        }
    }

}
