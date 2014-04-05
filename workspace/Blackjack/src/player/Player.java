package player;
import java.util.Vector;

import card.Card; // so  can use card objects

public class Player {
	public Vector<Card> hand = new Vector<Card>(0,1); // the players hand
	public int points;
	public boolean stay;
	public boolean aceflipped;
	public Player(){
		points = 0;
		stay = false;
		aceflipped = false;
	}
	
	public String printHand(){
		String h = "";
		Card temp = new Card();
		for (int i = 0; i < hand.size(); i++){
			h+= temp.printCard(hand.get(i)) + ", ";
		}
		return h;
	}
	
	public boolean checkForAce(){
		int count = 0;
		Card temp = new Card();
		if (aceflipped == false){
			for (int i = 0; i < hand.size(); i++){
				temp = hand.get(i);
				if ((temp.name).equals("Ace")){
					this.aceflipped = true;
					count =1;
				}
			}
			if (count ==1){return true;}
		}
	    return false; //ace was already flipped from value 11 to 1
	}
	
}
