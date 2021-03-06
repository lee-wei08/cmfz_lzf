<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>展示所有轮播图</h1>
</div>

<script>
    $(function () {
        $("#banner-show-table").jqGrid({
            url: '${pageContext.request.contextPath}/banner/findAll',
            datatype: "json",
            colNames: ['编号', '名称', '封面', '描述', '状态', '上传日期'],
            colModel: [
                {name: 'id', hidden: true, editable: false, align: 'center'},
                {name: 'name', editable: true, align: 'center'},
                {
                    name: 'cover',
                    align: 'center',
                    editable: true,
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:130px;height:50px;' src='${pageContext.request.contextPath}/back/img/" + rows.cover + "'>";
                    }
                },
                {name: 'description', editable: true, align: 'center'},
                {
                    name: 'status',
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "正常:正常;冻结:冻结"},
                    align: 'center'
                },
                {name: 'create_date', align: 'center'}
            ],
            height: 250,
            autowidth: true,
            styleUI: "Bootstrap",
            rowNum: 4,
            rowList: [3, 5, 10],
            pager: '#banner-page',
            sortname: 'id',
            viewrecords: true,
            caption: "轮播图列表",
            editurl: "${pageContext.request.contextPath}/banner/edit"
        }).navGrid("#banner-page", {edit: true, add: true, del: true, search: false}, {
            //控制修改
            closeAfterEdit: true,
            beforeShowForm: function (fmt) {
                fmt.find("#cover").attr("disabled", true);
            }
        }, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#banner-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
    })

</script>

<%--带标题的面板--%>
<div class="panel panel-default">
    <div class="panel-heading">所有轮播图数据</div>
    <div class="panel-body">
        <!--创建表格-->
        <table id="banner-show-table"></table>
    </div>
</div>

<!--分页-->
<div id="banner-page"></div>