package db_access;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
	
	
	private db_access_batch batchDBO;
	private db_access_field fieldDBO;
	private db_access_project projectDBO;
	private db_access_record recordDBO;
	private db_access_user userDBO;
	private db_access_values valuesDBO;

	private static String connectionURL = "jdbc:sqlite:FileIndexer.sqlite";
	private static Connection connection = null;
	
	public database() throws ClassNotFoundException
	{
		batchDBO = new db_access_batch(this);
		fieldDBO = new db_access_field(this);
		projectDBO = new db_access_project(this);
		recordDBO = new db_access_record(this);
		userDBO = new db_access_user(this);
		valuesDBO = new db_access_values(this);
		 
	}
	
	public static void initialize()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public db_access_batch getBatchDBO() {
		return batchDBO;
	}

	public void setBatchDBO(db_access_batch batchDBO) {
		this.batchDBO = batchDBO;
	}

	public db_access_field getFieldDBO() {
		return fieldDBO;
	}

	public void setAccessDBO(db_access_field accessDBO) {
		this.fieldDBO = accessDBO;
	}

	public db_access_project getProjectDBO() {
		return projectDBO;
	}

	public void setProjectDBO(db_access_project projectDBO) {
		this.projectDBO = projectDBO;
	}

	public db_access_record getRecordDBO() {
		return recordDBO;
	}

	public void setRecordDBO(db_access_record recordDBO) {
		this.recordDBO = recordDBO;
	}

	public db_access_user getUserDBO() {
		return userDBO;
	}

	public void setUserDBO(db_access_user userDBO) {
		this.userDBO = userDBO;
	}

	public db_access_values getValuesDBO() {
		return valuesDBO;
	}

	public void setValuesDBO(db_access_values valuesDBO) {
		this.valuesDBO = valuesDBO;
	}

	 public void startTransaction() throws ClassNotFoundException
	{
		
		try {
		 // Open a database connection
		 connection = DriverManager.getConnection(connectionURL);
		 
		 // Start a transaction
		 connection.setAutoCommit(false);
		}
		catch (SQLException e) {
		 // ERROR
			e.printStackTrace();
			System.out.println("didn't connect");
		}
		
	}
	 
	 public Connection getConnection()
	 {
		 return connection;
	 }
	
	 public void endTransaction(boolean commit) throws SQLException
	{
		try
		{
			if(commit)
			{
				connection.commit();
			}
			else
			{
				connection.rollback();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			connection.close();
		}
	}
	


}
