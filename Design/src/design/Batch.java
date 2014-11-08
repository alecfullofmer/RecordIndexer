package design;

public class Batch {

	private int id;
	private int project_id;
	private String imageFilePath;
	private boolean indexed = false;
	
	public int getId() {
		return id;
	}
	
	public void setIndexed(boolean b){
		indexed = b;
	}
	
	public boolean getIndexed()
	{
		return indexed;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public Batch(){}
}
