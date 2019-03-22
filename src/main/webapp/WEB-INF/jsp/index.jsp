<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/3/18
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css">
    <link href="https://cdn.bootcss.com/toastr.js/latest/toastr.css" rel="stylesheet">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="common/head.jsp" flush="true"></jsp:include>
    <jsp:include page="common/left.jsp" flush="true"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">内容主体区域</div>
    </div>
    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/toastr.js/latest/js/toastr.min.js"></script>
<script src="<%=basePath%>layui/layui.js"></script>
<script>
    layui.use(['element', 'layer'], function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function (elem) {
            //console.log(elem)
            layer.msg(elem.text());
        });
        window.updatePwd = function () {
            $.post('${pageContext.request.contextPath}/user/updatePwd', {}, function(str){
                layer.open({
                    type: 1,
                    title:'修改密码',
                    area: ['500px', '300px'],
                    content: str,
                    btn: ['保存','取消'],
                    yes: function(index, layero){
                        //按钮【下一步】的回调
                        var oldPwd = $("input[name=oldPassword]").val();
                        var newPwd = $("input[name=newPassword]").val();
                        var comfirmPwd = $("input[name=confirmPassword]").val();
                        $.ajax({
                            type:'POST',
                            url:'${pageContext.request.contextPath}/user/oldPwd',
                            data: {
                                'userid':${sessionScope.user.userid},
                                'oldPassword':oldPwd,
                                'newPassword':newPwd
                            },
                            dataType:'json',
                            success:function (result) {
                                layer.close(layer.index);
                                toastr.success(result.message);
                            },
                            error:function (rv) {
                                toastr.success(rv.message);
                            }
                        });
                    },
                    closeBtn: 1,
                    anim: 5,
                    move: false
                });
            });
        }

    });
</script>
</body>
</html>
