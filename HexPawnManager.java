/*
Author: Andrey Risukhin
Purpose: Build a decisional tree out of HexPawnBoxes
Psudeocode:
Maintenace Log:
12/26/19
- Created.
- Added some fields and commented structure, will see what I end up needing.
- Will link without being given links, can do this by checking states and matching them. 
- HexPawnManager will contain a main method. It will build the tree. Version 1.0.
12/27/19
- Reward/Punish happens when going back up the tree through prev link, modify as ascending. 
   > Even remembers what move (a/b/c/d) was made this way!
   
*/

import java.util.*; // For Scanner
import java.io.*; // For File processing


// Input File must be structured: id turn boardState aCount a(id) bCount b(id) cCount c(id) dCount d(id) prev
public class HexPawnManager {
   
   /*
   // Fields
   private HexPawnBox machineA; // Odd moves
   private HexPawnBox machineB; // Even moves
   private HexPawnBox currentGame; // Current games state
   private HexPawnBox overallRoot; // First move possible
   */
   
   public static void main(String args[]) throws FileNotFoundException {
      
      Scanner console = new Scanner(System.in);
      String version = "1.0";
         
      welcome();
      File inFile = new File(getFile(console));
            
      // make the tree
   
      // Read in file
      // Create node
      // Create next node
      // Link second to node to first, link first node to second (double link)
   
   // recursive play method
      // if win
         // end
      // else 
         // recur
   
   }
   
   // Post: Creates the HexPawn Tree.
   public static HexPawnBox buildTree(File inFile) throws FileNotFoundException {
      Scanner input = new Scanner(inFile);
      
      HexPawnBox overallRoot = new HexPawnBox("1", 1, 1); // Just for compiling
      
      if (input.hasNextLine()) { // If a HexPawnBox needs to be created
         // Make First box
         int id = input.nextInt();
         int turn = input.nextInt();
         String boardState = input.next();
         int aCount = input.nextInt();
         int aID = input.nextInt();
         HexPawnBox a = 
      }
      
      while (input.hasNext()) { // If more HexPawnBoxes need to be created
         // Make boxes
      }
      
      
      return overallRoot;
   }
   
   // Pre: Given input File, int ID of the box it's looking for.
   // Post: Returns a HexPawnBox for that ID.
   public static HexPawnBox recursiveBuildTree(File inFile, int id) {
      HexPawnBox box = new HexPawnBox();
      Scanner findRow = new Scanner(inFile);
      String currRow = findRow.nextLine();
      boolean rowFound = false;
      while (!rowFound) {
         Scanner token = new Scanner(currRow);
         String tempID = token.next();
         if (tempID.equals("" + id)) {
            rowFound = true;
            int turn = token.nextInt();
            String boardState = token.next();
            
            box.setID(id);
            box.setTurn(turn);
            box.setState(boardState);
            
            if (token.hasNext()) {
               int aCount = token.nextInt();
               int aID = token.nextInt(); 
               HexPawnBox a = recursiveBuildTree(inFile, aID);
               box.a = a;
            }
            if (token.hasNext()) {
               int bCount = token.nextInt();
               int bID = token.nextInt(); 
               HexPawnBox b = recursiveBuildTree(inFile, bID);
               box.b = b;
            }
            if (token.hasNext()) {
               int cCount = token.nextInt();
               int cID = token.nextInt(); 
               HexPawnBox c = recursiveBuildTree(inFile, cID);
               box.c = c;
            }
            if (token.hasNext()) {
               int dCount = token.nextInt();
               int dID = token.nextInt(); 
               HexPawnBox d = recursiveBuildTree(inFile, dID);
               box.d = d;
            }

         } else {
            someRow = findRow.nextLine();
         }
      }
      
      // Scan tree for id
      // create and return a box for that id
      
      return box; 
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
      System.out.println("Welcome to the HexPawn Manager!");
      System.out.println("You'll be able to play games of HexPawn, varying "); 
      System.out.println("several settings to see how different models learn.");
      System.out.println();
   }
}