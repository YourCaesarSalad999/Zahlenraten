package com.lolrewqsda.zahlenraten;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;

public interface ControllerInterface {

    @FXML
    void initialize() throws  Exception;

    @FXML
    void onInput();

    void radioButtonDifficultySetting(ActionEvent event);
}
