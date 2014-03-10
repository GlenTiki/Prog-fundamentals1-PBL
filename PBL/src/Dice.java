import java.util.Random;

public class Dice {
	
	static Random randomNumbers = new Random();
	
	public static int rollDice() {
		int diceRoll;
		diceRoll = 1 + randomNumbers.nextInt(6);
		return diceRoll;
	}
}