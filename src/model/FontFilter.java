package model;



import model.Artikel.style;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
	
	private String word ="";
	private TextRenderInfo trinf;
	private style curStyle;
	private boolean newWord = false;	
	private int oldY=600;
	
	public FontFilter(){ }
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		String text = tri.getText();
		int y = (int)tri.getBaseline().getStartPoint().get(1);
	
		if (newWord) {
			trinf = tri;
			curStyle = extractStyleFrotTri(trinf);
		}
		if (y != oldY)
			startNewLine(text, tri);
		else if (text.endsWith(" "))
			sendAllWords(word + text);
		else if (text.contains(" "))
			sendWords(word + text);
		else {
			word += text;
			newWord = false;
		}
		oldY = y;
		return true;
	}
 
	private void sendWords(String text){
		int size=text.split(" ").length;
		for (int i=0;i<size-1;i++){
			Main.getSession().getPdfController().createText(text.split(" ")[i], trinf, curStyle);
		}
		word=text.split(" ")[size-1];
		newWord = false;
	}
	
	private void sendAllWords(String text){
		int size=text.split(" ").length;
		if(trinf!=null){
			for (int i=0;i<size;i++){
				Main.getSession().getPdfController().createText(text.split(" ")[i], trinf, curStyle);
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
			return style.Hochgestzellt;
		else if (tri.getFont().getPostscriptFontName().contains("Regular")){
			return style.Normal;
		} else if(tri.getFont().getPostscriptFontName().contains("Bold") && tri.getFont().getPostscriptFontName().contains("It")){
			return style.BoldItalic;
		}else if (tri.getFont().getPostscriptFontName().contains("It")){
			return style.Italic;
		} else if (tri.getFont().getPostscriptFontName().contains("Bold")){
			return style.Bold;
		}
		return style.Hochgestzellt;
	}
}