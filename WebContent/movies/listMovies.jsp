<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="../js/login.js"></script>
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

#user-img {
width: 50%;
height: 50px;
float: left;
}

#cart-img {
width: 50%;
height: 50px;
float: right;
}

</style>
</head>
<body>
	<!-- should present movies in different way, maybe like grid -->
	<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
	<div id="header">
		<div id="header-e1">
			<form>
				<input type="image" src="../images/user1.svg" name="user-hdr-btn-submit" id="user-img"/>
			</form>
		</div>
		<div id="header-e2">
			<h7>THREE STOOGES EXCLUSIVE ANTIQUE FILM BOUTIQUE</h7>
			<form>
				<input type="text" id="search" placeholder="search our exclusive boutique..." />
			</form>
		</div>
		<div id="header-e3">
			<form>
				<input type="image" src="../images/shopingcart.svg" name="cart-hdr-btn-submit" id="cart-img"/>
			</form>
		</div>
	</div>
	<div id="content">
		<form id="movies" name="movies" method="post">
			<%
				ArrayList<Movie> movies = MovieDB.viewMovies(0);
				for (Movie m : movies) {
			%>
			<div class="box">
				<!--<img src="../images/ThreeStoogesGif.gif"/>-->
				<p>
					<%=m.getRelease_year()%>
					<br />
					<%=m.getLength()%>
					<br />
					<%=m.getTitle()%>
					<br />
					<%=m.getGenre()%>
					<br />
					<%=m.getActor()%>
					<br />
					<%=m.getActress()%>
					<br />
					<%=m.getDirector()%>
					<br />
					<%=m.getInventory()%>
					<br />
					<%=m.getPrice()%>
					<br />
					<%=m.getProduct_id()%>
					<br> <a href="javascript:viewAMovie(<%=m.getProduct_id()%>);">view</a>
				</p>
			</div>
			<script>
				function viewAMovie(product_id) {
					document.getElementById("product_id").value = product_id;
					document.viewMovie.submit();
				}
			</script>

			<%
				}
			%>
		</form>
	</div>
	<form name="viewMovie" method="get" action="showMovie.jsp">
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
<div id="footer"></div>
</body>
</html>