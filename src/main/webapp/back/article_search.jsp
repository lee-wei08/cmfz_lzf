<%@page pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#article-search").click(function () {
            var content = $("#article-search").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/article/search",
                type: "post",
                datatype: "json",
                data: {content: content},
                success: function (data) {
                    $("#article-search-show").empty();
                    $.each(data, function (i, article) {
                        var tr = $("<tr align=\"center\">" +
                            "<td>" + article.title + "</td>" +
                            "<td>" + article.author + "</td>" +
                            "<td>" + article.brief + "</td>" +
                            "<td><a class='btn btn-danger'onclick=\"show('" + article.content + "')\">内容详情</a></td>" +
                            "</tr>");
                        $("#article-search-show").append(tr);
                    })
                }
            })
        })
    })

    /*在模态框中显示文章详细内容*/
    function show(content) {
        $("#pp").html(content);
        $("#mm").modal("show");
    }


</script>


<div class="row">
    <div class="col-sm-2"></div>
    <div class="col-sm-6">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="请输入您要搜索的关键字..." aria-describedby="basic-addon2">
            <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" id="article-search"
                            style="margin-bottom: 20px;">百度一下</button>
                </span>
        </div>
    </div>

    <div class="col-sm-4"></div>
    <%-- border='3px' cellpadding='5px' cellspacing="0px" --%>
    <table align='center' border='3px' class="table table-bordered" id="article-search-show"></table>
</div>
<%--模态框--%>
<div class="modal fade" id="mm" data-backdrop="static" data-keyboard="false" data-show="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width: 833px">
            <!--头-->
            <div class="modal-header">
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
                <div class="modal-title"><h3><strong>文章内容详情</strong></h3></div>
            </div>
            <!--身体-->
            <div class="modal-body">

                <!--row-->
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">

                        <p id="pp"></p>

                    </div>
                </div>

            </div>
            <!--脚-->
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>