<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <script>
        function CreateValidation() {
            var email=document.CreateAccount.email.value;
            if (email===""){
                alert("Please enter an email");
                document.CreateAccount.email.focus();
                return false;
            }
            if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email)){
                document.CreateAccount.email.focus();
            }
            else{
                alert("Please enter a valid email address")
                document.CreateAccount.email.focus();
                return false
            }
            var phoneNum = document.CreateAccount.phone.value;

            if (phoneNum ===""){
                alert("Please enter a phone number")
                document.CreateAccount.phone.focus();
                return false;
            }
            if (/(\d{2}[-]\d{4}[-]\d{6,7}\b)/g.test(phoneNum)){
                document.CreateAccount.phone.focus();
            }
            else{
                alert("Please enter a valid phone number")
                document.CreateAccount.phone.focus();
                return false;
            }
            var password = document.CreateAccount.password.value;

            if (password ===""){
                alert("Please enter a password")
                document.CreateAccount.password.focus();
                return false;
            }
            if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,15}$/g.test(password)){
                document.CreateAccount.focus();
            }
            else{
                alert("Please enter a valid password")
                document.CreateAccount.password.focus();
                return false;
            }

            var isAdmin = document.CreateAccount.isAdmin.value;

            if (isAdmin==null){
                alert("Please select if the user is an admin or not")
                document.CreateAccount.isAdmin.focus();
                return false;
            }

        }

        function LogInValidation(){
            var password = document.UserLogin.password.value;

            if (password ===""){
                alert("Please enter a password")
                document.UserLogin.password.focus();
                return false;
            }
            if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,15}$/g.test(password)){
                document.UserLogin.focus();
            }
            else{
                alert("Please enter a valid password")
                document.UserLogin.password.focus();
                return false;
            }
        }

    </script>
</head>
<body>

<h1>Home Page</h1>
<h2>Log in:</h2>

<form name="UserLogin" action="UserLogin" method="post">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>

    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password"><br><br>

    <input type="submit" onclick = "return LogInValidation();" value="Submit">
</form>

<h2>Create Account:</h2>

<form name="CreateAccount" action="CreateAccount" method="post">
    <label for="firstname">First name:</label><br>
    <input type="text" id="firstname" name="firstname"><br>

    <label for="lastname">Last name:</label><br>
    <input type="text" id="lastname" name="lastname"><br>

    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>

    <label for="phone">Phone number:</label><br>
    <input type="text" id="phone" name="phone"><br>

    <label for="email">Email address:</label><br>
    <input type="text" id="email" name="email"><br>

    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password"><br>

    <label for="isAdmin">Admin:</label><br>
    <select name="isAdmin" id="isAdmin">
        <option>Yes</option>
        <option>No</option>
    </select>
    <br><br>

    <input type="submit" onclick="return CreateValidation();" value="Submit">
</form>


</body>
</html>
