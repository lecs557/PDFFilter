package windowCtl;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Main;
import model.Paragraph.detail;
import model.Session.window;
import controller.AnalizeController;

public class OptionsWindowController {
	
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
		AnalizeController pdfC = Main.getSession().getAnalizeController();
		lb.setText("Seite:"+Main.getSession().getStart());
		for (String para:pdfC.getAnalizeText()){
			ChoiceBox<String> detailCB = new ChoiceBox<String>(FXCollections.observableArrayList(getDetails()));
			detailCB.setValue(detail.values()[pdfC.getDetails().get(i)].name());
			detailVB.getChildren().add(detailCB);
			TextField text = new TextField();
			text.setText(para);
			text.setEditable(false);
			textVB.getChildren().add(text);
			TextField font = new TextField();
			font.setText(pdfC.getAnalizeFont().get(i));
			font.setEditable(false);
			fontVB.getChildren().add(font);
			TextField pos = new TextField();
			pos.setText(pdfC.getAnalizeX().get(i));
			pos.setEditable(false);
			xVB.getChildren().add(pos);
			i++;
		}
		
		
		for(int y:Main.getSession().getyIgnore()){
			CheckBox chb = new CheckBox();
			TextField ftf = new TextField();
			ftf.setText(""+y);;
			ftf.setEditable(false);
			HBox yHB = new HBox();
			yHB.getChildren().add(chb);
			yHB.getChildren().add(ftf);
			ignoredVB.getChildren().add(yHB);
		}
	}
	
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.OptionsWindow);
	}
	
	@FXML
	private void onPressOK(){
		ArrayList<Integer> yIgnore = Main.getSession().getyIgnore();
		
		
		for (int i=1;i<yIgnore.size()+1;i++){
			HBox hbox = (HBox) ignoredVB.getChildren().get(i);
			CheckBox chb = (CheckBox) hbox.getChildren().get(0);
			if(chb.isSelected())
				yIgnore.remove(i-1);
			
		}
		
		for ( int i=0;i<detailVB.getChildren().size();i++ ){
			ChoiceBox<String> cb = (ChoiceBox<String>) detailVB.getChildren().get(i);
			if(cb.getValue().equals("yIgnore")){
				TextField tf = (TextField) xVB.getChildren().get(i);
				String y = tf.getText().split(" ")[1];
				yIgnore.add(Integer.parseInt(y));
			}
		}		
		Main.getSession().setyIgnore(yIgnore);
		Main.getSession().closeWindow(window.OptionsWindow);
	}
	
	private ArrayList<String> getDetails(){
		ArrayList<String> details = new ArrayList<String>();
		for(detail d:detail.values()){
			details.add(d.name());
		}
		details.add("yIgnore");
		return details;
	}

}
