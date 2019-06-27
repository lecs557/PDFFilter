package controller;

import java.util.ArrayList;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

import model.Abschnitt;
import model.Start;
import model.Word;

public class StructureController {
	
	private Word currentWord = new Word("START");
	private ArrayList<Abschnitt> artikel = new ArrayList<Abschnitt>();
	private Abschnitt curAbschnitt;
	private boolean newWord = false;
	private int oldY;
	private int oldX;
	private Word foot; 

	public void makeLetterstoWords(String letter,TextRenderInfo tri) {
		String text = letter;
		int x = (int)tri.getBaseline().getStartPoint().get(0);
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		int size = (int)tri.getAscentLine().getStartPoint().get(1)-y;
		
		if (y < 570 && y > 52) {
		if (size<7) {
			if(foot==null)
				foot = new Word(tri);
			else
				foot.addLetter(tri.getText());
						
		} else {
				
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
					currentWord.addLetter(text,tri);
					newWord = false;
				} else { // incorrect
					makeWordsToAbschnitt();
					currentWord = new Word(text,tri);
					newWord = false;
				}
			}
		}
		}}
		oldY = y;
		oldX = x;
	}
	
	public void makeWordsToAbschnitt() {
		
		if (!artikel.isEmpty())
			curAbschnitt=artikel.get(artikel.size()-1);
		if (artikel.isEmpty()) {
			
			pushAbschnitt(new Abschnitt(currentWord));
			
		}else if(currentWord.isHeading()) {
			
			if (curAbschnitt.isHeading()) {
				curAbschnitt.addWord(currentWord);
			} else {
				pushAbschnitt(new Abschnitt(currentWord));
			}
			
		} else if(curAbschnitt.isHeading() && !currentWord.isHeading()) {
			
			pushAbschnitt(new Abschnitt(currentWord));
			
		}else if (curAbschnitt.getLastY() - currentWord.getY() < 15 &&  curAbschnitt.getLastY() - currentWord.getY() > -1)
			
			curAbschnitt.addWord(currentWord);
		
		else if (curAbschnitt.getLastY() - currentWord.getY() > 15 || curAbschnitt.getLastY() - currentWord.getY() < -1 ) {
			
			pushAbschnitt(new Abschnitt(currentWord));
		}
			
	}
	
	public Abschnitt getCurAbschnitt() {
		return curAbschnitt;
	}
	
	public ArrayList<Abschnitt> getArtikel() {
		artikel.add(new Abschnitt(foot));
		return artikel;
	}

	public void pushAbschnitt(Abschnitt absch) {
		if (absch.getFirstY() < 570 && absch.getLastY() > 52) {
			artikel.add(absch);
		}
		
	}
}
