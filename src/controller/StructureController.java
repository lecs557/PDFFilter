package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

import model.Abschnitt;
import model.Word;

public class StructureController {
	
	private Word currentWord = new Word("START");
	private Abschnitt curAbschnitt;
	private boolean newWord = false;
	private int oldY;
	private int oldX;
	

	public void makeLetterstoWords(String letter,TextRenderInfo tri) {
		String text = letter;
		int x = (int)tri.getBaseline().getStartPoint().get(0);
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		
		
		if (text.contains(" ") && !text.endsWith(" ")) {
			String old = text.split(" ",2)[0];
			String nw = text.split(" ",2)[1];
			makeLetterstoWords(old+" ", tri);
			makeLetterstoWords(nw, tri);
		}
					
		if (y != oldY)
		{ // New Line
			if(currentWord.check()) { // still old Word 
				currentWord.addLetter(text);		
			} else {
				makeWordsToAbschnitt();
				currentWord = new Word(text,tri);
			}
			newWord = false;
		}
		
		else if( y == oldY) 
		{ // old Line
			if (newWord) {
				makeWordsToAbschnitt();
				currentWord = new Word(text,tri);
				if(text.endsWith(" "))
					newWord=true;
				else
					newWord = false;
			}
			else if (text.endsWith(" ")) {
				currentWord.addLetter(text);
				newWord = true;
			
			}else if(!text.contains(" ")) {
				if (oldX <= x) { // correct
					currentWord.addLetter(text);
					newWord = false;
				} else { // incorrect
					makeWordsToAbschnitt();
					currentWord = new Word(text,tri);
					newWord = false;
				}
			}
		}
		oldY = y;
		oldX = x;
	}
	
	public Abschnitt getCurAbschnitt() {
		return curAbschnitt;
	}

	public void makeWordsToAbschnitt() {
		if (curAbschnitt == null) {
			curAbschnitt = new Abschnitt(currentWord);
		} else if (curAbschnitt.getLastY() - currentWord.getY() < 15)
			curAbschnitt.addWord(currentWord);
		else if (curAbschnitt.getLastY() - currentWord.getY() > 15) {
			curAbschnitt.say();
			curAbschnitt = new Abschnitt(currentWord);
		}
			
	}
}
