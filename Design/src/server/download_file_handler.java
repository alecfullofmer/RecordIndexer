package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import db_access.database;


public class download_file_handler implements HttpHandler {


	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		
		try {
			
			
			File file = new File(new File("").getAbsolutePath() + File.separator + exchange.getRequestURI().getPath());
			exchange.sendResponseHeaders(200, 0);
			Path filepath = Paths.get(file.getAbsolutePath());
			Files.copy(filepath, exchange.getResponseBody());
			exchange.getResponseBody().close();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
		
		
		
		
		
	}

}
