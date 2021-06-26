<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body>
	<jsp:include page="../main/top.jsp" />
	<jsp:include page="../main/left.jsp" />
	<jsp:include page="../commonJs.jsp" />
	<div id="wrapper">
		<!-- Simple splash screen-->
		<div class="splash">
			<div class="color-line"></div>
		</div>
		<!--[if lt IE 7]>
<p class="alert alert-danger">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
		<div class="content animate-panel">
			<div class="row">
				<div class="col-lg-12">
					<div class="hpanel">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-6">
									<h4>
										订单编号： <small>${order.orderNum}</small>
									</h4>
								</div>
								<div class="col-md-6">
									<div class="text-right">
										<button class="btn btn-primary btn-sm" data-toggle="modal"
											data-target="#myModal">
											<i class="pe-7s-plane"></i> 物流配送
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body p-xl">
							<div class="row m-b-xl">
								<div class="col-sm-6">
									<h4>订单状态： 
										<c:if test="${order.status==null || order.status==0}">
											订单编辑中
										</c:if>
										<c:if test="${order.status==1}">
											已下单
										</c:if>
										<c:if test="${order.status==2}">
											配送中
										</c:if>
										<c:if test="${order.status==3}">
											配送完成
										</c:if>
										<c:if test="${order.status==4}">
											订单取消
										</c:if>
									</h4>
									<address>
										<strong>客户姓名：${order.contactName}</strong><br> 客户地址:${order.contactAddress}<br> <abbr
											title="Phone">联系电话:</abbr> ${order.contactMobile}
									</address>
								</div>
								<div class="col-sm-6 text-right">
									<p>
										<span><strong>订单更新时间:</strong>
											<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd  HH:mm:ss" var="d" />
											${d}
											</span><br />
									</p>
								</div>
							</div>
							<div class="m-t">
								<strong>订单明细</strong>
							</div>
							<div class="table-responsive m-t">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>商品名称</th>
											<th>数量</th>
											<th>单价</th>
											<th>小计</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${order.orderItemList}" var="z" varStatus="s">
											<tr>
												<td>
													<div>
														<strong>${z.productList[0].name}</strong>
													</div> 
													<small>${z.jsonData}</small>
												</td>
												<td>${z.quantity}</td>
												<td>￥${z.price/100}</td>
												<td>￥${z.price*z.quantity/100}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="row m-t">
								<div class="col-md-4 col-md-offset-8">
									<table class="table table-striped text-right">
										<tbody>
											<tr>
												<td><strong>合计 :</strong></td>
												<td>￥${order.price/100}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="m-t">
										<strong>订单历史</strong>
										<div class="table-responsive m-t">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>操作明细</th>
														<th>操作时间</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${order.orderHistoryList}" var="z" varStatus="i">
														<tr>
															<td>
																<div>
																	<strong>
																		<c:if test="${z.status==0 || z.status==null}">
																			订单编辑中
																		</c:if>
																		<c:if test="${z.status==1}">
																			已下单
																		</c:if>
																		<c:if test="${z.status==2}">
																			配送中
																		</c:if>
																		<c:if test="${z.status==3}">
																			<div id="cs">配送完成</div>
																		</c:if>
																		<c:if test="${z.status==4}">
																			订单取消
																		</c:if>
																	</strong>
																</div>
															</td>
															<td><small>
																<fmt:formatDate value="${z.createTime}" pattern="yyyy-MM-dd  HH:mm:ss" var="d" />
																${d}
															</small></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Right sidebar -->
		<div id="right-sidebar" class="animated fadeInRight">
			<div class="text-center">
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="color-line"></div>
							<div class="text-center col-lg-12"
								style="width: 100%; height: 60px; background-color: white;">
								<h6 class="modal-title">物流配送</h6>
								<small class="font-bold"></small>
							</div>
							<div class="modal-body">
								<form id="form_saveArticleCategory name="
									form_userInfo" action="" method="">
									<input type="hidden" id="orderNum" value="${order.orderNum}">
									<div class="form-inline">
										<div class="col-sm-3" style="width: 20%; padding-top: 5px">
											<strong>物流公司名:</strong>
										</div>
										<div class="col-sm-9">
											<input type="text" name="companyName" id="companyName"
												onchange="illegalChar(this)" class="form-control"
												maxlength="50" />
										</div>
									</div>
									<div class="form-inline">
										<div class="col-sm-3" style="width: 20%; padding-top: 5px">
											<strong>配送状态:</strong>
										</div>
										<div class="col-sm-9">
											<select id="state" name="state">
												<option value="2">配送</option>
												<option value="3">配送完了</option>
											</select>
										</div>
									</div>
									<div class="text-right m-t-xs">
										<button id="save" onclick="subLogistics()"
											class="btn btn-success"
											style="margin-right: 5px; margin-top: 5px" type="button">
											<strong>确定发货</strong>
										</button>
										<button type="button" id="over" class="btn btn-default"
											data-dismiss="modal">
											<strong>关闭</strong>
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
		$(function() {
			$('.date').datepicker();
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		});
		function subLogistics() {
			if($("#cs").text()!=""){
				$("#over").click();
				return;
			}
			var flag = true;
			if ($("#companyName").val() == "" || $("#companyName") == undefined) {
				flag = false;
				swal({
					title : "物流公司名不能为空",
					type : "error"
				});
			}
			if ($("#companyName").val().length > 20) {
				flag = false;
				swal({
					title : "输入长度不能大于20位",
					type : "error"
				});
			}
			if (flag) {
				var logistics = {};
				logistics.orderNum = $("#orderNum").val();
				logistics.companyName = $("#companyName").val();
				logistics.state = $("#state").val();
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/admin/order/saveOrderLogistics",
					data : JSON.stringify(logistics),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						if (data == 1) {
							$("#over").click();
							lookOrder();
						} else {
							swal({
								title : "失败",
								type : "error"
							});
						}
					}
				});
			}
		}
		function illegalChar(data) {
			var re = /^[0-9a-zA-Z\u4e00-\u9fa5]+$/;
			if (!re.exec(data.value)) {
				data.value = "";
			}
		}
		function lookOrder(){
			$("#ordernum").val("${order.orderNum}");
			$("#userId").val("${order.userId}");
			$("#forwordform").submit();
		}
	</script>
</body>
</html>