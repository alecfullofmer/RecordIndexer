package client_communicator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import design.Batch;
import design.Project;
import design.field;

public class DownloadBatch_result {
	
	private Project Proj = new Project();
	private Batch batch = new Batch();
	private List<field> fields = new ArrayList<field>();
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

	public Project getProj() {
		return Proj;
	}
	
	public void setFields(List<field> f)
	{
		fields = f;
	}

	public void setProj(Project proj) {
		Proj = proj;
	}
	
	
	public DownloadBatch_result(){}
	
	public void addFields(field f)
	{
		fields.add(f);
	}
	
	public List<field> getFields()
	{
		return fields;
	}
	
	
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
	public String buildUrl(String image_url)
	{
		
		String ss = "http://" + host + ":" + port + "/Records/" + image_url;
		return ss;
	}
	
	public String toString()
	{
		if(Proj.getId() == 0)
			return "FAILED";
		String ss = batch.getId() + "\n" + Proj.getId() + "\n" + buildUrl(batch.getImageFilePath()) +
					"\n" + Proj.getFirst_y_coord() + "\n" + Proj.getHeight() + "\n" +
					Proj.getRecords_per_image() + "\n" +Proj.getNum_of_fields() + "\n";
		
			for(int i=0; i<fields.size(); i++)
			{
				ss += fields.get(i).getId() + "\n" + fields.get(i).getOrder() + "\n" +
						fields.get(i).getTitle() + "\n" + buildUrl(fields.get(i).getHelpHtml()) + "\n" +
						fields.get(i).getXcord() + "\n" + fields.get(i).getWidth() + "\n";
						if(fields.get(i).getKnowndata() != null)
						ss += buildUrl(fields.get(i).getKnowndata()) + "\n";
			}
			
			return ss;
	}
	
	

}
