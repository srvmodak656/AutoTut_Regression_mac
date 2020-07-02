import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegressionGUI {

	private static JFrame frame;
	private static JTextField targetPathField;
	private static JTextField workspaceField;
	private static JTextField timeoutField;
	private static JTextField reportPathField;
	private static JLabel timeoutLbl;
	private static JLabel workLbl;
	private static JLabel lblReportpath;
	public static String tempFilePath = "Imp/temp.txt";

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
	
	public static String getPresetDirectoryPath(String Path)
	{
		String path = null;
		File file = new File(Path);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(Exception e1)
			{
				System.out.println("New File creation Error\n"+e1.getMessage());
			}
		}
		try
		{
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
			path = (String)objIn.readObject();
			objIn.close();
			return path;
		}
		catch(Exception e1)
		{
			System.out.println(e1.getLocalizedMessage());
			return null;
		}
	}
	
	public static String chooseFile(String choosertitle, File workspacePath, String fileExtension) {
		
		String output = new String();
		JFileChooser chooser;
		chooser = new JFileChooser(); 
		chooser.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return fileExtension;
			}
			
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				if (file.getName().endsWith(fileExtension)||file.isDirectory()) {
                    return true;
                 }
                 return false;
			}
		} );
		if (workspacePath.getAbsolutePath() == "")
			chooser.setCurrentDirectory(new java.io.File("."));
		else
			chooser.setCurrentDirectory(workspacePath);
	    chooser.setDialogTitle(choosertitle);
	    String temp = workspacePath.getPath();
	    if (workspacePath.getPath().equals("0"))
	    {
	    	String chooserPath = null;
	    	try
	    	{
	    		chooserPath = getPresetDirectoryPath(tempFilePath);
	    		if (!chooserPath.equals(null))
	    	    {
	    			System.out.println("directory path ==== "+chooserPath);
	    			chooser.setCurrentDirectory(new File(chooserPath));
	    		}
	    		else
	    		{
	    			System.out.println("Temp File Not Found");
	    		}
	    	}
	    	catch(Exception e1)
	    	{
	    		System.out.println(e1.getMessage());
	    		System.out.println("Error in creating the temp file");
	    	}
		    	
	    }
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(chooser.getParent()) == JFileChooser.APPROVE_OPTION) { 
			
	        output = chooser.getSelectedFile().getAbsolutePath();
	        return output;
	        }
	      else {
	        System.out.println("No Selection ");
	        return null;
	        }
	   }
	
public static String chooseFolder(String choosertitle, File workspacePath) {
		
		String output = new String();
		JFileChooser chooser;
		chooser = new JFileChooser(); 
		
		if (workspacePath.getAbsolutePath() == "")
			chooser.setCurrentDirectory(new java.io.File("."));
		else
			chooser.setCurrentDirectory(workspacePath);
	    chooser.setDialogTitle(choosertitle);
	    String temp = workspacePath.getPath();
	    if (workspacePath.getPath().equals("0"))
	    {
	    	String chooserPath = null;
	    	try
	    	{
	    		chooserPath = getPresetDirectoryPath(tempFilePath);
	    		if (!chooserPath.equals(null))
	    	    {
	    			System.out.println("directory path ==== "+chooserPath);
	    			chooser.setCurrentDirectory(new File(chooserPath));
	    		}
	    		else
	    		{
	    			System.out.println("Temp File Not Found");
	    		}
	    	}
	    	catch(Exception e1)
	    	{
	    		System.out.println(e1.getMessage());
	    		System.out.println("Error in creating the temp file");
	    	}
		    	
	    }
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(chooser.getParent()) == JFileChooser.APPROVE_OPTION) { 
			
	        output = chooser.getSelectedFile().getAbsolutePath();
	        return output;
	        }
	      else {
	        System.out.println("No Selection ");
	        return null;
	        }
	   }

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
		
		targetPathField = new JTextField();
		targetPathField.setColumns(10);
		targetPathField.setBounds(120, 38, 130, 26);
		regressionSetting.add(targetPathField);
		
		workspaceField = new JTextField();
		workspaceField.setColumns(10);
		workspaceField.setBounds(412, 38, 130, 26);
		regressionSetting.add(workspaceField);
		
		timeoutField = new JTextField();
		timeoutField.setColumns(10);
		timeoutField.setBounds(120, 106, 130, 26);
		regressionSetting.add(timeoutField);
		
		reportPathField = new JTextField();
		reportPathField.setColumns(10);
		reportPathField.setBounds(412, 106, 130, 26);
		regressionSetting.add(reportPathField);
		
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
		targetBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = chooseFolder("Target File Path", new File(""));
				targetPathField.setText(path);
			}
		});
		targetBrowse.setBounds(130, 67, 117, 29);
		regressionSetting.add(targetBrowse);
		
		JButton workBrowse = new JButton("Browse");
		workBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = chooseFolder("Workspace File Path", new File(""));
				workspaceField.setText(path);
			}
		});
		workBrowse.setBounds(425, 67, 117, 29);
		regressionSetting.add(workBrowse);
		
		JButton reportBrowse = new JButton("Browse");
		reportBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = chooseFolder("Report File Path", new File(""));
				reportPathField.setText(path);
			}
		});
		reportBrowse.setBounds(425, 136, 117, 29);
		regressionSetting.add(reportBrowse);
		
		JButton start = new JButton("START");
		start.setBounds(228, 186, 117, 29);
		regressionSetting.add(start);
		
		JScrollPane console = new JScrollPane();
		console.setBounds(21, 254, 523, 146);
		regressionSetting.add(console);
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
