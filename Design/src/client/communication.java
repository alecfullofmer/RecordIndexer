package client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import client_communicator.DownloadBatch_params;
import client_communicator.DownloadBatch_result;
import client_communicator.GetFields_params;
import client_communicator.GetFields_result;
import client_communicator.GetProjects_result;
import client_communicator.GetSampleImage_params;
import client_communicator.GetSampleImage_result;
import client_communicator.Search_params;
import client_communicator.Search_result;
import client_communicator.SubmitBatch_params;
import client_communicator.SubmitBatch_result;
import client_communicator.ValidateUser_Params;
import client_communicator.ValidateUser_Result;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class communication {

	private String SERVER_HOST;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private String SERVER_PORT;
	private String URL_PREFIX;
	
	private XStream xmlStream;
	
	//get and set port and host values
	public communication(String host, String port)
	{
		xmlStream = new XStream(new DomDriver());
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	public ValidateUser_Result ValidateUser(ValidateUser_Params params)
	{
		return  (ValidateUser_Result)doPost("/ValidateUser", params);
	}
	
	public GetProjects_result GetProjects()
	{
		Object filler = null; //something to fill in the doPost param
		return (GetProjects_result)doPost("/GetProjects", filler);
	}
	
	public GetSampleImage_result GetSampleImage(GetSampleImage_params params)
	{
		return (GetSampleImage_result)doPost("/GetSampleImage", params);
	}
	
	public DownloadBatch_result DownloadBatch(DownloadBatch_params params)
	{
		return (DownloadBatch_result)doPost("/DownloadBatch", params);
	}
	
	public SubmitBatch_result SubmitBatch(SubmitBatch_params params)
	{
		return (SubmitBatch_result)doPost("/SubmitBatch", params);
	}
	
	public GetFields_result GetFields(GetFields_params params)
	{
		return (GetFields_result)doPost("/GetFields", params);
	}
	
	public Search_result Search(Search_params params)
	{
		return (Search_result)doPost("/Search", params);
	}
	//
	


	
	public Object doPost(String urlPath, Object postData)
	{
		try
		{
			URL url = new URL(URL_PREFIX +urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else
			{
				return null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
