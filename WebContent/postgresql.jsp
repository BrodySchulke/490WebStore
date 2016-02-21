<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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