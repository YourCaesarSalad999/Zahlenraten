package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.MainLogic;
import com.lolrewqsda.zahlenraten.backend.WrongInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.xml.sax.SAXException;
import com.lolrewqsda.zahlenraten.ConsoleOutput;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class HelloController {
    MainLogic mainLogic;
    File file;
    String workingDirectory = System.getProperty("user.dir");
    ConsoleOutput consoleOutput;

    public HelloController(){
        mainLogic = new MainLogic();
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
        mainLogic.difficulty(Difficulty.Easy);
        consoleOutput = new ConsoleOutput(mainLogic);
    }
    @FXML
    protected void onInput(){
        try {
            consoleOutput.setUserInput(Integer.parseInt(inputField.getText()));
            consoleOutput.tryCounter();
            consoleOutput.consoleOutputSolo(inputField, scoreVBox, file);
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
