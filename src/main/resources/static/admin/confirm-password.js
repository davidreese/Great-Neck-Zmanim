var password = document.getElementById("input-password"), confirmPassword = document.getElementById("input-cpassword");

function validatePassword(){
  if (password.value != confirm_password.value) {
    confirmPassword.setCustomValidity("Passwords Don't Match");
  } else {
    confirmPassword.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confirmPassword.onkeyup = validatePassword;