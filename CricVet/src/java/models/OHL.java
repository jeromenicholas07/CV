/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

public class OHL implements Serializable {

    private Header FW;
    private Header FX;
    private Header LX;
    private Header T;
    private Header FW2;
    private Header FX2;

    public OHL(Header FW, Header FX, Header LX, Header T, Header FW2, Header FX2) {
        this.FW = FW;
        this.FX = FX;
        this.LX = LX;
        this.T = T;
        this.FW2 = FW2;
        this.FX2 = FX2;
    }

    public Header getFW() {
        return FW;
    }

    public void setFW(Header FW) {
        this.FW = FW;
    }

    public Header getFX() {
        return FX;
    }

    public void setFX(Header FX) {
        this.FX = FX;
    }

    public Header getLX() {
        return LX;
    }

    public void setLX(Header LX) {
        this.LX = LX;
    }

    public Header getT() {
        return T;
    }

    public void setT(Header T) {
        this.T = T;
    }

    public Header getFW2() {
        return FW2;
    }

    public void setFW2(Header FW2) {
        this.FW2 = FW2;
    }

    public Header getFX2() {
        return FX2;
    }

    public void setFX2(Header FX2) {
        this.FX2 = FX2;
    }

    public boolean isEmpty() {
        return FW.isEmpty() && FX.isEmpty() && LX.isEmpty() && T.isEmpty() && FW2.isEmpty() && FX2.isEmpty();
    }

    public Header getHeader(boolean isFirstInning, int pIndex) {
        if (isFirstInning) {
            switch (pIndex) {
                case 1:
                    return FX;
                case 2:
                    return LX;
                case 3:
                    return FW;
                case 6:
                    return T;
            }
        } else {
            switch (pIndex) {
                case 1:
                    return FX2;
                case 3:
                    return FW2;
            }
        }
        return null;
    }

    public Header getTestHeader(int pIndex) {
        return null;
    }

}
