<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%@ page import="java.util.*, db.OrderDB, db.RatingDB, db.TransactionDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>Monthly Aggregate Sales: <%=TransactionDB.getMonthlyAggregateSales() %></p>
<p>Weekly Aggregate Sales: <%=TransactionDB.getWeeklyAggregateSales() %></p>
<p>Monthly Difference Sales: <%=TransactionDB.getMonthlyDifferenceAggregate() %></p>
<p>Weekly Difference Sales: <%=TransactionDB.getWeeklyDifferenceAggregate() %></p>
<p><%=OrderDB.getWeeklyBestSellersByIndividuals() %></p>
<p><%=OrderDB.getWeeklyBestSellersByCategory() %></p>
<p><%=RatingDB.getMostFavoriteMoviesBiWeekly() %></p>
</body>
</html>