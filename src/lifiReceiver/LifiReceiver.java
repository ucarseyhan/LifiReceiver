package lifiReceiver;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;

import model.ExitPacket;
import model.HelloPacket;
import model.LifiCTLStatistics;
import model.NTPDate;
import model.Packet;
/*****************************************************************************
 * LifiReceiver is the code that receiver connected PC should run.
 * The Desktop Unit (DU) is waiting data from Ceiling Unit via specified
 * port number. When the data is received then necessary actions are taken such
 * as decoding the packet and writing the data to 
 * 
 * @author seyhan
 *
 *******************************************************************************/
public class LifiReceiver 
{
	private int portNumber = 5004;
	
	public LifiReceiver()
	{
	}
	
	public LifiReceiver(int port)
	{
		this.portNumber = port;
	}

	/**
	 * @return the portNumber
	 */
	public int getPortNumber() 
	{
		return portNumber;
	}

	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(int portNumber) 
	{
		this.portNumber = portNumber;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public void start()
	{
		byte[] incomingData = new byte[1024];
		try 
		{
			DatagramSocket socket = new DatagramSocket(portNumber);
			//System.out.println("Server is starting on port number:"+portNumber);
			
			//Create the Lifi Receiver statistics
			LifiCTLStatistics lifiCTLStatistics = new LifiCTLStatistics();
			Timer timer = new Timer(true);
			//Start the timer
			//task,delay,interval
			timer.scheduleAtFixedRate(lifiCTLStatistics, 100, 2 * 1000);
			while(true)
			{
				
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				Packet packet = (Packet)is.readObject();
				decodePacket(packet);
				if(packet instanceof ExitPacket) break;
			}
			System.out.println("Shut Down Server");
			//Cancel the timer
			timer.cancel();
			//Close the socket.
			socket.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/************************************************************
	 * decodePacket is used for decoding packet content and getting 
	 * the packet informations such that;
	 * -packet sequence number
	 * -packet transmit time
	 * packet receive time
	 * 
	 * @param packet
	 ************************************************************/
	private void decodePacket(Packet packet)
	{
		try 
		{
			if(packet != null)
			{
				//Received packet is Hello Packet
				if(packet instanceof HelloPacket)
				{
					HelloPacket helloPacket = (HelloPacket)packet;
					
					/////////////////////////////////////////////////////////////////////////
					//Get center PC date
					//Disable on CENTER PC !!! IMPORTANT
					long centerPCDate = new NTPDate().getNTPDate();
					helloPacket.setReceiveTime(centerPCDate);
					/////////////////////////////////////////////////////////////////////////
					
					
					//helloPacket.setReceiveTime(System.currentTimeMillis());
					System.out.println(helloPacket.getSequenceNumber()+" "
					+helloPacket.getTransmitTime()+" "+helloPacket.getReceiveTime());
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
}
