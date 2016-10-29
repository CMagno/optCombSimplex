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
    
    public static void readInputFile(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String objstr = br.readLine();
            
            ArrayList< ArrayList<Double> > dynTableau = new ArrayList<>();
            
            byte obj = 0;
            int nVars = 0;
            
            if((objstr == null) || ((!objstr.equalsIgnoreCase("+")) && (!objstr.equalsIgnoreCase("-")))){
                System.err.println("Input reading error. The first line must be equals '+' or '-'. LINE: " + objstr);
                return;
            }
            
            switch(objstr){
                case "+":
                    obj = Model.MAX;
                    break;
                case "-":
                    obj = Model.MIN;
                    break;
            }
            
            String objFuncLine = br.readLine();
            if(objFuncLine == null){
                System.err.println("Input reading error. The file has no objctive function.");
                return;
            }
            
            
            dynTableau.add(new ArrayList<>());
            String[] tokens = objFuncLine.split(" ");
            for(String token: tokens){
                try{
                    double coef = Double.parseDouble(token);
                    if(obj == Model.MAX){
                        coef *= -1;
                    }
                    dynTableau.get(0).add(coef);
                    nVars++;
                }catch(NumberFormatException nfe){
                    System.err.println("Input reading error. The objctive function must have only numbers.");
                    return;
                }
            }
            
            int cc = 1;
            String constrLine;
            while((constrLine = br.readLine()) != null){
                dynTableau.add(new ArrayList<>());
                String tokensConst[] = constrLine.split(" ");
                for(String token: tokensConst){
                    try{
                        double coef = Double.parseDouble(token);
                        dynTableau.get(cc).add(coef);
                    }catch(NumberFormatException nfe){
                        switch(token){
                            case "<=":
                                dynTableau.get(0).add(0.0);
                                for(int i = cc -1; i > 0; --i){
                                    double aux = dynTableau.get(i).get(dynTableau.get(i).size() - 1);
                                    dynTableau.get(i).remove(dynTableau.get(i).size() - 1);
                                    dynTableau.get(i).add(0.0);
                                    dynTableau.get(i).add(aux);
                                }
                                for(int i = cc; i > nVars - 1; --i){
                                    dynTableau.get(cc).add(0.0);
                                }
                                dynTableau.get(cc).add(1.0);
                                break;
                            case "=":
                                break;
                            case ">=":
                                dynTableau.get(0).add(0.0);
                                for(int i = cc -1; i > 0; --i){
                                    double aux = dynTableau.get(i).get(dynTableau.get(i).size() - 1);
                                    dynTableau.get(i).remove(dynTableau.get(i).size() - 1);
                                    dynTableau.get(i).add(0.0);
                                    dynTableau.get(i).add(aux);
                                }
                                for(int i = cc; i > nVars - 1; --i){
                                    dynTableau.get(cc).add(0.0);
                                }
                                dynTableau.get(cc).add(-1.0);
                                break;
                            default:
                                System.err.println("Input reading error. Invalid constraint syntax.");
                                return;
                        }
                    }
                }
                cc++;
            }
            
            dynTableau.get(0).add(0.0);
            
            System.out.println("GENERATED TABLEAU:");
            for(ArrayList<Double> linha: dynTableau){
                for(Double coe: linha){
                    System.out.print(coe + "\t");
                }
                System.out.println("");
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("Input reading error. =(");
            Logger.getLogger(InputManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Input reading error. =(");
            Logger.getLogger(InputManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
