package com.lolrewqsda.zahlenraten.backend;

public enum Difficulty{
    Easy(50), Medium(100), Hard(500);

    public final Integer numbers;

    private Difficulty(Integer numbers){
        this.numbers = numbers;
    }
}
