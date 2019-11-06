<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
<script>
    $(function () {
        $("#chapter-table").jqGrid({
            url: '${pageContext.request.contextPath}/article/findAll',
            datatype: "json",
            colNames: ['编号', '标题', '作者', '简介', '内容', '创建时间', '操作'],
            colModel: [
                {name: 'id', hidden: true, editable: false, align: 'center'},
                {name: 'title', align: 'center'},
                {name: 'author', align: 'center'},
                {name: 'brief', align: 'center'},
                {name: 'content', hidden: true},
                {name: 'createDate', align: 'center'},
                {
                    name: 'operation ', align: 'center', formatter: function (value, option, rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','" + rows.id + "')\">修改</a>" +
                            "&nbsp;&nbsp;<a class='btn btn-danger' onclick=\"del('" + rows.id + "')\">删除</a>";
                    }
                }
            ],
            height: 250,
            autowidth: true,
            styleUI: "Bootstrap",
            rowNum: 8,
            rowList: [3, 5, 10],
            pager: '#chapter-page',
            viewrecords: true


        }).navGrid("#chapter-page", {edit: false, add: false, del: false, search: false}
        );
    })

    // 模态框函数
    function openModal(oper, id) {
        if ("add" == oper) {
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-brief").val("");
            KindEditor.html("#editor_id", "");
        }
        if ("edit" == oper) {
            //    获取jqgrid 中选中的数据的值 注意：只获取展示的值
            var a = $("#chapter-table").jqGrid("getRowData", id);
            // 为模态框中input 填充值
            $("#article-id").val(a.id);
            $("#article-title").val(a.title);
            $("#article-author").val(a.author);
            $("#article-brief").val(a.brief);
            KindEditor.html("#editor_id", a.content);
        }
        // 打开模态框
        $("#mm").modal("show");
    }

    // 添加修改
    function save() {
        var id = $("#article-id").val();
        var url = "";
        if (id) {
            url = "${pageContext.request.contextPath}/article/edit?oper=upload"
        } else {
            url = "${pageContext.request.contextPath}/article/edit?oper=add"
        }
        $.ajax({
            url: url,
            type: "post",
            data: $("#article-form").serialize(),
            datatype: "json",
            success: function () {
                $("#mm").modal("hide");
                $("#chapter-table").trigger("reloadGrid");
            }
        })
    }

    function del(id) {
        // var id = $("#article-id").val();
        // alert(id);
        $.ajax({
            url: "${pageContext.request.contextPath}/article/edit?oper=del",
            type: "post",
            data: {id: id},
            datatype: "json",
            success: function () {
                $("#chapter-table").trigger("reloadGrid");
            }
        })
    }

    KindEditor.create('#editor_id', {
        afterBlur: function () {
            this.sync();
        }

    });
</script>
<script>

</script>


<%--创建表格--%>


<ul class="nav nav-tabs">
    <li class="active"><a href="#home" data-toggle="tab">所有文章</a></li>
    <li role="presentation"><a href="#" data-toggle="modal" onclick="openModal('add','')">添加文章</a></li>
</ul>
<table id="chapter-table"></table>
<%--创建分页--%>
<div id="chapter-page" style="height: 40px"></div>


<!--构建模态框-->
<div class="modal fade" id="mm" data-backdrop="static" data-keyboard="false" data-show="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width: 833px">
            <!--头-->
            <div class="modal-header">
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
                <div class="modal-title"><h3><strong>文章操作</strong></h3></div>
            </div>
            <!--身体-->
            <div class="modal-body">

                <!--row-->
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">

                        <form action="" class="form-horizontal" id="article-form">
                            <input type="hidden" name="id" id="article-id">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章标题:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="article-title" name="title" class="form-control"
                                           placeholder="请输入文章标题">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章作者:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="article-author" name="author" class="form-control"
                                           placeholder="请输入文章作者">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章简介:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="article-brief" name="brief" class="form-control"
                                           placeholder="请输入文章简介">
                                </div>
                                <%--kindeditor输入框--%>
                                <textarea id="editor_id" name="content" style="width:678px;height:300px;">

                                </textarea>
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