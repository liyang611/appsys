<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>APP信息管理系统</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath }/statics/css/nprogress.css" rel="stylesheet">
    <%--Animate.css--%>
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet"/>
    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath }/statics/css/custom.min.css" rel="stylesheet">
<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <h1>APP信息管理平台</h1>
        <div>
            <a class="btn btn-link" href="backend/login">后台管理系统入口</a>
        </div>
        <div>
            <a class="btn btn-link" href="dev/login">开发者平台入口</a>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
</body>
</html>
