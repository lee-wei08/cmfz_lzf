<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="assets/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="assets/js/jquery.validate.min.js"></script>
    <script>

        $(function () {
            $("#captchaImage").click(function () {
                $("#captchaImage").prop("src", "${pageContext.request.contextPath}/code/getCode?time=" + new Date().getTime());
            });
        });

        $(function () {
            $("#loginButtonId").click(function () {
                var username = $("#form-username").val();
                var password = $("#form-password").val();
                var inputCode = $("#form-code").val();
                if (username && password && inputCode) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/login",
                        type: "POST",
                        data: $("#loginForm").serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.status) {
                                location.href = "${pageContext.request.contextPath}/back/main.jsp"
                            } else {
                                $("#error-message").html("<font color='red'>" + data.message + "<font>");
                            }
                        }
                    })
                } else {
                    $("#error-message").html("<font color='red'>请输入完整信息<font>");
                }
            })
        })

        /*打开模态框*/
        function Reg() {
            $("#mm").modal("show");
        }

        /*验证手机格式，发送短信*/
        function note() {
            var phone = $("#phone").val();
            var phoneReg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
            if (!phoneReg.test(phone)) {
                alert("手机号码格式不正确");
            } else {
                $.ajax({
                    url: "${pageContext.request.contextPath}/message/note",
                    type: "post",
                    data: {phone: phone},
                    datatype: "json",
                    success: function () {
                    }
                })
            }
        }

        /*提交注册信息*/
        function save() {
            // 取到每个文本框的值 做非空判断 非空禁止注册
            var username = $("#username").val();
            var nickname = $("#nickname").val();
            var password = $("#password").val();
            var phone = $("#phone").val();
            var code = $("#code").val();

            if (username && nickname && password && phone && code) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/reg",
                    type: "post",
                    data: $("#admin-form").serialize(),
                    datatype: "json",
                    success: function (data) {
                        // 注册成功 提示并关闭模态框
                        if (data.status) {
                            alert("注册成功，快去登录吧！");
                            $("#mm").modal("hide");
                        } else {
                            // 注册失败 回显异常信息
                            $("#admin-message").html("<font color='red'>" + data.message + "<font>");
                        }
                    }
                })
            } else {
                $("#admin-message").html("<font color='red'>请输入完整信息<font>");
            }
        }
    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to logon:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="${pageContext.request.contextPath}/Admin/login" method="post"
                              class="login-form" id="loginForm">
                            <span id="error-message"></span>
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username" required>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="请输入密码..."
                                       minlength="2" class="form-password form-control" id="form-password" required>
                            </div>
                            <div class="form-group">
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/code/getCode">
                                <input style="padding-left:20px;width: 287px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="code" id="form-code" required placeholder="请输入验证码...">
                            </div>
                            <input type="button"
                                   style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;margin-bottom: 5px;"
                                   id="loginButtonId" value="登录"><br/>

                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   onclick="Reg()" value="注册">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<%--模态框--%>
<div class="modal fade" id="mm" data-backdrop="static" data-keyboard="false" data-show="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width: 833px">
            <!--头-->
            <div class="modal-header">
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
                <div class="modal-title"><h3><strong>管理员注册</strong></h3></div>
            </div>
            <!--身体-->
            <div class="modal-body">

                <!--row-->
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">

                        <form action="" class="form-horizontal" id="admin-form">
                            <span id="admin-message"></span>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">真实姓名:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="username" name="username" class="form-control"
                                           placeholder="请输入真实姓名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">账户密码:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="password" name="password" class="form-control"
                                           placeholder="请输入账户密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户昵称:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="nickname" name="nickname" class="form-control"
                                           placeholder="请输入用户昵称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">联系方式:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="phone" name="phone" class="form-control"
                                           placeholder="请输入联系方式">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">输入短信验证码:</label>
                                <div class="col-sm-6">
                                    <input type="text" id="code" name="code" class="form-control" placeholder="请输入短信验证码"
                                           style="width: 108%;">
                                </div>
                                <div class="col-sm-4">
                                    <%--                                <a class='btn btn-primary' >获取验证码</a>--%>
                                    <button type="button" class="btn btn-primary btn-lg active" style="width: 200px"
                                            onclick="note()" id="tel_btn">获取短信验证码
                                    </button>
                                </div>
                            </div>

                        </form>

                    </div>
                </div>

            </div>
            <!--脚-->
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">关闭</button>
                <button class="btn btn-primary" onclick="save()">保存</button>
            </div>
        </div>
    </div>
</div>

</body>

</html>
