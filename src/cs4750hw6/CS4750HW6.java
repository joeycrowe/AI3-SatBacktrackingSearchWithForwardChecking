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
            for (int i=1; i <= result.varCount; i++){
                System.out.println(i + ": " + result.variableObjList.get(i).value);
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) );
        

    }
    
}
