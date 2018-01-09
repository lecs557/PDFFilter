package model;

import java.util.ArrayList;

import javafx.scene.Scene;
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

	private DailyText today;
	private PDFController pdfController;
	private TextFileController textFileController;
	private Stage[] stages = new Stage[3];
	
	public Session() {}

	public void initialize(Stage stage, Scene scene){
		this.stages[0] = stage;
		this.pdfController = new PDFController();
		this.textFileController = new TextFileController();
	}

	public PDFController getPDFController() {
		return pdfController;
	}

	public TextFileController getTextFileController() {
		return textFileController;
	}

	public Stage getStage(int i) {
		return stages[i];
	}
	
	public void setStage(int i, Stage stage) {
		 stages[i] = stage;
	}

	public DailyText getToday() {
		return today;
	}

	public void setToday(DailyText today) {
		this.today = today;
	}
}
