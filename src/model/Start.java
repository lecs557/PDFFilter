package model;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.pdf.PdfReader;
import javax.swing.SpinnerNumberModel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;


public class Start {
	
	private static Session ses;
	private PdfReader reader;
	int pages;

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ses = new Session(frame);
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
		frame.setBounds(100, 100, 449, 256);
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
		lbl_path.setBounds(67, 63, 238, 14);
		frame.getContentPane().add(lbl_path);
		
		JButton btnDurchsuchen = new JButton("Durchsuchen");
		btnDurchsuchen.setBounds(318, 59, 106, 23);
		frame.getContentPane().add(btnDurchsuchen);
		
		JLabel lbl_start = new JLabel("Startseite");
		lbl_start.setBounds(10, 88, 77, 14);
		frame.getContentPane().add(lbl_start);
		
		final JSpinner spn_start =  new JSpinner();
		spn_start.setEnabled(false);
		spn_start.setBounds(102, 84, 47, 20);
		frame.getContentPane().add(spn_start);
	
		JLabel lbl_end = new JLabel("Endseite");
		lbl_end.setBounds(10, 113, 77, 14);
		frame.getContentPane().add(lbl_end);
		
		final JSpinner spn_end = new JSpinner();
		spn_end.setEnabled(false);
		spn_end.setBounds(102, 109, 47, 20);
		frame.getContentPane().add(spn_end);
		
		final JButton btn_ok = new JButton("OK");
		
		btn_ok.setEnabled(false);
		btn_ok.setBounds(10, 183, 77, 23);
		frame.getContentPane().add(btn_ok);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 154, 414, 14);
		frame.getContentPane().add(progressBar);
		
		JButton btn_close = new JButton("Schlie\u00DFen");
		btn_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btn_close.setBounds(335, 183, 89, 23);
		frame.getContentPane().add(btn_close);
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = setupFileChooser();
				int returnVal = chooser.showOpenDialog(frame.getOwner());
				if(returnVal == JFileChooser.APPROVE_OPTION) {	
					try {
						reader = new PdfReader(chooser.getSelectedFile().getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					pages = reader.getNumberOfPages();
					lbl_path.setText(chooser.getSelectedFile().getAbsolutePath());
					spn_start.setModel(new SpinnerNumberModel(1, 1, pages, 1));
					spn_end.setModel(new SpinnerNumberModel(1, 1, pages, 1));
					spn_start.setEnabled(true);
					spn_end.setEnabled(true);
					btn_ok.setEnabled(true);
				} else{
					lbl_path.setText("");
					spn_start.setEnabled(false);
					spn_end.setEnabled(false);
					btn_ok.setEnabled(false);
				};
				
			} });
		
		spn_start.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				((SpinnerNumberModel) spn_end.getModel()).setMinimum( (Integer) spn_start.getValue() );
				if((Integer) spn_start.getValue() > (Integer) spn_end.getValue() ) {
					((SpinnerNumberModel) spn_end.getModel()).setValue( (Integer) spn_start.getValue() );
				}
				
			}
		});
		
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	private JFileChooser setupFileChooser(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open Resource File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("PDF-Files", "pdf"));
		return fileChooser;
	}
}
