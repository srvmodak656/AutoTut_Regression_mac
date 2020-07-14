import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import jdk.javadoc.doclet.Reporter;

/*
 * This is the java module to run regression and is a multithreaded module.
 * It is advised to be cautious about the code push since if now monitored 
 * properly then the code which is pushed can crash the process management of 
 * the operating system and can crash it. Also there is a possibility that 
 * a not code which is written casually can mess with the other program which 
 * is running in java jvm. So it is mandated to check it thoroughly with the 
 * architect or author of this module before pushing it to the build.
 * 
 * AUTHOR :- Sourav Modak
 * Date :- 1st July 2020
 * INDIA
 * 
 */
public class Regression {
	public static long start_time; 
	private static boolean exit;
	public static boolean stopFlag = false;
	public static int processFlag = 0; // 0 for CUI and 1 for GUI
	public static String reportPathFull = "";
	
	private static String handleSpace(String filePath)
	
	{
		StringBuilder strBuild = new StringBuilder(filePath);
		for (int i = 0; i<strBuild.length(); i++)
		{
			if(strBuild.charAt(i) == ' ')
			{
				strBuild.deleteCharAt(i);
				strBuild.replace(i, i, "|");
			}
		}
		return strBuild.toString();
	}
	private static boolean fileWrite(String content, String path)
	{
		File file = new File(path);
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
			objOut.writeObject(content);
			objOut.close();
			return true;
		}
		catch(Exception e1)
		{
			System.out.println(e1.getMessage());
			return false;
		}
		
	}
	private static Process runAutoTUT(String targetPath, String workSpaceDetails, String reportPath)
	{
		//ArrayList<String> workSpaceDetails= new ArrayList<String>();	////CHANGE FOR ACCESS
		String s = null;
		String errorString = null;
		String tutWrittenPath = "";
		try {
			Process p = Runtime.getRuntime().exec("python3 "+targetPath+File.separator+"writeTut.py "+workSpaceDetails);
			BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
	            		
	            while ((s = stdError.readLine()) != null) {
	                //System.out.println(s);
	                
	                if (s.startsWith("TUT file written in "))
	                {
	                	tutWrittenPath = s.split("TUT file written in ")[1];
	                }
	                else if (!s.contains("Done") && !s.startsWith("TUT file written in "))
	                	errorString += "\n [ERROR]"+" {"+workSpaceDetails+" "+s;
	                
	            }
	            // Record the report 
	            File reportFile = new File(reportPath);
        		
                try { 
                	if(!reportFile.exists())
                		reportFile.createNewFile();
                    BufferedWriter out = new BufferedWriter(new FileWriter(reportFile, true)); 
                    if(errorString!=null || !errorString.isEmpty())
                    	out.write(errorString+"\n"); 
                    out.close(); 
                } 
                catch (IOException e) { 
                    System.out.println("exception occoured" + e); 
                    if (processFlag == 1) 
                    	RegressionGUI.detailedLogText.append("exception occoured" + e + "\n"); 
                } 
                catch(NullPointerException e)
                {
                	e.getSuppressed();
                }
                
	            File file = new File(tutWrittenPath);
	            
	            file.delete(); // Delete the tut file since not needed.
	          //  System.out.println("File at "+tutWrittenPath+" is deleted successfully");
	            p.destroy();
	            return p;
		}
		catch(Exception e)
		{
			//e.getSuppressed();
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static ArrayList<String> getFilesPathList(String workspacePath, List<String> allowedProduct)
	{
		ArrayList <String> outputList = new ArrayList<String>();
		for(String product : allowedProduct) 
		{
			String targetPath = workspacePath + File.separator + product;
			String[] listOfFile = new File(targetPath).list();
			for(String folderName : listOfFile)
			{
				String targetFolderPath = targetPath + File.separator + folderName;
				if (folderName.startsWith(product))
				{
					String fullPath = targetFolderPath + File.separator + "Source" + File.separator + "Private";
					try
					{
						String [] filesInside = new File(fullPath).list();
						for(String file : filesInside)
						{
							String fileFullPath = fullPath + File.separator + file;
							outputList.add(fileFullPath);
						}
						
					}
					catch(Exception e)
					{
						e.getSuppressed();
						System.out.println("No .b files found in "+folderName);
						if (processFlag == 1) 
							RegressionGUI.detailedLogText.append("No .b files found in "+folderName+"\n");
					}
				}
			}
		}
		return outputList;
	}
	
	public static Thread timer = new Thread();
	
	public static Thread process = new Thread();
	
	public static String getTime(long timeInSecs)
	{
		String output = "";
		int mins = (int) (timeInSecs/60); // to convert into seconds
		int secs = (int) (timeInSecs%60);
		int hrs = (int) mins/60;
		mins = (int) mins%60;
		output = hrs+" hrs "+mins+" mins "+secs+" seconds";
		return output;
	}
	
	public static void runRegression(String targetPath, String workspacePath, List<String> allowedProduct, int timeoutMilliseconds, String reportPath)
	{
		ArrayList<String> fileList = getFilesPathList(workspacePath, allowedProduct);
		int count = 1;
		stopFlag = false;
		for(String filePath : fileList)
		{
			if(stopFlag)
			{
				System.out.println("Regression Terminated by user");
				System.out.println("Regression stopped, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000)+ "");
				if (processFlag == 1) 
				{
					RegressionGUI.consoleText.append("Regression Terminated by user");
					RegressionGUI.detailedLogText.append("Regression Terminated by user\n");
					RegressionGUI.detailedLogText.append("Regression stopped, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000)+ " \n");
					RegressionGUI.progressBar.setValue(0);
					RegressionGUI.progressBar1.setValue(0);
					RegressionGUI.timeRemaining.setText("");
					RegressionGUI.timeRemaining1.setText("");
				}
				break;
			}
			System.out.println("Running AutoTUT for "+filePath);
			if (processFlag == 1) 
			{
				RegressionGUI.consoleText.append("Running AutoTUT for "+filePath+"\n");
				RegressionGUI.detailedLogText.append("Running AutoTUT for "+filePath+"\n");
			}
			
			long timeRemaining = (fileList.size()-count)*(timeoutMilliseconds/1000);
			System.out.println("Remaining "+(fileList.size()-count));
			if (processFlag == 1) 
			{
				int progress = (int)(((double)count/(double)fileList.size())*100);
				
				RegressionGUI.progressBar.setValue(progress);
				RegressionGUI.progressBar1.setValue(progress);
				RegressionGUI.timeRemaining.setText("Remaining time approx : "+getTime(timeRemaining));
				RegressionGUI.timeRemaining1.setText("Remaining time approx : "+getTime(timeRemaining));
				RegressionGUI.detailedLogText.append("Remaining time approx : "+getTime(timeRemaining)+"\n");
				RegressionGUI.detailedLogText.append("Remaining "+(fileList.size()-count)+"\n");
			}
			
			System.out.println("Remaining time approx : "+getTime(timeRemaining));
			count++;
			timer = new Thread()  {
				
				public void run() {
					try {
						Thread.sleep(timeoutMilliseconds); // timeout
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			};
			timer.start();
			process = new Thread() {
				public void run() {
					Process p = runAutoTUT(targetPath, filePath, reportPath);
					p.destroy();
					p.destroyForcibly();
				}
			};
			process.start();
			while(true)
			{
				if(!timer.isAlive())	// if timer is over
				{
					ThreadStop.StopThread(process);
					
					try {
						Process p = Runtime.getRuntime().exec("pkill -f python");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
				}
				if(!process.isAlive())
				{
					ThreadStop.StopThread(timer);
					
					try {
						Process p = Runtime.getRuntime().exec("pkill -f python");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
					
				}
			}
			if (count%10 == 0)
			{
				try {
					Process p = Runtime.getRuntime().exec("pkill -f python");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void startRegressionCUI() 
	{
		Scanner scan = new Scanner(System.in);
		int ch = 0;
		String targetPath, workspacePath, reportPath, product;
		List<String>allowedProduct = new ArrayList<String>();
		int timeoutMilliseconds;
		
		File tempDetailsFile = new File("temp/temp.txt");
		List<Object> tempDetails = new ArrayList<>();
		
		if (tempDetailsFile.exists())
		{
			tempDetails = RegressionGUI.fetchTempRegressionSettingDetails();
			if (!tempDetails.contains(null))
			{
				System.out.println("Continue with temp record?");
				System.out.println("Target Path : "+tempDetails.get(0));
				System.out.println("Workspace Path : "+tempDetails.get(1));
				System.out.println("Timeout in milliseconds : "+tempDetails.get(2));
				System.out.println("Product : "+tempDetails.get(3));
				System.out.println("Report Path : "+tempDetails.get(4));
				ch = scan.nextInt();
			}
		}
		else
			try {
				tempDetailsFile.createNewFile();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		if (ch == 0)
		{
			System.out.println("Target path  : ");
			targetPath = scan.next();
			System.out.println("Workspace path  : ");
			workspacePath = scan.next();
			System.out.println("Product (like AA, FL, EB). Enter it separated by commas : ");
			product = scan.next();
			String [] temp = product.split(",");
			try {
				for(int i = 0; i<temp.length; i++)
					allowedProduct.add(temp[i]);
					
				for(int i = 0; i<allowedProduct.size(); i++)
					allowedProduct.set(i, allowedProduct.get(i).trim());
			}
			catch(Exception e)
			{
				e.getSuppressed();
			}
			System.out.println("Timeout in milliseconds : ");
			timeoutMilliseconds = scan.nextInt();
			while(true)
			{
				System.out.println("Report generation path : ");
				reportPath = scan.next();
				if (new File(reportPath).isDirectory())
				{
					System.out.println("Starting regression");
					if (processFlag == 1) 
					{
						RegressionGUI.consoleText.append("Starting regression\n");
						RegressionGUI.detailedLogText.append("Starting regression\n");
					}
					start_time = System.nanoTime();
					RegressionGUI.recordTempRegressionSettingsDetails(targetPath, workspacePath, timeoutMilliseconds, allowedProduct, reportPath);
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Date date = new Date();
					reportPathFull = reportPath+File.separator+"report"+formatter.format(date)+".txt";
					runRegression(targetPath, workspacePath, allowedProduct, timeoutMilliseconds, reportPathFull);
					
					if (!stopFlag)
					{
						System.out.println("Regression Completed, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000));
						if (processFlag == 1) 
							RegressionGUI.detailedLogText.append("Regression Completed, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000));
					}
					
					break;
				}
				else
				{
					System.out.println("Report generation path is not a directory try again");
				}
			}
		}
		else if (ch == 1)
		{
			targetPath = tempDetails.get(0).toString();
			workspacePath = tempDetails.get(1).toString();
			timeoutMilliseconds = Integer.parseInt(tempDetails.get(2).toString());
			reportPath = tempDetails.get(4).toString();
			allowedProduct = (List<String>) tempDetails.get(3);
			while(true)
			{
				if (new File(reportPath).isDirectory())
				{
					System.out.println("Starting regression");
					if (processFlag == 1) 
					{
						RegressionGUI.consoleText.append("Starting regression\n");
						RegressionGUI.detailedLogText.append("Starting regression\n");
					}
					start_time = System.nanoTime();
					RegressionGUI.recordTempRegressionSettingsDetails(targetPath, workspacePath, timeoutMilliseconds, allowedProduct, reportPath);
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Date date = new Date();
					reportPathFull = reportPath+File.separator+"report"+formatter.format(date)+".txt";
					runRegression(targetPath, workspacePath, allowedProduct, timeoutMilliseconds, reportPathFull);
					
					if (!stopFlag)
					{
						System.out.println("Regression Completed, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000));
						if (processFlag == 1) 
							RegressionGUI.detailedLogText.append("Regression Completed, total time taken = "+getTime((System.nanoTime() - start_time)/1000000000));
					}
					break;
				}
				else
				{
					System.out.println("Report generation path is not a directory try again");
					System.out.println("Report generation path : ");
					reportPath = scan.next();
				}
			}
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		startRegressionCUI();
	}

}
