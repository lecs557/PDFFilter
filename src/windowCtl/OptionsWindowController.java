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
	private TextField textField;
	@FXML
	private Label lb;
	@FXML
	private VBox detailVB, textVB, fontVB;
	@FXML
	private VBox xVB, datePosVB;
	
	
	public void initialize(){
		int i=0;
		int a=9;
		lb.setText("Seite:"+ses.getStart() + " Datum " + ses.getPdfController().getTextOfToday().getDatum());
		for (String para:analizeC.getAnalizeText()){
			if(analizeC.getParaList().get(i) != a) {
				detailVB.getChildren().add(setUpChoicebox(i));
				textVB.getChildren().add(setUpTextField(para));
				fontVB.getChildren().add(setUpTextField(analizeC.getAnalizeFont().get(i)));
				xVB.getChildren().add(setUpTextField(analizeC.getAnalizeX().get(i)));
				a = analizeC.getParaList().get(i);
			}
			else{
				textField.setText(textField.getText()+" // "+para);
			}
			i++;
		}
		for(Vector y:ses.getPosDate()){
			CheckBox chb = new CheckBox();
			HBox yHB = new HBox();
			yHB.getChildren().add(chb);
			yHB.getChildren().add(setUpTextField(y.get(0)+" "+y.get(1)));
			datePosVB.getChildren().add(yHB);
		}
	}
	
	// FXML
	@FXML
	private void onPressOK(){
		for (int i=1;i<posDate.size()+1;i++){
			HBox hbox = (HBox) datePosVB.getChildren().get(i);
			CheckBox chb = (CheckBox) hbox.getChildren().get(0);
			if(chb.isSelected()){
				posDate.remove(i-1);
				i--;
			}
		}
		for ( int i=0;i<detailVB.getChildren().size();i++ ){
			ChoiceBox<String> cb = (ChoiceBox<String>) detailVB.getChildren().get(i);
			processChoice(cb, i);
		}
		ses.setHasDate(posDate.size()!=0);
		ses.closeWindow(window.OptionsWindow);
	}
	
	@FXML
	private void onPressClose(){
		ses.closeWindow(window.OptionsWindow);
	}
	
	// PRIVATE
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
		textField = new TextField();
		textField.setText(content);
		textField.setEditable(false);		
		return textField;
	}

	private void processChoice (ChoiceBox<String> cb, int i){
		switch(cb.getValue()){
		case "Datum":
			TextField tf = (TextField) xVB.getChildren().get(i);
			posDate.add(vectorFromTf(tf));
		}
	}
	
	private Vector vectorFromTf(TextField tf){
		String pos[] = tf.getText().split(" ");
		int x =  Integer.parseInt(pos[0]);
		int y = Integer.parseInt(pos[1]);
		return new Vector(x, y, 0);
	}
}
