package model;

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
	
	public Session() {}

	public void initialize(Stage stage){
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


	

}
