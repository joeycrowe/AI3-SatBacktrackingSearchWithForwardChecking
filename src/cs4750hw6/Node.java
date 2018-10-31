/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw6;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Joey Crowe
 */
public class Node {
    public ArrayList<VariableObj> variableObjList;
    public ArrayList<Constraint> constraintList;
    public int varCount;
    public static long nodeCount = 0;
    public Node(int varCount){
        variableObjList = new ArrayList<VariableObj>();
        constraintList = new ArrayList<Constraint>();
        VariableObj temp = new VariableObj(1);
        //System.out.println("x");
        variableObjList.add(temp);//maybe cannot have an empty index at 0, so making a blank space, could be a bug
        for (int i = 1; i <= varCount; i++){//order matters
            variableObjList.add(i, new VariableObj(i));
        }
        this.varCount = varCount;
    }
    
    public Node(Node node){//copy constructor
        variableObjList = new ArrayList<VariableObj>();
        constraintList = new ArrayList<Constraint>();
        for (int i = 0; i <= node.varCount; i++){
            this.variableObjList.add(i,new VariableObj(node.variableObjList.get(i)));//need to create a new object list for every depth of tree
            //space will be depth times list size, not large for given problems
        }
        for (int i=0; i < node.constraintList.size(); i++){
            this.constraintList.add(i,new Constraint(node.constraintList.get(i)));
        }
        this.varCount = node.varCount;
        nodeCount++;
    }
    
    public void addConstraint(int x, int y, int z){
        constraintList.add(new Constraint(x,y,z));
    }
    
    void printArray(){
        for (int i = 0; i <= varCount; i++){
            System.out.println( i + ": value=" + variableObjList.get(i).value + " isSet: " + variableObjList.get(i).isSet);
        }
    }
    public Node recursiveBacktracking(Node node){
        if (Node.nodeCount % 10000000 == 0){
            System.out.println("Node Count So Far: " + Node.nodeCount);
             System.out.println("Variables Affected By Forward Checking: "+ Constraint.forwardCheckingVariables);
            
        }
       
        if (node.isComplete()){
            // System.out.println("x");
            return node;
        } 
        
        Node tempNode = new Node(node);
        Node result = null;
        boolean isInvalid = tempNode.update();
        if (Node.nodeCount <= 11){
            System.out.println(Node.nodeCount - 1 + "Variables Affected By Forward Checking: "+ Constraint.forwardCheckingVariables);
            //Will check on the first empty node, but is fine
            Constraint.forwardCheckingVariables = 0;
        }
        //tempNode.printArray();
        if (isInvalid){
            //System.out.println("y");
            return null;
        }
       // System.out.println("z");
        int selectedVar = tempNode.selectNextVar();
       //System.out.println("!" + selectedVar);
        if (selectedVar == -1){
            return null;
        }
        
       // System.out.println(tempNode.variableObjList.get(selectedVar).getDomain());
        if (tempNode.variableObjList.get(selectedVar).getDomain() == 2){
            
            tempNode.variableObjList.get(selectedVar).isSet = true ;
            tempNode.variableObjList.get(selectedVar).value = false;
            //tempNode.printArray();
            if (Node.nodeCount <= 10){
                System.out.println(Node.nodeCount + "Variable Selected: " + selectedVar + " with value: " + false);
            } 
                
            result = recursiveBacktracking(tempNode);
            //System.out.println(result);
            if (result != null){
                
                return result;
            } else {//need to restore original domains, so am going to make 2 copies
               Node tempNode2 = new Node(node);
               tempNode2.variableObjList.get(selectedVar).isSet = true;
               tempNode2.variableObjList.get(selectedVar).value = true;
               
               if (Node.nodeCount <= 10){
                System.out.println(Node.nodeCount + "Variable Selected: " + selectedVar + " with value: " + true);
            } 
               //tempNode2.printArray();
               result = recursiveBacktracking(tempNode2);
               //System.out.print("x");
            }
            if (result != null){
                return result;
            }
            
        } else if ( tempNode.variableObjList.get(selectedVar).getDomain() == 1 ){//case for true only
            tempNode.variableObjList.get(selectedVar).isSet = true ;
            tempNode.variableObjList.get(selectedVar).value = true;
            if (Node.nodeCount <= 10){
                System.out.println(Node.nodeCount  + "Variable Selected: " + selectedVar + " with value: " + true);
            } 
            result = recursiveBacktracking(tempNode);
            if (result != null){
                return result;
            }
        } else if (tempNode.variableObjList.get(selectedVar).getDomain() ==  0){
            tempNode.variableObjList.get(selectedVar).isSet = true ;
            tempNode.variableObjList.get(selectedVar).value = false;
            if (Node.nodeCount <= 10){
                System.out.println(Node.nodeCount + "Variable Selected: " + selectedVar + " with value: " + false);
            } 
            result = recursiveBacktracking(tempNode);
            if (result != null){
                return result;
            }
        } else return null;
        return null;
    }
    public int selectNextVar(){
        int temp = 3;//will be 0 if none exist, 1 if domain size is 1, or 2 if domain size is 2, 3 will be unititialized
        int selectedIndex = 0;
        for (int i = 1; i <= this.varCount; i++){
            //System.out.println(i + " " + variableObjList.get(i).isSet);
            if (!variableObjList.get(i).isSet){
                if (temp == 3){
                    selectedIndex = i;
                    if (variableObjList.get(i).getDomain() == 2){
                        temp = 2;
                    } else if (variableObjList.get(i).getDomain() == 1 || variableObjList.get(i).getDomain() == 0){
                        temp = 1;
                    } else {
                        temp = 0;
                        return -1;//variable with no valid domain
                    }
                } else if (temp ==2){
                    if (variableObjList.get(i).getDomain() == 2){
                        if (variableObjList.get(selectedIndex).getDegree() < variableObjList.get(i).getDegree() ){
                            selectedIndex = i;
                        }
                    } else if (variableObjList.get(i).getDomain() == 1 || variableObjList.get(i).getDomain() == 0){
                        temp = 1;
                        selectedIndex = i;
                    } else {
                        temp = 0;
                        return -1;
                    }
                } else if (temp == 1){
                    if (variableObjList.get(i).getDomain() == 2){
                        //do nothing
                    } else if (variableObjList.get(i).getDomain() == 1 || variableObjList.get(i).getDomain() == 0){
                        if (variableObjList.get(selectedIndex).getDegree() < variableObjList.get(i).getDegree() ){
                            selectedIndex = i;
                        }
                    } else {
                        temp = 0;
                        return -1;
                    }
                } else {
                    return -1;
                }
            }
        }
        return selectedIndex;
    }
    
    public boolean update(){
        for (int i=0; i < this.constraintList.size(); i++){
            this.constraintList.get(i).checkAndUpdate(variableObjList);
            if (this.constraintList.get(i).invalid){
                return true;
            }
        }
        return false;
    }
    
    public boolean isComplete(){
        
        for (int i = 1; i <= this.varCount; i++){
            
            if (!variableObjList.get(i).isSet){//if not set
            //    System.out.println(i);
                return false;
            }
            
            
        }
        for (int i=0; i < constraintList.size(); i++){
            if (!constraintList.get(i).valid){//valid, unknown, invalid are the three possible values
                return false;
            }
        }
        return true;
    }
}
