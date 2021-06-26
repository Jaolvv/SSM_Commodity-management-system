<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<div class="small-header transition animated fadeIn">
		<div class="content animate-panel">
		    <div class="hpanel">
		        <div class="panel-body">
					<div class="row">
						<div class="col-lg-8">
							<div class="hpanel">
								<div id="error_div" class="text-left"></div>
									<form id="form_userInfo" name="form_userInfo"  method="POST">
										<div class="form-group col-lg-12">
											<label>订单编号</label>
												<input type="text" id="orderNum" name="orderNum" class="form-control col-sm-12" maxlength="50" placeholder="订单编号" style="width: 100%"/>
										</div>
										<div class="form-group col-lg-6">
											<label>付款标志</label>
												<select name="paymentFlag" class="form-control">
													<option value="" selected="selected">全部</option>
													<option value="0">未付</option>
													<option value="1">已付</option>
												</select>
										</div>
										
										<div class="form-group col-lg-6">
											<label>订单状态</label>
												<select name="status" class="form-control">
													<option value="" selected="selected">全部</option>
													<option value="0">订单编辑中</option>
													<option value="1">已下单</option>
													<option value="2">配送中</option>
													<option value="3">配送完成</option>
													<option value="4">订单取消</option>
												</select>
										</div>
										
										
										<div class="form-group col-lg-6">
											<label>总价</label>
												<input type="text" id="startPrice" name="startPrice" class="form-control" maxlength="50" placeholder=""/>
										</div>
										<div class="form-group col-lg-6">
											<label>至</label>
												<input type="text" id="endPrice" name="endPrice" class="form-control" maxlength="50" placeholder=""/>
										</div>
										
										
										<div class="form-group col-lg-6">
											<label>下单时间</label>
					                        <div class="input-group col-sm-12 date">
					                            <input name="StartDate" type="text" class="form-control">
					                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					                        </div>
				                        </div>
										<div class="form-group col-lg-6">
											<label>至</label>
					                        <div class="input-group col-sm-12 date">
					                            <input name="endDate" type="text" class="form-control">
					                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					                        </div>
										</div>
										
		
										<div class="text-right m-t-xs">
												<button id="search" class="btn btn-success" style="margin-right: 5px;margin-top: 5px"
													type="button">
													<strong>查询</strong>
												</button>
											    <button id="reset" class="btn btn-success" style="margin-right: 5px;margin-top: 5px"
												style="margin-right: 20px;" type="reset">
												<strong>重置</strong>
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
		<div class="content animate-panel">	
			<div class="row">
				<div class="col-lg-12">
					<div class="hpanel">
						<div class="panel-body">
							<table id="userInfoList" style="width: 100%;" class="table table-striped table-bordered table-hover"></table>
						</div>
					</div>
				</div>
		</div>
	</div>
	</div>

<form id="forwordform" action='<c:url value="/admin/order/History?${_csrf.parameterName}=${_csrf.token}" />' method='POST'>
	<input type="hidden" name="userId" id="userId"/>
	<input type="hidden" name="roleStr" id="roleStr"/>
	<input type="hidden" name="orderNum" id="ordernum">
	
</form>
<script type="text/javascript">
$('#leftMenuOrder').addClass('active');
var oTable;
var header = $("meta[name='_csrf_header']").attr("content");  
var token = $("meta[name='_csrf']").attr("content"); 
var contextPath = '${pageContext.request.contextPath}';
$(function() {
	$('.date').datepicker();
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	userInfoListData()
});

$('#add').click(function() {
	addAdmin();
});

$('#search').click(function() {
	userInfoListData();
});
function userInfoListData() {
	if (typeof oTable == 'undefined') {
		// dataTable load
		oTable = $('#userInfoList').dataTable({
			'bPaginate' : true, // 翻页功能
			'sServerMethod' : 'POST', // 提交方式
			'bServerSide' : true,
			'bProcessing' : true,
			'bFilter' : false,
			"bSort" : false,
			'sAjaxSource' : contextPath + '/order/orderInfoDataTable',
			"fnServerParams" : function(aoData) { // 查询条件
				aoData.push({
								"name" : "orderNum",
								"value" : $('[name=orderNum]').val()
							},{
								"name" : "paymentFlag",
								"value" : $('[name=paymentFlag]').val()
							},{
								"name" : "status",
								"value" : $('[name=status]').val()
							},{
								"name" : "StartDate",
								"value" : $('[name=StartDate]').val()
							},{
								"name" : "endDate",
								"value" : $('[name=endDate]').val()
							},{
								"name" : "startPrice",
								"value" : $('[name=startPrice]').val()
							},{
								"name" : "endPrice",
								"value" : $('[name=endPrice]').val()
							});
			}, 
			aoColumns : [{
						'mData' : 'orderNum',
						'sTitle' : '订单编号'
						},{
							'mData' : 'price',
							'sTitle' : '总价',
							'mRender' : function(data, type, full) {
								return data/100;
							}
						},{
							'mData' : 'paymentFlag',
							'sTitle' : '付款标志',
							'mRender' : function(data, type, full) {
								var returnValue = "";
								if (data == 0) {
									returnValue = "未付";
								} else {
									returnValue = "已付";
								}
								return returnValue;
							}
						},{
							'mData' : 'status',
							'sTitle' : '订单状态',
							'mRender' : function(data, type, full) {
								var returnValue = "";
								if (data == 0) {
									returnValue = "订单编辑中";
								} else if (data == 1) {
									returnValue = "已下单";
								} else if (data == 2) {
									returnValue = "配送中";
								} else if (data == 3) {
									returnValue = "配送完成";
								} else{
									returnValue = "订单取消";
								}
								return returnValue;
							}
						},{ 'mData' : 'contactName',
							'sTitle' : '联系人'
						},{ 'mData' : 'contactMobile',
							'sTitle' : '联系电话'
						},{
							'mData' : 'createTime',
							'sTitle' : '订单时间'
						},{
							'sTitle' : '操作',
							'mData' : 'userId',
							'sWidth' : '5%',
							'mRender' : function(data, type, full) {
								//alert(full.role);
								var returnValue = '<button id="update" class="btn btn-default btn-sm" onclick="lookOrder('+data+',\''+full.orderNum+'\')"><i class="fa fa-pencil"></i>查看</button>';
								return returnValue;
							}
						}] 

			});
	} else {
		oTable.fnClearTable(0);
 		var oSettings = oTable.fnSettings();
/* 		oSettings._iDisplayLength = parseInt($('[name=userInfoList_length] option:selected').val());
		$('.dataTables_length select').val(('[name=userInfoList_length] option:selected').val()); */
		oSettings._iDisplayStart = 0;
		oTable.fnDraw(); 
	}
}
function lookOrder(userId,orderNum){
	$("#ordernum").val(orderNum);
	$("#userId").val(userId);
	$("#forwordform").submit();
}

function addAdmin(){	
	window.location.href =contextPath+'/admin/adduser' ;
}

</script>
</body>
</html>