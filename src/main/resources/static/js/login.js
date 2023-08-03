// login.js

// Function to perform form validation
function validateForm() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    if (username === '' || password === '') {
        alert('Please enter both username and password.');
        return false;
    }

    return true;
}

// Attach form validation to the login form
var loginForm = document.getElementById('login-form');
loginForm.onsubmit = function() {
    return validateForm();
};
