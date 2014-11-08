package db_access_test;

import java.sql.SQLException;

import client_communicator.Search_params;
import client_communicator.Search_result;
import db_access.database;
import db_access.db_access_values;
import design.Value;

public class values_test {

	private Value val;
	
	public values_test()
	{
		val = new Value();
		val.setActual("alec");
		val.setBatch_id(5);
		val.setField_id(3);
		val.setRecord_id(4);
	}
	
	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_values values = db.getValuesDBO();
		
		int get = values.create();
		db.endTransaction(true);
	}
	
	public void testAdd() throws Exception
	{
		database db = new database();
		db_access_values values = db.getValuesDBO();
		int c = values.add(val);
	}
	
	public void testSearch() throws ClassNotFoundException
	{
		database db = new database();
		db.startTransaction();
		Search_params params = new Search_params();
		String[] fields = new String[2];
		fields[0] = "2";
		
		params.setFields(fields);
		String[] values = new String[2];
		values[0] = "russell";
		
		params.setValues(values);
		try {
			Search_result result =  db.getValuesDBO().search(params);
			db.endTransaction(true);
			System.out.println(result.toString());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
	public void testUpdate() throws Exception
	{
		
	}
	
	public void testGetall() throws Exception
	{
		
	}
}
