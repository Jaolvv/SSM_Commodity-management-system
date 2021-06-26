<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/admin/vendor/webuploader/webuploader.css">
    <title>商城后台</title>
    <jsp:include page="../commonCss.jsp"/>
</head>
<body class="blank">
<jsp:include page="../main/top.jsp"/>
<jsp:include page="../main/left.jsp"/>
<jsp:include page="../commonJs.jsp"/>
<script src="${pageContext.request.contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/vendor/bootstrap-fileinput-master/js/fileinput.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/admin/vendor/bootstrap-fileinput-master/js/fileinput_locale_zh.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/admin/vendor/webuploader/webuploader.js"
        type="text/javascript"></script>
<div id="wrapper">
    <div class="small-header transition">
        <div class="content">
            <div class="hpanel">
                <div class="panel-body">
                    <div class="row">
                        <div class="hpanel">
                            <div id="error_div" class="text-left"></div>
                            <form id="form_productCategory" name="form_productCategory"
                                  action='<c:url value="/admin/save?${_csrf.parameterName}=${_csrf.token}" />'
                                  method="POST">
                                <input type="hidden" id="id" name="id" value="${productCategory.id}"/>
                                <div class="form-group col-lg-12">
                                    <label>商品类别名</label>
                                    <input type="text" id="name" name="name" class="form-control" maxlength="50"
                                           placeholder="商品类别名" value='${productCategory.name}'/>
                                    <input type="hidden" id="nameOld" value='${productCategory.name}'/>
                                </div>
                                <div class="form-group col-lg-12">
                                    <label>排序</label>
                                    <input type="text" id="sortOrder" name="sortOrder" class="form-control"
                                           maxlength="50" placeholder="排序" value='${productCategory.sortOrder}'/>
                                </div>
                                <div class="form-group col-lg-12">
                                    <label>图片</label>
                                    <input type="hidden" id="image" name="image" value='${productCategory.image}'/>
                                    <!--dom结构部分-->
                                    <div id="uploader-demo">
                                        <!--用来存放item-->
                                        <div id="fileList" class="uploader-list"></div>
                                        <div id="filePicker">选择图片</div>
                                        <div id="imageFile">
                                            <c:if test="${not empty productCategory.image}">
                                                <img src="${pageContext.request.contextPath}${productCategory.image}"
                                                     style="width: 150px;height: 150px"/>
                                            </c:if>
                                            <c:if test="${empty productCategory.image}">
                                                <img src=""/>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div style='padding-top:10px'>
                                        上传图片的最佳尺寸：300像素*300像素，其他尺寸会影响页面效果，格式png，jpeg，jpg，gif。大小不超过1M
                                    </div>
                                </div>
                                <div class="form-group col-lg-12">
                                    <label>描述</label>
                                    <textarea rows="3" cols="20" id="description" name="description"
                                              class="form-control" maxlength="2000"></textarea>
                                    <input type="hidden" id="descriptionName" value='${productCategory.description}'/>
                                </div>
                                <div class="form-group col-lg-12">
                                    <label>更新日期</label>
                                    <input type="text" id="updateTime" readonly="readonly" name="updateTime"
                                           class="form-control" maxlength="50"
                                           value='<fmt:formatDate value="${productCategory.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
                                </div>
                                <div class="form-group col-lg-12">
                                    <label>更新者</label>
                                    <input type="text" id="updateUserName" readonly="readonly" name="updateUserName"
                                           class="form-control" maxlength="50"
                                           value='${productCategory.updateUserName}'/>
                                </div>
                                <div class="text-left m-t-xs">
                                    <button id="save" class="btn btn-success" style="margin-left: 2px;" type="button">
                                        <strong>保存</strong>
                                    </button>
                                    <button id="back" class="btn btn-success" type="button">
                                        <strong>返回</strong>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    $('#leftMenuProduct').addClass('active');
    var oTable;
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var contextPath = '${pageContext.request.contextPath}';
    var validate;

    // 初始化Web Uploader
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,

        // swf文件路径
        swf: contextPath + '/resources/vendor/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: contextPath + '/admin/newUploadfile?${_csrf.parameterName}=${_csrf.token}',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });

    uploader.on('uploadAccept', function (object, ret) {
// 		console.log(object);
// 		console.log(ret.fileName);
        $("#image").val(ret.fileName);
    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {

        $('#imageFile').html('');
        var $li = $(
            '<div id="' + file.id + '" class="file-item">' +
            '<img>' +
            '</div>'
            ),
            $img = $li.find('img');
        // $list为容器jQuery实例
        $('#imageFile').append($li);

        var ratio = window.devicePixelRatio || 1;
        // 缩略图大小
        var thumbnailWidth = 150 * ratio;
        var thumbnailHeight = 150 * ratio;
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr('src', src);
        }, thumbnailWidth, thumbnailHeight);
    });

    $(function () {
        document.getElementById("description").innerText = $("#descriptionName").val();
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        validate = $("#form_productCategory").validate({
            onsubmit: false,
            focusInvalid: false,
            errorClass: "error",
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            ignoreTitle: true,
            rules: {
                name: {
                    required: true,
                    maxlength: 50,
                    remote: {
                        url: contextPath + "/admin/validationName?${_csrf.parameterName}=${_csrf.token}",
                        type: "post",
                        dataType: "json",
                        async: false,
                        data: {
                            name: function () {
                                return $("#name").val()
                            },
                            id: function () {
                                return $("#id").val()
                            }
                        }
                    }
                },
                sortOrder: {
                    required: true,
                    digits: true,
                    maxlength: 4
                }
            },
            messages: {
                name: {
                    required: "商品类别名必填",
                    maxlength: "最大位数不能超过50位",
                    remote: "商品分类名重复"
                },
                sortOrder: {
                    required: "排序必填",
                    digits: "只能输入正整数",
                    maxlength: "只能输入4位整数"
                }
            }
        });
    });

    /** 返回  */
    $("#back").bind("click", function () {
        window.location.href = contextPath + '/admin/productCategoryList';
    });

    /** 保存  */
    $("#save").bind("click", function () {
        if (!$("#form_productCategory").valid()) {
            return;
        }
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            url: contextPath + '/admin/save',
            data: formToJson($("#form_productCategory")),
            dataType: "json",
            async: false,
            success: function (json) {
                $("#updateTime").val(json.data.updateTime);
                $("#updateUserName").val(json.data.updateUserName);
                $("#id").val(json.data.id)
                swal({
                    title: "保存成功!",
                    text: "",
                    type: "success"
                });
            },
            error: function (json) {
                alert(json);
            }
        });
    });

    /** 获取from表单数据  */
    function formToJson($form) {
        var data = {};
        $($form.serializeArray()).each(function (i, v) {
            data[v.name] = v.value;
        });
        return JSON.stringify(data);
    }

</script>
</body>
</html>