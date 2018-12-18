/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.lca;

import static ca.mcmaster.instructiontreedynamiclb.Constants.ONE;
import ca.mcmaster.instructiontreedynamiclb.LCANode;
import ca.mcmaster.instructiontreedynamiclb.Leaf;
import ca.mcmaster.instructiontreedynamiclb.cplex.BranchingCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author tamvadss
 */
public class CBTree {
    
    public LCANode root;
     
    
    public CBTree leftCB=null;
    public CBTree rightCB=null;
    //small hack here, kids can be cbtrees or leaves
    public Leaf leftLeaf=null;
    public Leaf rightLeaf=null;
    
    //key = #of leafs rep, count of such lca nodes
    public static TreeMap<Integer, Integer> perfectLCANodeCollection= new TreeMap<Integer, Integer>();
     
    
    public CBTree ( LCANode root,
      CBTree left,
      CBTree right,
      Leaf leftLeaf,
      Leaf rightLeaf){
        
        this.root=root;
        this.leftCB=left;
        this.rightCB=right;
        this.leftLeaf=leftLeaf;
        this.rightLeaf=rightLeaf;
    }
    
    //lca node is perfectly packed if it has 2 single var branching conditions AND
    //any non leaf side is also perfectly packed
    public void markPerfectLCANodes (){
        if (leftCB!=null) leftCB.markPerfectLCANodes();
        if (rightCB!=null )rightCB.markPerfectLCANodes();
        
        boolean perfectPackingConditionFour= (leftCB!=null) && (rightCB!=null) && 
                                              (leftCB.root.isPerfectPacking) && 
                                              (rightCB.root.isPerfectPacking) &&
                                              ( leftCB.root.branchingConditionList.size()==ONE  )&&
                                               (rightCB.root.branchingConditionList.size()==ONE) ;
        
        boolean perfectPackingConditionThree= (leftLeaf==null) && (rightLeaf!=null) 
                && (rightLeaf.branchingConditionList.size()==ONE) && 
                ( leftCB.root.isPerfectPacking) && ( leftCB.root.branchingConditionList.size()==ONE  );
        
        boolean perfectPackingConditionTwo= (leftLeaf!=null) && (rightLeaf==null) 
                && (leftLeaf.branchingConditionList.size()==ONE) && 
                ( rightCB.root.isPerfectPacking) && (rightCB.root.branchingConditionList.size()==ONE);
                
                
        boolean perfectPackingConditionOne = (leftLeaf!=null) && (rightLeaf!=null) 
                && (leftLeaf.branchingConditionList.size()==ONE) && 
                (rightLeaf.branchingConditionList.size()==ONE) ;
        
        root.isPerfectPacking= perfectPackingConditionOne||perfectPackingConditionTwo||
                               perfectPackingConditionThree||perfectPackingConditionFour;
    }
    
    public void preparePerfectLCANodesMap (List<BranchingCondition> accumulatedConditions){
        if (root.isPerfectPacking){
            
            //System.out.println(root.printMe());
            for (BranchingCondition accumulatedCondition: accumulatedConditions){
                //System.out.println(accumulatedCondition.printMe());
            }
            
            Integer existingCount = this.perfectLCANodeCollection.get (root.numLeafsRepresented) ;
            this.perfectLCANodeCollection.put (root.numLeafsRepresented, existingCount ==null? ONE : (ONE+existingCount));
        }
        
        List<BranchingCondition> accumulatedConditionsLeft =  new ArrayList<BranchingCondition> () ;
        accumulatedConditionsLeft.addAll( accumulatedConditions );
        accumulatedConditionsLeft.addAll(root.branchingConditionList) ;
        if (leftCB!=null) {
            leftCB.preparePerfectLCANodesMap(accumulatedConditionsLeft);
        }
        
        
        List<BranchingCondition> accumulatedConditionsRight =  new ArrayList<BranchingCondition> () ;
        accumulatedConditionsRight.addAll( accumulatedConditions );
        accumulatedConditionsRight.addAll(root.branchingConditionList) ;
        if (rightCB!=null) {
            rightCB.preparePerfectLCANodesMap(accumulatedConditionsRight);
        }
    }
    
    //toString
    public void printMe (){
        System.out.println("LCa node at root "+root.printMe());
        
        
        if (leftCB!=null) leftCB.printMe();
        if (leftLeaf!=null) leftLeaf.printMe() ;
        if (rightCB!=null) rightCB.printMe();
        
        if (rightLeaf!=null) rightLeaf.printMe();
    }
}
