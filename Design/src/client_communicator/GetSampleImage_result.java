package client_communicator;

import java.net.MalformedURLException;
import java.net.URL;

public class GetSampleImage_result {
	
	private URL url;
	private String string_url;
	private String host;
	private int port;
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public GetSampleImage_result(){}
	
	public String getString_url() {
		return string_url;
	}

	public void setString_url(String string_url) {
		this.string_url = string_url;
	}

	
	
	public URL getUrl()
	{
		return url;
	}
	
	public String setUrl(String image_url)
	{
		if(image_url == null)
		{
			return "FAILED";
		}
		else
		{
		String ss = "http://" + host + ":" + port + "/Records/" + image_url;
		return ss;
		}
	
		
	}
	
	public String toString()
	{
		return setUrl(string_url);
	}

}
