<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import = "java.util.*, model.Customer, db.CustomerDB" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/login.js"></script>
<title>List of Customers</title>
<style>
	body {
				font-family: Arial, Verdana, sans-serif;
				color: #111111;}
	table {
				width: 800px;
				margin: auto;
				border-collapse: collapse;
				}
	th, td {
				padding: 12px 10px;}
	th{
		
		text-transform: uppercase;
		letter-spacing: 0.1em;
		font-size: 90%;
		border-bottom: 2px solid #111111;
		border-top: 1px solid #999;
		text-align: left;
	}
	
	tr:nth-child(even){
		background-color: #efefef;
	}
	
	tr:hover {
		background-color: #c3e6e5;
	}
	
	tr:last-child{
		border-bottom: 2px solid #111111;
	}
</style>
<script>
	function userModify(username){
		document.getElementById("username1").value= username;
		document.userModify.submit();
	}

	function userDelete(username){
		document.getElementById("username2").value= username;
		document.userDelete.submit();
	}
</script>
</head>
<body>
<form name="modify" method="post">
<table id="list">
	<tr> 
		<th>Username</th>
		<th>Email</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Date Joined</th>
	</tr>
<%
	ArrayList<Customer> customers = CustomerDB.showCustomers();
	for(Customer c: customers){
%>
<tr>
	<td width="20%"><%=c.getUsername()%></td>
	<td width="20%"><%=c.getEmail()%></td>
	<td width="20%"><%=c.getFirst_name()%></td>
	<td width="20%"><%=c.getLast_name()%></td>
	<td width="20%"><%=c.getDate_joined()%></td>
</tr>
<%
	}
%>
</table>
</form>
</body>
</html>