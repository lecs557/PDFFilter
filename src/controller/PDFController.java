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
	private boolean heading;
	private boolean first;
	private int num;
	
	
	//PUBLIC
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		initializeForNewDay(page);
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
	}

	public void createText(String word, TextRenderInfo rinfo) {
		String font = rinfo.getFont().getPostscriptFontName();
		Vector startBase = rinfo.getBaseline().getStartPoint();
		int y = (int) startBase.get(1);
		int x = (int) startBase.get(0);
		int ascentY = (int)rinfo.getAscentLine().getStartPoint().get(1);
		int size = ascentY - y;
		style style = createSryle(font);
		
		if ( !word.equals("") && range(10,x,289) && y!=471 &&y!=21) { 
			
			if(session.isHasDate() && (isDate || isYBigger(y)) && dateCondition(y)){
				if(newLine(y) && !textOfToday.getDatum().equals("") && !word.startsWith(" "))
					textOfToday.setDatum(" ");
				textOfToday.setDatum(word);
			} 
			else if(!belongsToCurrentParagraph(startBase, size)) {
				if(xVers==0)
					xVers=x;
				chooseDetailAndStartParagraph(word, style, startBase, size, false);
			} 
			else if (belongsToCurrentParagraph(startBase, size)){
				if (changedStyleOrSize(style, size))
					chooseDetailAndStartParagraph(word, style, startBase, size, true);
				else
					currentParagraph.add(" "+word);
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
	private void initializeForNewDay(int page) {
		num=-1;
		isDate=false;
		heading=false;
		first=true;
		xVers=0;
		oldY = 500;
		textOfToday = new TextOfToday(page);
	}
	
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
	
	private Boolean dateCondition(int y){
		for (Vector v:session.getPosDate()){
			if (range(-100,y-(int)v.get(1),100))
				return true;
		}
		return false;
	}
	
	private boolean newLine(int y){
		return y != oldY;
	}
	
	private boolean changedStyleOrSize(style style, int size){
		return style != oldStyle || !range(-2,oldSize-size,2);
	}
	
	private void chooseDetailAndStartParagraph(String word, style style, Vector start, int size, boolean para){
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		if (para && y<350){
			startParagraph(first?num+1:num, word, style, start, size, detail.Paragraph);
			first=false;
		}
		else {
			switch(style.ordinal()){
			case 2 :
				if(x==xVers){
					startParagraph(num+1, word, style, start, size, detail.Vers); 
					heading=false;
				}else if(!heading) {
					startParagraph(num+1, word, style, start, size, detail.Heading); 
					heading=true;
				} else
					currentParagraph.add(word);
				break;
			case 0: case 1:
				if(currentParagraph.getOrdDetail()==0)
					startParagraph(num+1, word, style, start, size, detail.Passage); 
				else {
					startParagraph(num+1, word, style, start, size, detail.Paragraph);
					first=false;				
				}
				break;
			}
		}
	}
	
	private void startParagraph(int paraNum, String word, style style, Vector start, int size, detail detail){
		if(currentParagraph!=null && currentParagraph.getParagraph().equals(" "))
			textOfToday.getContent().remove(currentParagraph);
		
		currentParagraph = new Paragraph(paraNum ,word, style, start, size, detail);
		textOfToday.getContent().add(currentParagraph);
		num=paraNum;
	}
	
	private boolean belongsToCurrentParagraph(Vector start, int size) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean btcp = y - oldY == 0 || range(-5,oldX -x,90) && range(-size*2,oldY - y,size*2);
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
