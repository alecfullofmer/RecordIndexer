package design;

public class user {

private String password;
private String username;
private String firstname;
private String lastname;
private String email;
private int id;
private int recordCount;
private int currentBatch;

 public user(){}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getRecordCount() {
	return recordCount;
}

public void setRecordCount(int recordCount) {
	this.recordCount = recordCount;
}

public int getCurrentBatch() {
	return currentBatch;
}

public void setCurrentBatch(int currentBatch) {
	this.currentBatch = currentBatch;
}
 
 

}
