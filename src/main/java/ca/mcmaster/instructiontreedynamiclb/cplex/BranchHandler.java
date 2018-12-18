/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.cplex;

import static ca.mcmaster.instructiontreedynamiclb.Constants.*;
import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.BranchDirection;

/**
 *
 * @author tamvadss
 */
public class BranchHandler extends IloCplex.BranchCallback{
    
        protected void main() throws IloException {
            if ( getNbranches()> ZERO ){  

                Long numReainingNodes = getNremainingNodes64();
                if (true) {
                     

                    IloCplex.NodeId thisNodeID = getNodeId(); 
                    boolean isMipRoot = ( thisNodeID.toString()).equals( MIP_ROOT_ID);

                    //get the node attachment for this node, any child nodes will accumulate the branching conditions

                    if (isMipRoot){
                        //root of mip

                        NodePayload data = new NodePayload (  );                 
                        setNodeData(data);                
                    } 

                    NodePayload nodeData = (NodePayload) getNodeData();

                    // vars needed for child node creation 
                    IloNumVar[][] vars = new IloNumVar[TWO][] ;
                    double[ ][] bounds = new double[TWO ][];
                    IloCplex.BranchDirection[ ][]  dirs = new  IloCplex.BranchDirection[ TWO][];

                    //get the branches CPLEX is about to make
                    getBranches(  vars,  bounds,   dirs);

                    //create the kids
                    NodePayload zeroChildData = new NodePayload();
                    BranchingCondition zeroChildCOndition = new BranchingCondition();

                    zeroChildCOndition.var=vars[ZERO][ZERO].getName();
                    zeroChildCOndition.bound= Math.round(bounds[ZERO][ZERO]);
                    boolean isUpper = BranchDirection.Down.equals (dirs[ZERO][ZERO]);
                    zeroChildCOndition.isUpperBound=isUpper;               

                    zeroChildData.parent=nodeData;
                    zeroChildData.branchingCondition=( zeroChildCOndition);



                    NodePayload oneChildData = new NodePayload();   
                    BranchingCondition oneChildCondition = new BranchingCondition ();

                    oneChildCondition.bound= Math.round(bounds[ONE][ZERO]);
                    oneChildCondition.isUpperBound          = !isUpper;
                    oneChildCondition.var=vars[ONE][ZERO].getName();

                    oneChildData.parent=nodeData;
                    oneChildData.branchingCondition= (oneChildCondition) ;


                    double lpEstimate = getObjValue();
                    makeBranch( vars[ZERO],  bounds[ZERO],dirs[ZERO],  lpEstimate  , zeroChildData );
                    makeBranch( vars[ONE],  bounds[ONE],dirs[ONE],   lpEstimate, oneChildData ); 
                }//end else
                
                
                   
            }
        }
    
}
