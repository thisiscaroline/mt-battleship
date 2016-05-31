package stbs;

import java.util.Scanner;

class Player extends Game{
	
	// boolean debug = true; // make false to turn off debugging options
	
	String name; // player name
	
	String[][] shipboard = new String[11][11]; // records this player's ship placement
	String[][] hitboard = new String[11][11]; // records this player's hits and misses
	boolean[][] verify = new boolean[11][11]; // checks whether the player can place a ship on the board
	
	public Player(){
		
		// Setting up shipboard, making boolean board all false
		for (int i = 0; i < 11; i++){
			for (int j = 0; j < 11; j++){
				this.shipboard[i][j] = "-";
				this.verify[i][j] = false;
			}
		}
		
		this.shipboard[0][0] = " ";
		
		// Make boolean borders true
		for (int i = 0; i < 11; i++){ 
			this.verify[0][i] = true;
			this.verify[i][0] = true;
		}
		
	}

	public void place(String ship) {
		
		char[] alpha = {'A','B','C','D','E','F','G','H','I','J'};
		String[] auto = {"A1", "A5", "B1", "B4", "C1", "C3", "D1", "D3", "E1", "E2", "J1", "J5", "I1", "I4", "H1", "H3", "G1", "G3", "F1", "F2"}; // debugging tool
		char c = 'X';
		String head, tail;
		int h1 = -1, h2, t1 = -1, t2;
		int length = -1;
		
		System.out.print("Placing ");
		
		switch (ship){
		
			case "aircraft":
				c = 'A';
				length = 5;
				System.out.print("aircraft.\n");
				break;
			case "battleship":
				c = 'B';
				length = 4;
				System.out.print("battleship.\n");
				break;
			case "submarine":
				c = 'S';
				length = 3;
				System.out.print("submarine.\n");
				break;
			case "destroyer":
				c = 'D';
				length = 3;
				System.out.print("destroyer.\n");
				break;
			case "patrol":
				c = 'P';
				length = 2;
				System.out.print("patrol boat.\n");
				break;
				
		}
		
		if (debug == true){ // debugging
			
			head = auto[count];
			count++;
			tail = auto[count];
			count++;		
			
		} else {
			
			Scanner put = new Scanner(System.in);
			
			System.out.print("Position the head of your ship: ");
			head = put.next();
			
			System.out.print("Position the tail of your ship: ");
			tail = put.next();
			
		}
						
		if (head.length() != 2 || tail.length() != 2){
			
			try {
				
				if (head.substring(1,3).equals("10") || tail.substring(1,3).equals("10")){
					; // do nothing, this is legal
				} else {
					System.out.println("You have entered an incorrect input.");
					System.exit(1); // for now
				}
				
			} catch (StringIndexOutOfBoundsException e){
				
				if (head.length() == 3){
					if (head.substring(1,3).equals("10"))
						; // do nothing, this is legal
				}
				
				if (tail.length() == 3){
					if (tail.substring(1,3).equals("10"))
						; // do nothing, this is legal
				}
				
				if (debug == true)
					System.out.println("StringIndexOOBEx handled"); // debug message
				
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
		
		if (h1 == -1 || t1 == -1){ // letter not found
			System.out.println("Your ship is over the board's border!");
			System.exit(1);
		}
		
		h2 = Integer.parseInt(head.substring(1));
		t2 = Integer.parseInt(tail.substring(1));
		
		if (h1 > 10 || t1 > 10 || h2 == 0 || t2 == 0){ // something runs over the border
			System.out.println("Your ship is over the board's border!");
			System.exit(1);
		}
		
		System.out.println("\nYour inputs are "+head+" and "+tail+". This has become positions "+h1+", "+h2+" and "+t1+", "+t2+".\n");
		
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
			
			if (diff+1 != length){ // +1 to count the initial square
				System.out.println("Your ship position is invalid, please try again!");
				System.exit(1);
			} else {
								
				int start, stop;
				
				if (h1 == t1){ // place the ship in the same row
					
					start = Math.min(h2, t2);
					stop = Math.max(h2, t2);
					
					// verify check
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
						shipboard[h1][i] = Character.toString(c);
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
						shipboard[i][h2] = Character.toString(c);
					}
					
				}
				
			}
			
		}
		
		//put.close(); check to see if it's called the right number of times before closing?
		
	}
	
}
