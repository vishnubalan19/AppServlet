package com.app;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import com.influxdb.*;

public class MainApp extends HttpServlet{
	
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		//String bucket="dbmonitor";
		String bucket = "tdbmonitor2";
		String org="demo";
		
		//token for system's influx
		String token="MglMIe5ROZDDmdzIZGoUUhPtsct8Zdf_YKep_M1GQjEzRS8Akx5HbS6B1Sp-YYsULb_GSSDpzaS6FAlsTxqtkg==";
		
		//token for docker's influx
		//String token = "eqWXHf4eBnGcaSg6x-n_JwnPM2gkL4MyOWlwVCsZzxOOhtowigqE5L4Z51IfV77eThUKuq4YLsIluhu7270xxw==";
		
		String start = request.getParameter("value1");
		String stop = request.getParameter("value2");
		System.out.println(start+" "+stop);
		
		//input verification and value generation 
		if(!start.isEmpty()){
			String query = String.format("from(bucket: \"%s\") |> range(start: %s, stop: %s)", bucket,start,stop);
			if(stop.isEmpty()){
				query = String.format("from(bucket: \"%s\") |> range(start: %s)", bucket,start);
			}
			InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
			//InfluxDBClient client = InfluxDBClientFactory.create("http://172.22.0.2:8086", token.toCharArray());
			List<FluxTable> tables = client.getQueryApi().query(query, org);
			request.setAttribute("tables", tables);
			
			//values are passed to result.jsp page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("result.jsp");
			requestDispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
		}
		
		
		
		
//		System.out.print("2");
//		String bucket="api";
//		String org="demo";
//		String token="MglMIe5ROZDDmdzIZGoUUhPtsct8Zdf_YKep_M1GQjEzRS8Akx5HbS6B1Sp-YYsULb_GSSDpzaS6FAlsTxqtkg==";
//		//String token = "eqWXHf4eBnGcaSg6x-n_JwnPM2gkL4MyOWlwVCsZzxOOhtowigqE5L4Z51IfV77eThUKuq4YLsIluhu7270xxw==";
//		
//		from(bucket: "dbmonitor1")
//		  |> range(start: 2021-10-22T10:41:00Z, stop: 2021-10-22T12:00:00Z)
		 
//		String start = request.getParameter("value1");
//		String stop = request.getParameter("value2");
//		System.out.println(start+" "+stop);
//		
//		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
//		//InfluxDBClient client = InfluxDBClientFactory.create("http://172.22.0.2:8086", token.toCharArray());
//		String query = String.format("from(bucket: \"%s\") |> range(start: %s, stop: %s)", bucket,start,stop);
//		//String query = String.format("from(bucket: \"%s\") |> range(start: %s)", bucket,start);
//		List<FluxTable> tables = client.getQueryApi().query(query, org);
//		PrintWriter printWriter = response.getWriter();
//		printWriter.println();
//		printWriter.println();
//		for(FluxTable fluxTable : tables){
//          //System.out.println(fluxTable.toString());
//          List<FluxRecord> records = fluxTable.getRecords();
//          for (FluxRecord fluxRecord : records) {
//              //printWriter.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//        	  printWriter.println(fluxRecord.getTime()+" : "+fluxRecord.getMeasurement()+" : "+fluxRecord.getField()+" : "+fluxRecord.getValue());
//          }
//      }
		
	}

}
