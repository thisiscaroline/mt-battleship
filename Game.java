package stbs;

/*
* Multithreaded Battleship
* First version: Singlethreaded Battleship against two players
*/

import java.util.*;

public class Game{
	
	static int count = 0; // debugging option
	static boolean debug = true; // debugging option
		
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		char[] alpha = {'A','B','C','D','E','F','G','H','I','J'};
		
		/**
		 * MT Note: Each player gets its own thread
		 */
		
		Player p1 = new Player();
		Player p2 = new Player();
		
		if (debug == true){ // debugging option
			
			p1.name = "Player 1 DEBUG";
			p2.name = "Player 2 DEBUG";
			
		} else {
			
			System.out.println("Enter your name here, Player 1: ");
			p1.name = sc.next();
			
			System.out.println("\nEnter your name here, Player 2: ");
			p2.name = sc.next();
			
		}	
		
		System.out.println("\nRound is between "+p1.name+" and "+p2.name+".\n"); // '\n' also works but need extra concat
		
		for (int i = 1; i < 11; i++){ // todo: move this up to constructor because why is it even here...?
			
			p1.shipboard[0][i] = Integer.toString(i);
			p2.shipboard[0][i] = Integer.toString(i);
			
			p1.shipboard[i][0] = Character.toString(alpha[i-1]);
			p2.shipboard[i][0] = Character.toString(alpha[i-1]);
			
		}
		
		p1.shipboard[0][10] = "10"; // ehhhhh why not
		p2.shipboard[0][10] = "10";
				
		/**
		 * MT note: this is where the other thread would
		 * sleep until all five ships are placed, then this
		 * thread would sleep and the other thread would
		 * wake up to place its ships
		 * */
		
		// todo: Allow players to choose ships to enter
		
		// p1 places ships
		p1.place("aircraft");
		p1.place("battleship");
		p1.place("destroyer");
		p1.place("submarine");
		p1.place("patrol");
		
		printBoard(p1.shipboard);
		
		// p2 places ships
		p2.place("aircraft");
		p2.place("battleship");
		p2.place("destroyer");
		p2.place("submarine");
		p2.place("patrol");
		
		printBoard(p2.shipboard);
		
	}
	
	public static void printBoard(String[][] board){ // prints a board
		
		for (int i = 0; i < 11; i++){
			
			for (int j = 0; j < 11; j++)
				System.out.print(board[i][j]+"  ");
			
			System.out.println("\n");
			
		}
		
	}

	
}