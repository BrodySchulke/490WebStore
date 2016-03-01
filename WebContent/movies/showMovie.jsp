<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/login.js"></script>
<title>The Three Stooges' Exquisite Exclusive Movie View</title>
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
</head>
<body>
<table>
	<tr> 
		<th>Release Year</th>
		<th>Length</th>
		<th>Title</th>
		<th>Genre</th>
		<th>Actor</th>
		<th>Actress</th>
		<th>Director</th>
		<th>Inventory</th>
		<th>Price</th>
		<th>Product Id</th>
	</tr>
<%
	Movie m = MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_product_id")));
%>
<tr>
	<td width="10%"><%=m.getRelease_year()%></td>
	<td width="10%"><%=m.getLength()%></td>
	<td width="10%"><%=m.getTitle()%></td>
	<td width="10%"><%=m.getGenre()%></td>
	<td width="10%"><%=m.getActor()%></td>
	<td width="10%"><%=m.getActress()%></td>
	<td width="10%"><%=m.getDirector()%></td>
	<td width="10%"><%=m.getInventory()%></td>
	<td width="10%"><%=m.getPrice()%></td>
	<td width="10%"><%=m.getProduct_id()%></td>
</tr>
</table>
<button id="add_to_cart">Add To Cart</button>
<script>
window.onload = function () {
	var orderButton = document.getElementById("add_to_cart");
	orderButton.onclick = function() {
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "../orders/cart", true);
		var mystring = "<%=m.toString()%>";
		xhttp.send(mystring);
	}
}
</script>
<!-- <script>
function addToCart(product_id) {
	document.getElementById("product_id").value=product_id;
	document.viewMovie.submit();
}
</script>
</table>
</form>
<form name="viewMovie" method="post" action="../orders/cart">
<input type="hidden" name="movie_product_id" id="product_id">
</form> -->
</body>
</html>