/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Jerome Nicholas
 */
public class Header implements Serializable {
    private double open;
    private double high;
    private double low;

    public Header(double open, double high, double low) {
        this.open = open;
        this.high = high;
        this.low = low;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }
    
    public boolean isEmpty(){
        return open == 0 && high == 0 && low == 0;
    }
    
}
