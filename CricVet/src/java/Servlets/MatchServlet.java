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
    
        
        String teamName =  request.getParameter("teamName");
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
        
        
        
        request.setAttribute("batRecords_In1", batRecords_In1);
        request.setAttribute("bowlRecords_In1", bowlRecords_In1);
        request.setAttribute("batRecords_In2", batRecords_In2);
        request.setAttribute("bowlRecords_In2", bowlRecords_In2);
        
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
    }// </editor-fold>

    
    

    
}
