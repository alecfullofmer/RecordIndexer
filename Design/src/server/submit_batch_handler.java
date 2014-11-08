package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.SubmitBatch_params;
import client_communicator.SubmitBatch_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class submit_batch_handler implements HttpHandler {

	private XStream xmlStream = new XStream(new DomDriver());
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		SubmitBatch_params params = (SubmitBatch_params)xmlStream.fromXML(exchange.getRequestBody());
		SubmitBatch_result result = new SubmitBatch_result();
		try
		{
			result = faccade.SubmitBatch(params);
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
