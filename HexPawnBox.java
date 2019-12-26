/*
Author: Andrey Risukhin
Purpose: An object to store information about moves for each box of HexPawn
Pseudocode:
Maintenance Log:
12/26/19
- Created Class.
- Recolored the box template, so that not having bCount (purple) implies not 
      having cCount, which implies not having dCount.
- Need a way to store the state of the board, and compare it to the actual game.
- Going to store it as a String, with 9 characters seperated by 8 underscores.
   > Each character is 0 (empty), B (black), or W (white)
   > Encoded as top row, middle row, bottom row, all left to right
- This is a tree, adding links between boxes would make rewarding/punishing easier
- Added the getMove() method, getState() method. 
- Nested constructors. 
- Not sure if I need state fields when add linking, will reduce as develop code. 
*/

import java.util.*; // For Random

public class HexPawnBox {
   
   // Fields
   private int aCount; // Count of the "beads" representing each move possible (green in experiment)
   private int bCount; // Purple in experiment
   private int cCount; // Orange in experiment
   private int dCount; // Black in experiment
   
   private int turn; // Keeps track of what turn the box may be played in (1 - 7)
   private int id;

   private String boardState; // Format: "1_2_3_4_5_6_7_8_9" top left corner -> bottom right corner, row by row
   
   private String aState; // Each of these is what the board will look like if corresponding count is chosen.
   private String bState;
   private String cState;
   private String dState; 
   
   private Random r; 
   
   public HexPawnBox a; // Link between boxes, based on aState (if chosen, aState leads to this box)
   public HexPawnBox b;
   public HexPawnBox c;
   public HexPawnBox d;
   public HexPawnBox prev; // Allows for traversing back up, to punish/reward accordingly
   
   // Post: Constructs HexPawnBox with four moves.
   public HexPawnBox(String boardState, int turn, int id, int a, String aState, 
         int b, String bState, int c, String cState, int d, String dState) {
      this(boardState, turn, id, a, aState, b, bState, c, cState);
      this.dCount = d;
      this.dState = dState; 
   }
   
   // Post: Constructs HexPawnBox with three moves.
   public HexPawnBox(String boardState, int turn, int id, int a, String aState, 
         int b, String bState, int c, String cState) {
      this(boardState, turn, id, a, aState, b, bState);
      this.cCount = c;
      this.cState = cState;  
   }
   
   // Post: Constructs HexPawnBox with two moves.
   public HexPawnBox(String boardState, int turn, int id, int a, String aState, 
         int b, String bState) {
      this(boardState, turn, id, a, aState);
      this.bCount = b;
      this.bState = bState; 
   }
   
   // Post: Constructs HexPawnBox with one move. The "overall" constructor.
   public HexPawnBox(String boardState, int turn, int id, int a, String aState) {
      this.boardState = boardState;
      this.turn = turn;
      this.id = id;
      this.aCount = a;
      this.aState = aState;
      this.bCount = 0;
      this.bState = null;
      this.cCount = 0;
      this.cState = null;
      this.dCount = 0;
      this.dState = null; 
      this.r = new Random();     
   }
   
   // Pre: Passed four int values.
   // Post: Changes this HexPawnBox's fields by that amount.
   public void changeProbability(int a, int b, int c, int d) {
      this.aCount += a;
      this.bCount += b;
      this.cCount += c;
      this.dCount += d;
      System.out.println("Box " + this.id + "has");
      System.out.println("\tA " + this.aCount);
      System.out.println("\tB " + this.bCount);
      System.out.println("\tC " + this.cCount);
      System.out.println("\tD " + this.dCount);
   }
   
   // Post: Returns the chosen String representing a move.
   public String getMove() {
      String move = "";
      int sum = this.aCount + this.bCount + this.cCount + this.dCount;
      int randomNumber = r.nextInt(sum) + 1; // +1 because includes 0, excludes bound; shift by one to fix
      
      // Break 100% into areas for each count, if randomNumber is in that area that count is chosen
      if (randomNumber > this.aCount) {
         if (randomNumber > (this.aCount + this.bCount)) {
            if (randomNumber > (this.aCount + this.bCount + this.cCount)) {
               if (randomNumber > (this.aCount + this.bCount + this.cCount + this.dCount)) {
                  System.out.println("Something went wrong, randomNumber was " + randomNumber + "and sum was " + sum);
               } else {
                  move = this.dState;
               }
            } else {
               move = this.cState;
            }
         } else {
            move = this.bState;
         }
      } else {
         move = this.aState;
      }
      return move;
   }
   
   // Post: Returns this box's board state.
   public String getState() {
      return this.boardState; 
   }
}