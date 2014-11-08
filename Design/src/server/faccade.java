package server;

import java.rmi.ServerException;
import java.sql.SQLException;

import client_communicator.DownloadBatch_params;
import client_communicator.DownloadBatch_result;
import client_communicator.GetFields_params;
import client_communicator.GetFields_result;
import client_communicator.GetProjects_result;
import client_communicator.GetSampleImage_result;
import client_communicator.Search_params;
import client_communicator.Search_result;
import client_communicator.SubmitBatch_params;
import client_communicator.SubmitBatch_result;
import client_communicator.ValidateUser_Params;
import client_communicator.ValidateUser_Result;
import db_access.database;

public class faccade {
	
	public static void initialize() throws ServerException 
	{		
		try 
		{
			database.initialize();		
		}
		catch (Exception e) 
		{
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static DownloadBatch_result DownloadBatch(DownloadBatch_params params) throws ClassNotFoundException, SQLException
	{
		database db = new database(); 
		
		try 
		{
			
			db.startTransaction();
			DownloadBatch_result result = db.getBatchDBO().downloadBatch(params);
			db.endTransaction(true);
			return result;
		}
		catch (SQLException | ClassNotFoundException e) 
		{
			db.endTransaction(false);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void DownloadFile()
	{
		
	}
	
	public static GetFields_result GetFields(GetFields_params params) throws SQLException, ClassNotFoundException
	{
		database db = new database();
		
		try
		{
			db.startTransaction();
			GetFields_result result = db.getFieldDBO().getAll(params.getProject_id());
			db.endTransaction(true);
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			db.endTransaction(false);
			return null;
		}
	}
	
	public static GetProjects_result GetProjects() throws SQLException, ClassNotFoundException
	{
		database db = new database(); 
		
		try 
		{
			
			db.startTransaction();
			GetProjects_result result = db.getProjectDBO().getAll();
			db.endTransaction(true);
			return result;
		}
		catch (SQLException | ClassNotFoundException e) 
		{
			db.endTransaction(false);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static GetSampleImage_result GetSampleImage(int project_id) throws SQLException, ClassNotFoundException 
	{
		database db = new database();
		
		try
		{
			
			db.startTransaction();
			String tempResult = db.getBatchDBO().getSampleImage(project_id);
			GetSampleImage_result result = new GetSampleImage_result();
			result.setString_url(tempResult);
			db.endTransaction(true);
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			db.endTransaction(false);
			return null;
		}
		
	}
	
	public static Search_result Search(Search_params params) throws ClassNotFoundException
	{
		database db = new database();
		
		try
		{
			db.startTransaction();
			Search_result result = db.getValuesDBO().search(params);
			db.endTransaction(true);
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static SubmitBatch_result SubmitBatch(SubmitBatch_params params) throws SQLException, ClassNotFoundException
	{
		database db = new database();
		
		try
		{
			db.startTransaction();
			SubmitBatch_result result = db.getBatchDBO().submitBatch(params);
			db.endTransaction(true);
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			db.endTransaction(false);
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.endTransaction(false);
			return null;
		}
		
	}
	
	public static ValidateUser_Result ValidateUser(ValidateUser_Params params) throws ClassNotFoundException, SQLException
	{
		
		database db = new database();
		
		try
		{
			db.startTransaction();
			ValidateUser_Result result = db.getUserDBO().validate(params);
			db.endTransaction(true);
			return result;
		}
		catch (SQLException e)
		{
			db.endTransaction(false);
			e.printStackTrace();
			
		}
		return null;
	}
}
