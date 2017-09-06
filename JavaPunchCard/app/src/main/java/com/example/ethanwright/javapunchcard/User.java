package com.example.ethanwright.javapunchcard;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class User {
	private ArrayList<CardDeck> deck_set = new ArrayList<>();
	private ArrayList<PunchCard> archive = new ArrayList<>();
	private String name = "";
	private Date registrationDate = new Date();
	private int number_of_decks = 0;
	private CardDeck active = new CardDeck();

	public ArrayList<Long> getDailyActivity(){
		ArrayList<CardDeck> list = deck_set;
		ArrayList<Long> worked = new ArrayList<>();
		for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
			CardDeck a = iter.next();
			worked.add(a.getTimeWorked());
		}
        return worked;
    }


	public void archive(PunchCard card){
		archive.add(card);
		String category = card.getCategoryName();
        String name = card.getName();
        if(category == ""){
            category = "default";
        }

        // If the category doesn't exist, make the category
        if(findDeck(category) == null){
            return;
        }
        // Now that we definitely have the deck, check to see if card exists already
        if(findDeck(category).findCard(name) == null){
            return;
        }
        else{
            PunchCard temp_card = findDeck(category).findCard(name);
            findDeck(category).removeCard(temp_card);
        }

	}

	public void removeDeck(String category){
		ArrayList<CardDeck> list = deck_set;
		ArrayList<CardDeck> newDeckSet = new ArrayList();
		for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
			CardDeck a = iter.next();
			if(!category.equals(a.getCategory())){
				newDeckSet.add(a);
			}
		}
		deck_set = newDeckSet;
	}

	public ArrayList<PunchCard> getArchive(){
		return archive;
	}




    public ArrayList<PunchCard> getAllCards () {
        ArrayList<PunchCard> allCards = new ArrayList<>();
        ArrayList<CardDeck> list = deck_set;
    // Iterate through all card decks
        for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
            CardDeck check_deck = iter.next();
            ArrayList<PunchCard> list_card = check_deck.getDeck();
        // Iterate through all cards
            for (Iterator<PunchCard> iter_card = list_card.listIterator(); iter_card.hasNext(); ) {
                PunchCard new_card = iter_card.next();
                allCards.add(new_card);

            }

        }
        return allCards;
    }

	
	public CardDeck getActive(){
		// Find a card in the list of deck
	    ArrayList<CardDeck> list = deck_set;
	    active = new CardDeck();
	    active.setNewDeck("active");
	    // Iterate through all card decks
		for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
		    CardDeck check_deck = iter.next();
		    ArrayList<PunchCard> list_card = check_deck.getDeck();
		    // Iterate through all cards
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
		deck_set = new ArrayList<>();
	}
	
	public void addDeck(CardDeck deck){
		deck_set.add(deck);
		number_of_decks += 1;
	}
	
	public CardDeck findDeck(String category){
		// Find a category in the list of decks
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



