/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.cplex;

import static ca.mcmaster.instructiontreedynamiclb.Constants.ZERO;
import ca.mcmaster.instructiontreedynamiclb.Leaf;

/**
 *
 * @author tamvadss
 */
public class BranchingCondition {
    
    public String var ;
    public Long bound ;
    public Boolean isUpperBound ;   //   cplex branch direction down
    
    //may not need a flag to indicate direction, may be  able to use a -ve sign to indicate lower bounds
    //but that may not be much faster
    
    public String printMe (){
        String result = "";
        result +=var + ",";
        result +=bound + ",";
        result +=isUpperBound ;
        return result;
    }
    
    public boolean equals (BranchingCondition other){
        boolean b1= var.equals(other.var) ;
                boolean b2= ZERO==Long.compare(bound,other.bound) ;
                boolean b3= (isUpperBound .equals(other.isUpperBound));
                return b1&&b2&&b3;
    }
}
