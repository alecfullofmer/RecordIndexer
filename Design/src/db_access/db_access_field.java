package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import client_communicator.GetFields_result;
import design.field;

public class db_access_field {
	
//create table

 private database db;

public  db_access_field(database data)
{
	db = data;
}

public void update()
{}
	/**
	 * Get all the field values in the DB
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @returns a list of field objects
	 */

public int create() throws ClassNotFoundException, SQLException
{
	Connection cxn = null;
	Statement stmt = null;
	int value;
	try{
		cxn = db.getConnection();
		
		String drop = "drop table if exists field";
		stmt = cxn.createStatement();
		stmt.executeUpdate(drop);
		
		String sql = "create table field" + 
					"(id integer not null primary key autoincrement," +
					"projectid integer not null," +
					"order_number integer not null," +
					"title varchar(255) not null," +
					"width integer not null," +
					"xcoord integer not null,"
					+ "helpHtml varchar(255)," +
					"knowndata varchar(255));";
			
		
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

	public GetFields_result getAll(int project_id) throws SQLException, ClassNotFoundException
	{
			
			Connection cxn = null;
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			List<field> field = new ArrayList<field>();
			GetFields_result result = null;
			try{
				cxn = db.getConnection();
				if(project_id > 0)
				{
					String sql = "select * from field where projectid = ?";
					pstmt = cxn.prepareStatement(sql);
					pstmt.setInt(1, project_id);
				}
				else
				{
					String sql = "SELECT * from field";
					pstmt = cxn.prepareStatement(sql);
				}
				rs = pstmt.executeQuery();
				
				
				while (rs.next())
				{
					if(result == null)
						result =new GetFields_result();
					
					field temp = new field();
					temp.setId(rs.getInt(1));
					temp.setProject_id(rs.getInt(2));
					temp.setOrder(rs.getInt(3));
					temp.setTitle(rs.getString(4));
					temp.setWidth(rs.getInt(5));
					temp.setXcord(rs.getInt(6));
					temp.setHelpHtml(rs.getString(7));
					temp.setKnowndata(rs.getString(8));
					field.add(temp);
				}
				
				if(result != null)
				result.setFields(field);
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				result = null;
			}
			finally
			{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				
			}
			return result;
		
	}
	
	/**
	 * Add a field into the Db
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @params all the column values except the auto incrementing id
	 */
	public int add(field fld) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = 1;
		try 
		{
	
			cxn = db.getConnection();
			
			String sql = "insert into field (projectid, order_number, title, width, xcoord, helphtml, knowndata)"
					+ " values(?, ?, ?, ?, ?, ?, ?)";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, fld.getProject_id());
			pstmt.setInt(2, fld.getOrder());
			pstmt.setString(3, fld.getTitle());
			pstmt.setInt(4, fld.getWidth());
			pstmt.setInt(5, fld.getXcord());
			pstmt.setString(6, fld.getHelpHtml());
			pstmt.setString(7, fld.getKnowndata());
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				fld.setId(id); 
			}
				
		}
		catch (SQLException e) //how
		{
			id = -1;
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return id;
	}
	
	public boolean update(field fld) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		boolean commit = true;
		try
		{
			
			cxn = db.getConnection();
			
			String sql = "UPDATE field SET help_html = " + 
						fld.getHelpHtml() + "known_data = " + fld.getKnowndata() + "WHERE id = " + fld.getId() +
						"and projectid = " + fld.getProject_id();
			pstmt = cxn.prepareStatement(sql);
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				stmt.executeUpdate(sql);
			
			}
			
				
		}
		catch (SQLException e)
		{
			commit = false;
		}
		finally
		{
			if(stmt != null)
				stmt.close();
				
		}
		return commit;
	}
	
	/**
	 * Takes values from a row from the DB and builds a field object
	 * @return a Batch object
	 */
	public field buildField(int id, int project_id, String title, int width, int xcord, String helpHtml, String knowndata){return null;}


}
