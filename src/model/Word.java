package model;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class Word {
	
	private String text;
	private String font;
	private int size;
	private int x;
	private int y;
	private int color;
	private boolean isHeading;
	

	public Word(TextRenderInfo tri) {
		text = tri.getText();
		font = tri.getFont().getPostscriptFontName();
		size = (int)tri.getRise();
		x = (int) tri.getBaseline().getStartPoint().get(0);
		y = (int)tri.getBaseline().getStartPoint().get(1);	
		
			if(tri.getFillColor() != null)
		color = tri.getFillColor().getGreen();
			else color=0;
			
		isHeading = color != 0 && font.contains("bold");
	}
	
	public Word(String w,TextRenderInfo tri) {
		text = w;
		font = tri.getFont().getPostscriptFontName();
		size = (int)tri.getRise();
		x = (int) tri.getBaseline().getStartPoint().get(0);
		y = (int)tri.getBaseline().getStartPoint().get(1);	
		if(tri.getFillColor() != null)
			color = tri.getFillColor().getGreen();
		else color=0;
	}
	
	public int getColor() {
		return color;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Word(String a) {
		text=a;
	}
	
	public boolean isHeading() {
		return isHeading;
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
