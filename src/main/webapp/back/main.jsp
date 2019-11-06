<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--当前页面更好支持移动端-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--引入bootstrap核心样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrapgrid/bootstrap/css/bootstrap.min.css"/>
    <%--引入jqgrid核心基础样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrapgrid/jqgrid/css/ui.jqgrid.css"/>
    <%--引入jqgrid的bootstra皮肤--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrapgrid/jqgrid/css/ui.jqgrid-bootstrap.css"/>
    <%--引入jquery核心js--%>
    <script src="${pageContext.request.contextPath}/bootstrapgrid/jquery-3.4.1.min.js"></script>
    <%--引入boot核心js--%>
    <script src="${pageContext.request.contextPath}/bootstrapgrid/bootstrap/js/bootstrap.min.js"></script>
    <%--引入jqgrid核心js--%>
    <script src="${pageContext.request.contextPath}/bootstrapgrid/jqgrid/js/jquery.jqGrid.min.js"></script>

    <%--引入i18njs--%>
    <script src="${pageContext.request.contextPath}/bootstrapgrid/jqgrid/js/i18n/grid.locale-cn.js"></script>

    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法州后台管理系统
                <small>SSS</small>
            </a>
        </div>

        <!--导航条内容-->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua">${admin.username}</font></a></li>
                <li><a href="${pageContext.request.contextPath}/login/login.jsp">退出登录 <span
                        class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>
</nav>

<!--页面主体内容-->
<div class="container-fluid">
    <div class="row">
        <!--手风琴-->
        <div class="col-sm-2">
            <!--面板 class属性 “panel” 代表面板有边框-->
            <div class=" panel-danger">
                <%--轮播图--%>
                <div class="panel-heading" role="tab" id="lunboPanel" style="margin-bottom: 5px;">
                    <h4 class="panel-title" style="text-align: center">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#LBOTU"
                           aria-expanded="true" aria-controls="collapseOne">
                            轮播图管理
                        </a>
                    </h4>
                </div>
                <div id="LBOTU" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/img.jsp');"
                                    id="btn">所有轮播图</a></li>
                        </ul>
                    </div>
                </div>
                <%--专辑--%>
                <div class="panel-heading" role="tab" id="zhuanjiPanel" style="margin-bottom: 5px;">
                    <h4 class="panel-title" style="text-align: center">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#ZJ" aria-expanded="true"
                           aria-controls="collapseOne">
                            专辑管理
                        </a>
                    </h4>
                </div>
                <div id="ZJ" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/Album.jsp');">专辑列表</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <%--文章--%>
                <div class="panel-heading" role="tab" id="wenzhangPanel" style="margin-bottom: 5px;">
                    <h4 class="panel-title" style="text-align: center">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#WZ" aria-expanded="true"
                           aria-controls="collapseOne">
                            文章管理
                        </a>
                    </h4>
                </div>
                <div id="WZ" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/article.jsp');">文章列表</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <%--用户--%>
                <div class="panel-heading" role="tab" id="userPanel" style="margin-bottom: 5px;">
                    <h4 class="panel-title" style="text-align: center">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#user"
                           aria-expanded="true" aria-controls="collapseOne">
                            用户管理
                        </a>
                    </h4>
                </div>
                <div id="user" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/user.jsp');">用户列表</a>
                            </li>
                        </ul>
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/reg.jsp');">用户注册趋势</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <%--明星--%>
                <div class="panel-heading" role="tab" id="starPanel">
                    <h4 class="panel-title" style="text-align: center">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#STAR"
                           aria-expanded="true" aria-controls="collapseOne">
                            明星管理
                        </a>
                    </h4>
                </div>
                <div id="STAR" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item" style="text-align: center"><a
                                    href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/star.jsp');">明星列表</a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>

        <%-- 页面主题内容 --%>
        <div class="col-sm-10" id="centerLayout">
            <%--巨幕--%>
            <div class="jumbotron">
                <p>欢迎光临持明法州后台管理系统</p>
            </div>
            <%--图片--%>
            <img src="${pageContext.request.contextPath}/back/img/微信图片_20190916092057.jpg" alt="..." class="img-rounded"
                 style=" width: 100%;">

        </div>
    </div>


    <%--文字--%>
    <div class="page-header">
        <h4 style="text-align: center">持明法州后台管理系统@百知教育</h4>
    </div>
</div>


</body>
</html>