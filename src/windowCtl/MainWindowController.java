package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Main;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	@FXML
	private TextField tf_absolutePath;
	@FXML
	private Button okBtn;
	@FXML
	private Button analizeBtn;
	@FXML
	private ProgressBar bar;
	
	private enum window {MainWindow,EvaluationWindow,AnalizeWindow};
	private int startPage=0;
	private int i=startPage;
	private int endPage=35;
	
	private Thread work = new Thread(){
		public void run(){
			try {
				process.start();
				okBtn.setDisable(true);		
				TextFileController xml = Main.getSession().getTextFileController();
				PDFController pdf = Main.getSession().getPDFController();
				String path = tf_absolutePath.getText();
				for (i = startPage; i <= endPage; i++) {
					int page = i + 1;
					pdf.readPDF(path, page);
				}	
				xml.writeDailytxt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private Thread process = new Thread(){
		public void run(){	
			System.out.println( "running");
			while(i<=endPage){		
				bar.setProgress((i-startPage)/(float) (endPage-startPage));
			}
			System.out.println("stop");
			analizeBtn.setDisable(false);
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
		openWindow(window.EvaluationWindow);
		openWindow(window.AnalizeWindow);		
	}

	@FXML
	private void onPressBrowse() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession()
				.getStage(0));
		tf_absolutePath.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_absolutePath != null) {
			okBtn.setDisable(false);
		}
	}
	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}
	
	private void openWindow(window window) throws IOException{
			if (Main.getSession().getStage(window.ordinal()) == null){
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
				Main.getSession().setStage(window.ordinal(), stage);
		} else
			Main.getSession().getStage(window.ordinal()).show();;
	}
}
