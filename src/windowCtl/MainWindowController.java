package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
	private Session sess;
	private TextFileController tfc;
	private PDFController pdf;
	private AnalizeController analize;
	@FXML
	private TextField tf_absolutePath;
	@FXML
	private Button okBtn;
	@FXML
	private Button analizeBtn;
	@FXML
	private ProgressBar bar;
	@FXML
	private TextField tf_startPage, tf_endPage;
	@FXML
	private TextField tf_pages;
	
	private int pages;
	private int start;
	private int i=start;
	private int end;
	

	private Thread work = new Thread(){
		public void run(){
			try {
				sess = Main.getSession();
				tfc = Main.getSession().getTextFileController();
				pdf = Main.getSession().getPDFController();
				analize = Main.getSession().getAnalizeController();
				start = Integer.parseInt(tf_startPage.getText());
				end = Integer.parseInt(tf_endPage.getText());
				okBtn.setDisable(true);
				process.start();
				String path = tf_absolutePath.getText();
				sess.setStart(start);
				for (i = start; i <= end; i++) {
					int page = i + 1;
					pdf.readPDF(path, page);
					tfc.writeDailytxt();
					analize.analize();
					
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
	private void onPressOk() throws IOException, InterruptedException {
		work.start();
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		sess.openWindow(window.EvaluationWindow);
		sess.openWindow(window.AnalizeWindow);		
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
			help();
			tf_pages.setText(""+pages);
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
			else if(Integer.parseInt(tf_endPage.getText()) > pages)
				okBtn.setDisable(true);
			else
				okBtn.setDisable(false);	
		}catch(Exception e){
			okBtn.setDisable(true);
		}
	}
	
	private void help() throws IOException{
		PdfReader reader = new PdfReader(tf_absolutePath.getText());
		pages = reader.getNumberOfPages();
		
		
		
	}
}
