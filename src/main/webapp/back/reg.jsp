<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%--为Echarts 提供一个具备宽高的DOM--%>
<div id="main" style="width: 600px;height: 400px"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册趋势'
        },
        tooltip: {},
        legend: {
            data: ['男', '女']
        },
        xAxis: {
            data: ["一", "二", "三", "四", "五", "六"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'line',//bar:柱状图
            data: [5, 20, 36, 10, 10, 20]
        }, {
            name: '女',
            type: 'line',//bar:柱状图
            data: [15, 20, 32, 40, 20, 25]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url: "${pageContext.request.contextPath}/user/reg",
        type: "get",
        datatype: "json",
        success: function (data) {
            console.log(data);

            myChart.setOption({
                xAxis: {
                    data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
                },
                series: [{
                    name: '男',
                    type: 'bar',//bar:柱状图
                    data: data.man
                }, {
                    name: '女',
                    type: 'bar',//bar:柱状图
                    data: data.nv
                }]
            });
        }
    })


</script>