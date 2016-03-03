<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Order, model.Customer, db.TransactionDB, model.Transaction, db.OrderDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/close.js"></script>
<title>Your Cart</title>
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
}

#search {
width: 80%;
display: block;
padding-right: 10%;
padding-left: 10%;
color: #f;
}

</style>
</head>
<body>
	<!-- should present movies in different way, maybe like grid -->
	<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
	<div id="header">
		<div id="header-e1">
		</div>
		<div id="header-e2">
			<h7>THREE STOOGES EXCLUSIVE ANTIQUE FILM BOUTIQUE</h7>
			<form>
				<input type="text" id="search" placeholder="search our exclusive boutique..." />
			</form>
		</div>
		<div id="header-e3">
		</div>
	</div>
	<div id="content">
	<p>Your cart</p>
		<%

			HttpSession sesh = request.getSession();
			Map<Movie, Order> cart = (Map<Movie, Order>)sesh.getAttribute("cart");
			Transaction t = (Transaction)sesh.getAttribute("transaction");
			int i = 0;
			for (Map.Entry e : cart.entrySet()) {	
			
				Movie m = (Movie)e.getKey();
				System.out.println(m);
				Order o = (Order)cart.get(e.getKey());
		%>
			<div>
				<span name="title<%=i %>"><%= "Title: " + m.getTitle() %> </span>
				<span name="quantity<%=i %>"><%= "Quantity: " + o.getQuantity() %> </span>
				<span name="price<%=i %>"><%= "Price: $" + o.getPrice() %> </span>
				<img id="cart-remove<%=i %>" height="25" width="25" src="../images/egore911-trash-can.svg"/>
				<script>
window.onload = function(){
	var trashCan = document.getElementsByClassName("cart-remove");
	console.log(trashCan);
	trashCan.onClick = function () {
		console.log("hi");
		this.previousSibling.innerHTML = "dog";
		var xhttp = new XMLHttpRequest();
		//add this routing and method
		xhttp.open("POST", "../orders/remove_cart", true);
		var mystring = "<%=m.toString()%>";
		xhttp.send(mystring);
	}
}
</script>
			</div>
			
<%-- 			<%= cart.get(e.getKey()) %> --%>
			<%-- <p>
			Past Orders:
			</p>
			<p>
			<%
			for (Transaction t : TransactionDB.getClosedTransactions((Customer)sesh.getAttribute("customer"))) {
			%>
			<p>
				<% 
				Order o = OrderDB.getOrder(t); 
				Movie m = MovieDB.getMovie(o);
				%>
				<%=m%>
				<%=o%>
			</p>
			<%
			}
			%> --%>	
			
		<%
		i++;
		}
		%>
		<div>
				<span name="total_price"><%= "Subtotal: $" + String.format("%.2f",t.getTotal_price()) %></span>
		</div>
	</div>
<div id="footer"></div>
</body>
</html>