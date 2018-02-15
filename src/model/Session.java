package model;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.itextpdf.text.pdf.PdfReader;

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

/**
 * Class is created by 'Main', contains the 
 * stage and starts the Controller used in
 * this session
 * @author Marcel
 */
public class Session {

	public enum window {MainWindow,EvaluationWindow,AnalizeWindow,OptionsWindow};
	private Stage[] stages = new Stage[4];
	private AnalizeController analizeController;
	private PDFController pdfController;
	private TextFileController textFileController;
	private int start;
	private int end;
	private String destination;
	private PdfReader pdfReader;
	private ArrayList<Integer> yIgnore = new ArrayList<Integer>();	
	private ArrayList<String> fontIgnore = new ArrayList<String>();	
	public Session() {}
	
	public void initialize(Stage stage){
		this.stages[0] = stage;
	}
	
	public void openWindow(window window) throws IOException {
		if (stages[window.ordinal()] == null){
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/"+window.name()+".xml")); 
			Scene scene = new Scene(root);
			stage.setTitle("PDF Filter");
			stage.setScene(scene);
			if (window.ordinal()==2)
				stage.setX(280);
			if (window.ordinal()==1)
				stage.setX(820);
			stage.show();
			stages[window.ordinal()]=stage;
		} else
			stages[window.ordinal()].show();
	}
	
	public void closeWindow(window window) {
		stages[window.ordinal()].hide();
	}
	
	public void refreshStages() {
		this.stages[1] = null;
		this.stages[2] = null;
		this.stages[3] = null;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public TextFileController getTextFileController() {
		return textFileController;
	}
	
	public Stage getStage(window window){
		return stages[window.ordinal()];
	}

	public PDFController getPdfController() {
		return pdfController;
	}

	public void setPdfController(PDFController pdfController) {
		this.pdfController = pdfController;
	}

	public PdfReader getPdfReader() {
		return pdfReader;
	}

	public void setPdfReader(PdfReader pdfReader) {
		this.pdfReader = pdfReader;
	}

	public void setAnalizeController(AnalizeController analizeController) {
		this.analizeController = analizeController;
	}

	public void setTextFileController(TextFileController textFileController) {
		this.textFileController = textFileController;
	}

	public AnalizeController getAnalizeController() {
		return analizeController;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ArrayList<Integer> getyIgnore() {
		return yIgnore;
	}

	public void setyIgnore(ArrayList<Integer> yIgnore) {
		this.yIgnore = yIgnore;
	}

	public ArrayList<String> getFontIgnore() {
		return fontIgnore;
	}

	public void setFontIgnore(ArrayList<String> fontIgnore) {
		this.fontIgnore = fontIgnore;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
