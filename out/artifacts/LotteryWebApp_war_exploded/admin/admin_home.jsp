<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Home</title>
</head>
<body>
<h1>Admin Home</h1>

<div>
    <%=request.getAttribute("data")%>
</div>

<div>
<a href="../index.jsp">Home Page</a>
</div>

</body>
</html>
