package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class TextOfToday {

	private String datum;
	private ArrayList<Paragraph> day;
	
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

}
