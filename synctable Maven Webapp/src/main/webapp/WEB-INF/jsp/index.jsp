<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
 	<link href="<c:url value="/resources/easyui.css"/>" rel="stylesheet">
  	<link href="<c:url value="/resources/icon.css"/>" rel="stylesheet">
  	<link href="<c:url value="/resources/demo.css"/>" rel="stylesheet">
  	
  	<script src="<c:url value="/resources/jquery.min.js"/>"></script>
  	<script src="<c:url value="/resources/jquery.easyui.min.js"/>"></script>
	<meta http-equiv="refresh" content="30">
	<base href="<%=basePath%>">
	<title>同步数据库</title>
<script type="text/javascript">  
    
    function start() {
    	var form = document.forms[0];  
        form.action = "${pageContext.request.contextPath}/synchronized/start";  
        form.method = "post";  
        form.submit();
	}
    
    function validateForm() {
    	var period = document.forms["form"]["period"].value;
    	if(period == null||period == ""){
    		alert("账号不能为空");
    		return false;
    	}
    	return true;
    }
    

    
	function stop() {
		var form = document.forms[0];  
        form.action = "${pageContext.request.contextPath}/synchronized/stop";  
        form.method = "post";  
        form.submit();
	}
	
	
 
 
</script>


</head>
<body >
	<div align="center">
		<div class="easyui-panel" title="操作" style="width:40%;">
			<div style="padding:20px 20px 20px 20px">
				<form id="form" class="easyui-form" onSubmit="return validateForm()">
					<table cellpadding="5" align="center">
						<tr>
							<td>同步间隔时间：</td>
							<td><input id="period" name="period"
								class="easyui-numberbox" validType="text"
								data-options="required:true" missingMessage="不能为空"
								invalidMessage="无效字符" tipPosition="right"></input></td>
						</tr>

					</table>


					<div style="text-align:center;padding:5px">
						<input type="submit" name="开始" value="开始" onclick="start()">
						<input type="button" name="暂停" value="暂停" onclick="stop()">
					</div>

				</form>

			</div>
		</div>
	</div>

</body>
</html>
