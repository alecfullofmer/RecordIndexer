package servertester.controllers;

import java.util.ArrayList;

import servertester.views.IView;
import client.communication;
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

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result != null)
		{
			if(result.getInvalid() == null)
			getView().setResponse(result.toString());
			else
				getView().setResponse("FALSE");
			
		}
		else
			getView().setResponse("FAILED");
		
	}
	
	private void getProjects() {
		
		
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			
			getView().setResponse("FAILED");
		}
		else
		{
			GetProjects_result resultList = comm.GetProjects();
			getView().setResponse(resultList.toString());
		}
	}
	
	private void getSampleImage() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			getView().setResponse("FAILED");
		}
		else
		{
			GetSampleImage_params sample = new GetSampleImage_params();
			int id = Integer.parseInt(params[2]);
			sample.setProject_id(id);
			GetSampleImage_result url = comm.GetSampleImage(sample);
			url.setHost(host);
			int portInt = Integer.parseInt(port);
			url.setPort(portInt);
			getView().setResponse(url.toString());
		}
		
	}
	
	private void downloadBatch() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			getView().setResponse("FAILED");
			
		}
		else
			
		{
			DownloadBatch_params send = new DownloadBatch_params();
			send.setUser(params[0]);
			send.setPassword(params[1]);
			send.setProject_id(Integer.parseInt(params[2]));
			DownloadBatch_result results = comm.DownloadBatch(send);
			System.out.println("got here");
			if(results != null)
			{
				
				results.setHost(host);
				int portInt = Integer.parseInt(port);
				results.setPort(portInt);
				getView().setResponse(results.toString());
				
			}
			else
				getView().setResponse("FAILED");
			
		}
	}
	
	private void getFields() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			getView().setResponse("FAILED");
		}
		else
		{
			String id = params[2];
			int project_id;
			if(id == "")
			{
				project_id = 0;
			}
			else
			{
				project_id = Integer.parseInt(id);
			}
			GetFields_params send = new GetFields_params();
			send.setProject_id(project_id);
			GetFields_result recieve = comm.GetFields(send);
			if(recieve != null)
			{
				getView().setResponse(recieve.toString());
			}
			else
				getView().setResponse("FAILED");
				
		}
	}
	
	private void submitBatch() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			
			getView().setResponse("FAILED");
			
		}
		else
		{
			SubmitBatch_params send = new SubmitBatch_params();
			send.setUsername(params[0]);
			send.setPassword(params[1]);
			send.setBatch(1);
			String rec = params[3];
			String[] records = rec.split(";");
			System.out.println(records.length);
			send.setRecords(records);
			SubmitBatch_result recieve = comm.SubmitBatch(send);
			if(recieve != null)
				getView().setResponse("TRUE");
			else
				getView().setResponse("FAILED");
		}
		
	}
	
	private void search() {
		
		String[] params = getView().getParameterValues();
		ValidateUser_Params param = new ValidateUser_Params();
		param.setUsername(params[0]);
		param.setPassword(params[1]);
		String host = getView().getHost();
		String port = getView().getPort();
		communication comm = new communication(host, port);
		ValidateUser_Result result = new ValidateUser_Result();
		result = comm.ValidateUser(param);
		if(result == null || result.getInvalid() != null)
		{
			getView().setResponse("FAILED");
		}
		else
		{
			Search_params send = new Search_params();
			String[] fields = params[2].split(",");
			String[] values = params[3].split(",");
			send.setFields(fields);
			send.setValues(values);
			Search_result recieve = comm.Search(send);
			if(recieve != null)
			{
				if(recieve.isEmpty() == false)
				{
					recieve.setHost(host);
					recieve.setPort(port);
					getView().setResponse(recieve.toString());
				}
				else
					getView().setResponse("");
			}
			else
				getView().setResponse("FAILED");
		}
	}

}

