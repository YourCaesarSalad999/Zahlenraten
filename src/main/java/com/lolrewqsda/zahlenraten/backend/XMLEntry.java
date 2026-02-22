package com.lolrewqsda.zahlenraten.backend;
import java.util.Date;

public class XMLEntry {
    long round;
    long tries;
    Date date;
    /// Use for creating new entries
    public XMLEntry(long round, long tries){
        this.round = round;
        this.tries = tries;
        this.date = new Date();
    }
    /// Use to load already existing entries
    public XMLEntry(long tries, long round, Date date){
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
    public Date getDate(){
        return date;
    }
}
