package windowCtl;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Abschnitt;
import model.Main;
import model.Session.window;
import controller.PDFController;

public class AnalizeWindowController {
	PDFController pdfc = Main.getSession().getPdfController();
	@FXML
	private TextArea analizeFont;
	@FXML
	private TextArea analizeX;
	
	public void initialize(){
		for (Abschnitt text:pdfc.getArtikel().getArtikel()){
			analizeFont.setText(analizeFont.getText()+text.getAbschnitt()+"\n");
		}
	}
	
	// FXML
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
