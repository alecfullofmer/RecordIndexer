package design;

public class Project {
	
	private int id;
	private String title;
	private int records_per_image;
	private int first_y_coord;
	private int height;
	private int num_of_fields;
	
	public int getNum_of_fields() {
		return num_of_fields;
	}

	public void setNum_of_fields(int num_of_fields) {
		this.num_of_fields = num_of_fields;
	}

	public Project(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRecords_per_image() {
		return records_per_image;
	}

	public void setRecords_per_image(int records_per_image) {
		this.records_per_image = records_per_image;
	}

	public int getFirst_y_coord() {
		return first_y_coord;
	}

	public void setFirst_y_coord(int first_y_coord) {
		this.first_y_coord = first_y_coord;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
