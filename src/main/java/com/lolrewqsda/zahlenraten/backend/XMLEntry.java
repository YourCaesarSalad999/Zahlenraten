package com.lolrewqsda.zahlenraten.backend;
import java.time.ZonedDateTime;

public class XMLEntry {
    long round;
    long tries;
    ZonedDateTime date;
    /// Use for creating new entries
    public XMLEntry(long round, long tries){
        this.round = round;
        this.tries = tries;
        this.date = ZonedDateTime.now();
    }
    /// Use to load already existing entries
    public XMLEntry(long tries, long round, ZonedDateTime date){
        this.tries = tries;
        this.round = round;
        this.date = date;
    }
    public long getTries(){
        return tries;
    }
    public long getRound(){
        return round;
    }
    public ZonedDateTime getDate(){
        return date;
    }
}
