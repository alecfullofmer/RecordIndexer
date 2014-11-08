package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.ValidateUser_Params;
import client_communicator.ValidateUser_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class validate_user_handler implements HttpHandler {
	
	private XStream xmlStream = new XStream(new DomDriver());

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
		ValidateUser_Result result = new ValidateUser_Result();
		try
		{
			result = faccade.ValidateUser(params);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
		
		
	}
	

}
