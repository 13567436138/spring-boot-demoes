<#assign contextPath=request.contextPath />
<#assign form=JspTaglibs["/WEB-INF/tag/spring-form.tld"]> 

<!DOCTYPE html PUBLIC "-/W3C/DTD XHTML 1.0 Transitional/EN" "http:/www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html>
  <head>
    <title>menu</title>
   <base id="base" href="${contextPath}">  
   <#include "/common/common.ftl">
   <script type="text/javascript"  charset="UTF-8">
   var searchUrl = "${contextPath}/menu/list/data";
   var updateUrl = "${contextPath}/departments/update.do";
   var insertUrl = "${contextPath}/departments/insert.do";
   var deleteUrl = "${contextPath}/departments/delete.do";
	$(function() {
	    $('#dataList').datagrid({  
	        title:'列表',  
	        iconCls:'icon-edit',//图标  
	        //width: 700,  
	        height: 'auto',  
	        nowrap: false,  
	        striped: true,  
	        border: true,  
	        collapsible:false,//是否可折叠的  
	        fit: true,//自动大小  
	        url:'#',  
	        remoteSort:false,   
	        singleSelect:true,//是否单选  
	        pagination:true,//分页控件  
	        rownumbers:true,//行号  
	        url:searchUrl, 
	        toolbar:'#tb',
	        columns:[[   
                   		{field:'id',title:'菜单编号',width:100,align:'center'},
                   		{field:'menuName',title:'菜单名称',width:100,align:'center'},
                   		{field:'parent',title:'父菜单',width:100,align:'center',formatter:function(cellvalue, options, rowObject){
                   			return cellvalue.menuName;
                   		}},
                   		{field:'link',title:'连接',width:200,align:'center'},
                   		{field:'menuDesc',title:'描述',width:300,align:'center'},
                   		{field:'order',title:'序号',width:100,align:'center'},
                   		{field:'icon',title:'图标',width:100,align:'center'}
	        ]],
	        
	         onBeforeLoad: function (params) {
			      params.pageSize = params.rows
			      params.currentPage = params.page
			      delete params.rows
			      delete params.page
			 }
	        
	    });  
	
	    //设置分页控件  
	    var p = $('#dataList').datagrid('getPager');  
	    $(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [10,20,30],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    })
	});
	</script>
  </head>
  
  <body class="easyui-layout" >
  <@form.form id="_csrf-form" cssStyle="display: none"/>
	<div  region="center" >
		<div id='dataList'>
			<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAddwindow({title:'新增'})">新增</a>|
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdate({title:'修改',readonlyFields:['departmentcode']});">修改</a>|
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRowData({id:'departmentcode'});">删除</a>
		</div>
		<div>
			<form  id='searchForm' action="" method="post">
				菜单名称:
				<input type="text" id="menuName" name="menuName"/>
				父菜单名称:
				<input type="text" id="pmenuName" name="parent.menuName"/>
				<input type="button" onclick="loadList(1);" value="查询"/>
			</form>
		</div>
	</div>
		</div>
	</div>
	
	<div style="visibility:hidden" >
		<div id="addwindow"  title="添加" style="width:600px;height:350px;padding:10px">
			<form id='addForm' action="" method="post">
				<table>
						<tr>
							<td>菜单名称:</td>
							<td><input type="text" id="departmentcode" name="departmentcode" style="width:120px"/></td>
							<td>菜单描述:</td>
							<td><input type="text" id="departmentname" name="departmentname" style="width:120px"/></td>
						</tr>
						<tr>
							<td>菜单连接:</td>
							<td><input type="text" id="deptdescription" name="deptdescription" style="width:120px"/></td>
							<td>菜单序号:</td>
							<td><input type="text" id="remarks" name="remarks" style="width:120px"/></td>
						</tr>
						<tr>
							<td>父菜单:</td>
							<td><input type="text" id="operatorbankcode" name="operatorbankcode" style="width:120px"/></td>
							<td>菜单序号:</td>
							<td><input type="text" id="operatorcode" name="operatorcode" style="width:120px"/></td>
						</tr>
						
				</table>
			</form>
		</div>
	</div>
	
  </body>
</html>
