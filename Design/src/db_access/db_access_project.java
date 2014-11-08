package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import client_communicator.GetProjects_result;
import design.Project;

public class db_access_project {
	
	private database db;
	
	public db_access_project(database data)
	{
		db = data;
	}
	
	public int create() throws ClassNotFoundException, SQLException
	{
		Connection cxn = null;
		Statement stmt = null;
		int value;
		try{
			cxn = db.getConnection();
			
			String drop = "drop table if exists project";
			stmt = cxn.createStatement();
			stmt.executeUpdate(drop);
			
			String sql = "create table project" + 
						"(id integer not null primary key autoincrement," +
						"title varchar(255) not null," +
						"records_per_image integer not null," +
						"first_y_coord integer not null,"
						+ "record_height integer not null,"
						+ "num_fields integer not null);";
				
			
			stmt.executeUpdate(sql);
			
			value = 1;
		}
		catch (SQLException e)
		{	
			value = -1;
			e.printStackTrace();
		}
		finally
		{
			if(stmt != null) stmt.close();
		}
		return value;
	}
	
	
	public void update(Project proj) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE project SET records_per_image = " + 
						proj.getRecords_per_image() + "record_height = " + proj.getHeight()
						+ "WHERE id = " + proj.getId();
			pstmt = cxn.prepareStatement(sql);
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				stmt.executeUpdate(sql);
			
			}
			
				
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			
			if(stmt != null) stmt.close();
		}
	}
	/**
	 * Get all the rows from the Project table
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @returns a list of Project objects
	 */
	public GetProjects_result getAll() throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
	
		Statement stmt = null;
		ResultSet rs = null;
		GetProjects_result result = new GetProjects_result();
		
		try{
			cxn = db.getConnection();
			String sql = "select * from project";
			stmt = cxn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				String id = Integer.toString(rs.getInt(1));
				String name = rs.getString(2);
				result.add(id, name);
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			
		}
		return result;
	}
	
	/**
	 * Creates a new row in the Project table
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int add(Project proj) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		try 
		{
			cxn = db.getConnection();
			
			String sql = "insert into project (title, records_per_image, first_y_coord, "
						+ "record_height, num_fields) values(?, ?, ?, ?, ?)";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setString(1, proj.getTitle());
			pstmt.setInt(2, proj.getRecords_per_image());
			pstmt.setInt(3, proj.getFirst_y_coord());
			pstmt.setInt(4, proj.getHeight());
			pstmt.setInt(5, proj.getNum_of_fields());
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				proj.setId(id); 
			}
				
		}
		catch (SQLException e) //how
		{
			
				e.printStackTrace();
			
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return id;
	}
	
	
	
	/**
	 * Takes all the values from one row in the Project table and creates 
	 * a Project object
	 * @params all the column values fromt he Project table
	 * @return a Project object
	 */
	public Project buildProject(int id, String title, int records_per_image, int ycord, int height){return null;}

}
