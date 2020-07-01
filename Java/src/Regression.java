import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.*;
import java.util.ArrayList;
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
	public static void runAutoTUT(String targetPath, String workSpaceDetails)
	{
		//ArrayList<String> workSpaceDetails= new ArrayList<String>();	////CHANGE FOR ACCESS
		System.out.println(workSpaceDetails);
		System.out.println(fileWrite(handleSpace(workSpaceDetails), FilePath.tempFilePath));
		System.out.println("To Python - "+workSpaceDetails);
		String s = null;
		String tutWrittenPath = "";
		try {
			Process p = Runtime.getRuntime().exec("python3 "+targetPath+File.separator+"writeTut.py "+workSpaceDetails);
			BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));

	            // read the output from the command
	            System.out.println("Here is the standard output of the command:\n");
	            while ((s = stdInput.readLine()) != null) {
	                System.out.println(s);
	            }
	            
	            // read any errors from the attempted command
	            System.out.println("Here is the standard error of the command (if any):\n");
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	                if (s.startsWith("TUT file written in "))
	                {
	                	tutWrittenPath = s.split("TUT file written in ")[1];
	                }
	            }
	            File file = new File(tutWrittenPath);
	            file.delete(); // Delete the tut file since not needed.
	            System.out.println("File at "+tutWrittenPath+" is deleted successfully");
		}
		catch(Exception e)
		{
			//e.getSuppressed();
			e.printStackTrace();
		}
	}
	
	public static class runnableThread implements Runnable {
		
		   String targetPath, workspacePath;
		   public runnableThread(String targetPath, String workspacePath) {
		       this.targetPath = targetPath;
		       this.workspacePath = workspacePath;
		   }

		   public void run() {
			   runAutoTUT(targetPath, workspacePath);
		   }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String targetPath = "/Users/souravmodak/Desktop/Final/python_src";
		String workspacePath = "/Users/souravmodak/Desktop/Dev_Workspace/AA/AA_PricingGrid/Source/Private/AA.PRICING.GRID.UPDATE.b";
		
		Runnable r1 = new runnableThread(targetPath, workspacePath);
		exit = false;
		Thread t1 = new Thread(r1);
		t1.start();
		try {
			Thread.sleep(1000);
			t1.stop();	// no other possibilty unless we use this deprecated function
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}

}
