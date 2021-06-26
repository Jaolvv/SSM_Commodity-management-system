<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>商城后台</title>
    <jsp:include page="../commonCss.jsp"/>
</head>
<body class="blank">
<jsp:include page="../main/top.jsp"/>
<jsp:include page="../main/left.jsp"/>
<jsp:include page="../commonJs.jsp"/>
<input type="hidden" id="parameterName" value="${_csrf.parameterName}">
<input type="hidden" id="token" value="${_csrf.token}">
<div id="wrapper">
    <div class="small-header transition animated fadeIn">
        <div class="content animate-panel">
            <div class="hpanel">
                <div class="panel-body">
                    <div class="row">
                        <div class="hpanel">
                            <div id="error_div" class="text-left"></div>
                            <form id="form_userInfo" class="form-horizontal"
                                  name="form_userInfo" method="POST">
                                <div class="form-group col-lg-12">
                                    <label class="control-label">标题</label>
                                    <div>
                                        <input type="hidden" id="id" value="${model.id}">
                                        <input type="text" id="title" name="title" class="form-control"
                                               maxlength="50" onchange="illegalChar(this)" style="width: 87%"
                                               value="${model.title}"/>
                                        <input type="text" style="display:none;">
                                    </div>
                                </div>
                                <div class="form-group col-lg-12">
                                    <input type="hidden" id="modelCategoryId" value="${model.category}">
                                    <label class="control-label">文章分类</label>
                                    <div>
                                        <select class="form-control" name="orStatus"
                                                id="categoryId" style="width: 87%">
                                        </select>
                                    </div>
                                </div>

                                <input type="hidden" id="content" value='${model.content}'>
                                <div class="form-group col-lg-12">
                                    <label>内容</label>
                                    <script id="container" type="text/plain"></script>
                                    <!-- 配置文件 -->
                                    <script type="text/javascript"
                                            src="${pageContext.request.contextPath}/resources/admin/js/ueditor/ueditor.config.js"></script>
                                    <!--　编辑器源码文件 -->
                                    <script type="text/javascript"
                                            src="${pageContext.request.contextPath}/resources/admin/js/ueditor/ueditor.all.js"></script>
                                    <!--　实例化编辑器 -->
                                    <script type="text/javascript">
                                        var ue = UE.getEditor('container', {autoWidth: true});
                                        ue.ready(function () {
                                            ue.setContent($("#content").val());
                                        });
                                    </script>
                                </div>

                                <div class="form-group col-lg-12">
                                    <div class="text-left col-sm-9">
                                        <button class="btn btn-success"
                                                style="margin-right: 5px; margin-top: 5px" type="button" id="save"
                                                onclick="saveArticle()">
                                            <strong>保存</strong>
                                        </button>
                                        <button id="new" onclick="ret()" data-toggle="modal"
                                                data-target="#myModal" class="btn btn-success"
                                                style="margin-right: 5px; margin-top: 5px"
                                                style="margin-right: 20px;" type="button">
                                            <strong>返回</strong>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    $('#leftMenuArticle').addClass('active');
    var oTable;
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $(function () {
        $('.date').datepicker();
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        getAllCategory();
        $('.summernote2').summernote({
            airMode: true,
        });
    });

    function getAllCategory() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/admin/receiveAllCategory",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                var str = "";
                for (i in data) {
                    var option = "<option value=" + data[i].categoryId + ">" + data[i].categoryName + "</option>";
                    str = str + option;
                }
                $("#categoryId").append(str);
                $("#categoryId").val($("#modelCategoryId").val());
            }
        });
    }

    function saveArticle() {
        var flag = true;
        var saveVo = {};
        var ur;
        if ($("#title").val() == "" || $("#title").val() == undefined) {
            flag = false;
            swal({
                title: "标题不能为空",
                type: "error"
            });
        }
        if ($("#title").val().length > 20) {
            flag = false;
            swal({
                title: "输入长度不能大于20位",
                type: "error"
            });
        }
        if ($("#categoryId").val() == "" || $("#categoryId").val() == undefined) {
            flag = false;
            swal({
                title: "分类不能为空",
                type: "error"
            });
        }

        if (flag) {
            saveVo.title = $("#title").val();
            saveVo.category = $("#categoryId").val();
            saveVo.content = ue.getContent();
            if ($("#id").val() == "" || $("#id").val() == undefined) {
                ur = "admin/receiveSaveArticle";
            } else {
                ur = "admin/receiveSetArticle";
                saveVo.id = $("#id").val();
            }

            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/" + ur,
                data: JSON.stringify(saveVo),
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (data) {
                    showcommon(data);
                }
            });
        }


    }

    function update(id) {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/admin/receiveUpdateArticle",
            data: JSON.stringify(id),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                $("#title").val(data.title);
                $("#categoryId").val(data.category);
                $("#content").val(data.content);
            }
        });
    }

    function showcommon(data) {
        var type = "";
        var title = "";
        if (data == 1) {
            type = "success";
            title = "成功";
        } else {
            type = "error";
            title = "失败";
        }
        swal({
            title: title,
            type: type
        });
    }

    function ret() {
        location.href = "${pageContext.request.contextPath}/admin/article";
    }

    function removeArticle(i) {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/admin/receiveDelArticle",
            data: JSON.stringify(i),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                showcommon(data);
                userInfoListData();
            }
        });
    }

    function illegalChar(data) {
        var re = /^[0-9a-zA-Z\u4e00-\u9fa5]+$/;
        if (!re.exec(data.value)) {
            data.value = "";
        }
    }
</script>
</body>
</html>