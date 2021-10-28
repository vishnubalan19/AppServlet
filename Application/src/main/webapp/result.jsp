<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="white">

<%@page import=" javax.servlet.http.HttpServlet"%>
<%@page import=" javax.servlet.http.HttpServletRequest"%>
 
<%@page import=" javax.servlet.http.HttpServletResponse"%>
<%@page import=" java.util.List"%>
<%@page import=" java.io.IOException"%>
<%@page import=" java.io.PrintWriter"%>
<%@page import="com.influxdb.query.*"%>
<%@page import="com.influxdb.client.*" %>
<!-- import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord; -->
<h1 align="center">Table</h1>
<%-- <%
		String bucket="dbmonitor";
		//String bucket = ""
		String org="demo";
		String token="MglMIe5ROZDDmdzIZGoUUhPtsct8Zdf_YKep_M1GQjEzRS8Akx5HbS6B1Sp-YYsULb_GSSDpzaS6FAlsTxqtkg==";
		//String token = "eqWXHf4eBnGcaSg6x-n_JwnPM2gkL4MyOWlwVCsZzxOOhtowigqE5L4Z51IfV77eThUKuq4YLsIluhu7270xxw==";
		
		String start = request.getParameter("value1");
		String stop = request.getParameter("value2");
		System.out.println(start+" "+stop);
		
		if(!start.isEmpty()){
			String query = String.format("from(bucket: \"%s\") |> range(start: %s, stop: %s)", bucket,start,stop);
			if(stop.isEmpty()){
				query = String.format("from(bucket: \"%s\") |> range(start: %s)", bucket,start);
			}
			InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
			//InfluxDBClient client = InfluxDBClientFactory.create("http://172.22.0.2:8086", token.toCharArray());
			//String query = String.format("from(bucket: \"%s\") |> range(start: %s)", bucket,start);
			List<FluxTable> tables = client.getQueryApi().query(query, org);
			//PrintWriter printWriter = response.getWriter();
			//out.println();
			//out.println();
			%> --%>
			<div align="center">
				<table border="1">
			<tr>
				<th>Time</th>
				<th>Measurement</th>
				<th>Field</th>
				<th>Value</th>
			</tr>
			
			<%
			//iterating the records
			List<FluxTable> tables= (List<FluxTable>)request.getAttribute("tables");
			for(FluxTable fluxTable : tables){
			  List<FluxRecord> records = fluxTable.getRecords();
			  for (FluxRecord fluxRecord : records) {
			%>
				 <tr>
				 <td><%out.print(fluxRecord.getTime()); %></td>
				<td><%= fluxRecord.getMeasurement() %> </td>
 				<td><%out.print(fluxRecord.getField()); %></td>
				 <td><%out.print(fluxRecord.getValue()); %></td>
				 </tr> 
				  
				  <%
			  }
			  out.println();
			}
			%>
			</table>
		</div>
</body>
</html>