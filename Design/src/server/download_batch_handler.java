package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import client_communicator.DownloadBatch_params;
import client_communicator.DownloadBatch_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class download_batch_handler implements HttpHandler {

	private XStream xmlStream = new XStream(new DomDriver());
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		DownloadBatch_params params  = (DownloadBatch_params)xmlStream.fromXML(exchange.getRequestBody());
		DownloadBatch_result result = new DownloadBatch_result();
		try
		{
			result = faccade.DownloadBatch(params);
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
