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
	private Word other;

	public void makeLetterstoWords(String letter,TextRenderInfo tri) {
		String text = letter;
		int x = (int)tri.getBaseline().getStartPoint().get(0);
		int y = (int)tri.getBaseline().getStartPoint().get(1);
		int size = (int)tri.getAscentLine().getStartPoint().get(1)-y;
		int color;
		if(tri.getFillColor() != null)
			color = tri.getFillColor().getGreen();
		else color=0;
		
		if (y < 570 && y > 52) {
			
			if (size<7 && oldY > y) {				
				if(other==null) {
					foot = new Word(tri);
					currentWord = foot;
				}else {
					currentWord = foot;
					currentWord.addLetter(tri.getText());
				}
			} else if (size>=7 && y > oldY && x!=66 && x!= 243 ) {			
				if(other==null) {
					other = new Word(tri);
					currentWord = other;
				}else {
					currentWord = other;
					currentWord.addLetter(tri.getText());
				}
			} else {					
						
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
		}
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
		if(other!=null)
			artikel.add(new Abschnitt(other));
		if(foot!=null)
			artikel.add(new Abschnitt(foot));
		return artikel;
	}

	public void pushAbschnitt(Abschnitt absch) {
			artikel.add(absch);		
	}
}
