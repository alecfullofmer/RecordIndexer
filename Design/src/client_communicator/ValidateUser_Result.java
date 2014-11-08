package client_communicator;

public class ValidateUser_Result
{
	
	private boolean result;
	private String first;
	private String last;
	private int records;
	private String invalid = null;
	
	public String getInvalid() {
		return invalid;
	}

	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}

	public ValidateUser_Result(){}
	
	public String toString()
	{
		String ss = "True" + "\n" + first + "\n" + last + "\n" + records;
		return ss;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}
	
	

}
