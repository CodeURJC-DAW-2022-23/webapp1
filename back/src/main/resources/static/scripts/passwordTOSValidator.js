function validatePasswordTOS() {
    if ((document.getElementById("password").value ==
            document.getElementById("confirmPassword").value) &&
            document.getElementById("checkBox").checked) {
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("submit").disabled = true;
    }
}