package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.MainLogic;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class CpuPlayController implements  ControllerInterface{

        MainLogic mainLogic;
        File file;
        String workingDirectory = System.getProperty("user.dir");
        ConsoleOutput consoleOutput;
        DiceRoll diceRoll;
        ComputerPlayer computerPlayer;

        public CpuPlayController(){
            mainLogic = new MainLogic();
            mainLogic.difficulty(Difficulty.Easy); // Generate the random number
            computerPlayer = new ComputerPlayer(mainLogic.getDifficulty(), mainLogic.getRandomInt());
            file = new File(workingDirectory + "/Scores");
            System.out.println(file);
            diceRoll = ComputerPlayer.rollDice();
            consoleOutput = new ConsoleOutput(mainLogic);
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
             //ToDo: All variables should be initialized in te constructor and not in the initialize function
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
                    consoleOutput.tryCounter();
                    consoleOutput.consoleOutputCPU(inputField, scoreVBox, file, diceRoll);
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
                mainLogic.difficulty(Difficulty.Easy);
            }
            else if (id.equals("medium")){
                easyButton.setSelected(false);
                hardButton.setSelected(false);
                mainLogic.difficulty(Difficulty.Medium);
            }
            else{
                easyButton.setSelected(false);
                mediumButton.setSelected(false);
                mainLogic.difficulty(Difficulty.Hard);
            }

            System.out.println(source);
        }
    }
