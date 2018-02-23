package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Paragraph.detail;
import model.Paragraph.style;
import model.TextOfToday;
import model.FontFilter;
import model.Main;
import model.Paragraph;
import model.Session;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

/**
 * A handler which undertakes tasks which have to do the
 * selected PDF-File
 * @author Marcel
 */
public class PDFController {	
	private Session session = Main.getSession();
	private PdfReader reader = session.getPdfReader();	
	private TextOfToday textOfToday;
	private Paragraph currentParagraph;
	private int xVers;
	private style oldStyle = style.Normal;
	private int oldSize = 0;
	private int oldY = 500;
	private int oldX = 0;
	private boolean isDate;
	
	
	//PUBLIC
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		
		isDate=false;
		xVers=0;
		oldY = 500;
		textOfToday = new TextOfToday(page);
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
	}

	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("- ", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector startBase = rinfo.getBaseline().getStartPoint();
		Vector startAscent = rinfo.getAscentLine().getStartPoint();
		int y = (int) startBase.get(1);
		int x = (int) startBase.get(0);
		int size = (int) (startAscent.get(1)-startBase.get(1));
		style style = createSryle(font);
		
		if ( !word.equals("") ) { 
			if(session.isHasDate() && (isDate || isYBigger(y))){
				if(newLine(y) && !textOfToday.getDatum().equals(""))
					textOfToday.setDatum(" ");
				textOfToday.setDatum(word);
			} else if (changedStyleOrSize(style, size)){
				if(xVers==0)
					xVers=x;
				startParagraph(word, style, startBase, size);
				chooseDetail(style, x);
			} else if(!belongsToCurrentParagraph(startBase, size)) {
				startParagraph(word, style, startBase, size);
				chooseDetail(style, x);
			} else if (belongsToCurrentParagraph(startBase, size)){
				currentParagraph.add(word);
			}
			if(newLine(y)) {		
				oldX = x;
			}
			oldStyle=style;
			oldY = y;		
			oldSize = size;			
		}
	}
	
	//PRIVATE
	private style createSryle (String temp){
		style style = model.Paragraph.style.Normal;
		for(style f:style.values()){
			if (temp.contains(f.name()))
				style = f;
		}	
		return style;
	}
	
	 
	private boolean isYBigger(int y){
		boolean is = oldY < y;
		if(is)
			isDate = true;
		return is;
	}
	
	private boolean newLine(int y){
		return y != oldY;
	}
	
	private boolean changedStyleOrSize(style style, int size){
		return style != oldStyle || size != oldSize;
	}
	
	private void startParagraph(String word, style style, Vector start, int size){
		currentParagraph = new Paragraph(word, style, start, size);
		textOfToday.getContent().add(currentParagraph);
	}
	
	private void chooseDetail(style style, int x){
		switch(style.ordinal()){
		case (0) :
			if(x==xVers)
				currentParagraph.setDetail(detail.Vers); 
			else
				currentParagraph.setDetail(detail.Heading);
			break;
		case(1):
			currentParagraph.setDetail(detail.Passage); 		
		case(2):
			if(x>120)
				currentParagraph.setDetail(detail.Passage); 
			else
				currentParagraph.setDetail(detail.Paragraph);
			break;
		}	
	}
	
	private boolean belongsToCurrentParagraph(Vector start, int size) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean btcp = y - oldY == 0 || range(-5,oldX -x,90) && oldX < 120 && oldY - y < size*2.2f;
		return btcp;
	}
	
	private boolean range (int min, int x, int max){
		return min <= x && x<= max;
	}
	
	//GETTER & SETTER
	public TextOfToday getTextOfToday() {
		return textOfToday;
	}
}
