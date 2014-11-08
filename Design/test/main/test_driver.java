package main;

import java.sql.SQLException;

import servertester.controllers.Controller;
import db_access.database;
import db_access_test.batch_test;
import db_access_test.field_test;
import db_access_test.project_test;
import db_access_test.record_test;
import db_access_test.user_test;
import db_access_test.values_test;

public class test_driver {
	
	public static void main(String[] args) throws SQLException
	{
//		values_test val = new values_test();
//		val.testAdd();
		
		field_test field = new field_test();
		project_test project = new project_test();
		record_test record = new record_test();
		user_test user = new user_test();
		values_test values = new values_test();
		batch_test batch = new batch_test();
		Controller control = new Controller();
		
		
		
		try {
				database.initialize();
				batch.testSubmit();
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
