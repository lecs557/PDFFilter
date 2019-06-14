package model;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class Word {
	
	private String text;
	private String font;
	private int size;
	private int x;
	private int y;
	
	public Word(TextRenderInfo tri) {
		text = tri.getText();
		font = tri.getFont().getPostscriptFontName();
		size = (int)tri.getRise();
		x = (int) tri.getBaseline().getStartPoint().get(0);
		y = (int)tri.getBaseline().getStartPoint().get(1);	
	}
	
	public Word(String a) {
		text=a;
	}
	
	public void sayIt() {
		System.out.println(text+" ");
	}
	
	public void addLetter(String letter) {
		text += letter;
	}
	
	public boolean check() {
		if(text.endsWith("-")) {
			text = text.substring(0,text.length()-1);
			return true;
		} else 
			return false;
		
	}
	
	

}
