package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.ThreadHandler;
import com.lolrewqsda.zahlenraten.backend.WrongInputException;
import com.lolrewqsda.zahlenraten.backend.computerplayers.ComputerPlayer;
import com.lolrewqsda.zahlenraten.backend.computerplayers.DiceRoll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.function.Consumer;


public class CpuPlayController implements  ControllerInterface{

        File file;
        String workingDirectory = System.getProperty("user.dir");
        ConsoleOutputCPU consoleOutput;
        DiceRoll diceRoll;
        ComputerPlayer computerPlayer;

        public CpuPlayController(){
            consoleOutput = new ConsoleOutputCPU(Difficulty.Easy);
            consoleOutput.difficulty(Difficulty.Easy); // Generate the random number
            computerPlayer = new ComputerPlayer(consoleOutput.getDifficulty(), consoleOutput.getRandomInt());
            file = new File(workingDirectory + "/Scores");
            System.out.println(file);
            diceRoll = ComputerPlayer.rollDice();
        }

        @FXML
        private GridPane mainGridPane;

        @FXML
        private TextField inputField;

        @FXML
        private VBox scoreVBox;

        @FXML
        private RadioButton easyButton;

        @FXML
        protected RadioButton mediumButton;

        @FXML
        private  RadioButton hardButton;

        @FXML
        private ScrollPane scoreScrollPane;

        Consumer<Integer> aiGuessedNumberConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
                inputField.setText(integer.toString());
                onInput();
            }
        };

        // constructor replacement
        @FXML
        @Override
        public void initialize() throws Exception {
            scoreVBox.prefWidthProperty().bind(scoreScrollPane.viewportBoundsProperty().map(Bounds::getWidth));
            scoreVBox.prefHeightProperty().bind(scoreScrollPane.viewportBoundsProperty().map(Bounds::getHeight));
            CreateScoreEntry scoreEntry = new CreateScoreEntry(scoreVBox, file);
            if (file.exists()) {
                scoreEntry.getBestScore();
                scoreEntry.loadData(scoreVBox);
            }
            inputField.prefHeightProperty().bind(mainGridPane.heightProperty().multiply(0.2));
            inputField.prefWidthProperty().bind(mainGridPane.widthProperty().multiply(0.2));
            manageTurn();
        }

        public void manageTurn(){
            if (diceRoll == DiceRoll.AI){
                ThreadHandler.aiIsThinking(aiGuessedNumberConsumer ,inputField, computerPlayer);
            }
            else if (diceRoll == DiceRoll.GameOver){
                System.out.println("Game is over");
            }
            else {
                inputField.setEditable(true);
            }
        }

        @FXML
        @Override
        public void onInput(){
                try {
                    consoleOutput.setUserInput(Integer.parseInt(inputField.getText()));
                    consoleOutput.tryCounter(diceRoll);
                    consoleOutput.consoleOutput(inputField, scoreVBox, file, diceRoll);
                    inputField.selectAll();
                    diceRoll = consoleOutput.getCurrentDiceRoll();
                    manageTurn();
                } catch (IllegalArgumentException e) {
                    inputField.setText("Not an Integer");
                    inputField.selectAll();
                } catch (WrongInputException e) {
                    inputField.setText("Number must be between given range");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        @FXML
        @Override
        public void radioButtonDifficultySetting(ActionEvent event){
            Object source =  event.getSource();
            String id = ((Node) source).getId();
            if (id.equals("easy")){
                mediumButton.setSelected(false);
                hardButton.setSelected(false);
                consoleOutput.difficulty(Difficulty.Easy);
            }
            else if (id.equals("medium")){
                easyButton.setSelected(false);
                hardButton.setSelected(false);
                consoleOutput.difficulty(Difficulty.Medium);
            }
            else{
                easyButton.setSelected(false);
                mediumButton.setSelected(false);
                consoleOutput.difficulty(Difficulty.Hard);
            }

            System.out.println(source);
        }
    }
