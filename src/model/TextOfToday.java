package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class TextOfToday {
	private ArrayList<Paragraph> day;
	private int AmountOfParagraphs = -1;
	private boolean invalid;
	
	
	public TextOfToday() {
		day = new ArrayList<Paragraph>();

	}
	
	// PRIVATE
	private int countAmountOfParagraphs() {
		int para=0;
		for (Paragraph p: day){
			if(p.getOrdDetail()==3)
				para++;
		}
		return para;	
	}
	
	// GETTERS & SETTERS

	public ArrayList<Paragraph> getContent(){
		return day;
	}
	public int getAmountOfParagraphs() {
		if(AmountOfParagraphs==-1)
			AmountOfParagraphs = countAmountOfParagraphs();
		return AmountOfParagraphs;
	}
	public boolean isInvalid() {
		return invalid;
	}
	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
	
}
