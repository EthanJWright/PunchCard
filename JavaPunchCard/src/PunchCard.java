import java.util.Date;

public class PunchCard {
	private boolean active;
	private Date beginDate = new Date();
	private Date endDate = new Date();
    private long goal;
	private String name;
	private String categoryName;
	private ActivityLog logger = new ActivityLog();

	
	public void generateNewCard(String _name, int day_number){
		name = _name;
        Date _beginDate = new Date();
		beginDate = _beginDate;
		endDate = new Date();
		long milliseconds_day = 86400000;
		endDate.setTime(endDate.getTime() + (day_number * milliseconds_day));
		goal = endDate.getTime() - beginDate.getTime();
		active = false;
		categoryName = "No Category";
	}
	

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean _active) {
		if(_active){
			logger.punch_in();
		}
		if(!_active){
			logger.punch_out();
		}
		this.active = _active;
	}


	public ActivityLog getLogger() {
		return logger;
	}


	public void setLogger(ActivityLog logger) {
		this.logger = logger;
	}


	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate() {
		Date current = new Date();
		this.beginDate = current;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getGoal() {
		return goal;
	}
	public void setGoal(long goal) {
		this.goal = goal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	

}
