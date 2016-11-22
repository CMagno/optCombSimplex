/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesimplex;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmagno
 */
public class InputManager {
    
    public static Tableau readInputFile(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String objstr = br.readLine();
            
            Tableau tableau = null;
            
            if((objstr == null) || ((!objstr.equalsIgnoreCase("+")) && (!objstr.equalsIgnoreCase("-")))){
                System.err.println("Input reading error. The first line must be equals '+' or '-'. LINE: " + objstr);
                return null;
            }
            
            switch(objstr){
                case "+":
                    tableau = new Tableau(Tableau.MAX);
                    break;
                case "-":
                    tableau = new Tableau(Tableau.MIN);
                    break;
            }
            
            String objFuncLine = br.readLine();
            if(objFuncLine == null){
                System.err.println("Input reading error. The file has no objctive function.");
                return null;
            }
            
            String[] tokens = objFuncLine.split(" ");
            for(String token: tokens){
                try{
                    tableau.addDVar(Double.parseDouble(token));
                }catch(NumberFormatException nfe){
                    System.err.println("Input reading error. The objctive function must have only numbers.");
                    return null;
                }
            }
            
            //int cc = 1; // tableau line index of constraint
            String constrLine;
            while((constrLine = br.readLine()) != null){
                tableau.newConstraint();
                String tokensConst[] = constrLine.split(" ");
                for(String token: tokensConst){
                    try{
                        tableau.addConstVarCoef(tableau.getCurTLine(), Double.parseDouble(token));
                    }catch(NumberFormatException nfe){
                        switch(token){
                            case "<=":
                                tableau.addSlackVar(tableau.getCurTLine(), Tableau.PLUS);
                                break;
                            case ">=":
                                tableau.addSlackVar(tableau.getCurTLine(), Tableau.MINUS);
                                break;
                            case "=":
                                tableau.addSlackVar(tableau.getCurTLine(), Tableau.NULL);
                                break;
                            default:
                                System.err.println("Input reading error. Invalid constraint syntax.");
                                return null;
                        }
                    }
                }
            }
            tableau.done();
            
            // tableau.print();
            
            return tableau;
            
        } catch (FileNotFoundException ex) {
            System.err.println("Input reading error. =(");
            Logger.getLogger(InputManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Input reading error. =(");
            Logger.getLogger(InputManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
