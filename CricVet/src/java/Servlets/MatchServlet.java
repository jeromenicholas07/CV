/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import Models.Inning;
import Models.Match;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet(name = "MatchServlet", urlPatterns = {"/MatchServlet"})
public class MatchServlet extends HttpServlet {

     CricDB db = new CricDB();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
    
        
        String teamName1 =  request.getParameter("teamName1");
        String teamName2 =  request.getParameter("teamName2");
        String groundName = request.getParameter("groundName");
        List <Match> matches_1 = db.getMatches(teamName1);
        List <Match> matches_2 = db.getMatches(teamName2);
        List <Match> groundMatches = db.getGroundData(groundName);
        List<Inning> batRecords_In1_1 = new ArrayList<>();
        List<Inning> bowlRecords_In1_1 =new ArrayList<>();
        List<Inning> batRecords_In2_1 = new ArrayList<>();
        List<Inning> bowlRecords_In2_1 =new ArrayList<>();
        List <Match> bat_1 = new ArrayList<>();
        List <Match> chase_1 = new ArrayList<>();
        List <String> runScoredBat_In1_1 = new ArrayList<>();
        List <String> runScoredBat_In2_1 = new ArrayList<>();
        List <String> runGivenBowl_In1_1 = new ArrayList<>();
        List <String> runGivenBowl_In2_1 = new ArrayList<>();
        
        
        List<Inning> batRecords_In1_2 = new ArrayList<>();
        List<Inning> bowlRecords_In1_2 =new ArrayList<>();
        List<Inning> batRecords_In2_2 = new ArrayList<>();
        List<Inning> bowlRecords_In2_2 =new ArrayList<>();
        List <Match> bat_2 = new ArrayList<>();
        List <Match> chase_2 = new ArrayList<>();
        List <String> runScoredBat_In1_2 = new ArrayList<>();
        List <String> runScoredBat_In2_2 = new ArrayList<>();
        List <String> runGivenBowl_In1_2 = new ArrayList<>();
        List <String> runGivenBowl_In2_2 = new ArrayList<>();
        List <String> groundData_In1= new ArrayList<>();
        List <String> groundData_In2= new ArrayList<>();
        
        Inning tempIn1;
        Inning tempIn2;
        int i =0, j=0, k=0;
        Match temp;
        Match temp1;
       
        
        //sorting ground matches
        Collections.sort(groundMatches, new Comparator<Match>() 
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
        Collections.reverse(groundMatches);
         while(i< groundMatches.size() && i<5)
        {   
            temp =  groundMatches.get(i);
            if(temp.getTossWinner().contains(temp.getHomeTeam()) && temp.getTossResult().contains("bat"))
            {
                groundData_In1.add(temp.getHomeScore());
                groundData_In2.add(temp.getAwayScore());
                
            }
            else if(temp.getTossWinner().contains(temp.getHomeTeam()) && temp.getTossResult().contains("chase"))
            {
                groundData_In2.add(temp.getHomeScore());
                groundData_In1.add(temp.getAwayScore());
            }
            else if(temp.getTossWinner().contains(temp.getAwayTeam()) && temp.getTossResult().contains("bat"))
            {
                groundData_In2.add(temp.getHomeScore());
                groundData_In1.add(temp.getAwayScore());
            }
            else if(temp.getTossWinner().contains(temp.getAwayTeam()) && temp.getTossResult().contains("chase"))
            {
                groundData_In1.add(temp.getHomeScore());
                groundData_In2.add(temp.getAwayScore());
            }
            i++;
        }
        
        Collections.sort(matches_1, new Comparator<Match>() 
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
        Collections.reverse(matches_1);
        i =0; j=0; k=0;
        while(i< matches_1.size() && i<11)
        {   temp =  matches_1.get(i);
        //figure out if ka logic
            if(temp.getTossResult().contains("chase") && !temp.getTossWinner().contains(teamName1))// || ((!temp.getTossWinner().equals("South Africa")&&temp.getTossResult().equals("chase"))) )
            {   System.out.println("iN THA IFF");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_1.add(temp1);
             
                
            }
         else if(temp.getTossWinner().contains(teamName1)&&temp.getTossResult().contains("bat"))
            {   temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_1.add(temp1);
             
                
            }
            else 
            {   System.out.println("iN ELSE BROTHA");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               chase_1.add(temp1);
               
            }
          
            
            
             System.out.println(matches_1.get(i).getHomeTeam() +" "+ matches_1.get(i).getMatchDate());
             Inning in =  db.getInning(matches_1.get(i).getOneId());
             System.out.println(in.getFirstOver());
             i++;
        }
        
        while(k< bat_1.size() && k<5)
        {
            tempIn1 = db.getInning(bat_1.get(k).getOneId());
            batRecords_In1_1.add(tempIn1);
            tempIn2 = db.getInning(bat_1.get(k).getTwoId());
            bowlRecords_In2_1.add(tempIn2);
            if(bat_1.get(k).getHomeTeam().contains(teamName1))
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
            tempIn1 = db.getInning(chase_1.get(j).getOneId());
            bowlRecords_In1_1.add(tempIn1);
            
            tempIn2 = db.getInning(chase_1.get(j).getTwoId());
            batRecords_In2_1.add(tempIn2);
            if(chase_1.get(j).getHomeTeam().contains(teamName1))
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
        
         Collections.sort(matches_2, new Comparator<Match>() 
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
        Collections.reverse(matches_2);
        while(i< matches_2.size() && i<11)
        {   temp =  matches_2.get(i);
        //figure out if ka logic
            if(temp.getTossResult().contains("chase") && !temp.getTossWinner().contains(teamName2))// || ((!temp.getTossWinner().equals("South Africa")&&temp.getTossResult().equals("chase"))) )
            {   System.out.println("iN THA IFF");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_2.add(temp1);
             
                
            }
         else if(temp.getTossWinner().contains(teamName2)&&temp.getTossResult().contains("bat"))
            {   temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               bat_2.add(temp1);
             
                
            }
            else 
            {   System.out.println("IN ELSE BROTHA");
                temp1 = new Match(temp.getMatchId(), temp.getHomeTeam(), temp.getAwayTeam(), temp.getMatchDate(), temp.getTossWinner(), temp.getTossResult(), temp.getOneId(), temp.getTwoId(), temp.getHomeScore(), temp.getAwayScore(), temp.getWinnerTeam(), temp.getResult(), temp.getGroundName()) {
                        @Override
                        public int compareTo(Match o) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
               chase_2.add(temp1);
               
            }
          
            
            
             System.out.println(matches_2.get(i).getHomeTeam() +" "+ matches_2.get(i).getMatchDate());
             Inning in =  db.getInning(matches_2.get(i).getOneId());
             System.out.println(in.getFirstOver());
             i++;
        }
        
        while(k< bat_2.size() && k<5)
        {
            tempIn1 = db.getInning(bat_2.get(k).getOneId());
            batRecords_In1_2.add(tempIn1);
            tempIn2 = db.getInning(bat_2.get(k).getTwoId());
            bowlRecords_In2_2.add(tempIn2);
            if(bat_2.get(k).getHomeTeam().contains(teamName2))
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
            tempIn1 = db.getInning(chase_2.get(j).getOneId());
            bowlRecords_In1_2.add(tempIn1);
            
            tempIn2 = db.getInning(chase_2.get(j).getTwoId());
            batRecords_In2_2.add(tempIn2);
            if(chase_2.get(j).getHomeTeam().contains(teamName2))
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
        
        
        
        request.setAttribute("teamName1", teamName1);
        request.setAttribute("batRecords_In1_1", batRecords_In1_1);
        request.setAttribute("bowlRecords_In1_1", bowlRecords_In1_1);
        request.setAttribute("batRecords_In2_1", batRecords_In2_1);
        request.setAttribute("bowlRecords_In2_1", bowlRecords_In2_1);
        request.setAttribute("runScoredBat_In1_1", runScoredBat_In1_1);
        request.setAttribute("runScoredBat_In2_1", runScoredBat_In2_1);
        request.setAttribute("runGivenBowl_In1_1", runGivenBowl_In1_1);
        request.setAttribute("runGivenBowl_In2_1",runGivenBowl_In2_1);
        
        
        request.setAttribute("teamName2", teamName2);
        request.setAttribute("batRecords_In1_2", batRecords_In1_2);
        request.setAttribute("bowlRecords_In1_2", bowlRecords_In1_2);
        request.setAttribute("batRecords_In2_2", batRecords_In2_2);
        request.setAttribute("bowlRecords_In2_2", bowlRecords_In2_2);
        request.setAttribute("runScoredBat_In1_2", runScoredBat_In1_2);
        request.setAttribute("runScoredBat_In2_2", runScoredBat_In2_2);
        request.setAttribute("runGivenBowl_In1_2", runGivenBowl_In1_2);
        request.setAttribute("runGivenBowl_In2_2",runGivenBowl_In2_2);
        
        request.setAttribute("groundName",groundName);
        request.setAttribute("groundData_In1", groundData_In1);
        request.setAttribute("groundData_In2",groundData_In2);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/show.jsp");
        dispatcher.forward(request, response);
           
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(MatchServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(MatchServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";




    } // </editor-fold>

    
    
}
