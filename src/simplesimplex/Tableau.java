/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesimplex;

import java.util.ArrayList;

/**
 *
 * @author carlosmagno
 */
public class Tableau {
    
    private ArrayList< ArrayList <Double> > tableau;
    private int tableauLineIdx = 0;
    private boolean max;
    
    public Tableau(boolean max){
        tableau = new ArrayList<>();
        tableau.add(new ArrayList<>());
    }
    
    public void addDVar(double foCoef){
        tableau.get(0).add((max) ? -foCoef : foCoef);
    }
    
    public void newConstraint(){
        tableau.add(new ArrayList<>());
        ++tableauLineIdx;
    }
    
    public void addConstVarCoef(double coef){
        tableau.get(tableauLineIdx).add(coef);
    }
    
    public void addSlackVar(){
        
    }
}
