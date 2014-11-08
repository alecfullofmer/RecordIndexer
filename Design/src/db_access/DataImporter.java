package db_access;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import design.Batch;
import design.Project;
import design.Record;
import design.Value;
import design.field;
import design.user;




public class DataImporter {
	
	private Document doc;
	private database db;
	private int fieldId = 1;
	private int num_of_fields = 0;
	
	
	
		public DataImporter() throws SQLException
		{
			try {
				db = new database();
				database.initialize();
				db.startTransaction();
				db.getBatchDBO().create();
				db.getFieldDBO().create();
				db.getProjectDBO().create();
				db.getRecordDBO().create();
				db.getUserDBO().create();
				db.getValuesDBO().create();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public static void main(String[] args)
		{
			DataImporter importer;
			try {
				importer = new DataImporter();
				importer.Parse(args[0]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void Parse(String xml) 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try
			{
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			File file = new File(xml);
			
			FileUtils.copyDirectory(file.getParentFile(), new File("Records"));
			
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			parseUsers();
			parseProjects();
			db.endTransaction(true);
			}
			catch (ParserConfigurationException | SAXException | IOException e)
			{
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
								
							
		}
		
		public void parseUsers()
		{
			NodeList users = doc.getElementsByTagName("user");
			int length = users.getLength();
			
			for(int i = 0; i<length; i++)
			{
				Element userEl = (Element)users.item(i);
				
				Element nameEl = (Element)userEl.getElementsByTagName("username").item(0);
				Element passEl = (Element)userEl.getElementsByTagName("password").item(0);
				Element firstEl = (Element)userEl.getElementsByTagName("firstname").item(0);
				Element lastEl = (Element)userEl.getElementsByTagName("lastname").item(0);
				Element emailEl = (Element)userEl.getElementsByTagName("email").item(0);
				Element recordsEl = (Element)userEl.getElementsByTagName("indexedrecords").item(0);
				
				String username = nameEl.getTextContent();
				String password = passEl.getTextContent();
				String firstname = firstEl.getTextContent();
				String lastname = lastEl.getTextContent();
				String email = emailEl.getTextContent();
				String records = recordsEl.getTextContent();
				int indexed = Integer.parseInt(records);
				
				user use = new user();
				use.setUsername(username);
				use.setPassword(password);
				use.setFirstname(firstname);
				use.setLastname(lastname);
				use.setEmail(email);
				use.setRecordCount(indexed);
				
				
				
				try {
					
					db_access_user userDBO = db.getUserDBO();
					userDBO.add(use);
				} catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
				
				
				
				
			}
			
			
			
		}
		
		public void parseProjects()
		{
			NodeList projects = doc.getElementsByTagName("project");
			
			for(int i=0;i<projects.getLength();++i)
			{
				Element projEl = (Element)projects.item(i);
				
				Element titleEl = (Element)projEl.getElementsByTagName("title").item(0);
				Element recordsPerImageEl = (Element)projEl.getElementsByTagName("recordsperimage").item(0);
				Element ycoordEl = (Element)projEl.getElementsByTagName("firstycoord").item(0);
				Element rheightEl = (Element)projEl.getElementsByTagName("recordheight").item(0);
				
				String title = titleEl.getTextContent();
				String RPI = recordsPerImageEl.getTextContent();
				int recordsPI = Integer.parseInt(RPI);
				String ycoordinate = ycoordEl.getTextContent();
				int ycoord = Integer.parseInt(ycoordinate);
				String rheight = rheightEl.getTextContent();
				int height = Integer.parseInt(rheight);
				
				Project proj = new Project();
				proj.setTitle(title);
				proj.setHeight(height);
				proj.setFirst_y_coord(ycoord);
				proj.setRecords_per_image(recordsPI);
				
				
				try {
					NodeList fields = projEl.getElementsByTagName("field");
					int num_fields = fields.getLength();
					proj.setNum_of_fields(num_fields);
					db_access_project projectDBO = db.getProjectDBO();
					int projectId = projectDBO.add(proj);
					
					parseFields(fields, projectId);
					NodeList images = projEl.getElementsByTagName("image");
					parseImages(images, projectId);
					
				} catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		public void parseFields(NodeList fields, int project_id)
		{
			/*
			 * needs project_id;
			 *    <title>Last Name</title>
		     *     <xcoord>60</xcoord>
		     *     <width>300</width>
		     *     <helphtml>fieldhelp/last_name.html</helphtml>
		     *     <knowndata>knowndata/1890_last_names.txt</knowndata>
			 */
			num_of_fields = 0;
			for(int i = 0;i<fields.getLength();++i)
			{
				num_of_fields++;
				Element fieldEl = (Element)fields.item(i);
				
				Element titleEl = (Element)fieldEl.getElementsByTagName("title").item(0);
				Element xcoordEl = (Element)fieldEl.getElementsByTagName("xcoord").item(0);
				Element widthEl = (Element)fieldEl.getElementsByTagName("width").item(0);
				Element htmlEl = (Element)fieldEl.getElementsByTagName("helphtml").item(0);
				Element dataEl = (Element)fieldEl.getElementsByTagName("knowndata").item(0);
				
				String title = titleEl.getTextContent();
				String xcoord = xcoordEl.getTextContent();
				int xcoordInt = Integer.parseInt(xcoord);
				String width = widthEl.getTextContent();
				int widthInt = Integer.parseInt(width);
				String html = htmlEl.getTextContent();
				String data = null;
				if(dataEl != null)
				{
				 data = dataEl.getTextContent();
				}
				
				field field = new field();
				field.setProject_id(project_id);
				
				
				field.setTitle(title);
				field.setXcord(xcoordInt);
				field.setWidth(widthInt);
				field.setHelpHtml(html);
				if(data != null)
				field.setKnowndata(data);
				field.setOrder(fieldId);
				fieldId++;
				
				
				
				
			
				
				try
				{
				
					db_access_field fieldDBO = db.getFieldDBO();
					int field_id = fieldDBO.add(field);
					
				}
				catch (ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void parseImages(NodeList images, int project_id)
		{
			/*
			 * needs project_id;
			 */
			for(int i = 0;i<images.getLength();++i)
			{
				Element imageEl = (Element)images.item(i);
				
				Element fileEl = (Element)imageEl.getElementsByTagName("file").item(0);
				String file = fileEl.getTextContent();
				
				Batch batch = new Batch();
				batch.setImageFilePath(file);
				batch.setIndexed(false);
				batch.setProject_id(project_id);
				
				
				try
				{
				
					db_access_batch batchDBO = db.getBatchDBO();
					int batchId = batchDBO.add(batch);
					NodeList records = imageEl.getElementsByTagName("record");
					parseRecords(batchId, records);
					
				}
				catch(ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void parseRecords(int batchId, NodeList records)
		{
			/*
			 * needs batch ID
			 */
			for(int i =0;i<records.getLength();i++)
			{
				Element recordEl = (Element)records.item(i);
				
				try
				{
					
					Record rec = new Record();
					rec.setBatch_id(batchId);
					rec.setRecord_number(i + 1);
					db_access_record recordDBO = db.getRecordDBO();
					int recordId = recordDBO.add(rec);
					NodeList values = recordEl.getElementsByTagName("value");
					parseValues(values, recordId, batchId);
				}
				catch (ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		public void parseValues(NodeList values, int recordId, int batchId)
		{
			/*
			 * need field_id, record_id and batch_id
			 */
			
			int realId = fieldId - num_of_fields;
			for (int i =0;i<values.getLength();i++)
			{
				String value = values.item(i).getTextContent();
				Value val = new Value();
				val.setActual(value.toLowerCase());
				val.setBatch_id(batchId);
				val.setField_id(realId);
				realId++;
				val.setRecord_id(recordId);
				
				
				try
				{
				
					db_access_values valuesDBO = db.getValuesDBO();
					valuesDBO.add(val);
				}
				catch (ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		
}
