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
	
	
	private int startPage=0;
	private int i=startPage;
	private int endPage=35;
	
	private Thread work = new Thread(){
		public void run(){
			try {
				process.start();
				okBtn.setDisable(true);
				tfc = Main.getSession().getTextFileController();
				pdf = Main.getSession().getPDFController();
				analize = Main.getSession().getAnalizeController();
				sess = Main.getSession();
				String path = tf_absolutePath.getText();
				for (i = startPage; i <= endPage; i++) {
					int page = i + 1;
					pdf.readPDF(path, page);
					tfc.writeDailytxt();
					analize.analize();
					
				}	
				analize.end();
				analizeBtn.setDisable(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private Thread process = new Thread(){
		public void run(){		
			while(i<=endPage){		
				bar.setProgress((i-startPage)/(float) (endPage-startPage));
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
	private void onPressBrowse() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession()
				.getStage(window.MainWindow));
		tf_absolutePath.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_absolutePath != null) {
			okBtn.setDisable(false);
		}
	}
	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}		
}
