public class NewsCard {

	private boolean aggressiveDebtTaxes;
	private boolean globalWarmingThreat;
	private boolean stateSalesTaxesDue;
	private boolean longColdWinter;
	private boolean snapInspection;
	private boolean uncertainty;
	private int count;

	public NewsCard(){
		aggressiveDebtTaxes = false;
		globalWarmingThreat = false;
		stateSalesTaxesDue = false;
		longColdWinter = false;
		snapInspection = false;
		uncertainty = false;
		count = 0;
	}

	public void setToAggressive() {
		aggressiveDebtTaxes = true;
	}

	public void setToGlobalWarming() {
		globalWarmingThreat = true;
	}

	public void setToStateSalesTaxes() {
		stateSalesTaxesDue = true;
	}

	public void setToLongColdWinter() {
		longColdWinter = true;
	}

	public void setToSnapInspection() {
		snapInspection = true;
	}

	public void setToUncertainty() {
		uncertainty = true;
	}

	public boolean carryOut(Player player) {
		boolean finished = false;
		if (aggressiveDebtTaxes) {
			StdOut.println("Aggressive national debt reduction taxes \n"
					+ "oil assets levied with new property taxes \n"
					+ "each player must pay the bank the following amounts for each of his assets:\n"
					+ "Drilling Rig....$2million\n"
					+ "Oil Well........$5million\n"
					+ "Gas Station..$15million\n"
					+ "Refinery.......$20million\n"
					+ "all the players must pay these property must pay these property taxes before the game continues."
					+ "\n if a player does not have enough cash to pay his taxes he must sell as many assets back to the bank as necessary"
					+ "\n (at their current liquidation price) in order to come to come up with the cash."
					+ "\n the tax must be paid on every assest, even those that must be sold back to the bank to come up with the cash."
					+ "\n after all taxes have been paid, return this card to the bottom of the News Deck.");
			int amtOwed = 0;
			amtOwed = (player.numberOfOilDrills() * 2)
					+ (player.numberOfOilWells() * 5)
					+ (player.numberOfGasStations() * 15)
					+ (player.numberOfRefineries() * 20);
			StdOut.println(player.getName() + ", You owe " + amtOwed);
			while (player.getMoney() < amtOwed) {
				StdOut.println("You dont have enough money to pay this yet! you must sell some assets...");
				player.sellAnyAsset();
			}
			if (player.getMoney() > amtOwed) {
				StdOut.println("You currently have enough enough money to pay this. hit enter when ready to have it deducted from your bank account.");
				StdIn.readString();
				player.subtMoney(amtOwed);
				player.showBalance();
			}
			count++;
			if (count == 4)
				finished = true;
		}
		if (globalWarmingThreat) {
			StdOut.println("Global Warming Threat\n"
					+ "\n Epa Enviromental impact data forces refineries to “clean-up” their act"
					+ "\n Each player must pay $25 million to the bank for each Refinery he owns to bring it up to current pollution control S t a n d a r d s."
					+ "Refineries cannot refine oil again until the  new pollution control equipment is installed."
					+ "If the $25 million payment is not made the player must sell refineries to the bank at the current liquidation value."
					+ "As soon as all refineries have installed the new pollution control equipment return this card to the bottom of the news deck.");
			int amtOwed = 0;
			amtOwed = (player.numberOfRefineries() * 25);
			StdOut.println(player.getName() + ", You owe " + amtOwed);
			while (player.getMoney() < amtOwed) {
				StdOut.println("You dont have enough money to pay this yet! you must sell some refineries...");
				player.sellAnyRefinery();
			}
			if (player.getMoney() > amtOwed) {
				StdOut.println("You currently have enough enough money to pay this. hit enter when ready to have it deducted from your bank account.");
				StdIn.readString();
				player.subtMoney(amtOwed);
				player.showBalance();
			}
			count++;
			if (count == 4)
				finished = true;
		}
		if (stateSalesTaxesDue) {
			StdOut.println("State Sales Taxes Due Soon"
					+ "\n Each player must pay the bank $15 million for each "
					+ "\n Gas Station that he owns before the game continues."
					+ "\n If a player does not have enough cash to pay his taxes he must sell Gas Stations back to the bank "
					+ "\n (at their current liquidation price) in order to come up with the cash."
					+ "\n the tax must be paid on every Gas Station, even those that must be sold back to the bank to come up "
					+ "\n with the cash. After all taxes have been paid, return this card to the bottom of the News Deck.");
			int amtOwed = 0;
			amtOwed = (player.numberOfGasStations() * 15);
			StdOut.println(player.getName() + ", You owe " + amtOwed);
			while (player.getMoney() < amtOwed) {
				StdOut.println("You dont have enough money to pay this yet! you must sell some gas stations...");
				player.sellAnyGasStation();
			}
			if (player.getMoney() > amtOwed) {
				StdOut.println("You currently have enough enough money to pay this. hit enter when ready to have it deducted from your bank account.");
				StdIn.readString();
				player.subtMoney(amtOwed);
				player.showBalance();
			}
			count++;
			if (count == 4)
				finished = true;
		}
		if (longColdWinter) {
			StdOut.println("Long Cold Winter Forecast"
					+ "\n Remove 5 barrels of Gasoline from the Domestic Market and return them to the bank"
					+ "\n (remove the 5 least expensive barrels that are available).");
			Economy.changeDemand(-5);
			count++;
			if (count == 1)
				finished = true;
		}
		if (snapInspection) {
			StdOut.println("Snap Inspection at huge oil tank farm finds numerous safety violations."
					+ "\n Federal and state regulations perform country-wide safety inspection at oilmen's cost."
					+ "\n Each player must immediately pay to the bank the following for each barrel he owns: "
					+ "\n crude oil.......$3 million"
					+ "\n Gasoline......$5 million"
					+ "\n If a player does not have enough cash to pay for his safety inspection he must sell as many assets back to the bank as necessary "
					+ "\n (at their current liquidation price) in order to come up with the cash."
					+ "\n After all inspection have been paid for, return this card to the bottom of the News Deck.");
			int amtOwed = 0;
			amtOwed = (player.crudeBarrels() * 3) + (player.gasBarrels() * 3);
			StdOut.println(player.getName() + ", You owe " + amtOwed);
			while (player.getMoney() < amtOwed) {
				StdOut.println("You dont have enough money to pay this yet! you must sell some assets...");
				player.sellAnyAsset();
			}
			if (player.getMoney() > amtOwed) {
				StdOut.println("You currently have enough enough money to pay this. hit enter when ready to have it deducted from your bank account.");
				StdIn.readString();
				player.subtMoney(amtOwed);
				player.showBalance();
			}
			count++;
			if (count == 4)
				finished = true;
		}

		if (uncertainty) {
			StdOut.println("Uncertainty in the world markets and high U.S. strategic petroleum Reserve."
					+ "\n Disagreement between oil producing companies and a high us stockpile is good news for consumers."
					+ "\n Immediately remove 5 barrels of Crude Oil from the Foreign Market "
					+ "\n (the 5 least expensive barrels that are available) and move them to the Domestic Market "
					+ "\n (the 5 most expensive empty spaces available)."
					+ "\n The price of Gasoline on the Consumer Market is immediately decreased by 5 red space "
					+ "\n (no lower than the last red space on the board)."
					+ "\n Then return this card to the bottom of the News Deck.");
			count++;
			Market.uncertainty();
			Economy.changeDemand(-5);
			if (count == 1)
				finished = true;
		}
		if (finished)
			count = 0;
		return finished;
	}
}
