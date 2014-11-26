package org.got5.tapestry5.highcharts.test.pages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;


@Import(stylesheet="context:css/samples.css")
public class ExportCsvSample {
	@Inject
	private Request request;
	
	public JSONArray getCategories(){
		return new JSONArray("Jan","Feb","Mar");
	}
	
	public JSONArray getSeries(){
		JSONObject serie1= new JSONObject();
		serie1.put("name", "Tokyo");
		serie1.put("data", new JSONArray(49.9, 71.5, 106.4));
		JSONObject serie2= new JSONObject();
		serie2.put("name", "New York");
		serie2.put("data", new JSONArray(83.6, 78.8, 98.5));		
		JSONObject serie3= new JSONObject();
		serie3.put("name", "London");
		serie3.put("data", new JSONArray(48.9, 38.8, 39.3));	

		return new JSONArray(serie1, serie2, serie3);
	}
	
	public JSONArray getSeries2(){
		JSONObject serie1= new JSONObject();
		serie1.put("name", "Jesus");
		serie1.put("data", new JSONArray(10.9, 15.5, 30.4));
		JSONObject serie2= new JSONObject();
		serie2.put("name", "Abraham");
		serie2.put("data", new JSONArray(3.6, 8.8, 30.5));		

		return new JSONArray(serie1, serie2);
	}
	
	
	/**
	 * Permits to save csv data between ajax request on method onActionFromJavaExportlink and normal call to this same method just after
	 */
	@Persist
	private String csv;
	
	/**
	 * Url for calling method onActionFromJavaExportlink
	 * @return
	 */
	public String getUrlPathForJavaExportlink(){
		return request.getContextPath()+"/"+ExportCsvSample.class.getSimpleName().toLowerCase()+".javaExportlink";
		
	}
	
	
	Object onActionFromJavaExportlink(){
		List<String> parameterNames = request.getParameterNames();
		if(request.isXHR()){
		
			for (String string : parameterNames) {
				String val = request.getParameter(string); 
				if(string.equals("csv")){
					csv=val;
					return null;
				}
			}
		}
		
		if(csv==null){
			csv="no csv";
		}else{
			csv="Result from java ; " + csv;
		}
		 return new StreamResponse() {
	            InputStream inputStream;

	            public void prepareResponse(Response response) {
	            	
	                inputStream =  new ByteArrayInputStream(csv.getBytes());

	                try {
	                	
	                    response.setHeader("Content-Length", "" + inputStream.available());
	                    response.setHeader("Content-Disposition", "inline; filename=demo.xls");
	                }
	                catch (IOException e) {
	                	e.printStackTrace();
	                }
	            }

	            public String getContentType() {
	                return "application/vnd.ms-excel";
	            }

	            public InputStream getStream() throws IOException {
	                return inputStream;
	            }
	        };
	}
	
	


}
