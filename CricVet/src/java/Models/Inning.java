/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

public class Inning {
    
    int inningId;
    int noOfParams;
    List<String> params;

    public Inning(int noOfParams, List<String> params) {
        this.noOfParams = noOfParams;
        this.params = params;
    }
    public Inning(int inningId, int noOfParams, List<String> params) {
        this.inningId = inningId;
        this.noOfParams = noOfParams;
        this.params = params;
    }
    
    
    public int getInningId() {
        return inningId;
    }
    public void setInningId(int inningId) {
        this.inningId = inningId;
    }

    public int getNoOfParams() {
        return noOfParams;
    }
    public void setNoOfParams(int noOfParams) {
        this.noOfParams = noOfParams;
    }

    public List<String> getParams() {
        return params;
    }
    public void setParams(List<String> params) {
        this.params = params;
    }
    
    
}
