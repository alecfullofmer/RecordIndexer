package db_access_test;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import client_communicator.DownloadBatch_params;
import client_communicator.DownloadBatch_result;
import client_communicator.GetSampleImage_result;
import client_communicator.SubmitBatch_params;
import client_communicator.SubmitBatch_result;
import db_access.database;
import db_access.db_access_batch;
import design.Batch;


public class batch_test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SubmitBatch_result expected = new SubmitBatch_result();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	
	@Test
	public void testSubmit() throws ClassNotFoundException
	{
		database db = new database();
		db.startTransaction();
		String[] records = new String[1];
		records[0] = "alec";
		
		SubmitBatch_params params = new SubmitBatch_params();
		params.setBatch(6);
		params.setUsername("test1");
		params.setPassword("test1");
		params.setRecords(records);
		expected.
		
		try
		{
			db.getBatchDBO().submitBatch(params);
			db.endTransaction(true);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDownloadBatch() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		String username = "test1";
		String password = "test1";
		int id = 1;
		DownloadBatch_params params = new DownloadBatch_params();
		params.setPassword(password);
		params.setUser(username);
		params.setProject_id(id);
		try
		{
			DownloadBatch_result result = new DownloadBatch_result();
			result = db.getBatchDBO().downloadBatch(params);
			System.out.println(result.toString());
			db.endTransaction(true);
		}
		catch (SQLException e)
		{
			db.endTransaction(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetImage() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		try {
			String result = db.getBatchDBO().getSampleImage(1);
			GetSampleImage_result get = new GetSampleImage_result();
			get.setUrl(result);
			System.out.println(get.toString());
			db.endTransaction(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_batch batch = db.getBatchDBO();
		if(batch.create())
		{
			db.endTransaction(true);
		}
		else
			db.endTransaction(false);
	}
	
	@Test
	public void testAdd() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_batch batch = db.getBatchDBO();
		Batch bat = new Batch();
		bat.setImageFilePath("hey");
		bat.setIndexed(false);
		bat.setProject_id(1);
		
		batch.add(bat); 
		
			db.endTransaction(true);
		
//		else
//		{
//			db.endTransaction(false);
//		}
	}
	
	@Test
	public void testUpdate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db_access_batch batch = db.getBatchDBO();
		Batch bat = new Batch();
		bat.setImageFilePath("hey");
		bat.setIndexed(false);
		bat.setProject_id(1);
		bat.setId(1);
		if(batch.update(bat))
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	@Test
	public void testGetall() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db_access_batch batch = db.getBatchDBO();
		
		
		if(batch.getAll(1) != null)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
}
