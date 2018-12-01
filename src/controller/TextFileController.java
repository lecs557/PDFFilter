package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

import model.Artikel;
import model.Main;
import model.Session;

public class TextFileController {
	private Session session = Main.getSession();
	private Artikel today;
	private FileOutputStream fos;
	private Writer bw; 
	private int counter = 0;
	private boolean text;
	private int j;
	
	
	//PUBLIC
	public void writeDailytxt() throws IOException {
		today = session.getPdfController().getArtikel();
		
	}
	
	//PRIVATE
	}
