/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

public class testInning {
    
    int inningId;
    int noOfParams;
    List<String> params;
    int totalruns;
    int sixes;
    int fours;
    int firstwicket;
    int runs5wicket;
    String winner;


    public testInning(int noOfParams, List<String> params) {
        this.noOfParams = noOfParams;
        this.params = params;
    }
    public testInning(int inningId, int noOfParams, List<String> params) {
        this.inningId = inningId;
        this.noOfParams = noOfParams;
        this.params = params;
    }
    
    public testInning(int inningId,int totalruns,int sixes,int fours,int firstwicket,int runs5wicket, String winner) {
        this.inningId = inningId;
        this.totalruns = totalruns;
        this.sixes = sixes;
        this.fours = fours;
        this.firstwicket = firstwicket;
        this.runs5wicket = runs5wicket;
        this.winner = winner;
    }
    
    
    public int getInningId() {
        return inningId;
    }   
    public void setInningId(int inningId) {
        this.inningId = inningId;
    }

    public int getNoOfParams() {
        return noOfParams;
    }
    public void setNoOfParams(int noOfParams) {
        this.noOfParams = noOfParams;
    }

    public List<String> getParams() {
        

        return params;
        }
    public void setParams(List<String> params) {
        this.params = params;
    }

    public int getTotalruns() {
        return totalruns;
    }

    public void setTotalruns(int totalruns) {
        this.totalruns = totalruns;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getFirstwicket() {
        return firstwicket;
    }

    public void setFirstwicket(int firstwicket) {
        this.firstwicket = firstwicket;
    }

    public int getRuns5wicket() {
        return runs5wicket;
    }

    public void setRuns5wicket(int runs5wicket) {
        this.runs5wicket = runs5wicket;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
    
}
