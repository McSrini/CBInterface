/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb;

import static ca.mcmaster.instructiontreedynamiclb.Constants.ZERO;
import ca.mcmaster.instructiontreedynamiclb.cplex.BranchingCondition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tamvadss
 */
public class LCANode {
    public List<BranchingCondition> branchingConditionList = new ArrayList<BranchingCondition>();
    
    public int numLeafsRepresented= ZERO;
    
    public boolean isPerfectPacking = false;
    
    //toString
    public String printMe(){
        String result = "numLeafsRepresented "+numLeafsRepresented;
        for (BranchingCondition  branchingCondition: branchingConditionList){
            result += " ["+branchingCondition.printMe() +"], ";
        }
        //System.out.println(result);
        return result;
    }
    
    
}
