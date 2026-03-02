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

    @Test
    void reset() {
        computerPlayer.lastGuess = 42;
        computerPlayer.firstGuess = false;
        computerPlayer.triesCounter = 4;
        computerPlayer.guessedValues.add(new ComputerTry(20, InfluenceFlags.toSmall));
        computerPlayer.reset();
        assertEquals(0, computerPlayer.lastGuess);
        assertTrue(computerPlayer.firstGuess);
        assertEquals(0, computerPlayer.triesCounter);
        assertTrue(computerPlayer.guessedValues.isEmpty());
    }

    @Test
    void guessedValues() {
        computerPlayer.triesCounter = 4;
        computerPlayer.guessedValues.add(new ComputerTry(25, InfluenceFlags.toSmall));
        computerPlayer.guessedValues.add(new ComputerTry(40, InfluenceFlags.toLarge));
        computerPlayer.guessedValues.add(new ComputerTry(30, InfluenceFlags.toSmall));
        computerPlayer.guessedValues.add(new ComputerTry(45, InfluenceFlags.toLarge));
        int test = computerPlayer.guessedValues(35, InfluenceFlags.toSmall);
        assertEquals(40, test);
        test = computerPlayer.guessedValues(50, InfluenceFlags.toLarge);
        assertEquals(35, test);
    }

    @Test
    void rollDice() {
        DiceRoll test = ComputerPlayer.rollDice();
        assertTrue(test.equals(DiceRoll.Player) || test.equals(DiceRoll.AI));
    }
}