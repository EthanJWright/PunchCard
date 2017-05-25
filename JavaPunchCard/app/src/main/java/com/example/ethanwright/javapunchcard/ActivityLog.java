package com.example.ethanwright.javapunchcard;
import android.widget.Button;


import java.util.*;





public class ActivityLog {
	// Currently not doing anything with this, can be used for logging information
	private ArrayList<Date> start_log = new ArrayList<Date>();
	private ArrayList<Date> end_log = new ArrayList<Date>();

	// This is time that is set when user 'punches in'
	private Date start_active;
	// This is updated every second by timer process
	private long active_duration = 0;
	public TimerTask printActiveTimer;
	// This allows for user to punch in and out without issue
	private long previous_active = 0;

	// Called form punch in, this is called every second of timer tick
	public void punch_card_active(){
		active_duration = previous_active + active_time();

	}



	// To be run when a card timer is supposed to start
	public void punch_in(){
		start_active = new Date();
		start_log.add(new Date());
		 Timer timerObj = new Timer();
		    printActiveTimer = new TimerTask() {
		        public void run() {
		        	punch_card_active();
		        }
		    };
		    timerObj.schedule(printActiveTimer, 0, 1000);

	}

	// Run when card timer is supposed to end
	public void punch_out(){
		previous_active = active_duration;
        // stop the timer
		printActiveTimer.cancel();
        // log the changes
		end_log.add(new Date());
	}

	// This will return the difference in time between when card was punched in  and now
    private long active_time(){
    	long current_time = new Date().getTime();
    	long current_active;
        current_active = current_time - start_active.getTime();
        return current_active;
    }

	public ArrayList<Date> getStart_log() {
		return start_log;
	}

	public ArrayList<Date> getEnd_log() {
		return end_log;
	}


	public long getActive_duration() {
		return active_duration;
	}

    
    
}

