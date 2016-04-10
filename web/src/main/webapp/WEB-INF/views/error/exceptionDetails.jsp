<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <a href="#" id="error-detail"><font color="green">点击显示详细错误信息</font></a>
    <%--<a href="#" class="btn btn-link">&gt;&gt;报告给系统管理员</a>--%>
    <div class="detail" style="display:none;">
        ${stackTrace}
    </div>
<script type="text/javascript">
    $("#error-detail").click(function() {
        var a = $(this);
        a.find("i").toggleClass("icon-collapse-alt").toggleClass("icon-expand-alt");
        $(".detail").toggle();
    });
</script>