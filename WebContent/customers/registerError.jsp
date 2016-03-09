<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Error</title>
<% if (request.getSession().getAttribute("customer") == null) {
	response.sendRedirect("../home/loginForm.html");
	return;
}
%>
</head>
<body>
<p>That username is taken, please try again.</p>
<a href="../signup/signUpForm.html">Sign Up</a>
</body>
</html>