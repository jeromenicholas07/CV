/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author DELL
 */
public abstract class OdiMatch implements Comparable<Match>{
    
    
    String matchId;
    String homeTeamId;
    String awayTeamId;
    String matchDate;
    String tossWinner;
    String tossResult;
    String oneId;
    String twoId;
    String homeScore;
    String awayScore;
    String winnerTeam;
    String result;
    String groundName; 

    public OdiMatch(String matchId, String homeTeamId, String awayTeamId, String matchDate, String tossWinner, String tossResult, String oneId, String twoId, String homeScore, String awayScore, String winnerTeam, String result, String groundName) {
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
        this.tossWinner = tossWinner;
        this.tossResult = tossResult;
        this.oneId = oneId;
        this.twoId = twoId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.winnerTeam = winnerTeam;
        this.result = result;
        this.groundName = groundName;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getTossResult() {
        return tossResult;
    }

    public void setTossResult(String tossResult) {
        this.tossResult = tossResult;
    }

    public String getOneId() {
        return oneId;
    }

    public void setOneId(String oneId) {
        this.oneId = oneId;
    }

    public String getTwoId() {
        return twoId;
    }

    public void setTwoId(String twoId) {
        this.twoId = twoId;
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

    public String getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(String winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    @Override
    public String toString() {
        return "OdiMatch{" + "matchId=" + matchId + ", homeTeamId=" + homeTeamId + ", awayTeamId=" + awayTeamId + ", matchDate=" + matchDate + ", tossWinner=" + tossWinner + ", tossResult=" + tossResult + ", oneId=" + oneId + ", twoId=" + twoId + ", homeScore=" + homeScore + ", awayScore=" + awayScore + ", winnerTeam=" + winnerTeam + ", result=" + result + ", groundName=" + groundName + '}';
    }
    
    
    
    
}
