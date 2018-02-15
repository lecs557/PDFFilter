package controller;

import java.io.BufferedWriter;
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
	private TextOfToday today;
	private int counter = 0;
	private FileOutputStream bw;
	private Writer fw; 
	
	/**
	 * writes the daily-objects in different files and
	 * their segments are divided with two paragraphs
	 * @throws IOException
	 */
	public void writeDailytxt() throws IOException {
		today = Main.getSession().getPdfController().getTextOfToday();
		
		if(fw==null){
			bw = new FileOutputStream(Main.getSession().getDestination()+"\\productTitle.txt");
			fw = new BufferedWriter(new OutputStreamWriter(bw,				
					StandardCharsets.UTF_8));
		}
		int length = today.getContent().size();
		for (int i = 0; i < length; i++) {
			write(i);
			if(i==length-1)
				fw.append("ENDEDESKAPTELS\r\n\r\n");
		}					
		if(counter== (Main.getSession().getEnd() - Main.getSession().getStart()) ){
			fw.close();
		}
		counter++;
	}
	
	private void write(int i) throws IOException{
		Paragraph paragraph = today.getContent().get(i);
		switch (paragraph.getOrdDetail()){
		case 0:
			fw.append("[{product_id:2,number:"+counter+",title:"+paragraph.getParagraph()+",text:\r\n");
			break;
		case 1:
			fw.append("<h1>"+paragraph.getParagraph()+"</h1>\r\n");
			break;
		case 2:
			fw.append("<b>"+paragraph.getParagraph()+"</b>");
			break;
		case 3:
			fw.append("<p style=\"margin-left:0cm; margin-right:0cm\">"+paragraph.getParagraph()+"</p>\r\n");
			break;			
		}
	}
}
