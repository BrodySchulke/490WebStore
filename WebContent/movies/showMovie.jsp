<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/close.js"></script>
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
	
	.star {
	width: 25px;
	display:inline;
	float: left;
	}
	@-moz-keyframes spin { 100% { -moz-transform: rotate(360deg); } }
	@-webkit-keyframes spin { 100% { -webkit-transform: rotate(360deg); } }
	@keyframes spin { 100% { -webkit-transform: rotate(360deg); transform:rotate(360deg); } }
	.largestar {
	width: 25px;
	display: inline;
	float: left;
	-webkit-animation:spin 1s linear infinite;
    -moz-animation:spin 1s linear infinite;
    animation:spin 1s linear infinite;
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
<form>
<input type="image" src="../images/Outlined-star-45623.svg" id="1" class="star"/>
<input type="image" src="../images/Outlined-star-45623.svg" id="2" class="star"/>
<input type="image" src="../images/Outlined-star-45623.svg" id="3" class="star"/>
<input type="image" src="../images/Outlined-star-45623.svg" id="4" class="star"/>
<input type="image" src="../images/Outlined-star-45623.svg" id="5" class="star"/>
</form>
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
	window.onbeforeunload = function(event) {
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "../orders/sync", true);
		xhttp.send();
	}
	var star1 = document.getElementById("1");
	var star2 = document.getElementById("2");
	var star3 = document.getElementById("3");
	var star4 = document.getElementById("4");
	var star5 = document.getElementById("5");
	star1.onmouseover = function() {
		star1.setAttribute("class", "largestar");
	}
	star2.onmouseover = function() {
		star1.setAttribute("class", "largestar");
		star2.setAttribute("class", "largestar");
	}
	star3.onmouseover = function() {
		star1.setAttribute("class", "largestar");
		star2.setAttribute("class", "largestar");
		star3.setAttribute("class", "largestar");
	}
	star4.onmouseover = function() {
		star1.setAttribute("class", "largestar");
		star2.setAttribute("class", "largestar");
		star3.setAttribute("class", "largestar");
		star4.setAttribute("class", "largestar");
	}
	star5.onmouseover = function() {
		star1.setAttribute("class", "largestar");
		star2.setAttribute("class", "largestar");
		star3.setAttribute("class", "largestar");
		star4.setAttribute("class", "largestar");
		star5.setAttribute("class", "largestar");
	}
	star1.onmouseout = function() {
		star1.setAttribute("class", "star");
		star2.setAttribute("class", "star");
		star3.setAttribute("class", "star");
		star4.setAttribute("class", "star");
		star5.setAttribute("class", "star");
	}
	star2.onmouseout = function() {
		star1.setAttribute("class", "star");
		star2.setAttribute("class", "star");
		star3.setAttribute("class", "star");
		star4.setAttribute("class", "star");
		star5.setAttribute("class", "star");
	}
	star3.onmouseout = function() {
		star1.setAttribute("class", "star");
		star2.setAttribute("class", "star");
		star3.setAttribute("class", "star");
		star4.setAttribute("class", "star");
		star5.setAttribute("class", "star");
	}
	star4.onmouseout = function() {
		star1.setAttribute("class", "star");
		star2.setAttribute("class", "star");
		star3.setAttribute("class", "star");
		star4.setAttribute("class", "star");
		star5.setAttribute("class", "star");
	}
	star5.onmouseout = function() {
		star1.setAttribute("class", "star");
		star2.setAttribute("class", "star");
		star3.setAttribute("class", "star");
		star4.setAttribute("class", "star");
		star5.setAttribute("class", "star");
	}
}
</script>
</body>
</html>