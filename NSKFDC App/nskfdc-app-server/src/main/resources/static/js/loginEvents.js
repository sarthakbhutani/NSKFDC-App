

function showPassword() {
    var x = document.getElementById("userpassword");
    if (x.type === "password") {
    	iconFunction(0);
        x.type = "text";
    } else {
        x.type = "password";
        iconFunction(1);
    }
}

function iconFunction(y){
	var status= document.getElementById("eyeToggle");
	if(y==0){
		status.className="glyphicon glyphicon-eye-close";
	}
	else{
		status.className="glyphicon glyphicon-eye-open";
	}
}
