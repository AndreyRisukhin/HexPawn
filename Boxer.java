/*
Title: Boxer.java
Author: Andrey Risukhin
Purpose:
Psuedocode:
Maintenance Log:
9/9/2020
- Created to assist recreating Boxes.txt with mirrored boxes.
9/11/2020
- First attempt at efficient file writing code.
9/13/2020
- I need the following structure to repeat:
   > Name of machine
   > ID  states   X
   > XX
   >
9/14/2020
- Implemented new loop system for writing to file. It makes more intuitive sense,
   and now I need to type less! :)
- Added reverseString method and check for state reversibility method.
*/
import java.util.*;
import java.io.*;

public class Boxer {
   // File io, throws exceptions
   public static void main(String[] args) throws FileNotFoundException {
      
      Scanner console = new Scanner(System.in);
      File outFile = new File(getFile(console));
      PrintStream toOutput = new PrintStream(outFile);
      
      int machineCount = 0;
      
      boolean addingMachines = true;
      
      while (addingMachines) {
         machineCount++;
         // Name of machine
         
         boolean workingOnMachine = true;
         int boxesCount = 0;
         while (workingOnMachine) {
            boxesCount++;
            // Iteration ID ("A01")  
            int stateCount = 0; 
            boolean mirrorable = false; 
            boolean addingStates = true; 
            while (addingStates) {
               stateCount++;
               String a = console.next(); // Each row of box is stored a/b/c
               String b = console.next(); // Later formatted as needed by method
               String c = console.next(); // User inputs x/x/x when done
               String state = makeState(a, b, c);
               
               // User adds X when done with line
               if (state.equals("X")) {
                  addingStates = false;
               }
               
               toOutput.print("\t" + state);
               System.out.print("\t" + state);
               
               // If detect the state can be reversed AND stateCount == 1, 
               // create a second box mirrored automatically at the end
               if (stateCanMirror(a, b, c)) {
                  if (stateCount == 1) {
                     mirrorable = true;
                  } else {
                     // Do not add the mirrored state, it would teleport the board
                     // When encoding, make sure to show every move possible
                  }
               }
            } // End of adding states
            
            // THIS is where to add a new mirrored box         
            if (mirrorable) {
               // Create mirrored box
               boxesCount++;
               // Iteration ID
                  
               // X
               mirrorable = false;
            }

            
         } // End of working on machine
         // XX
      } // End of adding machines (file done)                       
      
      /*
      
      boolean done = false;
      boolean machineFinished = false;
      String machineName = "";
      
      while (!done) { // While writing to the file
         
         System.out.println();
         machineName = console.next();
         while (!machineFinished) { // While working on a single machine
         
            System.out.println("What are you adding to file? ");
            String filter = console.next();
            if (filter.equals("done")) {
               machineFinished = true;
            } else { // Example: BBB---WWW
               
               // Write A#\tB_B_B -_-_- W_W_W\t...
               
            }      
         
         }        
         
         
         
         System.out.println("If you are done with the file, type \"done\".");
         String response = console.next();
         if (response.equals("done")) {
            done = true;
         }
      }  
      */
      
      // Close the file
       
   }
   
   // Pre: Passed 3 Strings.
   // Post: Returns boolean. True if a || b || c is different when reversed.
   public static boolean stateCanMirror (String a, String b, String c) {
      // Check if any of three strings is different if mirrored
      boolean canMirror = false;
      if (!reverseString(a).equals(a) || !reverseString(b).equals(b) || 
            !reverseString(c).equals(c)) {
         canMirror = true;
      }
      return canMirror;
   }
   
   // Pre: Passed a String.
   // Post: Returns the reverse of that String.
   public static String reverseString(String s) {
      String reversed = "";
      int len = s.length();
      for (int i = 0; i < len; i++) {
         reversed = reversed + s.substring(len - 1 - i, len - i);
      }
      return reversed;
   }
   
   // Pre: Passed 3 Strings.
   // Post: Returns String. Formatted versions of a, b, c. 
   public static String makeState(String a, String b, String c) {
      String state = "";
      if (a.equals("x")) { // Done with state
         state = "X";
      } else {
         state = a.substring(0,1) + "_" + a.substring(1,2) + "_" + a.substring(2,3) + " "
                + b.substring(0,1) + "_" + b.substring(1,2) + "_" + b.substring(2,3) + " "
                + c.substring(0,1) + "_" + c.substring(1,2) + "_" + c.substring(2,3);
         state = state.toUpperCase();
      }    
      return state;
   }
   
   // Pre: Passed Scanner attached to user input.
   // Post: Gets the name of the file used to create the boxes file.
   public static String getFile(Scanner console) {
      System.out.println("What is the name of the file to build the boxes file? (remember .txt)");
      String fileName = console.next();
      return fileName;
   } 
}