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
public class OdiInning {
    String inningId;
    int firstOver;	
    int firstTenOvers;
    int lastTenOvers;	
    int firstWicket;	
    int fours;	
    int sixes;

    public OdiInning(String inningId, int firstOver, int firstTenOvers, int lastTenOvers, int firstWicket, int fours, int sixes) {
        this.inningId = inningId;
        this.firstOver = firstOver;
        this.firstTenOvers = firstTenOvers;
        this.lastTenOvers = lastTenOvers;
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

    public int getFirstTenOvers() {
        return firstTenOvers;
    }

    public void setFirstTenOvers(int firstTenOvers) {
        this.firstTenOvers = firstTenOvers;
    }

    public int getLastTenOvers() {
        return lastTenOvers;
    }

    public void setLastTenOvers(int lastTenOvers) {
        this.lastTenOvers = lastTenOvers;
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

    @Override
    public String toString() {
        return "OdiInning{" + "inningId=" + inningId + ", firstOver=" + firstOver + ", firstTenOvers=" + firstTenOvers + ", lastTenOvers=" + lastTenOvers + ", firstWicket=" + firstWicket + ", fours=" + fours + ", sixes=" + sixes + '}';
    }

    
    
}
