package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
	
	private int i=0;
	
	private Thread work = new Thread(){
		public void run(){
			try {
			TextFileController xml = Main.getSession().getTextFileController();
			PDFController pdf = Main.getSession().getPDFController();
			String path = tf_absolutePath.getText();
			for (i = 0; i <= 15; i++) {
				int page = i + 1;
				pdf.readPDF(path, page);
			}	
			xml.writeDailytxt();
			okBtn.setDisable(true);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private Thread process = new Thread(){
		public void run(){	
			while(i<15){
				bar.setProgress(i/15f);
				System.out.println(i);
			}
		}
	};
	
	public void initialize(){
		bar.setProgress(0);
		process.start();
		tf_absolutePath.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				work.start();
			}	
		});
	}
	

	@FXML
	private void onPressOk() throws IOException, InterruptedException {
		work.start();
		if(i==15){
			openWindow("EvaluationWindow");
			openWindow("AnalizeWindow");			
		}
		
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		openWindow("AnalizeWindow");
		openWindow("EvaluationWindow");
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
			analizeBtn.setDisable(false);
		}
	}
	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}
	
	private void openWindow(String window) throws IOException{
		int i=0;
		if (window.equals("EvaluationWindow"))
			i=1;
		if (window.equals("AnalizeWindow"))
			i=2;
		if (Main.getSession().getStage(i) == null){
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/"+window+".xml")); 
			Scene scene = new Scene(root);
			stage.setTitle("PDF Filter");
			stage.setScene(scene);
			if (i==2)
				stage.setX(280);
			if (i==1)
				stage.setX(820);
			stage.show();
			Main.getSession().setStage(i, stage);
		} else
			Main.getSession().getStage(i).toFront();
	}
}
