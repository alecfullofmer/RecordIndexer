package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.GetFields_params;
import client_communicator.GetFields_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class get_fields_handler implements HttpHandler {

	private XStream xmlStream = new XStream(new DomDriver());
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		GetFields_params params = (GetFields_params)xmlStream.fromXML(exchange.getRequestBody());
		GetFields_result result = new GetFields_result();
		try
		{
			result = faccade.GetFields(params);
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
