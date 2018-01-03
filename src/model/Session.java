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

	private PDFController pdfController;
	private TextFileController textFileController;
	private Stage currentStage;
	private ArrayList<DailyText> daily;
	private Scene scene;	
	
	public Session() {}

	public void initialize(Stage stage, Scene scene){
		this.daily = new ArrayList<DailyText>();
		this.currentStage=stage;
		this.pdfController = new PDFController();
		this.textFileController = new TextFileController();
		this.scene=scene;
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

	public Scene getScene() {
		return scene;
	}


	

}
