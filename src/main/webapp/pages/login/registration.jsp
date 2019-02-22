<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<head>
</head>
<body>
<div align="center">
    <H2 align="center">Registration</H2>
    <div style="padding-bottom: 8pt ">
        <label for="login">
            <span>
                Username
            </span>
        </label>
        <input type="text" id="login" autocomplete="off">
    </div>
    <div style="padding-bottom: 8pt">
        <label for="password">
            <span>
                Password
            </span>
        </label>
        <input type="password" id="password" autocomplete="off">
    </div>
    <div style="padding-top: 4pt;">
        <button>
            << Back
        </button>
        <button>
            Sign up
        </button>
    </div>
</div>
</body>
</html>