import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;

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
	public static boolean exit;
	public static String handleSpace(String filePath)
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
	public static boolean fileWrite(String content, String path)
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
	public static String runAutoTUT(String targetPath, String workSpaceDetails)
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
	                	errorString += "\n [ERROR]"+s;
	            }
	            File file = new File(tutWrittenPath);
	            file.delete(); // Delete the tut file since not needed.
	          //  System.out.println("File at "+tutWrittenPath+" is deleted successfully");
		}
		catch(Exception e)
		{
			//e.getSuppressed();
			e.printStackTrace();
		}
		return errorString;
	}
	
	public static ArrayList<String> getFilesPathList(String workspacePath, String[] allowedProduct)
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
					}
				}
			}
		}
		return outputList;
	}
	public static void runRegression(String targetPath, String workspacePath, String[] allowedProduct, int timeoutMilliseconds, String reportPath)
	{
		ArrayList<String> fileList = getFilesPathList(workspacePath, allowedProduct);
		int count = 1;
		for(String filePath : fileList){
			System.out.println("Running AutoTUT for "+filePath);
			System.out.println("Remaining "+(fileList.size()-count));
			count++;
			Thread t1 = new Thread() {
				public void run() {
					String errorString = runAutoTUT(targetPath, filePath);
					File reportFile = new File(reportPath);
					
						try {
							if (!reportFile.exists())
								reportFile.createNewFile();
							//reportFile.createNewFile();
							ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(reportFile));
							objOut.writeChars(errorString+"\n");
							objOut.close();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
					
				}
			};
			t1.start();
			try {
				Thread.sleep(timeoutMilliseconds); // timeout
				ThreadStop.StopThread(t1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Target path  : ");
		String targetPath = scan.next();
		System.out.println("Workspace path  : ");
		String workspacePath = scan.next();
		System.out.println("Product (like AA, FL, EB). Enter it separated by commas : ");
		String product = scan.next();
		String [] allowedProduct = product.split(",");
		for(int i = 0; i<allowedProduct.length; i++)
			allowedProduct[i] = allowedProduct[i].trim();
		System.out.println("Timeout in milliseconds : ");
		int timeoutMilliseconds = scan.nextInt();
		System.out.println("Report generation path : ");
		String reportPath = scan.next();
		System.out.println("Starting regression");
		long start_time = System.nanoTime();
		runRegression(targetPath, workspacePath, allowedProduct, timeoutMilliseconds, reportPath);
		System.out.println("Regression stopped, total time taken = "+(System.nanoTime() - start_time)+ " seconds");
	}

}
