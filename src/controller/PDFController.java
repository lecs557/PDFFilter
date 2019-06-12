package controller;
import java.io.IOException;
import java.util.ArrayList;

import model.Abschnitt;
import model.Artikel;
import model.Artikel.style;
import model.FontFilter;
import model.Main;
import model.Session;

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
	private Abschnitt abschnitt;
	private Artikel artikel = new Artikel();
	private int oldY = 800;
	
	
	
	//PUBLIC
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		oldY=800;
		
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here,
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
	
	}

	public void createText(String word, TextRenderInfo rinfo, style style) {
		Vector startBase = rinfo.getBaseline().getStartPoint();
		int y = (int) startBase.get(1);
		int x = (int) startBase.get(0);
		System.out.println(word+" "+x+" "+y+" "+style.name()+"-->") ;
		
		if(60<y && y<570){
			
			if (oldY - y < 14){
				abschnitt.addWord(format(word+" ", style));		
			} else {
				abschnitt = new Abschnitt();	
				artikel.expand(abschnitt);
				abschnitt.addWord(format(word+" ", style));
			}
			oldY = y;
		}
	}
	
	private String format(String word, style style){
		switch (style){
		case Normal: return word;
		case BoldItalic: return "<em><strong>"+word+"</em></strong>";
		case Bold: return "<strong>"+word+"</strong>";
		case Italic: return "<em>"+word+"</em>";
		case Hochgestellt: return "<sup>"+word+"</sup>";
		}
		return word;
	}

	public Artikel getArtikel() {
		return artikel;
	}
}

