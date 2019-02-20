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
public class Inning {
    
    String inningId;
    int firstOver;	
    int firstFiveOvers;
    int lastFiveOvers;	
    int firstWicket;	
    int fours;	
    int sixes;
    
    public Inning(String inningId, int firstOver, int firstFiveOvers, int lastFiveOvers, int firstWicket, int fours, int sixes) {
        this.inningId = inningId;
        this.firstOver = firstOver;
        this.firstFiveOvers = firstFiveOvers;
        this.lastFiveOvers = lastFiveOvers;
        this.firstWicket = firstWicket;
        this.fours = fours;
        this.sixes = sixes;
    }

    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public int getFirstOver() {
        return firstOver;
    }

    public void setFirstOver(int firstOver) {
        this.firstOver = firstOver;
    }

    public int getFirstFiveOvers() {
        return firstFiveOvers;
    }

    public void setFirstFiveOvers(int firstFiveOvers) {
        this.firstFiveOvers = firstFiveOvers;
    }

    public int getLastFiveOvers() {
        return lastFiveOvers;
    }

    public void setLastFiveOvers(int lastFiveOvers) {
        this.lastFiveOvers = lastFiveOvers;
    }

    public int getFirstWicket() {
        return firstWicket;
    }

    public void setFirstWicket(int firstWicket) {
        this.firstWicket = firstWicket;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }
    

  
}
