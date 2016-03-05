<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, db.OrderDB, db.CustomerDB, model.Customer, db.TransactionDB, model.Transaction, model.Order, model.Rating"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/close.js"></script>
<title>List of Movies</title>
<style>
@import url(http://fonts.googleapis.com/css?family=Oswald);

body {
	font-family: 'Oswald', 'Futura', sans-serif;
	color: #fff;
	background-color: #000;
	margin: 0 auto;
}

.box {
	float: left;
	text-align: center;
	width: 20%;
	height: 20%;
}

button {
	margin-right: 5%;
	margin-left: 5%;
	margin-top: 5%;
	margin-bottom: 5%;
	float: right;
	width: 40%;
	display: inline;
	overflow: hidden;
}

h2, button {
	color: #fff;
}

#content {
	display: block;
}

div {
	border-radius: 5px;
	margin: auto;
}

#header {
	z-index: 1;
	position: fixed;
	background-color: #000;
	height: 100px;
	width: 100%;
	left: 0;
	right: 0;
	top: 0;
}

#content {
	margin-top: 100px;
}

#footer {
	background-color: #fff;
	height: 100px;
	clear: both;
	width: 100%;
	margin-top: 100%;
}

#header-e1 {
    float: left;
    width:33%;
    height: 100px;
}

#header-e2 {
    float: left;
    width:33%;
    height: 100px;
}

#header-e3 {
    float: left;
    width:33%;
    height: 100px;
}

h7 {
	color: #fff;
	text-align: center;
	margin-bottom: 20px;
	display: block;
}

#search {
width: 80%;
display: block;
padding-right: 10%;
padding-left: 10%;
color: #f;
}

#list-img {
width: 50%;
height: 50px;
display: block;
margin-left: 0;
float: left;
}

#list-img:hover {
height: 75px;
}

#cart-img {
width: 50%;
height: 50px;
display: block;
margin-right: 0;
float: right;
}

#cart-img:hover {
height: 75px;
}

.small {
width: 25px;
height: 25px;
display: inline;
}

.twentypercent {
width: 50%;
float: left;
}

#past-orders {
margin-left: 10px;
margin-right: 10px;
float: left;
width: 40%;
}

#past-ratings {
margin-left: 10px;
margin; right: 10px;
float: left;
width: 40%;
}

</style>
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
				<p>Total Price: <%=t.getTotal_price() %></p>
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
<div id="footer"></div>
</body>
</html>