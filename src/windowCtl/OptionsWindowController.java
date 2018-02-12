package windowCtl;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Main;
import model.Paragraph.detail;
import model.Session.window;
import controller.AnalizeController;

public class OptionsWindowController {
	
	@FXML
	private VBox detailVB;
	@FXML
	private VBox textVB;
	@FXML
	private VBox fontVB;
	@FXML
	private VBox xVB;
	
	public void initialize(){
		int i=0;
		AnalizeController pdfC = Main.getSession().getAnalizeController();
		
		for (String para:pdfC.getAnalizeText()){
			
			ChoiceBox<String> detailCB = new ChoiceBox<String>(FXCollections.observableArrayList(getDetails()));
			detailCB.setValue(detail.values()[pdfC.getDetails().get(i)].name());
			detailVB.getChildren().add(detailCB);
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
	}
	
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.OptionsWindow);
	}
	
	private ArrayList<String> getDetails(){
		ArrayList<String> details = new ArrayList<String>();
		for(detail d:detail.values()){
			details.add(d.name());
		}
		return details;
	}

}
