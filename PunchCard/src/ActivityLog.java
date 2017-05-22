import java.util.Date;
import java.util.ArrayList;

public class ActivityLog {
	private ArrayList<Date> start_log = new ArrayList<Date>();
	private ArrayList<Date> end_log = new ArrayList<Date>();
	
	private Date start_active;
	private long active_duration = 0;
	
	
	public void punch_in(){
		start_active = new Date();
		start_log.add(new Date());
	}
	
	public void punch_out(){
		active_duration += active_time();
		end_log.add(new Date());
	}
	
    public long active_time(){
    	long current_time = new Date().getTime();
    	long current_active = current_time - start_active.getTime();
    	return current_active;
    }

	public ArrayList<Date> getStart_log() {
		return start_log;
	}

	public void setStart_log(ArrayList<Date> start_log) {
		this.start_log = start_log;
	}

	public ArrayList<Date> getEnd_log() {
		return end_log;
	}

	public void setEnd_log(ArrayList<Date> end_log) {
		this.end_log = end_log;
	}

	public Date getStart_active() {
		return start_active;
	}

	public void setStart_active(Date start_active) {
		this.start_active = start_active;
	}

	public long getActive_duration() {
		return active_duration;
	}

	public void setActive_duration(long active_duration) {
		this.active_duration = active_duration;
	}
    
    
}

