package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class DailyText {

	private String datum;
	private ArrayList<String> day;
	private boolean hasTitle;
	
	public DailyText() {
		day = new ArrayList<String>();
	}
	
	public ArrayList<String> getDay(){
		return day;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getDatum(){
		return datum;
	}

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}
}
