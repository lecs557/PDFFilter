package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

import model.Abschnitt;
import model.Word;

public class StructureController {

	public void makeLetterstoWords(TextRenderInfo tri) {
		
	}
	
	public void makeWordsToAbschnitt(Word word) {
		if (curAbschnitt == null) {
			curAbschnitt = new Abschnitt(word);
		} else 
			curAbschnitt.addWord(word);
			
	}
}
