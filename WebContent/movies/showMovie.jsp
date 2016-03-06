<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/close.js"></script>
<title>The Three Stooges' Exquisite Exclusive Movie View</title>
<style>
    @import url(http://fonts.googleapis.com/css?family=Oswald);
body {
    font-family: 'Oswald', 'Futura', sans-serif;
    color: #fff;
    background-color: #000;
    margin: 0 auto;
}
.box {
    text-align: center;
}
button {
    margin: 0 auto;
    margin-top: 5px;
    margin-bottom: 5px;
}
#search {
width: 80%;
display: block;
padding-right: 10%;
padding-left: 10%;
color: #fff;
}
h2 {
    color: #fff;
}
#content {
    display: block;
}
    .star {
    width: 25px;
    cursor: pointer;
    }
    
    @-moz-keyframes spin { 100% { -moz-transform: rotate(360deg); } }
    @-webkit-keyframes spin { 100% { -webkit-transform: rotate(360deg); } }
    @keyframes spin { 100% { -webkit-transform: rotate(360deg); transform:rotate(360deg); } }
    .largestar {
    width: 25px;
    cursor: pointer;
    -webkit-animation:spin 1s linear infinite;
    -moz-animation:spin 1s linear infinite;
    animation:spin 1s linear infinite;
    }
    
    #content {
    margin-top: 100px;
    text-align: center;
}
#footer {
    background-color: #fff;
    height: 100px;
    clear: both;
    width: 100%;
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
#container > div {
    width: 25px;
    display: inline-block;
    vertical-align: top;
    *display: inline;
    }
#container:after {
    content: "";
    width: 100%;
    display: inline-block;
    }
#clapper {
	width: 75px;
	height: 75px;
}

#list-img {
width: 50%;
height: 50px;
display: block;
margin-left: 0;
float: left;
}

#list-img:hover {
height: 75px;
}
</style>
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
	<%=RatingDB.getRating(m)%>
	<img class="small" src="../images/Outlined-star-45623.svg"/>
	<br />
	Year: <%=m.getRelease_year()%>
	<br />
	<%=m.getLength()%> minutes
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
<script>

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
</div>
<div id="footer"></div>
</body>
</html>