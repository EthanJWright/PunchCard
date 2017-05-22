import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

public class CardDeck {
	private ArrayList<PunchCard> deck;
	private String category;
	private Integer totalCount;
	private Date dateCreated;
	
	public void setNewDeck(String... args){
		if(args.length == 0){
			category = "default";
		}
		else{
			category = args[0];
		}
		System.out.println(category);
		
		Date _dateCreated = new Date();
		deck = new ArrayList<PunchCard>();
		dateCreated = _dateCreated;
		totalCount = 0;
	
	}
	
	public void addCard(PunchCard card){
		card.setCategoryName(category);
		totalCount += 1;
		deck.add(card);	
	}

    public void removeCard(PunchCard card){
		card.setCategoryName("No Category");
		totalCount -= 1;
        deck.remove(card);
    }

	public PunchCard findCard(String name){
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
