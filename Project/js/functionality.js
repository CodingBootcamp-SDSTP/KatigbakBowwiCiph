function validate() {
	var user = document.getElementById('name');
	var pass = document.getElementById('password');
	if(user.value != '' && pass.value != '') {
		if(user.value != pass.value) {
			alert("Hello, '"+ user.value + "'\nYour password is '" + pass.value + "'");
			var i = confirm("Are you a programmer?")
			if(i == true) {
				var x = prompt("What kind/job of a programmer type are you?");
				alert("Name : " + user.value + "\nJob : " + x);
				alert("Great Job! " + user.value);
			}
			if(i == false) {
				var y = prompt('What are you?');
				alert('Name : ' + user.value + '\nJob : '+ y);
				alert('Great Job, '+user.value);
			}
		}
		return(true);
	}
	else {
		alert('Input should be filled');
		return(false);
	}
}
