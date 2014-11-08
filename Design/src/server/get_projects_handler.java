package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.GetProjects_result;
import client_communicator.ValidateUser_Params;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class get_projects_handler implements HttpHandler {
	
	private XStream xmlStream = new XStream(new DomDriver());

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		
		GetProjects_result result = new GetProjects_result();
		try
		{
			result = faccade.GetProjects();
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
