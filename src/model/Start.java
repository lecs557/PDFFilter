package model;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.itextpdf.text.pdf.PdfReader;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javafx.stage.FileChooser;
import model.Session.window;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Start {
	
	private static Session ses;
	private Session session = Main.getSession();
	private PdfReader reader;
	private int start;
	private int i=start;
	private int end;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("PDF to HTML");
		title.setBounds(170, 6, 93, 20);
		title.setFont(new Font("Tahoma", Font.PLAIN, 16));
		title.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(title);
		
		JLabel lbl_pdfFile = new JLabel("PDF File");
		lbl_pdfFile.setBounds(10, 63, 47, 14);
		frame.getContentPane().add(lbl_pdfFile);
		
		final JLabel lbl_path = new JLabel("PATH");
		lbl_path.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_path.setBounds(70, 63, 238, 14);
		frame.getContentPane().add(lbl_path);
		
		JButton btnDurchsuchen = new JButton("Durchsuchen");
		btnDurchsuchen.setBounds(329, 59, 95, 23);
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 JFileChooser chooser = setupFileChooser();
				 int returnVal = chooser.showOpenDialog(frame.getOwner());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
						try {
							reader = new PdfReader(chooser.getSelectedFile().getAbsolutePath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lbl_path.setText(chooser.getSelectedFile().getAbsolutePath());
						//preBtn.setDisable(false);
						//bar.setProgress(0);
				    	} else{
					lbl_path.setText("");
					//preBtn.setDisable(true);
				}
			}
		});
		frame.getContentPane().add(btnDurchsuchen);
		
		JLabel lbl_start = new JLabel("Startseite");
		lbl_start.setBounds(10, 116, 47, 14);
		frame.getContentPane().add(lbl_start);
		
		JSpinner spn_start = new JSpinner();
		spn_start.setBounds(63, 113, 29, 20);
		frame.getContentPane().add(spn_start);
		
		JLabel lbl_end = new JLabel("Endseite");
		lbl_end.setBounds(10, 142, 41, 14);
		frame.getContentPane().add(lbl_end);
		
		JSpinner spn_end = new JSpinner();
		spn_end.setBounds(63, 139, 29, 20);
		frame.getContentPane().add(spn_end);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(287, 215, 137, 23);
		frame.getContentPane().add(btnOk);
	}
	
	private JFileChooser setupFileChooser(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open Resource File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("PDF-Files", "pdf"));
		return fileChooser;
	}
}
