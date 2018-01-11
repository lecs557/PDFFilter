package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Main;
import model.Session;
import model.Session.window;

import com.itextpdf.text.pdf.PdfReader;

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	private Session session = Main.getSession();;
	private TextFileController tfctrl;
	private PDFController pdfctrl;
	private AnalizeController analizectrl;
	
	@FXML
	private TextField tf_absolutePath, tf_pages;
	@FXML
	private Button okBtn, analizeBtn;
	@FXML
	private ProgressBar bar;
	@FXML
	private TextField tf_startPage, tf_endPage;
		
	private PdfReader reader;
	private int pages;
	private int start;
	private int i=start;
	private int end;
	
	private Thread work = new Thread(){
		public void run(){
			try {
				start = Integer.parseInt(tf_startPage.getText());
				end = Integer.parseInt(tf_endPage.getText());
				okBtn.setDisable(true);
				setVariables();
				process.start();
				for (i = start; i < end; i++) {
					int page = i;
					pdfctrl.readPDF(page);
					tfctrl.writeDailytxt();
					analizectrl.analize();
				}	
				analizeBtn.setDisable(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private Thread process = new Thread(){
		public void run(){		
			while(i<=end){		
				bar.setProgress((i-start)/(float) (end-start));
			}
		}
	};
	
	public void initialize(){
		bar.setProgress(0);
	}
	

	@FXML
	private void onPressOk() {
		work.start();
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		session.openWindow(window.EvaluationWindow);
		session.openWindow(window.AnalizeWindow);		
	}

	@FXML
	private void onPressBrowse() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession()
				.getStage(window.MainWindow));
		tf_absolutePath.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_absolutePath != null) {
			okBtn.setDisable(false);
			tf_startPage.setDisable(false);
			tf_endPage.setDisable(false);
			reader = new PdfReader(tf_absolutePath.getText());
			pages = reader.getNumberOfPages();	
			tf_pages.setText(""+pages);
		} else{
			tf_startPage.setDisable(true);
			tf_endPage.setDisable(true);
			okBtn.setDisable(true);
		}
	}
	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}	
	
	@FXML
	private void validatePages(){
		try{
			if( Integer.parseInt(tf_startPage.getText()) > Integer.parseInt(tf_endPage.getText()) )
				okBtn.setDisable(true);
			else if(Integer.parseInt(tf_endPage.getText()) > pages || Integer.parseInt(tf_startPage.getText())==0)
				okBtn.setDisable(true);
			else
				okBtn.setDisable(false);	
		}catch(Exception e){
			okBtn.setDisable(true);
		}
	}
	
	
	private void setVariables(){
		session.setStart(start);
		session.setPdfReader(reader);
		tfctrl = new TextFileController();
		session.setTextFileController(tfctrl);
		analizectrl = new AnalizeController();
		session.setAnalizeController(analizectrl);
		pdfctrl = new PDFController();
		session.setPdfController(pdfctrl);
		
	}
}
