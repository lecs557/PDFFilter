package controller;

import java.io.IOException;

import model.DailyText;
import model.FontFilter;
import model.Main;

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
	private AnalizeController analize = Main.getSession().getAnalizeController();
	
	private DailyText today;
	private String analizeText ="";
	private String analizeX ="";
	private String segment = "";
	
	private boolean isDate = false;
	private int oldy = 500;
	private int day = 0;

	
	/**
	 * Scans every word of the PDF-File, invokes <<FontFilter>> to get 
	 * the render info which are given to <createText>
	 * Can be invoked only for one page a time 
	 * @param path @param page of the PDF-File
	 */
	public void readPDF(String path, int page) throws IOException {
		PdfReader reader = new PdfReader(path);
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		oldy = 500;
		segment="";
		today = new DailyText();
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		today.getDay().add(segment);
		createDatum();
		analizeText += "\n------------" + day + "----\n";
		analizeX += "\n------------" + day + "----\n";
		isDate=false;
		day++;
		analize.setAnalizeText(analizeText);
		analize.setAnalizeX(analizeX);
	}

	/**
	 * Tries to find out the different segments
	 * and puts the into the daily-Object. 
	 * @param rinfo
	 */
	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("-", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector start = rinfo.getBaseline().getStartPoint();
		if (!word.equals("")) {
			if (isYbigger(start) || isDate) {
				if(!isDate){
					today.getDay().add(segment);
					isDate = true;
					analizeX += "\n"+(int) start.get(0) + "  " + (int) start.get(1)+ " DATE";
					analizeText +="\n"+word;
					segment = word;
					yChanged(start);
				}
				else if(yChanged(start)){
					analizeX += " "+(int) start.get(0) + "  " + (int) start.get(1)+ " D ";
					segment += " "+word;
					analizeText +=" "+word;
				}else {
					analizeText +=word;
					segment +=word;
				}
			} else if (isParagraph(start)) {
				if (font.contains("Bold") && today.getDay().size()>=1 && !today.isHasTitle() ){
					today.getDay().add(segment);
					today.setHasTitle(true);
					analizeX +="\n"+(int) start.get(0) + "  " + (int) start.get(1);
					analizeText +="\n" +word;				
					segment = word;
				} else if(font.contains("Bold") && today.getDay().size()>=1 && today.isHasTitle()){
					analizeX +=(int) start.get(0) + "  " + (int) start.get(1)+ "\n";
					analizeText += word;				
					segment += word;	
				} else{
					today.getDay().add(segment);
					analizeX +="\n"+(int) start.get(0) + "  " + (int) start.get(1);
					analizeText +="\n" +word;				
					segment = word;					
				}
			} else{
				segment += word;
				analizeText +=word;
			}
		}
	}

	private boolean isParagraph(Vector start) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean newPara = x != 104 && x != 31 && x !=25 && y!= oldy;
		oldy = (int) start.get(1);
		return newPara;
	}
	
	private boolean yChanged(Vector start){
		int y = (int) start.get(1);
		boolean changed = y!= oldy;
		oldy = (int) start.get(1);
		return changed;
	}

	private boolean isYbigger(Vector start) {
		boolean bigger = (int) start.get(1) > oldy;
		return bigger;
	}

	private void createDatum() {
		int length = today.getDay().size();
		today.setDatum( today.getDay().get(length-1) );
	}
	
	public String getAnalizeFont() {
		return analizeText;
	}

	public String getAnalizeX() {
		return analizeX;
	}

	public DailyText getToday() {
		return today;
	}
}
