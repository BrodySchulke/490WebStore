<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, db.OrderDB, db.CustomerDB, model.Customer, db.TransactionDB, model.Transaction, model.Order, model.Rating"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/close.js"></script>
<link rel="stylesheet" type="text/css" href="../css/account.css">
<title>List of Movies</title>
</head>
<body>
	<!-- should present movies in different way, maybe like grid -->
	<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
	<div id="header">
		<div id="header-e1">
			<a href="../movies/listMovies.jsp"><img src="../images/Shopping-List-Clipboard-Tablet.svg" id="list-img"/></a>
		</div>
		<div id="header-e2">
		<h7>THREE STOOGES EXLCUSIVE ANTIQUE FILM BOUTIQUE</h7>
		</div>
		<div id="header-e3">
		<a href="../cart/cart.jsp">
		    	<img src ="../images/shopingcart.svg" id="cart-img"/>
		    </a>
		</div>
	</div>
	<div id="content">
	<div id="past-orders">
	<h5>PAST ORDERS</h5>
	<%
		HttpSession sesh = request.getSession();
		for (Transaction t : TransactionDB.getClosedTransactions((Customer)sesh.getAttribute("customer"))) {
			%>
			<div class="twentypercent">
			<ul>
				<% 
				List<Order> orders = OrderDB.getOrders(t);
				for (Order o : orders) {
					Movie m = MovieDB.getMovie(o);
				%>
				<p>Movie:</p>
				<li>Title: <%=m.getTitle() %></li>
				<li>Director: <%=m.getDirector() %></li>
				<li>Release Year: <%=m.getRelease_year() %></li>
				<li>Genre: <%=m.getGenre() %>
				<li>Price: $<%=m.getPrice() %>
				<li>Quantity Ordered: <%=o.getQuantity() %>
				<li>Quantity Price: $<%=o.getPrice() %>
				<li>Date Ordered: <%=o.getOrder_date() %>
				<%
				}
				%>
				</ul>
				<p>Total Price: $<%=t.getTotal_price() %></p>
				</div>
			<% 
			} 
			%>
			</div>
			<div id="past-ratings">
			<h5>PAST RATINGS</h5>
			<%
		HttpSession sesh2 = request.getSession();
			for (Rating r : RatingDB.getRatings((Customer)sesh.getAttribute("customer"))) { %>
			<div class="twentypercent">
				<% List<Movie> movies = MovieDB.getMovies(r);
				for (Movie m : movies) { %>
				<ul>
					<p>Movie:</p>
					<li>Title: <%=m.getTitle() %></li>
					<li>Director: <%=m.getDirector() %></li>
					<li>Release Year: <%=m.getRelease_year() %></li>
					<li>Genre: <%=m.getGenre() %>
					<li>Price: $<%=m.getPrice() %>
					<li>Rating: <%=r.getRating() %>
					<li>Date: <%=r.getTime_stamp() %>
					</ul>
					</div>
				<% } %>
			<% } %>
			</div>
	</div>
</body>
</html>