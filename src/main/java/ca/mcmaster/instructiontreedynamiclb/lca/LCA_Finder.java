/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.lca;

import static ca.mcmaster.instructiontreedynamiclb.Constants.ZERO;
import ca.mcmaster.instructiontreedynamiclb.LCANode;
import ca.mcmaster.instructiontreedynamiclb.Leaf;
import ca.mcmaster.instructiontreedynamiclb.cplex.BranchingCondition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tamvadss
 */
public class LCA_Finder {
    
    public List<Leaf> leafCollection  = null;
    
    //startindex can be used to skip past condition sused in previuosly identified LCA nodes
    public int startIndex;
    
    //this is the lca node we identify
    public LCANode lcaNode = new LCANode();
     
    //this is the var just past the LCA vars, and split the collection of leafs into 2 sets
    public int splittingVariableIndex ;
    
    public LCA_Finder (List<Leaf> leafs, int startIndex) {
        this.leafCollection=leafs;
        this.  startIndex =   startIndex;
    }
    
    //get LCA node for this set of leafs    
    public LCANode getLCA (   ){
     
        for (int index=startIndex; ; index ++){
            //get the branching condition at index for every leaf
            //add to list of conditions for LCA if condition is common to all leafs
            
            BranchingCondition thisCondition = leafCollection.get(ZERO).branchingConditionList.get(index);
             
            boolean isMatching = true;
            for (Leaf leaf:leafCollection  ){
                if (! leaf.branchingConditionList.get(index).equals(thisCondition)){
                    isMatching=false;
                    break;
                }                
            }
            if (isMatching) {
                lcaNode.branchingConditionList.add(thisCondition);
            }else {
                //the first mismatch means we have already found the LCA node
                splittingVariableIndex = index;
                break;
            }
            
        }
        
        return lcaNode;
    }
    
    public void split (List<Leaf> leftSideleafs, List<Leaf> rightSideLeafs) {
        //use the splittingVariableIndex
        
        for (Leaf leaf:leafCollection  ){
            BranchingCondition thisCondition = leaf .branchingConditionList.get(splittingVariableIndex);
            if (thisCondition.isUpperBound){
                leftSideleafs.add(leaf);
            }else {
                rightSideLeafs.add(leaf);
            }
        }
    }
    
    //toString()
    public void printMe (){
        System.out.println("Printing LCA node "+this.lcaNode.printMe());
       
        System.out.println("splittingVariableIndex "+splittingVariableIndex);
    }
}
