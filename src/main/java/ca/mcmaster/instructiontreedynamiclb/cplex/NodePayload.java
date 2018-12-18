/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.cplex;

import ilog.cplex.IloCplex;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author tamvadss
 */
public class NodePayload implements IloCplex.MIPCallback.NodeData  {
    
      BranchingCondition branchingCondition ;
      NodePayload parent;

    
    public void delete() {
         
        //parent=null;
        //branchingCondition=null;
        //java will garbage collect it
    }
    
}
