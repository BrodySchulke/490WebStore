<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import = "java.util.*, model.Movie, db.MovieDB" %>
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
	}
	
	.box {
	float:left;
	text-align: center;
	width: 25%;
	height: 25%;
	}
	
	
	button {
	margin-right: 5%;
	margin-left: 5%;
	margin-top: 5%;
	margin-bottom: 5%;
	float: right;
	width: 40%;
	display: inline;
	}
	
	#header {
	position: fixed;
	width: 100%;
	background-color: #000;
	}
	
	h2, button {
	color: #fff;
	}
	
</style>
</head>
<body>
<!-- should present movies in different way, maybe like grid -->
<!-- this will be users' default home page. account settings/cart/past orders will live in top right -->
<div id="header"><h2>THE THREE STOOGES</h2>
<input id="search" type="text" placeholder="search our exclusive boutique..."/>
</div>
<form name="modify" method="post">
<%
	ArrayList<Movie> movies = MovieDB.viewMovies(0);
	for(Movie m : movies){
%>
<div class="box">
	<img src="../images/ThreeStoogesGif.gif"/>
	<p>
	<%=m.getRelease_year()%>
	<br/>
	<%=m.getLength()%>
	<br/>
	<%=m.getTitle()%>
	<br/>
	<%=m.getGenre()%>
	<br/>
	<%=m.getActor()%>
	<br/>
	<%=m.getActress()%>
	<br/>
	<%=m.getDirector()%>
	<br/>
	<%=m.getInventory()%>
	<br/>
	<%=m.getPrice()%>
	<br/>
	<%=m.getProduct_id()%>
	<br>
	<a href="javascript:viewAMovie(<%=m.getProduct_id()%>);">view</a>
	</p>
</div>
<script>
function viewAMovie(product_id) {
	document.getElementById("product_id").value=product_id;
	document.viewMovie.submit();
}
</script>

<%
	}
%>
</form>
<form name="viewMovie" method="get" action="showMovie.jsp">
<input type="hidden" name="movie_product_id" id="product_id">
</form>
<% if (movies.size() == 20) { 
	%>
<form action="../movies/show_next" method="get">
	<button type="submit">Next</button>
</form>
<% } else { %>
<h7>You've reached the end of the catalogue.  Thanks for viewing The Three Stooges' Antique Film Boutique.</h7>
<% } %>
<%
int offset = MovieDB.getOffset();
if (offset > 0) {
	%>
	<form action="../movies/show_previous" method="get">
		<button type="submit">Previous</button>
	</form>
<% } %>
</body>
</html>