<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>展示所有明星信息</h1>
</div>

<script>
    $(function () {
        $("#star-show-table").jqGrid({

            url: '${pageContext.request.contextPath}/star/findAll',
            datatype: "json",
            height: 250,
            colNames: ['编号', '艺名', '真名', '照片', '性别', '生日'],
            colModel: [
                {name: 'id', hidden: true, editable: false, align: 'center'},
                {name: 'nickname', editable: true, align: 'center',},
                {name: 'realname', editable: true, align: 'center'},
                {
                    name: 'photo',
                    editable: true,
                    align: "center",
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:130px;height:50px;' src='${pageContext.request.contextPath}/back/img/" + rows.photo + "'>";
                    }
                },
                {name: 'sex', editable: true, align: "center", edittype: "select", editoptions: {value: "男:男;女:女"}},
                {name: 'bir', align: "center"}
            ],
            styleUI: 'Bootstrap',
            autowidth: true,
            rowNum: 5,
            rowList: [8, 10, 20, 30],
            pager: '#star-page',
            sortname: 'id',
            viewrecords: true,
            subGrid: true,
            editurl: "${pageContext.request.contextPath}/star/edit",
            subGridRowExpanded: function (subgrid_id, id) {
//我们传递两个参数
// subgrid_id是在表数据中创建的div标记的id
//这个元素的id是行的“sg_”+ id的组合
// row_id是行的id
//如果我们想要传递额外的参数到我们可以使用的url
//一个方法getRowData(row_id)——它返回类型为name-value的关联数组
//在这里，我们可以很容易地构建流动
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${pageContext.request.contextPath}/user/findAll?starId=" + id,
                        datatype: "json",
                        colNames: ['编号', '用户名', '昵称', '头像', '电话', '性别', '地址', '签名'],
                        colModel: [
                            {name: "id", hidden: true, editable: false},
                            {name: "username"},
                            {name: "nickname"},
                            {
                                name: "photo", edittype: "file", formatter: function (value, option, rows) {
                                    return "<img style='width:130px;height:50px;' src='${pageContext.request.contextPath}/back/img/" + rows.photo + "'>";
                                }
                            },
                            {name: "phone"},
                            {name: "sex"},
                            {name: "address"},
                            {name: "sign"}
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 3,
                        pager: pager_id,
                        autowidth: true,
                        viewrecords: true,
                        height: '50%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: false,
                        add: false,
                        del: false,
                        search: false
                    });
            },

        }).navGrid('#star-page', {
            add: true,
            edit: false,
            del: false,
            search: false
        }, {}, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/star/upload",
                        type: "post",
                        fileElementId: "photo",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });


    })


</script>


<%--创建表格--%>
<div class="panel panel-default">
    <div class="panel-heading">所有明星数据</div>
    <div class="panel-body">
        <!--创建表格-->
        <table id="star-show-table"></table>
    </div>
</div>
<!--分页-->
<div id="star-page"></div>