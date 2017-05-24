import java.util.Date;

public class Categories {
	private String name;
	private CardDeck contents;
	private Date dateCreated;
	private Integer totalCount;
	
	public void setNewCategory(String _name){
		Date _dateCreated = new Date();
		contents = new CardDeck();
		dateCreated = _dateCreated;
		totalCount = 0;
		name = _name;
	}
	
	public void removeCard(PunchCard removing){
		removing.setCategoryName("No Category");
		contents.removeCard(removing);
		totalCount -= 1;
	}
	
	public void addCard(PunchCard adding){
		adding.setCategoryName(name);
		totalCount += 1;
		contents.addCard(adding);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardDeck getContents() {
		return contents;
	}

	public void setContents(CardDeck contents) {
		this.contents = contents;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
