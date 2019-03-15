/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetch;

import Database.CricDB;

/**
 *
 * @author DELL
 */
public class temp {

    public static void main(String[] args) {

        CricDB db = new CricDB();
        

        System.out.println(db.getMatches("Australia", 2, 2).size());
    }

}
