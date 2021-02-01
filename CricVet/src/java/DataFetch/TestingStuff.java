/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetch;

import Database.CricDB;
import models.Header;
import models.OHL;

/**
 *
 * @author Jerome Nicholas
 */
public class TestingStuff {
    public static void main(String[] args){
        CricDB db = new CricDB();
        
        OHL ohlObj = new OHL(new Header(1, 2, 3), new Header(0, 0, 0), new Header(0, 0, 0), new Header(0, 0, 0), new Header(0, 0, 0), new Header(0, 0, 0));
        db.updateOHL(1, ohlObj);
        
        OHL readOhl = db.getOHL(1);
        System.out.println("donee");
    }
}
