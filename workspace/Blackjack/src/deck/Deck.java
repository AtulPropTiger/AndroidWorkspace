package deck;
import java.util.Vector;
import card.Card ;
import java.util.Random;

public class Deck {
	private Vector<Card> deck_of_cards = new Vector<Card>(52,1);
	
	public Deck(){
	    //public Vector<String> four_suits = new Vector<String>();
		//Vector<Card> deck_of_cards = new Vector<Card>(52,1);
		Vector<String> four_suits = new Vector<String>();
		four_suits.add("Hearts");
		four_suits.add("Spades");
		four_suits.add("Clubs");
		four_suits.add("Diamonds");
		for (int j =0; j<4 ; j++){ // stch suits
			String tempsuit = four_suits.get(j); // change suit
			for (int i = 1; i <14 ;i++){ //13 cards of one suit
				Card temp = new Card();
				if (i== 1){
					temp.value = 11; //ace = 1 or 11
					temp.name = "Ace";
				}
				else if(i == 11 || i == 12 || i ==13){
					temp.value = 10;//jack,queen,king
					if (i ==11){
						temp.name = "Jack";
					}
					else if( i == 12){
						temp.name = "Queen";
					}
					else{
						temp.name = "king";
					}
				}
				else{
					temp.value = i;//all non ace non face cards
					temp.name = Integer.toString(i);
				}
				temp.rank = i;
				temp.suit = tempsuit;
				temp.in_deck = true;
				deck_of_cards.add(temp);
				
			}
		}
	}
	
	public Card drawCard(){
		Random rand = new Random();
		int  rand_num = rand.nextInt(51) + 0; //0 to 51, all cards in deck
		Card temp = deck_of_cards.get(rand_num);
		deck_of_cards.remove(rand_num);
		return temp;
	}
	
	public void giveCardBack(Card temp){
		deck_of_cards.add(temp);
	}
	
	


}
