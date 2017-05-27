package com.example.ethanwright.javapunchcard;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class User {
	private ArrayList<CardDeck> deck_set = new ArrayList<>();
	private String name = "";
	private Date registrationDate = new Date();
	private int number_of_decks = 0;
	private CardDeck active = new CardDeck();




    public ArrayList<PunchCard> getAllCards () {
        ArrayList<PunchCard> allCards = new ArrayList<>();
        ArrayList<CardDeck> list = deck_set;
    /* Iterate through all card decks  */
        for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
            CardDeck check_deck = iter.next();
            ArrayList<PunchCard> list_card = check_deck.getDeck();
        /* Iterate through all cards  */
            for (Iterator<PunchCard> iter_card = list_card.listIterator(); iter_card.hasNext(); ) {
                PunchCard new_card = iter_card.next();
                allCards.add(new_card);

            }

        }
        return allCards;
    }

	
	public CardDeck getActive(){
		/* Find a card in the list of deck */
	    ArrayList<CardDeck> list = deck_set;
	    active = new CardDeck();
	    active.setNewDeck("active");
	    /* Iterate through all card decks  */
		for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
		    CardDeck check_deck = iter.next();
		    ArrayList<PunchCard> list_card = check_deck.getDeck();
		    /* Iterate through all cards  */
		    for(Iterator<PunchCard> iter_card = list_card.listIterator(); iter_card.hasNext(); ){
		    	PunchCard check_card = iter_card.next();
		    	if(check_card.isActive()){
		    		active.addCard(check_card);
		    	}
		    }
		    
	   	}
		return active;
	}
	
	public void setNewUser(String _name){
		name = _name;
		registrationDate = new Date();
		number_of_decks = 0;
		deck_set = new ArrayList<CardDeck>();
	}
	
	public void addDeck(CardDeck deck){
		deck_set.add(deck);
		number_of_decks += 1;
	}
	
	public CardDeck findDeck(String category){
		/* Find a category in the list of decks */
		if(deck_set.size() == 1){
			return deck_set.get(0);
		}
	    ArrayList<CardDeck> list = deck_set;
		for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
		    CardDeck a = iter.next();
		    if (a.getCategory().equals(category)) {
		        return a;	    
		    }		
	   	}
		return null;
	}
	
	
	public int getNumber_of_decks() {
		return number_of_decks;
	}

	public void setNumber_of_decks(int number_of_decks) {
		this.number_of_decks = number_of_decks;
	}

	public ArrayList<CardDeck> getDeck_set() {
		return deck_set;
	}
	public void setDeck_set(ArrayList<CardDeck> deck_set) {
		this.deck_set = deck_set;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	


}



