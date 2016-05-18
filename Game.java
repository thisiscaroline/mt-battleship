package stbs;

/*
* Multithreaded Battleship
* First version: Singlethreaded Battleship against two players
*/

import java.io.*;
import java.util.*;

class Player{
	
	String name;
	
	char[] aircraft = {'A', 'A', 'A', 'A', 'A'};
	char[] battleship = {'B', 'B', 'B', 'B'};
	char[] destroyer = {'D', 'D', 'D'};
	char[] submarine = {'S', 'S', 'S'};
	char[] patrol = {'P', 'P'};
	
	String[][] scoreboard = new String[11][11];
	boolean[][] verify = new boolean[11][11];
	
	public Player(){
		
		/*
		this.name = name;
		this.aircraft = aircraft;
		this.battleship = battleship;
		this.destroyer = destroyer;
		this.submarine = submarine;
		this.patrol = patrol;
		*/
		
		for (int i = 0; i < 11; i++){
			for (int j = 0; j < 11; j++){
				this.scoreboard[i][j] = "-";
				this.verify[i][j] = false;
			}
		}
		
		this.scoreboard[0][0] = " ";
		
		// todo: true-ify verify borders
		
	}

	public void place(char[] ship) {
		
		char[] alpha = {'A','B','C','D','E','F','G','H','I','J'};
		String head, tail;
		int h1 = -1, h2, t1 = -1, t2;
		
		Scanner put = new Scanner(System.in);
		
		System.out.print("Position the head of your ship: ");
		head = put.next();
		
		System.out.print("Position the tail of your ship: ");
		tail = put.next();
				
		if (head.length() != 2 || tail.length() != 2){
			
			if (head.substring(1,3).equals("10") || tail.substring(1,3).equals("10")){
				; // do nothing, this is legal
			} else {
				System.out.println("You have entered an incorrect input.");
				System.exit(1); // for now
			}
			
		}
			
		for (int i = 1; i < alpha.length+1; i++){
			if ((Character.toString(alpha[i-1])).equalsIgnoreCase(head.substring(0, 1))){ // yikes
				h1 = i;
			}
			if ((Character.toString(alpha[i-1])).equalsIgnoreCase(tail.substring(0, 1))){
				t1 = i;
			}
		}
		
		h2 = Integer.parseInt(head.substring(1));
		t2 = Integer.parseInt(tail.substring(1));
		
		if (h1 > 10 || t1 > 10){ // runs over the border
			System.out.println("Your ship is over the board's border!");
			System.exit(1);
		}
		
		if (h2 == 0 || t2 == 0){ // runs over the border
			System.out.println("Your ship is over the board's border!");
			System.exit(1);
		}
		
		System.out.println("\nYour inputs are "+head+" and "+tail+". This has become positions "+h1+", "+h2+" and "+t1+", "+t2+".\n");
		
		if (h1 == -1 || t1 == -1){ // letter not found
			System.out.println("Your ship is over the board's border!");
			System.exit(1);
		}
		
		if (h1 != t1 && h2 != t2){ // diagonal
			System.out.println("Error: You cannot place a ship diagonally!");
			System.exit(1);
		} else {
			
			int diff = -1;
			
			if (h1 == t1){ // same row, horizontal positioning
				
				diff = Math.abs(h2 - t2);
				
			} else if (h2 == t2){ // same column, vertical positioning
				
				diff = Math.abs(h1 - t1);
				
			}
			
			if (diff+1 != ship.length){ // +1 to count the initial square
				System.out.println("Your ship position is invalid, please try again!");
				System.exit(1);
			} else {
				
				char c = ship[0]; // teeeechnically more efficient re: memory caching
				
				int start, stop;
				
				if (h1 == t1){ // place the ship in the same row
					
					start = Math.min(h2, t2);
					stop = Math.max(h2, t2);
					
					// first need to check that the space is allowed
					
					for (int i = start; i <= stop; i++){
						
						if (verify[h1][i] == true){
							System.out.println("Error: cannot place ship on this square!\n");
							System.exit(1);
						} else {
							verify[h1][i] = true; 
							// scoreboard[h1][i] = Character.toString(c); // CAN put this here but may need to erase pieces so prob not
						}
						
					}
					
					// if here, we made it past the verification stage
					for (int i = start; i <= stop; i++){
						scoreboard[h1][i] = Character.toString(c);
					}
					
				} else if (h2 == t2){ // place the ship in the same column
					
					start = Math.min(h1, t1);
					stop = Math.max(h1, t1);
					
					for (int i = start; i <= stop; i++){
						
						if (verify[i][h2] == true){
							System.out.println("Error: cannot place ship on this square!\n");
							System.exit(1);
						} else {
							verify[i][h2] = true;
						}
						
					}
					
					// passed verification stage
					for (int i = start; i <= stop; i++){
						scoreboard[i][h2] = Character.toString(c);
					}
					
				}
				
			}
			
		}
		
		//put.close(); check to see if it's called the right number of times before closing?
		
	}
	
}


public class Game{
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		char[] alpha = {'A','B','C','D','E','F','G','H','I','J'};
		
		Player p1 = new Player();
		Player p2 = new Player();
		
		System.out.println("Enter your name here, Player 1: ");
		p1.name = sc.next();
		
		System.out.println("\nEnter your name here, Player 2: ");
		p2.name = sc.next();
		
		System.out.println("\nRound is between "+p1.name+" and "+p2.name+".\n"); // '\n' also works but need extra concat
		
		for (int i = 1; i < 11; i++){ // todo: move this up to constructor because why is it even here...?
			
			p1.scoreboard[0][i] = Integer.toString(i);
			p2.scoreboard[0][i] = Integer.toString(i);
			
			p1.scoreboard[i][0] = Character.toString(alpha[i-1]);
			p2.scoreboard[i][0] = Character.toString(alpha[i-1]);
			
		}
		
		p1.scoreboard[0][10] = (String)"10"; // ehhhhh why not
		p2.scoreboard[0][10] = (String)"10";
		
		/*
		printBoard(p1.scoreboard);
		System.out.println("\n");
		printBoard(p2.scoreboard);
		*/
		
		// Pick a position on the board to place your ships
		
		p1.place(p1.aircraft);
		p1.place(p1.battleship);
		
		printBoard(p1.scoreboard);
		
	}
	
	public static void printBoard(String[][] board){ // prints a board
		
		for (int i = 0; i < 11; i++){
			for (int j = 0; j < 11; j++){
				
				System.out.print(board[i][j]+"  ");
				
			}
			
			System.out.println();
			System.out.println();
			
		}
		
	}

	
}