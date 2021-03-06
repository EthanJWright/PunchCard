package com.example.ethanwright.javapunchcard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;


public class CardDeck {
	private ArrayList<PunchCard> deck = new ArrayList<>();
	private String category;
	private Integer totalCount = 0;
	private Date dateCreated = new Date();
    private int color;


    public int getColor(){
        return color;
    }


    public void setColor(int color) {
        this.color = color;
    }

    public void clearAll(){
		ArrayList<PunchCard> list = deck;
		for(Iterator<PunchCard> iter = list.listIterator(); iter.hasNext();){
			PunchCard a = iter.next();
			a.clearProgress();
		}
		deck = list;
	}

    public void setNewDeck(String... args){
		if(args.length == 0){
			category = "default";
		}
		else{
			category = args[0];
		}
		
		Date _dateCreated = new Date();
		deck = new ArrayList<>();
		dateCreated = _dateCreated;
		totalCount = 0;
	
	}

	public Long getTimeWorkedToday(){
		ArrayList<PunchCard> list = deck;
		Date currentDate = new Date();
		Long timeWorkedToday = (long) 0;
		for (Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ) {
		    PunchCard a = iter.next();
			timeWorkedToday += a.getTimeWorkedToday();
		    }
		    return timeWorkedToday;
	}

	public Long getTotalTimeWorked(){
		Long totalTime = (long) 0;
		ArrayList<PunchCard> list = deck;
		for (Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ) {
		    PunchCard a = iter.next();
			totalTime += a.getTotalWorked();
		    }
		    return totalTime;
	}

	public Long getTimeWorked(){
        long timeWorked = 0;
	    ArrayList<PunchCard> list = deck;
		for (Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ) {
		    PunchCard a = iter.next();
			timeWorked += a.getActiveDuration();
		    }
        return timeWorked;
	   	}


	
	public void addCard(PunchCard card){
		card.setCategoryName(category);
		totalCount += 1;
		deck.add(card);	
	}

    public void removeCard(PunchCard card){
		card.setCategoryName("default");
		totalCount -= 1;
        deck.remove(card);
    }

	public PunchCard findCard(String name){
	    if(deck.size() == 1){
    		System.out.println("before return");
    		System.out.println(deck.get(0).getName());
	    	if(deck.get(0).getName().equals(name)){
	    		return deck.get(0);
	    	}
	    	else{
	    		return null;
	    	}
	    }
		/* Find a card in the list of deck */
	    ArrayList<PunchCard> list = deck;
		for (Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ) {
		    PunchCard a = iter.next();
		    if (a.getName().equals(name)) {
		        return a;	    
		    }		
	   	}
		return null;
	}
	

	public ArrayList<PunchCard> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<PunchCard> deck) {
		this.deck = deck;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	
	
}
