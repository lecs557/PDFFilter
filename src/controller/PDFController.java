package controller;

import java.io.IOException;

import model.FontFilter;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
/**
 * Reads the PDF-File and puts different segments
 * of the file into separate objects
 * @author Marcel
 *
 */
public class PDFController {
	
	String paragraph= "" ;
	int oldy = 0;

	public void readPDF(String path, int page) {

		try {
			PdfReader reader = new PdfReader(path);
			RenderFilter info = new FontFilter();

			TextExtractionStrategy strategy = new FilteredTextRenderListener(
					new LocationTextExtractionStrategy(), info);

			String content = PdfTextExtractor.getTextFromPage(reader, page,
					strategy);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createText(TextRenderInfo rinfo){
		
		String word = rinfo.getText();
		String font = rinfo.getFont().getPostscriptFontName();
		Vector start = rinfo.getBaseline().getStartPoint();
		Vector end = rinfo.getBaseline().getEndPoint();
		
		if (yHasChanged(start)){
			paragraph += word+" ";
		}
		else {
			paragraph += word+" ";
		}

	}
	
	private boolean yHasChanged(Vector start){
		
		boolean changed = (int)start.get(1)!=oldy;
		oldy=(int)start.get(1);
		
		return changed;
				
	
	}

	public String getParagraph(){
		System.out.println(paragraph);
		return paragraph;
	}
}
