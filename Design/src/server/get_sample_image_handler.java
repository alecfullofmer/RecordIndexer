package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.GetSampleImage_params;
import client_communicator.GetSampleImage_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class get_sample_image_handler implements HttpHandler {

	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		GetSampleImage_params params = (GetSampleImage_params)xmlStream.fromXML(exchange.getRequestBody());
		int id = params.getProject_id();
		GetSampleImage_result result = new GetSampleImage_result();
		try
		{
			result = faccade.GetSampleImage(id);
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
