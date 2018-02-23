package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class TextOfToday {
	private int page;
	private String datum = "";
	private ArrayList<Paragraph> day;
	private boolean invalid;
	
	
	public TextOfToday(int page) {
		day = new ArrayList<Paragraph>();
		this.page=page;
	}
	
	// GETTERS & SETTERS
	public int getPage() {
		return page;
	}
	public String getDatum(){
		return datum;
	}
	public void setDatum(String datum) {
		this.datum += datum;
	}
	public ArrayList<Paragraph> getContent(){
		return day;
	}
	public boolean isInvalid() {
		return invalid;
	}
	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
	public String getMonth(){
		return datum.split(" ")[0];
	}
}
