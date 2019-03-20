<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/3/20
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="https://cdn.bootcss.com/toastr.js/latest/toastr.css" rel="stylesheet">
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">



    <title>注册界面</title>
</head>
<body>
    <div style="display: -webkit-flex;-webkit-justify-content: center;-webkit-align-items: center;">

            <div class="form-group">
                <label for="userid">账号:</label>
                <input type="text" class="form-control" id="userid" name="userid" style="display:inline;width:200px;"autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="username">真实姓名:</label>
                <input type="text" class="form-control" id="username" name="username" style="display:inline;width:200px;"autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="password">密码:</label>
                <input type="password" class="form-control" id="password" name="password" style="display:inline;width:200px;"autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="remark">备注:</label>
                <input type="text" class="form-control" id="remark" name="remark" style="display:inline;width:200px;"autocomplete="off" />
            </div>
            <button type="button" id="confirm" class="btn btn-primary">确认注册</button>

    </div>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/toastr.js/latest/js/toastr.min.js"></script>
<%--<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>--%>
<script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
<script type="text/javascript">
    $('#confirm').click(function () {

        var user=[];
        user["userid"]=$('input[name=userid]').val();
        user["username"]=$('input[name=username]').val();
        user["password"]=$('input[name=password]').val();
        user["remark"]=$('input[name=remark]').val();
        $.ajax({
           type:'POST',
           url:'${pageContext.request.contextPath}/regist/getForm',
           data: {
               'userid':user.userid,
               'username':user.username,
               'password':user.password,
               'remark':user.remark
            },
           dataType:'json',
           success:function (result) {
               toastr.success(result.message);
               window.open("login.jsp","_self");
           },
           error:function (rv) {
               toastr.success(rv.message);
           }
        });
    });
</script>
</html>
