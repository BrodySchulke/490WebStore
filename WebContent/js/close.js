window.onload = function () {
	window.onbeforeunload = function(event) {
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "../order/sync", true);
		xhttp.send();
	}
}
