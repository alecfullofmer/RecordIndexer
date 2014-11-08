package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import client_communicator.SearchTuple;
import client_communicator.Search_params;
import client_communicator.Search_result;
import design.Value;

public class db_access_values {

	private database db;
	
	public db_access_values(database data)
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
			
			String drop = "drop table if exists value";
			stmt = cxn.createStatement();
			stmt.executeUpdate(drop);
			
			String sql = "create table value" + 
						"(id integer not null primary key autoincrement," +
						"field_id integer not null," +
						"record_id integer not null," +
						"batch_id integer not null," +
						"actual varchar(255) not null);";
				
			
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
	
	public Search_result search(Search_params params)
	{
		Connection cxn = null;
		Search_result result = new Search_result();
		
		
		try
		{
			cxn = db.getConnection();
			String[] fields = params.getFields();
			String[] values = params.getValues();
			
			List<SearchTuple> tuples = new ArrayList<SearchTuple>();
			
			for(int i = 0; i<fields.length;i++)
			{
	
				for(int j = 0;j<values.length; j++)
				{
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT value.batch_id, image_file_path, record_number, field_id"
							+ " FROM batch, field, value, record"
							+ " WHERE value.field_id = ? AND value.field_id = field.id AND value.batch_id = batch.id"
							+ " AND value.record_id = record.id AND value.batch_id = record.batch_id "
							+ "AND value.actual = ?";
					
					pstmt = cxn.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(fields[i]));
					pstmt.setString(2, values[j].toLowerCase());
					rs = pstmt.executeQuery();
					while(rs.next())
					{
						result.setEmpty(false);
						SearchTuple tup = new SearchTuple();
						tup.setBatch_id(rs.getInt(1));
						tup.setImage_url(rs.getString(2));
						tup.setRecord_num(rs.getInt(3));
						tup.setField_id(rs.getInt(4));
						tuples.add(tup);
					}
					rs.close();
					pstmt.close();
					
				}
			}
			if(tuples.size() > 0)
			result.setTuples(tuples);
			
			
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public void update(Value val) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE value Set actual = " + val.getActual() + "WHERE field_id = "
					+ val.getField_id() + " record_id = " + val.getRecord_id() + " batch_id= "
							+ val.getBatch_id();
			pstmt = cxn.prepareStatement(sql);
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				stmt.executeUpdate(sql);
			
			}
			
				
		}
		catch (SQLException e)
		{}
		finally
		{
			
			if(stmt != null) stmt.close();
		}
	}
	/**
	 * Retrieves all the rows from the Value table
	 * @returns a list of Value objects
	 */
	public List<Value> getAll()
	{
		return null;
	}
	
	/**
	 * Add a new row into the Value table
	 * @param all the column values except the aut incrementing id
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public int add(Value val) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		try 
		{
			cxn = db.getConnection();
			
			//cxn.setAutoCommit(false); is this how it works?
			String sql = "INSERT INTO value(field_id, record_id, batch_id, actual)"
					+ " VALUES(?, ?, ?, ?)";
			
			//input the actual values into the statement
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, val.getField_id());
			pstmt.setInt(2, val.getRecord_id());
			pstmt.setInt(3, val.getBatch_id());
			pstmt.setString(4, val.getActual());
			
			
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				val.setId(id); 
			}
				
		}
		catch (SQLException e) //how
		{
			System.out.println(e.getMessage());
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
	 * Builds a Value object from the params
	 * @return a Value object 
	 */
	public Value buildValue(int id, int field_id, int record_id, int batch_id){return null;}
}
