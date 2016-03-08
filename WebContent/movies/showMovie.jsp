<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>The Three Stooges' Exquisite Exclusive Movie View</title>
<link rel="stylesheet" href="../css/showmovie.css" />
</head>
<body>
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
            <a href="../movies/listMovies.jsp"><img src="../images/Shopping-List-Clipboard-Tablet.svg" id="list-img"/></a>
        </div>
        <div id="header-e3">
            <a href="../cart/cart.jsp">
                <img src ="../images/shopingcart.svg" id="cart-img"/>
            </a>
        </div>
    </div>
<div id="content">
<%
    Movie m = MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_product_id")));
%>
<div class="box">
	<img src="../images/movie-clapperboard.svg" id="clapper"/>
	<br/>
	Rating:<%=RatingDB.getMovieRating(m)%>
	<img class="small" src="../images/Outlined-star-45623.svg"/>
	<br />
	Year: <%=m.getRelease_year()%>
	<br />
	Length: <%=m.getLength()%> minutes
	<br />
	Title: <%=m.getTitle()%>
	<br />
	Genre: <%=m.getGenre()%>
	<br />
	Actor: <%=m.getActor()%>
	<br />
	Actress: <%=m.getActress()%>
	<br />
	Director: <%=m.getDirector()%>
	<br />
	$<%=m.getPrice()%>
	<br/>
	<div id="container">
	<div><img src="../images/Outlined-star-45623.svg" id="1" class="star"/></div>
	<div><img src="../images/Outlined-star-45623.svg" id="2" class="star"/></div>
	<div><img src="../images/Outlined-star-45623.svg" id="3" class="star"/></div>
	<div><img src="../images/Outlined-star-45623.svg" id="4" class="star"/></div>
	<div><img src="../images/Outlined-star-45623.svg" id="5" class="star"/></div>
	</div>
	<button id="add_to_cart">Add To Cart</button>
</div>
</div>
<script>

var orderButton = document.getElementById("add_to_cart");
orderButton.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "../orders/cart", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
    alert("Movie added to cart!");
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
star1.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var response = this.responseText;
                alert(response);
            };
        };
    };
    xhttp.open("POST", "../ratings/star1", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
}
star2.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var response = this.responseText;
                alert(response);
            };
        };
    };
    xhttp.open("POST", "../ratings/star2", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
}
star3.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var response = this.responseText;
                alert(response);
            };
        };
    };
    xhttp.open("POST", "../ratings/star3", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
}
star4.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var response = this.responseText;
                alert(response);
            };
        };
    };
    xhttp.open("POST", "../ratings/star4", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
}
star5.onclick = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var response = this.responseText;
                alert(response);
            };
        };
    };
    xhttp.open("POST", "../ratings/star5", true);
    var mystring = "<%=m.toString()%>";
    xhttp.send(mystring);
}
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
</script>
</body>
</html>