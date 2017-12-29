package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class DailyText {

	ArrayList<String> day;
	
	public DailyText() {
		day = new ArrayList<String>();
	}
	
	public ArrayList<String> getDay(){
		return day;
	}
	
	}
