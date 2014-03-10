import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;


public class NewsDeck {
	
	private static boolean finished;
	private static ArrayList<NewsCard> deck;
	 
	public NewsDeck(){
		finished = false;
		deck = new ArrayList<NewsCard>();
		NewsCard deck0 = new NewsCard();
		NewsCard deck1 = new NewsCard();
		NewsCard deck2 = new NewsCard();
		NewsCard deck3 = new NewsCard();
		NewsCard deck4 = new NewsCard();
		NewsCard deck5 = new NewsCard();
		deck0.setToAggressive();
		deck1.setToGlobalWarming();
		deck2.setToLongColdWinter();
		deck3.setToSnapInspection();
		deck4.setToStateSalesTaxes();
		deck5.setToUncertainty();
		deck.add(deck0);
		deck.add(deck1);
		deck.add(deck3);
		deck.add(deck4);
		deck.add(deck5);
		Collections.shuffle(deck);
	}

	public static boolean mostLikely(Player player){
		NewsCard cardBeingUsed = deck.get(0);
		finished = cardBeingUsed.carryOut(player);
		if(finished){
			NewsCard toBottomOfDeck = deck.get(0);
			deck.remove(0);
			deck.add(toBottomOfDeck);
		}
		return finished;
	}
	
	public static boolean leastLikely(Player player){
		finished = deck.get(1).carryOut(player);
		if(finished){
			NewsCard toBottomOfDeck = deck.get(1);
			deck.remove(1);
			deck.add(toBottomOfDeck);
		}
		return finished;
	}
}
