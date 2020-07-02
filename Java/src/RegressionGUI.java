import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
 
public class RegressionGUI {
    
	public static JFrame frame;
	public static JProgressBar progressBar=new JProgressBar();
	public static JLabel message=new JLabel();
	public static JLabel longer = new JLabel();
	private JTextField pathField;
	private JTextField workspacePathField;
	private JTextField timeoutField;
	private JTextField reportPathField;
	public static String tempFilePath = "Imp/temp.txt";
    RegressionGUI()
    {
    	JLabel heading=new JLabel("AutoTUT Regression");
    	heading.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel message=new JLabel();//Crating a JLabel for displaying the message
        
        frame = new JFrame();
        frame.setEnabled(true);
    	frame.setSize(598,506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.getContentPane().setLayout(null);
        heading.setBounds(131, 28, 332, 40);
        heading.setFont(new Font("arial",Font.BOLD,30));
        heading.setForeground(Color.BLUE);
        heading.setVisible(true);
        frame.getContentPane().add(heading);
        
        message.setBounds(250, 320, 200, 40);
        message.setForeground(Color.black);//Setting foreground Color
        message.setFont(new Font("arial",Font.BOLD,15));//Setting font properties
        message.setVisible(true);
        frame.getContentPane().add(message);//adding label to the frame
        
        progressBar.setBounds(95, 292, 402, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        progressBar.setVisible(true);
        frame.getContentPane().add(progressBar);
        
        JLabel targetPath = new JLabel("Target Path");
        targetPath.setHorizontalAlignment(SwingConstants.CENTER);
        targetPath.setBounds(33, 96, 87, 16);
        frame.getContentPane().add(targetPath);
        
        pathField = new JTextField();
        pathField.setBounds(141, 91, 130, 26);
        frame.getContentPane().add(pathField);
        pathField.setColumns(10);
        
        JButton targetBrowser = new JButton("Browse");
        targetBrowser.setBounds(151, 118, 117, 29);
        frame.getContentPane().add(targetBrowser);
        
        JLabel workspacePath = new JLabel("Workspace Path");
        workspacePath.setHorizontalAlignment(SwingConstants.CENTER);
        workspacePath.setBounds(303, 96, 117, 16);
        frame.getContentPane().add(workspacePath);
        
        workspacePathField = new JTextField();
        workspacePathField.setBounds(421, 91, 130, 26);
        frame.getContentPane().add(workspacePathField);
        workspacePathField.setColumns(10);
        
        JButton workspaceBrowse = new JButton("Browse");
        workspaceBrowse.setBounds(431, 118, 117, 29);
        frame.getContentPane().add(workspaceBrowse);
        
        JLabel timeoutlbl = new JLabel("Timeout (sec)");
        timeoutlbl.setHorizontalAlignment(SwingConstants.CENTER);
        timeoutlbl.setBounds(42, 159, 87, 30);
        frame.getContentPane().add(timeoutlbl);
        
        timeoutField = new JTextField();
        timeoutField.setBounds(141, 159, 130, 26);
        frame.getContentPane().add(timeoutField);
        timeoutField.setColumns(10);
        
        JLabel reportPathLbl = new JLabel("Report Path");
        reportPathLbl.setHorizontalAlignment(SwingConstants.CENTER);
        reportPathLbl.setBounds(316, 166, 104, 16);
        frame.getContentPane().add(reportPathLbl);
        
        reportPathField = new JTextField();
        reportPathField.setBounds(421, 159, 130, 26);
        frame.getContentPane().add(reportPathField);
        reportPathField.setColumns(10);
        
        JButton reportBrowse = new JButton("Browse");
        reportBrowse.setBounds(431, 187, 117, 29);
        frame.getContentPane().add(reportBrowse);
        
        JButton startButton = new JButton("START");
        startButton.setBounds(234, 237, 117, 29);
        frame.getContentPane().add(startButton);
        
        JScrollPane console = new JScrollPane();
        console.setBounds(33, 332, 523, 118);
        frame.getContentPane().add(console);
        
        frame.setEnabled(true);
        
    }    
    public void initialise()
    {
    	JLabel text=new JLabel("Downloading AutoTut");
        
        JLabel message=new JLabel();//Crating a JLabel for displaying the message
        
        frame = new JFrame();
        frame.setEnabled(true);
    	frame.setSize(600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.getContentPane().setLayout(null);
        text.setBounds(132, 124, 332, 40);
        text.setFont(new Font("arial",Font.BOLD,30));
        text.setForeground(Color.BLUE);
        text.setVisible(true);
        frame.getContentPane().add(text);
        
        message.setBounds(250, 320, 200, 40);
        message.setForeground(Color.black);//Setting foreground Color
        message.setFont(new Font("arial",Font.BOLD,15));//Setting font properties
        message.setVisible(true);
        frame.getContentPane().add(message);//adding label to the frame
        
        progressBar.setBounds(95, 175, 402, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        progressBar.setVisible(true);
        frame.getContentPane().add(progressBar);
        
        frame.setEnabled(true);
        
    }
    
    public static void runBar()
    {
    	int i = 0;
		while( i<=99)
        {
			  try{ RegressionGUI.progressBar.setValue(i);//Setting value of Progress Bar
			  RegressionGUI.message.setText("LOADING "+Integer.toString(i)+"%");//Setting text of theSplashScreen.message JLabel 
			  if (i==99)
				  longer.setVisible(true);
			  if(i==100) frame.dispose(); 
			  i++;
			  Thread.sleep(500);//Pausingexecution for 50 milliseconds 
			  }catch(Exception e){ e.printStackTrace(); }
        }
    }
    public static void stopBar()
    {
    	RegressionGUI.progressBar.setValue(100);
    	try {
    	}catch(Exception e){ e.printStackTrace(); }
    	 frame.dispose();
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
    public static void main(String[] args) {
    	RegressionGUI window = new RegressionGUI();
    	window.frame.setVisible(true);
        //window.runningPBar();			
int i=0;//Creating an integer variable and intializing it to 0
        runBar();
        while( i<=100)
        {
			/*
			 * try{ progressBar.setValue(i);//Setting value of Progress Bar
			 * message.setText("LOADING "+Integer.toString(i)+"%");//Setting text of the
			 * message JLabel i++; if(i==100) frame.dispose(); Thread.sleep(500);//Pausing
			 * execution for 50 milliseconds }catch(Exception e){ e.printStackTrace(); }
			 */
 
 
        }
	}
}