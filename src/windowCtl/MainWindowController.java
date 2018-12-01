package windowCtl;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Main;
import model.Session;
import model.Session.window;

import com.itextpdf.text.pdf.PdfReader;

import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {

	private Session session = Main.getSession();
	private PdfReader reader;
	private int start;
	private int i=start;
	private int end;
	@FXML
	private TextField tf_absolutePath, tf_pathDes, tf_start, tf_end ;
	@FXML
	private Button okBtn, analizeBtn, browseDesBtn, preBtn, evaluationBtn;
	@FXML
	private ProgressBar bar;
	
	
	// FXML
	@FXML
	private void onPressBrowse() throws IOException {
		File file = setupFileChooser().showOpenDialog(session
				.getStage(window.MainWindow));
		if (file != null) {
			reader = new PdfReader(file.getAbsolutePath());
			tf_absolutePath.setText(file.getAbsolutePath());
			preBtn.setDisable(false);
			bar.setProgress(0);
		} else{
			tf_absolutePath.setText("");
			preBtn.setDisable(true);
		}
	}
		
	@FXML
	private void onPressPre(){
		start = Integer.parseInt(tf_start.getText());
		end = Integer.parseInt(tf_end.getText());
		filter();
		analizeBtn.setDisable(false);
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		session.openWindow(window.AnalizeWindow);
	}
		
	@FXML
	private void onPressClose(){
		System.exit(0);
	}		
	
	// PRIVATE
	private FileChooser setupFileChooser(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF-Files", "*.pdf"));
		return fileChooser;
	}
		
	 void filter(){
		session.setStart(start);
		session.setEnd(end);
		startFiltering();
	}
	
	private void setVariables(){
		session.setPdfReader(reader);
		session.refreshStages();
		session.setPdfController(new PDFController());	
		session.setTextFileController(new TextFileController());
	}
	
	private void startFiltering(){
		new Thread(){
			public void run(){
				try {
					setVariables();	
					startProcessBar();
					for (i = start; i <= end; i++) {
						int page = i;
						session.getPdfController().readPDF(page);
					}	
					analizeBtn.setDisable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void startProcessBar(){
		new Thread(){
			public void run(){		
				while(i<=end){		
					bar.setProgress((i-start)/(float) (end-start));
				}
			}
		}.start();
	}
}
