/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesimplex;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
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
    private int curTabLine = 0, nVars = 0, nCols = 0, nLins = 0;
    private boolean max;
    
    public Tableau(boolean max){
        tableau = new ArrayList<>();
        tableau.add(new ArrayList<>());
        this.max = max;
        nLins++;
    }
    
    public void addDVar(double foCoef){
        tableau.get(0).add((max) ? (-foCoef) : foCoef);
        nVars++;
        nCols++;
    }
    
    public void newConstraint(){
        tableau.add(new ArrayList<>());
        ++curTabLine;
        ++nLins;
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
        nCols++;
    }
    
    public void done(){
        tableau.get(0).add(0.0);
        nCols++;
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
        //System.out.println("Cols: " + nCols);
    }
    
    private ArrayList<Double> getFO(){
        return tableau.get(0);
    }
    
    private double getFOCoef(int idx){
        if((idx < 0) || (idx > (nCols - 2))) return Double.NaN;
        return getFO().get(idx);
    }
    
    private double getConstrCoef(int cidx, int idx){
        if((idx < 0) || (idx > (nCols - 2))) return Double.NaN;
        return getConstr(cidx).get(idx);
    }
    
    private ArrayList<Double> getConstr(int idx){
        if(idx < 1) return null;
        return tableau.get(idx);
    }
    
    private double getConstrB(int idx){
        if(idx < 1) return Double.NaN;
        return tableau.get(idx).get(nCols - 1);
    }
    
    public int[] getPivotElement(){
        double minVarCol = Double.MAX_VALUE, minRatio = Double.MAX_VALUE;
        int column = -1;
        int line = -1;
        
        for(int var = 0; var < nVars; ++var){
            if(getFO().get(var) < minVarCol){
                column = var;
                minVarCol = getFO().get(var);
            }
        }
        
        for(int constr = 1; constr < nLins; ++constr){
            double ratio = (getConstrB(constr)) / getConstrCoef(constr, column);
            if(ratio < minRatio){
                line = constr;
                minRatio = ratio;
            }
        }
        System.out.println("Pivot ["+line+"]["+column+"]: "+ getCoef(line, column));
        return new int[]{line, column};
    }
    
    private double getCoef(int idxs[]){
        return getCoef(idxs[0], idxs[1]);
    }
    
    private double getCoef(int line, int column){
        return tableau.get(line).get(column);
    }
    
    private void setCoef(int line, int column, double newValue){
        tableau.get(line).set(column, new Double(newValue));
    }
    
    public void pivot(int pivotIdx[]){
        double pivotVal = getCoef(pivotIdx);
        
        for(int col = 0; col < nCols; ++col){
            setCoef(pivotIdx[0], col, (getCoef(pivotIdx[0], col) / pivotVal));
        }
        
        for(int lin = 0; lin < nLins; ++lin){
            if(lin == pivotIdx[0]){
               continue;
            }else{
                for(int col = 0; col < nCols; ++col){
                    setCoef(lin, col, getCoef(lin, col) - (getCoef(pivotIdx[0], col) * getCoef(lin, pivotIdx[1])));
                }
            }
        }
    }
    
    public double getFOValue(){
        return getFO().get(nCols - 1);
    }
    
    public boolean verifyStpCond(){
        for(int var = 0; var < nVars; ++var){
            if(getFOCoef(var) < 0) return true;
        }
        return false;
    }
}
