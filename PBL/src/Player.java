public class Player {

	private String name;
	private int crudeCount;
	private int gasCount;
	private int money;
	private boolean declared;
	private boolean activatedNews;
	private Asset grid[][] = new Asset[6][6];

	public Player(String pName) {
		name = pName;
		crudeCount = 2;
		gasCount = 2;
		money = 200;
		activatedNews = false;
		declared = false;
		initialiseGrid();
	}

	public int numberOfOilWells() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (grid[i][j].checkForAsset().equals("O"))
					count++;
			}
		}
		return count;
	}

	public int numberOfRefineries() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (grid[i][j].checkForAsset().equals("R"))
					count++;
			}
		}
		return count;
	}

	public int numberOfOilDrills() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (grid[i][j].checkForAsset().equals("D"))
					count++;
			}
		}
		return count;
	}

	public int numberOfGasStations() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (grid[i][j].checkForAsset().equals("G"))
					count++;
			}
		}
		return count;
	}

	public int gasBarrels() {
		return gasCount;
	}

	public int crudeBarrels() {
		return crudeCount;
	}

	public boolean getActivatedNews() {
		return activatedNews;
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int amt) {
		money = money + amt;
	}

	public void subtMoney(int amt) {
		money = money - amt;
	}

	public boolean getDeclared() {
		return declared;
	}

	public void setDeclared() {
		if (money > 750) {
			StdOut.println("Are you sure you wish to declare victory? (y/n)");
			String chk = StdIn.readString();
			chk.toLowerCase();
			if (chk.equals("y")) {
				declared = true;
			}
		} else
			StdOut.println("You don't have enough money to delcare victory!");
	}

	private void initialiseGrid() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				grid[i][j] = new Asset();
	}

	private void printGrid() {
		StdOut.println("\n|.:| 1.| 2.| 3.| 4.| 5.| 6.|");
		StdOut.println("--------------------------");
		for (int i = 0; i < 6; i++) {
			StdOut.print("|" + (i + 1) + ".");
			for (int j = 0; j < 6; j++) {
				StdOut.print("| ");
				StdOut.print(grid[i][j].checkForAsset());
				StdOut.print(" ");
			}
			StdOut.println("|");
		}
		StdOut.println("--------------------------");
	}

	public void buyingRound(boolean firstTime, int dice1, int dice2) {
		printGrid();
		boolean hitRig = false;
		for (int x = 0; x < 6; x++) {
			if (grid[dice1][x].checkForAsset().equals("D")) {
				hitRig = true;
			}
		}
		for (int y = 0; y < 6; y++) {
			if (grid[y][dice2].checkForAsset().equals("D")) {
				hitRig = true;
			}
		}

		if (firstTime)
			StdOut.println("Its "
					+ name
					+ "'s turn. This is the buying round, purchase any assets you like.");
		String ovrChk = "y";
		if (!firstTime) {
			StdOut.println("would you like to make a purchase?(y/n)");
			ovrChk = StdIn.readString();
		}
		ovrChk.toLowerCase();
		if (ovrChk.equals("yes"))
			ovrChk = "y";
		while (ovrChk.equals("y")) {
			StdOut.println("Your balance is currently: " + money);
			StdOut.println("What would you like to buy?");

			Economy.listBuyPrices(hitRig);
			if (!firstTime)
				StdOut.println("Enter 0 to exit.");
			firstTime = false;
			int purchase = StdIn.readInt();
			while (purchase < 0 || purchase >= 5) {
				StdOut.println("You entered: " + purchase
						+ " as a number. this is invalid. Try again.");
				StdOut.println("What would you like to buy?");
				Economy.listBuyPrices(hitRig);
				purchase = StdIn.readInt();
			}
			switch (purchase) {
			case 0:
				break;
			case 1:
				int refinery = Economy.getRefineryPrice();
				if (refinery <= money) {
					subtMoney(refinery);
					StdOut.println("Where would you like to place it?");
					printGrid();
					placeOnGrid("refinery");
				} else {
					StdOut.println("You dont have enough money for this!");
				}
				break;
			case 2:
				int gasStation = Economy.getStationPrice();
				if (gasStation <= money) {
					subtMoney(gasStation);
					StdOut.println("Where would you like to place it?");
					printGrid();
					placeOnGrid("gasStation");
				} else {
					StdOut.println("You dont have enough money for this!");
				}

				break;
			case 3:
				int drillingRig = Economy.getDrillingRigPrice();
				if (drillingRig <= money) {
					subtMoney(drillingRig);
					StdOut.println("Where would you like to place it?");
					printGrid();
					placeOnGrid("drillingRig");
				} else {
					StdOut.println("You dont have enough money for this!");
				}

				break;
			}
			if (purchase == 4 && hitRig && Economy.getOilWellPrice() <= money) {
				buyWell(dice1, dice2);
			} else if (purchase == 4 && !hitRig) {
				StdOut.println("You entered: " + purchase
						+ " as a number. this is invalid. Try again.");
				StdOut.println("What would you like to buy?");
				Economy.listBuyPrices(hitRig);
			} else if (money > Economy.getOilWellPrice() && purchase == 4) {
				StdOut.println("You dont have enough money for this!");
			}

			StdOut.println("Would you like to make another purchase?(Y/N)");
			ovrChk = StdIn.readString();
			ovrChk.toLowerCase();
			if (ovrChk.equals("yes"))
				ovrChk = "y";
		}
	}

	private void placeOnGrid(String assetType) {
		int chk = 1;
		int vertRef = 0;
		int horRef = 0;
		while (chk == 1) {
			StdOut.println("What Column would you like to place it in?(1-6)");
			vertRef = StdIn.readInt();
			while (vertRef < 1 || vertRef > 6) {
				StdOut.println("Invalid entry. Try between 1 and 6.");
				vertRef = StdIn.readInt();
			}
			StdOut.println("what Row would you like to place it in?(1-6)");
			horRef = StdIn.readInt();
			while (horRef < 1 || vertRef > 6) {
				StdOut.println("Invalid entry. Try between 1 and 6.");
				horRef = StdIn.readInt();
			}
			vertRef--;
			horRef--;
			if (grid[horRef][vertRef].checkForAsset().equals("-")) {
				chk = 2;
			} else {
				StdOut.println("Theres already something there! Try another position.");
			}
		}
		if (assetType.equals("refinery")) {
			grid[horRef][vertRef].setAssetTo("refinery");
		} else if (assetType.equals("oilWell")) {
			grid[horRef][vertRef].setAssetTo("OilWell");
		} else if (assetType.equals("gasStation")) {
			grid[horRef][vertRef].setAssetTo("gasStation");
		} else if (assetType.equals("drillingRig")) {
			grid[horRef][vertRef].setAssetTo("drillingRig");
		}
		printGrid();
	}

	private void buyWell(int dice1, int dice2) {
		Economy.someoneBoughtOilWell();
		int oilWell = Economy.getOilWellPrice();
		subtMoney(oilWell);
		boolean search = true;
		while (search) {
			int dice1a = dice1;
			int dice2a = dice2;
			printGrid();
			for (int x = 0; x < 6; x++) {
				if (grid[dice1a][x].checkForAsset().equals("D")) {
					StdOut.println("You may place it at (" + (dice1a + 1)
							+ ", " + (x + 1) + ")");
				}
				dice1a++;
			}
			for (int y = 0; y < 6; y++) {
				if (grid[y][dice2a].checkForAsset().equals("D")) {
					StdOut.println("You may place it at (" + (y + 1) + ", "
							+ (dice2a + 1) + ")");
				}
				dice2a++;
			}
			StdOut.println("Enter the column:");
			int i = StdIn.readInt();
			StdOut.println("enter the row:");
			int j = StdIn.readInt();
			if (grid[i][j].checkForAsset().equals("D")) {
				grid[i][j].deleteAsset();
				grid[i][j].setAssetTo("oilWell");
				search = false;
			}
		}
	}

	public boolean round() {
		StdOut.println("its " + name + "'s turn");
		marketPhase();
		productionPhase(); // this then calls buying and selling phase.
		endOfPhase();
		StdOut.println("its the end of " + name
				+ "'s turn. Press enter to continue");
		StdIn.readString();
		return declared;
	}

	public void finalRound() {
		StdOut.println("its " + name
				+ "'s turn. this is the final round! Good Luck!");
		marketPhase();
		productionPhase(); // this then calls the buying and selling phase.
		endOfPhase();
		StdOut.println("its the end of " + name
				+ "'s turn. Press enter to continue");
		StdIn.readString();
	}

	public void sellAssets() {
		StdOut.println("It's time to sell your assets at depression prices to get your net worth! Press enter when ready...");
		StdIn.readString();
		int totalCash = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (!grid[i][j].checkForAsset().equals("-")) {
					if (grid[i][j].checkForAsset().equals("D")) {
						StdOut.println("you sold a drilling rig at " + (i + 1)
								+ ", " + (j + 1) + ". This is worth "
								+ Economy.getDrillSellPrice()
								+ " Million dollars.");
						totalCash = totalCash + Economy.getDrillSellPrice();
					} else if (grid[i][j].checkForAsset().equals("O")) {
						StdOut.println("you sold an oil well at " + (i + 1)
								+ ", " + (j + 1) + ". This is worth "
								+ Economy.getWellSellPrice()
								+ " Million dollars.");
						totalCash = totalCash + Economy.getWellSellPrice();
					} else if (grid[i][j].checkForAsset().equals("R")) {
						StdOut.println("you sold a refinery at " + (i + 1)
								+ ", " + (j + 1) + ". This is worth "
								+ Economy.getRefinerySellPrice()
								+ " Million dollars.");
						totalCash = totalCash + Economy.getRefinerySellPrice();
					} else if (grid[i][j].checkForAsset().equals("G")) {
						StdOut.println("you sold a gas station at " + (i + 1)
								+ ", " + (j + 1) + ". This is worth "
								+ Economy.getStationSellPrice()
								+ " Million dollars.");
						totalCash = totalCash + Economy.getStationSellPrice();
					}
				}
			}
		}
		StdOut.println("The amount of cash you have received from this round is:"
				+ totalCash);
		money = money + totalCash;
		showBalance();
		StdOut.println("press enter when ready...");
		StdIn.readString();
	}

	public void sellAnyAsset() {
		Economy.listSellPrices();
		StdOut.println("what would you like to sell?");
		printGrid();StdOut.println("Enter the column:");
		int i = StdIn.readInt() - 1;
		StdOut.println("enter the row:");
		int j = StdIn.readInt() - 1;
		if (grid[i][j].checkForAsset().equals("D")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getDrillSellPrice());
		} else if (grid[i][j].checkForAsset().equals("O")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getWellSellPrice());
		} else if (grid[i][j].checkForAsset().equals("R")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getRefinerySellPrice());
		}  else if (grid[i][j].checkForAsset().equals("G")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getStationSellPrice());
		} else if (grid[i][j].checkForAsset().equals("-")) {
			StdOut.println("your entry was invalid. nothing was sold.");
		}
		showBalance();
	}

	public void sellAnyRefinery() {
		Economy.listSellPrices();
		StdOut.println("what  refinery would you like to sell?");
		printGrid();StdOut.println("Enter the column:");
		int i = StdIn.readInt() - 1;
		StdOut.println("enter the row:");
		int j = StdIn.readInt() - 1;
		if (grid[i][j].checkForAsset().equals("R")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getRefinerySellPrice());
		}  else if (!(grid[i][j].checkForAsset().equals("R"))) {
			StdOut.println("your entry was invalid. nothing was sold.");
		}
		showBalance();
	}
	
	public void sellAnyGasStation() {
		Economy.listSellPrices();
		StdOut.println("what gas station would you like to sell?");
		printGrid();StdOut.println("Enter the column:");
		int i = StdIn.readInt() - 1;
		StdOut.println("enter the row:");
		int j = StdIn.readInt() - 1;
		if (grid[i][j].checkForAsset().equals("G")) {
			grid[i][j].deleteAsset();
			addMoney(Economy.getStationSellPrice());
		}  else if (!(grid[i][j].checkForAsset().equals("G"))) {
			StdOut.println("your entry was invalid. nothing was sold.");
		}
		showBalance();
	}
	
	private void marketPhase() {
		showCrude();
		showGas();
		StdOut.println("------------");
		StdOut.println("the current buying price in the domestic market for gas is:"
				+ Market.buyGasDomestic(true));
		StdOut.println("the current buying price in the domestic market for crude oil is:"
				+ Market.buyCrudeDomestic(true));
		StdOut.println("the current selling price in the domestic market for gas is:"
				+ Market.sellGasDomestic(true));
		StdOut.println("the current selling price in the domestic market for crude is:"
				+ Market.sellCrudeDomestic(true));
		StdOut.println("the current buying price in the foreign market for gas is:"
				+ Market.buyGasForeign(true));
		StdOut.println("the current buying price in the foreign market for crude oil is:"
				+ Market.buyCrudeForeign(true));
		StdOut.println("the current selling price in the foreign market for gas is:"
				+ Market.sellGasForeign(true));
		StdOut.println("the current selling price in the foreign market for crude oil is:"
				+ Market.sellCrudeForeign(true));
		StdOut.println("------------");
		StdOut.println("Would you like to buy or sell gas or crude in these markets?(y/n)");
		String input = StdIn.readString();
		input.toLowerCase();
		if (input.equals("yes")) {
			input = "y";
		}
		if (input.equals("no")) {
			input = "n";
		}
		while (!(input.equals("y") || input.equals("n"))) {
			StdOut.println("You entered an invalid entry. please enter y for yes or n for no.");
			input = StdIn.readString();
			input.toLowerCase();
			if (input.equals("yes")) {
				input = "y";
			}
			if (input.equals("no")) {
				input = "n";
			}
		}
		if (input.equals("y")) {
			StdOut.println("Which market would you like to enter? Domestic or foreign?(D/F)");
			StdOut.println("(you may enter E to exit to the next phase.)");
			input = StdIn.readString();
			input.toLowerCase();
			if (input.equals("domestic")) {
				input = "d";
			} else if (input.equals("foreign")) {
				input = "f";
			} else if (input.equals("exit")) {
				input = "e";
			}
			while (!(input.equals("f") || input.equals("d") || input
					.equals("e"))) {
				StdOut.println("You did not enter a valid character. Please enter D to enter domestic, F to enter foreign or E to exit.");
				input = StdIn.readString();
				input.toLowerCase();
				if (input.equals("domestic")) {
					input = "d";
				} else if (input.equals("foreign")) {
					input = "f";
				} else if (input.equals("exit")) {
					input = "e";
				}
			}
			String marketInputs = "y";
			if (input.equals("f")) { // entering foreign market
				while (marketInputs.equals("y")) {
					showCrude();
					showGas();
					StdOut.println("------------");
					StdOut.println("the current buying price in the foreign market for gas is:"
							+ Market.buyGasForeign(true));
					StdOut.println("the current buying price in the foreign market for crude oil is:"
							+ Market.buyCrudeForeign(true));
					StdOut.println("the current selling price in the foreign market for gas is:"
							+ Market.sellGasForeign(true));
					StdOut.println("the current selling price in the foreign market for crude oil is:"
							+ Market.sellCrudeForeign(true));
					StdOut.println("------------");
					StdOut.println("Would you like to buy or sell crude or gas? (c/g)");
					StdOut.println("(you may enter E to exit to the next phase.)");
					marketInputs = StdIn.readString();
					marketInputs.toLowerCase();
					if (marketInputs.equals("crude")) {
						marketInputs = "c";
					} else if (marketInputs.equals("gas")) {
						marketInputs = "g";
					} else if (marketInputs.equals("exit")) {
						marketInputs = "e";
					}
					while (!(marketInputs.equals("c")
							|| marketInputs.equals("g") || marketInputs
								.equals("e"))) {
						StdOut.println("You did not enter a valid character. Please enter c to buy crude, g to buy gas or e to exit.");
						input = StdIn.readString();
						input.toLowerCase();
						if (input.equals("crude")) {
							input = "c";
						} else if (input.equals("gas")) {
							input = "g";
						} else if (input.equals("exit")) {
							input = "e";
						}
					}
					while (marketInputs.equals("c")) { // entering crude options
														// in
														// foreign market
						showCrude();
						showGas();
						StdOut.println("the current buying price in the foreign market for crude oil is:"
								+ Market.buyCrudeForeign(true));
						StdOut.println("the current selling price in the foreign market for crude oil is:"
								+ Market.sellCrudeForeign(true));
						StdOut.println("------------");
						StdOut.println("Would you like to buy or sell? (b/s)");
						StdOut.println("(you may enter E to exit to the next phase.)");
						marketInputs = StdIn.readString();
						marketInputs.toLowerCase();
						if (marketInputs.equals("buy")) {
							marketInputs = "b";
						} else if (marketInputs.equals("sell")) {
							marketInputs = "s";
						} else if (marketInputs.equals("exit")) {
							marketInputs = "e";
						}
						while (!(marketInputs.equals("b")
								|| marketInputs.equals("s") || marketInputs
									.equals("e"))) {
							StdOut.println("You did not enter a valid character. Please enter b to buy, s to sell or e to exit.");
							input = StdIn.readString();
							input.toLowerCase();
							if (input.equals("buy")) {
								input = "b";
							} else if (input.equals("sell")) {
								input = "s";
							} else if (input.equals("exit")) {
								input = "e";
							}
						}
						if (marketInputs.equals("b")) {
							if (money > Market.buyCrudeForeign(true)) {
								money = money - Market.buyCrudeForeign(false);
								crudeCount++;
								showBalance();
								marketInputs = "c";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("s")) {
							if (crudeCount > 0) {
								money = money + Market.sellCrudeForeign(false);
								crudeCount--;
								showBalance();
								marketInputs = "c";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("e")) {
							marketInputs = "eeeeeee";
						}
					}
					while (marketInputs.equals("g")) { // entering gas options
														// for
														// foreign market
						showCrude();
						showGas();
						StdOut.println("the current buying price in the foreign market for gas is:"
								+ Market.buyGasDomestic(true));
						StdOut.println("the current selling price in the foreign market for gas is:"
								+ Market.sellGasDomestic(true));
						StdOut.println("------------");
						StdOut.println("Would you like to buy or sell? (b/s)");
						StdOut.println("(you may enter E to exit.)");
						marketInputs = StdIn.readString();
						marketInputs.toLowerCase();
						if (marketInputs.equals("buy")) {
							marketInputs = "b";
						} else if (marketInputs.equals("sell")) {
							marketInputs = "s";
						} else if (marketInputs.equals("exit")) {
							marketInputs = "e";
						}
						while (!(marketInputs.equals("b")
								|| marketInputs.equals("s") || marketInputs
									.equals("e"))) {
							StdOut.println("You did not enter a valid character. Please enter b to buy, s to sell or e to exit.");
							input = StdIn.readString();
							input.toLowerCase();
							if (input.equals("buy")) {
								input = "b";
							} else if (input.equals("sell")) {
								input = "s";
							} else if (input.equals("exit")) {
								input = "e";
							}
						}
						if (marketInputs.equals("b")) {
							if (money > Market.buyGasForeign(true)) {
								money = money - Market.buyGasForeign(false);
								gasCount++;
								showBalance();
								marketInputs = "g";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("s")) {
							if (gasCount > 0) {
								money = money + Market.sellGasForeign(false);
								gasCount--;
								showBalance();
								marketInputs = "g";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("e")) {
							marketInputs = "eeeeeee";
						}
					}
					if (marketInputs.equals("e")) { // exiting market phase.
						marketInputs = "n";
					} else {
						marketInputs = "y";
					}
				}
			}
			if (input.equals("d")) { // entering domestic market
				while (marketInputs.equals("y")) {
					showCrude();
					showGas();
					StdOut.println("------------");
					StdOut.println("the current buying price in the domestic market for gas is:"
							+ Market.buyGasDomestic(true));
					StdOut.println("the current buying price in the domestic market for crude oil is:"
							+ Market.buyCrudeDomestic(true));
					StdOut.println("the current selling price in the domestic market for gas is:"
							+ Market.sellGasDomestic(true));
					StdOut.println("the current selling price in the domestic market for crude oil is:"
							+ Market.sellCrudeDomestic(true));
					StdOut.println("------------");
					StdOut.println("Would you like to buy or sell crude or gas? (c/g)");
					StdOut.println("(you may enter E to exit to the next phase.)");
					marketInputs = StdIn.readString();
					marketInputs.toLowerCase();
					if (marketInputs.equals("crude")) {
						marketInputs = "c";
					} else if (marketInputs.equals("gas")) {
						marketInputs = "g";
					} else if (marketInputs.equals("exit")) {
						marketInputs = "e";
					}
					while (!(marketInputs.equals("c")
							|| marketInputs.equals("g") || marketInputs
								.equals("e"))) {
						StdOut.println("You did not enter a valid character. Please enter c to buy crude, g to buy gas or e to exit.");
						input = StdIn.readString();
						input.toLowerCase();
						if (input.equals("crude")) {
							input = "c";
						} else if (input.equals("gas")) {
							input = "g";
						} else if (input.equals("exit")) {
							input = "e";
						}
					}
					while (marketInputs.equals("c")) { // entering crude options
														// in domestic market
						showCrude();
						showGas();
						StdOut.println("the current buying price in the domestic market for crude oil is:"
								+ Market.buyCrudeDomestic(true));
						StdOut.println("the current selling price in the domestic market for crude oil is:"
								+ Market.sellCrudeDomestic(true));
						StdOut.println("------------");
						StdOut.println("Would you like to buy or sell? (b/s)");
						StdOut.println("(you may enter E to exit)");
						marketInputs = StdIn.readString();
						marketInputs.toLowerCase();
						if (marketInputs.equals("buy")) {
							marketInputs = "b";
						} else if (marketInputs.equals("sell")) {
							marketInputs = "s";
						} else if (marketInputs.equals("exit")) {
							marketInputs = "e";
						}
						while (!(marketInputs.equals("b")
								|| marketInputs.equals("s") || marketInputs
									.equals("e"))) {
							StdOut.println("You did not enter a valid character. Please enter b to buy, s to sell or e to exit.");
							input = StdIn.readString();
							input.toLowerCase();
							if (input.equals("buy")) {
								input = "b";
							} else if (input.equals("sell")) {
								input = "s";
							} else if (input.equals("exit")) {
								input = "e";
							}
						}
						if (marketInputs.equals("b")) {
							if (money > Market.buyCrudeDomestic(true)) {
								money = money - Market.buyCrudeDomestic(false);
								crudeCount++;
								showBalance();
								marketInputs = "c";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("s")) {
							if (crudeCount > 0) {
								money = money + Market.sellCrudeDomestic(false);
								crudeCount--;
								showBalance();
								marketInputs = "c";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("e")) {
							marketInputs = "eeeeeee";
						}
					}
					while (marketInputs.equals("g")) { // entering gas options
														// for domestic market
						showCrude();
						showGas();
						StdOut.println("the current buying price in the domestic market for gas is:"
								+ Market.buyGasDomestic(true));
						StdOut.println("the current selling price in the domestic market for gas is:"
								+ Market.sellGasDomestic(true));
						StdOut.println("------------");
						StdOut.println("Would you like to buy or sell? (b/s)");
						StdOut.println("(you may enter E to exit to the next phase.)");
						marketInputs = StdIn.readString();
						marketInputs.toLowerCase();
						if (marketInputs.equals("buy")) {
							marketInputs = "b";
						} else if (marketInputs.equals("sell")) {
							marketInputs = "s";
						} else if (marketInputs.equals("exit")) {
							marketInputs = "e";
						}
						while (!(marketInputs.equals("b")
								|| marketInputs.equals("s") || marketInputs
									.equals("e"))) {
							StdOut.println("You did not enter a valid character. Please enter b to buy, s to sell or e to exit.");
							input = StdIn.readString();
							input.toLowerCase();
							if (input.equals("buy")) {
								input = "b";
							} else if (input.equals("sell")) {
								input = "s";
							} else if (input.equals("exit")) {
								input = "e";
							}
						}
						if (marketInputs.equals("b")) {
							if (money > Market.buyGasDomestic(true)) {
								money = money - Market.buyGasDomestic(false);
								gasCount++;
								showBalance();
								marketInputs = "g";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("s")) {
							if (gasCount > 0) {
								money = money + Market.sellGasDomestic(false);
								gasCount--;
								showBalance();
								marketInputs = "g";
							} else {
								StdOut.println("you dont have enough to do this!");
							}
						} else if (marketInputs.equals("e")) {
							marketInputs = "eeeeeee";
						}
					}

					if (marketInputs.equals("e")) { // exiting market phase.
						marketInputs = "n";
					} else {
						marketInputs = "y";
					}
				}
			}
		}
	}

	private void showCrude() {
		StdOut.println("you currently have " + crudeCount + " crude oil");
	}

	private void showGas() {
		StdOut.println("You currently have " + gasCount + " gasoline");
	}

	public void showBalance() {
		StdOut.println("Your current balance is: " + money + "Million dollars.");

	}

	private void productionPhase() {
		StdOut.println("now entering production phase");
		StdOut.println("press any button when you want to roll the dice!");
		StdIn.readString();
		int dice1 = Dice.rollDice();
		int dice2 = Dice.rollDice();
		StdOut.println("You rolled a " + dice1 + " and a " + dice2);
		int difference = 0;
		if (dice1 > dice2) {
			difference = dice1 - dice2;
		} else {
			difference = dice2 - dice1;
		}
		Economy.changeEcon(difference);
		if (difference == 0) {
			if ((dice1 == 1 && dice2 == 1) || (dice1 == 3 && dice2 == 3)) {
				activatedNews = true;
				StdOut.println("you have activated the most likely news card!");
				Board.mostLikelyNews();
				activatedNews = false;
			} else if (dice1 == 6 && dice2 == 6) {
				activatedNews = true;
				StdOut.println("you have activated the least likely news card!");
				Board.leastLikelyNews();
				activatedNews = false;
			}
		}
		int dice1a = dice1 - 1;
		int dice2a = dice2 - 1;
		for (int x = 0; x < 6; x++) {
			if (!grid[dice1a][x].checkForAsset().equals("-")) {
				if (grid[dice1a][x].checkForAsset().equals("D")) {
					StdOut.println("you hit a drilling rig at "
							+ (dice1a + 1)
							+ ", "
							+ x
							+ ". This may be upgraded to a oil well in the buying round.");
				} else if (grid[dice1a][x].checkForAsset().equals("O")) {
					StdOut.println("you hit an oil well at "
							+ (dice1a + 1)
							+ ", "
							+ x
							+ ". This will produce 2 crude oil for you. hit enter when ready.");
					StdIn.readString();
					crudeCount += 2;
				} else if (grid[dice1a][x].checkForAsset().equals("R")) {
					StdOut.println("you hit a refinery at "
							+ (dice1a + 1)
							+ ", "
							+ x
							+ ". This will exchange 1 crude oil to gasoline for you, if you have enough crude oil. hit enter when ready.");
					StdIn.readString();
					if (crudeCount > 0) {
						crudeCount--;
						gasCount++;
					} else {
						StdOut.println("You dont have enough crude to change to gas. :(");
					}
				} else if (grid[dice1a][x].checkForAsset().equals("G")) {
					StdOut.println("you hit a gas station at "
							+ (dice1a + 1)
							+ ", "
							+ x
							+ ". This will sell 1 gasoline for you on the consumer market, if you have enough gasoline and the market isnt full. hit enter when ready.");
					if (gasCount > 0) {
						gasCount--;
						money = money + Economy.sellGasFromStation();
					} else {
						StdOut.println("You dont have enough gasoline to sell! :(");
					}
				}
			}
		}
		for (int y = 0; y < 6; y++) {
			if (!grid[y][dice2a].checkForAsset().equals("-")) {
				if (grid[y][dice2a].checkForAsset().equals("D")) {
					StdOut.println("you hit a drilling rig at "
							+ y
							+ ", "
							+ (dice2a + 1)
							+ ". This may be upgraded to a oil well in the buying round.");
				} else if (grid[y][dice2a].checkForAsset().equals("O")) {
					StdOut.println("you hit an oil well at "
							+ y
							+ ", "
							+ (dice2a + 1)
							+ ". This will produce 2 crude oil for you. hit enter when ready.");
					StdIn.readString();
					crudeCount += 2;
				} else if (grid[y][dice2a].checkForAsset().equals("R")) {
					StdOut.println("you hit a refinery at "
							+ y
							+ ", "
							+ (dice2a + 1)
							+ ". This will exchange 1 crude oil to gasoline for you, if you have enough crude oil. hit enter when ready.");
					StdIn.readString();
					if (crudeCount > 0) {
						crudeCount--;
						gasCount++;
					} else {
						StdOut.println("You dont have enough crude to change to gas. :(");
					}
				} else if (grid[y][dice2a].checkForAsset().equals("G")) {
					StdOut.println("you hit a gas station at "
							+ y
							+ ", "
							+ (dice2a + 1)
							+ ". This will sell 1 gasoline for you on the consumer market, if you have enough gasoline and the market isnt full. hit enter when ready.");
					if (gasCount > 0) {
						gasCount--;
						money = money + Economy.sellGasFromStation();
					} else {
						StdOut.println("You dont have enough gasoline to sell! :(");
					}
				}
			}
		}
		buyingRound(false, dice1a, dice2a);
		sellingRound(dice1a, dice2a);
	}

	private void sellingRound(int dice1a, int dice2a) {
		printGrid();
		Economy.listSellPrices();
		for (int x = 0; x < 6; x++) {
			if (!grid[dice1a][x].checkForAsset().equals("-")) {
				if (grid[dice1a][x].checkForAsset().equals("D")) {
					StdOut.println("you hit a drilling rig at " + (dice1a + 1)
							+ ", " + x + ". You may sell this.");
				} else if (grid[dice1a][x].checkForAsset().equals("O")) {
					StdOut.println("you hit an oil well at " + (dice1a + 1)
							+ ", " + x + ". you may sell this.");
				} else if (grid[dice1a][x].checkForAsset().equals("R")) {
					StdOut.println("you hit a refinery at " + (dice1a + 1)
							+ ", " + x + ". You may sell this.");
				} else if (grid[dice1a][x].checkForAsset().equals("G")) {
					StdOut.println("you hit a gas station at " + (dice1a + 1)
							+ ", " + x + ". you may sell this.");
				}
			}
		}
		for (int y = 0; y < 6; y++) {
			if (!grid[y][dice2a].checkForAsset().equals("-")) {
				if (grid[y][dice2a].checkForAsset().equals("D")) {
					StdOut.println("you hit a drilling rig at " + y + ", "
							+ (dice2a + 1) + ". you may sell this.");
				} else if (grid[y][dice2a].checkForAsset().equals("O")) {
					StdOut.println("you hit an oil well at " + y + ", "
							+ (dice2a + 1) + ". you may sell this.");
				} else if (grid[y][dice2a].checkForAsset().equals("R")) {
					StdOut.println("you hit a refinery at " + y + ", "
							+ (dice2a + 1) + ". You may sell this.");
				} else if (grid[y][dice2a].checkForAsset().equals("G")) {
					StdOut.println("you hit a gas station at " + y + ", "
							+ (dice2a + 1) + ". You may sell this.");
				}
			}
		}
		String ovrChk = "";
		StdOut.println("would you like to sell some of your assets?(y/n)");
		ovrChk = StdIn.readString();
		ovrChk.toLowerCase();
		if (ovrChk.equals("yes")) {
			ovrChk = "y";
		}
		while (ovrChk.equals("y")) {
			printGrid();
			StdOut.println("enter the x position you would like to sell from:");
			int vertPos = StdIn.readInt() - 1;
			StdOut.println("enter the y position you would like to sell from:");
			int horPos = StdIn.readInt() - 1;
			if (!grid[vertPos][horPos].checkForAsset().equals("-")
					&& (vertPos == dice1a || horPos == dice2a)) {
				if (grid[vertPos][vertPos].checkForAsset().equals("D")) {
					money = money + Economy.getDrillSellPrice();
					grid[vertPos][vertPos].deleteAsset();
					showBalance();
				} else if (grid[vertPos][horPos].checkForAsset().equals("O")) {
					money = money + Economy.getWellSellPrice();
					grid[vertPos][vertPos].deleteAsset();
					showBalance();
					;
				} else if (grid[vertPos][horPos].checkForAsset().equals("R")) {
					money = money + Economy.getRefinerySellPrice();
					grid[vertPos][vertPos].deleteAsset();
					showBalance();
					;
				} else if (grid[vertPos][horPos].checkForAsset().equals("G")) {
					money = money + Economy.getStationSellPrice();
					grid[vertPos][vertPos].deleteAsset();
					showBalance();
					;
				}
			} else {
				StdOut.println("you entered a wrong position. would you like to try again? (y/n)");
				ovrChk = StdIn.readString();
				if (ovrChk.equals("yes"))
					ovrChk = "y";
			}
		}
	}

	private void endOfPhase() {
		if (money >= 750) {
			StdOut.println("you have more than 750 million, you can now declare victory! Would you like to? (y/n)");
			String input = "";
			input = StdIn.readString();
			input.toLowerCase();
			if (input.equals("yes")) {
				input = "y";
			}
			if (input.equals("y")) {
				declared = true;
			}
		}
	}

	public void wonGame() {
		StdOut.println(name + " won! Now... time for a rematch?");
		StdIn.readLine();
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("");
		StdOut.println("GAME OVER!");
		StdOut.println("Thanks for playing!");
		StdOut.println("Credits");
		StdOut.println("Programmed by:");
		StdOut.println("Glen Keane");
		StdOut.println("Alan McAuliffe");
		StdOut.println("Chris Moore");
		StdOut.println("Peter Kiernan");
	}
}
