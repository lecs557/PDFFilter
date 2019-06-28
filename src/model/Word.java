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
	private Abschnitt abschnitt;
	


	public Word(String a) {
		text=a;
	}
	
	public Word(TextRenderInfo tri) {
		text = tri.getText();
		font = tri.getFont().getPostscriptFontName();
		x = (int) tri.getBaseline().getStartPoint().get(0);
		y = (int)tri.getBaseline().getStartPoint().get(1);	
		size = (int)tri.getAscentLine().getStartPoint().get(1)-y;
		
		if(tri.getFillColor() != null)
			color = tri.getFillColor().getGreen();
		else color=0;
			
		isHeading = font.contains("bold");
	}
	
	public Word(String w,TextRenderInfo tri) {
		text = w;
		font = tri.getFont().getPostscriptFontName();
		x = (int) tri.getBaseline().getStartPoint().get(0);
		y = (int)tri.getBaseline().getStartPoint().get(1);	
		size = (int)tri.getAscentLine().getStartPoint().get(1)-y;
		if(tri.getFillColor() != null)
			color = tri.getFillColor().getGreen();
		else
			color=0;
		
		isHeading = color != 0 && font.contains("Bold");
	}

	public void addLetter(String letter) {
		text += letter;
	}
	
	public void addLetter(String letter,TextRenderInfo tri) {
		text += letter;
		font = tri.getFont().getPostscriptFontName();
		if(tri.getFillColor() != null)
			color = tri.getFillColor().getGreen();
		else color=0;
			
		isHeading = color != 0 && font.contains("Bold");
	}
	// METHODS
	
	public boolean check() {
		if(text.endsWith("-")) {
			text = text.substring(0,text.length()-1);
			return true;
		} else 
			return false;
	}
	
	
	// GETTERS & SETTERS
	
	public String getText() {
		if (isHeading)
			return text;
		else if(font.contains("Bold"))
			return "<b>" + text + "</b>";
		else if(font.contains("It") && !abschnitt.getFont().contains("It"))
			return "<it>" + text + "</it>";
		else if(size==5)
			return "<abcdef>"+text+"</abcdef>";
		else
			return text;
	}

	public String getFont() {
		return font;
	}

	public int getSize() {
		return size;
	}

	public int getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isHeading() {
		return isHeading;
	}

	public Abschnitt getAbschnitt() {
		return abschnitt;
	}

	public void setAbschnitt(Abschnitt abschnitt) {
		this.abschnitt = abschnitt;
	}


}
