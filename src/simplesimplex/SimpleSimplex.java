/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesimplex;

import java.io.File;

/**
 *
 * @author carlosmagno
 */
public class SimpleSimplex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tableau tableau = InputManager.readInputFile(new File("instances/inst4"));
        Model model = new Model(tableau);
        
        System.out.println("Optimal Solution: " + model.solve());
    }
    
}
