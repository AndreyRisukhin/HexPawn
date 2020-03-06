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
- Working parser to create a map of keys and values. 

Map to list of strings
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
         System.out.println(key);
         
         machineA.put(key, new ArrayList<String>()); // ArrayList
                  
         System.out.println();
         while(input.hasNext()) { // Value(s)
            String value = "";
            for (int i = 0; i < 3; i++) { // a Value
               String token = input.next();
               System.out.println(token);
               value += token;
            }
            System.out.println(value);
            
            machineA.get(key).add(value);
         }    
         System.out.println();
      }
            
      // File inFile = new File(getFile(console));
         
      while(play) { // Game loop
      
      }
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