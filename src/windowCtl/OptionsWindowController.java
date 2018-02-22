package windowCtl;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Main;
import model.Paragraph.detail;
import model.Session;
import model.Session.window;

import com.itextpdf.text.pdf.parser.Vector;

import controller.AnalizeController;

public class OptionsWindowController {
	Session ses = Main.getSession();
	AnalizeController analizeC = ses.getAnalizeController();
	ArrayList<Vector> posDate = Main.getSession().getPosDate();
	
	@FXML
	private Label lb;
	@FXML
	private VBox detailVB;
	@FXML
	private VBox textVB;
	@FXML
	private VBox fontVB;
	@FXML
	private VBox xVB;
	@FXML
	private VBox fontsVB;
	@FXML
	private VBox ignoredVB;
	@FXML
	private VBox ignoredFontVB;
	
	
	public void initialize(){
		int i=0;
		lb.setText("Seite:"+ses.getStart() + " Datum " + ses.getPdfController().getTextOfToday().getDatum());
		for (String para:analizeC.getAnalizeText()){
			detailVB.getChildren().add(setUpChoicebox(i));
			textVB.getChildren().add(setUpTextField(para));
			fontVB.getChildren().add(setUpTextField(analizeC.getAnalizeFont().get(i)));
			xVB.getChildren().add(setUpTextField(analizeC.getAnalizeX().get(i)));
			i++;
		}
		
		
		for(Vector y:ses.getPosDate()){
			CheckBox chb = new CheckBox();
			HBox yHB = new HBox();
			yHB.getChildren().add(chb);
			yHB.getChildren().add(setUpTextField(y.get(0)+" "+y.get(1)));
			ignoredVB.getChildren().add(yHB);
		}
	}
	
	@FXML
	private void onPressClose(){
		ses.closeWindow(window.OptionsWindow);
	}
	
	@FXML
	private void onPressOK(){
		for (int i=1;i<posDate.size()+1;i++){
			HBox hbox = (HBox) ignoredVB.getChildren().get(i);
			CheckBox chb = (CheckBox) hbox.getChildren().get(0);
			if(chb.isSelected())
				posDate.remove(i-1);
		}
		
		for ( int i=0;i<detailVB.getChildren().size();i++ ){
			ChoiceBox<String> cb = (ChoiceBox<String>) detailVB.getChildren().get(i);
			processChoice(cb, i);
		}
		
		ses.closeWindow(window.OptionsWindow);
	}
	
	private ObservableList<String> setUpDetails(){
		ArrayList<String> details = new ArrayList<String>();
		for(detail d:detail.values()){
			details.add(d.name());
		}
		details.add("Datum");
		return  FXCollections.observableArrayList(details);
	}
	
	private ChoiceBox<String> setUpChoicebox(int i){
		ChoiceBox<String> detailCB = new ChoiceBox<String>(setUpDetails());
		detailCB.setValue(detail.values()[analizeC.getDetails().get(i)].name());
		return detailCB;
	}
	
	private TextField setUpTextField(String content){
		TextField textField = new TextField();
		textField.setText(content);
		textField.setEditable(false);		
		return textField;
	}

	private void processChoice (ChoiceBox<String> cb, int i){
		switch(cb.getValue()){
		case "Datum":
			TextField tf = (TextField) xVB.getChildren().get(i);
			String pos[] = tf.getText().split(" ");
			Vector date = new Vector(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), 0);
			posDate.add(date);
		}
	}
}
