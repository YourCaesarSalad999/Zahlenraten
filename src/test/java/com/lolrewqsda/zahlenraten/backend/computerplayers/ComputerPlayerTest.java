package com.lolrewqsda.zahlenraten.backend.computerplayers;

import com.lolrewqsda.zahlenraten.backend.Difficulty;
import com.lolrewqsda.zahlenraten.backend.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {
    int randomInt;
    ComputerPlayer computerPlayer;

    @BeforeEach
    void setUp() {
        RandomGenerator randomGenerator = new RandomGenerator();
        randomInt = randomGenerator.generateRandomInt(0, 50);
        computerPlayer = new ComputerPlayer(Difficulty.Easy, randomInt);
    }

    @Test
    void binarySearch() {
        int tries = 0;
        int guessedNumber;
        do {
            guessedNumber = computerPlayer.binarySearch();
            tries++;
        } while (guessedNumber != randomInt);
        System.out.println("AI needed " + tries + " tries");
        assertEquals(randomInt, guessedNumber);
    }
}