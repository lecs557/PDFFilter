package model;

import java.util.ArrayList;

public class Abschnitt {
	
	private ArrayList<Word> words = new ArrayList<Word>();
	private String text;
	private String font;
	private int size;
	private int lastX, firstX;
	private int lastY, firstY;
	private int color;
	private boolean isHeading;
	
	
	public Abschnitt(Word word) {
		word.setAbschnitt(this);
		words.add(word);
		color = word.getColor();
		font = word.getFont();
		size = word.getSize();
		lastX = word.getX();
		lastY = word.getY();
		isHeading = word.isHeading();
		firstY = word.getY();
		firstX = word.getX();
		
	}
	
	public void addWord(Word word) {
		word.setAbschnitt(this);
		words.add(word);
		lastX = word.getX();
		lastY = word.getY();
	}
	
	public String getResult() {
		String result;
		if (font.contains("It")) {
			result="<it>";
			for (Word w: words) {
				result += w.getText();
			}
			result +="</it>";		
		} else if(isHeading) {
			result ="<h3>";
			for (Word w: words) {
				result +=w.getText();
			}
			result +="</h3>";
		} else {
			result ="<p>";
			for (Word w: words) {
				result +=w.getText();
			}
			result +="</p>";
		}	
		return result;
	}

	public String getInfo() {
		return font+" s "+size+" "+firstX+":"+firstY+" -  "+lastX+":"+lastY+" h "+isHeading+" x "+color;
	}
	public ArrayList<Word> getWords() {
		return words;
	}

	public String getText() {
		return text;
	}

	public String getFont() {
		return font;
	}

	public int getSize() {
		return size;
	}

	public int getLastX() {
		return lastX;
	}

	public boolean isHeading() {
		return isHeading;
	}

	public int getLastY() {
		return lastY;
	}

	public int getFirstY() {
		return firstY;
	}
	
}
