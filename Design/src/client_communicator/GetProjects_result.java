package client_communicator;

import java.util.ArrayList;
import java.util.List;

public class GetProjects_result {
	
	private List<String> list = new ArrayList<String>();
	
	public GetProjects_result(){}
	
	public void add(String id, String name)
	{
		list.add(id);
		list.add(name);
	}
	
	public String toString()
	{
		String ss = "";
		for(int i = 0;i<list.size(); i++)
		{
			ss += list.get(i);
			ss += "\n";
		}
		
		return ss;
	}

}
