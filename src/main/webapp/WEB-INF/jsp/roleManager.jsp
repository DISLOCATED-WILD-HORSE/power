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
    <link rel="stylesheet" href="<%=basePath%>static/layuiadmin/layui/css/layui.css" media="all">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-inline">
                <input type="tel" name="roleName" lay-verify="required|phone" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">启用状态</label>
            <select class="layui-select" name="isactive">
                <option value="">--请选择--</option>
                <option value="0">未启用</option>
                <option value="1">启用</option>
            </select>
        </div>
        <div class="layui-inline">
            <button id="query" class="layui-btn layui-btn-sm">查询</button>
        </div>
    </div>

</div>
<table id="roleManager" lay-filter="test"></table>

<script src="<%=basePath%>static/layuiadmin/layui/layui.js"></script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="toolbarDemo">
    <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
    <button class="layui-btn layui-btn-sm" lay-event="delete">批量删除</button>
</script>
<script>
    layui.config({
        base: '<%=basePath%>static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'layer', 'table'], function () {

        var $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , admin = layui.admin

        var table = layui.table;
        form.render();

        //
        table.render({
            elem: '#roleManager'
            , height: 600
            , url: '${pageContext.request.contextPath}/role/getRoleList' //数据接口
            , toolbar: '#toolbarDemo'
            , defaultToolbar: ['exports']
            , cols: [[
                  {type:'checkbox'}
                , {field: 'roleId', width: 100, title: '角色编号'}
                , {field: 'roleName', width: 100, title: '角色名'}
                , {field: 'isactive', width: 120, title: '是否激活'}
                , {field: 'createUser', width: 100, title: '创建人'}
                , {
                    field: 'modifyDate',
                    title: '修改时间',
                    minWidth: 150,
                    templet: '<div>{{ layui.laytpl.toDateString(d.modifyDate) }}</div>'
                }
                , {field: 'right', width: 180, align: 'center', toolbar: '#barDemo'}
            ]]
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                //,curr: 1 //设定初始在第 1 页
                , limit:50
                , limits: [50, 100, 200]
                , groups: 2 //只显示 2 个连续页码
                , first: false //不显示首页
                , last: false//不显示尾页

            }
            , request: {
                pageName: 'currentPage' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            , response: {
                statusCode: 1 //重新规定成功的状态码为 1，table 组件默认为 0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code,
                    "message": res.message,
                    "data": res.data,//解析数据列表
                    "count": res.count
                };
            }
        });

        //时间戳的处理
        layui.laytpl.toDateString = function (d, format) {
            var date = new Date(d || new Date())
                , ymd = [
                this.digit(date.getFullYear(), 4)
                , this.digit(date.getMonth() + 1)
                , this.digit(date.getDate())
            ]
                , hms = [
                this.digit(date.getHours())
                , this.digit(date.getMinutes())
                , this.digit(date.getSeconds())
            ];

            format = format || 'yyyy-MM-dd HH:mm:ss';

            return format.replace(/yyyy/g, ymd[0])
                .replace(/MM/g, ymd[1])
                .replace(/dd/g, ymd[2])
                .replace(/HH/g, hms[0])
                .replace(/mm/g, hms[1])
                .replace(/ss/g, hms[2]);
        };

        //数字前置补零
        layui.laytpl.digit = function (num, length, end) {
            var str = '';
            num = String(num);
            length = length || 2;
            for (var i = num.length; i < length; i++) {
                str += '0';
            }
            return num < Math.pow(10, length) ? str + (num | 0) : num;
        };

        //数据表格条件搜索
        $("#query").click(function () {
            var roleName = $("input[name='roleName']").val();
            var isActive = $("select[name='isactive']").val();
            //所获得的 tableIns 即为当前容器的实例
            table.reload('roleManager', {
                url: '${pageContext.request.contextPath}/role/getRoleList',
                where: {roleName: roleName, isActive: isActive},
                done: function (res) {
                    if (res.code != 1) {
                        layer.msg(res.message);
                    }
                }
            });
        });

        //表格工具条监听
        //监听事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    //表单初始赋值
                    form.val('example', {
                        "roleId": "" // "name": "value"
                        ,"roleName": ""
                        ,"isactive": 0
                    })
                    document.all.namedItem("roleId").disabled=false;
                    layer.open({
                        type: 1
                        , title: '新增角色'
                        , area: ['500px', '300px']
                        , offset: '100px'
                        , content: $('#addF')
                        , maxmin: true
                    });
                    break;
                case 'delete':
                    var checkStatus = table.checkStatus('roleManager');
                    var selectDatas = checkStatus.data;
                    if(selectDatas==null||selectDatas.length<=0){
                        layer.msg("至少选中一行数据！");
                        return;
                    }
                    layer.confirm('确定删除所选的行吗？',function (index) {
                        $.ajax({
                           type:'post',
                           url:'${pageContext.request.contextPath}/role/delRole',
                           data:JSON.stringify(selectDatas),
                           contentType:'application/json',
                           dataType:'json',
                           success:function (res) {
                               layer.msg(res.message);
                               if(res.code==1){
                                   table.reload('roleManager',{
                                       url:'${pageContext.request.contextPath}/role/getRoleList'
                                   });
                               }
                           }
                        });
                        layer.close(index);
                    })
            }
        });

        //监听工具条
        table.on('tool(test)', function(obj){
            var data = obj.data;
            var datas=[];
            datas.push(data);
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        type:'post',
                        url:'${pageContext.request.contextPath}/role/delRole',
                        data:JSON.stringify(datas),
                        contentType: "application/json",
                        dataType:'json',
                        success:function (res) {
                            layer.msg(res.message);
                            if(res.code==1){
                                table.reload('roleManager',{
                                    url:'${pageContext.request.contextPath}/role/getRoleList'
                                });
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                //表单初始赋值
                form.val('example', {
                    "roleId": data.roleId // "name": "value"
                    ,"roleName": data.roleName
                    ,"isactive": data.isactive
                })
                $(".isActive").val(data.isactive);
                document.all.namedItem("roleId").disabled=true;
                layer.open({
                    type: 1
                    , title: '编辑角色'
                    , area: ['500px', '300px']
                    , offset: '100px'
                    , content: $('#addF')
                    , maxmin: true
                    , btn: ['保存','关闭']
                    , yes:function (index,layero) {
                        var roleId = $("#ri").val();
                        var roleName = $("#rn").val();
                        var isactive = $(".isActive").val();
                        var role = {};
                        role["roleId"]=roleId;
                        role["roleName"]=roleName;
                        role["isactive"]=isactive;
                        $.ajax({
                            type:'post',
                            url:'${pageContext.request.contextPath}/role/updateRole',
                            data:JSON.stringify(role),
                            contentType:'application/json',
                            dataType:'json',
                            success:function (res) {
                                layer.msg(res.message);
                                if(res.code==1){
                                    table.reload('roleManager',{
                                        url:'${pageContext.request.contextPath}/role/getRoleList'
                                    });
                                    layer.close(index);
                                }
                            }
                        });
                    }
                });
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
            }
        });

        form.on('submit(formDemo)',function (obj) {
            if(obj.field.roleId==null||obj.field.roleName==null||obj.field.isactive==null){layer.msg("表单信息不完整，请检查！");
                layer.msg('表单信息不完整，请检查！', {
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                return;
            }
            admin.req({
                url:'${pageContext.request.contextPath}/role/addRole',
                type:'post',
                data:obj.field,
                done: function(res){
                    if(res===1){
                        layer.msg(res.message);
                    }
                }
            });
        });

        /**
         * 开关值处理
         */
        form.on('switch(filter)', function(data){
            if(data.elem.checked){
                $(".isActive").val("1");
            }else{
                $(".isActive").val("0");
            }
        });
    })




</script>
<form id="addF" class="layui-form" lay-filter="example" hidden>
    <div class="layui-form-item">
        <label class="layui-form-label">角色编号</label>
        <div class="layui-input-inline">
            <input type="tel" id="ri" name="roleId" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-inline">
            <input type="tel" id="rn" name="roleName" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">激活</label>
        <div class="layui-input-block">
            <input class="isActive" type="checkbox" name="isactive" lay-skin="switch" lay-filter="filter" value="1" lay-text="开启|关闭">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</body>
</html>