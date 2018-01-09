package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import model.Main;
import model.Session;

public class TextFileController {

	private Session sess = Main.getSession();
	private int j = 0;
	private int errorCounter = 0;
	private ArrayList<Integer> errors = new ArrayList<Integer>();
	/**
	 * writes the daily-objects in different files and
	 * their segments are divided with two paragraphs
	 * @throws IOException
	 */
	public void writeDailytxt() throws IOException {
		try{
		int length = sess.getToday().getDay().size();
			FileOutputStream bw = new FileOutputStream("C:\\Users\\User\\Desktop\\Russisch\\"+ j+" " +sess.getToday().getDatum() + ".txt");
			Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
					StandardCharsets.UTF_8));
			for (int i = 0; i < length-1; i++) {
				if (i==0){
					fw.append("VERS    "+sess.getToday().getDay().get(i) + " VERS\r\n\r\n");
				}else if (i==1){
					fw.append("Stelle    "+sess.getToday().getDay().get(i) + " Stelle\r\n\r\n");
				} else if(i==2 && sess.getToday().isHasTitle()){
					fw.append("Title    "+sess.getToday().getDay().get(i) + " Title\r\n\r\nTEXT\r\n");
				}else if(i==2){
					fw.append("TEXT\r\nPARAGRAPH    "+sess.getToday().getDay().get(i) + "\r\n");
				}else if(i==length-2){
					fw.append("PARAGRAPH    "+sess.getToday().getDay().get(i) + "\r\nTEXT");
				}else
					fw.append("PARAGRAPH    "+sess.getToday().getDay().get(i) + "\r\n");
			}
			j++;
			fw.close();
			
		} catch(Exception e){
				errorCounter++;
				errors.add(j);
		}
		
	
	}

	public int getErrorCounter() {
		return errorCounter;
	}

}
