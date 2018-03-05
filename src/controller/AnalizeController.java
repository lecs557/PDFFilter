package controller;

import java.util.ArrayList;

import model.TextOfToday;
import model.Main;
import model.Paragraph;
import model.Session;

public class AnalizeController {
	private Session session = Main.getSession();
	private PDFController pdfC = session.getPdfController();
	private TextOfToday today;
	private ArrayList<String> daysSegments;
	private ArrayList<ArrayList<String>> monthList = new ArrayList<ArrayList<String>>();
	private ArrayList<String> textList = new ArrayList<String>();
	private ArrayList<String> positionList = new ArrayList<String>();
	private ArrayList<String> fontList = new ArrayList<String>();
	private ArrayList<Integer> detailList = new ArrayList<Integer>();
	private ArrayList<Integer> paraList = new ArrayList<Integer>();
	private String month="";
	
	
	// PUBLIC
	public void analize(){
		today = pdfC.getTextOfToday();
		if ( session.isHasDate() && !today.isInvalid()){
			buildUpMonthList();
		}
		buildUpOptionLists();
	}
	
	// PRIVATE
	private void buildUpMonthList (){
		if ( month.equals(today.getMonth()) ){
			daysSegments.add(monthListContent());			
		} else{
			month = today.getMonth();
			daysSegments = new ArrayList<String>();
			monthList.add(daysSegments);
			daysSegments.add(month+"\n"+monthListContent());
		}
	}
	
	private void buildUpOptionLists(){
		if(today.isInvalid()){
			today.setDatum("Invalid");
			detailList.add(3);
			paraList.add(55);
			textList.add("FEHLER");
			fontList.add("FEHLER");
			positionList.add("FEHLER");
		} else {
			for(Paragraph para:today.getContent()){
				detailList.add(para.getOrdDetail());
				textList.add(para.getParagraph());
				fontList.add(para.getFont());
				positionList.add(para.getPosition());
				paraList.add(para.getParaNum());
			}
		}
	}
	
	private String monthListContent(){
		return today.getPage()+" "+today.getAmountOfParagraphs();
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

	public ArrayList<Integer> getParaList() {
		return paraList;
	}
}
