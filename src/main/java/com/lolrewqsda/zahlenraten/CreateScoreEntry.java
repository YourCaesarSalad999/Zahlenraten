package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.XMLLoader;
import com.lolrewqsda.zahlenraten.backend.XMLWriter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.lolrewqsda.zahlenraten.backend.XMLEntry;

public class CreateScoreEntry {
    VBox vBox;
    File file;
    ArrayList<XMLEntry> writableArray;
    XMLLoader xmlLoader;
    public CreateScoreEntry(VBox vBox, File file) throws ParserConfigurationException, IOException, SAXException {
        this.vBox = vBox;
        this.file = file;
        this.xmlLoader = new XMLLoader(this.file);
        // check if the file exists and load the data into the ArrayList
        if (file.exists()) {
            this.writableArray = xmlLoader.loadXML();
        }
        // create a new ArrayList if not
        else {
            this.writableArray = new ArrayList<>();
        }
    }

    public void loadData(VBox vBox) throws Exception {
        XMLLoader xmlLoader = new XMLLoader(file);
        ArrayList<XMLEntry> xmlEntries = xmlLoader.loadXML();
        for (XMLEntry entry : xmlEntries){
            long round = entry.getRound();
            long score = entry.getTries();
            Date date = entry.getDate();
            vBox.getChildren().add(createTextFlow(score, round, date));
        }
    }

    public TextFlow createTextFlow(long counter, long round, Date date) {
        try {
            TextFlow textFlow = new TextFlow();
            Text text = new Text();
            text.setText("Versuche: " + counter + " in Runde: " + round + " gebraucht" + " am: " + date);
            textFlow.getChildren().add(text);
            textFlow.prefHeightProperty().bind(vBox.heightProperty().multiply(0.025));
            textFlow.prefWidthProperty().bind(vBox.widthProperty().multiply(0.025));
            textFlow.setStyle("-fx-background-color: #bcfd4c; -fx-border-width: 1; -fx-border-color: #d0fe81");
            return textFlow;
        }
        catch (Exception e){
            System.out.println("Error creating textflow");
            e.printStackTrace();
            return null;
        }
    }

    public void getBestScore() throws Exception {
        ArrayList<Long> rounds = new ArrayList<>();
        XMLLoader xmlLoader = new XMLLoader(file);
        ArrayList<XMLEntry> xmlEntries = xmlLoader.loadXML();
        for (XMLEntry entry : xmlEntries){
            rounds.add(entry.getTries());
        }
        Collections.sort(rounds);
        long bestScore = rounds.getFirst();
        TextFlow textFlow = new TextFlow();
        Text text = new Text();
        text.setText("Dein bester versuch ist " + bestScore + " Versuche!");
        textFlow.getChildren().add(text);
        textFlow.prefHeightProperty().bind(vBox.heightProperty().multiply(0.025));
        textFlow.prefWidthProperty().bind(vBox.widthProperty().multiply(0.025));
        textFlow.setStyle("-fx-background-color: #bcfd4c; -fx-border-width: 1; -fx-border-color: #d0fe81");
        vBox.getChildren().add(textFlow);
    }

}
