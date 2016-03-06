<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.*, model.Movie, db.MovieDB, db.RatingDB, model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/close.js"></script>
<title>The Three Stooges' Exquisite Exclusive Movie View</title>
<html>
<head><title>Modify Movie Information Form</title>
		<link rel="stylesheet" href="../css/c06.css" />
		<style>
			span.emph{
				color:yellow;
				font-size: 120%;
			}
		</style>
	</head>
	<body>
	<%
		Movie m = MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_product_id")));
	%>  
		<form action="user/modify" method="post">
			<fieldset>
				<legend class="legend_text">User's Info</legend>
				<div><span class="star">*</span><label> Title : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Genre : </label>
					<span class="emph"><%=m.getGenre() %></span></div>
				<div><span class="star">*</span><label> Release year : </label>
					<span class="emph"><%=m.getRelease_year() %></span></div>
				<div><span class="star">*</span><label> Length : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Actor : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Actress : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Director : </label>
					<span class="emph"><%=m.getTitle() %></span></div>
				<div><span class="star">*</span><label> Price : </label>
					<span class="emph"><%=m.getTitle() %></span></div>	
				<div><span class="star">*</span><label> Inventory : </label>
					<span class="emph"><%=m.getTitle() %></span></div>	
				<div><span class="star">*</span><label> Rating : </label>
					<span class="emph"><%=m.getTitle() %></span></div>			
				<div><span class="star">*</span><label>  Email: </label>
					<input type="text" name="email" id="email" value="<%=user.getEmail()%>"/></div>
				<div><span class="emph">You joined this website on <%=user.getSignUpDate() %>.</span></div>
				<div id="warning" class="feedback"></div>
				<input type="hidden" name="username" value=<%=username %>>
				<input type="submit" value="Sign Up" id="submit"/>
			</fieldset>
			<div><span class="star">*</span><label>Mandatory Information</label></div><br/>
		</form>
		<script>
   		var elMsg = document.getElementById('warning');
		 
		function loginCheck(){
	        if(document.getElementById('username').value==""){
	                elMsg.textContent= "Pleaes enter your username";
	                document.getElementById('username').focus();
	                return false;
	        }
	        if(document.getElementById('passwd').value==""){
	              	elMsg.textContent = "Please enter your password";
	                document.getElementById('passwd').focus();
	                return false;
	        }
	        if(document.getElementById('repasswd').value==""){
	        	elMsg.textContent = "Please confirm your password";
                document.getElementById('repasswd').focus();
                return false;
	        }
	        if(document.getElementById('passwd').value != document.getElementById('repasswd').value){
                elMsg.textContent = "The new password and confirmation password do not match.";
                document.getElementById('passwd').focus();
                return false;
        	}
	        if(document.getElementById('email').value==""){
	        	elMsg.textContent = "Please enter your email";
                document.getElementById('email').focus();
                return false;
	        }
		}
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick =loginCheck;
	</script>
	</body>
</html>