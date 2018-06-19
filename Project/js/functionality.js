function validate() {
		var user = document.getElementById('name');
		var pass = document.getElementById('password');
		if(user.value != '' && pass.value != '') {
			if(user.value != pass.value) {
				alert("Hello, '"+ user.value + "'\nYour password is '" + pass.value + "'");
				var i = confirm("Are you a programmer?")
				if(i == true) {
					var x = prompt("Are you interested in Ciphers?");
					alert("Welcome to Ciph! " + user.value);
				}
				else {
					var x = prompt("Are you interested in Ciphers?");
					alert("Welcome to Ciph! " + user.value);
				}
		}
		return(true);
	}
	else {
		alert('Input should be filled');
		return(false);
	}
}
