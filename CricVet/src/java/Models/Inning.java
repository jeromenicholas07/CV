/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

public class Inning {
    
    List<String> params;

    public Inning(List<String> params) {
        this.params = params;
    }
    
    public List<String> getParams() {
        return params;
    }
    public void setParams(List<String> params) {
        this.params = params;
    }
    
    
}
