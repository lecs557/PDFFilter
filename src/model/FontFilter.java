package model;

import model.Artikel.style;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
	
	private Session ses = Start.getSession();
	
	public FontFilter(){ }
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		ses.getStructureController().makeLetterstoWords(tri.getText(),tri);
		return true;
	}
}