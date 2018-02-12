package model;

import com.itextpdf.text.pdf.parser.Vector;

public class Paragraph {
	
	public enum detail {Vers, Passage, Heading, Paragraph};
	private String paragraph;
	private String font;
	private String position;
	private int ordDetail;
	
	
	public Paragraph(String para, String font, Vector pos, int size) {
		this.paragraph = para;
		this.font = font+" "+size;
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
		return font;
	}
	public String getPosition() {
		return position;
	}


}
