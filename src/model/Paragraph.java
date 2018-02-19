package model;

import com.itextpdf.text.pdf.parser.Vector;

public class Paragraph {
	
	public enum detail {Vers, Passage, Heading, Paragraph};
	public enum style {Bold, Italic, Normal}
	private int ordDetail;
	private String paragraph;
	private String font;
	private String position;
	
	
	public Paragraph(String para, style style, Vector pos, int size) {
		this.paragraph = para;
		this.font = style.name()+" "+size;
		this.position = (int)pos.get(0)+" "+(int)pos.get(1);
	}
	
	public void add (String word){
		paragraph += word;
	}
	
	public void setDetail(detail detail){
		ordDetail = detail.ordinal();
	}
	public String getParagraph() {
		return paragraph;
	}
	public int getOrdDetail() {
		return ordDetail;
	}
	public String getFont() {
		return font +" Words: "+paragraph.split(" ").length;
	}
	public String getPosition() {
		return position;
	}


}
