<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

	<head>
		<title>瑞萨上传</title>
		<link rel="stylesheet" type="text/css" href="css/upload.css" />
	</head>

	<body>
		<div class="head">
			<ul class="head_ul1">
				<li><img src="img/logo.png" /></li>
				<li>
					<p>瑞萨半导体(北京)有限公司</p>
				</li>
			</ul>
		</div>

		<div class="main" id="main1">
			<div class="main_content">
				<h2 style="text-align: center;color: white;margin-top: 30px;">半年盘点</h2>
				<form action="upload.action" method="post" enctype="multipart/form-data">
					<p style="color: white;font-size: 18px;margin-top: 30px;margin-left: 160px;">
						选择文件：<input type="file" name="upload" id="upload"style="color: white;font-size: 16px"/>
					</p>
					<p style="text-align: center;"><span id="uploadSpan" style="color:red;font-size:13px;"></span></p>
					<p style="color: white;font-size: 18px;margin-top: 30px;margin-left: 160px;">
						业务选择：<select name="bussiness" id="bussiness" style="width:160px;height:30px;font-size: 18px;">
							<option value="">  </option>
							<option value="1">半年盘点</option>
						</select> <span id="bussSpan" style="color:red"></span>
					</p>
					<p style="margin-top: 30px;text-align:center;margin-right:81px;">
						<input type="submit" value="上传" style="font-size: 18px;width:80px;"/>
					</p>
				</form>
			</div>
		</div>

		<div class="foot">
			<p>© 2010-2017 Renesas Electronics Corporation. All rights reserved.</p>
			<p>沪ICP备09059608号</p>
		</div>
	</body>
	<script src="js/jquery-1.4.3.js" type="text/javascript" charset="utf-8"></script>
	<script>
		var fileRequired = false; //文件必选性
		var bussRequired = false; //业务必选性
		$(function() {
			//控制表单的提交
			$("form").submit(function() {

				var fileValue = $("#upload").val();

				var str = fileValue.substr(fileValue.length - 3);

				if(str != "xls") {
					//$("#uploadSpan").html("请选择excel2003文件");
					alert("请选择excel2003文件");
				} else if(str == "xls") {
					$("#uploadSpan").empty();

				}
				
				

				var selectValue = $("#bussiness option:selected").val();

				//alert(selectValue+" "+fileValue);

				//判断是否选择了值
				if(fileValue.length != 0 && selectValue != "") {
					fileRequired = true;
					bussRequired = true;
				}

				//为false时弹出对话框提示
				if(fileRequired && bussRequired) {

				} else {
					alert("请选择上传文件以及业务！");
				}

				return fileRequired && bussRequired;
			});
			
			
			
		});
	</script>
</html>