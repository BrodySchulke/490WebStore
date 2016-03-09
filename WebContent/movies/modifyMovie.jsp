<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>The Three Stooges' Exquisite Exclusive Movie View</title>
<html>
<head><title>Modify Movie Information Form</title>
<% if (request.getSession().getAttribute("customer") == null) {
	response.sendRedirect("../home/loginForm.html");
	return;
}
%>
</head>
	<body>
	<%
		Movie m = MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_product_id")));
	%>  
		<form action="../movies/modify" method="post">
			<fieldset>
				<legend class="legend_text">Movie's Info</legend>
				<div><span class="star">*</span><label> Title : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Genre : </label>
					<span class="emph"><%=m.getGenre() %></span></div>
				<div><span class="star">*</span><label> Release year : </label>
					<span class="emph"><%=m.getRelease_year() %></span></div>
				<div><span class="star">*</span><label> Length : </label>
					<span class="emph"><%=m.getLength() %></span></div>
				<div><span class="star">*</span><label> Actor : </label>
					<span class="emph"><%=m.getActor() %></span></div>
				<div><span class="star">*</span><label> Actress : </label>
					<span class="emph"><%=m.getActress() %></span></div>
				<div><span class="star">*</span><label> Director : </label>
					<span class="emph"><%=m.getDirector() %></span></div>
				<div><span class="star">*</span><label> Price : </label>
					<input type="number" name="price" min=4.99 max=19.99 value="<%=m.getPrice()%>"/></div>
				<div><span class="star">*</span><label> Inventory : </label>
					<input type="number" name="inventory" min=0 max=500 value="<%=m.getInventory()%>"/></div>
				<div><span class="star">*</span><label> Rating : </label>
					<span class="emph"><%=RatingDB.getMovieRating(m) %></span></div>
				<div id="warning" class="feedback"></div>
				<input type="hidden" name="product_id" value=<%=m.getProduct_id() %>>
				<input type="submit" value="Update Movie" id="submit"/>
			</fieldset>
			<div><span class="star">*</span><label>Mandatory Information</label></div><br/>
		</form>
	</body>
</html>