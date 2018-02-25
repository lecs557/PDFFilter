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

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {

	private Session session = Main.getSession();
	private PdfReader reader;
	private int start;
	private int i=start;
	private int end;
	@FXML
	private TextField tf_absolutePath, tf_pathDes ;
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
			setupForFile(true);
		} else{
			tf_absolutePath.setText("");
			setupForFile(false);
		}
	}
	
	@FXML
	private void onPressBrowseDes() throws IOException {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File file = directoryChooser.showDialog(session
				.getStage(window.MainWindow));
		if (tf_pathDes != null) {
			tf_pathDes.setText(file.getAbsolutePath());
		}
	}
	
	@FXML
	private void onPressPre(){
		int rn = new Random().nextInt(reader.getNumberOfPages()-2) + 1;
		rn=267;
		filter(rn,rn+1);
		analizeBtn.setDisable(false);
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		session.openWindow(window.OptionsWindow);	
		session.openWindow(window.AnalizeWindow);
		session.openWindow(window.EvaluationWindow);
	}
	
	@FXML
	private void onPressOk(){
		filter(1,reader.getNumberOfPages());
		okBtn.setDisable(true);
	}

	@FXML
	private void onPressEvaluate() throws IOException{
		session.openWindow(window.EvaluationWindow);
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
	
	private void setupForFile(boolean file){
		preBtn.setDisable(!file);
		okBtn.setDisable(!file);
		browseDesBtn.setDisable(!file);
		tf_pathDes.setText(!file ? "" : "C:\\");
		bar.setProgress(0);
	}
	
	 void filter(int s, int e){
		start=s;
		end=e;
		session.setStart(start);
		session.setEnd(end);
		startFiltering();
	}
	
	private void setVariables(){
		session.setDestination(tf_pathDes.getText());
		session.setPdfReader(reader);
		session.refreshStages();
		session.setPdfController(new PDFController());	
		session.setTextFileController(new TextFileController());
		session.setAnalizeController(new AnalizeController());
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
						session.getTextFileController().writeDailytxt();
						session.getAnalizeController().analize();
					}	
					analizeBtn.setDisable(false);
					evaluationBtn.setDisable(false);
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
