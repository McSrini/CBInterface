/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.cplex;

import static ca.mcmaster.instructiontreedynamiclb.Constants.*;
import static ca.mcmaster.instructiontreedynamiclb.Constants.TWO;
import   ca.mcmaster.instructiontreedynamiclb.lca.LCA_Finder;
import static ca.mcmaster.instructiontreedynamiclb.Constants.ZERO;
import ca.mcmaster.instructiontreedynamiclb.LCANode;
import ca.mcmaster.instructiontreedynamiclb.Leaf;
import static ca.mcmaster.instructiontreedynamiclb.Parameters.FRONTIER_SIZE;
import ca.mcmaster.instructiontreedynamiclb.lca.CBTree;
import ilog.concert.IloException;
import ilog.cplex.IloCplex.NodeCallback;
import static java.lang.System.exit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tamvadss
 */
public class Nodehandler extends NodeCallback {
    
    public List<Leaf> leafs = new ArrayList<Leaf>();
     
    protected void main() throws IloException {
        long remainingNodeCount=getNremainingNodes64() ;
        if (remainingNodeCount>=FRONTIER_SIZE) {
            //loop thru all nodes and collect them
            System.out.println("Leaf collection started at "+ LocalDateTime.now());  
            for (long nodeNumber = ZERO; nodeNumber < remainingNodeCount;nodeNumber++ ){
                //
                NodePayload payload = (NodePayload)getNodeData(  nodeNumber) ;
                //
                //create a leaf by travelling up, and store the leaf
                Leaf thisLeaf = new Leaf ();
                thisLeaf.id =  getNodeId( nodeNumber  )  .toString();
                while (payload.branchingCondition!=null){
                    thisLeaf.branchingConditionList.add(payload.branchingCondition);
                    payload= payload.parent;
                }
                leafs.add(thisLeaf);
            }
            for (Leaf leaf : leafs){
                
                //get branching conditions is correct order, starting from first branch
                Collections.reverse(leaf.branchingConditionList);
                
                //leaf.printMe();
            }
            
            
            //test
            
            //removeLeavesForTesting () ;
            
            //end test
            
            
            
            
            
            abort();
        }
    }
    
    private void removeLeavesForTesting (){
        List<Integer> removalIndices = new ArrayList<Integer>() ;
        
        int index = ZERO;
        for (Leaf leaf : leafs){
            if ((leaf.id.equals("Node14"))||(leaf.id.equals("Node12"))||(leaf.id.equals("Node18"))) {
                removalIndices.add(index);
            }
            index ++;
        }
        Collections.reverse( removalIndices);
        for (int rem  : removalIndices){
            leafs.remove(rem);
        }
    }
    
   
    
}
