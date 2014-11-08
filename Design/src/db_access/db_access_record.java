package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import design.Record;
public class db_access_record {
	
	private database db;
	
	public db_access_record(database data)
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
			
			String drop = "drop table if exists record";
			stmt = cxn.createStatement();
			stmt.executeUpdate(drop);
			
			String sql = "create table record" + 
						"(id integer not null primary key autoincrement," +
						
						"batch_id integer not null," +
						"record_number integer not null);";
				
		
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
	
	public boolean update(Record rec) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		boolean commit=true;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE record SET batch_id = " + 
						rec.getBatch_id() + "record_number = " + rec.getRecord_number()
						+ "WHERE id = " + rec.getId();
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
	 * Retrieve all the Records from the DB and put them in Records objects
	 * @return a list of Record objects
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Record> getAll(int batchid) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		List<Record> record = new ArrayList<Record>();
		try{
			cxn = db.getConnection();
			String sql = "select * from record where batch_id = " + batchid;
			stmt = cxn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				Record rec= new Record();
				rec.setId(rs.getInt(1));
				rec.setBatch_id(rs.getInt(2));
				rec.setRecord_number(rs.getInt(3));
				record.add(rec);
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
		return record;
	}
	
	/**
	 * Takes the values from the database and creates Record objects
	 * @param id: the Record id
	 * @param batch_id: the Record batch_id
	 * @param number: the record number
	 * @return Record object
	 */
	public Record buildRecord(int id, int batch_id, int number){return null;}
	
	/**
	 * Adds a new record into the DB
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @params the column values except the auto increment key
	 */
	public int add(Record rec) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = 1;
		try 
		{
			cxn = db.getConnection();
			
			String sql = "insert into record (batch_id, record_number) values(?, ?)";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, rec.getBatch_id());
			pstmt.setInt(2, rec.getRecord_number());
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				rec.setId(id); 
			}
				
		}
		catch (SQLException e) //how
		{
			e.printStackTrace();
			id = -1;
		}
		finally
		{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return id;
	}
	
}
