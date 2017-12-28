package windowCtl;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Main;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	@FXML
	private TextField tf_absolutePath;	    
	@FXML
	private TextArea tf_aimFile;	// so far useless
    @FXML
	private Button okBtn;
	@FXML
	private void onPressOk(){		
		TextFileController xml = Main.getSession().getTextFileController();
		PDFController pdf = Main.getSession().getPDFController();
		String path = tf_absolutePath.getText();
		
		for (int i=1; i<2; i++){
			int page= i+2;
			pdf.readPDF(path, page);
			
		}
		
		
		
	}	
	
	@FXML
	private void onPressBrowse(){			
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession().getCurrentStage());	
		tf_absolutePath.setText(file == null? "" : file.getAbsolutePath() );
		if(tf_absolutePath != null)
			okBtn.setDisable(false);
			
	}
}