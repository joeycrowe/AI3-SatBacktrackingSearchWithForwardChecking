/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw6;

import java.util.ArrayList;

/**
 *
 * @author Joey Crowe
 */
public class Constraint {//since all examples are 3-Sat, reduced for simplicity sake
    private int c1;//set these to positve or negative of the value, no order in particular
    private int c2;
    private int c3;
    public boolean valid;
    private int absc1;
    private int absc2;
    private int absc3;
    public boolean invalid;// may or may not need
    
    public static int forwardCheckingVariables = 0;
    
    //getters
    public int getC1(){
        return c1;
    }
    public int getC2(){
        return c2;
    }
    public int getC3(){
        return c3;
    }

    public Constraint(int x,int y, int z){
        this.c1 = x;
        this.c2 = y;
        this.c3 = z;
       // System.out.println(c1 + " " + c2 + " " + c3);
        this.valid = false;
        this.invalid = false;
        this.absc1 = Math.abs(x);//avoid repeat calls, not sure of actual speedup, but simplifies code
        this.absc2 = Math.abs(y);
        this.absc3 = Math.abs(z);
    }
    public Constraint(Constraint other){//copy constructor
       this(other.getC1(),other.getC2(),other.getC3());
        this.valid = other.valid;
        //this.invalid = other.invalid;
      
    }
    public void checkAndUpdate(ArrayList<VariableObj> array){
        boolean temp = false;
        if (array.get(absc1).isSet && array.get(absc2).isSet && array.get(absc3).isSet ){//all 3 are set
            if (c1 - array.get(absc1).getComparisonNumber() == 0 || c2 - array.get(absc2).getComparisonNumber() == 0 
                    || c3 - array.get(absc3).getComparisonNumber() == 0){
                valid = true;
            } else {
               // System.out.println("x1");
                invalid = true;
            }
        } else if (!array.get(absc1).isSet && array.get(absc2).isSet && array.get(absc3).isSet){//2 and 3 are set
            if (c2 - array.get(absc2).getComparisonNumber() == 0 || c3 - array.get(absc3).getComparisonNumber() == 0){
                valid = true;
            } else {
                forwardCheckingVariables++;
                array.get(absc1).incrementDegree();
                if (c1 == absc1){//posititive case
                    temp = array.get(absc1).reduceFalseDomain();
                } else {//negative case
                    temp = array.get(absc1).reduceTrueDomain();
                }
                if (temp){
                  //  System.out.println("x2");
                    invalid = true;
                }
            }
        } else if (array.get(absc1).isSet && !array.get(absc2).isSet && array.get(absc3).isSet){//1 and 3
            if (c1 - array.get(absc1).getComparisonNumber() == 0 || c3 - array.get(absc3).getComparisonNumber() == 0){
                valid = true;
            } else {
                forwardCheckingVariables++;
                array.get(absc2).incrementDegree();
                if (c2 == absc2){//posititive case
                    temp = array.get(absc2).reduceFalseDomain();
                } else {//negative case
                    temp = array.get(absc2).reduceTrueDomain();
                }
                if (temp){
                    
                   // System.out.println(array.get(absc1).isSet + " " + array.get(absc2).isSet + array.get(absc3).isSet);
                    //System.out.println("x3");
                    invalid = true;
                }
            }
        } else if (array.get(absc1).isSet && array.get(absc2).isSet && !array.get(absc3).isSet){//1 and 2
            if (c1 - array.get(absc1).getComparisonNumber() == 0 || c2 - array.get(absc2).getComparisonNumber() == 0){
                valid = true;
            } else {
                forwardCheckingVariables++;
                array.get(absc3).incrementDegree();
                if (c3 == absc3){//posititive case
                    temp = array.get(absc3).reduceFalseDomain();
                } else {//negative case
                    temp = array.get(absc3).reduceTrueDomain();
                }
                if (temp){
                    //System.out.println("x4");
                    invalid = true;
                }
            }
        } else if (array.get(absc1).isSet && !array.get(absc2).isSet && !array.get(absc3).isSet){//1 only set
            //do not need to reduce domain, only need to increment degree
            if (c1 - array.get(absc1).getComparisonNumber() == 0){
                valid = true;
            } else {
                array.get(absc2).incrementDegree();
                array.get(absc3).incrementDegree();
            }
        } else if (!array.get(absc1).isSet && array.get(absc2).isSet && !array.get(absc3).isSet){//2 only set
            //do not need to reduce domain, only need to increment degree
            if (c2 - array.get(absc2).getComparisonNumber() == 0){
                valid = true;
            } else {
                array.get(absc1).incrementDegree();
                array.get(absc3).incrementDegree();
            }
        } else if (!array.get(absc1).isSet && !array.get(absc2).isSet && array.get(absc3).isSet){//3 only set
            //do not need to reduce domain, only need to increment degree
            if (c3 - array.get(absc3).getComparisonNumber() == 0){
                valid = true;
            } else {
                array.get(absc1).incrementDegree();
                array.get(absc2).incrementDegree();
            }
        } else {
            array.get(absc1).incrementDegree();
            array.get(absc2).incrementDegree();
            array.get(absc3).incrementDegree();
         
        }
    }
}
