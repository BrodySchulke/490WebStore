window.onload = function () {  		

		var elMsg = document.getElementById('feedback1'); 
   		var elMsg1 = document.getElementById('feedback2');
		
   		function checkUsername() {                            // Declare function
		  if (this.value.length < 5) {                        // If username too short
		    elMsg.textContent = 'Username must be 5 characters or more';  // Set msg
		  } else {                                            // Otherwise
			elMsg.textContent = '';                           // Clear message
		  }
		}
		 
		function loginCheck(){
	        if(document.getElementById('username').value==""){
	                elMsg.textContent= "Pleaes enter your LOGIN ID";
	                document.getElementById('username').focus();
	                return false;
	        }
	        if(document.getElementById('password').value==""){
	                elMsg1.textContent = "Please enter your PASSWORD";
	                document.getElementById('password').focus();
	                return false;
	        }
		}

		var elUsername = document.getElementById('username'); // Get username input
		elUsername.onblur = checkUsername;  // When it loses focus call checkuserName()
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick =loginCheck;
}