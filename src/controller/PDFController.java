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
 * @author Marcel
 */
public class PDFController {

	private ArrayList<DailyText> daily;
	private String analizeFont ="";
	private String analizeX ="";
	private String segment = "";
	private boolean isDate = false;
	private int oldy = 500;
	private int day = 0;

	public PDFController() {
		this.daily = Main.getSession().getDaily();
	}

	/**
	 * Scans PDF, invokes FontFilter, which executes createText having render
	 * information.
	 * @param path @param page of the PDF-File
	 */
	public void readPDF(String path, int page) throws IOException {
		PdfReader reader = new PdfReader(path);
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		daily.add(new DailyText());
		oldy = 500;
		segment="";
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		analizeFont += "------------" + day + "----\n";
		analizeX += "------------" + day + "----\n";
		daily.get(day).getDay().add(segment);
		createDatum();
		isDate=false;
		day++;
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
					daily.get(day).getDay().add(segment);
					isDate = true;
					segment=word;
					analizeFont += font +" "+ (int) start.get(1)+"\n";
				}
				segment += word;
			} else if (isParagraph(start)) {
				daily.get(day).getDay().add(segment);
				analizeX +=(int) start.get(0) + "  " + (int) start.get(1)+ "\n";
				segment = word;
				if (font.contains("Bold") && daily.get(day).getDay().size()>1){
					System.out.println(font+" "+ daily.get(day).getDay().size());
					daily.get(day).setHasTitle(true);
				}
			} else
				segment += word;

		}

	}

	private boolean isParagraph(Vector start) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean newPara = x != 104 && x != 31 && x !=25 && y!= oldy;
		oldy = (int) start.get(1);
		return newPara;
	}

	private boolean isYbigger(Vector start) {
		boolean bigger = (int) start.get(1) > oldy;
		return bigger;
	}

	private void createDatum() {
		int length = daily.get(day).getDay().size();
		ArrayList<String> current = daily.get(day).getDay();
		daily.get(day).setDatum(current.get(length - 1));
	}
	

	public String getAnalizeFont() {
		return analizeFont;
	}

	public String getAnalizeX() {
		return analizeX;
	}
}
