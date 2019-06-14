package model;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class Word {
	
	private String text;
	private String font;
	private int size;
	
	public Word(String letter,String f, int s) {
		text = letter;
		font = f;
		size = s;
	}
	
	public void addLetter(String letter) {
		text += letter;
	}
	
	

}
