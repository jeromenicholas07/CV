/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.Inning;
import Models.Match;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author DELL
 */
public class TestDB {
    
    public static void main(String[] args) throws Exception
    {
        
        CricDB db = new CricDB();
        String teamName = "Pakistan";
        List <Match> matches = db.getMatches(teamName);
        List<Inning> batRecords_In1 = new ArrayList<>();
        List<Inning> bowlRecords_In1 =new ArrayList<>();
        List<Inning> batRecords_In2 = new ArrayList<>();
        List<Inning> bowlRecords_In2 =new ArrayList<>();
        List <Match> bat = new ArrayList<>();
        List <Match> chase = new ArrayList<>();
        Inning tempIn1;
        Inning tempIn2;
        int i =0, j=0, k=0;
        Match temp;
        Match temp1;
       
        Collections.sort(matches, new Comparator<Match>() 
          {
                DateFormat f = new SimpleDateFormat("MMM dd yyyy");
                @Override
                public int compare(Match o1, Match o2) {
                    try {
                        return f.parse(o1.getMatchDate()).compareTo(f.parse(o2.getMatchDate()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
         });
        Collections.reverse(matches);
        while(i< matches.size() && i<11)
        {   temp =  matches.get(i);
        //figure out if ka logic
            if(temp.getTossResult().contains("chase") && !temp.getTossWinner().contains(teamName))// || ((!temp.getTossWinner().equals("South Africa")&&temp.getTossResult().equals("chase"))) )
            {   System.out.println("iN THA IFF");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat.add(temp1);
             
                
            }
         else if(temp.getTossWinner().contains(teamName)&&temp.getTossResult().contains("bat"))
            {   temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat.add(temp1);
             
                
            }
            else 
            {   System.out.println("iN ELSE BROTHA");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               chase.add(temp1);
               
            }
          
            
            
             System.out.println(matches.get(i).getHomeTeam() +" "+ matches.get(i).getMatchDate());
             Inning in =  db.getInning(matches.get(i).getOneId());
             System.out.println(in.getFirstOver());
             i++;
        }
        
        while(k< bat.size() && k<6)
        {
            tempIn1 = db.getInning(bat.get(k).getOneId());
            batRecords_In1.add(tempIn1);
            tempIn2 = db.getInning(bat.get(k).getTwoId());
            bowlRecords_In2.add(tempIn2);
            
            k++;
            
        }
        
        while(j< chase.size() && j<6)
        {
            tempIn1 = db.getInning(chase.get(j).getOneId());
            bowlRecords_In1.add(tempIn1);
            
            tempIn2 = db.getInning(chase.get(j).getTwoId());
            batRecords_In2.add(tempIn2);
            j++;
            
        }
        j=0;
        while( j< batRecords_In1.size() )
        {
            //System.out.println(bowlRecords_In1.get(j).getFirstFiveOvers()+"||"+bowlRecords_In2.get(j).getFirstFiveOvers());
            System.out.println(bat.get(j).getMatchDate()+"||" +bat.get(j).getWinnerTeam()+"||"+ batRecords_In1.get(j).getFirstFiveOvers()+"||"+bowlRecords_In2.get(j).getFirstFiveOvers()+"||" +bat.get(j).getTossWinner()+"||" +bat.get(j).getTossResult());
            j++;
        }
        j=0;
        while( j< batRecords_In2.size() )
        {
            System.out.println(Integer.toString(batRecords_In2.size())+"||"+ bowlRecords_In1.get(j).getFirstFiveOvers()+"||"+batRecords_In2.get(j).getFirstFiveOvers()+"||" +chase.get(j).getTossWinner()+"||" +chase.get(j).getTossResult());
            //System.out.println(bat.get(j).getMatchDate()+"||" +bat.get(j).getWinnerTeam()+"||"+ batRecords_In1.get(j).getFirstFiveOvers()+"||"+bowlRecords_In2.get(j).getFirstFiveOvers()+"||" +bat.get(j).getTossWinner()+"||" +bat.get(j).getTossResult());
            j++;
        }
        
        
    }
   
}
