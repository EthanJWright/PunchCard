import java.util.Scanner;

class View {
	private static Scanner scanner;
	
	public static void main(String args[]){
        // Create User 
		User model = new User();
		model.setNewUser("Ethan");
		
		CardDeck deck = new CardDeck();
		deck.setNewDeck();
		model.addDeck(deck);

        // Create card
		PunchCard card = new PunchCard();

        /* Set end date in three days  */
        card.generateNewCard("Operating Systems", 4);
        card.setActive(true);
       
        /*
		model.findDeck("default").addCard(card);
		
		card = new PunchCard();
        card.generateNewCard("SPD", 7);
        card.setActive(true); 
		model.findDeck("default").addCard(card);
		
		CardDeck active = model.getActive();
		System.out.println(active.getDeck().size());
		System.out.println(active.getDeck().get(0).getName());
		System.out.println(active.getDeck().get(1).getName());
		
		//while(true){
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
		
		active.getDeck().get(0).setActive(response);
		long seconds = active.getDeck().get(0).getLogger().getActive_duration() / 1000;
		System.out.println(seconds);
		
       // TODO add support for no arguments in findDeck
		//TODO implement running timer
		//}
		 
		 */
	}


}
