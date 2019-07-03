package controller;

import java.util.ArrayList;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

import model.Abschnitt;
import model.Start;
import model.Word;

public class StructureController {
	
	private ArrayList<Abschnitt> artikel = new ArrayList<Abschnitt>();
	private Abschnitt curAbschnitt;
	private Word currentWord = new Word("START");
	private Abschnitt footAbschnitt;
	private Abschnitt otherAbschnitt;
	
	private boolean newWord = false;
	private int oldY;
	private int oldX;

	public void makeLetterstoWords(String letter,TextRenderInfo tri) {
		String text = letter;
		int x = (int)tri.getBaseline().getStartPoint().get(0);
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		
		if (y < 570 && y > 52) {
									
			if (text.contains(" ") && !text.endsWith(" "))
			{
				String old = text.split(" ",2)[0];
				String nw = text.split(" ",2)[1];
				makeLetterstoWords(old+" ", tri);
				makeLetterstoWords(nw, tri);
			}
			if (y != oldY)
			{
				if(currentWord.check()) { // still old Word 
					currentWord.addLetter(text);	
				}else if(0>oldY - y && oldY - y > -10) {
					currentWord.addLetter(text);
				} else {
					makeWordsToAbschnitt();
					currentWord = new Word(text,tri);
				}
				newWord = false;
			}
			else if( y == oldY) 
			{ 
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
		}
		
		oldY = y;
		oldX = x;
	}
	
	public void makeWordsToAbschnitt() {
		
		if (!artikel.isEmpty()) {
		if(curAbschnitt == null)
			chooseAbschnitt();
		}
		
		if (artikel.isEmpty()) {
			pushAbschnitt(new Abschnitt(currentWord));
			curAbschnitt=artikel.get(artikel.size()-1);
			
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
	
	
	private void chooseAbschnitt() {
		
		if (!artikel.isEmpty())
			curAbschnitt=artikel.get(artikel.size()-1);
		if(currentWord.getSize()<7) {
			if(footAbschnitt == null)
				footAbschnitt = new Abschnitt(currentWord);
			curAbschnitt = footAbschnitt;
		}
		if(currentWord.getY()>curAbschnitt.getLastY()) {
			if(otherAbschnitt == null)
				otherAbschnitt = new Abschnitt(currentWord);
			curAbschnitt = otherAbschnitt;
			System.out.println(currentWord.getText());
		}
	}
	
	public ArrayList<Abschnitt> getArtikel() {
		if(otherAbschnitt!=null)
			artikel.add(otherAbschnitt);
		if(footAbschnitt!=null)
			artikel.add(footAbschnitt);
		return artikel;
	}

	public void pushAbschnitt(Abschnitt absch) {
		boolean isfoot = false;
		boolean isother = false;
		
		if(footAbschnitt != null)
			isfoot=footAbschnitt.getInfo().equals(absch.getInfo());		
		if(otherAbschnitt != null)
			isother=otherAbschnitt.getInfo().equals(absch.getInfo());		
		if(!isfoot &&!isother) {
			artikel.add(absch);		
		}
		curAbschnitt=null;
	}
}
