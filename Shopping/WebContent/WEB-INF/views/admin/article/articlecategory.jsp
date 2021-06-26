<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                    <div class="row">
                        <div class="hpanel">
                            <div id="error_div" class="text-left"></div>
                            <form id="form_userInfo" name="form_userInfo" method="POST">
                                <div class="form-group col-lg-6">
                                    <label>更新时间</label>
                                    <div class="input-group date">
                                        <input name="updateTimeFrom" type="text" class="form-control"
                                               readonly="readonly" style="background-color: transparent;">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>至</label>
                                    <div class="input-group date">
                                        <input name="updateTimeTo" type="text" class="form-control" readonly="readonly"
                                               style="background-color: transparent;">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>分类名</label>
                                    <input type="text" name="categoryName"
                                           class="form-control" maxlength="50" placeholder="分类名"
                                           onchange="illegalChar(this)"/>
                                </div>
                                <div class="form-group col-lg-6">
                                    <div class="text-right m-t-xs">
                                        <button id="search" class="btn btn-success" type="button"><strong>查询</strong>
                                        </button>
                                        <button id="reset" class="btn btn-success" type="reset"><strong>重置</strong>
                                        </button>
                                        <button id="new" onclick="resetId()" data-toggle="modal" data-target="#myModal"
                                                class="btn btn-success" type="button"><strong>新增</strong></button>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>

                </div>
            </div>

        </div>

    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="col-lg-12">
                <div class="hpanel">
                    <div class="panel-body">
                        <table id="articleCategoryInfoList" style="width: 100%;"
                               class="table table-striped table-bordered table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="text-center">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="color-line"></div>
                <div class="text-center col-lg-12" style="width: 100%;height: 60px;background-color: white;">
                    <h6 class="modal-title">修改/新增</h6>
                    <small class="font-bold"></small>
                </div>
                <div class="modal-body">
                    <form id="form_saveArticleCategory name=" form_userInfo
                    " action="" method="">
                    <div class="form-inline">
                        <strong>分类名:</strong>

                        <input type="hidden" id="categoryId"/>
                        <input type="text" id="categoryName" onchange="illegalChar(this)"
                               class="form-control" maxlength="50" placeholder="分类名"/>

                    </div>
                    <div class="text-right m-t-xs">
                        <button id="save" onclick="saveArticleCategory()" class="btn btn-success"
                                style="margin-right: 5px;margin-top: 5px"
                                type="button">
                            <strong>保存</strong>
                        </button>
                        <button type="button" id="over" class="btn btn-default" data-dismiss="modal"><strong>关闭</strong>
                        </button>
                    </div>
                    </form>
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
        userInfoListData();
    });
    $('#search').click(function () {
        if ($("input[name='categoryName']").val().length <= 20) {
            userInfoListData();
        } else {
            swal({
                title: "输入长度不能超过20位",
                type: "error"
            });
        }

    });

    function saveArticleCategory() {
        var saveVo = {};
        var ur;
        if ($("#categoryName").val() != "" && $("#categoryName").val() != undefined) {
            if ($("#categoryName").val().length <= 20) {
                saveVo.categoryName = $("#categoryName").val();
                if ($("#categoryId").val() == "" || $("#categoryId").val() == undefined) {
                    ur = "admin/receiveSaveArticleCategory";
                } else {
                    ur = "admin/receiveSetArticleCategory";
                    saveVo.categoryId = $("#categoryId").val();
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
            } else {
                swal({
                    title: "输入长度不能超过20位",
                    type: "error"
                });
            }
        } else {
            swal({
                title: "分类名不能为空",
                type: "error"
            });
        }

    }

    function update(id) {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/admin/receiveUpdateArticleCategory",
            data: JSON.stringify(id),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                $("#categoryName").val(data.categoryName);
                $("#categoryId").val(data.categoryId);
            }
        });
    }

    function showcommon(data) {
        var error = "";
        if (data.contentError == "1") {
            error = "分类下存在内容!";
        } else if (data.nameError == "1") {
            error = "分类名重复!";
        }
        var title = "";
        if (data.title == "1") {
            title = "成功";
        } else if (data.title == "2") {
            title = "失败";
        }
        swal({
            title: title,
            text: error,
            type: data.type
        });
    }

    function resetId() {
        $("#categoryId").val("");
        $("#categoryName").val("");
    }

    function removeCategory(i) {
        swal({
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
                    var delVo = {};
                    delVo.categoryId = i;
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/admin/receiveDelArticleCategory",
                        data: JSON.stringify(delVo),
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
            oTable = $('#articleCategoryInfoList').dataTable({
                'bPaginate': true, // 翻页功能
                'sServerMethod': 'POST', // 提交方式
                'bServerSide': true,
                'bProcessing': true,
                'bFilter': false,
                "bSort": false,
                'sAjaxSource': contextPath + '/admin/receiveGetArticleCategory',
                "fnServerParams": function (aoData) { // 查询条件
                    aoData.push({
                        "name": "categoryName",
                        "value": $('[name=categoryName]').val()
                    }, {
                        "name": "updateTimeFrom",
                        "value": $('[name=updateTimeFrom]').val()
                    }, {
                        "name": "updateTimeTo",
                        "value": $('[name=updateTimeTo]').val()
                    });
                }, aoColumns: [{
                    'mData': 'categoryId',
                    'sTitle': '分类编号',
                    'visible': false
                }, {
                    'sTitle': '分类名',
                    'mData': 'categoryName'
                }, {
                    'sTitle': '最后更新时间',
                    'mData': 'updateTime'
                }, {
                    'sTitle': '更新者名',
                    'mData': 'updateUserId'
                }, {
                    'sTitle': '操作',
                    'mData': 'categoryId',
                    'sWidth': '10%',
                    'mRender': function (data) {
                        var returnValue = '<button id="update" data-toggle="modal" data-target="#myModal" onclick="update(' + data + ')" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>编辑</button><button id="remove" onclick="removeCategory(' + data + ')" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>删除</button>';
                        return returnValue;
                    }
                }]

            });
        } else {
            var oSettings = oTable.fnSettings();
            oSettings._iDisplayLength = parseInt($('[name=articleCategoryInfoList_length] option:selected').val());
            $('.dataTables_length select').val($('[name=articleCategoryInfoList_length] option:selected').val());
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
</script>
</body>
</html>