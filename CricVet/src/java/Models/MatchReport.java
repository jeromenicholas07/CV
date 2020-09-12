/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jerome Nicholas
 */
public class MatchReport {
    private String matchLink;
    private MatchStatus status;
    private Exception error;
    private Pattern matchIdFinder = Pattern.compile("/ci/engine/match/(.*)\\.html");

    public MatchReport(String matchLink, MatchStatus status, Exception error) {
        this.matchLink = matchLink;
        this.status = status;
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
    
    public String getErrorStackTrace() {
        if(error == null)
            return "NA";
        
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    
    public String getErrorMessage() {
        if(error == null)
            return "NULL";
        return error.getMessage();
    }
    
    public boolean hasErrorLink() {
        if(error == null)
            return false;
        
        if(error.getCause() == null)
            return false;
        
        return error.getCause().getMessage().matches("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
    }
    
    public String getErrorLink() {
        return error.getCause().getMessage();
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public String getMatchLink() {
        return matchLink;
    }

    public void setMatchLink(String matchLink) {
        this.matchLink = matchLink;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }
    
    public String getMatchId() {
        Matcher idMatcher = matchIdFinder.matcher(matchLink);
        if(idMatcher.find()){
            String currId = idMatcher.group(1);
            return currId;
        }
        else{
            return "N/A";
        }
    }
    
    
}
