package controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Reads the PDF-File and puts different segments of the file into separate
 * objects
 * 
 * @author Marcel
 * 
 */
public class PDFController {

	private ArrayList<DailyText> daily;
	private String paragraph = "";
	private int oldy = 0;
	private int day=0;
	private String oldfont = "SQUOPY+Arial-BoldMT";
	
	public PDFController(){
		this.daily = Main.getSession().getDaily();
	}

	public void readPDF(String path, int page) {
		try {
			PdfReader reader = new PdfReader(path);
			RenderFilter info = new FontFilter();
			TextExtractionStrategy strategy = new FilteredTextRenderListener(
					new LocationTextExtractionStrategy(), info);
			daily.add(new DailyText());
			String content = PdfTextExtractor.getTextFromPage(reader, page,
					strategy);
			createDatum();
			day++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("-", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector start = rinfo.getBaseline().getStartPoint();
		if (!word.equals("")) {
			if (fontChanged(font)) {
				daily.get(day).getDay().add(paragraph) ;
				paragraph = word;
				isYChanged(start);
			}else if(isYChanged(start) && (int) start.get(0)!=31 && (int) start.get(0) != 25 && (int) start.get(0) != 104){
				System.out.println(start.get(0));
				daily.get(day).getDay().add(paragraph) ;
				paragraph = word;
			}else
			paragraph += word;
			
		}

	}

	private boolean isYChanged(Vector start) {
		boolean yChanged = (int) start.get(1) != oldy;
		oldy = (int) start.get(1);
		return yChanged;
	}

	private boolean fontChanged(String font) {
		boolean changed = !font.equals(oldfont);
		oldfont = font;
		return changed;
	}

	private void createDatum(){
		if ( daily.size() > 1){
			int length = daily.get(day-1).getDay().size();
			ArrayList<String> current = daily.get(day).getDay();
			ArrayList<String> before = daily.get(day-1).getDay();
			daily.get(day-1).setDatum(current.get(0)+"_"+before.get(length-2)+"_"+before.get(length-1));
			daily.get(day).setDatum("next");
		}
	}
	
}
