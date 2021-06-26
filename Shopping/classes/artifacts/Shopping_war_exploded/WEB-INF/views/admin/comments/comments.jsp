<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>商城后台</title>
<jsp:include page="../commonCss.jsp" />
</head>
<body class="blank">
<jsp:include page="../main/top.jsp" />
<jsp:include page="../main/left.jsp" />
<jsp:include page="../commonJs.jsp" />
<div id="wrapper">
	<div class="small-header transition animated fadeIn">
		<div class="content animate-panel">
			<div class="hpanel">
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="hpanel">
								<div id="error_div" class="text-left"></div>
								<form id="form_userInfo" name="form_userInfo" class="form-horizontal"
									action="userInfo_save.do" method="POST">
									<div class="form-group col-lg-6">
										<label>开始时间</label>
										<div class="input-group col-sm-10 date">
											<input name="createTimeFrom" type="text" class="form-control">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label>结束时间</label>
										<div class="input-group col-sm-10 date">
											<input name="createTimeTo" type="text" class="form-control">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
									<div class="form-group col-lg-5">
										<label>商品名</label>
											<input name="productName" type="text" class="form-control">
									</div>
									<div class="form-group col-lg-6">
									<div class="text-right m-t-xs">
										<button id="search" class="btn btn-success"
											style="margin-right: 5px; margin-top: 5px" type="button">
											<strong>查询</strong>
										</button>
										<button id="reset" class="btn btn-success"
											style="margin-right: 5px; margin-top: 5px"
											style="margin-right: 20px;" type="reset">
											<strong>重置</strong>
										</button>
										<button class="btn btn-success" onclick="examineAllComments(1)"
											style="margin-right: 5px; margin-top: 5px" type="button">
												<strong>全部通过</strong>
										</button>
										<button  class="btn btn-success" onclick="examineAllComments(0)"
											style="margin-right: 5px; margin-top: 5px" type="button">
												<strong>全部驳回</strong>
										</button>
										<button class="btn btn-success" onclick="deleteAllComments()"
											style="margin-right: 5px; margin-top: 5px" type="button">
												<strong>全部删除</strong>
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
	<div class="content animate-panel">
		<div class="row">
			<div class="col-lg-12">
				<div class="hpanel">
					<div class="panel-body">
						<table id="commentsList" style="width: 100%;"
							class="table table-striped table-bordered table-hover"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    var checkid = [];
	var oTable;
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	$(function() {
		$('.date').datepicker();
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
		commentsListData()
	});
	$('#search').click(function() {
		commentsListData();
	});
	//选择全部
	function allCheckCancel(item) {
		if($(item).is(':checked')){
			checkid=[];
			$("[name=checkid]").prop("checked",true);
			for(var i = 0;i<$("[name=checkid]").length;i++){
				checkid[i] = $("[name=checkid]").eq(i).val();
			}
		}else{
			$("[name=checkid]").prop("checked",false);
			checkid = [];
		}
	}
	//审核
	$('#audit').click(function() {
		if(checkid.length==0){
			alert("请选择列");
		}else{
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/admin/auditTable",
				data: JSON.stringify({"idList":checkid}),
				dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
				async : true,
				success : function(json) {
					alert("审核成功");
				},
				error : function(json) {
					alert(1);
				}
			});
		}
	})
function toJson(checkList,audit) {
	var dataArray=[];
	$(checkList).each(function(i, v) {
		var blankDataRow = {};
		blankDataRow["id"] = v;
		if(audit!=null){
			blankDataRow["audit"] = audit;	
		}
		dataArray.push(blankDataRow);
	});
	return JSON.stringify(dataArray);
}
function examineAllComments(audit){
	if(checkid.length==0){
	    swal({
               title: "提示",
               text: "请选择需要审核的评论！"
      	    });
	}else{
		swal({
			title : "确定审核吗?",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#62cb31",
			confirmButtonText : "审核",
			cancelButtonText : '取消',
		}, function() {
				$.ajax({
					type : "POST",
					contentType : "application/json;charset=utf-8", 
					url : "${pageContext.request.contextPath}/admin/comments/examine",
					data: toJson(checkid,audit),
					dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
					async : true,
					success : function(json) {
						if(json>0){
							commentsListData();
						}
						checkid = [];
						choose
						$("#choose").prop("checked",false);
					},
					error : function(json) {
						alert(json);
					}
				})	
		});
	}
}
function deleteAllComments(){
	if(checkid.length==0){
	    swal({
               title: "提示",
               text: "请选择需要删除的评论！"
      	    });
	}else{
		swal({
			title : "确定删除吗?",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#62cb31",
			confirmButtonText : "删除",
			cancelButtonText : '取消',
		}, function() {
				$.ajax({
					type : "POST",
					contentType : "application/json;charset=utf-8", 
					url : "${pageContext.request.contextPath}/admin/comments/delete",
					data: toJson(checkid,null),
					dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
					async : true,
					success : function(json) {
						if(json>0){
							commentsListData();
						}
						checkid = [];
						choose
						$("#choose").prop("checked",false);
					},
					error : function(json) {
						alert(json);
					}
				})	
		});
	}
}
function examineComments(id,audit){
	swal({
		title : "确定审核吗?",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#62cb31",
		confirmButtonText : "审核",
		cancelButtonText : '取消',
	}, function() {
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8", 
			url : "${pageContext.request.contextPath}/admin/comments/examine",
			data: JSON.stringify([{"id":id,"audit":audit}]),
			dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
			async : true,
			success : function(json) {
				if(json>0){
					commentsListData();
				}
			},
			error : function(json) {
				alert(json);
			}
		});
	});
	
}
function deleteComments(id){
	swal({
		title : "确定删除吗?",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#62cb31",
		confirmButtonText : "删除",
		cancelButtonText : '取消',
	}, function() {
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8", 
			url : "${pageContext.request.contextPath}/admin/comments/delete",
			data: JSON.stringify([{"id":id}]),
			dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
			async : true,
			success : function(json) {
				if(json>0){
					commentsListData();
				}
			},
			error : function(json) {
				alert(json);
			}
		});
	});
}
//单选
function checkPerform(obj){
	if($(obj).is(':checked')){
		checkid.push($(obj).val());
	}else{
		checkid.splice(jQuery.inArray($(obj).val(),checkid),1);
	}
}
function commentsListData() {
	var contextPath = '${pageContext.request.contextPath}';
	if (typeof oTable == 'undefined') {
		// dataTable load
		oTable = $('#commentsList').dataTable({
							'bPaginate' : true, // 翻页功能
							'sServerMethod' : 'POST', // 提交方式
							'bServerSide' : true,
							'bProcessing' : true,
							'bFilter' : false,
							"bSort" : false,
							'sAjaxSource' : contextPath
									+ '/admin/commentsDataTable',
							"fnServerParams" : function(aoData) { // 查询条件
								aoData.push({
									"name" : "productName",
									"value" : $('[name=productName]').val()
								}, {
									"name" : "createTimeFrom",
									"value" : $('[name=createTimeFrom]').val()
								}, {
									"name" : "createTimeTo",
									"value" : $('[name=createTimeTo]').val()
								});
							},
							aoColumns : [{
										'mData' : 'id',
										'sTitle' : '编号',
										'visible' : false
									},{
										'mData' : 'id',
										'sTitle' : '<input id="choose" type="checkbox" name="checkid" value="2" onclick="allCheckCancel(this)">',
										'sWidth' : '2%',
										'mRender' : function(data, type, full) {
											var v = '<input type="checkbox" name="checkid" value="'+data+'" onclick="checkPerform(this)">';
											return v;
										},
								
									},
									{
										'sTitle' : '用户名',
										'mData' : 'userName'
									},
									{
										'sTitle' : '商品名',
										'mData' : 'productName'
									},
									{
										'sTitle' : '内容',
										'mData' : 'content',
										'mRender' : function(data, type, full) {
											var divData = "<div>"+data+"</div>"
											return divData;
										}
									},
									{
										'sTitle' : '评论星级',
										'mData' : 'stars',
										'mRender' : function(data, type, full) {
											var stars = "";
											for(var i = 0;i<data;i++){
												stars+="*";
											}
											return stars;
										}
									},
									{
										'sTitle' : '创建时间',
										'mData' : 'createTime',
										'mRender' : function(data, type, full) {
											return data;	
										}
									},{
										'sTitle' : '审核状态',
										'mData' : 'audit',
										'sWidth' : '5%',
										'mRender' : function(data, type, full) {
											if(data==1){
												return "已审核";
											}else{
												return "未审核";
											}
										}
									},{
										'sTitle' : '操作',
										'mData' : 'id',
										'sWidth' : '13%',
										'mRender' : function(data, type, full) {
											var returnValue = '<button id="update" class="btn btn-info btn-sm" onclick="examineComments('+data+',1)">通过</button>&nbsp;&nbsp;';
											returnValue+='<button id="update" class="btn btn-info btn-sm" onclick="examineComments('+data+',0)">驳回</button>&nbsp;&nbsp;';
											returnValue+='<button id="update" class="btn btn-info btn-sm" onclick="deleteComments('+data+')">删除</button>';
											return returnValue;
										}
									}]

						});

	} else {
		// oTable.fnClearTable(0);
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayLength = parseInt($(
				'[name=commentsList_length] option:selected').val());
		$('.dataTables_length select').val(
				$('[name=commentsList_length] option:selected').val());
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}
}
</script>
</body>
</html>