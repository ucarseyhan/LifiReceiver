package lifiReceiver;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import model.ExitPacket;
import model.HelloPacket;
import model.Packet;

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
			socket.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
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
					helloPacket.setReceiveTime(System.currentTimeMillis());
					System.out.println(helloPacket.getSequenceNumber()+" "+helloPacket.getTransmitTime()+" "+helloPacket.getReceiveTime());
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
}
