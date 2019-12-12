/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author DELL
 */
import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.naming.InitialContext;
import models.testInning;
import models.testMatch;


/**
 *
 * @author DELL
 * AUS : Adelaide Oval

VM558:3 Bdesh : Bangabandhu National Stadium, Dhaka
VM558:3 Bdesh : Khan Shaheb Osman Ali Stadium, Fatullah
VM558:3 Bdesh : MA Aziz Stadium, Chattogram
VM558:3 Bdesh : Shaheed Chandu Stadium, Bogra
VM558:3 Bdesh : Sheikh Abu Naser Stadium, Khulna
VM558:3 Bdesh : Shere Bangla National Stadium, Mirpur, Dhaka
VM558:3 Bdesh : Sylhet International Cricket Stadium
VM558:3 Bdesh : Zahur Ahmed Chowdhury Stadium, Chattogram
VM558:3 Belg : Royal Brussels Cricket Club Ground, Waterloo
VM558:3 Bmda : National Stadium, Hamilton
VM558:3 Bmda : White Hill Field, Sandys Parish, Hamilton
VM558:3 CAN : Maple Leaf North-West Ground, King City
VM558:3 CAN : Toronto Cricket, Skating and Curling Club
VM558:3 DEN : Svanholm Park, Brondby
VM558:3 ENG : Bramall Lane, Sheffield
VM558:3 ENG : County Ground, Bristol
VM558:3 ENG : County Ground, Chelmsford
VM558:3 ENG : County Ground, Derby
VM558:3 ENG : County Ground, Hove
VM558:3 ENG : County Ground, New Road, Worcester
VM558:3 ENG : County Ground, Northampton
VM558:3 ENG : County Ground, Southampton
VM558:3 ENG : Edgbaston, Birmingham
VM558:3 ENG : Grace Road, Leicester
VM558:3 ENG : Headingley, Leeds
VM558:3 ENG : Kennington Oval, London
VM558:3 ENG : Lord's, London
VM558:3 ENG : Nevill Ground, Tunbridge Wells
VM558:3 ENG : North Marine Road Ground, Scarborough
VM558:3 ENG : Old Trafford, Manchester
VM558:3 ENG : Riverside Ground, Chester-le-Street
VM558:3 ENG : Sophia Gardens, Cardiff
VM558:3 ENG : St Helen's, Swansea
VM558:3 ENG : St Lawrence Ground, Canterbury
VM558:3 ENG : The Cooper Associates County Ground, Taunton
VM558:3 ENG : The Rose Bowl, Southampton
VM558:3 ENG : Trent Bridge, Nottingham
VM558:3 Fin : Kerava National Cricket Ground
VM558:3 : Marina Ground, Corfu
VM558:3 GUE : College Field, St Peter Port
VM558:3 GUE : King George V Sports Ground, Castel
VM558:3 HKG : Mission Road Ground, Mong Kok, Hong Kong
VM558:3 Ind : Arun Jaitley Stadium, Delhi
VM558:3 Ind : Barabati Stadium, Cuttack
VM558:3 Ind : Barkatullah Khan Stadium, Pal Road, Jodhpur
VM558:3 Ind : Barsapara Cricket Stadium, Guwahati
VM558:3 Ind : Bharat Ratna Shri Atal Bihari Vajpayee Ekana Cricket Stadium, Lucknow
VM558:3 Ind : Brabourne Stadium, Mumbai
VM558:3 Ind : Captain Roop Singh Stadium, Gwalior
VM558:3 Ind : Dr DY Patil Sports Academy, Mumbai
VM558:3 Ind : Dr. Y.S. Rajasekhara Reddy ACA-VDCA Cricket Stadium, Visakhapatnam
VM558:3 Ind : Eden Gardens, Kolkata
VM558:3 Ind : Gandhi Sports Complex Ground, Amritsar
VM558:3 Ind : Gandhi Stadium, Jalandhar
VM558:3 Ind : Greater Noida Sports Complex Ground, Greater Noida
VM558:3 Ind : Green Park, Kanpur
VM558:3 Ind : Greenfield International Stadium, Thiruvananthapuram
VM558:3 Ind : Gymkhana Ground, Mumbai
VM558:3 Ind : Himachal Pradesh Cricket Association Stadium, Dharamsala
VM558:3 Ind : Holkar Cricket Stadium, Indore
VM558:3 Ind : Indira Gandhi Stadium, Vijayawada
VM558:3 Ind : Indira Priyadarshini Stadium, Visakhapatnam
VM558:3 Ind : Jawaharlal Nehru Stadium, New Delhi
VM558:3 Ind : JSCA International Stadium Complex, Ranchi
VM558:3 Ind : K.D.Singh 'Babu' Stadium, Lucknow
VM558:3 Ind : Keenan Stadium, Jamshedpur
VM558:3 Ind : Lal Bahadur Shastri Stadium, Hyderabad
VM558:3 Ind : M.Chinnaswamy Stadium, Bengaluru
VM558:3 Ind : MA Chidambaram Stadium, Chepauk, Chennai
VM558:3 Ind : Madhavrao Scindia Cricket Ground, Rajkot
VM558:3 Ind : Maharashtra Cricket Association Stadium, Pune
VM558:3 Ind : Moin-ul-Haq Stadium, Patna
VM558:3 Ind : Molana Azad Stadium, Jammu
VM558:3 Ind : Moti Bagh Stadium, Vadodara
VM558:3 Ind : Nahar Singh Stadium, Faridabad
VM558:3 Ind : Nehru Stadium, Fatorda, Margao
VM558:3 Ind : Nehru Stadium, Guwahati
VM558:3 Ind : Nehru Stadium, Indore
VM558:3 Ind : Nehru Stadium, Kochi
VM558:3 Ind : Nehru Stadium, Madras
VM558:3 Ind : Nehru Stadium, Pune
VM558:3 Ind : Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh
VM558:3 Ind : Rajiv Gandhi International Cricket Stadium, Dehradun
VM558:3 Ind : Rajiv Gandhi International Stadium, Uppal, Hyderabad
VM558:3 Ind : Reliance Stadium, Vadodara
VM558:3 Ind : Sardar Patel (Gujarat) Stadium, Motera, Ahmedabad
VM558:3 Ind : Sardar Vallabhai Patel Stadium, Ahmedabad
VM558:3 Ind : Saurashtra Cricket Association Stadium, Rajkot
VM558:3 Ind : Sawai Mansingh Stadium, Jaipur
VM558:3 Ind : Sector 16 Stadium, Chandigarh
VM558:3 Ind : Sher-i-Kashmir Stadium, Srinagar
VM558:3 Ind : University Ground, Lucknow
VM558:3 Ind : University Stadium, Trivandrum
VM558:3 Ind : Vidarbha C.A. Ground, Nagpur
VM558:3 Ind : Vidarbha Cricket Association Stadium, Jamtha, Nagpur
VM558:3 Ind : Wankhede Stadium, Mumbai
VM558:3 IRE : Bready Cricket Club, Magheramason, Bready
VM558:3 IRE : Castle Avenue, Dublin
VM558:3 IRE : Civil Service Cricket Club, Stormont, Belfast
VM558:3 IRE : The Village, Malahide, Dublin
VM558:3 Kenya : Aga Khan Sports Club Ground, Nairobi
VM558:3 Kenya : Gymkhana Club Ground, Nairobi
VM558:3 Kenya : Jaffery Sports Club Ground, Nairobi
VM558:3 Kenya : Mombasa Sports Club Ground
VM558:3 Kenya : Nairobi Club Ground
VM558:3 Kenya : Ruaraka Sports Club Ground, Nairobi
VM558:3 Kenya : Simba Union Ground, Nairobi
VM558:3 MWI : Indian Sports Club, Blantyre
VM558:3 MWI : Lilongwe Golf Club, Lilongwe
VM558:3 MWI : Saint Andrews International High School, Blantyre
VM558:3 MAL : Bayuemas Oval, Kuala Lumpur
VM558:3 MAL : Kinrara Academy Oval, Kuala Lumpur
VM558:3 Malta : Marsa Sports Club
VM558:3 Mex : Reforma Athletic Club, Naucalpan
VM558:3 Mrc : National Cricket Stadium, Tangier
VM558:3 NAM : Affies Park, Windhoek
VM558:3 NAM : United Cricket Club Ground, Windhoek
VM558:3 NAM : Wanderers Cricket Ground, Windhoek
VM558:3 Nep : Tribhuvan University International Cricket Ground, Kirtipur
VM558:3 NL : Hazelaarweg, Rotterdam
VM558:3 NL : Sportpark Het Schootsveld, Deventer
VM558:3 NL : Sportpark Maarschalkerweerd, Utrecht
VM558:3 NL : Sportpark Thurlede, Schiedam
VM558:3 NL : Sportpark Westvliet, The Hague
VM558:3 NL : VRA Ground, Amstelveen
VM558:3 NZ : AMI Stadium, Christchurch
VM558:3 NZ : Basin Reserve, Wellington
VM558:3 NZ : Bay Oval, Mount Maunganui
VM558:3 NZ : Bert Sutcliffe Oval, Lincoln
VM558:3 NZ : Carisbrook, Dunedin
VM558:3 NZ : Cobham Oval (New), Whangarei
VM558:3 NZ : Eden Park, Auckland
VM558:3 NZ : Hagley Oval, Christchurch
VM558:3 NZ : McLean Park, Napier
VM558:3 NZ : Owen Delany Park, Taupo
VM558:3 NZ : Pukekura Park, New Plymouth
VM558:3 NZ : Queenstown Events Centre
VM558:3 NZ : Saxton Oval, Nelson
VM558:3 NZ : Seddon Park, Hamilton
VM558:3 NZ : University Oval, Dunedin
VM558:3 NZ : Westpac Stadium, Wellington
VM558:3 OMAN : Al Amerat Cricket Ground Oman Cricket (Ministry Turf 1)
VM558:3 OMAN : Al Amerat Cricket Ground Oman Cricket (Ministry Turf 2)
VM558:3 PAK : Arbab Niaz Stadium, Peshawar
VM558:3 PAK : Ayub National Stadium, Quetta
VM558:3 PAK : Bagh-e-Jinnah, Lahore
VM558:3 PAK : Bahawal Stadium, Bahawalpur
VM558:3 PAK : Bugti Stadium, Quetta
VM558:3 PAK : Gaddafi Stadium, Lahore
VM558:3 PAK : Ibn-e-Qasim Bagh Stadium, Multan
VM558:3 PAK : Iqbal Stadium, Faisalabad
VM558:3 PAK : Jinnah Stadium, Gujranwala
VM558:3 PAK : Jinnah Stadium, Sialkot
VM558:3 PAK : Multan Cricket Stadium
VM558:3 PAK : National Stadium, Karachi
VM558:3 PAK : Niaz Stadium, Hyderabad
VM558:3 PAK : Peshawar Club Ground
VM558:3 PAK : Pindi Club Ground, Rawalpindi
VM558:3 PAK : Rawalpindi Cricket Stadium
VM558:3 PAK : Sheikhupura Stadium
VM558:3 PAK : Southend Club Cricket Stadium, Karachi
VM558:3 PAK : Sports Stadium, Sargodha
VM558:3 PAK : Zafar Ali Stadium, Sahiwal
VM558:3 PNG : Amini Park, Port Moresby
VM558:3 Peru : El Cortijo Polo Club Pitch A Ground, Lima
VM558:3 Peru : El Cortijo Polo Club Pitch B Ground, Lima
VM558:3 Peru : Lima Cricket and Football Club, Lima
VM558:3 QAT : West End Park International Cricket Stadium, Doha
VM558:3 ROM : Moara Vlasiei Cricket Ground
VM558:3 Sam : Faleata Oval No 1, Apia
VM558:3 Sam : Faleata Oval No 2, Apia
VM558:3 Sam : Faleata Oval No 3, Apia
VM558:3 SCOT : Cambusdoon New Ground, Ayr
VM558:3 SCOT : Grange Cricket Club, Raeburn Place, Edinburgh
VM558:3 SCOT : Mannofield Park, Aberdeen
VM558:3 SCOT : Titwood, Glasgow
VM558:3 SGP : Indian Association Ground, Singapore
VM558:3 SGP : Kallang Ground, Singapore
VM558:3 SGP : Singapore Cricket Club, Padang
VM558:3 SA : Boland Park, Paarl
VM558:3 SA : Buffalo Park, East London
VM558:3 SA : City Oval, Pietermaritzburg
VM558:3 SA : Diamond Oval, Kimberley
VM558:3 SA : Ellis Park, Johannesburg
VM558:3 SA : Kingsmead, Durban
VM558:3 SA : Lord's, Durban
VM558:3 SA : Mangaung Oval, Bloemfontein
VM558:3 SA : Moses Mabhida Stadium, Durban
VM558:3 SA : Newlands, Cape Town
VM558:3 SA : Old Wanderers, Johannesburg
VM558:3 SA : Senwes Park, Potchefstroom
VM558:3 SA : St George's Park, Port Elizabeth
VM558:3 SA : SuperSport Park, Centurion
VM558:3 SA : The Wanderers Stadium, Johannesburg
VM558:3 SA : Willowmoore Park, Benoni
VM558:3 ESP : Desert Springs Cricket Ground
VM558:3 ESP : La Manga Club Bottom Ground
VM558:3 SL : Asgiriya Stadium, Kandy
VM558:3 SL : Colombo Cricket Club Ground
VM558:3 SL : Galle International Stadium
VM558:3 SL : Mahinda Rajapaksa International Cricket Stadium, Sooriyawewa, Hambantota
VM558:3 SL : P Sara Oval, Colombo
VM558:3 SL : Pallekele International Cricket Stadium
VM558:3 SL : R.Premadasa Stadium, Khettarama, Colombo
VM558:3 SL : Rangiri Dambulla International Stadium
VM558:3 SL : Sinhalese Sports Club Ground, Colombo
VM558:3 SL : Tyronne Fernando Stadium, Moratuwa
VM558:3 UGA : Kyambogo Cricket Oval, Kampala
VM558:3 UGA : Lugogo Cricket Oval, Kampala
VM558:3 UAE : Dubai International Cricket Stadium
VM558:3 UAE : ICC Academy Ground No 2, Dubai
VM558:3 UAE : ICC Academy, Dubai
VM558:3 UAE : Sharjah Cricket Stadium
VM558:3 UAE : Sheikh Zayed Stadium, Abu Dhabi
VM558:3 UAE : Tolerance Oval, Abu Dhabi
VM558:3 USA : Central Broward Regional Park Stadium Turf Ground, Lauderhill
VM558:3 WI : Albion Sports Complex, Albion, Berbice, Guyana
VM558:3 WI : Antigua Recreation Ground, St John's, Antigua
VM558:3 WI : Arnos Vale Ground, Kingstown, St Vincent
VM558:3 WI : Bourda, Georgetown, Guyana
VM558:3 WI : Darren Sammy National Cricket Stadium, Gros Islet, St Lucia
VM558:3 WI : Kensington Oval, Bridgetown, Barbados
VM558:3 WI : Mindoo Phillip Park, Castries, St Lucia
VM558:3 WI : National Cricket Stadium, St George's, Grenada
VM558:3 WI : Providence Stadium, Guyana
VM558:3 WI : Queen's Park (Old), St George's, Grenada
VM558:3 WI : Queen's Park Oval, Port of Spain, Trinidad
VM558:3 WI : Sabina Park, Kingston, Jamaica
VM558:3 WI : Sir Vivian Richards Stadium, North Sound, Antigua
VM558:3 WI : Warner Park, Basseterre, St Kitts
VM558:3 WI : Windsor Park, Roseau, Dominica
VM558:3 ZIM : Bulawayo Athletic Club
VM558:3 ZIM : Harare Sports Club
VM558:3 ZIM : Kwekwe Sports Club
VM558:3 ZIM : Old Hararians, Harare
VM558:3 ZIM : Queens Sports Club, Bulawayo
 */
public class CricDB extends BaseDAO {
    
    public String checkhomeoraway(String teamOne, String teamTwo,String groundName){
       Connection con = null;
        Statement s = null;
        ResultSet r = null;
        String hometeam = null;
        try {
            con = getConnection();
            String sq = "select * from APP.HOMEGROUND WHERE teamname='" + teamOne + "'";
            s = con.createStatement();
            r = s.executeQuery(sq);
            while (r.next()) {
                String ground1 = r.getString("GROUND1");
                String ground2 = r.getString("GROUND2");
                String ground3 = r.getString("GROUND3");
                String ground4 = r.getString("GROUND4");
                String ground5 = r.getString("GROUND5");
                String ground6 = r.getString("GROUND6");
                String ground7 = r.getString("GROUND7");
                String ground8 = r.getString("GROUND8");
                String ground9 = r.getString("GROUND9");
                String ground10 = r.getString("GROUND10");
                String ground11 = r.getString("GROUND11");
                String ground12 = r.getString("GROUND12");
                String ground13 = r.getString("GROUND13");
                String ground14 = r.getString("GROUND14");
                String ground15 = r.getString("GROUND15");
                String ground16 = r.getString("GROUND16");
                String ground17 = r.getString("GROUND17");
                String ground18 = r.getString("GROUND18");
                
            if(groundName.equals(ground1)||groundName.equals(ground2)||groundName.equals(ground3)||groundName.equals(ground4)||groundName.equals(ground5)||groundName.equals(ground6)||groundName.equals(ground7)||groundName.equals(ground8)||groundName.equals(ground9)||groundName.equals(ground10)||groundName.equals(ground11)||groundName.equals(ground12)||groundName.equals(ground13)||groundName.equals(ground14)||groundName.equals(ground15)||groundName.equals(ground16)||groundName.equals(ground17)||groundName.equals(ground18)){
                 hometeam = teamOne;
                 return hometeam;
                
            }
            else{
                hometeam = teamTwo;
                return hometeam;
            }
            
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return hometeam;
        
    }
        
    
    
   
    public void addtestMatch(testMatch match) {
        
        Connection con = null;
        Statement s = null;
        ResultSet r = null;
        try {
            con = getConnection();
            String sq = "select * from APP.TESTMATCH WHERE MATCHID=" + String.valueOf(match.getMatchId());
            s = con.createStatement();
            r = s.executeQuery(sq);
            if (r.next()) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                return;
            }

            if (match.getResult().contains("No result")) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " has no result");
                return;
            }

            int inn11 = addtestInning(match.getInningOne1());

            int inn21 = addtestInning(match.getInningTwo1());
            int inn12 = addtestInning(match.getInningOne2());

            int inn22 = addtestInning(match.getInningTwo2());

            String sql = "insert into APP.TESTMATCH (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BATTINGFIRST, ONE1ID, TWO1ID, ONE2ID, TWO2ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE,TEAMATHOME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBattingFirst());
            ps.setInt(7, inn11);
            ps.setInt(8, inn21);
            ps.setInt(9, inn12);
            ps.setInt(10, inn22);
            
            ps.setString(11, match.getHomeScore());
            ps.setString(12, match.getAwayScore());

            ps.setString(13, match.getResult());
            ps.setString(14, match.getGroundName());
            ps.setString(15, String.valueOf(match.getMatchType()));
            ps.setString(16,String.valueOf(match.getteamathome()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    
    public int addtestInning(testInning i) {
        System.out.println(i.getNoOfParams());
        System.out.println(i.getParams());
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            
            String sql = "insert into APP.TESTINNING (TOTALRUNS, SIXES, FOURS, FIRSTWICKET, RUNS5WICKET,WINNER) values(?,?,?,?,?,?)";
            String generatedColumns[] = {"ID"};
            ps = con.prepareStatement(sql, generatedColumns);
            for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it-1));
                }
                  
            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                }
              

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return id;
    }
// fetches all test matches
    public List<testMatch> gettestMatch() {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");
                String teamathome = rs.getString("teamathome");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

// fetches all TEAM A's test matches
    public List<testMatch> getteamtestMatch(String teamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where (hometeam='" + teamName + "' OR awayteam='" + teamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
                int matchType = rs.getInt("matchtype");
                String teamathome = rs.getString("teamathome");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
    
    public List<Match> getHth(int matchType, String A, String B){
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        try {
            con = getConnection();

            String sql = "select * from APP.Matches where matchtype= " + matchType + " and ((hometeam='" + A + "' AND awayteam='" + B + "') OR (hometeam='" + B + "' AND awayteam='" + A + "')) order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
// get team A vs team B HTH
    public List<testMatch> gettestHth(int matchType, String A, String B){
        
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where matchtype= " + matchType + " and ((hometeam='" + A + "' AND awayteam='" + B + "') OR (hometeam='" + B + "' AND awayteam='" + A + "')) order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                String teamathome = rs.getString("teamathome");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

    public List<Match> getDB(int matchType, String TeamName) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.Matches where matchtype= " + matchType + " and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }
    
    public List<testMatch> gettestDB(int matchType, String TeamName) {
        List<testMatch> matches = new ArrayList<>();

        Connection con = null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTMATCH where matchtype= " + matchType + " and (hometeam='" + TeamName + "' OR awayteam='" + TeamName + "') order by MATCHDATE DESC FETCH FIRST 30 ROWS ONLY";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String result = rs.getString("result");
                String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                String teamathome = rs.getString("teamathome");
                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                matches.add(m);
                
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return matches;
    }

    public void initDB() {

        System.out.println("at init");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        
        
        /*
        try {
            String sql = "drop table \"APP\".TESTINNING";
            con = getConnection();
            stmt = con.createStatement();
            System.out.println("Dropping test inning");
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        try {
            String sql = "drop table \"APP\".TESTMATCH";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Dropping test matches");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "drop table \"APP\".MATCHES";
            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        */
        
        
        try {
            String sql = "create table \"APP\".TESTMATCH\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE DATE,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BATTINGFIRST VARCHAR(120) not null,\n"
                    + "	ONE1ID INTEGER not null,\n"
                    + "	TWO1ID INTEGER not null,\n"
                    + "	ONE2ID INTEGER not null,\n"
                    + "	TWO2ID INTEGER not null,\n"
                    + "	HOMESCORE VARCHAR(120) not null,\n"
                    + "	AWAYSCORE VARCHAR(120) not null,\n"
                    + "	RESULT VARCHAR(120),\n"
                    + "	GROUNDNAME VARCHAR(120),\n"
                    + "	MATCHTYPE INTEGER\n"
                    + "	TEAMATHOME VARCHAR(120)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        
        try {
            String sql = "create table \"APP\".TESTINNING\n"
                    + "(\n"
                    + "	ID INTEGER GENERATED ALWAYS AS IDENTITY not null primary key,\n"
                    + "	TOTALRUNS VARCHAR(20),\n"
                    + "	SIXES VARCHAR(20),\n"
                    + "	FOURS VARCHAR(20),\n"
                    + "	FIRSTWICKET VARCHAR(20),\n"
                    + "	RUNS5WICKET VARCHAR(20),\n"
                    + " WINNER LONG VARCHAR )";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        try {
            String sql = "create table \"APP\".HOMEGROUND\n"
                    + "(\n"
                    + "	TEAMNAME VARCHAR(120) NOT NULL,\n"
                    + "	GROUND1 VARCHAR(120),\n"
                    + "	GROUND2 VARCHAR(120),\n"
                    + "	GROUND3 VARCHAR(120),\n"
                    + "	GROUND4 VARCHAR(120),\n"
                    + "	GROUND5 VARCHAR(120),\n"
                    + " GROUND6 VARCHAR(120),\n"
                    + "	GROUND7 VARCHAR(120),\n"
                    + "	GROUND8 VARCHAR(120),\n"
                    + "	GROUND9 VARCHAR(120)\n"
                    + "	GROUND10 VARCHAR(120)\n"
                    + "	GROUND11 VARCHAR(120)\n"
                    + "	GROUND12 VARCHAR(120)\n"
                    + "	GROUND13 VARCHAR(120)\n"
                    + "	GROUND14 VARCHAR(120)\n"
                    + "	GROUND15 VARCHAR(120)\n"
                    + "	GROUND16 VARCHAR(120)\n"
                    + "	GROUND17 VARCHAR(120)\n"+ ""
                    + "	GROUND18 VARCHAR(120)\n"
                    +")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        
      
        try {
            String sql = "create table \"APP\".HEADERS\n"
                    + "(\n"
                    + "	MATCHTYPE INTEGER not null primary key,\n"
                    + "	PARAM1 VARCHAR(100),\n"
                    + "	PARAM2 VARCHAR(100),\n"
                    + "	PARAM3 VARCHAR(100),\n"
                    + "	PARAM4 VARCHAR(100),\n"
                    + "	PARAM5 VARCHAR(100),\n"
                    + "	PARAM6 VARCHAR(100),\n"
                    + "	PARAM7 VARCHAR(100),\n"
                    + "	PARAM8 VARCHAR(100)\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

        try {
            String sql = "create table \"APP\".MATCHES\n"
                    + "(\n"
                    + "	MATCHID INTEGER default -1 not null primary key,\n"
                    + "	HOMETEAM VARCHAR(120) not null,\n"
                    + "	AWAYTEAM VARCHAR(120) not null,\n"
                    + "	MATCHDATE DATE,\n"
                    + "	TOSSWINNER VARCHAR(120) not null,\n"
                    + "	BATTINGFIRST VARCHAR(120) not null,\n"
                    + "	INNING1_ID INTEGER not null,\n"
                    + "	INNING2_ID INTEGER not null,\n"
                    + "	HOMESCORE VARCHAR(120) not null,\n"
                    + "	AWAYSCORE VARCHAR(120) not null,\n"
                    + "	RESULT VARCHAR(120),\n"
                    + "	GROUNDNAME VARCHAR(120),\n"
                    + "	MATCHTYPE INTEGER\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

        try {
            String sql = "create table \"APP\".INNINGS\n"
                    + "(\n"
                    + "	ID INTEGER GENERATED ALWAYS AS IDENTITY not null primary key,\n"
                    + "	PARAM1 VARCHAR(20),\n"
                    + "	PARAM2 VARCHAR(20),\n"
                    + "	PARAM3 VARCHAR(20),\n"
                    + "	PARAM4 VARCHAR(20),\n"
                    + "	PARAM5 VARCHAR(20),\n"
                    + "	PARAM6 VARCHAR(20),\n"
                    + "	PARAM7 VARCHAR(20),\n"
                    + "	PARAM8 VARCHAR(20),\n"
                    + "	NOOFPARAMS INTEGER default 6 not null\n"
                    + ")";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) { }
        }

        try {
            String sql = "INSERT INTO APP.HEADERS (MATCHTYPE, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6,PARAM7,PARAM8) VALUES (117, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(2, 'First Over', 'First ten Overs', 'Last ten Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(158, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(159, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(748, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(205, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')\n"
                    + ",(3, 'First Over', 'First six Overs', 'Last five Overs', 'First Wicket', 'Fours', 'Sixes','Total Runs', 'Winner')";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "INSERT INTO APP.HOMEGROUND VALUES ('Australia', 'Melbourne Cricket Ground', 'Sydney Cricket Ground', 'Adelaide Oval', 'Brisbane Cricket Ground, Woolloongabba, Brisbane', 'Perth Stadium', 'W.A.C.A. Ground, Perth', 'Bellerive Oval, Hobart', 'Manuka Oval, Canberra',NULL)\n"
                    + ",('Bangladesh', 'Shere Bangla National Stadium, Mirpur, Dhaka', 'Bangabandhu National Stadium, Dhaka', 'Zahur Ahmed Chowdhury Stadium, Chattogram', 'Sylhet International Cricket Stadium', 'Sheikh Abu Naser Stadium, Khulna', 'Shaheed Chandu Stadium, Bogra', 'Khan Shaheb Osman Ali Stadium, Fatullah', 'MA Aziz Stadium, Chattogram',NULL)\n"
                    + ",('England','Kennington Oval, London','Old Trafford, Manchester','Lord''s, London','Edgbaston, Birmingham','Headingley, Leeds','Trent Bridge, Nottingham','Riverside Ground, Chester-le-Street','The Rose Bowl, Southampton','Bramall Lane, Sheffield')\n"
                    + ",('India','Holkar Cricket Stadium, Indore','Eden Gardens, Kolkata','JSCA International Stadium Complex, Ranchi','Maharashtra Cricket Association Stadium, Pune','Dr. Y.S. Rajasekhara Reddy ACA-VDCA Cricket Stadium, Visakhapatnam','Rajiv Gandhi International Stadium, Uppal, Hyderabad','Saurashtra Cricket Association Stadium, Rajkot','M.Chinnaswamy Stadium, Bengaluru','Vidarbha Cricket Association Stadium, Jamtha, Nagpur','Himachal Pradesh Cricket Association Stadium, Dharamsala','Wankhede Stadium, Mumbai','Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh','Green Park, Kanpur','MA Chidambaram Stadium, Chepauk, Chennai','Arun Jaitley Stadium, Delhi','Sardar Patel (Gujarat) Stadium, Motera, Ahmedabad','Rajiv Gandhi International Cricket Stadium, Dehradun','Bharat Ratna Shri Atal Bihari Vajpayee Ekana Cricket Stadium, Lucknow')\n"
                    + ",('Ireland','Bready Cricket Club, Magheramason, Bready','Castle Avenue, Dublin','Civil Service Cricket Club, Stormont, Belfast','The Village, Malahide, Dublin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)\n"
                    + ",('New Zealand','AMI Stadium, Christchurch','Basin Reserve, Wellington','Bay Oval, Mount Maunganui','Bert Sutcliffe Oval, Lincoln','Carisbrook, Dunedin','Cobham Oval (New), Whangarei','Eden Park, Auckland','Hagley Oval, Christchurch','McLean Park, Napier','Owen Delany Park, Taupo','Pukekura Park, New Plymouth', 'Queenstown Events Centre','Saxton Oval, Nelson','Seddon Park, Hamilton','University Oval, Dunedin','Westpac Stadium, Wellington',NULL,NULL)\n"
                    + ",('Pakistan','Sheikh Zayed Stadium, Abu Dhabi','Dubai International Cricket Stadium','Sharjah Cricket Stadium',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL)\n"
                    + ",('Afghanistan','Bharat Ratna Shri Atal Bihari Vajpayee Ekana Cricket Stadium, Lucknow','Rajiv Gandhi International Cricket Stadium, Dehradun','Shahid Vijay Singh Pathik Sports Complex, Greater Noida',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, NULL,NULL,NULL,NULL,NULL,NULL,NULL)\n"
                    + ",('South Africa','Boland Park, Paarl','Buffalo Park, East London','City Oval, Pietermaritzburg','Diamond Oval, Kimberley','Ellis Park, Johannesburg','Kingsmead, Durban','Lord''s, Durban','Mangaung Oval, Bloemfontein','Moses Mabhida Stadium, Durban','Newlands, Cape Town','Old Wanderers, Johannesburg','Senwes Park, Potchefstroom','St George''s Park, Port Elizabeth','SuperSport Park, Centurion','The Wanderers Stadium, Johannesburg','Willowmoore Park, Benoni',NULL,NULL)\n"
                    + ",('Sri Lanka','Asgiriya Stadium, Kandy','Colombo Cricket Club Ground','Galle International Stadium','Mahinda Rajapaksa International Cricket Stadium, Sooriyawewa, Hambantota','P Sara Oval, Colombo','Pallekele International Cricket Stadium','R.Premadasa Stadium, Khettarama, Colombo','Rangiri Dambulla International Stadium','Sinhalese Sports Club Ground, Colombo','Tyronne Fernando Stadium, Moratuwa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)\n"
                    + ",('West Indies','Albion Sports Complex, Albion, Berbice, Guyana','Antigua Recreation Ground, St John''s, Antigua','Arnos Vale Ground, Kingstown, St Vincent','Bourda, Georgetown, Guyana','Darren Sammy National Cricket Stadium, Gros Islet, St Lucia','Kensington Oval, Bridgetown, Barbados','Mindoo Phillip Park, Castries, St Lucia','National Cricket Stadium, St George''s, Grenada','Providence Stadium, Guyana','Queen''s Park (Old), St George''s, Grenada','Queen''s Park Oval, Port of Spain, Trinidad','Sabina Park, Kingston, Jamaica','Sir Vivian Richards Stadium, North Sound, Antigua','Warner Park, Basseterre, St Kitts','Windsor Park, Roseau, Dominica',NULL,NULL,NULL)\n";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        
        try {
            String sql = "UPDATE APP.MATCHES SET HOMETEAM = 'Delhi Capitals' WHERE HOMETEAM = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "UPDATE APP.MATCHES SET awayteam = 'Delhi Capitals' WHERE awayteam = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

        try {
            String sql = "UPDATE APP.MATCHES SET battingfirst = 'Delhi Capitals' WHERE battingfirst = 'Delhi Daredevils' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        
        try {
            String sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to field first' WHERE tosswinner = 'Delhi Daredevils , elected to field first' ";

            con = getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
            
            sql = "UPDATE APP.MATCHES SET tosswinner = 'Delhi Capitals , elected to bat first' WHERE tosswinner = 'Delhi Daredevils , elected to bat first' ";
            Statement st = con.createStatement();
            st.execute(sql);
            

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) {  }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }

    }
    
    public boolean checktestMatchEntry(int matchId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.TESTMATCH WHERE MATCHID=" + String.valueOf(matchId);

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return false;
    }
    
    public testInning gettestInning(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        testInning inning = null;

        try {
            con = getConnection();

            String sql = "select * from APP.TESTINNING where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");
                int totalruns = rs.getInt("totalruns");
                int sixes = rs.getInt("sixes");
                int fours = rs.getInt("fours");
                int firstwicket = rs.getInt("firstwicket");
                int runs5wicket = rs.getInt("runs5wicket");
                String winner = rs.getString("winner");

                //for (int i = 1; i <= noOfParams; i++) {
                //    params.add(rs.getString("param" + i));
                //}

                inning = new testInning(inningId, totalruns, sixes, fours, firstwicket,runs5wicket,winner);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return inning;
    }

    public List<String> getHeaders(int matchType) {
        List<String> headers = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.HEADERS WHERE MATCHTYPE=" + matchType;

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next() && matchType != 1) {
                for (int i = 1; i <= 8; i++) {
                    headers.add(rs.getString("param" + i));
                }
            } else if (matchType == 1) {
                String one = "Total Runs";
                String two = "Sixes";
                String three = "fours";
                String four = "Runs before First Wicket";
                String five = "Runs after 5 Wickets";
                String six = "winner";
                headers.add(one);
                headers.add(two);
                headers.add(three);
                headers.add(four);
                headers.add(five);
                headers.add(six);
                
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return headers;
    }

    public void addMatch(Match match) {
        
        Connection con = null;
        Statement s = null;
        ResultSet r = null;
        try {
            con = getConnection();
            String sq = "select * from APP.MATCHES WHERE MATCHID=" + String.valueOf(match.getMatchId());
            s = con.createStatement();
            r = s.executeQuery(sq);
            if (r.next()) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " already exists in DB");
                return;
            }

            if (match.getResult().contains("No result")) {
                System.out.println("Match " + String.valueOf(match.getMatchId()) + " has no result");
                return;
            }

            int inn1 = addInning(match.getInningOne());

            int inn2 = addInning(match.getInningTwo());

            String sql = "insert into APP.MATCHES (MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, TOSSWINNER, BATTINGFIRST, INNING1_ID, INNING2_ID, HOMESCORE, AWAYSCORE, RESULT, GROUNDNAME, MATCHTYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, match.getMatchId());
            ps.setString(2, match.getHomeTeam());
            ps.setString(3, match.getAwayTeam());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getTossWinner());
            ps.setString(6, match.getBattingFirst());
            ps.setInt(7, inn1);
            ps.setInt(8, inn2);
            ps.setString(9, match.getHomeScore());
            ps.setString(10, match.getAwayScore());

            ps.setString(11, match.getResult());
            ps.setString(12, match.getGroundName());
            ps.setString(13, String.valueOf(match.getMatchType()));
            ps.execute();

            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { r.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean checkMatchEntry(int matchId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM APP.MATCHES WHERE MATCHID=" + String.valueOf(matchId);

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return false;
    }

    public int addInning(Inning i) {
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            con = getConnection();
            ps = null;
            rs = null;
            if (i.getNoOfParams() == 4) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, noofparams) values(?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(5, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else if (i.getNoOfParams() == 6) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, param5, param6, noofparams) values(?,?,?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(7, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else if (i.getNoOfParams() == 8) {
                String sql = "insert into APP.INNINGS (param1, param2, param3, param4, param5, param6, param7, param8, noofparams) values(?,?,?,?,?,?,?,?,?)";
                String generatedColumns[] = {"ID"};
                ps = con.prepareStatement(sql, generatedColumns);
                for (int it = 1; it <= i.getNoOfParams(); it++) {
                    ps.setString(it, i.getParams().get(it - 1));
                }
                ps.setInt(9, i.getNoOfParams());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
            //todo: show that ur unable to connect to db
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return id;
    }
// gets a Team's test match if 0 : all teams matches, 1 : team a bats first, 2 : team a bats second
    public List<testMatch> gettestMatches(String teamName, int matchType, int type){
        
        List<testMatch> testmatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        switch (type) {
            case 0:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                                
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");
                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
                
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
                
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where ((hometeam = '" + teamName + "' or awayteam = '" + teamName + "') "
                            + "and matchtype = " + matchType + ") AND NOT battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
            return testmatches;
            
        
    }
   
    public List<testMatch> gettesthomeoraway(String teamName,int matchType, int type, boolean home){
        
        List<testMatch> testmatches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        if(home = true){
        switch (type) {
//batting first            
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) AND teamathome = '" + teamName +"' AND BATTINGFIRST = '"+teamName+"' ORDER BY MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
//bowling first               
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) AND teamathome = '" + teamName +"' AND NOT BATTINGFIRST = '"+teamName+"' ORDER BY MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");

                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
            return testmatches;
            
        }
        
        else{
            
            switch (type) {
                
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) AND NOT teamathome = '" + teamName +"' AND BATTINGFIRST = '"+teamName+"' ORDER BY MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");
                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);
                            
                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
                
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.TESTMATCH where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' ) AND NOT teamathome = '" + teamName +"' AND NOT BATTINGFIRST = '"+teamName+"' ORDER BY MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int one1 = rs.getInt("one1id");
                        int two1 = rs.getInt("two1id");
                        int one2 = rs.getInt("one2id");
                        int two2 = rs.getInt("two2id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");
                        String teamathome = rs.getString("teamathome");
                        testInning inningOne1 = gettestInning(one1);
                        testInning inningTwo1 = gettestInning(two1);
                        testInning inningOne2 = gettestInning(one2);
                        testInning inningTwo2 = gettestInning(two2);

                        testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                        testmatches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
            return testmatches;
            
        
        }
    }

    public List<Match> getMatches(String teamName, int matchType, int type) {

        List<Match> matches = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        switch (type) {
            case 0:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where (hometeam = '" + teamName + "' OR awayteam = '" + teamName + "' )  and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            case 1:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where battingfirst = '" + teamName + "' and matchtype = " + matchType + " order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            case 2:

                try {
                    con = getConnection();
                    String sql = "select * from APP.MATCHES where ((hometeam = '" + teamName + "' or awayteam = '" + teamName + "') "
                            + "and matchtype = " + matchType + ") AND NOT battingfirst = '" + teamName + "' order by MATCHDATE DESC";

                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int matchId = rs.getInt("matchid");
                        String homeTeam = rs.getString("hometeam");
                        String awayTeam = rs.getString("awayteam");
                        Date matchDate = rs.getDate("matchdate");
                        String tossWinner = rs.getString("tosswinner");
                        String battingFirst = rs.getString("battingfirst");
                        int inning1_id = rs.getInt("inning1_id");
                        int inning2_id = rs.getInt("inning2_id");
                        String homeScore = rs.getString("homescore");
                        String awayscore = rs.getString("awayscore");
                        String result = rs.getString("result");
                        String groundName = rs.getString("groundname");
//                int matchType = rs.getInt("matchtype");

                        Inning inningOne = getInning(inning1_id);
                        Inning inningTwo = getInning(inning2_id);

                        Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                        matches.add(m);
                    }

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    try { rs.close(); } catch (Exception e) { /* ignored */ }
                    try { stmt.close(); } catch (Exception e) { /* ignored */ }
                    try { con.close(); } catch (Exception e) { /* ignored */ }
                }
                break;
            default:
                break;
            }
        return matches;
        
        
        

    }

    public Inning getInning(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Inning inning = null;

        try {
            con = getConnection();

            String sql = "select * from APP.INNINGS where id = " + id + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int inningId = rs.getInt("id");
                List<String> params = new ArrayList<>();
                int noOfParams = rs.getInt("noofparams");

                for (int i = 1; i <= noOfParams; i++) {
                    params.add(rs.getString("param" + i));
                }

                inning = new Inning(inningId, noOfParams, params);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return inning;
    }
    
   

    public List<Match> getGroundInfo(String groundName, int matchType) {
        List<Match> matches = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.MATCHES where groundname = ? AND MATCHTYPE = ? order by MATCHDATE DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, groundName);
            stmt.setInt(2, matchType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int inning1_id = rs.getInt("inning1_id");
                int inning2_id = rs.getInt("inning2_id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");

                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                Inning inningOne = getInning(inning1_id);
                Inning inningTwo = getInning(inning2_id);

                Match m = new Match(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne, inningTwo, homeScore, awayscore, result, groundName, matchType);
                matches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        return matches;
    }
    
    public List<testMatch> gettestGroundInfo(String groundName, int matchType) {
        List<testMatch> testmatches = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select * from APP.TESTMATCH where groundname = ? AND MATCHTYPE = ? order by MATCHDATE DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, groundName);
            stmt.setInt(2, matchType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                String homeTeam = rs.getString("hometeam");
                String awayTeam = rs.getString("awayteam");
                Date matchDate = rs.getDate("matchdate");
                String tossWinner = rs.getString("tosswinner");
                String battingFirst = rs.getString("battingfirst");
                int one1 = rs.getInt("one1id");
                int two1 = rs.getInt("two1id");
                int one2 = rs.getInt("one2id");
                int two2 = rs.getInt("two2id");
                String homeScore = rs.getString("homescore");
                String awayscore = rs.getString("awayscore");
                String teamathome = rs.getString("teamathome");

                String result = rs.getString("result");
//                String groundName = rs.getString("groundname");

                testInning inningOne1 = gettestInning(one1);
                testInning inningTwo1 = gettestInning(two1);
                testInning inningOne2 = gettestInning(one2);
                testInning inningTwo2 = gettestInning(two2);

                testMatch m = new testMatch(matchId, homeTeam, awayTeam, matchDate, tossWinner, battingFirst, inningOne1, inningTwo1,inningOne2,inningTwo2, homeScore, awayscore, result, groundName, matchType,teamathome);
                testmatches.add(m);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        return testmatches;
    }


    public List<String> getGroundList(int matchType) {
        List<String> grounds = new ArrayList<String>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;

        try {
            con = getConnection();
            if(matchType == 1){
             sql = "select distinct groundname from APP.TESTMATCH order by groundname ASC";
            }
            else{
                sql = "select distinct groundname from APP.MATCHES where MATCHTYPE = " + matchType + " order by groundname ASC";
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String gName = rs.getString("groundname");
                if (!grounds.contains(gName)) {
                    grounds.add(gName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return grounds;
    }

    public List<String> getTeamsList(int matchType) {
        List<String> teams = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select distinct HOMETEAM from APP.MATCHES where MATCHTYPE = " + matchType + " "
                    + "union select distinct AWAYTEAM from APP.MATCHES where MATCHTYPE = " + matchType + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                ResultSetMetaData md = rs.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount; i++) {
//                    String col_name = md.getColumnName(i);
//                    System.out.println("col:"+col_name);
//                }

                String tName = rs.getString("1");
                if (!teams.contains(tName)) {
                    teams.add(tName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return teams;
    }
    
    public List<String> getTestTeamsList() {
        int matchType = 1;
        List<String> teams = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "select distinct HOMETEAM from APP.TESTMATCH where MATCHTYPE = " + matchType + " "
                    + "union select distinct AWAYTEAM from APP.TESTMATCH where MATCHTYPE = " + matchType + "";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                ResultSetMetaData md = rs.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount; i++) {
//                    String col_name = md.getColumnName(i);
//                    System.out.println("col:"+col_name);
//                }

                String tName = rs.getString("1");
                if (!teams.contains(tName)) {
                    teams.add(tName);
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CricDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return teams;
    }
    
    
//    BaseDAO db = new BaseDAO();;

}
