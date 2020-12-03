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
    private int matchId;
    
    private String favTeam;
    private String open1;
    private String high1;
    private String low1;
    private String open2;
    private String high2;
    private String low2;

    public dbMatch(int matchId, String matchDate, String oppTeam, String toss, String BorC, String result, int totalSixes, Inning one, Inning two, String favTeam, String open1, String high1, String low1, String open2, String high2, String low2) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.oppTeam = oppTeam;
        this.toss = toss;
        this.BorC = BorC;
        this.result = result;
        this.totalSixes = totalSixes;
        this.one = one;
        this.two = two;
        
        this.favTeam = favTeam;
        this.open1 = open1;
        this.high1 = high1;
        this.low1 = low1;
        this.open2 = open2;
        this.high2 = high2;
        this.low2 = low2;
    }
    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
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

    public String getFavTeam() {
        return favTeam;
    }

    public void setFavTeam(String favTeam) {
        this.favTeam = favTeam;
    }

    public String getOpen1() {
        return open1;
    }

    public String getHigh1() {
        return high1;
    }

    public String getLow1() {
        return low1;
    }

    public String getOpen2() {
        return open2;
    }

    public String getHigh2() {
        return high2;
    }

    public String getLow2() {
        return low2;
    }

    
    
    
    
    }
