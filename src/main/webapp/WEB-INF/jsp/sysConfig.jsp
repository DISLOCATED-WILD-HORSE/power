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
    <title>layuiAdmin 系统配置 iframe 框</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
    <table id="demoTable" lay-filter="test"></table>


    <table id="childrenTable" lay-filter="childrenTest" hidden="hidden" style="display:none;"></table>


<<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="save">保存</button>
        <button class="layui-btn layui-btn-sm" lay-event="cansle">取消</button>
    </div>
</script>

<script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '<%=basePath%>layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'layer','table'], function () {
        var $ = layui.$
            , form = layui.form
            ,layer = layui.layer

        var table = layui.table;

        //系统配置类型数据
        table.render({
            elem: '#demoTable'
            ,height: 312
            ,url: '${pageContext.request.contextPath}/systemConfig/getConfigTypeInfo' //数据接口
            ,toolbar: '#toolbarDemo'
            ,page: false //开启分页
            ,cols: [[ //表头
                {type:'radio'}
                ,{field:'id',title:'ID',hide:true}
                ,{field: 'typeId', title: '配置类型编码', width:160,edit:'text'}
                ,{field: 'name', title: '配置类型名称', width:160,edit:'text'}
                ,{field: 'describe', title: '描述', width:200,edit:'text'}
                ,{field: 'isEnable', title: '是否启用', width: 120,edit:'text'}
            ]]
            ,response: {
                statusCode: 1 //重新规定成功的状态码为 1，table 组件默认为 0
            }
            ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code,
                    "data": res.data //解析数据列表
                };
            }
        });
        //类型表工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    addTr("demoTable");
                    break;
                case 'delete':
                    deleteTr("demoTable");
                    break;
                case 'save':
                    saveTr("demoTable");
                    break;
                case 'cansle':
                    cansel("demoTable");
                    break;
            };
        });

        //类型表工具栏事件
        table.on('toolbar(childrenTest)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    addTr("childrenTable");
                    break;
                case 'delete':
                    deleteTr("childrenTable");
                    break;
                case 'save':
                    saveTr("childrenTable");
                    break;
                case 'cansle':
                    cansel("childrenTable");
                    break;
            };
        });

        //点击添加按钮新增一空行
        function addTr(tableId){
            var datas=[];
            if(tableId==='demoTable'){
                var tableBak = table.cache.demoTable;
                for (var i = 0; i < tableBak.length; i++) {
                    datas.push(tableBak[i]);      //将之前的数组备份
                }

                datas.push({
                    "describe": ""
                    ,"typeId": ""
                    ,"isEnable": 0
                    ,"name": ""
                });
                table.reload('demoTable',{
                    url:"",
                    data:datas,   // 将新数据重新载入表格
                });
            }else{
                var tableBak = table.cache.childrenTable;
                for (var i = 0; i < tableBak.length; i++) {
                    datas.push(tableBak[i]);      //将之前的数组备份
                }

                datas.push({
                    "id": ""
                    ,"name": ""
                });
                table.reload('childrenTable',{
                    url:"",
                    data:datas,   // 将新数据重新载入表格
                });
            }
        }
        //添加完成保存数据
        function saveTr(tableId){
            if(tableId==="demoTable"){
                //获取表格全部数据
                var tableBak = table.cache.demoTable;
                var typeidArray = [];
                for (var i=0;i<tableBak.length;i++){
                    typeidArray.push(tableBak[i].typeId);
                }
                var typeidArrays = typeidArray.sort();
                for(var i=0;i<typeidArray.length;i++) {

                    if (typeidArrays[i] == typeidArrays[i + 1]) {
                        layer.msg("配置类型编码重复，请检查！");
                        return;
                    }
                }
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/systemConfig/saveConfig',
                    data:JSON.stringify(tableBak),
                    contentType: "application/json",
                    dataType:'json',
                    success:function (res) {
                        if(res.code===1){
                            table.reload('demoTable',{
                                url:'${pageContext.request.contextPath}/systemConfig/getConfigTypeInfo'
                            });
                            layer.msg(res.message);
                        }
                    },
                    error:function (res) {
                        layer.msg(res.message);
                    }
                });
            }else{
                //获取主表选中行数据
                var checkStatus = table.checkStatus('demoTable');
                var selectData = checkStatus.data;
                //获取表格全部数据
                var tableBak = table.cache.childrenTable;
                var id = [];
                for (var i=0;i<tableBak.length;i++){
                    id.push(tableBak[i].id);
                }
                var idArray = id.sort();
                for(var i=0;i<id.length;i++) {

                    if (idArray[i] == idArray[i + 1]) {
                        layer.msg("配置参数编码重复，请检查！");
                        return;
                    }
                }
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/systemConfig/saveConfigData?typeId='+selectData[0].typeId,
                    data:JSON.stringify(tableBak),
                    contentType: "application/json",
                    dataType:'json',
                    success:function (res) {
                        if(res.code===1){

                            layer.msg(res.message);
                        }
                    },
                    error:function (res) {
                        layer.msg(res.message);
                    }
                });
            }
        }

        //删除数据
        function deleteTr(tableId) {
            var checkStatus = table.checkStatus(tableId);
            var selectData = checkStatus.data;
            if(selectData.length<=0){
                layer.msg("请选中想要删除的一行数据！");
                return;
            }
            layer.open({
                type:1,
                title:false,
                closeBtn:false,
                area:'300px;',
                shade: [0.3, 'gray'],
                id: 'delConfig', //设定一个id，防止重复弹出
                btn: ['确定', '取消'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">是否确定删除</div>',
                success:function (layero) {
                    var btn = layero.find('.layui-layer-btn');
                    btn.on('click', function (e){
                        if (e.target.innerText === "确定") {
                            if(tableId==="demoTable"){
                                $.ajax({
                                    type:'post',
                                    url:'${pageContext.request.contextPath}/systemConfig/deleteConfig',
                                    data:JSON.stringify(selectData),
                                    contentType: "application/json",
                                    dataType:'json',
                                    success:function (res) {
                                        if(res.code===1){
                                            table.reload('demoTable',{
                                                url:'${pageContext.request.contextPath}/systemConfig/getConfigTypeInfo'
                                            });
                                            table.reload('childrenTable',{
                                                url:'${pageContext.request.contextPath}/systemConfig/getConfigDataInfo?id='+selectData[0].typeId
                                                // ,done:function (res) {
                                                //     if(res.data.length<=0){
                                                //         table.hidden;
                                                //     }
                                                // }
                                            });
                                            layer.msg(res.message);
                                        }else{
                                            layer.msg(res.message);
                                        }
                                    },
                                    error:function (res) {
                                        layer.msg(res.message);
                                    }
                                });
                            }else{
                                $.ajax({
                                    type:'post',
                                    url:'${pageContext.request.contextPath}/systemConfig/deleteConfigData',
                                    data:JSON.stringify(selectData),
                                    contentType: "application/json",
                                    dataType:'json',
                                    success:function (res) {
                                        if(res.code===1){
                                            console.log(selectData)
                                            table.reload('childrenTable',{
                                                url:'${pageContext.request.contextPath}/systemConfig/getConfigDataInfo?id='+selectData[0].typeId
                                                // ,done:function (res) {
                                                //     if(res.data.length<=0){
                                                //         table.hidden;
                                                //     }
                                                // }
                                            });
                                            layer.msg(res.message);
                                        }else{
                                            layer.msg(res.message);
                                        }
                                    },
                                    error:function (res) {
                                        layer.msg(res.message);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        //取消操作
        function cansel(tableId){
            var checkStatus = table.checkStatus("demoTable");
            var selectData = checkStatus.data;
            if(tableId==="demoTable"){
                table.reload(tableId,{
                    url: '${pageContext.request.contextPath}/systemConfig/getConfigTypeInfo'
                })
            }else{
                table.reload(tableId,{
                    url: '${pageContext.request.contextPath}/systemConfig/getConfigDataInfo?typeId='+selectData[0].typeId
                })
            }
        }


        //监听单选按钮点击事件
        table.on('radio(test)', function(obj){
            table.render({
                elem: '#childrenTable'
                ,height: 312
                ,url: '${pageContext.request.contextPath}/systemConfig/getConfigDataInfo?typeId='+obj.data.typeId //数据接口
                ,toolbar: '#toolbarDemo'
                ,page: false //开启分页
                ,cols: [[ //表头
                    {type:'radio'}
                    ,{field: 'id', title: '配置参数编码', width:160,edit:'text'}
                    ,{field: 'name', title: '配置参数名称', width:160,edit:'text'}
                    ,{field: 'typeId', title: 'typeId',hide:true}
                ]]
                ,response: {
                    statusCode: 1 //重新规定成功的状态码为 1，table 组件默认为 0
                }
                ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
                    return {
                        "code": res.code,
                        "data": res.data //解析数据列表
                    };
                }
            });
        });
    })

</script>
</body>
</html>