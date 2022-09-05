<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
   <h1>Error Page</h1>

   <p><%= request.getAttribute("message") %></p>
   <form action="UserLogin" method="post">
       <input type="hidden" id="endSession" name="endSession" value="true">
       <input type="submit" value="endSession">
   </form>
   <a href="index.jsp">Home Page</a>
</body>
</html>
