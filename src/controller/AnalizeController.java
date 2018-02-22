package controller;

import java.util.ArrayList;

import model.TextOfToday;
import model.Main;
import model.Paragraph;
import model.Session;

public class AnalizeController {
	private Session session = Main.getSession();
	private TextFileController tfc = session.getTextFileController();
	private TextOfToday today;
	
	private ArrayList<String> daysSegments;
	private ArrayList<ArrayList<String>> monthList = new ArrayList<ArrayList<String>>();
	
	private ArrayList<String> textList = new ArrayList<String>();
	private ArrayList<String> positionList = new ArrayList<String>();
	private ArrayList<String> fontList = new ArrayList<String>();
	private ArrayList<Integer> detailList = new ArrayList<Integer>();
	
	private String month="";
	private int k = session.getStart();
	private boolean analyzeOnly=true;
	
	
	
	public void analize(){
		today = Main.getSession().getPdfController().getTextOfToday();
		if ( session.isHasDate() && !today.isInvalid()){
			buildUpMonthList(today);
		}
		buildUpOptionLists(today);
	}
	
	private void buildUpMonthList (TextOfToday today){
		if ( month.equals(today.getMonth()) ){
			daysSegments.add(monthListContent(today));			
		} else{
			month = today.getMonth();
			daysSegments = new ArrayList<String>();
			monthList.add(daysSegments);
			daysSegments.add(month+"\n"+monthListContent(today));
		}
	}
	
	private void buildUpOptionLists(TextOfToday today){
		if(today.isInvalid()){
			today.setDatum("Invalid");
			detailList.add(3);
			textList.add("FEHLER");
			fontList.add("FEHLER");
			positionList.add("FEHLER");
		} else {
			for(Paragraph para:today.getContent()){
				detailList.add(para.getOrdDetail());
				textList.add(para.getParagraph());
				fontList.add(para.getFont());
				positionList.add(para.getPosition());
			}
		}
	}
	
	private String monthListContent(TextOfToday today){
		int para=0;
		for (Paragraph p: today.getContent()){
			if(p.getOrdDetail()==3)
				para++;
		}
		return today.getPage()+" "+para;
	}
	
	
	// GETTERS & SETTERS
	public ArrayList<ArrayList<String>> getAmountOfSegments() {
		return monthList;
	}
	public ArrayList<String> getAnalizeText() {
		return textList;
	}
	public ArrayList<String> getAnalizeFont() {
		return fontList;
	}
	public ArrayList<String> getAnalizeX() {
		return positionList;
	}
	public ArrayList<Integer> getDetails() {
		return detailList;
	}
}
