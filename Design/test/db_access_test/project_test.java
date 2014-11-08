package db_access_test;

import java.sql.SQLException;

import client_communicator.GetProjects_result;
import db_access.database;
import db_access.db_access_project;
import design.Project;

public class project_test {

	
	public void testAdd() throws SQLException, ClassNotFoundException
	{
		database db = new database();
		db_access_project project = db.getProjectDBO();
		Project proj = new Project();
		proj.setFirst_y_coord(10);
		proj.setHeight(20);
		proj.setRecords_per_image(5);
		proj.setTitle("project");
		
		if(project.add(proj) > 0)
		{
			db.endTransaction(true);
		}
		else
		{
			db.endTransaction(false);
		}
	}
	
	public void testUpdate()
	{
		
	}
	
	public void testGetall() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_project project = db.getProjectDBO();
		
		GetProjects_result result = project.getAll();
		System.out.println(result.toString());
		db.endTransaction(true);
		
	}
	public void testCreate() throws ClassNotFoundException, SQLException
	{
		database db = new database();
		db.startTransaction();
		db_access_project proj = db.getProjectDBO();
		
		int get = proj.create();
		db.endTransaction(true);
	}
	
}
