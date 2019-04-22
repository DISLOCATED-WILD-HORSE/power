<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/3/21
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>左侧导航</title>
</head>
<body>
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="demo">
            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">我的工作平台</a>
                <dl class="layui-nav-child">
                    <dd><a href="${pageContext.request.contextPath}/user/toPersonal">个人资料</a></dd>
                    <dd><a href="javascript:;">待办列表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">系统管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">角色管理</a></dd>
                    <dd><a href="javascript:;">用户管理</a></dd>
                    <dd><a href="">角色权限配置</a></dd>
                    <dd><a href="">系统配置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">杆塔管理</a></li>
            <li class="layui-nav-item"><a href="">线路管理</a></li>
            <li class="layui-nav-item"><a href="">缺陷管理</a></li>
            <li class="layui-nav-item"><a href="">巡检任务管理</a></li>
            <li class="layui-nav-item"><a href="">消缺任务管理</a></li>
            <li class="layui-nav-item"><a href="">信息统计</a></li>
        </ul>
    </div>
</div>
</body>
</html>
