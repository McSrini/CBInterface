/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.instructiontreedynamiclb.cplex;

import static ca.mcmaster.instructiontreedynamiclb.Constants.*;
import ca.mcmaster.instructiontreedynamiclb.Leaf;
import static ca.mcmaster.instructiontreedynamiclb.Parameters.MIP_FILENAME;
import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import java.util.List;

/**
 *
 * @author tamvadss
 */
public class Cplex {
    
    public IloCplex cplex ;
    public List<Leaf> collectedLeafs;

    public Cplex() throws IloException {
        this.cplex = new IloCplex();
    }
    
    public void run () throws IloException {
        cplex.importModel(MIP_FILENAME);
        cplex.use (new BranchHandler()) ;
        Nodehandler nodehandler = new Nodehandler();
        cplex.use (nodehandler) ;
        cplex.setParam(IloCplex.Param.Emphasis.MIP,  3);
        
         cplex.setParam( IloCplex.Param.MIP.Strategy.File, 0 );
        cplex.setParam( IloCplex.Param.Threads, ONE);
        cplex.solve();
        
             
        collectedLeafs = nodehandler.leafs;
       
        
    }
        
    
}
