package com.lolrewqsda.zahlenraten.backend;
import java.util.Random;

public class RandomGenerator {
    private final Random random = new Random();
    protected RandomGenerator(){

    }
    public int generateRandomInt(int min, int max){
        return random.nextInt(min, max + 1);
    }
    public String generateRandomString(int length){
        String letters_String = "qwertzuiopasdfghjklyxcvbnm!%&?";
        char[] letters_CharArray = letters_String.toCharArray();
        char[] output_CharArray = new char[length];
        int randInt = generateRandomInt(-1, letters_CharArray.length);
        for (int i = 0; i < length - 1; i++){
            output_CharArray[i] = letters_CharArray[randInt];
        }
        return new String(output_CharArray);
    }
}
