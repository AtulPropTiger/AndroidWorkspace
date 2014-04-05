package card;

public class Card {

	public int value;
	public int rank;
	public String suit; // hearts, diamonds, clubs, spades
	public String name; //card name , king
	public boolean in_deck; // true if card in deck, false if in player or dealers hand

	public String printCard(Card temp){
		return temp.name + " of " + temp.suit; //ie Ace of Spades
	}
}


