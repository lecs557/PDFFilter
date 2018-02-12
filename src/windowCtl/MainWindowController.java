package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Main;
import model.Session;
import model.Session.window;

import com.itextpdf.text.pdf.PdfReader;

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	private Session session = Main.getSession();;
	private TextFileController tfctrl;
	private PDFController pdfctrl;
	private AnalizeController analizectrl;
	
	@FXML
	private TextField tf_absolutePath, tf_pathDes ;
	@FXML
	private Button okBtn, analizeBtn, browseDesBtn;
	@FXML
	private ProgressBar bar;
	
		
	private PdfReader reader;
	private int start;
	private int i=start;
	private int end;
	
	public void initialize(){
		bar.setProgress(0);
	}
	
	@FXML
	private void onPressBrowse() throws IOException {
		File file = setupFileChooser().showOpenDialog(Main.getSession()
				.getStage(window.MainWindow));
		if (file != null) {
			tf_absolutePath.setText(file.getAbsolutePath());
			reader = new PdfReader(tf_absolutePath.getText());
			filter(3,4);
		} else{
			tf_absolutePath.setText("");
			analizeBtn.setDisable(true);
		}
	}
	
	@FXML
	private void onPressBrowseDes() throws IOException {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File file = directoryChooser.showDialog(Main.getSession()
				.getStage(window.MainWindow));
		tf_pathDes.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_pathDes == null) {
			tf_pathDes.setText("C:\\Users\\User\\Desktop\\Russisch\\");
		}
	}
	
	@FXML
	private void onPressOk(){
		
	}
	
	private FileChooser setupFileChooser(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF-Files", "*.pdf"));
		return fileChooser;
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		session.openWindow(window.OptionsWindow);		
	}

	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}		
	
	private void setVariables(){
		session.setStart(start);
		session.setDestination(tf_pathDes.getText());
		session.setPdfReader(reader);
		tfctrl = new TextFileController();
		session.setTextFileController(tfctrl);
		analizectrl = new AnalizeController();
		session.setAnalizeController(analizectrl);
		pdfctrl = new PDFController();
		session.setPdfController(pdfctrl);
		
	}
	
	 void filter(int s, int e){
		start=s;
		end=e;
		startFiltering();
		
	}
	
	private void startProcessBar(){
		new Thread(){
			public void run(){		
				while(i<=end){		
					bar.setProgress((i-start)/(float) (end-start));
				}
			}
		}.start();
	}
	
	private void startFiltering(){
		new Thread(){
			public void run(){
				try {
					setVariables();	
					startProcessBar();
					for (i = start; i <= end; i++) {
						int page = i;
						pdfctrl.readPDF(page);
						tfctrl.writeDailytxt();
						analizectrl.analize();
					}	
					analizeBtn.setDisable(false);
					okBtn.setDisable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
