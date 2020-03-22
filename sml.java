/*
Author: Andrey Risukhin
Purpose: An object to store information about moves for each box of HexPawn
Pseudocode: Map Strings (board states) to a list of Strings (possible states)
Maintenance Log:
3/2/20
- Created Class. Much simpler way to simulate Hexapawn
- Store board state as 3 by 3 grid: 
   B B B
   - - -
   W W W
3/3/20
- Created machineA.
- Deciding how to store the information, it would be great to be able to build it
    up from a file.
- For now, input file is hardcoded.
- Working on parser to create a map of keys and values. 
3/20/20
- Put parser in a seperate method, buildMachine(machine, fileScanner).
- Game engine checks for win conditions, stalemate conditions. Not active yet.
- Issues reading in more than one box, no way to know that end of line reached. 
- Added "X" as End Of Line character in boxes.txt.
- Note: Will have checking for box labels to still be A# for machine A. 
3/21/20
- Completed A Machine encoding, started B Machine encoding (boxes.txt)
- Add some error checking code? To make sure everything is encoded correctly (_ where there should be, 
*/

import java.util.*;
import java.io.*;

public class sml {

   public static void main(String args[]) throws FileNotFoundException {      
      boolean play = false;
      Map<String, List<String>> machineA = new TreeMap<String, List<String>>(); // Maps board states to possible future states
      Map<String, List<String>> machineB = new TreeMap<String, List<String>>(); // Same, player 2
      
      Scanner console = new Scanner(System.in);
      String version = "1.0";      
      
      welcome();
      // File inFile = new File(getFile(console));
      
      Scanner inputA = new Scanner(new File("boxes.txt"));
      machineA = buildMachine("A", machineA, inputA);
      Scanner inputB = new Scanner(new File("boxes.txt")); // Reference Semantics, new Scanner needed like this  
      machineB = buildMachine("B", machineB, inputB); // I know a machine is bounded by title and XX, but method can only return one, machine can't be referenced and even if it can this is clearer
      
      // System.out.println("Would you like to play a game?");
      
      char turn = 'A';
      while(play) { // Game loop: Move, check for Win, check for Stalemate (run these as method(char turn))
         // Make a move
         if (turn == 'A') {
            // Check for Stalemate
               // if (machineA.contains(boardState) == FALSE)        // Redundant to have for both machines
            // machineA moves
         } else if (turn == 'B') {
            // Check for Stalemate
               // if (machineB.contains(boardState) == FALSE)
            // machineB moves
         } 
         // Check for a win
            // if (boardState.substring(top row has W) || boardState.substring(no B on board))
               // machineA win
            // else if (boardState.substring(bottom row has B) || boardState.substring(no W on board)
               // machineB win
      }
   }
   
   // Pre: Passed machineA, Scanner input connected to file to build machineA.
   // Post: Returns completed machineA.
   public static Map<String, List<String>> buildMachine(String id, Map<String, List<String>> machine, Scanner input) throws FileNotFoundException {
      boolean newMachineTitle = true;
      while (input.hasNextLine()) {
         //boolean machineScanDone = true;
         if (newMachineTitle) {
            System.out.println("Title of Machine: " + input.nextLine());
            newMachineTitle = false;
            //machineScanDone = false;
         } else {
//            while(input.hasNextLine() /*&& !machineScanDone*/) { // Each Key / Value List
               String boxName = input.next();
               if (boxName.equals("XX")) { // if the end of a machine is reached
                  newMachineTitle = true;
               } else if (id.equals(boxName.substring(0,1))) { // If box is for this machine
                  // Add box to machine
                  System.out.println("Box: " + boxName);
                  String key = input.next(); // First 1/3 of key
                  System.out.println(key);
                  for (int i = 0; i < 2; i++) { // Other 2/3 Key
                     String token = input.next();
                     System.out.println(token);
                     key += " " + token;
                  }
                  System.out.println("Key: " + key);
                  machine.put(key, new ArrayList<String>()); // Key (current boardState)    
                                            // maps to ArrayList of possible boardStates.        
                  System.out.println();
                  boolean thereAreValuesLeft = true;
                  while(input.hasNext() && thereAreValuesLeft) { // Value(s) combining
                     String value = input.next();
                     System.out.println(value);
                     if (value.substring(0, 1).equals("X")) {
                        thereAreValuesLeft = false;
                        if (value.equals("XX")) {
                           newMachineTitle = true;   
                        }               
                     } else {            
                        for (int i = 0; i < 2; i++) { // Combines values (rows of board) in groups of 3
                           String token = input.next(); // a Value (row of board)
                           System.out.println(token);
                           value += " " + token; 
                        }
                        System.out.println("Value: " + value); // A complete board state.      
                        machine.get(key).add(value); // Saves complete board state.
                     }
                  }    
                  System.out.println();
               } else {
                  // Skip, check next box
                  input.nextLine(); // Move scanner to next line
               }  
            //}
         }
      }  
      return machine;
   }
   
   // Pre: Passed Scanner attached to user input.
   // Post: Gets the name of the file used to create the HexPawn tree.
   public static String getFile(Scanner console) {
      System.out.println("What is the name of the file to build the HexPawn tree? (remember .txt)");
      String fileName = console.next();
      return fileName;
   }
   
   // Post: Welcomes the user.
   public static void welcome() {
      System.out.println("Welcome to the Simple Machine Learning Game!");
      System.out.println("You'll be able to play games of HexPawn, varying "); 
      System.out.println("several settings to see how different models learn.");
      System.out.println();
   }
}