<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <jsp:include page="../commonJs.jsp"/>
    <jsp:include page="../commonCss.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/admin/vendor/webuploader/webuploader.css">
    <script src="${pageContext.request.contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.js"></script>
    <script src="${pageContext.request.contextPath}/resources/admin/vendor/bootstrap-fileinput-master/js/fileinput.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/admin/vendor/bootstrap-fileinput-master/js/fileinput_locale_zh.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/admin/vendor/webuploader/webuploader.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/pc/assets/js/bootstrap-touchspin-master/src/jquery.bootstrap-touchspin.js"></script>
</head>

<body class="blank">
<jsp:include page="../main/top.jsp"/>
<jsp:include page="../main/left.jsp"/>
<div id="wrapper">
    <div class="small-header transition animated fadeIn">
        <div class="content animate-panel">
            <div class="hpanel">
                <div class="panel-body">
                    <div class="hpanel">
                        <ul class="nav nav-tabs">
                            <li class="active"><a id="liProduct" data-toggle="tab" href="#tab-1">商品信息</a></li>
                        </ul>
                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <form:form modelAttribute="product" id="form_productInfo" name="form_productInfo"
                                           method="POST">
                                    <input type="hidden" id="parameterName" value="${_csrf.parameterName}">
                                    <input type="hidden" id="token" value="${_csrf.token}">
                                    <div class="row" style="padding:20px">
                                        <div class="row">
                                            <div class="form-group col-lg-6">
                                                <label>商品名称</label>
                                                <input id="id" name="id" type="hidden" value="${product.id}">
                                                <input name="name" class="form-control" type="text" placeholder="商品名称"
                                                       value="${product.name}">
                                            </div>
                                            <div class="form-group col-lg-6">
                                                <label>商品外部编号</label>
                                                <input name="externalId" class="form-control" type="text"
                                                       placeholder="商品外部编号" value="${product.externalId}">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-lg-6">
                                                <label>所属分类</label>
                                                <form:select path="categoryId" items="${categoryMap}"
                                                             cssClass="form-control"/>
                                            </div>
                                            <div class="form-group col-lg-6">
                                                <label>市场价格</label>
                                                <input name="price" class="form-control" type="text" placeholder="市场价格"
                                                       value="${product.price/100}">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-lg-6">
                                                <label>店内价格</label>
                                                <input name="shopPrice" class="form-control" type="text"
                                                       placeholder="店内价格" value="${product.shopPrice/100}">
                                            </div>
                                            <div class="form-group col-lg-6">
                                                <label>商品库存</label>
                                                <input name="quantity" class="form-control" type="text"
                                                       placeholder="商品库存" value="${product.quantity}">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-lg-6">
                                                <label>是否记录库存</label>
                                                <div>
                                                    <label>
                                                        <input type="radio" value="0" class="i-checks"
                                                               name="inventoryFlag"
                                                               <c:if test="${product.inventoryFlag==0}">checked</c:if>>&nbsp;&nbsp;是</label>&nbsp;&nbsp;
                                                    <label>
                                                        <input type="radio" value="1" class="i-checks"
                                                               name="inventoryFlag"
                                                               <c:if test="${product.inventoryFlag==1}">checked</c:if>>&nbsp;&nbsp;否</label>
                                                </div>
                                            </div>
                                            <div class="form-group col-lg-6">
                                                <label>是否热销</label>
                                                <div>
                                                    <label>
                                                        <input type="radio" value="0" class="i-checks" name="hot"
                                                               <c:if test="${product.hot==0}">checked</c:if>>&nbsp;&nbsp;非热门商品</label>&nbsp;&nbsp;
                                                    <label>
                                                        <input type="radio" value="1" class="i-checks" name="hot"
                                                               <c:if test="${product.hot==1}">checked</c:if>>&nbsp;&nbsp;热门商品</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-lg-6">
                                                <label>状态</label>
                                                <select name="productStatus" class="form-control">
                                                    <option value="0"
                                                            <c:if test="${product.productStatus==0}">selected</c:if>>上架
                                                    </option>
                                                    <option value="1"
                                                            <c:if test="${product.productStatus==1}">selected</c:if>>下架
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="form-group col-lg-6">
                                                <label>商品概要说明</label>
                                                <input name="generalExplain" class="form-control" type="text"
                                                       placeholder="商品概要说明" value="${product.generalExplain}">
                                            </div>
                                        </div>
                                        <div class="form-group col-lg-6">
                                            <label>上传图片</label>
                                            <!--dom结构部分-->
                                            <div id="uploader-demo">
                                                <div id="fileList" class="uploader-list"></div>
                                                <div id="filePicker">选择图片</div>
                                                <div style='padding-top:10px'>
                                                    上传图片的最佳尺寸：300像素*300像素，其他尺寸会影响页面效果，格式png，jpeg，jpg，gif。大小不超过1M。最多允许上传4张
                                                </div>
                                                <c:if test="${product.id!=null}">
                                                    <div id="imageFile">
                                                        <div id="DB_WU_FILE_0" style="float:left"
                                                             class="file-item thumbnail">
                                                            <input type="radio" name="defaultImg" value="DB_WU_FILE_0"
                                                                   checked/>设为默认
                                                            <img src="${pageContext.request.contextPath}${product.defaultImg}"
                                                                 style="width: 150px;height: 150px"/>
                                                            <div style="padding-top: 2px">
                                                                <button class="btn btn-default btn-sm" type="button"
                                                                        onclick="deleteImage(this)">删除
                                                                </button>
                                                            </div>
                                                            <input type="hidden" name="DB_WU_FILE_0"
                                                                   value="${product.defaultImg}"/>
                                                        </div>
                                                        <c:forEach items="${product.productImageList}" var="imageInfo"
                                                                   varStatus="status">
                                                            <div id="DB_WU_FILE_${status.index+1}" style="float:left"
                                                                 class="file-item thumbnail">
                                                                <input type="radio" name="defaultImg"
                                                                       value="DB_WU_FILE_${status.index+1}"/>设为默认
                                                                <img src="${pageContext.request.contextPath}${imageInfo.url}"
                                                                     style="width: 150px;height: 150px"/>
                                                                <div style="padding-top: 2px">
                                                                    <button class="btn btn-default btn-sm" type="button"
                                                                            onclick="deleteImage(this)">删除
                                                                    </button>
                                                                </div>
                                                                <input type="hidden" name="DB_WU_FILE_${status.index+1}"
                                                                       value="${imageInfo.url}"/>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:if>
                                                <c:if test="${product.id==null}">
                                                    <div id="imageFile">
                                                        <img src=""/>
                                                    </div>
                                                </c:if>
                                                <div id="hiddenImage"></div>
                                            </div>
                                        </div>
                                        <div class="form-group col-lg-12">
                                            <input type="hidden" id="explain" name="explain" value='${product.explain}'>
                                            <label class="control-label">商品说明</label>
                                            <div style="width: auto;height: auto;">
                                                <script id="container" type="text/plain"></script>
                                                <!-- 配置文件 -->
                                                <script src="${pageContext.request.contextPath}/resources/admin/js/ueditor/ueditor.config.js"
                                                        type="text/javascript"></script>
                                                <!--　编辑器源码文件 -->
                                                <script src="${pageContext.request.contextPath}/resources/admin/js/ueditor/ueditor.all.js"
                                                        type="text/javascript"></script>
                                                <!--　实例化编辑器 -->
                                                <script type="text/javascript">
                                                    var ue = UE.getEditor('container', {
                                                        autoWidth: true
                                                    });
                                                    ue.ready(function () {
                                                        ue.setContent($("#explain").val());
                                                    });
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row" style="padding:20px">
                                        <div class="text-left m-t-xs">
                                            <button id="saveProductInfo" class="btn btn-success"
                                                    style="margin-right: 5px;margin-top: 5px" type="button">
                                                <strong>保存</strong>
                                            </button>
                                            <button id="back" class="btn btn-success"
                                                    style="margin-right: 5px;margin-top: 5px" type="button">
                                                <strong>返回</strong>
                                            </button>
                                        </div>
                                    </div>
                                </form:form>
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

    var uploader;
    $(function () {
        $('.date').datepicker();
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: true,

            // swf文件路径
            swf: contextPath + '/resources/vendor/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: contextPath + '/admin/newUploadfile?${_csrf.parameterName}=${_csrf.token}',

            fileNumLimit: 300,
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
            var hiddenImage = '<input type="hidden" name="' + object.file.id + '" value="' + ret.fileName + '" />'
            $("#hiddenImage").append(hiddenImage);
        });
        // 当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            var $li = $(
                '<div id="' + file.id + '" style="float:left" class="file-item thumbnail">' +
                '<input type="radio" name="defaultImg" value="' + file.id + '"/>设为默认' + '<img>' + '<div style="padding-top: 2px"><button class="btn btn-default btn-sm" type="button" onclick="deleteImage(this)">删除</button></div>' +
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
        var validate = $("#form_productInfo").validate({
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
                    maxlength: 100
                },
                externalId: {
                    maxlength: 50
                },
                shopPrice: {
                    required: true,
                    number: true,
                    decimals: 2,
                    max: 99999.99,
                    min: 0
                },
                price: {
                    required: true,
                    number: true,
                    decimals: 2,
                    max: 99999.99,
                    min: 0
                },
                quantity: {
                    required: true,
                    digits: true,
                    maxlength: 4
                },
                generalExplain: {
                    maxlength: 255
                }

            },
            messages: {
                name: {
                    required: "请输入商品名!",
                    maxlength: "商品名不能超过100位"
                },
                externalId: {
                    maxlength: "商品外部编号不能超过50位",
                },
                shopPrice: {
                    required: "请输入店内价格!",
                    number: "请输入合法数字！",
                    max: "店内价格最大不能超过99999.99元！",
                    min: "店内价格不能小于0元！",
                    decimals: "小数位数不能大于两位！"
                },
                price: {
                    required: "请输入市场价格!",
                    number: "请输入合法数字！",
                    max: "市场价格最大不能超过99999.99元！",
                    min: "市场价格不能小于0元！",
                    decimals: "小数位数不能大于两位！"
                },
                quantity: {
                    required: "请输入商品库存!",
                    digits: "请输入正整数！",
                    maxlength: "商品库存最大长度不能超过4位！"
                },
                generalExplain: {
                    maxlength: "商品概要说明不能超过255位！"
                }
            }
        });
    });
    //保存/更新商品信息
    $("#saveProductInfo").bind("click", function () {
        if (!$("#form_productInfo").valid()) {
            return;
        }
        if (checkImage() == false) {
            return;
        }
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            url: "${pageContext.request.contextPath}/admin/product/save",
            data: formToJson($("#form_productInfo")),
            dataType: "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
            async: true,
            success: function (json) {
                if (json.error == '0') {
                    $("#id").val(json.data.id);
                    swal({
                        title: "保存成功!",
                        text: "",
                        type: "success"
                    });
                } else {
                    swal("保存失败！", "请与系统管理员联系！", "error");
                }
            },
            error: function (json) {
                alert(json);
            }
        })
    });
    $('.priceTouchSpin').TouchSpin({
        min: 1,
        max: 99999,
        step: 0.01,
        decimals: 2,
        boostat: 5,
        maxboostedstep: 10
    });
    $('.quantityTouchSpin').TouchSpin({
        min: 1,
        max: 99999,
        step: 1,
        boostat: 5,
        maxboostedstep: 10
    });
    /** 返回  */
    $("#back").bind("click", function () {
        window.location.href = contextPath + '/admin/productList';
    });
    $('#deleteSkuBtn').click(function () {
        $('#skuGroup').empty();
    });
    var dataArray = [];
    <c:if test="${productOptionInfo != null}">
    var optionChecked = ${productOptionInfo};
    $(optionChecked).each(function () {
        dataArray[dataArray.length] = this.optionId;
        $('input:checkbox[value="' + this.optionId + '"]').attr('checked', 'checked');
    });
    </c:if>
    $('#createSkuBtn').click(function () {
        if ($("#id").val() == '') {
            swal({
                title: "提示",
                text: "请登录商品信息！"
            });
        } else {
            if ($("[name=optionId]:checked").length > 2 || $("[name=optionId]:checked").length == 0) {
                swal({
                    title: "提示",
                    text: "商品选项只能有1或2个！"
                });
            } else if ($('#skuGroup').find('div.panel-body').length > 0) {
                swal({
                    title: "提示",
                    text: "生成SKU必须先清除SKU！"
                });
            } else {
                dataArray = [];
                $("[name=optionId]:checked").each(function (i, v) {
                    dataArray.push(Number($(v).val()));
                });
                $.ajax({
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    url: "${pageContext.request.contextPath}/admin/product/option/value",
                    data: JSON.stringify(dataArray),
                    dataType: "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
                    success: function (json) {
                        if (json.length > 0) {
                            addMoreRow(json);
                            $('.priceTouchSpin').TouchSpin({
                                min: 1,
                                max: 99999,
                                step: 0.01,
                                decimals: 2,
                                boostat: 5,
                                maxboostedstep: 10
                            });
                            $('.quantityTouchSpin').TouchSpin({
                                min: 1,
                                max: 99999,
                                step: 1,
                                boostat: 5,
                                maxboostedstep: 10
                            });
                        }
                    },
                    error: function (json) {
                        alert(json);
                    }
                });
            }
        }

    });

    function delMoreRow(id) {
        $('div[data-key="' + id + '"]').remove();
    }

    function addMoreRow(json) {
        for (var item in json) {
            $('#skuGroup').append(
                '<div class="panel-body" data-key="' + json[item].key + '">' +
                '	<h4>' + json[item].titleName + '</h4>' +
                '	<div class="form-group">' +
                '		<label class="col-sm-2 control-label">价格</label>' +
                '		<div class="col-sm-10">' +
                '			<input type="text" name="price" class="form-control priceTouchSpin" value="0">' +
                '			<input type="hidden" name="key" value="' + json[item].key + '">' +
                '		</div>' +
                '	</div>' +
                '	<div class="form-group">' +
                '		<label class="col-sm-2 control-label">库存</label>' +
                '		<div class="col-sm-10">' +
                '			<input type="text" name="quantity" class="form-control quantityTouchSpin" value="0">' +
                '		</div>' +
                '	</div>' +
                '	<div class="form-group">' +
                '		<div class="col-sm-8 col-sm-offset-2">' +
                '			<button class="btn btn-default" onclick="delMoreRow(\'' + json[item].key + '\')">删除</button>' +
                '		</div>' +
                '	</div>' +
                '</div>'
            );
        }
    }

    function saveSku(dom) {
        $(dom).attr("disabled", "disabled");
        var productId = $('#id').val();
        if (productId == null || productId == "") {
            swal({
                title: "提示",
                text: "请登录商品信息！"
            });
            $(dom).removeAttr("disabled");
            return;
        }
        productId = Number(productId);
        var saveData = {'productOptionInfoList': [], 'skuList': [], 'id': productId};
        var skuList = saveData.skuList;
        var quantitySum = 0;
        $('#skuGroup').find('div.panel-body').each(function () {
            var price = Number($(this).find('input[name="price"]').val());
            var quantity = Number($(this).find('input[name="quantity"]').val());
            quantitySum += quantity;
            if (price == 0 && quantity == 0) {
                $(this).remove();
                return;
            }
            var sku = {};
            sku['optionValueKeyGroup'] = $(this).find('input[name="key"]').val();
            sku['price'] = price * 100;
            sku['quantity'] = quantity;
            sku['productId'] = productId;
            sku['skuJson'] = $(this).find('h4').html().trim();
            skuList[skuList.length] = sku;
        });
        if (quantitySum > Number($('#form_productInfo').find('input[name="quantity"]').val())) {
            swal({
                title: "提示",
                text: "库存总和不能大于商品库存!"
            });
            $(dom).removeAttr("disabled");
            return;
        }
        if (skuList.length != 0) {
            var optionList = saveData.productOptionInfoList;
            for (var i in dataArray)
                optionList[optionList.length] = {'optionId': dataArray[i], 'productId': productId};
        } else {
            $('input[name="optionId"]').removeAttr('checked');
        }
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            url: "${pageContext.request.contextPath}/admin/product/save/sku",
            data: JSON.stringify(saveData),
// 			data: saveData,
            dataType: "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
            success: function (json) {
                if (json.success) {
                    swal({
                        title: "提示",
                        text: json.message
                    });
                }
                $(dom).removeAttr("disabled");
            },
            error: function (json) {
                alert('error' + json.code);
            }
        });
    }

    function formToJson($form) {
        var data = {};
        var imageArray = [];
        $($form.serializeArray()).each(function (i, v) {
            if (v.name == "shopPrice" || v.name == "price") {
                data[v.name] = Number(v.value) * Math.pow(10, 2)
            } else {
                data[v.name] = v.value
            }

        });
        var imageName = $("[name=defaultImg]:checked").val();
        data["defaultImg"] = $('[name="' + imageName + '"]').val();
        data["explain"] = ue.getContent();


        //添加图片信息
        $("[name=defaultImg]").each(function () {
            if ($(this).is(":checked") == false) {
                var blankData = {};
                blankData["url"] = $('[name="' + $(this).val() + '"]').val();
                imageArray.push(blankData);
            }
        });
        data["productImageList"] = imageArray;
        return JSON.stringify(data);
    }

    function checkImage() {
        if (typeof ($("[name=defaultImg]")) == "undefined" || $("[name=defaultImg]").length == 0) {
            swal({
                title: "提示",
                text: "请上传商品图片！"
            });
            return false;
        } else {
            if ($("[name=defaultImg]").length > 4) {
                swal({
                    title: "提示",
                    text: "最多只允许上传4张图片！"
                });
                return false;
            } else {
                var checkFlag = false;
                $("[name=defaultImg]").each(function () {
                    if ($(this).is(":checked") == true) {
                        checkFlag = true;
                    }
                });
                if (checkFlag == false) {
                    swal({
                        title: "提示",
                        text: "请设置默认图片！"
                    });
                    return false;
                }
            }

        }
        return true;

    }

    function deleteImage(item) {
        for (var i = 0; i < uploader.getFiles().length; i++) {
            // 将图片从上传序列移除
            if ($(item).parent().parent().find("input").val() == uploader.getFiles()[i].id) {
                uploader.removeFile(uploader.getFiles()[i]);
            }
        }
        $(item).parent().parent().remove();
    }

    //验证小数点后的位数
    jQuery.validator.addMethod("decimals", function (value, element, d) {
        var a = value.indexOf(".") + 1;
        if (a == 0) {
            a = value.length;
        }
        var b = value.length;
        var c = b - a;
        return this.optional(element) || c <= d;
    });
</script>
</body>

</html>
