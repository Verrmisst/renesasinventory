<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

 
	<title>生成盘点文件</title>
 </head>
  
  
  
  <body>
  <div>    
  <h3 align="center">操作列表</h3> 
  <table border="1" width="20%" align="center"
  cellspacing="0px">
  	 <tr>
  	 	<td>科室</td>
		<td>操作</td>
  	 </tr>
	 
	 <c:forEach items="${rlist}" var="r">
	 	
		<tr>
			<td>${r.department}</td>
			<td><a href="export.action?flag=${r.flag}">下载</a></td>			
		</tr>
		
	 </c:forEach>
	
  </table>
  </div> 
   <s:debug />
  <br/>
  <br/> 
        
  </body>
</html>
