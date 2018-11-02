/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4750hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Joey Crowe
 */
public class CS4750HW6 {

    public static int variableCount = 0; //these static variables used for the Scatter Plot
    public static boolean[] TFArray = new boolean[200];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {//assuming good input, otherwise, will behave unpredictably
        // TODO code application logic here
        final long startTime = System.currentTimeMillis();
        Node parentNode = null;
        File inFile = null;
        if (0 < args.length) {
           inFile = new File(args[0]);
        } else {
           System.err.println("Invalid arguments count:" + args.length);
           System.exit(1);
        }
        BufferedReader br = null;
        try {
            String currentLine, removeLeadingWhiteSpaceLine;
            String regex = "^\\s+";
            br = new BufferedReader(new FileReader(inFile));
            currentLine = br.readLine();//read first line
            String delims = "[ ]+";
            String[] tokens = currentLine.split(delims);
            int varCount = Integer.parseInt(tokens[2]);
            int consCount = Integer.parseInt(tokens[3]);
            //System.out.println(varCount);
            parentNode = new Node(varCount);
            for (int i = 0; i < consCount; i++){
                currentLine = br.readLine();
                removeLeadingWhiteSpaceLine = currentLine.replaceAll(regex, "");
                tokens = removeLeadingWhiteSpaceLine.split(delims);
                int x,y,z;
               //System.out.println(Arrays.toString(tokens));
                x = Integer.parseInt(tokens[0]);
                y = Integer.parseInt(tokens[1]);
                z = Integer.parseInt(tokens[2]);
              //  System.out.println(x + " " + y + " " + z);
                parentNode.addConstraint(x,y,z);
            }
            
        } catch (IOException e){
            System.out.print(e);
        } finally{
            try {if (br != null)br.close();}
            catch (Exception ex) {
                System.out.print("Error");
            }
        }
        //input hopefully finished
        
        Node result;
        result = parentNode.recursiveBacktracking(parentNode);
        if (result == null){
            System.out.println("No solution found");
            System.out.println("Assignments tried: "+ Node.nodeCount);
            
        } else {
            System.out.println("Solution Found!");
            System.out.println("Assignments tried: "+ Node.nodeCount);
            variableCount = result.varCount;
            for (int i=1; i <= result.varCount; i++){
                System.out.println(i + ": " + result.variableObjList.get(i).value);
                TFArray[i] = result.variableObjList.get(i).value;
            }
        }
        
        buildScatterPlot();
        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) );
        
    }
    
    public static void buildScatterPlot(){
        System.out.print("\n\t\t\tResults\n\n1 |");
        for(int i=0; i <= variableCount; i++){
            if(TFArray[i] == true){
                System.out.print("o");
            }else{
                System.out.print(" ");
            }
        }
        System.out.print("\n  |\n  |\n  |\n  |\n  |\n  |\n  |\n  |\n  |\n  |\n  |\n0 |");
        for(int i=0; i <= variableCount; i++){
            if(TFArray[i] == false){
                System.out.print("o");
            }else{
                System.out.print(" ");
            }
        }
        System.out.print("\n  ");
        for(int i=0; i <= variableCount; i++){
            System.out.print("-");
        }
        System.out.print("\n  ");
        for(int i=0; i <= variableCount; i=i+20){
            System.out.print(i + "                  ");
        }
        System.out.print("\n\n");
        System.out.print("\tX-Axis: Variable ID     Y-Axis: True(1) or False(0)\n\n\n");
                    
    }
    
}
