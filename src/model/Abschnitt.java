package model;

import java.util.ArrayList;

public class Abschnitt {
	
	private ArrayList<String> words = new ArrayList<String>();
	private String text;
	private String font;
	private int size;
	private int lastX;
	private int lastY;
	private int color;
	private boolean isHeading;
	
	
	public Abschnitt(Word word) {
		words.add(word.getText());
		font = word.getFont();
		size = word.getSize();
		lastX = word.getX();
		lastY = word.getY();
		color = word.getColor();
		isHeading = word.isHeading();
		
	}
	
	public void addWord(Word word) {
		words.add(word.getText());
		lastX = word.getX();
		lastY = word.getY();
	}
	
	public void say() {
		System.out.println("Artikel "+ font + "  " + words.size() + "  " + words.get(0)+" "+color);
	}

	public ArrayList<String> getWords() {
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
	

	
	

}
