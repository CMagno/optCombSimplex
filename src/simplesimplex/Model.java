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
    
    public static final byte MIN = -1;
    public static final byte MAX = 1;
    
    private int nVars;
    private int nFVars;
    private int nConst;
    private byte obj;
    
    private double[][] tableau;
    
    public Model(byte obj){
        this.obj = obj;
    }
}
