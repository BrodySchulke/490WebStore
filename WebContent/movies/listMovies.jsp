<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="text/css" href="../css/listmovies.css">
<title>List of Movies</title>
<% if (request.getSession().getAttribute("customer") == null) {
	response.sendRedirect("../home/loginForm.html");
	return;
}
%>
</head>
<body>
<script language="JavaScript" type="text/javascript">
function checkform ( form ) {
	if (form.search.value.length < 1) {
	    form.search.focus();
		return false;
	}
	return true;
}
function getVals(){
	  // Get slider values
	  var parent = this.parentNode;
	  var slides = parent.getElementsByTagName("input");
	    var slide1 = parseFloat( slides[0].value );
	    var slide2 = parseFloat( slides[1].value );
	  // Neither slider will clip the other, so make sure we determine which is larger
	  if( slide1 > slide2 ){ var tmp = slide2; slide2 = slide1; slide1 = tmp; }
	  
	  var displayElement = parent.getElementsByClassName("rangeValues")[0];
	      displayElement.innerHTML = slide1 + " - " + slide2;
	}

	window.onload = function(){
	  // Initialize Sliders
	  var sliderSections = document.getElementsByClassName("range-slider");
	      for( var x = 0; x < sliderSections.length; x++ ){
	        var sliders = sliderSections[x].getElementsByTagName("input");
	        for( var y = 0; y < sliders.length; y++ ){
	          if( sliders[y].type ==="range" ){
	            sliders[y].oninput = getVals;
	            // Manually trigger event first time to display values
	            sliders[y].oninput();
	          }
	        }
	      }
	}
</script>
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
		<a href="javascript:logOut()">Log out</a>
		<div id="header-e2">
			<h7>THREE STOOGES EXCLUSIVE ANTIQUE FILM BOUTIQUE</h7>
			<form action="../movies/search" method="get" onsubmit="return checkform(this);" id="search-form">
				<input type="text" name="search_value" id="search" placeholder="search our exclusive boutique..." />
				<input type="submit" value="submit" id="submit"/>
			</form>
		</div>
		<div id="header-e3">
		    <a href="../cart/cart.jsp">
		    	<img src ="../images/shopingcart.svg" id="cart-img"/>
		    </a>
		</div>
	</div>
	<div id="content">
		<table id="list">
			<tr>
				<th><a href="javascript:sortMovies('title');">Title</a></th>
				<th><a href="javascript:sortMovies('genre');">Genre</a>
					<select name="filter" form="filter_genre">
					  <option value="Adventure">Adventure</option>
					  <option value="Drama">Drama</option>
					  <option value="Horror">Horror</option>
					  <option value="Comedy">Comedy</option>
					  <option value="Science Fiction">Science Fiction</option>
					  <option value="Fantasy">Fantasy</option>
					  <option value="Westerns">Westerns</option>
					  <option value="Western">Western</option>
					  <option value="Crime">Crime</option>
					  <option value="Action">Action</option>
					  <option value="Music">Music</option>
					  <option value="War">War</option>
					  <option value="Romance">Romance</option>
					  <option value="Mystery">Mystery</option>
					  <option value="War">Short</option>
					</select>
					<form name="filterGenre" method="get" action="../movies/filter_genre" id="filter_genre">
						<input type="submit" name="filter_genre" id="genre_filter">
					</form>
				</th>
				<th><a href="javascript:sortMovies('release_year');">Release year</a>
					<section class="range-slider">
						<span class="rangeValues"></span>
						<input value="1920" min="1920" max="2016" step="1" type="range" id="lowyear">
						<input value="2016" min="1920" max="2016" step="1" type="range" id="highyear">
					</section>
					<a href="javascript:filterYears();">Filter by year</a>
				</th>
				<th><a href="javascript:sortMovies('length');">Length</a></th>
				<th><a href="javascript:sortMovies('actor');">Actor</a></th>
				<th><a href="javascript:sortMovies('actress');">Actress</a></th>
				<th><a href="javascript:sortMovies('director');">Director</a></th>
				<th><a href="javascript:sortMovies('price');">Price</a>
					<section class="range-slider">
						<span class="rangeValues"></span>
						<input value="4.99" min="4.99" max="19.99" step="1" type="range" id="lowprice">
						<input value="19.99" min="4.99" max="19.99" step="1" type="range" id="highprice">
					</section>
					<a href="javascript:filterPrice();">Filter by price</a>
				</th>
				<th><a href="javascript:sortMovies('rating');">Rating</a>
					<select name="rating_select" id="rating_select">
					  <option value="1">1</option>
					  <option value="2">2</option>
					  <option value="3">3</option>
					  <option value="4">4</option>
					  <option value="5">5</option>
					</select>
					<a href="javascript:filterRatings();">Filter by rating</a>
				</th>
				<th>View</th>
			</tr>
			<%
			ArrayList<Movie> movies = new ArrayList<>();
			String narrow = (String)request.getSession().getAttribute("narrow");
			if (narrow == null) {
				movies = MovieDB.viewMovies((String)request.getSession().getAttribute("sort_value"), 0);
			} else if (narrow.equals("search")) {
				movies = MovieDB.viewMoviesSearch((String)request.getSession().getAttribute("search_value"), 0);
			} else if (narrow.equals("filter")) {
				movies = MovieDB.viewMoviesFilter((String[])request.getSession().getAttribute("filter_value"), 0);
			} else {
				movies = MovieDB.viewMovies((String)request.getSession().getAttribute("sort_value"), 0);
			}
				for (Movie m : movies) {
			%>
			<tr>
				<td width="9%"><%=m.getTitle()%></td>
				<td width="9%"><%=m.getGenre()%></td>
				<td width="9%"><%=m.getRelease_year()%></td>
				<td width="9%"><%=m.getLength()%> minutes</td>
				<td width="9%"><%=m.getActor()%></td>
				<td width="9%"><%=m.getActress()%></td>
				<td width="9%"><%=m.getDirector()%></td>
				<td width="9%">$<%=m.getPrice()%></td>
				<td width="9%"><%=RatingDB.getMovieRating(m)%></td>
				<%if (((Customer)request.getSession().getAttribute("customer")).getUsername().equals("admin")) {%><td width="9%"><%=m.getInventory()%></td><% } %>				
				<td width="9%"><a href="javascript:viewAMovie(<%=m.getProduct_id()%>);">view</a>
				</td>
			</tr>
			<script>
				function viewAMovie(product_id) {
					<%if (((Customer)request.getSession().getAttribute("customer")).getUsername().equals("admin")) {%>
						document.getElementById("modify_product_id").value = product_id;
						document.modifyMovie.submit();
					<% } else { %>
						document.getElementById("product_id").value = product_id;
						document.viewMovie.submit();
					<% } %>
				}
				function sortMovies(string) {
					document.getElementById("sort_movie").value = string;
					document.sortMovies.submit();
				}
				function filterYears() {
					var lowyear = document.getElementById("lowyear").value;
					var highyear = document.getElementById("highyear").value;
					document.getElementById("filter_years").value = lowyear + " " + highyear;
					document.getElementById("yearform").submit();
				}
				function filterPrice() {
					var lowprice = document.getElementById("lowprice").value;
					var highprice = document.getElementById("highprice").value;
					document.getElementById("filter_price").value = lowprice + " " + highprice;
					document.getElementById("priceform").submit();
				}
				function filterRatings() {
					var rating = document.getElementById("rating_select").value;
					document.getElementById("filter_rating").value = rating;
					document.getElementById("ratingform").submit();
				}
				function logOut() {
					console.log("logout func");
					document.getElementById("logoutForm").submit();
				}
			</script>

			<%
				}
			%>
	</table>
	<form name="viewMovie" method="get" action="showMovie.jsp">
		<input type="hidden" name="movie_product_id" id="product_id">
	</form>
	<form name="modifyMovie" method="get" action="modifyMovie.jsp">
		<input type="hidden" name="movie_product_id" id="modify_product_id">
	</form>
	<form name="sortMovies" method="get" action="../movies/sort">
		<input type="hidden" name="sort_value" id="sort_movie">
	</form>
	<form name="filter" method="get" action="../movies/filter_years" id="yearform">
		<input type="hidden" name="filter" id="filter_years">
	</form>
	<form name="filter" method="get" action="../movies/filter_price" id="priceform">
		<input type="hidden" name="filter" id="filter_price">
	</form>
	<form name="filter" method="get" action="../movies/filter_rating" id="ratingform">
		<input type="hidden" name="filter" id="filter_rating">
	</form>
		<form name="logout" method="post" action="../customers/logout" id="logoutForm">
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
</body>
</html>