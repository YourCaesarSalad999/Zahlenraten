package com.lolrewqsda.zahlenraten.backend.computerplayers;

public class ComputerTry {
    InfluenceFlags influenceFlag;
    int number;
    ComputerTry(int number, InfluenceFlags influenceFlag){
        this.influenceFlag = influenceFlag;
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
