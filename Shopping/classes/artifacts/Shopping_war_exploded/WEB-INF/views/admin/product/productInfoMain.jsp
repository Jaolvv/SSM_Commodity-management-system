<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
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
	<div class="small-header transition">
		<div class="content">
		    <div class="hpanel">
		        <div class="panel-body">
					<div class="row">
								<div id="error_div" class="text-left"></div>
									<form:form modelAttribute="product" id="form_productInfo" name="form_productInfo" method="POST">
										<div class="form-group col-lg-6">
											<label>商品名</label>
											<input type="text" id="name" name="name" class="form-control" maxlength="50" placeholder="商品名"/>	
										</div>
										<div class="form-group col-lg-6">
											<label>商品分类</label>
											<form:select path="categoryId" items="${categoryMap}" cssClass="form-control"/>  
										</div>
										<div class="form-group col-lg-6">
											<label>热门商品</label>
					                        <br><input type="radio" value="0" class="i-checks" name="hot">&nbsp;&nbsp;非热门商品
					                        &nbsp;&nbsp;<input type="radio" value="1" class="i-checks" name="hot">&nbsp;&nbsp;热门商品
					                        &nbsp;&nbsp;<input type="radio" value="2" class="i-checks" name="hot" checked>&nbsp;&nbsp;全部
					                        
				                        </div>
										<div class="form-group col-lg-6">
											<label>状态</label>
					                        <select name="productStatus" class="form-control">
													<option value="2" selected="selected">全部</option>
													<option value="0">已上架</option>
													<option value="1">已下架</option>
											</select>
										</div>
										<div class="form-group col-lg-6">
											<label>更新日期</label>
					                        <div class="input-group date">
					                            <input name="updateTimeFrom" type="text" class="form-control">
					                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					                        </div>
				                        </div>
										<div class="form-group col-lg-6">
											<label></label>
					                        <div class="input-group date">
					                            <input name="updateTimeTo" type="text" class="form-control">
					                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					                        </div>
										</div>	
										<div class="form-group col-lg-12">
											<div class="text-right m-t-xs">
												<button id="add" class="btn btn-success" type="button">
														<strong>添加</strong>
												</button>
												<button id="search" class="btn btn-success" type="button">
													<strong>查询</strong>
												</button>
											    <button id="reset" class="btn btn-success" type="reset">
													<strong>重置</strong>
											   </button>
											</div>
										</div>
									</form:form>
								</div>
						</div>
					</div>
			</div>
	</div>
	<div class="content">	
		<div class="row">
			<div class="col-lg-12">
				<div class="hpanel">
					<div class="panel-body">
						<table id="productInfoList" style="width: 100%;" class="table table-striped table-bordered table-hover"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="forwordform" action='<c:url value="/admin/product/add?${_csrf.parameterName}=${_csrf.token}" />' method='POST'>
	<input type="hidden" name="productId" id="productId"/>
</form>
<script type="text/javascript">
$('#leftMenuProduct').addClass('active');
var oTable;
var header = $("meta[name='_csrf_header']").attr("content");  
var token = $("meta[name='_csrf']").attr("content"); 
var contextPath = '${pageContext.request.contextPath}';
$(function() {
	$('.date').datepicker();
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	productInfoListData()
});

$('#add').click(function() {
	addProductInfo();
});

$('#search').click(function() {
	//alert($("input[name='hot']:checked").val());
	productInfoListData();
});
function productInfoListData() {
	if (typeof oTable == 'undefined') {
		// dataTable load
		oTable = $('#productInfoList').dataTable({
							'bPaginate' : true, // 翻页功能
							'sServerMethod' : 'POST', // 提交方式
							'bServerSide' : true,
							'bProcessing' : true,
							'bFilter' : false,
							"bSort" : false,
							'sAjaxSource' : contextPath + '/admin/productInfoDataTable',
							"fnServerParams" : function(aoData) { // 查询条件
								aoData.push({
												"name" : "name",
												"value" : $('[name=name]').val()
											},{
												"name" : "categoryId",
												"value" : $('[name=categoryId]').val()
											},{
												"name" : "hot",
												"value" : $('[name=hot]:checked').val()
											},{
												"name" : "productStatus",
												"value" : $('[name=productStatus]').val()
											},{
												"name" : "updateTimeFrom",
												"value" : $('[name=updateTimeFrom]').val()
											},{
												"name" : "updateTimeTo",
												"value" : $('[name=updateTimeTo]').val()
											});
							},aoColumns : [{
										'mData' : 'id',
										'sTitle' : '商品编号',
										'visible' : false
									},{
										'sTitle' : '商品名',
										'mData' : 'name'
									},{
										'sTitle' : '商品分类',
										'mData' : 'categoryName'
									},{
										'sTitle' : '商品概要说明',
										'mData' : 'generalExplain'
									},{
										'sTitle' : '店内价格',
										'mData' : 'shopPrice',
										'mRender' : function(data, type, full) {
											var returnValue = accDiv(parseFloat(data),100).toFixed(2);
											return returnValue;
										}
									},{
										'sTitle' : '市场价格',
										'mData' : 'price',
										'mRender' : function(data, type, full) {
											var returnValue = accDiv(parseFloat(data),100).toFixed(2);
											return returnValue;
										}
									},{
										'sTitle' : '数量',
										'mData' : 'quantity'
									},{
										'sTitle' : '热门商品',
										'mData' : 'hot',
										'mRender' : function(data, type, full) {
											//var returnValue = accDiv;
											var returnValue =" ";
											if (data == 0) {
												returnValue = "否";
											} else {
												returnValue = "是";
											}
											return returnValue;
										}
									},{
										'sTitle' : '状态',
										'mData' : 'productStatus',
										'mRender' : function(data, type, full) {
											var returnValue = "";
											if (data == 0) {
												returnValue = "已上架";
											} else {
												returnValue = "已下架";
											}
											return returnValue;
										}
									},{
										'sTitle' : '更新时间',
										'mData' : 'updateTime',
										'mRender' : function(data) {
											return data;
										}
									},{
										'sTitle' : '更新者',
										'mData' : 'updateUserName',
										'mRender' : function(data) {
											return data;
										}
									},{
										'sTitle' : '操作',
										'mData' : 'id',
										'sWidth' : '5%',
										'mRender' : function(data, type, full) {
											var returnValue = '<button id="update" class="btn btn-default btn-sm" onclick="editProductInfo('+data+')"><i class="fa fa-pencil"></i>编辑</button>';
											return returnValue;
										}
									}] 

						});
	} else {
		// oTable.fnClearTable(0);
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayLength = parseInt($(
				'[name=productInfoList_length] option:selected').val());
		$('.dataTables_length select').val(
				$('[name=productInfoList_length] option:selected').val());
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}
}
function editProductInfo(productId){	
	$("#productId").val(productId);
	$("#forwordform").attr('action','<c:url value="/admin/product/addEdit?${_csrf.parameterName}=${_csrf.token}" />')
	$("#forwordform").submit();
}

function addProductInfo(){	
	$("#forwordform").attr('action','<c:url value="/admin/product/addEdit?${_csrf.parameterName}=${_csrf.token}" />')
	$("#forwordform").submit();
}
</script>
</body>
</html>