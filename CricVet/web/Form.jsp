<%-- 
    Document   : Form
    Created on : Feb 19, 2019, 12:36:53 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Select</h1>

        <form action="T20MatchServlet" method="get">
            <h3>Format: T20 </h3>
            <table>

                         <td><label>Team One:</label></td>
                            <td><select name="teamName1">
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-T20 Internationals-/-</i></font></option>
                                    <option value="India">India</option>    
                                    <option value="Pakistan">Pakistan</option>
                                    <option value="South Africa">South Africa</option>
                                    <option value="England">England</option>
                                    <option value="Australia">Australia</option>
                                    <option value="New Zealand">New Zealand</option>
                                    <option value="West Indies"> West Indies</option>
                                    <option value="Afghanistan">Afghanistan</option>
                                    <option value="Sri Lanka">Sri Lanka</option>
                                    <option value="Bangladesh">Bangladesh</option>
                                    <option value="Scotland">Scotland</option>
                                    <option value="Zimbabwe">Zimbabwe</option>
                                    <option value="Netherlands">Netherlands</option>
                                    <option value="Nepal">Nepal</option>
                                    <option value="United Arab Emirates">United Arab Emirates</option>
                                    <option value="Hong Kong">Hong Kong</option>
                                    <option value="Ireland">Ireland</option>
                                    <option value="Oman">Oman</option>
                                    <option disabled="disabled" ><font color="white"><i>-/-IPL-/-</i></font></option>
                                    <option value="Chennai Super Kings">Chennai Super Kings</option>
                                    <option value="Delhi Daredevils">Delhi Capitals (Daredevils)</option>
                                    <option value="Kings XI Punjab">Kings XI Punjab</option>
                                    <option value="Kolkata Knight Riders">Kolkata Knight Riders</option>
                                    <option value="Mumbai Indians">Mumbai Indians</option>
                                    <option value="Rajasthan Royals">Rajasthan Royals</option>
                                    <option value="Royal Challengers Bangalore">Royal Challengers Bangalore</option>
                                    <option value="Sunrisers Hyderabad">Sunrisers Hyderabad</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Big Bash League-/-</i></font></option>
                                    <option value="Adelaide Strikers">Adelaide Strikers</option>
                                    <option value="Brisbane Heat">Brisbane Heat</option>
                                    <option value="Hobart Hurricanes">Hobart Hurricanes</option>
                                    <option value="Melbourne Renegades">Melbourne Renegades</option>
                                    <option value="Melbourne Stars">Melbourne Stars</option>
                                    <option value="Perth Scorchers">Perth Scorchers</option>
                                    <option value="Sydney Sixers">Sydney Sixers</option>
                                    <option value="Sydney Thunder">Sydney Thunder</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Bangladesh Premiere League-/-</i></font></option>
                                    <option value="Chittagong Vikings">Chittagong Vikings</option>
                                    <option value="Comilla Victorians">Comilla Victorians</option>
                                    <option value="Dhaka Dynamites">Dhaka Dynamites</option>
                                    <option value="Rajshahi Kings">Rajshahi Kings</option>
                                    <option value="Melbourne Stars">Melbourne Stars</option>
                                    <option value="Rangpur Riders">Rangpur Riders</option>
                                    <option value="Sylhet Sixers">Sylhet Sixers</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Caribbean Premiere League-/-</i></font></option>
                                    <option value="Barbados Tridents">Barbados Tridents</option>
                                    <option value="St Kitts and Nevis Patriots">St Kitts and Nevis Patriots</option>
                                    <option value="Guyana Amazon Warriors">Guyana Amazon Warriors</option>
                                    <option value="Jamaica Tallawahs">Jamaica Tallawahs</option>
                                    <option value="St Lucia Stars">St Lucia Stars</option>
                                    <option value="Trinbago Knight Riders">Trinbago Knight Riders</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Pakistan Super League-/-</i></font></option>
                                    <option value="Islamabad United">Islamabad United</option>
                                    <option value="Karachi Kings">Karachi Kings</option>
                                    <option value="Lahore Qalandars">Lahore Qalandars</option>
                                    <option value="Multan Sultans">Multan Sultans</option>
                                    <option value="Peshawar Zalmi">Peshawar Zalmi</option>
                                    <option value="Quetta Gladiators">Quetta Gladiators</option>
                        </select></td>
                </tr>
                <tr>
                    <td><label>Team Two:</label></td>
                            <td><select name="teamName2">
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-T20 Internationals-/-</i></font></option>
                                    <option value="Pakistan">Pakistan</option>    
                                    <option value="India">India</option>    
                                    <option value="South Africa">South Africa</option>
                                    <option value="England">England</option>
                                    <option value="Australia">Australia</option>
                                    <option value="New Zealand">New Zealand</option>
                                    <option value="West Indies"> West Indies</option>
                                    <option value="Afghanistan">Afghanistan</option>
                                    <option value="Sri Lanka">Sri Lanka</option>
                                    <option value="Bangladesh">Bangladesh</option>
                                    <option value="Scotland">Scotland</option>
                                    <option value="Zimbabwe">Zimbabwe</option>
                                    <option value="Netherlands">Netherlands</option>
                                    <option value="Nepal">Nepal</option>
                                    <option value="United Arab Emirates">United Arab Emirates</option>
                                    <option value="Hong Kong">Hong Kong</option>
                                    <option value="Ireland">Ireland</option>
                                    <option value="Oman">Oman</option>
                                    <option disabled="disabled" ><font color="white"><i>-/-IPL-/-</i></font></option>
                                    <option value="Chennai Super Kings">Chennai Super Kings</option>
                                    <option value="Delhi Daredevils">Delhi Capitals (Daredevils)</option>
                                    <option value="Kings XI Punjab">Kings XI Punjab</option>
                                    <option value="Kolkata Knight Riders">Kolkata Knight Riders</option>
                                    <option value="Mumbai Indians">Mumbai Indians</option>
                                    <option value="Rajasthan Royals">Rajasthan Royals</option>
                                    <option value="Royal Challengers Bangalore">Royal Challengers Bangalore</option>
                                    <option value="Sunrisers Hyderabad">Sunrisers Hyderabad</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Big Bash League-/-</i></font></option>
                                    <option value="Adelaide Strikers">Adelaide Strikers</option>
                                    <option value="Brisbane Heat">Brisbane Heat</option>
                                    <option value="Hobart Hurricanes">Hobart Hurricanes</option>
                                    <option value="Melbourne Renegades">Melbourne Renegades</option>
                                    <option value="Melbourne Stars">Melbourne Stars</option>
                                    <option value="Perth Scorchers">Perth Scorchers</option>
                                    <option value="Sydney Sixers">Sydney Sixers</option>
                                    <option value="Sydney Thunder">Sydney Thunder</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Bangladesh Premiere League-/-</i></font></option>
                                    <option value="Chittagong Vikings">Chittagong Vikings</option>
                                    <option value="Comilla Victorians">Comilla Victorians</option>
                                    <option value="Dhaka Dynamites">Dhaka Dynamites</option>
                                    <option value="Rajshahi Kings">Rajshahi Kings</option>
                                    <option value="Melbourne Stars">Melbourne Stars</option>
                                    <option value="Rangpur Riders">Rangpur Riders</option>
                                    <option value="Sylhet Sixers">Sylhet Sixers</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Caribbean Premiere League-/-</i></font></option>
                                    <option value="Barbados Tridents">Barbados Tridents</option>
                                    <option value="St Kitts and Nevis Patriots">St Kitts and Nevis Patriots</option>
                                    <option value="Guyana Amazon Warriors">Guyana Amazon Warriors</option>
                                    <option value="Jamaica Tallawahs">Jamaica Tallawahs</option>
                                    <option value="St Lucia Stars">St Lucia Stars</option>
                                    <option value="Trinbago Knight Riders">Trinbago Knight Riders</option>
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-Pakistan Super League-/-</i></font></option>
                                    <option value="Islamabad United">Islamabad United</option>
                                    <option value="Karachi Kings">Karachi Kings</option>
                                    <option value="Lahore Qalandars">Lahore Qalandars</option>
                                    <option value="Multan Sultans">Multan Sultans</option>
                                    <option value="Peshawar Zalmi">Peshawar Zalmi</option>
                                    <option value="Quetta Gladiators">Quetta Gladiators</option>
                        </select></td>
                </tr>
                <tr><td><label>Ground Name: </label></td>
                    <td>
                    <select name="groundName">
                                            <option value="Adelaide Oval">Adelaide Oval</option>
                                            <option value="Ted Summerton Reserve, Moe">Ted Summerton Reserve, Moe</option>
                                            <option value="Wankhede Stadium, Mumbai">Wankhede Stadium, Mumbai</option>
                                            <option value="Docklands Stadium, Melbourne">Docklands Stadium, Melbourne</option>
                                            <option value="Manuka Oval, Canberra">Manuka Oval, Canberra</option>
                                            <option value="Melbourne Cricket Ground">Melbourne Cricket Ground</option>
                                            <option value="M.Chinnaswamy Stadium, Bengaluru">M.Chinnaswamy Stadium, Bengaluru</option>
                                            <option value="Eden Gardens, Kolkata">Eden Gardens, Kolkata</option>
                                            <option value="Perth Stadium">Perth Stadium</option>
                                            <option value="Carrara Oval">Carrara Oval</option>
                                            <option value="Rajiv Gandhi International Stadium, Uppal, Hyderabad">Rajiv Gandhi International Stadium, Uppal, Hyderabad</option>
                                            <option value="Simonds Stadium, South Geelong, Victoria">Simonds Stadium, South Geelong, Victoria</option>
                                            <option value="Holkar Cricket Stadium, Indore">Holkar Cricket Stadium, Indore</option>
                                            <option value="Maharashtra Cricket Association Stadium, Pune">Maharashtra Cricket Association Stadium, Pune</option>
                                            <option value="Sydney Showground Stadium">Sydney Showground Stadium</option>
                                            <option value="Sawai Mansingh Stadium, Jaipur">Sawai Mansingh Stadium, Jaipur</option>
                                            <option value="Brian Lara Stadium, Tarouba, Trinidad">Brian Lara Stadium, Tarouba, Trinidad</option>
                                            <option value="Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh">Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh</option>
                                            <option value="Aurora Stadium, Launceston">Aurora Stadium, Launceston</option>
                                            <option value="Providence Stadium, Guyana">Providence Stadium, Guyana</option>
                                            <option value="Geelong Cricket Ground">Geelong Cricket Ground</option>
                                            <option value="MA Chidambaram Stadium, Chepauk, Chennai">MA Chidambaram Stadium, Chepauk, Chennai</option>
                                            <option value="Brisbane Cricket Ground, Woolloongabba, Brisbane">Brisbane Cricket Ground, Woolloongabba, Brisbane</option>
                                            <option value="Feroz Shah Kotla, Delhi">Feroz Shah Kotla, Delhi</option>
                                            <option value="Bellerive Oval, Hobart">Bellerive Oval, Hobart</option>
                                            <option value="Sydney Cricket Ground">Sydney Cricket Ground</option>      
                    </select>
                        </td>
                
                </tr>
            
                <tr><td span="2"></td></tr>
            </table>
                <input type="submit" value="Calculate Data Points"/>
            
            
            
            
            
        </form>
         <form action="OdiMatchServlet" method="get">
            <h3>Format: ODI </h3>
            <table>

                         <td><label>Team One:</label></td>
                            <td><select name="teamName1">
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-ODI Internationals-/-</i></font></option>
                                    <option value="India">India</option>    
                                    <option value="Pakistan">Pakistan</option>
                                    <option value="South Africa">South Africa</option>
                                    <option value="England">England</option>
                                    <option value="Australia">Australia</option>
                                    <option value="New Zealand">New Zealand</option>
                                    <option value="West Indies"> West Indies</option>
                                    <option value="Afghanistan">Afghanistan</option>
                                    <option value="Sri Lanka">Sri Lanka</option>
                                    <option value="Bangladesh">Bangladesh</option>
                                    <option value="Scotland">Scotland</option>
                                    <option value="Zimbabwe">Zimbabwe</option>
                                    <option value="Netherlands">Netherlands</option>
                                    <option value="Nepal">Nepal</option>
                                    <option value="United Arab Emirates">United Arab Emirates</option>
                                    <option value="Hong Kong">Hong Kong</option>
                                    <option value="Ireland">Ireland</option>
                                    <option value="Oman">Oman</option>
                                                            </select></td>
                </tr>
                <tr>
                    <td><label>Team Two:</label></td>
                            <td><select name="teamName2">
                                    <option disabled="disabled" ><font color="#FFFFFF"><i>-/-T20 Internationals-/-</i></font></option>
                                    <option value="Pakistan">Pakistan</option>    
                                    <option value="India">India</option>    
                                    <option value="South Africa">South Africa</option>
                                    <option value="England">England</option>
                                    <option value="Australia">Australia</option>
                                    <option value="New Zealand">New Zealand</option>
                                    <option value="West Indies"> West Indies</option>
                                    <option value="Afghanistan">Afghanistan</option>
                                    <option value="Sri Lanka">Sri Lanka</option>
                                    <option value="Bangladesh">Bangladesh</option>
                                    <option value="Scotland">Scotland</option>
                                    <option value="Zimbabwe">Zimbabwe</option>
                                    <option value="Netherlands">Netherlands</option>
                                    <option value="Nepal">Nepal</option>
                                    <option value="United Arab Emirates">United Arab Emirates</option>
                                    <option value="Hong Kong">Hong Kong</option>
                                    <option value="Ireland">Ireland</option>
                                    <option value="Oman">Oman</option>
                                                            </select></td>
                </tr>
                <tr><td><label>Ground Name: </label></td>
                    <td>
                    <select name="groundName">
                                            <option value="Adelaide Oval">Adelaide Oval</option>
                                            <option value="Ted Summerton Reserve, Moe">Ted Summerton Reserve, Moe</option>
                                            <option value="Wankhede Stadium, Mumbai">Wankhede Stadium, Mumbai</option>
                                            <option value="Docklands Stadium, Melbourne">Docklands Stadium, Melbourne</option>
                                            <option value="Manuka Oval, Canberra">Manuka Oval, Canberra</option>
                                            <option value="Melbourne Cricket Ground">Melbourne Cricket Ground</option>
                                            <option value="M.Chinnaswamy Stadium, Bengaluru">M.Chinnaswamy Stadium, Bengaluru</option>
                                            <option value="Eden Gardens, Kolkata">Eden Gardens, Kolkata</option>
                                            <option value="Perth Stadium">Perth Stadium</option>
                                            <option value="Carrara Oval">Carrara Oval</option>
                                            <option value="Rajiv Gandhi International Stadium, Uppal, Hyderabad">Rajiv Gandhi International Stadium, Uppal, Hyderabad</option>
                                            <option value="Simonds Stadium, South Geelong, Victoria">Simonds Stadium, South Geelong, Victoria</option>
                                            <option value="Holkar Cricket Stadium, Indore">Holkar Cricket Stadium, Indore</option>
                                            <option value="Maharashtra Cricket Association Stadium, Pune">Maharashtra Cricket Association Stadium, Pune</option>
                                            <option value="Sydney Showground Stadium">Sydney Showground Stadium</option>
                                            <option value="Sawai Mansingh Stadium, Jaipur">Sawai Mansingh Stadium, Jaipur</option>
                                            <option value="Brian Lara Stadium, Tarouba, Trinidad">Brian Lara Stadium, Tarouba, Trinidad</option>
                                            <option value="Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh">Punjab Cricket Association IS Bindra Stadium, Mohali, Chandigarh</option>
                                            <option value="Aurora Stadium, Launceston">Aurora Stadium, Launceston</option>
                                            <option value="Providence Stadium, Guyana">Providence Stadium, Guyana</option>
                                            <option value="Geelong Cricket Ground">Geelong Cricket Ground</option>
                                            <option value="MA Chidambaram Stadium, Chepauk, Chennai">MA Chidambaram Stadium, Chepauk, Chennai</option>
                                            <option value="Brisbane Cricket Ground, Woolloongabba, Brisbane">Brisbane Cricket Ground, Woolloongabba, Brisbane</option>
                                            <option value="Feroz Shah Kotla, Delhi">Feroz Shah Kotla, Delhi</option>
                                            <option value="Bellerive Oval, Hobart">Bellerive Oval, Hobart</option>
                                            <option value="Sydney Cricket Ground">Sydney Cricket Ground</option>      
                    </select>
                        </td>
                
                </tr>
            
                <tr><td span="2"></td></tr>
            </table>
                <input type="submit" value="Calculate Data Points"/>
            
            
            
            
            
        </form>   
            
            
         
    </body>
</html>
