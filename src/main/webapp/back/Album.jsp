<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>展示所有明星信息</h1>
</div>

<script>
    $(function () {
        $("#al-show-table").jqGrid({
            url: '${pageContext.request.contextPath}/Album/findAll',
            datatype: "json",
            height: 250,
            colNames: ['编号', '专辑名称', '专辑作者', '专辑封面', '音乐数量', '专辑简介', '创建时间'],
            colModel: [
                {name: 'id', hidden: true, editable: false, align: 'center'},
                {name: 'name', editable: true, align: 'center',},
                {
                    name: 'starId', editable: true, edittype: "select", editoptions: {
                        dataUrl: "${pageContext.request.contextPath}/star/All",
                    }, formatter: function (value, options, row) {
                        return row.star.nickname;
                    }
                },

                {
                    name: 'cover',
                    editable: true,
                    align: "center",
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:130px;height:50px;' src='${pageContext.request.contextPath}/back/img/" + rows.cover + "'>";
                    }
                },
                {name: 'count', editable: false, align: "center"},
                {name: 'brief', editable: true},
                {name: 'createDate', editable: true, edittype: "date"}
            ],
            styleUI: 'Bootstrap',
            autowidth: true,
            rowNum: 4,
            rowList: [8, 10, 20, 30],
            pager: '#al-page',
            sortname: 'id',
            viewrecords: true,
            subGrid: true,
            editurl: "${pageContext.request.contextPath}/Album/edit",

            subGridRowExpanded: function (subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${pageContext.request.contextPath}/chapter/findAll?albumId=" + id,
                        datatype: "json",
                        colNames: ['编号', '名字', '歌手', '大小', '时长', '创建时间', '在线播放'],
                        colModel: [
                            {name: "id", hidden: true},
                            {name: "name", editable: true, edittype: "file"},
                            {name: "singer", editable: true},
                            {name: "size"},
                            {name: "duration"},
                            {name: "createDate"},
                            {
                                name: "operation", width: 300, formatter: function (value, option, rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/back/music/" + rows.name + "' >\n" +
                                        "</audio>";
                                }
                            }
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 3,
                        pager: pager_id,
                        autowidth: true,
                        viewrecords: true,
                        height: '100%',
                        // 执行增删改是的url
                        editurl: "${pageContext.request.contextPath}/chapter/edit?albumId=" + id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: false,
                        add: true,
                        del: false,
                        search: false
                    }, {}, {
                        // 控制二级表格添加
                        closeAfterAdd: true,
                        afterSubmit: function (data) {
                            var status = data.responseJSON.status;
                            var cid = data.responseJSON.message;
                            if (status) {
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "post",
                                    fileElementId: "name",
                                    data: {id: cid, albumId: id},
                                    success: function (response) {
                                        //自动刷新jqgrid表格
                                        $("#al-show-table").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "123";
                        }
                    });
            },

        }).navGrid("#al-page", {
            edit: false,
            add: true,
            del: false
        }, {}, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/Album/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#al-show-table").trigger("reloadGrid");
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
        <table id="al-show-table"></table>
    </div>
</div>
<!--分页-->
<div id="al-page"></div>