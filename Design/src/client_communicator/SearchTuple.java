package client_communicator;



public class SearchTuple {
	
	private int batch_id;
	private String image_url;
	private int record_num;
	private int field_id;
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SearchTuple(){}

	public int getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}



	public void setImage_url(String image_url) {
		
		this.image_url = image_url;
	}

	public int getRecord_num() {
		return record_num;
	}

	public void setRecord_num(int record_num) {
		this.record_num = record_num;
	}

	public int getField_id() {
		return field_id;
	}

	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	
	public String buildUrl(String image_url, String host, String port)
	{
		String ss = "http://" + host + ":" + port + "/Records/" + image_url;
		return ss;
	}
	
	public String toString(String host, String port)
	{
		String ss = batch_id + "\n" + buildUrl(image_url, host, port) + "\n" + record_num + "\n" + field_id + "\n";
		return ss;
	}
	

}
