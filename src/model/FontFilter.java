package model;

import model.Artikel.style;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
	
	private Session ses = Start.getSession();
	
	private String word ="";
	private TextRenderInfo trinf;
	private style curStyle;
	private boolean newWord = false;	
	private int oldY=600;
	private Word currentWord;
	
	public FontFilter(){ }
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		String text = tri.getText();
		String font = tri.getFont().getPostscriptFontName();
		int size = (int)tri.getRise();
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		if (newWord) {
			currentWord = new Word(text,font,size);
		}
		else if (y != oldY) { // New Line
			currentWord = new Word(text,font,size);
		}
		else if (text.endsWith(" ")) {
			currentWord.addLetter(text);
			newWord = true;
		}
		else if (text.contains(" ")) {
			String old = text.split(" ")[0];
			String nw = text.split(" ")[1];
			currentWord.addLetter(old);
			currentWord = new Word(text,font,size);
		}
		else {
			currentWord.addLetter(text);
		}
		oldY = y;
		return true;
	}
 
	private void sendWords(String text){
		int size=text.split(" ").length;
		for (int i=0;i<size-1;i++){
			ses.getPdfController().createText(text.split(" ")[i], trinf, curStyle);
		}
		word=text.split(" ")[size-1];
		newWord = false;
	}
	
	private void sendAllWords(String text){
		int size=text.split(" ").length;
		if(trinf!=null){
			for (int i=0;i<size;i++){
				ses.getPdfController().createText(text.split(" ")[i], trinf, curStyle);
			}
			word="";
			newWord = true;
		}	
	}
	
	private void startNewLine(String text, TextRenderInfo tri){
		if (word.endsWith("-")){
			word = word.substring(0, word.length()-1)+text;
		} else {
			sendAllWords(word);
			word = text;
			trinf=tri;
			curStyle = extractStyleFrotTri(trinf);
			newWord=false;
		}
	}
	
	private style extractStyleFrotTri (TextRenderInfo tri){
		if ((int)tri.getBaseline().getStartPoint().get(1) - oldY < 30 && (int)tri.getBaseline().getStartPoint().get(1) - oldY > 0 )
			return style.Hochgestellt;
		else if (tri.getFont().getPostscriptFontName().contains("Regular")){
			return style.Normal;
		} else if(tri.getFont().getPostscriptFontName().contains("Bold") && tri.getFont().getPostscriptFontName().contains("It")){
			return style.BoldItalic;
		}else if (tri.getFont().getPostscriptFontName().contains("It")){
			return style.Italic;
		} else if (tri.getFont().getPostscriptFontName().contains("Bold")){
			return style.Bold;
		}
		return style.Hochgestellt;
	}
}