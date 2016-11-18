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
    
    public static boolean MAX = true;
    public static boolean MIN = false;
    public static boolean PLUS = true;
    public static boolean MINUS = false;
    
    private ArrayList< ArrayList <Double> > tableau;
    private int curTabLine = 0, nVars = 0;
    private boolean max;
    
    public Tableau(boolean max){
        tableau = new ArrayList<>();
        tableau.add(new ArrayList<>());
        this.max = max;
    }
    
    public void addDVar(double foCoef){
        tableau.get(0).add((max) ? (-foCoef) : foCoef);
        nVars++;
    }
    
    public void newConstraint(){
        tableau.add(new ArrayList<>());
        ++curTabLine;
    }
    
    public void addConstVarCoef(int constLine, double coef){
        tableau.get(constLine).add(coef);
    }
    
    public void addSlackVar(int constLine, boolean plus){
        /*
        Add slack variable to the objective function
        */
        tableau.get(0).add(0.0);
        for(int i = constLine -1; i > 0; --i){
            /*
            For each previous constraint add a new 
            slack variable before the last element.
            Remove the last element and insert again
            after the slack coeficient.
            */
            double aux = tableau.get(i).get(tableau.get(i).size() - 1);
            tableau.get(i).remove(tableau.get(i).size() - 1);
            tableau.get(i).add(0.0);
            tableau.get(i).add(aux);
        }
        /*
        Add previous slack variables coeficients to
        the current constraint.
        */
        for(int i = constLine; i > nVars - 1; --i){
            tableau.get(constLine).add(0.0);
        }
        /*
        Add the own slack varibale to the current.
        constraint.
        */
        tableau.get(constLine).add((plus ? 1.0 : -1.0));
    }
    
    public void done(){
        tableau.get(0).add(0.0);
    }
    
    public int getCurTLine(){
        return curTabLine;
    }
    
    public void print(){
        System.out.println("GENERATED TABLEAU:");
        for(ArrayList<Double> linha: tableau){
            for(Double coe: linha){
                System.out.print(coe + "\t");
            }
            System.out.println("");
        }
    }
    
    public void verifyStpCond(){
        
    }
}
