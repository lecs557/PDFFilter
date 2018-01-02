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
			for (int i = 0; i < length; i++) {		
				fw.append(day.getDay().get(i) + "\r\n\r\n");
			}
			j++;
			fw.close();
		}
	}

}
