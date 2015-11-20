package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.TimerTask;
/**
 * LifiCTLStatistics class is used for collecting the statistics from 
 * connected device. The device must directly connected to this computer. 
 * LifiCTLStatistics is timer task and periodically run  and collect
 * information from receiver unit namely Desktop Unit.
 * 
 * @author seyhan
 *
 */
public class LifiCTLStatistics extends TimerTask
{
	
	/**
	 * Periodic task that will run by the system
	 */
	@Override
	public void run() 
	{
		try 
		{
			/**
			 * Important point to mention
			 */
			/**Absolute path is needed.*/ //For error: 2 no file found exception.
			/**If any chmod u+x lifictl*/ //For error: 13 permission denied.
			/**-s, --get-statistics       get stats */
			String[] command = new String[]{"/home/seyhan/workspaceEE/Receiver/lifictl","-s", Constant.DEVICE_IP};
			Runtime rt = Runtime.getRuntime();
			Process process = rt.exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			//Get the data from command and write into 
			try 
			{
				while ((line = br.readLine()) != null) 
				{
					writeDeviceStatisticsToFile(line);
				}
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			br.close();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/***********************************************************
	 * writeDeviceStatisticsToFile is used for writing device
	 * statistics to output file. The device statistics are 
	 * retrieved from connected device.
	 * 
	 * @param line
	 **********************************************************/
	public void writeDeviceStatisticsToFile(String line)
	{
		try 
		{
			String fileName = "statistics.txt";
			File outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile,true);
			fileWriter.write(line+"\r\n");
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
