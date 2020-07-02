import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class RegressionGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel timeoutLbl;
	private JLabel workLbl;
	private JLabel lblReportpath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegressionGUI window = new RegressionGUI();
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
	public RegressionGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 599, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("AutoTUT Regression");
		title.setForeground(Color.BLUE);
		title.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(6, 6, 587, 53);
		frame.getContentPane().add(title);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(6, 64, 587, 504);
		frame.getContentPane().add(tabbedPane_1);
		
		Panel regressionSetting = new Panel();
		tabbedPane_1.addTab("Regression Settings", null, regressionSetting, null);
		regressionSetting.setLayout(null);
		
		JLabel targetLabel = new JLabel("Target Path");
		targetLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		targetLabel.setBounds(21, 43, 87, 16);
		regressionSetting.add(targetLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(120, 38, 130, 26);
		regressionSetting.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(412, 38, 130, 26);
		regressionSetting.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(120, 106, 130, 26);
		regressionSetting.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(412, 106, 130, 26);
		regressionSetting.add(textField_3);
		
		timeoutLbl = new JLabel("Timeout in sec");
		timeoutLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		timeoutLbl.setBounds(6, 111, 102, 16);
		regressionSetting.add(timeoutLbl);
		
		workLbl = new JLabel("Workspace Path");
		workLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		workLbl.setBounds(275, 43, 125, 16);
		regressionSetting.add(workLbl);
		
		lblReportpath = new JLabel("Report Path");
		lblReportpath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblReportpath.setBounds(313, 111, 87, 16);
		regressionSetting.add(lblReportpath);
		
		JButton targetBrowse = new JButton("Browse");
		targetBrowse.setBounds(130, 67, 117, 29);
		regressionSetting.add(targetBrowse);
		
		JButton workBrowse = new JButton("Browse");
		workBrowse.setBounds(425, 67, 117, 29);
		regressionSetting.add(workBrowse);
		
		JButton reportBrowse = new JButton("Browse");
		reportBrowse.setBounds(425, 136, 117, 29);
		regressionSetting.add(reportBrowse);
		
		JButton start = new JButton("START");
		start.setBounds(228, 186, 117, 29);
		regressionSetting.add(start);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 254, 523, 146);
		regressionSetting.add(scrollPane);
		JProgressBar progressBar=new JProgressBar();
		progressBar.setBounds(6, 215, 554, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        progressBar.setVisible(true);
        regressionSetting.add(progressBar);
        
        JButton btnTerminate = new JButton("TERMINATE");
        btnTerminate.setForeground(Color.RED);
        btnTerminate.setBounds(176, 423, 117, 29);
        regressionSetting.add(btnTerminate);
        
        JButton btnExit = new JButton("EXIT");
        btnExit.setForeground(Color.RED);
        btnExit.setBounds(305, 423, 117, 29);
        regressionSetting.add(btnExit);
		
		Panel regressionLog = new Panel();
		tabbedPane_1.addTab("Regression Log", null, regressionLog, null);
		regressionLog.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 35, 554, 346);
		regressionLog.add(scrollPane_1);
		
		JButton terminate = new JButton("TERMINATE");
		terminate.setForeground(Color.RED);
		terminate.setBounds(176, 423, 117, 29);
		regressionLog.add(terminate);
		
		JButton btnExit_1 = new JButton("EXIT");
		btnExit_1.setForeground(Color.RED);
		btnExit_1.setBounds(305, 423, 117, 29);
		regressionLog.add(btnExit_1);
		
		JLabel lblNewLabel = new JLabel("Detailed Log");
		lblNewLabel.setBounds(18, 7, 199, 16);
		regressionLog.add(lblNewLabel);
	}
}
