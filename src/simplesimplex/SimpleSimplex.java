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
        InputManager.readInputFile(new File("instances/inst2"));
        System.out.println("FIM");
    }
    
}
