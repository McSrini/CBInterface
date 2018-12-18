/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb;

import static ca.mcmaster.instructiontreedynamiclb.Constants.FIFTY;
import static ca.mcmaster.instructiontreedynamiclb.Constants.HUNDRED;
import static ca.mcmaster.instructiontreedynamiclb.Constants.TWO;
import static ca.mcmaster.instructiontreedynamiclb.Constants.ZERO;
import ca.mcmaster.instructiontreedynamiclb.cplex.BranchingCondition;
import ca.mcmaster.instructiontreedynamiclb.cplex.Cplex;
import ca.mcmaster.instructiontreedynamiclb.lca.CBTree;
import ca.mcmaster.instructiontreedynamiclb.lca.LCA_Finder;
import ilog.cplex.IloCplex; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author tamvadss
 */
public class Driver {
    public static void main(String[] args) throws Exception {
        Cplex cplex = new Cplex();
        cplex.run();
        
        System.out.println("\n\nLeaf collection completed at "+ LocalDateTime.now());  
        
        
            
        CBTree cbTree =findCBInstructionTree(cplex.collectedLeafs, ZERO);
        //cbTree.printMe();

        System.out.println("Completed constructing CB instruction tree  at "+ LocalDateTime.now());  

        System.out.println(" \nMarking perfect LCA nodes   " ); 

        cbTree.markPerfectLCANodes () ;
        System.out.println("Completed marking perfect LCA nodes at "+ LocalDateTime.now());  

        System.out.println(" \nPrinting perfect LCA nodes   " ); 
        cbTree.preparePerfectLCANodesMap(new ArrayList<BranchingCondition>());
        Entry<Integer, Integer> lastEntry =cbTree.perfectLCANodeCollection.lastEntry();
        System.out.println("Size " + lastEntry.getKey() + " count "+lastEntry.getValue());
        int bestLCANodeSize = lastEntry.getKey();
        
        //print the number of perfect LCA nodes whose leaf membership is within 10% of the above LCA node
        int countOFLargeNodes = ZERO;
        for (Entry<Integer, Integer> entry  :cbTree.perfectLCANodeCollection.entrySet()){
            if (HUNDRED*entry.getKey()>=FIFTY*bestLCANodeSize) {
                // System.out.println("" +entry.getKey());
                countOFLargeNodes                +=entry.getValue();
            }
        }
        System.out.println("Coount of reasonably large perfect LCA nodes   "+ countOFLargeNodes + " perecentage size " + FIFTY ); 
         
        System.out.println("Completed printing perfect LCA nodes at "+ LocalDateTime.now());  
    }
     
     
    
    //for these leafs, skip past skipCount vars, then find lca node and the cb tree rooted at it
    private static CBTree findCBInstructionTree(List<Leaf> leafList,  int skipCount ){
      
        LCA_Finder lcaFinder = new LCA_Finder( leafList, skipCount);
        LCANode thisLca = lcaFinder.getLCA();
        thisLca.numLeafsRepresented=leafList.size();
        //lcaFinder.printMe();
        
        List<Leaf> leftSideleafs = new ArrayList<Leaf>();
        List<Leaf> rightSideleafs = new ArrayList<Leaf>();
        lcaFinder.split( leftSideleafs, rightSideleafs );
        
        //if any side has 2 or more leafs, we must find the lca node on that side
        //else just create the leaf and the pointer to it
        CBTree leftCB= null;
        CBTree rightCB=null;
        Leaf leftLeaf =null;
        Leaf rightLeaf = null;
        if (leftSideleafs.size()>=TWO) {
           leftCB = findCBInstructionTree( leftSideleafs, lcaFinder.splittingVariableIndex );
        }else{
            leftLeaf =leftSideleafs.get(ZERO);
            for (int index = ZERO; index <lcaFinder.splittingVariableIndex; index ++ ){
                leftLeaf.branchingConditionList.remove(ZERO);
            }
             
        }
        if (rightSideleafs.size()>=TWO){
            rightCB =findCBInstructionTree( rightSideleafs, lcaFinder.splittingVariableIndex);
        }else{
            rightLeaf =rightSideleafs.get(ZERO);
            for (int index = ZERO; index <lcaFinder.splittingVariableIndex; index ++ ){
                rightLeaf.branchingConditionList.remove(ZERO);
            }
             
        }
        
        
        return new CBTree(thisLca, leftCB, rightCB, leftLeaf, rightLeaf) ;
    }
    
    
     
    
}
