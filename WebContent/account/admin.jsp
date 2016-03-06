<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%@ page import="java.util.*, db.OrderDB, db.RatingDB, db.TransactionDB, model.Customer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Customer user = (Customer)request.getSession().getAttribute("customer");
if (user.getUsername().equals("admin")) {	 }
else { response.sendRedirect("../home/loginForm.html"); }
%>
<p>Monthly Aggregate Sales: <%=TransactionDB.getMonthlyAggregateSales() %></p>
<p>Weekly Aggregate Sales: <%=TransactionDB.getWeeklyAggregateSales() %></p>
<p>Monthly Difference Sales: <%=TransactionDB.getMonthlyDifferenceAggregate() %></p>
<p>Weekly Difference Sales: <%=TransactionDB.getWeeklyDifferenceAggregate() %></p>
<p>Top Ten Best Sellers Weekly: <%=OrderDB.getWeeklyBestSellersByIndividuals() %></p>
<p>Top Ten Highest Rated Movies for The Past Two Weeks: <%=RatingDB.getMostFavoriteMoviesBiWeekly() %></p>
<p>Average Number of Closed Transactions Per Day for The Past Two Years: <%=TransactionDB.getAverageOfClosedTransactionsPerDayLastYear() %>
<p>Create a Movie: </p>
		<form action="../movies/create" method="post">
			<fieldset>
				<legend class="legend_text">Movie's Info</legend>
				<div><span class="star">*</span><label> Title : </label>
					<input type="text" name="title"/></div>
				<div><span class="star">*</span><label> Genre : </label>
					<input type="text" name="genre"/></div>
				<div><span class="star">*</span><label> Release year : </label>
					<input type="number" name="year" min=1900 max=2020 value="2016"/></div>
				<div><span class="star">*</span><label> Length : </label>
					<input type="number" name="length" min=1 max=500 value="120"/></div>
				<div><span class="star">*</span><label> Actor : </label>
					<input type="text" name="actor"/></div>
				<div><span class="star">*</span><label> Actress : </label>
					<input type="text" name="actress"/></div>
				<div><span class="star">*</span><label> Director : </label>
					<input type="text" name="director"/></div>
				<div><span class="star">*</span><label> Price : </label>
					<input type="number" name="price" min=4.99 max=19.99 value="19.99"/></div>
				<div><span class="star">*</span><label> Inventory : </label>
					<input type="number" name="inventory" min=0 max=500 value="500"/></div>
					<span class="emph"></span></div>
				<input type="hidden" name="product_id" value="0">
				<input type="submit" value="Create Movie" id="submit"/>
			</fieldset>
			<div><span class="star">*</span><label>Mandatory Information</label></div><br/>
		</form>
</body>
</html>