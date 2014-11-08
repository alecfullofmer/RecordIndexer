package client_communicator;

import java.util.ArrayList;
import java.util.List;

import design.field;

public class GetFields_result {
	
	private List<field> fields = new ArrayList<field>();
	
	public GetFields_result(){}
	
	public List<field> getFields()
	{
		return fields;
	}
	
	public void setFields(List<field> f)
	{
		fields = f;
	}
	
	public String toString()
	{
		String ss = "";
		
		for(int i=0;i<fields.size();i++)
		{
			ss += fields.get(i).getProject_id() + "\n" + fields.get(i).getId() + "\n" +
					fields.get(i).getTitle() + "\n";
		}
		return ss;
	}

}
