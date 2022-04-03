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

    private String team;
    private String oppTeam;
    private String toss;
    private String BorC;
    private String result;
    private int totalSixes;

    private Inning one;
    private Inning two;
    private int matchId;

    private String favTeam;
    private String bias;

    private OHL ohl;
    private OverallOHL overallOHL;

    public dbMatch(int matchId, String matchDate, String team, String oppTeam, String toss, String BorC, String result, int totalSixes, Inning one, Inning two, String favTeam, String bias, OHL ohl, OverallOHL overallOHL) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.team = team;
        this.oppTeam = oppTeam;
        this.toss = toss;
        this.BorC = BorC;
        this.result = result;
        this.totalSixes = totalSixes;
        this.one = one;
        this.two = two;

        this.favTeam = favTeam;
        this.bias = bias;
        this.ohl = ohl;
        this.overallOHL = overallOHL;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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

    public String getBias() {
        return bias;
    }

    public void setBias(String bias) {
        this.bias = bias;
    }

    public OHL getOhl() {
        return ohl;
    }

    public void setOhl(OHL ohl) {
        this.ohl = ohl;
    }

    public OverallOHL getOverallOHL() {
        return overallOHL;
    }

    public void setOverallOHL(OverallOHL overallOHL) {
        this.overallOHL = overallOHL;
    }

}
