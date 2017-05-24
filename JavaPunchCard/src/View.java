import java.util.Scanner;

class View {
	private static Scanner scanner;
	
	public static void main(String args[]){
        // Create User 
		User model = new User();
		model.setNewUser("John Smith");
		
		// Create first deck
		CardDeck deck = new CardDeck();
		deck.setNewDeck();
		
		//Give deck to user
		model.addDeck(deck);

        // Create card
		PunchCard card = new PunchCard();
        /* Set end date in three days  */
        card.generateNewCard("work", 3);
        model.findDeck("default").addCard(card);
		System.out.println(model.findDeck("default").findCard("work").getName());
     
		while(true){
		System.out.println("For Operating Systems would you like to punch in(i) or punch out(o)?");
		scanner = new Scanner(System.in);
		String search = scanner.nextLine().toLowerCase();
		boolean response;
		if(search.equals("i")){
			response = true;
		}
		else{
			response = false;
		}
		model.findDeck("default").findCard("work").setActive(response);
	}
 
	}


}
