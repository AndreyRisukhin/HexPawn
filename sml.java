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
*/

import java.util.*;
import java.io.*;

public class sml {

   public static void main(String args[]) throws FileNotFoundException {
      
      boolean play = false;
      Map<String, List<String>> machineA = new TreeMap<String, List<String>>(); // Maps board states to possible future states
      
      Scanner console = new Scanner(System.in);
      String version = "1.0";      
      // String a = console.next();   
      welcome();
      Scanner input = new Scanner(new File("boxes.txt"));
      machineA = buildMachine(machineA, input);
      // File inFile = new File(getFile(console));
      char turn = 'A';
      while(play) { // Game loop
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
            // if (boardState.substring(top row has W) || boardState.substring(no B))
               // machineA win
            // else if (boardState.substring(bottom row has B) || boardState.substring(no W)
               // machineB win
      }
   }
   
   // Pre: Passed machineA, Scanner input connected to file to build machineA.
   // Post: Returns completed machineA.
   public static Map<String, List<String>> buildMachine(Map<String, List<String>> machineA, Scanner input) throws FileNotFoundException {
      if (input.hasNext()) {
         System.out.println("Title of File: " + input.nextLine());
      } 
      while(input.hasNextLine()) { // Each Key / Value List
         System.out.println("Box: " + input.next());
         String key = input.next(); // First 1/3 of key
         System.out.println(key);
         for (int i = 0; i < 2; i++) { // Other 2/3 Key
            String token = input.next();
            System.out.println(token);
            key += " " + token;
         }
         System.out.println("Key: " + key);
         machineA.put(key, new ArrayList<String>()); // Key (current boardState)    
                                   // maps to ArrayList of possible boardStates.        
         System.out.println();
         boolean thereAreValuesLeft = true;
         while(input.hasNext() && thereAreValuesLeft) { // Value(s) combining
            String value = input.next();
            System.out.println(value);
            if (value.equals("X")) {
               thereAreValuesLeft = false;
            } else {            
               for (int i = 0; i < 2; i++) { // Combines values (rows of board) in groups of 3
                  String token = input.next(); // a Value (row of board)
                  System.out.println(token);
                  value += " " + token; 
               }
               System.out.println("Value: " + value); // A complete board state.      
               machineA.get(key).add(value); // Saves complete board state.
            }
         }    
         System.out.println();
      }
      return machineA;
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