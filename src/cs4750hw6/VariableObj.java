/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw6;

/**
 *
 * @author Joey Crowe
 */
public class VariableObj {
    private int varNum;//may not be neccessary
    public boolean value;
    public boolean isSet;
    private int domain;
    //-1 is no values left, 0 is only zero (false) left, 1 is only 1 (true) left, 2 is 0 and 1 left
    private int degree;//used in tie breaking for selection
    
    public VariableObj(int varNum){
        this.varNum = varNum;
        this.isSet = false;
        this.domain = 2;
        this.degree = 0;
    }
    
    public VariableObj(VariableObj varObj){//copies object, keeps domain, resets degree to zero
        this.varNum = varObj.getVarNum();
        this.isSet = varObj.isSet;
        this.value= varObj.value;
        this.domain = varObj.getDomain();
        this.degree=0;
    }
    
    public int getDegree(){
        return degree;
    }
    
    public void setDomain(int x){
        this.domain = x;
       
    }
    
    public boolean reduceFalseDomain(){//Reduces False Domain and Returns True if the domain is empty
        if (this.domain == 2){
            this.domain = 1;
            return false;
        } else if (this.domain == 0){
            this.domain = -1;
            return true;
        } else {
            return false;
        }
    }
    
    
    public int getVarNum(){
        return varNum;
    }
    
    public int getComparisonNumber(){//return negative if false, positive if true, 
        //idea is to subtract constraint number from this comparison number
        //ex, if constraint is -4 and this is false, -4 - (-4) == 0 which will evaluate to true
        //otherwise, number will not equal zero and return false
        if (value == false){//return negative if false
            return varNum * -1 ;
        } else {
            return varNum;
        }//note, must not check unless set, maybe should add a check
    }
    
    public int getDomain(){
        return domain;
    }
    public boolean reduceTrueDomain(){//Reduces True Domain and returns true is the domain is empty, else false
        if (this.domain == 2){
            this.domain = 0;
            return false;
        } else if (this.domain == 1){
            this.domain = -1;
            return true;
        } else {
            return false;
        }
    }
    
    public void incrementDegree(){
        this.degree++;
    }
    
}
