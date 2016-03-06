<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Order, model.Customer, db.TransactionDB, model.Transaction, db.OrderDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/cart.js"></script>
<title>Your Cart</title>
<link rel="stylesheet" type="text/css" href="../css/cart.css">
</head>
<body>
	<!-- should present movies in different way, maybe like grid -->
	<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
	<div id="header">
		<div id="header-e1">
			<a href="../movies/listMovies.jsp"><img src="../images/Shopping-List-Clipboard-Tablet.svg" id="list-img"/></a>
		</div>
		<div id="header-e2">
			<h7>THREE STOOGES EXCLUSIVE ANTIQUE FILM BOUTIQUE</h7>
		</div>
		<div id="header-e3">
		<%if (((Customer)request.getSession().getAttribute("customer")).getUsername().equals("admin")) {%>
			<a href="../account/admin.jsp">
				<img src="../images/user1.svg" id="user-img"/>
			</a>
			<% } else { %>
				<a href="../account/account.jsp">
				<img src="../images/user1.svg" id="user-img"/>
			</a>
		<% } %>
		</div>
	</div>
	<div id="content">	
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