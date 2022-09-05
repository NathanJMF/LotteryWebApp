<%@ page import="java.lang.reflect.Array" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <script>
        function NumberValidation(){
            var number1 = document.AddUserNumbers.number1.value;
            var number2 = document.AddUserNumbers.number2.value;
            var number3 = document.AddUserNumbers.number3.value;
            var number4 = document.AddUserNumbers.number4.value;
            var number5 = document.AddUserNumbers.number5.value;
            var number6 = document.AddUserNumbers.number6.value;
            if (number1<0 || number1>60 || number1===""){
                alert("Number 1 is out of range!")
                document.AddUserNumbers.number1.focus();
                return false;
            }
            if (number2<0 || number2>60 || number2===""){
                alert("Number 2 is out of range!")
                document.AddUserNumbers.number2.focus();
                return false;
            }
            if (number3<0 || number3>60 || number3===""){
                alert("Number 3 is out of range!")
                document.AddUserNumbers.number3.focus();
                return false;
            }
            if (number4<0 || number4>60 || number4===""){
                alert("Number 4 is out of range!")
                document.AddUserNumbers.number4.focus();
                return false;
            }
            if (number5<0 || number5>60 || number5===""){
                alert("Number 5 is out of range!")
                document.AddUserNumbers.number5.focus();
                return false;
            }
            if (number6<0 || number6>60 || number6===""){
                alert("Number 6 is out of range!")
                document.AddUserNumbers.number6.focus();
                return false;
            }

        }
        function GenerateNumbers() {
            document.getElementById("number1").value=Math.floor(Math.random() * (60+1));
            document.getElementById("number2").value=Math.floor(Math.random() * (60+1));
            document.getElementById("number3").value=Math.floor(Math.random() * (60+1));
            document.getElementById("number4").value=Math.floor(Math.random() * (60+1));
            document.getElementById("number5").value=Math.floor(Math.random() * (60+1));
            document.getElementById("number6").value=Math.floor(Math.random() * (60+1));
            return false;

        }

        function checkNumExist() {
            if (!(<%=session.getAttribute("userNumExist")%>)){
                alert("Please submit your 6 numbers")
                return false;
            }
        }

        function checkGetNumExist() {
            if (!(<%=session.getAttribute("userGetNumExist")%>)){
                alert("Please get your 6 numbers")
                return false;
            }
        }

        





    </script>
</head>
<body>
<h1>User Account</h1>

<p><%= request.getAttribute("message") %></p>
<p><%= session.getAttribute("firstname") %>
    <%= session.getAttribute("lastname") %><br>
    <%= session.getAttribute("username")%><br>
    <%= session.getAttribute("email")%></p>


<p>
    <%
        String[] userNumbers = (String[]) request.getAttribute("draws");
        if(userNumbers==null){userNumbers = new String[]{};};
        String convertedOutput = "";
        for (int x = 0; x< userNumbers.length; x++){
            if (x<userNumbers.length-1){
                convertedOutput = convertedOutput+userNumbers[x]+",";
            }
            else{
                convertedOutput = convertedOutput+userNumbers[x];
            }
    }
    %>
    <%=convertedOutput%>
</p>

<form name="AddUserNumbers" action="AddUserNumbers" method="post">
    <label for="number1">Number 1:</label><br>
    <input type="text" id="number1" name="number1"><br>
    <label for="number2">Number 2:</label><br>
    <input type="text" id="number2" name="number2"><br>
    <label for="number3">Number 3:</label><br>
    <input type="text" id="number3" name="number3"><br>
    <label for="number4">Number 4:</label><br>
    <input type="text" id="number4" name="number4"><br>
    <label for="number5">Number 5:</label><br>
    <input type="text" id="number5" name="number5"><br>
    <label for="number6">Number 6:</label><br>
    <input type="text" id="number6" name="number6"><br><br>
    <input type="submit" onclick = "return NumberValidation();" value="Submit">
    <button onclick="return GenerateNumbers()">Random numbers</button>
</form>
<form name="GetUserNumbers" action="GetUserNumbers" method="post">
    <input type="submit" onclick = "return checkNumExist();" name="Get Draws" value="Get user numbers">
</form>

<form name = "WinChecker" action="WinChecker" method="post">
    <input type="submit" onclick = "return checkGetNumExist();" name="Check Draws" value="Win check">
</form>


<a href="index.jsp">Home Page</a>

</body>
</html>
