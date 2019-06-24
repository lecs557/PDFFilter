package model;

import model.Artikel.style;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
	
	private Session ses = Start.getSession();
	
	private boolean newWord = false;	
	private int oldY=600;
	private Word currentWord = ses.getPdfController().getCurWord();
	
	public FontFilter(){ }
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		String text = tri.getText();
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		System.out.println(text);
		
		if (newWord) {
			ses.getPdfController().makeStructure(currentWord);
			currentWord = new Word(tri);
			newWord = false;
		}
		else if (y != oldY) { // New Line
			if(currentWord.check()) {
				currentWord.addLetter(text);		
			} else {
				ses.getPdfController().makeStructure(currentWord);
				currentWord = new Word(tri);
			}
			newWord = false;
		}
		else if (text.endsWith(" ")) {
				currentWord.addLetter(text);
				newWord = true;
			
		}
		else if (text.contains(" ")) {
			String old = text.split(" ")[0];
			String nw = text.split(" ")[1];
			currentWord.addLetter(old);
			ses.getPdfController().makeStructure(currentWord);
			currentWord = new Word(tri,nw);
			newWord = false;
		}
		else {
			currentWord.addLetter(text);
			newWord = false;
		}
		oldY = y;
		ses.getPdfController().setCurWord(currentWord);
		return true;
	}
}