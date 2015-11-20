package lifiReceiver;

public class TestLifi 
{

	public static void main(String[] args) 
	{
		try 
		{
			//Start lifi receiver on default port number
			LifiReceiver lifiReceiver = new LifiReceiver();
			lifiReceiver.start();
			
//			Analyzer analyzer = new Analyzer();
//			analyzer.readFile();
//			analyzer.analyze();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
