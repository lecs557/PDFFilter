package model;

import javax.swing.JFrame;

import com.itextpdf.text.pdf.PdfReader;

import controller.PDFController;
import controller.StructureController;

/**
 * Class is created by 'Main', contains the 
 * stage and starts the Controller used in
 * this session
 * @author Marcel
 */
public class Session {
	
	private JFrame currentFrame; 

	private PDFController pdfController;
	private StructureController structureController;

	private PdfReader pdfReader;
	private int start;
	private int end;
	
	
	// PUBLIC
	
	public Session (JFrame frame) {
		currentFrame = frame;
		structureController = new StructureController();
		pdfController = new PDFController();
	}
	
	public void setVariables(int s, int e) {
		start = s;
		end = e;
	}
	
	
	public PdfReader getPdfReader() {
		return pdfReader;
	}
	public void setPdfReader(PdfReader pdfReader) {
		this.pdfReader = pdfReader;
	}
	public PDFController getPdfController() {
		return pdfController;
	}
	public void setPdfController(PDFController pdfController) {
		this.pdfController = pdfController;
	}
	public StructureController getStructureController() {
		return structureController;
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
}
