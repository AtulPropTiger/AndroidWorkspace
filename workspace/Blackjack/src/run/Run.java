package run;
import card.Card;
import deck.Deck;
import player.Player;
import dealer.Dealer;
import java.util.Scanner;

public class Run {
	public static void main(String a[]){
		Deck b = new Deck();// = null;
		//Card temp = b.drawCard();
		//System.out.print(temp.name);
		Player user = new Player();
		Dealer dealer = new Dealer();
		System.out.println("the Dealer hits");
		while(user.points <21 && dealer.points <21) {
			Card temp = b.drawCard();
			//below is the dealers turn
			System.out.println("Dealer has: " + dealer.printHand());
			System.out.println("Dealer total: " + dealer.points);
			if (dealer.points < 18){
				dealer.stay = false;
				System.out.println("the Dealer hits");
				System.out.println("the Dealer drew a " + temp.printCard(temp));
				(dealer.hand).add(temp);
				dealer.points+= temp.value; // add new card value to dealers total
				if (dealer.points >= 21){
					if ( dealer.points == 21){
						System.out.println("the Dealer has 21 , the dealer Wins!");
					}
					else if(user.checkForAce()){
						user.points+= -10;
					}
					else{
						System.out.println("the Dealer busted, the user wins!");
					}
					return; // game over exit the program
				}
				
			}
			else{
				dealer.stay = true;
				System.out.println("the Dealer stays");
			}
			// dealers turn is over, player is up
			  System.out.println("Player has: " + user.printHand());
			  System.out.println("Player total: " + user.points);
			  Scanner in = new Scanner(System.in);
			  String hit_stay = "";
		      System.out.println("Hit(h) or Stay(s)");
		      hit_stay = in.nextLine();
		      System.out.println(hit_stay);
		      if (hit_stay.equals("h")){
		    	  	user.stay = false;
		    	    temp = b.drawCard(); // draw new card cuz you hit
					System.out.println("the Playerr hits");
					System.out.println("the Player drew a " + temp.printCard(temp));
					(user.hand).add(temp);
					user.points+= temp.value; // add new card value to dealers total
					if (user.points >= 21){
						if ( user.points == 21){
							System.out.println("the Playerr has 21 , the Player Wins!");
						}
						else if(user.checkForAce()){
							user.points+= -10;
						}
						else{
							System.out.println("the Player busted, the Dealer wins!");
						}
						return; // game over exit the program
					}
		      }
		      else{
					user.stay = true;
					System.out.println("the User stays");
					if (dealer.stay){
						if (user.points > dealer.points){
							System.out.println("Both players stayed, player had more points and wins!");
							return;
						}
						System.out.println("Both players stayed, dealer had more points and wins!");
						return;
					}
		      }
			
		}
		
	}
}
