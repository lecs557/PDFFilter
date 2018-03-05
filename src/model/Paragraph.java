package model;

import com.itextpdf.text.pdf.parser.Vector;

public class Paragraph {
	public enum detail {Vers, Passage, Heading, Paragraph};
	public enum style {Italic, Normal, Bold}
	private String paragraph;
	private String font;
	private String position;
	private int ordDetail;
	private int paraNum;
	

	// PUBLIC
	public Paragraph(int num, String para, style style, Vector pos, int size, detail detail) {
		this.paragraph = para;
		this.font = style.name()+" Größe:"+size;
		this.position = (int)pos.get(0)+" "+(int)pos.get(1);
		this.ordDetail = detail.ordinal();
		this.paraNum=num;
	}
	
	public void add (String word){
		paragraph += word;
	}
	
	// GETTERS & SETTERS
	public String getParagraph() {
		while(paragraph.contains("  "))
			paragraph = paragraph.replace("  ", " ");
		
		return paragraph;
	}
	public String getFont() {
		return font +" Words: "+paragraph.split(" ").length;
	}
	public String getPosition() {
		return position;
	}
	public int getOrdDetail() {
		return ordDetail;
	}

	public int getParaNum() {
		return paraNum;
	}
}
