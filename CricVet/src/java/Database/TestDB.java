/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.Inning;
import Models.Match;
import Models.OdiInning;
import Models.OdiMatch;
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
           
        String teamName1 =  "England";
        String teamName2 =  "Bangladesh";
        String groundName = "Kensington Oval, Bridgetown, Barbados";
        List <OdiMatch> matches_1 = db.getOdiMatches(teamName1);
        List <OdiMatch> matches_2 = db.getOdiMatches(teamName2);
        List <OdiMatch> groundMatches = db.getOdiGroundData(groundName);
        
      
        List<OdiInning> batRecords_In1_1 = new ArrayList<>();
        List<OdiInning> bowlRecords_In1_1 =new ArrayList<>();
        List<OdiInning> batRecords_In2_1 = new ArrayList<>();
        List<OdiInning> bowlRecords_In2_1 =new ArrayList<>();
       
        
        
        
         List <OdiMatch> bat_1 = new ArrayList<>();
        List <OdiMatch> chase_1 = new ArrayList<>();
        List <String> runScoredBat_In1_1 = new ArrayList<>();
        List <String> runScoredBat_In2_1 = new ArrayList<>();
        List <String> runGivenBowl_In1_1 = new ArrayList<>();
        List <String> runGivenBowl_In2_1 = new ArrayList<>();
        
        
        List<OdiInning> batRecords_In1_2 = new ArrayList<>();
        List<OdiInning> bowlRecords_In1_2 =new ArrayList<>();
        List<OdiInning> batRecords_In2_2 = new ArrayList<>();
        List<OdiInning> bowlRecords_In2_2 =new ArrayList<>();
        List <OdiMatch> bat_2 = new ArrayList<>();
        List <OdiMatch> chase_2 = new ArrayList<>();
        List <String> runScoredBat_In1_2 = new ArrayList<>();
        List <String> runScoredBat_In2_2 = new ArrayList<>();
        List <String> runGivenBowl_In1_2 = new ArrayList<>();
        List <String> runGivenBowl_In2_2 = new ArrayList<>();
        List <String> groundData_In1= new ArrayList<>();
        List <String> groundData_In2= new ArrayList<>();
        
        OdiInning tempIn1;
        OdiInning tempIn2;
        int i=0, j=0, k=0;
        OdiMatch temp;
        OdiMatch temp1;
       
        
        //sorting ground matches
        Collections.sort(groundMatches, new Comparator<OdiMatch>() 
          {
                DateFormat f = new SimpleDateFormat("MMM dd yyyy");
               

            @Override
            public int compare(OdiMatch o1, OdiMatch o2) {
                try {
                        return f.parse(o1.getMatchDate()).compareTo(f.parse(o2.getMatchDate()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
            }
         });
        Collections.reverse(groundMatches);
         while(i< groundMatches.size() && i<5)
        {   
            temp =  groundMatches.get(i);
            if(temp.getTossWinner().contains(temp.getHomeTeamId()) && temp.getTossResult().contains("bat"))
            {
                groundData_In1.add(temp.getHomeScore());
                groundData_In2.add(temp.getAwayScore());
                
            }
            else if(temp.getTossWinner().contains(temp.getHomeTeamId()) && temp.getTossResult().contains("chase"))
            {
                groundData_In2.add(temp.getHomeScore());
                groundData_In1.add(temp.getAwayScore());
            }
            else if(temp.getTossWinner().contains(temp.getAwayTeamId()) && temp.getTossResult().contains("bat"))
            {
                groundData_In2.add(temp.getHomeScore());
                groundData_In1.add(temp.getAwayScore());
            }
            else if(temp.getTossWinner().contains(temp.getAwayTeamId()) && temp.getTossResult().contains("chase"))
            {
                groundData_In1.add(temp.getHomeScore());
                groundData_In2.add(temp.getAwayScore());
            }
            i++;
        }
        
        Collections.sort(matches_1, new Comparator<OdiMatch>() 
          {
                DateFormat f = new SimpleDateFormat("MMM dd yyyy");
                @Override
                public int compare(OdiMatch o1, OdiMatch o2) {
                    try {
                        return f.parse(o1.getMatchDate()).compareTo(f.parse(o2.getMatchDate()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
         });
        Collections.reverse(matches_1);
        i =0; j=0; k=0;
        while(i< matches_1.size() && i<11)
        {   temp =  matches_1.get(i);
        //figure out if ka logic
            if(temp.getTossResult().contains("chase") && !temp.getTossWinner().contains(teamName1))// || ((!temp.getTossWinner().equals("South Africa")&&temp.getTossResult().equals("chase"))) )
            {   System.out.println("iN THA IFF");
                temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_1.add(temp1);
             
                
            }
         else if(temp.getTossWinner().contains(teamName1)&&temp.getTossResult().contains("bat"))
            {   temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_1.add(temp1);
             
                
            }
            else 
            {   System.out.println("iN ELSE BROTHA");
                temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               chase_1.add(temp1);
               
            }
          
            
            
             System.out.println(matches_1.get(i).toString());
             OdiInning in =  db.getOdiInning(matches_1.get(i).getOneId());
             System.out.println(in.getFirstOver());
             i++;
        }
         i =0 ;
        while(i< matches_1.size())
        {
            System.out.println(matches_1.get(i).toString());
            i++;
        }
        i=0;
        while(i< matches_2.size())
        {
            System.out.println(matches_2.get(i).toString());
            i++;
        }
        i=0;
        while(i< groundMatches.size())
        {
            System.out.println(groundMatches.get(i).toString());
            i++;
        }
        
        while(k< bat_1.size() && k<5)
        {
            tempIn1 = db.getOdiInning(bat_1.get(k).getOneId());
            batRecords_In1_1.add(tempIn1);
            tempIn2 = db.getOdiInning(bat_1.get(k).getTwoId());
            bowlRecords_In2_1.add(tempIn2);
            if(bat_1.get(k).getHomeTeamId().contains(teamName1))
            {
                runScoredBat_In1_1.add(bat_1.get(k).getHomeScore());
                runGivenBowl_In2_1.add(bat_1.get(k).getAwayScore());
            }
            else
            {
                runScoredBat_In1_1.add(bat_1.get(k).getAwayScore());
                runGivenBowl_In2_1.add(bat_1.get(k).getHomeScore());
            }
            k++;
            
        }
        
        while(j< chase_1.size() && j<5)
        {
            tempIn1 = db.getOdiInning(chase_1.get(j).getOneId());
            bowlRecords_In1_1.add(tempIn1);
            
            tempIn2 = db.getOdiInning(chase_1.get(j).getTwoId());
            batRecords_In2_1.add(tempIn2);
            if(chase_1.get(j).getHomeTeamId().contains(teamName1))
            {
               
                 runScoredBat_In2_1.add(chase_1.get(j).getHomeScore());
                runGivenBowl_In1_1.add(chase_1.get(j).getAwayScore());
            }
            else
            {
                runScoredBat_In2_1.add(chase_1.get(j).getAwayScore());
                runGivenBowl_In1_1.add(chase_1.get(j).getHomeScore());
                
            }
            j++;
            
        }
        
        //Second TEAm Data
        i =0; j=0; k=0;
        
         Collections.sort(matches_2, new Comparator<OdiMatch>() 
          {
                DateFormat f = new SimpleDateFormat("MMM dd yyyy");
                @Override
                public int compare(OdiMatch o1, OdiMatch o2) {
                    try {
                        return f.parse(o1.getMatchDate()).compareTo(f.parse(o2.getMatchDate()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
         });
        Collections.reverse(matches_2);
        while(i< matches_2.size() && i<11)
        {   temp =  matches_2.get(i);
        //figure out if ka logic
            if(temp.getTossResult().contains("chase") && !temp.getTossWinner().contains(teamName2))// || ((!temp.getTossWinner().equals("South Africa")&&temp.getTossResult().equals("chase"))) )
            {   System.out.println("iN THA IFF");
                temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_2.add(temp1);
             
                
            }
         else if(temp.getTossWinner().contains(teamName2)&&temp.getTossResult().contains("bat"))
            {   temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_2.add(temp1);
             
                
            }
            else 
            {   System.out.println("IN ELSE BROTHA");
                temp1 = new OdiMatch(temp.getMatchId(), temp.getHomeTeamId(), temp.getAwayTeamId(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               chase_2.add(temp1);
               
            }
          
            
            
             System.out.println(matches_2.get(i).toString());
             OdiInning in =  db.getOdiInning(matches_2.get(i).getOneId());
             System.out.println(in.getFirstOver());
             i++;
        }
        
        while(k< bat_2.size() && k<5)
        {
            tempIn1 = db.getOdiInning(bat_2.get(k).getOneId());
            batRecords_In1_2.add(tempIn1);
            tempIn2 = db.getOdiInning(bat_2.get(k).getTwoId());
            bowlRecords_In2_2.add(tempIn2);
            if(bat_2.get(k).getHomeTeamId().contains(teamName2))
            {
                runScoredBat_In1_2.add(bat_2.get(k).getHomeScore());
                runGivenBowl_In2_2.add(bat_2.get(k).getAwayScore());
            }
            else
            {
                runScoredBat_In1_2.add(bat_2.get(k).getAwayScore());
                runGivenBowl_In2_2.add(bat_2.get(k).getHomeScore());
            }
            k++;
            
        }
        
        while(j< chase_2.size() && j<5)
        {
            tempIn1 = db.getOdiInning(chase_2.get(j).getOneId());
            bowlRecords_In1_2.add(tempIn1);
            
            tempIn2 = db.getOdiInning(chase_2.get(j).getTwoId());
            batRecords_In2_2.add(tempIn2);
            if(chase_2.get(j).getHomeTeamId().contains(teamName2))
            {
               
                 runScoredBat_In2_2.add(chase_2.get(j).getHomeScore());
                runGivenBowl_In1_2.add(chase_2.get(j).getAwayScore());
            }
            else
            {
                runScoredBat_In2_2.add(chase_2.get(j).getAwayScore());
                runGivenBowl_In1_2.add(chase_2.get(j).getHomeScore());
                
            }
            j++;
            
        }
        
        i=0;
        while(i< batRecords_In1_1.size())
        {
            System.out.println(batRecords_In1_1.get(i).toString());
            i++;
        }
        
        printhis( batRecords_In1_1);
         printhis( batRecords_In1_2);
          printhis( batRecords_In2_1);
           printhis( batRecords_In2_2);
            printhis( bowlRecords_In1_1);
             printhis( bowlRecords_In1_2);
              printhis( bowlRecords_In2_1);
               printhis( bowlRecords_In2_2);
             
       
        
        
        
        
        /*String teamName = "Pakistan";
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
        */
        
    }
    
    public static void printhis(List<OdiInning> list){
        int j=0;
         while( j< list.size() )
        {
            System.out.println(Integer.toString(list.size()));
            //System.out.println(bat.get(j).getMatchDate()+"||" +bat.get(j).getWinnerTeam()+"||"+ batRecords_In1.get(j).getFirstFiveOvers()+"||"+bowlRecords_In2.get(j).getFirstFiveOvers()+"||" +bat.get(j).getTossWinner()+"||" +bat.get(j).getTossResult());
            j++;
        }
    }
   
}
