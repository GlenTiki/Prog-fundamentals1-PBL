public class Market {

	private static int domesticGasBuyPrice;
	private static int domesticCrudeBuyPrice;
	private static int foreignGasBuyPrice;
	private static int foreignCrudeBuyPrice;

	public Market() {
		domesticGasBuyPrice = 16;
		domesticCrudeBuyPrice = 6;
		foreignGasBuyPrice = 16;
		foreignCrudeBuyPrice = 6;
	}
	
	public static void uncertainty(){
		foreignCrudeBuyPrice = foreignCrudeBuyPrice - 5;
		domesticCrudeBuyPrice = domesticCrudeBuyPrice + 5;
		if (domesticCrudeBuyPrice > 62) domesticCrudeBuyPrice = 62;
		if(foreignCrudeBuyPrice < 1) foreignCrudeBuyPrice = 1;
	}
	

	public static int buyGasDomestic(boolean pricing) {
		int price = domesticGasBuyPrice;
		if (!pricing) {
			if (domesticGasBuyPrice < 62) {
				domesticGasBuyPrice += 2;
			} else {
				price = 0;
				StdOut.println("The market is empty! You cant buy from here!");
			}
		}
		return price;
	}

	public static int buyCrudeDomestic(boolean pricing) {
		int price = domesticCrudeBuyPrice;
		if (!pricing) {
			if (domesticCrudeBuyPrice < 31) {
				domesticCrudeBuyPrice++;
			} else {
				price = 0;
				StdOut.println("The market is empty! You cant buy from here!");
			}
		}
		return price;
	}

	public static int sellGasDomestic(boolean pricing) {
		int price = domesticGasBuyPrice - 2;
		if (!pricing) {
			if (domesticGasBuyPrice > 2) {
				domesticGasBuyPrice -= 2;
			} else {
				price = 0;
				StdOut.println("The market is full! You cant sell here!");
			}
		}
		return price;
	}

	public static int sellCrudeDomestic(boolean pricing) {
		int price = domesticCrudeBuyPrice - 1;
		if (!pricing) {
			if (domesticCrudeBuyPrice > 1) {
				domesticCrudeBuyPrice--;
			} else {
				price = 0;
				StdOut.println("The market is full! You cant sell here!");
			}
		}
		return price;
	}

	public static int buyGasForeign(boolean pricing) {
		int price = foreignGasBuyPrice;
		if (!pricing) {
			if (foreignGasBuyPrice < 62) {
				foreignGasBuyPrice += 2;
			} else {
				price = 0;
				StdOut.println("The market is empty! You cant buy from here!");
			}
		}
		return price;
	}

	public static int buyCrudeForeign(boolean pricing) {
		int price = foreignCrudeBuyPrice;
		if (!pricing) {
			if (foreignCrudeBuyPrice < 31) {
				foreignCrudeBuyPrice--;
			} else {
				price = 0;
				StdOut.println("The market is empty! You cant buy from here!");
			}
		}
		return price;
	}

	public static int sellGasForeign(boolean pricing) {
		int price = foreignGasBuyPrice - 2;
		if (!pricing) {
			if (foreignGasBuyPrice > 2) {
				foreignGasBuyPrice -= 2;
			} else {
				price = 0;
				StdOut.println("The market is full! You cant sell here!");
			}
		}
		return price;
	}

	public static int sellCrudeForeign(boolean pricing) {
		int price = foreignCrudeBuyPrice - 1;
		if (!pricing) {
			if (foreignCrudeBuyPrice > 1) {
				foreignCrudeBuyPrice--;
			} else {
				price = 0;
				StdOut.println("The market is full! You cant sell here!");
			}
		}
		return price;
	}
}
