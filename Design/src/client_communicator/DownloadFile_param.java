package client_communicator;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFile_param {

	//params here is a URL??
	
	private URL url;
	
	public DownloadFile_param ()
	{}
	
	public URL getUrl()
	{
		return url;
	}
	
	public void setUrl(String myUrl)
	{
		try 
		{
			url = new URL(myUrl);
		} 
		catch (MalformedURLException e)
		{
			
			e.printStackTrace();
		}
	}
}
