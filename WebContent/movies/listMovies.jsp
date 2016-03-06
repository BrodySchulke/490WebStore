<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/close.js"></script>
<title>List of Movies</title>
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
color: #fff;
}
#user-img {
width: 50%;
height: 50px;
display: block;
margin-left: 0;
float: left;
}
#user-img:hover {
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
</style>
</head>
<body>
	<!-- should present movies in different way, maybe like grid -->
	<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
	<div id="header">
		<div id="header-e1">
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
		<div id="header-e2">
			<h7>THREE STOOGES EXCLUSIVE ANTIQUE FILM BOUTIQUE</h7>
			<form>
				<input type="text" id="search" placeholder="search our exclusive boutique..." />
			</form>
		</div>
		<div id="header-e3">
		    <a href="../cart/cart.jsp">
		    	<img src ="../images/shopingcart.svg" id="cart-img"/>
		    </a>
		</div>
	</div>
	<div id="content">
		<form id="movies" name="movies" method="post">
		<table id="list">
			<tr>
				<th>Title</th>
				<th>Genre</th>
				<th>Release year</th>
				<th>Length</th>
				<th>Actor</th>
				<th>Actress</th>
				<th>Director</th>
				<th>Price</th>
				<th>Rating</th>
				<th>View</th>
			</tr>
			<%
				ArrayList<Movie> movies = MovieDB.viewMovies(0);
				for (Movie m : movies) {
			%>
			<tr>
				<td width="10%"><%=m.getTitle()%></td>
				<td width="10%"><%=m.getGenre()%></td>
				<td width="10%"><%=m.getRelease_year()%></td>
				<td width="10%"><%=m.getLength()%> minutes</td>
				<td width="10%"><%=m.getActor()%></td>
				<td width="10%"><%=m.getActress()%></td>
				<td width="10%"><%=m.getDirector()%></td>
				<td width="10%"><%=m.getPrice()%></td>
				<td width="10%"><%=RatingDB.getRating(m)%></td>
				<td width="10%"><a href="javascript:viewAMovie(<%=m.getProduct_id()%>);">view</a>
				</td>
			</tr>

			<script>
				function viewAMovie(product_id) {
					document.getElementById("product_id").value = product_id;
					<%if (((Customer)request.getSession().getAttribute("customer")).getUsername().equals("admin")) {%>
						document.modifyMovie.submit();
					<% } else {%>
					
					document.viewMovie.submit();
					<% } %>
				}
			</script>

			<%
				}
			%>
		</form>
	<form name="viewMovie" method="get" action="showMovie.jsp">
		<input type="hidden" name="movie_product_id" id="product_id">
	</form>
	<form name="modifyMovie" method="get" action="modifyMovie.jsp">
		<input type="hidden" name="movie_product_id" id="product_id">
	</form>
	<%
		if (movies.size() == 20) {
	%>
	<form action="../movies/show_next" method="get">
		<button type="submit">Next</button>
	</form>
	<%
		} else {
	%>
	<h7>You've reached the end of the catalogue. Thanks for viewing The Three Stooges' Antique Film Boutique.</h7>
	<%
		}
	%>
	<%
		int offset = MovieDB.getOffset();
		if (offset > 0) {
	%>
	<form action="../movies/show_previous" method="get">
		<button type="submit">Previous</button>
	</form>
	<% } %>
</div>
	
<div id="footer"></div>
</body>
</html>