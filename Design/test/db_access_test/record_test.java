package db_access_test;

import java.sql.SQLException;

import db_access.database;
import db_access.db_access_project;
import db_access.db_access_record;
import design.Project;
import design.Record;

public class record_test {

	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_record record = db.getRecordDBO();
		
		int get = record.create();
		db.endTransaction(true);
	}
	
	public void testAdd() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_record record = db.getRecordDBO();
		Record rec = new Record();
		rec.setBatch_id(1);
		rec.setRecord_number(5);
		
		if(record.add(rec) > 0)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testUpdate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db_access_record record = db.getRecordDBO();
		Record rec = new Record();
		rec.setBatch_id(1);
		rec.setRecord_number(5);
		rec.setId(1);
		
		if(record.update(rec))
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testGetall() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db_access_record record = db.getRecordDBO();
		
		
		if(record.getAll(1) != null)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
}
