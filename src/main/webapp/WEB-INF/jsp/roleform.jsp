<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layuiAdmin 角色权限 iframe 框</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="<%=basePath%>static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>static/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath%>static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/zTree_v3/js/jquery.ztree.excheck.js"></script>
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block">
            <select id="isCheck" name="rolename">
                <c:forEach items="${roleList}" var="rl">
                    <option value="${rl.roleId}">${rl.roleName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限范围</label>
        <div class="layui-input-block" id="zNode">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
        <input id="select_all" value="no" type="hidden">
         <a id="checkAllNodes" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">全选</a>

    </div>
    <div class="layui-form-item">
        <button class="layui-btn" lay-submit lay-filter="LAY-user-role-submit" id="LAY-user-role-submit">保存</button>
    </div>
</div>

<script src="<%=basePath%>static/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '<%=basePath%>static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'layer'], function () {
        var $ = layui.$
            , form = layui.form
            ,layer = layui.layer


        form.on('select',function(data){

            ajaxData();
        });

        form.on('submit(LAY-user-role-submit)',function (obj) {
            var options=$("#isCheck option:selected");  //获取选中的项
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getCheckedNodes(true);
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/menu/maimTain?roleId="+options.val(),
                data:JSON.stringify(nodes),
                contentType: "application/json",
                dataType:"json",
                success:function (res) {
                    layer.msg(res.message);
                },
                error:function (res) {
                    layer.msg(res.message);
                }

            })
        })
    })


    //zTree树形
    var setting = {
        async:{
            enable: true,
            type:"GET",
            dataType:"json",
            url: "${pageContext.request.contextPath}/menu/getMenu",
            autoParam: ['id'],
            dataFilter:ajaxDataFilter
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "ps" } //勾选checkbox对于父子节点的关联关系
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        callback:{
            onClick:onClick
        }
    };

    var zNodes =[];
    function onClick(event, treeId, treeNode, clickFlag){

    }

    //当每次加载成功后，执行该函数
    function ajaxDataFilter(treeId, parentNode, responseData) {
        //当每次加载成功后且点击全选按钮时，将新加载后的全部节点选中，responseData表示每次新加载的所有节点
        if (responseData&&$('#select_all').val()=='yes') {
            for(var i =0; i < responseData.length; i++) {
                responseData[i].checked = true;
            }
        }
        return responseData;
    };

    function checkAllNodes() {
        $('#select_all').val('yes');
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //将现有节点全部选中（不包括还没有展开的节点）
        treeObj.checkAllNodes(true);
    };

    function ajaxData(){
        var options=$("#isCheck option:selected");  //获取选中的项
        $.ajax({
            type:'GET',
            url:'${pageContext.request.contextPath}/menu/getMenu',
            data:{roleId:options.val()},
            dataType:'json',
            success:function (res) {
                zNodes=res.data;
                //初始化ztree
                $.fn.zTree.init($("#treeDemo"), setting,zNodes);
                $("#checkAllNodes").click(checkAllNodes);
                //展开全部节点
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.expandAll(true);
                for (var i = 0; i < res.data.length; i++) {
                    var node = treeObj.getNodeByParam("id", res.data[i].id );
                    if (res.data[i].isChecked==true)
                    {
                        zTree.checkNode(node, true);
                        for (var j = 0; j < res.data[i].children.length; j++) {
                            var node1 = zTree.getNodeByParam("id", res.data[i].children[j].id );
                            if (res.data[i].children[j].isChecked == true) {
                                zTree.checkNode(node1,true)
                            }

                        }
                    }
                }
            }
        })
    }


    $(document).ready(function(){

        ajaxData();
    });



</script>
</body>
</html>