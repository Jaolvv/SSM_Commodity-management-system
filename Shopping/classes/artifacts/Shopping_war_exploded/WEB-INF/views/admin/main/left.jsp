<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Navigation -->
<aside id="menu">
    <div id="navigation">
        <div class="profile-picture">
            <a href="#">
                <img style="width: 150px" src="${pageContext.request.contextPath}/resources/admin/images/logo.jpg" class="img-circle m-b" alt="logo">
            </a>
        </div>
        <ul class="nav" id="side-menu">
            <li id="leftMenuProduct">
                <a href="#"><span class="nav-label">商品管理</span><span class="fa arrow"></span> </a>
                <ul class="nav nav-second-level">
                    <li><a href="${pageContext.request.contextPath}/admin/productCategoryList">商品分类管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/productList">商品管理</a></li>
                </ul>
            </li>
            <li id="leftMenuOrder">
                <a href="#"><span class="nav-label">订单管理</span><span class="fa arrow"></span> </a>
                <ul class="nav nav-second-level">
                    <li><a href="${pageContext.request.contextPath}/order/select">查看订单</a></li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/user"><span class="nav-label">用户管理</span></a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/comments"><span class="nav-label">评论管理</span></a>
            </li>
            <li id="leftMenuArticle">
                <a href="#"><span class="nav-label">文章管理</span><span class="fa arrow"></span> </a>
                <ul class="nav nav-second-level">
                    <li><a href="${pageContext.request.contextPath}/admin/articlecategory">分类管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/article">文章管理</a></li>
                </ul>
            </li>
        </ul>
    </div>
</aside>