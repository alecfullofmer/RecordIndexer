package db_access_test;

import org.junit.*;

import db_access.database;
import static org.junit.Assert.*;

public class database_test {
	
	private static database db;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver		
		db = new database();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	
	public void testCommit()
	{
		
	}
	
	public void testRollback()
	{
		
	}

}
