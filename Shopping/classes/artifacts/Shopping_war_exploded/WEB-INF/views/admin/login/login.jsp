<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>商品后台管理</title>
    <style>
        body {
            background-image: url("https://files.catbox.moe/y5d1o6.jpg");
        }
        .button {
            background-color: #4CAF50; /* Green */
            border: 1px solid green;
            border-radius: 10px;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            float: left;
        }

        .button:hover {
            background-color: #3e8e41;
        }
    </style>
    <jsp:include page="../commonCss.jsp"/>
</head>
<body class="blank" onload='document.loginForm.username.focus();'>
<div class="color-line"></div>
<div class="login-container">
    <div class="row">
        <div class="col-md-12">
            <div class="text-center m-b-md">
                <h3 style="color: #FFFFFF"><b>后台管理系统DEMO</b></h3>
                <small></small>
            </div>
            <c:if test="${not empty error}">
                <div class="error" style='color:red'>${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg" style='color:red'>${msg}</div>
            </c:if>
            <div class="hpanel">
                <div class="panel-body">
                    <form class="form-signin" name='loginForm'
                          action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method='POST'>
                        <div class="form-group">
                            <label class="control-label" for="username">用户名</label>
                            <input type="text" placeholder="admin" title="请输入用户名" required="" value="" name="username"
                                   id="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="password">密码</label>
                            <input type="password" title="请输入用户名" placeholder="123123" required="" value=""
                                   name="password" id="password" class="form-control">
                        </div>
                        <button class="btn btn-success btn-block" type="submit">登录</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="tips" style="color: #FFFFFF;text-align: center">
                    <h4><b>账号：admin</b></h4>
                    <h4><b>密码：123123</b></h4>
                </div>
                <div class="link">
                    <h3 style="color: #FFFFFF">获取源码</h3>
                    <button class="button">
                        <a href="https://github.com/Jaolvv/SSM_Commodity-management-system.git"
                           target="_blank">Github</a>
                    </button>

                    <button class="button">
                        <a href="https://gitee.com/jaolvv/ssm_shopping.git" target="_blank">码云Gitee</a>
                    </button>
                    <br><br><br>
                    <h3 style="color: #FFFFFF">
                        博客：
                        <a href="https://www.cnblogs.com/jaolvv" target="_blank" style="color: blue">https://www.cnblogs.com/jaolvv</a>
                    </h3>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../commonJs.jsp"/>
</body>
</html>