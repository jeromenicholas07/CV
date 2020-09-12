/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.CricDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.sql.Timestamp;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

/**
 *
 * @author DELL
 */
public class getDB extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            CricDB db = new CricDB();

            db.initDB();

            int matchType = Integer.parseInt(request.getParameter("tournament"));
            String teamOne = request.getParameter("teamName1");
            String homeoraway = request.getParameter("homeoraway");
            
            if(matchType == 1){
                
                TestType side = homeoraway.equals("Home") ? TestType.HOME : TestType.AWAY;
                    List<testMatch> matches = db.getTestMatches(teamOne, 0, side);

                    request.setAttribute("team", teamOne);            
                    request.setAttribute("matches", matches);
            
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/showtest.jsp");
                    dispatcher.forward(request, response);
            }
            else {
            List<Match> matches = db.getDB(matchType, teamOne);

            List<dbMatch> dbMatches = new ArrayList<>();

            dbMatch temp;

            for (int i = 0; i < matches.size(); i++) {
            	if(matches.get(i).getResult().contains("-1")){
            		continue;
            	}
                int matchId;
                String matchDate;
                String oppTeam;
                String tossWinner;
                String BorC;
                String result;
                
                matchId = matches.get(i).getMatchId();
                DateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
                System.out.println(matches.get(i).getMatchId());
                Timestamp ts = matches.get(i).getMatchDate();
                matchDate = dateFormat.format(ts);

                if (matches.get(i).getHomeTeam().equals(teamOne)) {
                    oppTeam = matches.get(i).getAwayTeam();
                } else {
                    oppTeam = matches.get(i).getHomeTeam();
                }

                if (matches.get(i).getTossWinner().contains(teamOne)) {
                    if (matches.get(i).getTossWinner().contains("field")) {
                        tossWinner = "W/C";
                        BorC = "C";
                    } else {
                        tossWinner = "W/B";
                        BorC = "B";
                    }
                } else {
                    if (matches.get(i).getTossWinner().contains("field")) {
                        tossWinner = "L/B";
                        BorC = "B";
                    } else {
                        tossWinner = "L/C";
                        BorC = "C";
                    }
                }

                if (matches.get(i).getResult().contains("run")) {
                    if (matches.get(i).getHomeTeam().equals(teamOne)) {
                    	if(matches.get(i).getResult().contains("D/L")){
									result = "W " + "D/L";
                    	}
                    	else{
                    		result = "W";
                    	}
                        				
                    } else {
              			if(matches.get(i).getResult().contains("D/L")){
              				result = "L " + "D/L";
              			}
              			else{
              				result = "L";
              			}
                        
                    }
                } else if (matches.get(i).getResult().contains("wicket")) {
                    if (matches.get(i).getHomeTeam().equals(teamOne)) {
                    	if (matches.get(i).getResult().contains("D/L")) {
                    		result = "L " + "D/L";
                    	}
                    	else{
                    		result = "L";
                    	}
                       
                    } else {
                    	if(matches.get(i).getResult().contains("D/L")){
                    		result = "W " + "D/L";
                    	}
                    	else{
                    		result = "W";
                    	}
                        
                    }
                } else {
                	if(matches.get(i).getResult().contains("D/L")){
                    result = "- " + "D/L";
                }
                else{
                	result = "-";
                }
                }


                Inning one;
                Inning two;

                if (BorC.equals("B")) {
                    one = matches.get(i).getInningOne();
                    two = matches.get(i).getInningTwo();
                }
                else{
                    one = matches.get(i).getInningTwo();
                    two = matches.get(i).getInningOne();
                }

                int totalSixes = Integer.parseInt(one.getParams().get(5)) + Integer.parseInt(two.getParams().get(5));

                temp = new dbMatch(matchId,matchDate, oppTeam, tossWinner, BorC, result, totalSixes, one, two);
                dbMatches.add(temp);

            }

            request.setAttribute("team", teamOne);
            request.setAttribute("matches", dbMatches);
            request.setAttribute("inningHeaders", db.getHeaders(matchType));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/dbResults.jsp");
            dispatcher.forward(request, response);
            }
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
