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
	
	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public TextOfToday(int page) {
		day = new ArrayList<Paragraph>();
		this.page=page;
	}
	
	public int getPage() {
		return page;
	}

	public ArrayList<Paragraph> getContent(){
		return day;
	}
	public void setDatum(String datum) {
		this.datum += datum;
	}
	public String getDatum(){
		return datum;
	}
	public String getMonth(){
		return datum.split(" ")[0];
	}

}
