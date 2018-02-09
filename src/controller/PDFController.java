package controller;

import java.io.IOException;
import java.util.ArrayList;

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
	
	private String oldFont = "";
	private int oldSize = 0;
	private int oldY = 500;
	private int oldX = 0;
	
	private Paragraph currentParagraph;
	
	/**
	 * Scans every word of the PDF-File, invokes <<FontFilter>> to get 
	 * the render info which are given to <createText>
	 * Can be invoked only for one page a time 
	 * @param path @param page of the PDF-File
	 */
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		oldY = 500;
		textOfToday = new TextOfToday();
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
	}

	/**
	 * Tries to find out the different segments
	 * and puts the into the daily-Object. 
	 * @param rinfo
	 */
	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("-", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector startBase = rinfo.getBaseline().getStartPoint();
		Vector startAscent = rinfo.getAscentLine().getStartPoint();
		int y = (int) startBase.get(1);
		int x = (int) startBase.get(0);
		int size = (int) (startAscent.get(1)-startBase.get(1));
		
		if (!word.equals("") && y!=43 ) { //y=43 : Seitenzahl
			
			if (!sameFont(font, size) && newLine(y)){
				System.out.println("1 Change" );
				
				if(font.contains("Bold")){
					startParagraph(word, font, startBase, size);							 				
				}else{
					startParagraph(word, font, startBase, size);					
				}
			}
//			else if(!sameFont(font, size) && !newLine(y)){
//				startParagraph(word, font, startBase, size);
//			}
			else if (!belongsToCurrentParagraph(startBase, size)) {
				startParagraph(word, font, startBase, size);
				System.out.println("----------2 Change" );
			} else{
				currentParagraph.add(word);
			}
		if(newLine(y)) {		
			oldX = x;
		}
		oldFont=font;
		oldY = y;		
		oldSize = size;
		}
	}
	

	private boolean belongsToCurrentParagraph(Vector start, int size) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean btcp = y - oldY == 0 ||  -5 < oldX -x &&  oldX - x < 18 && oldY - y < size*2;
		return btcp;
	}
	
	private boolean sameFont(String font, int size){
		return font.equals(oldFont) && size == oldSize;
	}

	private boolean newLine(int y){
		return y != oldY;
	}

	private void startParagraph(String word, String font, Vector start, int size){
		currentParagraph = new Paragraph(word, font, start, size);
		textOfToday.getDay().add(currentParagraph);
		
	}
	public TextOfToday getTextOfToday() {
		return textOfToday;
	}
}
