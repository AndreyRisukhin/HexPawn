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
9/8/2020
- Added gameDesire(), queries user if they would like to play.
- Removed some debug for boxes, now machine outputs keys and their values as it constructs them.
- Added moveTranscript (List<String>). This is a record of every move played in a 
   game. After a game concludes, it is used to reward/penalize the machines by 
   adding/removing some amount of their values (board states). Ex: board state
   A_B_C was lossy for machine A, that value is found and removed from the box
   it was played from. 
- Began work on move mechanic. Use boardState as a key in machine who is moving,
   then choose a value uniformly at random to become the new boardState.
9/9/2020
- Idea for UI: At end of each game, split moveTranscript to 3, print them to 
   show sequence of moves. Easier for user to understand this way.
- Create printState() method to display board state in a friendly way.
- Currently, creating constants to reward/punish machine score. Eventually, 
   prompt user if they want to set different weights for different machines.
- BUG: A move of "B_B_B -_W_- W_-_W" registers as a stalemate in B. I suspect
   B is incorrectly storing information from Boxes.txt
- Bug fixed.
- New issue: because mirrored boxes are not included, I need to make sure Boxes.txt
   has the same side of the mirror for all boxes. Two positions can be practically 
   identical but have a different key because they are flipped. Should I just 
   include mirrors? They are unique if not distinct moves.
- Yes. This is a big issue. I'll just make duplicate boxes. TO DO
*/

import java.util.*; // Includes .Random
import java.io.*;

public class sml {

   public static void main(String args[]) throws FileNotFoundException {      
      Map<String, List<String>> machineA = new TreeMap<String, List<String>>(); // Maps board states to possible future states
      Map<String, List<String>> machineB = new TreeMap<String, List<String>>(); // Same, player 2
      
      final String INITIAL_STATE = "B_B_B -_-_- W_W_W"; // The starting state of board for all games (key for first machineA box)
      
      Random rand = new Random();
       
      Scanner console = new Scanner(System.in);
      String version = "1.0";      
      
      welcome();
      // File inFile = new File(getFile(console));
      
      // Create the machines.
      Scanner inputA = new Scanner(new File("boxes.txt"));
      machineA = buildMachine("A", machineA, inputA);
      Scanner inputB = new Scanner(new File("boxes.txt")); // Reference Semantics, new Scanner needed like this  
      machineB = buildMachine("B", machineB, inputB); // I know a machine is bounded by title and XX, but method can only return one, machine can't be referenced and even if it can this is clearer
      
      // Create supporting variables.
      boolean play = playDesire(console); // True -> play a game, False -> do not.
      boolean inAGame = play; // If user wants to play, start a new game.
      boolean aCanMove = true;
      boolean bCanMove = true;
      List<String> moveTranscript = new ArrayList<String>(); // Records all moves in a game. Used to reward/penalize machines.    
      char turn = 'A'; // Machine A always plays first.
      int moveCount = 0; // Keeps track of the move.
      String boardState = INITIAL_STATE; // The state of the current board.
      
      final double STALEMATE_POINTS_A = 0.5; // Reward machines for stalemate
      final double STALEMATE_POINTS_B = 0.5;
      final double VICTORY_POINTS_A = 1;
      final double VICTORY_POINTS_B = 1;
      final double LOSS_POINTS_A = 0;
      final double LOSS_POINTS_B = 0;
      
      double aScore = 0;
      double bScore = 0;
      
      printState(boardState, moveCount);
      
      while(play) { 
         moveCount = 0;
         while(inAGame) { // Game loop: Move, check for Win, check for Stalemate (run these as method(char turn))
            // Make a move
            if (turn == 'A') {     
               // machineA moves
               boardState = makeMove(boardState, machineA, rand);
            } else if (turn == 'B') {
               // machineB moves
               boardState = makeMove(boardState, machineB, rand); 
            } else {
               System.out.println("Error: game loop - char turn != A or B.");
            }
            // By updating transcript after a move, transcript alternates A, B, A, B, ...
            moveCount++;
            moveTranscript.add(boardState); // Add the move to the transcript.
            printState(boardState, moveCount);
            // System.out.println("Move transcript: " + moveTranscript);
            
            aCanMove = canMove(machineA, boardState);
            bCanMove = canMove(machineB, boardState);
            
            // Check for a win, one or both can move
            if (boardState.substring(0,5).contains("W") || !boardState.contains("B")) { 
               // Top row has W || no B on board -> machineA win
               aScore += VICTORY_POINTS_A;
               bScore += LOSS_POINTS_B;
               System.out.println("A score: " + aScore);
               inAGame = false;
            } else if (boardState.substring(12,17).contains("B") || !boardState.contains("W")) { 
               // Bottom row has B || no W on board -> machineB win
               bScore += VICTORY_POINTS_B;
               aScore += LOSS_POINTS_A;
               System.out.println("B score: " + bScore);
               inAGame = false;
            }
            if (inAGame && !aCanMove && !bCanMove) { // Check for a stalemate now, neither can move, and not a win
               aScore += STALEMATE_POINTS_A;
               bScore += STALEMATE_POINTS_B;
               System.out.println("Stalemate! A score: " + aScore + ", B score: " + bScore);
               inAGame = false;
            }
            
            turn = nextTurn(turn); // If A went, B goes; if B went, A goes.
         }
         play = playDesire(console); // Check if they want to play another game
         if (play) {
            boardState = INITIAL_STATE;
            turn = 'A';
            printState(boardState, moveCount);
         }
      }
   }
   
   // Pre: Passed machine, Scanner input connected to file to build all machine, String specifying machine to build.
   // Post: Returns completed machineA.
   public static Map<String, List<String>> buildMachine(String id, Map<String, List<String>> machine, Scanner input) throws FileNotFoundException {
      // Get to the correct section of boxes
      String current = input.next();
      while (!current.equals(id)) {
         current = input.next();
      }
      // Build the machine
      boolean newMachineTitle = true;
      while (input.hasNextLine()) {
         if (newMachineTitle) {
            System.out.println("Title of Machine: " + current + " " + input.nextLine());
            newMachineTitle = false;
         } else {
            String boxName = input.next();
            if (boxName.equals("XX")) { // if the end of a machine is reached
               newMachineTitle = true;
            } else if (id.equals(boxName.substring(0,1))) { // If box is for this machine
               // Add box to machine
               String key = input.next(); // First 1/3 of key
               for (int i = 0; i < 2; i++) { // Other 2/3 Key
                  String token = input.next();
                  key += " " + token;
               }
               System.out.println("Key: " + key);
               machine.put(key, new ArrayList<String>()); // Key (current boardState)    
                                         // maps to ArrayList of possible boardStates.        
               System.out.println();
               boolean thereAreValuesLeft = true;
               while(input.hasNext() && thereAreValuesLeft) { // Value(s) combining
                  String value = input.next();
                  if (value.substring(0, 1).equals("X")) {
                     thereAreValuesLeft = false;
                     if (value.equals("XX")) {
                        newMachineTitle = true;   
                     }               
                  } else {            
                     for (int i = 0; i < 2; i++) { // Combines values (rows of board) in groups of 3
                        String token = input.next(); // a Value (row of board)
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
   
   // Pre: Takes scanner to user input.
   // Post: Returns true to play, false to stop.
   public static boolean playDesire(Scanner console) {
      System.out.println("Would you like to play a game? (Y/N) ");
      String response = console.next();
      response = response.substring(0,1).toUpperCase(); // Check 1st letter of response
      if (response.equals("Y")) {
         System.out.println("You have chosen to play. Let's play!");
         return true;
      } else {
         System.out.println("You have chosen not to play. Goodbye!");
         return false;
      }
   }
   
   // Pre: Takes current board position (String), machine who is moving. boardState 
   //       must be a key in machine!
   // Post: Returns new board position (String)
   public static String makeMove(String boardState, Map<String, List<String>> machine, Random rand) {
      // Find the possible values for key = boardState
      List<String> moveList = machine.get(boardState);
      // Uniformly at Random choose one of them
      int choice = rand.nextInt(moveList.size());
      String newState = moveList.get(choice);      
      return newState;
   }
   
   // Pre: Passed a char (A or B).
   // Post: Returns a char (B or A).
   public static char nextTurn(char turn) {
      if (turn == 'A') {
         return 'B';
      } else if (turn == 'B') {
         return 'A';
      } else {
         System.out.println("nextTurn was passed a character, not A or B.");
         return 'C';
      }
   }
   
   // Pre: Passed a String representing a board state ("_-_-_ _-_-_ _-_-_") in that form.
   // Post: Prints the String as a box label (3 lines, _-_-_ row on each).
   public static void printState(String boardState, int moveCount) {
      // Print substrings (0,5), (6,11), (12,17) -> (6i,6(i+1)-1), (6i,6(i+1)-1), (6i,6(i+1)-1)
      System.out.println("Move " + moveCount);
      for (int i = 0; i < 3; i++) {
         System.out.println(boardState.substring((6*i),(6*(i+1)-1)));
      }
   }   
   
   // Pre: Takes a machine and current boardState (String).
   // Post: Returns true if the boardState isn't a key in machine.
   public static boolean canMove(Map<String, List<String>> machine, String boardState) {
      if (machine.containsKey(boardState)) {
         return true; // Has key -> can make a move 
      } else {
         return false;
      } 
   }
}