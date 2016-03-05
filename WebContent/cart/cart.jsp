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
	
}

.cart-icon:hover {
width: 35px;
}

h2 {
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
<script>
function removeCart(m) {
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "../orders/remove", false);
	xhttp.send(m);
 	location.reload();
}
function addCart(m) {
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "../orders/addto", false);
	xhttp.send(m);
 	location.reload();
}
</script>	
<script>
window.onload = function() {
	window.onbeforeunload = function(event) {
		<% System.out.println("trigger"); %>
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "../orders/sync", true);
		xhttp.send();
	}
	var purchase = document.getElementById("purchase_button");
	purchase.onclick = function(){
		 var xhttp = new XMLHttpRequest();
	     xhttp.onreadystatechange = function() {
	         if (this.readyState == 4) {
	             if (this.status == 200) {
	                 var response = this.responseText;
	                 if (response === "") {
	                	 location.href='../movies/listMovies.jsp';
	                 } else {
	                 	alert(response);
	                 }
	             };
	         };
	     };
	     xhttp.open("POST", "../transactions/purchase", true);
	     xhttp.send();
	}
}
</script>	
	<p>Your cart</p>
		<%
			System.out.println("here again");
			HttpSession sesh = request.getSession();
			Map<Movie, Order> cart = (Map<Movie, Order>)sesh.getAttribute("cart");
			System.out.println("cart" + cart);
			Transaction t = (Transaction)sesh.getAttribute("transaction");
			for (Map.Entry e : cart.entrySet()) {	
				Movie m = (Movie)e.getKey();
				Order o = (Order)cart.get(e.getKey());
		%>
			<div>
				<span id="title<%=m.getProduct_id() %>"><%= "Title: " + m.getTitle() %> </span>
				<span id="quantity<%=m.getProduct_id() %>"><%= "Quantity: " + o.getQuantity() %> </span>
				<span id="price<%=m.getProduct_id() %>"><%= "Price: $" + String.format("%.2f",o.getPrice()) %> </span>
				<span><a href="javascript:removeCart(&quot;<%=m%>&quot;)"><img src="../images/egore911-trash-can.svg" width="25" class="cart-icon"/></a></span>
				<span><a href="javascript:addCart(&quot;<%=m%>&quot;)"><img src="../images/tasto-2-architetto-franc-01-black-border.svg" width="25" class="cart-icon"/></a></span>
			</div>
		<%
		}
		%>
		<div>
			<span id="subtotal"><%= "Subtotal: $" + String.format("%.2f",t.getTotal_price()) %></span>
		</div>
		<div>
			<button id="purchase_button">Purchase</button>
		</div>
	</div>
<div id="footer"></div>
</body>
</html>