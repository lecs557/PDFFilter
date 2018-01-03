package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Main;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	@FXML
	private TextField tf_absolutePath;
	@FXML
	public ProgressBar bar;	
	@FXML
	private Button okBtn;
	@FXML
	private Button analizeBtn;

	@FXML
	private void onPressOk() throws IOException, InterruptedException {
		TextFileController xml = Main.getSession().getTextFileController();
		PDFController pdf = Main.getSession().getPDFController();
		String path = tf_absolutePath.getText();

		bar.setProgress(0);
		for (int i = 1; i <= 100; i++) {
			int page = i + 1;
			pdf.readPDF(path, page);
		}
		xml.writeDailytxt();
	}
	@FXML
	private void onPressAnalize() throws IOException{
		PDFController pdf = Main.getSession().getPDFController();
		String path = tf_absolutePath.getText();

		for (int i = 1; i < 5; i++) {
			int page = i + 1;
			pdf.readPDF(path, page);
		}
		
		Stage stage = Main.getSession().getCurrentStage();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/AnalizeWindow.xml")); 
		Scene scene = new Scene(root);
		stage.setTitle("PDF Filter");
		stage.setScene(scene);        
		stage.show();    
		
	}

	@FXML
	private void onPressBrowse() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession()
				.getCurrentStage());
		tf_absolutePath.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_absolutePath != null) {
			okBtn.setDisable(false);
			analizeBtn.setDisable(false);
		}
	}
}