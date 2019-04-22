<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/3/25
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>个人资料</title>
    <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css">
</head>
<body>
<jsp:include page="../common/head.jsp" flush="true"></jsp:include>
<jsp:include page="../common/left.jsp" flush="true"></jsp:include>
    <table id="personal_data" lay-filter="personal_data"></table>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="<%=basePath%>layui/layui.js"></script>
    <script>
        layui.use('table', function(){
            var table = layui.table;

            //第一个实例
            table.render({
                elem: '#personal_data'
                ,height: 312
                ,url: '${pageContext.request.contextPath}/user/userList' //数据接口
                ,page: {
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    ,curr: 1 //设定初始在第 1 页
                    ,groups: 2 //只显示 2 个连续页码
                    ,first: false //不显示首页
                    ,last: false //不显示尾页
                } //开启分页
                ,cols: [[ //表头
                    {field: 'userid', title: '用户编号', width:120, sort: true, fixed: 'left'}
                    ,{field: 'username', title: '用户名', width:120}
                    ,{field: 'remark', title: '备注', width:120}
                    ,{field: 'isdisable', title: '是否激活', width:120}
                ]]
                ,response:{
                    statusCode: 1
                }
                ,parseData:function (res) {
                    console.log(res)
                    return {
                        "code": res.code, //解析接口状态
                        "msg": res.message,
                        "data": res.data //解析数据列表
                    };
                }
            });

        });
    </script>
</body>
</html>
