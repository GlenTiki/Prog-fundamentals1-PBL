public class Economy {

	private static boolean recovery;
	private static boolean depress;
	private static boolean recess;
	private static boolean rapidGrowth;
	private static boolean prosperity;
	private static boolean expansion;
	private static boolean downturn;
	private static boolean first;
	private static int drillingRig;
	private static int gasStation;
	private static int refinery;
	private static int oilWell;
	private static int sellDrillingRig;
	private static int sellGasStation;
	private static int sellRefinery;
	private static int sellOilWell;
	private static int wellCount;
	private static int marketDemand;
	private static int economyTrack;

	public Economy() {
		first = true;
		drillingRig = 00;
		gasStation = 00;
		refinery = 00;
		oilWell = 05;
		sellDrillingRig = 00;
		sellGasStation = 00;
		sellRefinery = 00;
		sellOilWell = 00;
		economyTrack = 00;
		wellCount = 01;
		marketDemand = 23; //set to 23 because it goes up by 3 in recovery.
		recovery();
	}

	public static void listBuyPrices(boolean hitRig) {
		StdOut.println("--------Buy Prices---------");
		StdOut.println("1: A Refinery is currently: " + refinery
				+ " Million Dollars to buy");
		StdOut.println("2: A Gas Station is currently: " + gasStation
				+ " Million Dollars to buy");
		StdOut.println("3: A Drilling Rig is currently: " + drillingRig
				+ " Million Dollars to buy");
		if (hitRig)
			StdOut.println("4: An Oil Well is currently: "
					+ (oilWell * wellCount) + " Million Dollars to buy.");
		StdOut.println("-----------------");
	}

	public static void listSellPrices() {
		StdOut.println("--------Sell Prices---------");
		StdOut.println("1: A Refinery is currently: " + sellRefinery
				+ " Million Dollars to sell");
		StdOut.println("2: A Gas Station is currently: " + sellGasStation
				+ " Million Dollars to sell");
		StdOut.println("3: A Drilling Rig is currently: " + sellDrillingRig
				+ " Million Dollars to sell");
		StdOut.println("4: An Oil Well is currently: " + sellOilWell
				+ " Million Dollars to sell.");
		StdOut.println("-----------------");
	}

	public static int sellGasFromStation() {
		int currentDemand = marketDemand -1;
		if(currentDemand > 0){
		marketDemand--;
		}
		else{
			StdOut.println("The demand is currently at 0! You can't sell anymore!");
		}
		return currentDemand;
	}

	public static void someoneBoughtOilWell() {
		wellCount++;
	}

	public static int getOilWellPrice() {
		return (oilWell * wellCount);
	}

	public static int getDrillingRigPrice() {
		return drillingRig;
	}

	public static int getStationPrice() {
		return gasStation;
	}

	public static int getRefineryPrice() {
		return refinery;
	}

	public static int getDrillSellPrice() {
		return sellDrillingRig;
	}

	public static int getStationSellPrice() {
		return sellGasStation;
	}

	public static int getRefinerySellPrice() {
		return sellRefinery;
	}

	public static int getWellSellPrice() {
		return sellOilWell;
	}

	public static void changeDemand(int change) {
		marketDemand = marketDemand + change;
		if(marketDemand > 81){
			marketDemand = 81;
		}
		if(marketDemand < 1){
			marketDemand = 1;
		}
		if (!first) {
			StdOut.println("The current price for Gasoline due to consumer demand is:"
					+ (marketDemand - 1));
		}
		first = false;
	}
	
	private static void recovery() {
		if(!first) StdOut.println("The Economy has just entered into recovery!");
		drillingRig = 5;
		gasStation = 48;
		refinery = 64;
		sellDrillingRig = 3;
		sellGasStation = 24;
		sellRefinery = 30;
		sellOilWell = 30;
		changeDemand(3);
		recovery = true;
	}

	private static void depress() {
		StdOut.println("The Economy has just entered into a depression!");
		drillingRig = 4;
		gasStation = 32;
		refinery = 50;
		sellDrillingRig = 2;
		sellGasStation = 18;
		sellRefinery = 24;
		sellOilWell = 20;
		changeDemand(-2);
		depress = true;
	}

	private static void recess() {
		StdOut.println("The Economy has just entered into a recession!");
		drillingRig = 8;
		gasStation = 55;
		refinery = 80;
		sellDrillingRig = 4;
		sellGasStation = 30;
		sellRefinery = 32;
		sellOilWell = 45;
		changeDemand(0);
		recess = true;
	}

	private static void rapidGrowth() {
		StdOut.println("The Economy has just entered into rapid growth!");
		drillingRig = 14;
		gasStation = 88;
		refinery = 132;
		sellDrillingRig = 9;
		sellGasStation = 50;
		sellRefinery = 90;
		sellOilWell = 65;
		changeDemand(6);
		rapidGrowth = true;
	}

	private static void prosperity() {
		StdOut.println("The Economy has just entered into prosperity!");
		drillingRig = 16;
		gasStation = 100;
		refinery = 160;
		sellDrillingRig = 12;
		sellGasStation = 60;
		sellRefinery = 120;
		sellOilWell = 75;
		changeDemand(7);
		prosperity = true;
	}

	private static void expansion() {
		StdOut.println("The Economy has just entered into expansion!");
		drillingRig = 9;
		gasStation = 66;
		refinery = 96;
		sellDrillingRig = 5;
		sellGasStation = 32;
		sellRefinery = 60;
		sellOilWell = 50;
		changeDemand(4);
		expansion = true;
	}

	private static void downturn() {
		StdOut.println("The Economy has just entered into a downturn!");
		drillingRig = 12;
		gasStation = 75;
		refinery = 120;
		sellDrillingRig = 8;
		sellGasStation = 45;
		sellRefinery = 80;
		sellOilWell = 60;
		changeDemand(2);
		downturn = true;
	}

	public static void changeEcon(int difference) {
		economyTrack = economyTrack + difference;
		if (economyTrack >= 8) { // changes the economy if the economy track
									// reaches 8 or over.
			StdOut.println("The economy dice has reached 8 or over! "
					+ "Press enter when you are ready to roll the dice for the economy change!");
			StdIn.readLine();
			economyTrack = 00;
			int diceRoll = Dice.rollDice();
			if (recovery) {
				recovery = false;
				switch (diceRoll) {
				case 1:
					downturn();
					break;
				case 2:
					expansion();
					break;
				case 3:
					expansion();
					break;
				case 4:
					expansion();
					break;
				case 5:
					expansion();
					break;
				case 6:
					rapidGrowth();
					break;
				}
			}
			else if (depress) {
				depress = false;
				switch (diceRoll) {
				case 1:
					recovery();
					break;
				case 2:
					recovery();
					break;
				case 3:
					recovery();
					break;
				case 4:
					recovery();
					break;
				case 5:
					expansion();
					break;
				case 6:
					expansion();
					break;
				}
			}
			else if (recess) {
				recess = false;
				switch (diceRoll) {
				case 1:
					depress();
					break;
				case 2:
					depress();
					break;
				case 3:
					depress();
					break;
				case 4:
					recovery();
					break;
				case 5:
					recovery();
					break;
				case 6:
					recovery();
					break;
				}
			}
			else if (rapidGrowth) {
				rapidGrowth = false;
				switch (diceRoll) {
				case 1:
					expansion();
					break;
				case 2:
					prosperity();
					break;
				case 3:
					prosperity();
					break;
				case 4:
					prosperity();
					break;
				case 5:
					prosperity();
					break;
				case 6:
					downturn();
					break;
				}
			}
			else if (prosperity) {
				prosperity = false;
				switch (diceRoll) {
				case 1:
					rapidGrowth();
					break;
				case 2:
					downturn();
					break;
				case 3:
					downturn();
					break;
				case 4:
					downturn();
					break;
				case 5:
					downturn();
					break;
				case 6:
					recess();
					break;
				}
			}
			else if (expansion) {
				expansion = false;
				switch (diceRoll) {
				case 1:
					recovery();
					break;
				case 2:
					rapidGrowth();
					break;
				case 3:
					rapidGrowth();
					break;
				case 4:
					rapidGrowth();
					break;
				case 5:
					rapidGrowth();
					break;
				case 6:
					prosperity();
					break;
				}
			}
			else if (downturn) {
				downturn = false;
				switch (diceRoll) {
				case 1:
					prosperity();
					break;
				case 2:
					recess();
					break;
				case 3:
					recess();
					break;
				case 4:
					recess();
					break;
				case 5:
					recess();
					break;
				case 6:
					depress();
					break;
				}
			}
		} else {
			StdOut.println("The Economy dice is currently on " + economyTrack);
		}
	}
	
	public void EndOfGame(){
		drillingRig = 4;
		gasStation = 32;
		refinery = 50;
		sellDrillingRig = 2;
		sellGasStation = 18;
		sellRefinery = 24;
		sellOilWell = 20;
		changeDemand(-2);
		depress = true;
	}
}