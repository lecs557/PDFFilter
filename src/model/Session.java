package model;

import java.util.ArrayList;

import javafx.stage.Stage;
import controller.PDFController;
import controller.TextFileController;

/**
 * Class is created by 'Main', contains the 
 * stage and starts the Controller used in
 * this session
 * @author Marcel
 */
public class Session {

	private PDFController pdfController;
	private TextFileController textFileController;
	private Stage currentStage;
	private ArrayList<DailyText> daily;
	
	public Session() {}

	public void initialize(Stage stage){
		this.daily = new ArrayList<DailyText>();
		this.currentStage=stage;
		this.pdfController = new PDFController();
		this.textFileController = new TextFileController();
	}

	public PDFController getPDFController() {
		return pdfController;
	}

	public TextFileController getTextFileController() {
		return textFileController;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}
	
	public ArrayList<DailyText> getDaily(){
		return daily;
	}


	

}
