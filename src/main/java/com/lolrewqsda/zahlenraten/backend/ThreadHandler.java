package com.lolrewqsda.zahlenraten.backend;

import com.lolrewqsda.zahlenraten.backend.computerplayers.ComputerPlayer;
import javafx.scene.control.TextField;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ThreadHandler {
    public static void aiIsThinking(Consumer<Integer> consumer, TextField textField, ComputerPlayer computerPlayer) {
        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(1);
        textField.setEditable(false);
        ses.schedule(() -> {
            System.out.println("AI is thinking...");
            textField.setText("AI is thinking...");
        }, 1, TimeUnit.SECONDS);
        ses.schedule(() ->{
            Integer guessedValue = computerPlayer.binarySearch();
            consumer.accept(guessedValue);
            ses.shutdown();
        }, 3, TimeUnit.SECONDS);
    }
}
