package windowCtl;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Main;
import model.Paragraph;
import model.Session.window;
import controller.AnalizeController;
import controller.PDFController;

public class OptionsWindowController {
	
	@FXML
	private VBox fontVB;
	@FXML
	private VBox xVB;
	@FXML
	private VBox textVB;
	
	public void initialize(){
		int i=0;
		AnalizeController pdfC = Main.getSession().getAnalizeController();
		
		for (String para:pdfC.getAnalizeText()){
			TextField text = new TextField();
			text.setText(para);
			textVB.getChildren().add(text);
			TextField font = new TextField();
			font.setText(pdfC.getAnalizeFont().get(i));
			fontVB.getChildren().add(font);
			TextField pos = new TextField();
			pos.setText(pdfC.getAnalizeX().get(i));
			xVB.getChildren().add(pos);
			i++;
		}
		System.out.println(i);
	}
	
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.OptionsWindow);
	}

}
