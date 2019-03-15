/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author DELL
 */
public class dbMatch {
    private String matchDate;
    private String oppTeam;
    private String toss;
    private String BorC;
    private String result;
    private int totalSixes;
    
    private Inning one;
    private Inning two;

    public dbMatch(String matchDate, String oppTeam, String toss, String BorC, String result, int totalSixes, Inning one, Inning two) {
        this.matchDate = matchDate;
        this.oppTeam = oppTeam;
        this.toss = toss;
        this.BorC = BorC;
        this.result = result;
        this.totalSixes = totalSixes;
        this.one = one;
        this.two = two;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    
    public String getOppTeam() {
        return oppTeam;
    }

    public void setOppTeam(String oppTeam) {
        this.oppTeam = oppTeam;
    }

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }

    public String getBorC() {
        return BorC;
    }

    public void setBorC(String BorC) {
        this.BorC = BorC;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTotalSixes() {
        return totalSixes;
    }

    public void setTotalSixes(int totalSixes) {
        this.totalSixes = totalSixes;
    }

    public Inning getOne() {
        return one;
    }

    public void setOne(Inning one) {
        this.one = one;
    }

    public Inning getTwo() {
        return two;
    }

    public void setTwo(Inning two) {
        this.two = two;
    }

    
    
    }
