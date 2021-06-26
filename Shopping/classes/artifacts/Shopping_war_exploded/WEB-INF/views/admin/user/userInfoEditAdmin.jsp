<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   

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
								<div class="hpanel">
									<div id="error_div" class="text-left"></div>
									<form id="form_userInfo" name="form_userInfo" action='<c:url value="/admin/user/save?${_csrf.parameterName}=${_csrf.token}" />' method="POST">
										<input type="hidden" id="id" name="id" value="${user.id}"/>	
										<div class="form-group col-lg-12">
											<label>管理员名</label>
											<input type="text" id="userName" name="userName" class="form-control" maxlength="50" placeholder="会员名"	value='${user.userName}' />
										</div>
										<div class="form-group col-lg-12">
											<label>昵称</label>
											<input type="text" id="nickname" name="nickname"  class="form-control" maxlength="50" placeholder="昵称"  value='${user.nickname}' />
										</div>
										<div class="form-group col-lg-12">
											<label>密码</label>
											<input type="password" id="pwd" name="pwd"	class="form-control" maxlength="50" placeholder="密码"  />
										</div>
										<div class="form-group col-lg-12">
											<label>确认密码</label>
					        				<input type="password" id="pwd2" name="pwd2"	class="form-control" maxlength="50" placeholder="确认密码"  />
			      						</div>
										<div class="form-group col-lg-12">
											<label>email</label>
											<input type="text" id="email" name="email"	class="form-control" maxlength="50" placeholder="email" value='${user.email}' />
										</div>
										<div class="form-group col-lg-12">
											<label>用户角色</label>
											<div>
												<div class="form-group checkbox checkbox-success checkbox-inline" style="margin-top:3px; margin-bottom: 10px">
													<input type="checkbox" id="permisskey1" name="role" value="ROLE_ADMIN" disabled="disabled" checked >
													<label>管理员</label>
												</div>
												<div class="form-group checkbox checkbox-success checkbox-inline" style="margin-top:3px; margin-bottom: 10px">
													<input type="checkbox" id="permisskey2" name="role" value="ROLE_USER" disabled="disabled">
													<label>一般用户 </label>
												</div>
											</div>
										</div>
										<div class="text-left m-t-xs">
											<button id="save" class="btn btn-success" type="button">
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

	<script type="text/javascript">
		var oTable;
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		var contextPath = '${pageContext.request.contextPath}';
		var validate;
		$(function() {
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
			contectListData();
			
			
			  validate = $("#form_userInfo").validate({
				  	onsubmit:false,
			    	focusInvalid:false,
			    	errorClass:"error",
			    	onfocusout:false,
			    	onkeyup:false,
			    	onclick:false,
			    	ignoreTitle:true,
			    	
	                rules:{
	                	userName:{
	                        required:true,
	                        remote:{
	                        	 url:contextPath+"/admin/user/checkUserName?${_csrf.parameterName}=${_csrf.token}",
	                             type:"post",
	                             dataType:"json",
	                             async:false,
	                             data:{
	                            	 userName:function (){return $("#userName").val()},
	                            	 id:function (){return $("#id").val()} 
	                             }
	                        }
	                    },
	                    pwd:{
	                    	required:true,
	                        rangelength:[6,20]
	                    },
	                    email:{
	                        email:true
	                    },
	                    pwd2:{
	                        equalTo:"#pwd"
	                    }                    
	                },
	                messages:{
	                	userName:{
	                        required:"管理员名必填",
	                        remote:"管理员姓名已经存在"
	                    },
	                    pwd:{
	                        required:"密码必填",
	                        rangelength: "密码6-20位"
	                    },
	                    email:{
	                        email:"E-Mail格式不正确"
	                    },
	                    pwd2:{
	                        equalTo:"两次密码输入不一致"
	                    }                                    
	                }
	                          
	            });    

			
			
			
		});
		$("#back").bind("click",function(){
			window.location.href =contextPath+'/admin/user';
		});
		$("#save").bind("click",function(){
			
			if (!$("#form_userInfo").valid()) {
				return;
			}
			
			$.ajax({
				type : "POST",
				contentType : "application/json;charset=utf-8", 
				url : '<c:url value="/admin/user/save" />',
				data : formToJson($("#form_userInfo")),
				dataType : "json", 
				async : false,
				success : function(json) {
					
					if(json.error){
						swal({
						    title: "保存成功!",
						    text: "",
						    type: "success"
						});
						//成功后赋值
						$("#id").val(json.data.id);
					}else{
						swal({
						    title: "保存失败!",
						    text: "",
						    type: "success"
						});
					}
					
					
				},
				error : function(json) {
					existFlag = true;
				}
			});
		});
		function formToJson($form) {
			var data = {};
			$($form.serializeArray()).each(function(i, v) {
				//if(v.name=="id" || v.name=="enabled"){
					data[v.name] = v.value;
	
				//}
			});
			return JSON.stringify(data);
		}
function contectListData() {
		// dataTable load
	if (typeof oTable == 'undefined') {
		oTable = $('#contectList').dataTable(
				{
					 'bPaginate' : true, // 翻页功能
					'sServerMethod' : 'POST', // 提交方式
					'bServerSide' : true,
					'bProcessing' : true,
					'bFilter' : false,
					"bSort" : false,
					'sAjaxSource' : contextPath + '/admin/contect',
					"fnServerParams" : function(aoData) { // 查询条件
						aoData.push({
										"name" : "userId",
										"value" : $('[name=id]').val()
									});
					},
					aoColumns : [{
								'mData' : 'id',
								'sTitle' : '用户编号',
								'visible' : false
							},{
								'sTitle' : '用户编号',
								'mData' : 'userId'
							},{
								'sTitle' : '联系人姓名',
								'mData' : 'name'
							},{
								'sTitle' : '联系人电话',
								'mData' : 'mobile'
							},{
								'sTitle' : '默认联系人标识',
								'mData' : 'contactFlag',
								'mRender' : function(data, type, full) {
									var returnValue = "";
									if (data == "1") {
										returnValue = "默认联系人";
									}
									return returnValue;
								}
							},{
								'sTitle' : '联系人地址',
								'mData' : 'address'
							}] 

				});
	} else {
		// oTable.fnClearTable(0);
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayLength = parseInt($('[name=contectList_length] option:selected').val());
		$('.dataTables_length select').val($('[name=contectList_length] option:selected').val());
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}
		

}
		
	</script>
</body>
</html>