/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb;

import ca.mcmaster.instructiontreedynamiclb.cplex.BranchingCondition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tamvadss
 */
public class Leaf {
    public List<BranchingCondition> branchingConditionList = new ArrayList<BranchingCondition>();
    public String id;
    //toString
    public String printMe(){
        String result = ""+id;
        for (BranchingCondition  branchingCondition: branchingConditionList){
            result += " ["+branchingCondition.printMe() +"], ";
        }
        System.out.println(result);
        return result;
    }
    
    
}
