package models;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author j
 */
public class OverallOHL implements Serializable {

    private Header inning1;
    private Header inning2;

    public OverallOHL(Header inning1, Header inning2) {
        this.inning1 = inning1;
        this.inning2 = inning2;
    }

    public Header getInning1() {
        return inning1;
    }

    public void setInning1(Header inning1) {
        this.inning1 = inning1;
    }

    public Header getInning2() {
        return inning2;
    }

    public void setInning2(Header inning2) {
        this.inning2 = inning2;
    }

}