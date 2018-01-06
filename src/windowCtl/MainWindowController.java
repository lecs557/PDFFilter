package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private void onPressOk() throws IOException, InterruptedException {
		TextFileController xml = Main.getSession().getTextFileController();
		PDFController pdf = Main.getSession().getPDFController();
		String path = tf_absolutePath.getText();
		for (int i = 0; i <= 15; i++) {
			int page = i + 1;
			pdf.readPDF(path, page);
		}
		xml.writeDailytxt();
		Stage okstage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/EvaluationWindow.xml")); 
		Scene scene = new Scene(root);
		okstage.setTitle("PDF Filter");
		okstage.setScene(scene);        
		okstage.show();    
		
		Stage stage = new Stage();
		Parent anaRoot = FXMLLoader.load(getClass().getResource("/gui/AnalizeWindow.xml")); 
		Scene anaScene = new Scene(anaRoot);
		stage.setTitle("PDF Filter");
		stage.setScene(anaScene);        
		stage.show();    
	}
	@FXML
	private void onPressAnalize() throws IOException{
			
		Stage stage = new Stage();
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