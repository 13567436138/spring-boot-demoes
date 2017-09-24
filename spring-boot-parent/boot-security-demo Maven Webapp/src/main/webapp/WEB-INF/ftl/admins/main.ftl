<#assign contextPath=request.contextPath />
<#assign form=JspTaglibs["/WEB-INF/tag/spring-form.tld"]> 

<!DOCTYPE html PUBLIC "-/W3C/DTD XHTML 1.0 Transitional/EN" "http:/www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http:/www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<title>index</title> 
<base id="base" href="${contextPath}">  
<#include "/common/common.ftl">

</head>  
<body class="easyui-layout">
	<@form.form id="_csrf-form" cssStyle="display: none"/>

	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		<h1>mark's technic world</h1>
	</div>
	<div data-options="region:'west',split:true,title:'West'" style="width:150px;padding:0px;">
		<div id="nav" class="easyui-accordion"  fit="true"  border="false">
			
		</div>
	</div>
	
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">
		<h1>all right reserved!</h1>
	</div>
	<div class="easyui-tabs" id="tabs" data-options="region:'center',title:'Center'">
	</div>
	<div id="mm" class="easyui-menu" style="width: 200px;">
		<div id="mm-tabupdate">
			刷新
		</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">
			关闭
		</div>
		<div id="mm-tabcloseall">
			全部关闭
		</div>
		<div id="mm-tabcloseother">
			除此之外全部关闭
		</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">
			当前页右侧全部关闭
		</div>
		<div id="mm-tabcloseleft">
			当前页左侧全部关闭
		</div>
	</div>
</body>
</html>