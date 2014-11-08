package client_communicator;



public class SubmitBatch_params {
	
	private String username;
	private String password;
	private int batch;
	String[] records;
	
	
	
	public String[] getRecords() {
		return records;
	}

	public void setRecords(String[] records) {
		this.records = records;
	}

	public SubmitBatch_params(){}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	
	
	
	
	
	// list of field_ids and record values

}
