package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import model.DailyText;
import model.Main;

public class TextFileController {

	ArrayList<DailyText> dailytxt;
	private int j = 0;
	
	/**
	 * writes the daily-objects in different files and
	 * their segments are divided with two paragraphs
	 * @throws IOException
	 */
	public void writeDailytxt() throws IOException {
		dailytxt = Main.getSession().getDaily();
		for (DailyText day : dailytxt) {
			int length = day.getDay().size();
			FileOutputStream bw = new FileOutputStream("C:\\Users\\User\\Desktop\\Russisch\\"+ j +day.getDatum() + ".txt");
			Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
					StandardCharsets.UTF_8));
			for (int i = 0; i < length-1; i++) {
				if (i==0){
					fw.append(i+"VERS    "+day.getDay().get(i) + " VERS\r\n\r\n");
				}else if (i==1){
					fw.append(i+"Stelle    "+day.getDay().get(i) + " Stelle\r\n\r\n");
				} else if(i==2 && day.isHasTitle()){
					fw.append(i+"Title    "+day.getDay().get(i) + " Title\r\n\r\nTEXT\r\n");
				}else if(i==2){
					fw.append(i+"TEXT\r\n");
				}else if(i==length-2){
					fw.append(i+"TEXT");
				}else
					fw.append(i+"PARAGRAPH    "+day.getDay().get(i) + "\r\n");
			}
			j++;
			fw.close();
		}
	}

}
