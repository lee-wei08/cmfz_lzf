<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>展示所有用户</h1>
</div>

<script>
    $(function () {
        $("#user-show-table").jqGrid({
            url: '${pageContext.request.contextPath}/user/selectAll',
            datatype: "json",
            colNames: ['编号', '名字', '封面', '描述', '省份', '城市', '手机', '性别', '注册日期'],
            colModel: [
                {name: 'id', hidden: true, editable: false, align: 'center'},
                {name: 'username', editable: true, align: 'center'},
                {
                    name: 'photo',
                    align: 'center',
                    editable: true,
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:130px;height:50px;' src='${pageContext.request.contextPath}/back/img/" + rows.photo + "'>";
                    }
                },
                {name: 'sign', editable: true, align: 'center'},
                {name: 'province', editable: true, align: 'center'},
                {name: 'city', editable: true, align: 'center'},
                {name: 'phone', align: 'center'},
                {name: 'sex', edittype: "select", editoptions: {value: "男:男;女:女"}, align: 'center'},
                {name: 'createDate', align: 'center'}
            ],
            height: 250,
            autowidth: true,
            styleUI: "Bootstrap",
            rowNum: 4,
            rowList: [3, 5, 10],
            pager: '#user-page',
            viewrecords: true


        }).navGrid("#user-page", {edit: false, add: false, del: false, search: false}
        );
    })

</script>
<ul class="nav nav-tabs">
    <li class="active"><a href="" data-toggle="tab">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/download" data-toggle="modal">导出用户</a></li>
</ul>
<!--创建表格-->
<table id="user-show-table"></table>


<!--分页-->
<div id="user-page"></div>