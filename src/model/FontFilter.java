package model;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
	
	private String word ="";
	private TextRenderInfo trinf;
	private boolean newWord = true;	
	private int oldY=0;
	
	public FontFilter(){ }
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		String text = tri.getText();
		System.out.print(text+"|");
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		if(y!=oldY)
			startNewLine(text);
		else if(text.endsWith(" "))
			sendAllWords(word+text);
		else if(text.contains(" "))	
			sendWords(word+text);
		 else {
			word+=text;
		}
		if(newWord)
			trinf = tri;
		newWord=false;
		oldY=y;
		return true;
	}
 
	private void sendWords(String text){
		int size=text.split(" ").length;
		for (int i=0;i<size-1;i++){
			Main.getSession().getPdfController().createText(text.split(" ")[i], trinf);
			System.out.print(i+"GES ");
		}
		word=text.split(" ")[size-1];
	}
	
	private void sendAllWords(String text){
		int size=text.split(" ").length;
		if(trinf!=null){
			for (int i=0;i<size;i++){
				Main.getSession().getPdfController().createText(text.split(" ")[i], trinf);
			}
			word="";
			newWord = true;
			System.out.println("ALLES GESENDET\n");
		}	
	}
	
	private void startNewLine(String text){
		sendAllWords(word);
		word = text;
	}
}