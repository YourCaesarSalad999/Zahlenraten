package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.WrongInputException;
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


public class HelloController {
    File file;
    String workingDirectory = System.getProperty("user.dir");
    ConsoleOutputSolo consoleOutput;

    public HelloController(){
        consoleOutput = new ConsoleOutputSolo(Difficulty.Easy);
        file = new File(workingDirectory + "/Scores");
        System.out.println(file);
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

    // constructor replacement
    @FXML
    protected void initialize() throws Exception {
        scoreVBox.prefWidthProperty().bind(scoreScrollPane.viewportBoundsProperty().map(Bounds::getWidth));
        scoreVBox.prefHeightProperty().bind(scoreScrollPane.viewportBoundsProperty().map(Bounds::getHeight));
        CreateScoreEntry scoreEntry = new CreateScoreEntry(scoreVBox, file);
        if (file.exists()) {
            scoreEntry.getBestScore();
            scoreEntry.loadData(scoreVBox);
        }
        inputField.prefHeightProperty().bind(mainGridPane.heightProperty().multiply(0.2));
        inputField.prefWidthProperty().bind(mainGridPane.widthProperty().multiply(0.2));
        consoleOutput.difficulty(Difficulty.Easy);
    }
    @FXML
    protected void onInput(){
        try {
            consoleOutput.setUserInput(Integer.parseInt(inputField.getText()));
            consoleOutput.tryCounter();
            consoleOutput.consoleOutput(inputField, scoreVBox, file);
            inputField.selectAll();
        }
        catch (IllegalArgumentException e){
            inputField.setText("Not an Integer");
            inputField.selectAll();
        }
        catch (WrongInputException e){
            inputField.setText("Number must be between given range");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void radioButtonDifficultySetting(ActionEvent event){
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
