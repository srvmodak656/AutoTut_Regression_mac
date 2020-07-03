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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

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
	public static String targetPath;
	public static String workspacePath;
	public static String[] allowedProduct;
	public static int timeoutMilliseconds;
	public static String reportPath;
	private static int allowedProdCount = 0;

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
	private static Object[] popElement(Object arr[])
	{
		Object output[] = new Object[arr.length - 1];
		for (int i = 0; i < output.length; i++)
			output[i] = arr[i];
		return output;
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
		targetLabel.setBounds(21, 18, 87, 16);
		regressionSetting.add(targetLabel);
		
		targetPathField = new JTextField();
		targetPathField.setColumns(10);
		targetPathField.setBounds(120, 13, 130, 26);
		regressionSetting.add(targetPathField);
		
		workspaceField = new JTextField();
		workspaceField.setColumns(10);
		workspaceField.setBounds(412, 13, 130, 26);
		regressionSetting.add(workspaceField);
		
		timeoutField = new JTextField();
		timeoutField.setColumns(10);
		timeoutField.setBounds(120, 76, 130, 26);
		regressionSetting.add(timeoutField);
		
		reportPathField = new JTextField();
		reportPathField.setColumns(10);
		reportPathField.setBounds(120, 125, 130, 26);
		regressionSetting.add(reportPathField);
		
		timeoutLbl = new JLabel("Timeout in sec");
		timeoutLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		timeoutLbl.setBounds(6, 81, 102, 16);
		regressionSetting.add(timeoutLbl);
		
		workLbl = new JLabel("Workspace Path");
		workLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		workLbl.setBounds(275, 18, 125, 16);
		regressionSetting.add(workLbl);
		
		lblReportpath = new JLabel("Report Path");
		lblReportpath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblReportpath.setBounds(21, 130, 87, 16);
		regressionSetting.add(lblReportpath);
		
		JButton targetBrowse = new JButton("Browse");
		targetBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetPath = chooseFolder("Target File Path", new File(""));
				targetPathField.setText(targetPath);
			}
		});
		targetBrowse.setBounds(130, 40, 117, 29);
		regressionSetting.add(targetBrowse);
		
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(346, 103, 76, 82);
        regressionSetting.add(scrollPane);
        
        JList allowedProductList = new JList();
        allowedProductList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(allowedProductList);
        
        JTextArea allowedListArea = new JTextArea();
        allowedListArea.setBounds(468, 103, 76, 82);
        regressionSetting.add(allowedListArea);
        
        JButton toBtn = new JButton(">>");
        toBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		List <String> valuesSelected = allowedProductList.getSelectedValuesList();
        		for(String value : valuesSelected)
        			{
        				String temp = allowedListArea.getText();
        				temp += value+"\n";
        				allowedListArea.setText(temp);
        				allowedProduct[allowedProdCount++] = value;
        			}
        	}
        });
        toBtn.setBounds(422, 100, 44, 29);
        regressionSetting.add(toBtn);
        
        JButton fromBtn = new JButton("<<");
        fromBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String temp = allowedListArea.getText();
        		String[] tempList = temp.split("\n");
        		tempList = (String[]) popElement(tempList);
        		
        		for (String val:tempList)
        		{
        			allowedListArea.setText(val+"\n");
        			allowedProduct = (String[]) popElement(allowedProduct);
        		}
        	}
        });
        fromBtn.setBounds(423, 156, 44, 29);
        regressionSetting.add(fromBtn);
        
		JButton workBrowse = new JButton("Browse");
		workBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				workspacePath = chooseFolder("Workspace File Path", new File(""));
				workspaceField.setText(workspacePath);
				allowedProductList.setEnabled(true);
				ArrayList<String>temp = new ArrayList<>();
				int i = 0;
				
				for (String fdr : new File(workspacePath).list())
				{
					if (fdr.length()<=2)
						temp.add(fdr);
				}
				allowedProductList.setListData(temp.toArray());
				//allowedProduct = (String[])catchList.toArray();
			}
		});
		workBrowse.setBounds(422, 40, 117, 29);
		regressionSetting.add(workBrowse);
		
		JButton reportBrowse = new JButton("Browse");
		reportBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportPath = chooseFile("Report File Path", new File(""), "");
				reportPathField.setText(reportPath);
			}
		});
		reportBrowse.setBounds(133, 152, 117, 29);
		regressionSetting.add(reportBrowse);
		
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (workspaceField.getText().isEmpty())
				{
					Message.setMessage("Workspace path is a mandatory input and should be chosen", 0);
				}
				if (targetPathField.getText().isEmpty())
				{
					Message.setMessage("Target path is a mandatory input and should be choosed", 0);
				}
				if (timeoutField.getText().isEmpty())
				{
					Message.setMessage("Timeout is a mandatory input and should be filled", 0);
				}
				if (reportPathField.getText().isEmpty())
				{
					Message.setMessage("Report Path is a mandatory input and should be choosed", 0);
				}
				if (!allowedListArea.getText().isEmpty())
				{
					Message.setMessage("Products is a mandatory input and should be choosed", 0);
				}
				timeoutMilliseconds = Integer.parseInt(timeoutField.getText())*1000;
				Regression.runRegression(targetPath, workspacePath, allowedProduct, timeoutMilliseconds, reportPath);
			}
		});
		start.setBounds(228, 204, 117, 29);
		regressionSetting.add(start);
		
		JScrollPane console = new JScrollPane();
		console.setBounds(21, 287, 523, 113);
		regressionSetting.add(console);
		
		JProgressBar progressBar=new JProgressBar();
		progressBar.setBounds(6, 245, 554, 30);
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
        
        JLabel lblNewLabel_1 = new JLabel("Available Product");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
        lblNewLabel_1.setBounds(346, 81, 158, 16);
        regressionSetting.add(lblNewLabel_1);
        
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
		
		JProgressBar progressBar1=new JProgressBar();
		progressBar1.setBounds(6, 393, 554, 30);
		progressBar1.setBorderPainted(true);
		progressBar1.setStringPainted(true);
		progressBar1.setBackground(Color.WHITE);
		progressBar1.setForeground(Color.BLACK);
		progressBar1.setValue(0);
		progressBar1.setVisible(true);
		regressionLog.add(progressBar1);
	}
}
