package controller;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.sun.org.apache.bcel.internal.generic.AALOAD;

import model.Abschnitt;
import model.Artikel;
import model.FontFilter;
import model.Session;
import model.Start;
import model.Word;


public class PDFController {	
	
	private Session ses = Start.getSession();
	private PdfReader reader;	
	private Word curWord = new Word("STARTSTART");

	
	//PUBLIC
	public void readPDF(int page) throws IOException {
		reader = Start.getSession().getPdfReader();	
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here,
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		Start.getSession().getStructureController().getCurAbschnitt().say();
	
	}
	


	public Word getCurWord() {
		return curWord;
	}



	public void setCurWord(Word curWord) {
		this.curWord = curWord;
	}

}

