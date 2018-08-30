<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>file uploder</title>
</head>
<body>
<h1>${message}</h1>
<br/>
<form action=<spring:url value="/"/> method="POST"  enctype="multipart/form-data">
    File to upload: <input type="file" name="file" ><br/>
    <input type="submit" value="Upload">
</form>
<br/>
<c:forEach var="fileName" items="${fileNames}">
    <a href="<spring:url value="/show/${fileName}"/>">show "${fileName}"</a> <br/>
</c:forEach>
<br/>
<c:forEach var="file" items="${files}">
    <a href="<spring:url value="/download/${file}"/>">download "${file}"</a> <br/>
</c:forEach>
</body>
</html>