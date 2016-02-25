<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<h2>Database Connectivity Test</h2>
<%
	Connection conn = null;
	try{
		String dbURL = "jdbc:postgresql://localhost:5432/webstore";
		String dbUser = "css490";
		String dbPass = "css490pass";
		
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		out.println("Successfully connected");
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>