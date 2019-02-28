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
public class Match {

    private int matchId;
    private String homeTeam;
    private String awayTeam;
    private Date matchDate;
    private String tossWinner;
    private String battingFirst;

    private Inning inningOne;
    private Inning inningTwo;

    private String homeScore;
    private String awayScore;

    
    private String result;
    private String groundName;
    private int matchType;

    public Match(int matchId, String homeTeam, String awayTeam, Date matchDate, String tossWinner, String battingFirst, Inning one, Inning two, String homeScore, String awayScore, String result, String groundName, int matchType) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.tossWinner = tossWinner;
        this.battingFirst = battingFirst;
        this.inningOne = one;
        this.inningTwo = two;
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
    public Inning getInningOne() {
        return inningOne;
    }
    public void setInningOne(Inning inningOne) {
        this.inningOne = inningOne;
    }
    public Inning getInningTwo() {
        return inningTwo;
    }
    public void setInningTwo(Inning inningTwo) {
        this.inningTwo = inningTwo;
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

}
