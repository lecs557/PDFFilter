package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import model.DailyText;
import model.Main;

public class TextFileController {

	DailyText dailytxt;

	public void writeDailytxt() throws IOException {
		dailytxt = Main.getSession().getDaily();
		FileOutputStream bw = new FileOutputStream("ausgabe.txt");
		Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
				StandardCharsets.UTF_8));

		for(String segment : dailytxt.getDay()) {
			fw.append("\r\n"+segment);
		}
		fw.close();
	}

	public void setDailytxt(DailyText dailytxt) {
		this.dailytxt = dailytxt;
	}

}
