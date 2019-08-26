/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class testMatch {

    private int matchId;
    private String homeTeam;
    private String awayTeam;
    private Date matchDate;
    private String tossWinner;
    private String battingFirst;

    private testInning one1;
    private testInning two1;
    private testInning one2;
    private testInning two2;

    private String homeScore;
    private String awayScore;

    
    private String result;
    private String groundName;
    private int matchType;

    public testMatch(int matchId, String homeTeam, String awayTeam, Date matchDate, String tossWinner, String battingFirst, testInning one1, testInning two1,testInning one2, testInning two2, String homeScore, String awayScore, String result, String groundName, int matchType) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.tossWinner = tossWinner;
        this.battingFirst = battingFirst;
        this.one1 = one1;
        this.two1 = two1;
        this.one2 = one2;
        this.two2 = two2;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        
        this.result = result;
        this.groundName = groundName;
        this.matchType = matchType;
    }

    public String getGroundName() {
        return groundName;
    }
    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }
    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
    public String getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }
    public String getAwayTeam() {
        return awayTeam;
    }
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
    public Date getMatchDate() {
        return matchDate;
    }
    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
    public String getTossWinner() {
        return tossWinner;
    }
    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }
    public String getBattingFirst() {
        return battingFirst;
    }
    public void setBattingFirst(String battingFirst) {
        this.battingFirst = battingFirst;
    }
    public testInning getInningOne1() {
        return one1;
    }
    public void setInningOne1(testInning one1) {
        this.one1 = one1;
    }
    public testInning getInningTwo1() {
        return two1;
    }
    public void setInningTwo1(testInning two1) {
        this.two1 = two1;
    }
    public testInning getInningOne2() {
        return one2;
    }
    public void setInningOne2(testInning one2) {
        this.one2 = one2;
    }
    public testInning getInningTwo2() {
        return two2;
    }
    public void setInningTwo2(testInning two2) {
        this.two2 = two2;
    }
    public String getHomeScore() {
        return homeScore;
    }
    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }
    public String getAwayScore() {
        return awayScore;
    }
    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public int getMatchType() {
        return matchType;
    }
    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    @Override
    public String toString() {
        return "ID: " + matchId + " Date: "+ matchDate.toString() + " BatFirst: "+ battingFirst;
    }
    
    

}
