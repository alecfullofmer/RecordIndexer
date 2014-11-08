package design;

public class field {
	
	private int id;
	private int project_id;
	private String title;
	private int width;
	private int xcoord;
	private String helpHtml;
	private String knowndata;
	private int order;
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public field(){}

	public int getId() {
		return id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getXcord() {
		return xcoord;
	}

	public void setXcord(int xcord) {
		this.xcoord = xcord;
	}

	public String getHelpHtml() {
		return helpHtml;
	}

	public void setHelpHtml(String helpHtml) {
		this.helpHtml = helpHtml;
	}

	public String getKnowndata() {
		return knowndata;
	}

	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}
	
	

}
