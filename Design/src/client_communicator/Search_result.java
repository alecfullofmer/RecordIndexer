package client_communicator;

import java.util.ArrayList;
import java.util.List;

public class Search_result {
	
	private List<SearchTuple> tuples = new ArrayList<SearchTuple>();
	private String host;
	private String port;
	private boolean empty = true;
	
	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public Search_result(){}

	public List<SearchTuple> getTuples() {
		return tuples;
	}

	public void setTuples(List<SearchTuple> tuples) {
		this.tuples = tuples;
	}
	
	public void setHost(String h)
	{
		host = h;
	}
	
	public void setPort(String p)
	{
		port = p;
	}
	
	public String toString()
	{
		String ss = "";
		for(int i = 0;i<tuples.size(); i++)
		{
			ss += tuples.get(i).toString(host, port);
		}
		return ss;
	}

}
