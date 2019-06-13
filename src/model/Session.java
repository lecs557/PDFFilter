package model;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.Vector;

import controller.PDFController;

/**
 * Class is created by 'Main', contains the 
 * stage and starts the Controller used in
 * this session
 * @author Marcel
 */
public class Session {
	
	private JFrame currentFrame; 
	
	
	private Stage[] stages = new Stage[4];
	public enum window {MainWindow,EvaluationWindow,AnalizeWindow,OptionsWindow};
	private PDFController pdfController;
	private PdfReader pdfReader;
	private ArrayList<Vector> posDate = new ArrayList<Vector>();	
	private ArrayList<String> invalids = new ArrayList<String>();	
	private String destination;
	private int start;
	private int end;
	private boolean hasDate;
	
	
	// PUBLIC
	
	public Session (JFrame frame) {
		currentFrame = frame;
	}
	
	
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
	
	public void refreshStages() {
		this.stages[1] = null;
		this.stages[2] = null;
		this.stages[3] = null;
	}
	
	public void closeWindow(window window) {
		stages[window.ordinal()].hide();
	}
	
	// GETTERS & SETTERS
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
	public ArrayList<Vector> getPosDate() {
		return posDate;
	}
	public ArrayList<String> getInvalids() {
		return invalids;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}	
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public boolean isHasDate() {
		return hasDate;
	}
	public void setHasDate(boolean hasDate) {
		this.hasDate = hasDate;
	}
}
