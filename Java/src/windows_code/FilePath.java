package windows_code;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class FilePath {

	public static JFrame frame;
	private static JTextField fileName;
	private static JTextField packageName;
	public static ArrayList <String> workspaceDetails =  new ArrayList <String>(3);
	public static ArrayList <String> output = new ArrayList <String>(3);
	public static boolean done = false;
	private static JTextField workPath;
	public static String tempFilePath = "Imp/temp.txt";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilePath window = new FilePath();
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
	public FilePath() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		workspaceDetails.add("0");
		workspaceDetails.add("0");
		workspaceDetails.add("0");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Workspace Path :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(67, 142, 101, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("File Path :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(67, 73, 101, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		workPath = new JTextField();
		workPath.setEditable(false);
		workPath.setHorizontalAlignment(SwingConstants.TRAILING);
		workPath.setForeground(Color.BLACK);
		workPath.setColumns(10);
		workPath.setBounds(178, 139, 158, 20);
		frame.getContentPane().add(workPath);
		
		fileName = new JTextField();
		fileName.setBounds(178, 70, 118, 20);
		frame.getContentPane().add(fileName);
		fileName.setColumns(10);
		
		JLabel lblPackageName = new JLabel("Package Name :");
		lblPackageName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPackageName.setBounds(50, 109, 118, 14);
		frame.getContentPane().add(lblPackageName);
		
		packageName = new JTextField();
		packageName.setEditable(false);
		packageName.setForeground(Color.BLACK);
		packageName.setBounds(178, 106, 118, 20);
		frame.getContentPane().add(packageName);
		packageName.setColumns(10);
		
		JButton submit = new JButton("Submit");
		submit.setEnabled(false);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!fileName.getText().isEmpty())
				{
					frame.dispose();
					done = true;
					workspaceDetails.set(0, workPath.getText());
					workspaceDetails.set(1, fileName.getText());
					workspaceDetails.set(2, packageName.getText());
				}
				else
					
					Message.setMessage("Please Input the file path", 0);
			}
		});
		submit.setBounds(164, 198, 89, 23);
		frame.getContentPane().add(submit);
		
		JButton pickFileName = new JButton("...");
		pickFileName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileNameText = chooseFile("File Name", new File(workspaceDetails.get(0)), ".b");
				//workspaceDetails.set(1, fileNameText);
				fileName.setText(fileNameText);
				try {
				packageName.setText(getPackageName(fileNameText));
				workPath.setText(getWorkspacePath(fileNameText));
				System.out.println("File path : "+fileNameText);
				submit.setEnabled(true);
				frame.getRootPane().setDefaultButton(submit);

				}
				catch(Exception e1)
				{
					System.out.println(e1.getMessage());
					System.out.println("Incorrect directory tree structure");
					System.out.println(fileNameText);
				}
			}
		});
		pickFileName.setBounds(306, 69, 30, 23);
		frame.getContentPane().add(pickFileName);
		frame.getRootPane().setDefaultButton(pickFileName);
		
		JLabel lblSetWorkspace = new JLabel("SET WORKSPACE");
		lblSetWorkspace.setForeground(Color.RED);
		lblSetWorkspace.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblSetWorkspace.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetWorkspace.setBounds(114, 11, 192, 23);
		frame.getContentPane().add(lblSetWorkspace);
		
	}
	
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
	
	public static String getPackageName(String filePath) {
		String output = "";
		StringBuilder strBuild = new StringBuilder(filePath);
		ArrayList<String> tempString = new ArrayList<String>();
		StringBuilder tempWord = new StringBuilder();
		for (int i = 0; i<strBuild.length(); i++)
		{
			if (strBuild.charAt(i) != File.separator.charAt(0))
				tempWord.append(strBuild.charAt(i));
			else
			{
				tempString.add(tempWord.toString());
				tempWord = new StringBuilder();
			}
		}
		return tempString.get(tempString.size()-3);
	}
	
	public static String getFileName(String filePath)
	{
		String output = "";
		filePath += "\\";
		StringBuilder strBuild = new StringBuilder(filePath);
		ArrayList<String> tempString = new ArrayList<String>();
		StringBuilder tempWord = new StringBuilder();
		for (int i = 0; i<strBuild.length(); i++)
		{
			if (strBuild.charAt(i) != File.separator.charAt(0))
				tempWord.append(strBuild.charAt(i));
			else
			{
				tempString.add(tempWord.toString());
				tempWord = new StringBuilder();
			}
		}
		return tempString.get(tempString.size()-1);
	}
	
	public static String getWorkspacePath(String filePath)
	{
		String output = "";
		filePath += "\\";
		StringBuilder strBuild = new StringBuilder(filePath);
		ArrayList<String> tempString = new ArrayList<String>();
		StringBuilder tempWord = new StringBuilder();
		for (int i = 0; i<strBuild.length(); i++)
		{
			if (strBuild.charAt(i) != File.separator.charAt(0))
				tempWord.append(strBuild.charAt(i));
			/*
			 * else if(i == strBuild.length()-1) tempString.add(tempWord.toString());
			 */
			else
			{
				tempString.add(tempWord.toString());
				tempWord = new StringBuilder();
			}
		}
		filePath = "";
		for (int i = 0; i<tempString.size()-5; i++)
		{
			if (i!=tempString.size()-6)
				filePath += tempString.get(i)+File.separator;
			else
				filePath += tempString.get(i);
		}
		return filePath;
	}
	
	@SuppressWarnings("static-access")
	public static ArrayList<String> getWorkspaceDetails()
	{
		/*  This method will return all the details of the workspace needed by the program to run
		 *  The output is a array list and the format is as mentioned below:
		 * 	0th index --> Workspace directory
		 *  1st index --> Full file path
		 *  2nd index --> Path of package name
		 */
		try {
			FilePath window = new FilePath();
			window.frame.setVisible(true);
			while(!window.done)
			{
				Thread.sleep((long) 0.001);;
			}
			return window.workspaceDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
