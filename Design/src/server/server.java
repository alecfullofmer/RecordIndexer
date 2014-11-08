package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.ServerException;


import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class server {

	private int port;
	private HttpServer server;
	private HttpHandler download_batch_handler = new download_batch_handler();
	private HttpHandler download_file_handler = new download_file_handler();
	private HttpHandler get_fields_handler = new get_fields_handler();
	private HttpHandler get_projects_handler = new get_projects_handler();
	private HttpHandler get_sample_image_handler = new get_sample_image_handler();
	private HttpHandler search_handler = new search_handler();
	private HttpHandler submit_batch_handler = new submit_batch_handler();
	private HttpHandler validate_user_handler = new validate_user_handler();
	
	public server(int port)
	{
		this.port = port;
	}
	
	public static void main(String[] args)
	{
		int port = 8080;
		
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				// Ignore
				
			}
		}
		
		if (port >= 0 && port <= 65535) {
			server server = new server(port);
			server.run();
		}
		else {
			System.out.println("USAGE: java Server <port>");
		}
	}
	
	public void run()
	{
			try
			{
				faccade.initialize();
			}
			catch (ServerException e)
			{
				e.printStackTrace();
			}
			
			try {
				server = HttpServer.create(new InetSocketAddress(port), 10);
			} 
			catch (IOException e) {
							
				e.printStackTrace();
				return;
			}
			
			server.setExecutor(null);
			
			server.createContext("/ValidateUser", validate_user_handler);
			server.createContext("/GetProjects", get_projects_handler);
			server.createContext("/GetSampleImage", get_sample_image_handler);
			server.createContext("/DownloadBatch", download_batch_handler);
			server.createContext("/SubmitBatch", submit_batch_handler);
			server.createContext("/GetFields", get_fields_handler);
			server.createContext("/Search", search_handler);
			server.createContext("/", download_file_handler);
			
			
			server.start();
			
			
	}
}
