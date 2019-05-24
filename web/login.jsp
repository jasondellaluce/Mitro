<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div class="split left">
		<div class="centered">
			<p id="welcome">Bentornato nel Registro Elettronico Mitro</p></br></br></br>
			<form action="/Mitro/login" method="post">
				<label for="uname">Username</label>
				<input type="text" id="uname" name="username" placeholder="Your username..">
                
				<label for="pword">Password</label>
				<input type="password" id="pword" name="password">
                
                <input type="submit" value="Login">
            </form>
        </div>
        <%
        	if(request.getAttribute("error") != null) {
        		String error = (String) request.getAttribute("error");
        %>
        <p> ERRORE: <%= error %></p>
        <%
        	}
        %>
    </div>
            
    <div class="split right">
    	<div class="centered">
                
        </div>
    </div>
</body>
</html>