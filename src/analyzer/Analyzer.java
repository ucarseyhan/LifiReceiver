package analyzer;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import model.Constant;
import model.HelloPacket;
/**
 * Analyzer is used for analyzing the collected statistics
 * Given file is read and correlated statistics are analyzed
 * and necessary outputs are shown in console.
 * 
 * @author seyhan
 *
 */
public class Analyzer 
{
	//Attributes
	public static final String fileLocation = "output/indoor.txt";
	private ArrayList<HelloPacket> systemHelloPacket = new ArrayList<HelloPacket>();
	
	/**
	 * Default Constructor
	 */
	public Analyzer()
	{
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Read the file  and parse the file line by line
	 */
	public void readFile()
	{
		try 
		{
			File file = new File(fileLocation);
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) 
			{
				String line = sc.nextLine();
				if(line.length()<= 0) break;
				String[] parsedData = line.split("\\s+");
				int sequenceNumber = Integer.parseInt(parsedData[0]);
				long transmitTime = Long.parseLong(parsedData[1]);
				long receiveTime = Long.parseLong(parsedData[2]);
				HelloPacket h = new HelloPacket(transmitTime, receiveTime, sequenceNumber, Constant.HELLO_PACKET);
				systemHelloPacket.add(h);
				System.out.println(line);
			}
			sc.close();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Analyze the result and show the output on console.
	 */
	public void analyze()
	{
		long overallDelay = 0;
		long maxDelay = Long.MIN_VALUE;
		try 
		{
			for(HelloPacket helloPacket : systemHelloPacket)
			{
				long difference =  helloPacket.getReceiveTime() - helloPacket.getTransmitTime();
				overallDelay += difference;
				if(difference > maxDelay) maxDelay = difference;
			}
			System.out.println("AverageDelay:"+(overallDelay/(double)Constant.NUMBER_OF_PACKET) / systemHelloPacket.size()+" second.");
			System.out.println("MaxDelay:"+maxDelay / (double)Constant.NUMBER_OF_PACKET);
			System.out.println("Throughput:" +(systemHelloPacket.size() * 100.0) / Constant.NUMBER_OF_PACKET);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
