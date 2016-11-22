/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesimplex;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author carlosmagno
 */
public class Model {
    
    Tableau tableau;
    double fo_val;    
    
    public Model(Tableau tableau){
        this.tableau = tableau;
    }
    
    public double solve(){
        
        tableau.print();
        
        while(tableau.verifyStpCond()){
            tableau.pivot(tableau.getPivotElement());
            tableau.print();
        }
        
        return tableau.getFOValue();
    }
}
