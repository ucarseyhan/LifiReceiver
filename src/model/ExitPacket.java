package model;
/**
 * System Exit Data Packet
 * @author seyhan
 *
 */
public class ExitPacket  extends Packet 
{
	private static final long serialVersionUID = 1L;
	
	public ExitPacket()
	{
		super();
	}
	/**
	 * Specialized Exit Packet
	 * 
	 * @param transmitTime
	 * @param receiveTime
	 * @param sequenceNumber
	 * @param packetType
	 */
	public ExitPacket(long transmitTime, long receiveTime, int sequenceNumber,int packetType) 
	{
		super(transmitTime, receiveTime, sequenceNumber, packetType);
	}

	
}
