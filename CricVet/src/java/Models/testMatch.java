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
    private String BCW;

    private Inning one1;
    private Inning two1;
    private Inning one2;
    private Inning two2;

    private String homeScore;
    private String awayScore;
    
    private String teamathome;
    private String teamataway;

    
    private String result;
    private String groundName;
    
    public testMatch(int matchId){
        this.matchId = matchId;
    }

    public testMatch(int matchId, String homeTeam, String awayTeam, Date matchDate, String tossWinner, String BCW, Inning one1, Inning two1,Inning one2, Inning two2, String homeScore, String awayScore, String result, String groundName, String teamathome, String teamataway) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.tossWinner = tossWinner;
        this.BCW = BCW;
        this.one1 = one1;
        this.two1 = two1;
        this.one2 = one2;
        this.two2 = two2;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        
        this.result = result;
        this.groundName = groundName;
        this.teamathome = teamathome;
        this.teamataway = teamataway;
    }

    public String getGroundName() {
        return groundName;
    }
    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }
    public String getteamathome() {
        return teamathome;
    }
    public void setteamathome(String teamathome) {
        this.teamathome = teamathome;
    }
    public String getteamataway() {
        return teamataway;
    }
    public void setteamataway(String teamataway) {
        this.teamataway = teamataway;
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
    public String getBCW() {
        return BCW;
    }
    public void setBCW(String BCW) {
        this.BCW = BCW;
    }
    public Inning getInningOne1() {
        return one1;
    }
    public void setInningOne1(Inning one1) {
        this.one1 = one1;
    }
    public Inning getInningTwo1() {
        return two1;
    }
    public void setInningTwo1(Inning two1) {
        this.two1 = two1;
    }
    public Inning getInningOne2() {
        return one2;
    }
    public void setInningOne2(Inning one2) {
        this.one2 = one2;
    }
    public Inning getInningTwo2() {
        return two2;
    }
    public void setInningTwo2(Inning two2) {
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
    
    @Override
    public String toString() {
        return "testMatch ID: " + matchId ;
    }
    
    

}
