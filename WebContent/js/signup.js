function checkform ( form ) {
	var reValidEmail = new RegExp('.+@.+[.].+');
	if (reValidEmail.test(form.email.value) == false) {
		document.getElementById("warning").innerHTML = "That isn't an email";
	    form.email.focus();
	    return false;
	}
	document.getElementById("warning").innerHTML = "";
	if (form.email.value.length < 4) {
		document.getElementById("warning").innerHTML = "Please type your email of at least 4 characters.";
	    form.email.focus();
	    return false;
	  }
   if (form.username.value.length < 4) {
	   document.getElementById("warning").innerHTML = "Please type your username of at least 4 characters.";
	   form.username.focus();
	   return false;
   }
   if (form.passwd.value.length < 4) {
	   document.getElementById("warning").innerHTML = "Please type a password of at least 4 characters.";
	   form.passwd.focus();
	   return false;
   }
   if (form.passwd.value != form.repasswd.value) {
	   document.getElementById("warning").innerHTML = "Your passwords don't match.";
	   form.repasswd.focus();
	   return false;
   }
   if (form.first_name.value.length < 2) {
	   document.getElementById("warning").innerHTML = "Please type your first name of at least 4 characters.";
	   form.first_name.focus();
	   return false;
   }
   if (form.last_name.value.length < 2) {
	   document.getElementById("warning").innerHTML = "Please type your last name of at least 4 characters.";
	   form.last_name.focus();
	   return false;
   }
  return true;
}