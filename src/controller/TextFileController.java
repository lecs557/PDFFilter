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
	
	
	//PUBLIC
	public void writeDailytxt() throws IOException {
		today = session.getPdfController().getTextOfToday();
		if(fos==null)
			createFOSBW();
		
		int length = today.getContent().size();
		if(correctDate(today.getDatum())){
			today.setInvalid(false);
			for (int i = 0; i < length; i++) 
				write(i, length);				
		}else {
			today.setInvalid(true);
			session.getInvalids().add("INVALID SEITE" + today.getPage());
			System.out.println("INVALID "+today.getDatum());
		}
		
		System.out.println("VALID "+today.getDatum());
		if(counter == (session.getEnd() - session.getStart()) )
			bw.close();
		
		counter++;
	}
	
	//PRIVATE
	private void createFOSBW() throws FileNotFoundException{
		fos = new FileOutputStream(session.getDestination()
				+"\\productTitle.txt");
		bw = new BufferedWriter(new OutputStreamWriter(fos,				
				StandardCharsets.UTF_8));
	}
	
	private void write(int i, int length) throws IOException{
		Paragraph paragraph = today.getContent().get(i);
		chooseText(paragraph);
		if(i==length-1)
			bw.append("ENDEDESKAPTELS\r\n\r\n");
	
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
			bw.append("[{product_id:2,number:"+counter+","
					+ "title:"+paragraph.getParagraph()+",text:\r\n");
			break;
		case 1:
			bw.append("<h1>"+paragraph.getParagraph()+"</h1>\r\n");
			break;
		case 2:
			bw.append("<b>"+paragraph.getParagraph()+"</b>");
			break;
		case 3:
			bw.append("<p style=\"margin-left:0cm; margin-right:0cm\">"
					+paragraph.getParagraph()+"</p>\r\n");
			break;			
		}
	}
}
