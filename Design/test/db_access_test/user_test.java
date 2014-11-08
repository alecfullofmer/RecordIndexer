package db_access_test;

import java.sql.SQLException;

import client_communicator.ValidateUser_Params;
import client_communicator.ValidateUser_Result;
import db_access.database;
import db_access.db_access_user;
import design.user;

public class user_test {
	
	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_user user = db.getUserDBO();
		
		int get = user.create();
		db.endTransaction(true);
	}
	
	
	public void testAdd() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_user user = db.getUserDBO();
		user us = new user();
		us.setCurrentBatch(1);
		us.setEmail("E");
		us.setFirstname("f");
		us.setId(1);
		us.setLastname("l");
		us.setPassword("p");
		us.setRecordCount(0);
		us.setUsername("f");
		
		if(user.add(us) != -1)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testUpdate() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_user user = db.getUserDBO();
		user us = new user();
		us.setCurrentBatch(1);
		us.setEmail("email");
		us.setFirstname("first");
		us.setId(1);
		us.setLastname("last");
		us.setPassword("pass");
		us.setRecordCount(0);
		us.setUsername("face");
		
		if(user.update(us))
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testGetall() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_user user = db.getUserDBO();
		
		
		
		if(user.getAll() != null)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testValidate() throws ClassNotFoundException
	{
		database db = new database();
		db.initialize();
		db.startTransaction();
		ValidateUser_Params params = new ValidateUser_Params();
		ValidateUser_Result result = new ValidateUser_Result();
		params.setPassword("face");
		params.setUsername("frog");
		try {
			result = db.getUserDBO().validate(params);
			String ss;
			ss = result.getFirst() + " " + result.getLast() + " " + result.getRecords();
			System.out.println(ss);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
