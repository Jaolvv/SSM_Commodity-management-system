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
<div id="wrapper">
    <div class="small-header transition animated fadeIn">
        <div class="content animate-panel">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="error_div" class="text-left"></div>
                    <form id="form_userInfo" name="form_userInfo" method="POST">
                        <div class="row">
                            <div class="form-group col-lg-6">
                                <label>标题</label>
                                <input type="text" name="title" class="form-control" maxlength="50"
                                       onchange="illegalChar(this)"/>
                            </div>
                            <div class="form-group col-lg-6">
                                <label>文章分类</label>
                                <input type="text" name="articleCategoryName" class="form-control" maxlength="50"
                                       placeholder="分类名" onchange="illegalChar(this)"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-6">
                                <label>更新时间</label>
                                <div class="input-group col-sm-10 date">
                                    <input name="updateTimeFrom" type="text" class="form-control"
                                           style="background-color: transparent;">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                </div>
                            </div>
                            <div class="form-group col-lg-6">
                                <label>至</label>
                                <div class="input-group col-sm-10 date">
                                    <input name="updateTimeTo" type="text" class="form-control"
                                           style="background-color: transparent;">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-12">
                                <div class="text-right m-t-xs">
                                    <button id="search" class="btn btn-success" type="button">
                                        <strong>查询</strong>
                                    </button>
                                    <button id="reset" class="btn btn-success" type="reset">
                                        <strong>重置</strong>
                                    </button>
                                    <button id="new" onclick="add()" class="btn btn-success"
                                            type="button">
                                        <strong>新增</strong>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="col-lg-12">
                <div class="hpanel">
                    <div class="panel-body">
                        <table id="userInfoList" style="width: 100%;"
                               class="table table-striped table-bordered table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="forwordform"
      action='<c:url value="/admin/receiveUpdateArticle?${_csrf.parameterName}=${_csrf.token}" />'
      method='POST'>
    <input type="hidden" name="id" id="ArticleId"/>
</form>
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
        userInfoListData();
        $('.summernote2').summernote({
            airMode: true,
        });
    });
    $('#search').click(function () {
        var flag = true;
        if ($("input[name='title']").val().length > 20) {
            flag = false;
            lengthcheck();
        }
        if ($("input[name='articleCategoryName']").val().length > 20) {
            flag = false;
            lengthcheck();
        }
        if (flag) {
            userInfoListData();
        }
    });

    function lengthcheck() {
        swal({
            title: "输入长度不能超过20位",
            type: "error"
        });
    }

    function saveArticle() {
        var saveVo = {};
        var ur;
        if ($("#title").val() != "" && $("#title").val() != undefined) {
            saveVo.title = $("#title").val();
            saveVo.category = $("#categoryId").val();
            saveVo.content = $("#content").val();
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
                    $("#over").click();
                    showcommon(data);
                    userInfoListData();
                }
            });
        }

    }

    function update(id) {
        $
            .ajax({
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

    function add() {
        location.href = "${pageContext.request.contextPath}/admin/articleadd";
    }

    function removeArticle(i) {

        swal(
            {
                title: "确定要删除该条记录?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm) {
                    $
                        .ajax({
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
            });

    }

    function userInfoListData() {
        var contextPath = '${pageContext.request.contextPath}';
        if (typeof oTable == 'undefined') {
            // dataTable load
            oTable = $('#userInfoList')
                .dataTable(
                    {
                        'bPaginate': true, // 翻页功能
                        'sServerMethod': 'POST', // 提交方式
                        'bServerSide': true,
                        'bProcessing': true,
                        'bFilter': false,
                        "bSort": false,
                        'sAjaxSource': contextPath
                            + '/admin/receiveGetArticle',
                        "fnServerParams": function (aoData) { // 查询条件
                            aoData
                                .push(
                                    {
                                        "name": "title",
                                        "value": $(
                                            '[name=title]')
                                            .val()
                                    },
                                    {
                                        "name": "articleCategoryName",
                                        "value": $(
                                            '[name=articleCategoryName]')
                                            .val()
                                    },
                                    {
                                        "name": "updateTimeFrom",
                                        "value": $(
                                            '[name=updateTimeFrom]')
                                            .val()
                                    },
                                    {
                                        "name": "updateTimeTo",
                                        "value": $(
                                            '[name=updateTimeTo]')
                                            .val()
                                    });
                        },
                        aoColumns: [
                            {
                                'mData': 'id',
                                'sTitle': '编号',
                                'visible': false
                            },
                            {
                                'sTitle': '标题',
                                'mData': 'title'
                            },
                            {
                                'sTitle': '分类名',
                                'mData': 'articleCategoryName'
                            },
                            {
                                'sTitle': '最后更新时间',
                                'mData': 'updateTime'/* ,
																						'mRender' : function(data, type, full) {
																							string t=Convert.ToDateTime(data).ToString("yyyy-MM-dd");//格式转换	
																							return t;
																							} */
                            },
                            {
                                'sTitle': '更新者名',
                                'mData': 'updateUserId'
                            },
                            {
                                'sTitle': '操作',
                                'mData': 'id',
                                'sWidth': '10%',
                                'mRender': function (data) {
                                    var returnValue = '<button id="update"  onclick="openPostWindow('
                                        + data
                                        + ')" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>编辑</button><button id="remove" onclick="removeArticle('
                                        + data
                                        + ')" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>删除</button>';
                                    return returnValue;
                                }
                            }]

                    });
        } else {
            // oTable.fnClearTable(0);
            var oSettings = oTable.fnSettings();
            oSettings._iDisplayLength = parseInt($(
                '[name=userInfoList_length] option:selected').val());
            $('.dataTables_length select').val(
                $('[name=userInfoList_length] option:selected').val());
            oSettings._iDisplayStart = 0;
            oTable.fnDraw();
        }
    }

    function illegalChar(data) {
        var re = /^[0-9a-zA-Z\u4e00-\u9fa5]+$/;
        if (!re.exec(data.value)) {
            data.value = "";
        }
    }

    function openPostWindow(params) {
        /* 	var newWin = window.open(),
         formStr = '';
         formStr = '<form style="visibility:hidden;" method="POST" action="
        ${pageContext.request.contextPath}/admin/receiveUpdateArticle?
        ${_csrf.parameterName}=
        ${_csrf.token}">' +
			 '<input type="hidden" name="id" value="' + params + '" />' +
			 '</form>';
			
			 newWin.document.body.innerHTML = formStr;
			 newWin.document.forms[0].submit();
			 return newWin; */
        $("#ArticleId").val(params);
        $("#forwordform").submit();
    }
</script>
</body>
</html>