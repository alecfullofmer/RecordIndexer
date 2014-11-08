package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import client_communicator.DownloadBatch_params;
import client_communicator.DownloadBatch_result;
import client_communicator.GetFields_result;
import client_communicator.SubmitBatch_params;
import client_communicator.SubmitBatch_result;
import design.Batch;
import design.Project;
import design.field;

public class db_access_batch {
	
	private database db;
	
	public db_access_batch(database data)
	{	
		db = data;
	}
	//check if a user already has a batch checked out
	public boolean userBatch(String username, String password) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean curr_batch = false;
		
		try
		{
			cxn = db.getConnection();
			String sql = "SELECT curr_batch FROM user WHERE username = ? AND password = ?";
			
			pstmt = cxn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0)
				curr_batch = true;
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		}
		return curr_batch;
		
	}
	
	public boolean markAsIndexed(int batch_id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		boolean worked = true;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE batch SET indexed = ? WHERE id = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, batch_id);
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
	
	public boolean checkIfIndexed(int id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean worked = false;
		try
		{
			cxn = db.getConnection();
			String sql = "SELECT indexed FROM batch WHERE id = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			worked = rs.getBoolean(1);
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pstmt != null)
				pstmt.close();
			if(rs != null)
				rs.close();
		}
		return worked;
	}
	
	public int getProject(int batch_id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int project_id = 0;
		try
		{
			cxn = db.getConnection();
			String sql = "SELECT projectid FROM batch WHERE id = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, batch_id);
			rs = pstmt.executeQuery();
			rs.next();
			project_id = rs.getInt(1);
			return project_id;
					
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return project_id;
		}
		finally
		{
			if(pstmt != null) pstmt.close();
			if(rs != null) rs.close();
		}
		
	}
	
	
	public SubmitBatch_result submitBatch(SubmitBatch_params params) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		SubmitBatch_result result = new SubmitBatch_result();
		if(checkIfIndexed(params.getBatch()) == true)
		{
			result = null;
			return result;
		}
		
		
		
		
		try
		{
			cxn = db.getConnection();
			String[] records = params.getRecords();
			int batch_id = params.getBatch();
			//look for project
			int project_id = getProject(batch_id);
			
			//get field number
			GetFields_result fields = db.getFieldDBO().getAll(project_id);
			List<field> allFields = fields.getFields();
			
			//determine which field id to start at
			
			int start = allFields.get(0).getId();
			
			int records_indexed = records.length + 1;
			int record_id;
			for(int i=0;i<records.length; i++)
			{
				PreparedStatement prep = null;
				ResultSet rs = null;
				Statement stmt = null;
				String insert = "INSERT into record (batch_id, record_number) values (?, ?)";
				prep = cxn.prepareStatement(insert);
				prep.setInt(1, batch_id);
				prep.setInt(2, i+1);
				prep.executeUpdate();
				
					stmt = cxn.createStatement();
					rs = stmt.executeQuery("Select last_insert_rowid()");
					rs.next();
					record_id = rs.getInt(1);
				
				
				String[] values = records[i].split(",");
				System.out.println(values.length);
				int field_id = start;
				for(int j = 0;j<values.length; j++)
				{
					PreparedStatement ps = null;
					String sql = "INSERT into value (field_id, record_id, batch_id, actual) values (?, ?, ?, ?)";
					ps = cxn.prepareStatement(sql);
					ps.setInt(1, field_id);
					field_id++;
					ps.setInt(2, record_id);
					ps.setInt(3, batch_id);
					ps.setString(4, values[j]);
					ps.executeUpdate();
					ps.close();
				}
				
			}
			
			boolean user = db.getUserDBO().increaseCount(records_indexed, params.getUsername(), params.getPassword());
			if(!user)
				result = null;
			boolean batch = markAsIndexed(batch_id);
			if(!batch)
				result = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pstmt != null) pstmt.close();
		}
		return result;
	}
	
	public boolean checkOutBatch(int id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		boolean work = true;
		
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE batch SET checked_out = ? WHERE id = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			work = false;
		}
		finally
		{
			if(pstmt != null) pstmt.close();
		}
		return work;
	}
	
	public Batch getBatch(int project_id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Batch batch = null;
		
		try
		{
			cxn = db.getConnection();
			String sql = "SELECT * FROM batch WHERE projectid = ? AND indexed = ? AND checked_out = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				batch = new Batch();
				batch.setId(rs.getInt(1));
				batch.setImageFilePath(rs.getString(3));
				if(checkOutBatch(rs.getInt(1)) == false)
				{
					batch = null;
				System.out.println("checking out batch failed");
				}
				
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		}
		return batch;
		
	}
	
	
	public DownloadBatch_result downloadBatch(DownloadBatch_params params) throws SQLException, ClassNotFoundException
	{
		
		String username = params.getUser();
		String password = params.getPassword();
		
		//check if the user already has a batch out
		if(userBatch(username, password) == false)
			return null;
		
		int project_id = params.getProject_id();
		Connection cxn = null;
		PreparedStatement project_pstmt = null;
		DownloadBatch_result result = new DownloadBatch_result();
		Project proj = new Project();
		Batch batch = new Batch();
		ResultSet rs = null;
		
		
		try
		{
			cxn = db.getConnection();
			//find the correct project
			String sql = "SELECT * from project WHERE id = ?";
			project_pstmt = cxn.prepareStatement(sql);
			project_pstmt.setInt(1, project_id);
			
			rs = project_pstmt.executeQuery();
			rs.next();
			proj.setFirst_y_coord(rs.getInt(4));
			proj.setId(project_id);
			proj.setHeight(rs.getInt(5));
			proj.setRecords_per_image(rs.getInt(3));
			proj.setNum_of_fields(rs.getInt(6));
			result.setProj(proj);
			
			//find a batch from correct project that isnt checked out
			batch = getBatch(project_id);
			if(batch == null)
			{
			 result = null;
			}
			else
			{
				
				result.setBatch(batch);
				GetFields_result fieldResult = db.getFieldDBO().getAll(project_id);
				List<field> fields = fieldResult.getFields();
				result.setFields(fields);
				int batch_id = batch.getId();
				boolean update_user = db.getUserDBO().checkOutBatch(params.getUser(), params.getPassword(), batch_id);
				if(update_user == false)
					result = null;
			}	
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(project_pstmt != null) project_pstmt.close();
		}
		return result;
		
	}
	
	
	public String getSampleImage(int project_id) throws SQLException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String url = null;
		
		try
		{
			cxn = db.getConnection();
			
			String sql = "SELECT image_file_path FROM batch WHERE projectid = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			rs = pstmt.executeQuery();
			rs.next();
			url = rs.getString(1);
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		}
		return url;
	}
	
	public boolean create() throws ClassNotFoundException, SQLException
	{
		Connection cxn = null;
		Statement stmt = null;
		boolean commit = true;
		
		try{
			cxn = db.getConnection();
			
			String drop = "drop table if exists batch";
			stmt = cxn.createStatement();
			stmt.executeUpdate(drop);
			
			String sql = "CREATE TABLE batch" + 
						"(id integer not null primary key autoincrement," +
						"projectid integer not null," +
						"image_file_path varchar(255) not null," +
						"indexed bool not null,"
						+ "checked_out bool);";
			
			stmt.executeUpdate(sql);
		}
		catch (SQLException e)
		{	
			commit = false;
			e.printStackTrace();
		}
		finally
		{
			if(stmt != null)
				stmt.close();
				
		}
		return commit;
		 
	}
	
	/**
	 * Get all the batch values in the DB
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @returns a list of Batch objects
	 */
	public List<Batch> getAll(Integer project_id) throws SQLException, ClassNotFoundException
	{
		
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Batch> batch = new ArrayList<Batch>();
		try{
			cxn = db.getConnection();
			String sql = "select * from batch where projectid = ?";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			stmt = cxn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				Batch b = new Batch();
				b.setId(rs.getInt(1));
				b.setProject_id(rs.getInt(2));
				b.setImageFilePath(rs.getString(3));
				b.setIndexed(rs.getBoolean(4));
				batch.add(b);
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
		return batch;
	}
	
	/**
	 * Add a Batch into the Db
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @params all the column values except the auto incrementing id
	 */
	public int add(Batch b) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = 0;
		try 
		{
			cxn = db.getConnection();
		
			String sql = "insert into batch (projectid, image_file_path, indexed, checked_out) values(?, ?, ?, ?)";
			pstmt = cxn.prepareStatement(sql);
			pstmt.setInt(1, b.getProject_id());
			pstmt.setString(2, b.getImageFilePath());
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			if(pstmt.executeUpdate() == 1)
			{
				stmt = cxn.createStatement();
				rs = stmt.executeQuery("Select last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				b.setId(id); 
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
	
	/*
	 * takes in a Batch object with params that need updated
	 * Finds the batch in the database with the same id
	 */
	
	public boolean update(Batch b) throws SQLException, ClassNotFoundException
	{
		Connection cxn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		boolean commit = true;
		try
		{
			cxn = db.getConnection();
			String sql = "UPDATE batch SET image_file_path = " + 
						b.getImageFilePath() + "indexed = " + b.getIndexed() + "WHERE id = " + b.getId() +
						"and projectid = " + b.getProject_id();
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
	 * Takes values from a row from the DB and builds a Batch object
	 * @param id: the auto incremented id
	 * @param project_id: the associated project
	 * @param imageFile: the url to respective image
	 * @return a Batch object
	 */
	public Batch buildBatch(int id, int project_id, String imageFile){return null;}

}
