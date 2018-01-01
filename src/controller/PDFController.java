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

	private String analizeFont ="";
	private String analizeX ="";
	private String segment = "";
	private String oldfont = "SQUOPY+Arial-BoldMT";
	private ArrayList<DailyText> daily;
	private int oldy = 0;
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
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		createDatum();
		analizeFont += "------------" + day + "----\n";
		analizeX += "------------" + day + "----\n";
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
			if (fontChanged(font)) {
				daily.get(day).getDay().add(segment);
				analizeFont += font +" "+ (int) start.get(1)+"\n";
				segment = word;
				isParagraph(start);
			} else if (isParagraph(start)) {
				daily.get(day).getDay().add(segment);
				analizeX +=(int) start.get(0) + "  " + (int) start.get(1)+ "\n";
				segment = word;
			} else
				segment += word;

		}

	}

	private boolean isParagraph(Vector start) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean yChanged = x != 104 && x != 31 && x !=25 && y!= oldy;
		oldy = (int) start.get(1);
		return yChanged;
	}

	private boolean fontChanged(String font) {
		boolean changed = !font.equals(oldfont);
		oldfont = font;
		return changed;
	}

	private void createDatum() {
		if (daily.size() > 1) {
			int length = daily.get(day - 1).getDay().size();
			ArrayList<String> current = daily.get(day).getDay();
			ArrayList<String> before = daily.get(day - 1).getDay();
			daily.get(day - 1).setDatum(
					current.get(0) + "_" + before.get(length - 2) + "_"
							+ before.get(length - 1));
			daily.get(day).setDatum("next");
		}
	}

	public String getAnalizeFont() {
		return analizeFont;
	}

	public String getAnalizeX() {
		return analizeX;
	}
}
