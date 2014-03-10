public class Board {

	private boolean declaredMode;
	private static Player players[] = new Player[4];

	/*
	 * TO-DO: First turn. Rounds. Declared mode. MAKE OBJECTS AND METHODS TO BUY
	 * ASSETS IN PLAYER CLASS.
	 */

	public static void main(String[] args) {
		Board app = new Board();
		app.run();
	}

	public Board() {
		new NewsDeck();
		new Market();
		new Economy();
		StdOut.println("-----------------");
		StdOut.println("Welcome to Crude: The Oil Game.");
		StdOut.println("We hope you brought your wellies with you, Cause you're going to be knee deep in gasoline.");
		StdOut.println("-----------------");
		declaredMode = false;
		String chk = "";
		String playerName = "";
		for (int i = 0; i < 4; i++) {
			while (!chk.equals("y")) { // Players initialisation
				StdOut.println("Player " + (i+1) + " enter your name:");
				playerName = StdIn.readString();
				while (playerName.equals("") || playerName.equals(" ")) {
					StdOut.println("Player " + (i+1)
							+ " enter your name, AND DON'T LEAVE BLANKS!");
					playerName = StdIn.readString();
				}
				StdOut.println("Player "
						+ (i+1)
						+ ", you entered "
						+ playerName
						+ " as your name. Is this right?(y for yes, anything else for no.)");
				chk = StdIn.readString();
				chk.toLowerCase();
				if (chk.equals("yes"))
					chk = "y";
			}
			StdOut.println("-----------------");
			chk = "";
			players[i] = new Player(playerName);
		}
	}

	public void run() {
		// buying round
		Player player1 = players[0];
		Player player2 = players[1];
		Player player3 = players[2];
		Player player4 = players[3];
		player1.buyingRound(true, 0, 0);
		player2.buyingRound(true, 0, 0);
		player3.buyingRound(true, 0, 0);
		player4.buyingRound(true, 0, 0);
		// Rounds
		while (declaredMode == false) {
			if (declaredMode == false)
				declaredMode = player1.round();
			if (declaredMode == false)
				declaredMode = player2.round();
			if (declaredMode == false)
				declaredMode = player3.round();
			if (declaredMode == false)
				declaredMode = player4.round();
		}
		Player winningPlayer = someoneDeclared();
		winningPlayer.wonGame();
	}

	private Player someoneDeclared() {
		Player player1 = players[0];
		Player player2 = players[1];
		Player player3 = players[2];
		Player player4 = players[3];
		// finish
		// player1 declared
		if (player1.getDeclared() == true) {
			player2.finalRound();
			player3.finalRound();
			player4.finalRound();
			player1.sellAssets();
			player2.sellAssets();
			player3.sellAssets();
			player4.sellAssets();
		}
		// player2 declared
		if (player2.getDeclared() == true) {
			player3.finalRound();
			player4.finalRound();
			player1.finalRound();
			player2.sellAssets();
			player3.sellAssets();
			player4.sellAssets();
			player1.sellAssets();
		}
		// player3 declared
		if (player3.getDeclared() == true) {
			player4.finalRound();
			player1.finalRound();
			player2.finalRound();
			player3.sellAssets();
			player4.sellAssets();
			player1.sellAssets();
			player2.sellAssets();
		}
		// player4 declared
		if (player4.getDeclared() == true) {
			player1.finalRound();
			player2.finalRound();
			player3.finalRound();
			player4.sellAssets();
			player1.sellAssets();
			player2.sellAssets();
			player3.sellAssets();
		}
		Player winner = null;
		// check for who wins
		if (player1.getMoney() > player2.getMoney()
				&& player1.getMoney() > player3.getMoney()
				&& player1.getMoney() > player4.getMoney()) {
			winner = player1;
		} else if (player2.getMoney() > player1.getMoney()
				&& player2.getMoney() > player3.getMoney()
				&& player2.getMoney() > player4.getMoney()) {
			winner = player2;
		} else if (player3.getMoney() > player1.getMoney()
				&& player3.getMoney() > player2.getMoney()
				&& player3.getMoney() > player4.getMoney()) {
			winner = player3;
		} else if (player4.getMoney() > player1.getMoney()
				&& player4.getMoney() > player2.getMoney()
				&& player4.getMoney() > player3.getMoney()) {
			winner = player4;
		}
		return winner;
	}

	public static void mostLikelyNews(){
		Player player1 = players[0];
		Player player2 = players[1];
		Player player3 = players[2];
		Player player4 = players[3];
		boolean finished =  false;
		// news
		// player1 activated news card
		if (player1.getActivatedNews()) {
			NewsDeck.mostLikely(player1);
			if(!finished) NewsDeck.mostLikely(player2);
			if(!finished) NewsDeck.mostLikely(player3);
			if(!finished) NewsDeck.mostLikely(player4);
		}
		// player2 activated
		if (player2.getActivatedNews()) {
			NewsDeck.mostLikely(player2);
			if(!finished) NewsDeck.mostLikely(player3);
			if(!finished) NewsDeck.mostLikely(player4);
			if(!finished) NewsDeck.mostLikely(player1);
		}
		// player3 activated
		if (player3.getActivatedNews()) {
			NewsDeck.mostLikely(player3);
			if(!finished) NewsDeck.mostLikely(player4);
			if(!finished) NewsDeck.mostLikely(player1);
			if(!finished) NewsDeck.mostLikely(player2);
		}
		// player4 activated
		if (player4.getActivatedNews()) {
			NewsDeck.mostLikely(player4);
			if(!finished) NewsDeck.mostLikely(player1);
			if(!finished) NewsDeck.mostLikely(player2);
			if(!finished) NewsDeck.mostLikely(player3);
		}
	}
	
	public static void leastLikelyNews(){
		Player player1 = players[0];
		Player player2 = players[1];
		Player player3 = players[2];
		Player player4 = players[3];
		boolean finished = false;
		// news
		// player1 activated news card
		if (player1.getActivatedNews()) {
			if(!finished) NewsDeck.leastLikely(player1);
			if(!finished) NewsDeck.leastLikely(player2);
			if(!finished) NewsDeck.leastLikely(player3);
			if(!finished) NewsDeck.leastLikely(player4);
		}
		// player2 activated
		if (player2.getActivatedNews()) {
			if(!finished) NewsDeck.leastLikely(player2);
			if(!finished) NewsDeck.leastLikely(player3);
			if(!finished) NewsDeck.leastLikely(player4);
			if(!finished) NewsDeck.leastLikely(player1);
		}
		// player3 activated
		if (player3.getActivatedNews()) {
			if(!finished) NewsDeck.leastLikely(player3);
			if(!finished) NewsDeck.leastLikely(player4);
			if(!finished) NewsDeck.leastLikely(player1);
			if(!finished) NewsDeck.leastLikely(player2);
		}
		// player4 activated
		if (player4.getActivatedNews()) {
			if(!finished) NewsDeck.leastLikely(player4);
			if(!finished) NewsDeck.leastLikely(player1);
			if(!finished) NewsDeck.leastLikely(player2);
			if(!finished) NewsDeck.leastLikely(player3);
		}
	}
}
