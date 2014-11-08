package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import client_communicator.ValidateUser_Params;
import client_communicator.ValidateUser_Result;
import design.user;

public class db_access_user {
	
	private database db;
	
	public db_access_user(database data)
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
			
			String drop = "drop table if exists user";
			stmt = cxn.createStatement();
			stmt.executeUpdate(drop);
//			
			String sql = "create table user" + 
						"(id integer not null primary key autoincrement," +
						"username varchar(255) not null," +
						"password varchar(255) not null," +
						"firstname varchar(255) not null," +
						"lastname varchar(255) not null," +
						"email varchar(255) not null," +
						"indexed_records integer," + 
						"curr_batch integer);";
				
			
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
	
	public boolean increaseCount(int records, String username, String password) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		boolean worked = true;
		
		try
		{
			cxn = db.getConnection();
			String sql = "SELECT indexed_records FROM user WHERE username = ? AND password = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				PreparedStatement ps = null;
				int number = rs.getInt(1);
				number = number + records - 1;
				
				String SQL = "UPDATE user SET indexed_records = ? WHERE username = ? AND password = ?";
				ps = cxn.prepareStatement(SQL);
				ps.setInt(1, number);
				ps.setString(2, username);
				ps.setString(3, password);
				ps.executeUpdate();
			}
			else
			{
				worked = false;
			}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			worked = false;
		}
		finally
		{
			if(pstmt != null) pstmt.close();
			if(rs != null) rs.close();
			
		}
		return worked;
	}
	
	public boolean checkOutBatch(String username, String password, int batch_id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		boolean worked = true;
		
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE user SET curr_batch = ? WHERE username = ? AND password = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, batch_id);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.executeUpdate();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			worked = false;
		}
		finally
		{
			if(pstmt != null) pstmt.close();
		}
		
		return worked;
	}
	
	public boolean update(user use) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		boolean commit = true;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE user SET username = " + 
						use.getUsername() + "password = " + use.getPassword() + " firstname = "
						+ use.getFirstname() + " lastname = " + use.getLastname() +
						" email = " + use.getEmail() + " indexed_records " + use.getRecordCount()
						+ " current_batch = " + use.getCurrentBatch() + "WHERE id = " + use.getId(); 
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
			
			if(stmt != null) stmt.close();
		}
		return commit;
	}
	/**
	 * Gets all the rows from the user table and puts user objects into a list
	 * @return a list of user objects
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<user> getAll() throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		List<user> use = new ArrayList<user>();
		try{
			cxn = db.getConnection();
			String sql = "select * from user";
			
			stmt = cxn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				user temp = new user();
				temp.setUsername(rs.getString(1));
				temp.setPassword(rs.getString(2));
				temp.setFirstname(rs.getString(3));
				temp.setLastname(rs.getString(4));
				temp.setEmail(rs.getString(5));
				temp.setRecordCount(rs.getInt(6));
				temp.setCurrentBatch(rs.getInt(7));
				use.add(temp);
			}
			
		}
		catch (SQLException e)
		{
			//throw error
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			
		}
		return use;
	}
	
	/**
	 * Creates a new row in the user table
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @params all the column values in the user table
	 */
	public int add(user use) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		try 
		{
			cxn = db.getConnection();
			
			String sql = "insert into user (username, password, firstname, lastname,"
					+ " email, indexed_records, curr_batch) values(?, ?, ?, ?, ?, ?, ?)";
			
			//input the actual values into the DB
			pstmt = cxn.prepareStatement(sql);
			pstmt.setString(1, use.getUsername());
			pstmt.setString(2, use.getPassword());
			pstmt.setString(3, use.getFirstname());
			pstmt.setString(4, use.getLastname());
			pstmt.setString(5, use.getEmail());
			pstmt.setInt(6, use.getRecordCount());
			pstmt.setInt(7, use.getCurrentBatch());
			
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				use.setId(id); 
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
	
	public ValidateUser_Result validate(ValidateUser_Params params) throws SQLException
	{
		Connection cxn = null;
		ValidateUser_Result result = null;
		
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		try
		{
			cxn = db.getConnection();
			String name = params.getUsername();
			String pass = params.getPassword();
			String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = new ValidateUser_Result();
				result.setFirst(rs.getString(4));
				result.setLast(rs.getString(5));
				result.setRecords(rs.getInt(7));
			}
			else
			{
				result = new ValidateUser_Result();
				result.setInvalid("FALSE");
			}
			
		}
		catch (SQLException e)
		{
				e.printStackTrace();
				return null;
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return result;
			
			
		
		
	}
	
	/**
	 * Takes all the column values from the user table in the DB and
	 * builds a user object
	 * @param all the column values in the user table
	 * @return a user object
	 */
	public user buildUser(String username, String password, String first_name, String last_name,
			String email, int indexed_records){return null;}

}
