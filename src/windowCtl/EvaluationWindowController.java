package windowCtl;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Main;
import model.Session;
import model.Session.window;
import controller.AnalizeController;
import controller.TextFileController;
	

public class EvaluationWindowController {
	private Session sess = Main.getSession();;
	private AnalizeController analize = sess.getAnalizeController();
	@FXML
	private TextField sum;
	@FXML 
	private HBox hb;
	@FXML 
	private TextArea crntTA, invalids;
	
	
	public void initialize(){
		sum.setText("Gesamt: "+ (sess.getPdfReader().getNumberOfPages()-sess.getInvalids().size()) );
		for(ArrayList<String> aos : analize.getAmountOfSegments()){
			createTA();
			for(String aoms: aos){
				editTA(aoms+"\n");			
			}
		}
		for (String inv: sess.getInvalids()){
			invalids.setText(invalids.getText()+inv+"\n");
		}
	}
	
	// FXML
	@FXML
	private void onPressClose(){
		sess.closeWindow(window.EvaluationWindow);
	}
	
	// PRIVATE
	private void createTA(){
		crntTA = new TextArea();
		crntTA.setEditable(false);
		hb.getChildren().add(crntTA);
	}
	
	private void editTA(String text){
		crntTA.setText(crntTA.getText()+text);
	}

}
