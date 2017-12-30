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

	public void writeDailytxt() throws IOException {
		dailytxt = Main.getSession().getDaily();

		for (DailyText day : dailytxt) {
			FileOutputStream bw = new FileOutputStream(day.toString()+".txt");
			Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
					StandardCharsets.UTF_8));
			
			int length = day.getDay().size();
			for(int i=1; i<length-2; i++){
				fw.append("\r\n\r\n"+day.getDay().get(i));
			}
			
			fw.close();
		}
	}

	

}
