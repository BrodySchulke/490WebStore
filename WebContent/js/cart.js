
function removeCart(m) {
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "../orders/remove", false);
	xhttp.send(m);
 	location.reload();
}
function addCart(m) {
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "../orders/addto", false);
	xhttp.send(m);
 	location.reload();
}

window.onload = function() {
	window.onbeforeunload = function(event) {
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "../orders/sync", true);
		xhttp.send();
	}
	var purchase = document.getElementById("purchase_button");
	purchase.onclick = function(){
		 var xhttp = new XMLHttpRequest();
	     xhttp.onreadystatechange = function() {
	         if (this.readyState == 4) {
	             if (this.status == 200) {
	                 var response = this.responseText;
	                 if (response === "") {
	                	 location.href='../movies/listMovies.jsp';
	                 } else {
	                 	alert(response);
	                 	location.href='../movies/listMovies.jsp';
	                 }
	             };
	         };
	     };
	     xhttp.open("POST", "../transactions/purchase", true);
	     xhttp.send();
	}
}
