package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.Search_params;
import client_communicator.Search_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class search_handler implements HttpHandler {
	
	private XStream xmlStream = new XStream(new DomDriver());
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		Search_params params = (Search_params)xmlStream.fromXML(exchange.getRequestBody());
		Search_result result = new Search_result();
		try
		{
			result = faccade.Search(params);
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
