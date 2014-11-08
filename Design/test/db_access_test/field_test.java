package db_access_test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import db_access.database;
import db_access.db_access_batch;
import db_access.db_access_field;
import db_access.db_access_values;
import design.field;

public class field_test {
	
	@Before
	public void setUp() {
		
		
	}

	@After
	public void tearDown() {
		return;
	}
	
	public void testAdd() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_field field = db.getFieldDBO();
		field fi = new field();
		fi.setHelpHtml("html");
		fi.setKnowndata("data");
		fi.setProject_id(1);
		fi.setTitle("title");
		fi.setWidth(10);
		fi.setXcord(5);
		
		if(field.add(fi) > 0)
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
		db_access_field field = db.getFieldDBO();
		field fi = new field();
		fi.setHelpHtml("html");
		fi.setKnowndata("data");
		fi.setProject_id(1);
		fi.setTitle("title");
		fi.setWidth(10);
		fi.setXcord(5);
		fi.setId(1);
		
		if(field.update(fi))
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_field field = db.getFieldDBO();
		
		int get = field.create();
		db.endTransaction(true);
	}
	
	public void testGetall() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_field field = db.getFieldDBO();
		
		
		if(field.getAll(1) != null)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}

}
