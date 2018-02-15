package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class TextOfToday {

	private ArrayList<Paragraph> day;
	private String datum;
	private boolean hasTitle;
	
	public TextOfToday() {
		day = new ArrayList<Paragraph>();
	}
	
	public ArrayList<Paragraph> getContent(){
		return day;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getDatum(){
		return datum;
	}
	
	public String getMonth(){
		return datum.split(" ")[0];
	}

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}
}
