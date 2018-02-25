package controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import model.Main;
import model.Paragraph;
import model.Session;
import model.TextOfToday;

public class TextFileController {
	private Session session = Main.getSession();
	private TextOfToday today;
	private FileOutputStream fos;
	private Writer bw; 
	private int counter = 0;
	private boolean text;
	private int j;
	
	
	//PUBLIC
	public void writeDailytxt() throws IOException {
		today = session.getPdfController().getTextOfToday();
		if(fos==null)
			createFOSBW();
		
		j=0;
		text=false;
		int length = today.getContent().size();
		if(correctDate(today.getDatum())){
			today.setInvalid(false);
			for (int i = 0; i < length; i++) 
				write(i, length);				
		}else {
			today.setInvalid(true);
			session.getInvalids().add("INVALID SEITE" + today.getPage() + "  " + today.getDatum());
		}
		
		if(counter == (session.getEnd() - session.getStart()) ){
			bw.append("]}");
			bw.close();
		}
		counter++;
	}
	
	//PRIVATE
	private void createFOSBW() throws IOException{
		fos = new FileOutputStream(session.getDestination()
				+"\\productTitle.txt");
		bw = new BufferedWriter(new OutputStreamWriter(fos,				
				StandardCharsets.UTF_8));
		bw.append("{\"sheets\": \r\n");
	}
	
	private void write(int i, int length) throws IOException{
		Paragraph paragraph = today.getContent().get(i);
		if(i==0)
			bw.append((counter==0?"":",")+"[{\"datum\":\""+today.getDatum()+"\"\r\n");
		
		chooseText(paragraph);
		if(i==length-1)
			bw.append(",\"version\":\"2017-12-26 23:17:17\"}\r\n\r\n");
	
	}
	
	private boolean correctDate(String date){
		if (session.getPosDate().size()!=0){
			session.setHasDate(true);
			String[] split = date.split(" ");
			try{
				Integer.parseInt(split[1]);		
			}catch (Exception e){
				return false;
			}
			return split.length==3;
		}
		return true;
	}
	
	private void chooseText (Paragraph paragraph) throws IOException{
		switch (paragraph.getOrdDetail()){
		case 0:
			bw.append(",\"vers\":\""+ paragraph.getParagraph()+"\"\r\n");
			break;
		case 1:
			bw.append(",\"stelle\":\""+ paragraph.getParagraph()+"\"\r\n");
			break;
		case 2:
			bw.append(",\"titel\":\""+ paragraph.getParagraph()+"\"\r\n");
			break;
		case 3:
			j++;
			if(!text){
				bw.append(",\"text\":\"\r\n");
				text=true;
			} 
			bw.append("<p>"	+paragraph.getParagraph()+"</p>\r\n");
			if (j==today.getAmountOfParagraphs())
				bw.append("\",\"tagesvers\":\"\"");
			break;			
		}
	}
}
