<#assign contextPath=request.contextPath />
<#assign form=JspTaglibs["/WEB-INF/tag/spring-form.tld"]>

<!DOCTYPE html PUBLIC "-/W3C/DTD XHTML 1.0 Transitional/EN" "http:/www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>menu</title>
    <base id="base" href="${contextPath}">
    <#include "/common/common.ftl">
    <script type="text/javascript"  charset="UTF-8">
        var searchUrl = "${contextPath}/admins/search/solr/list/data";
        var updateUrl = "${contextPath}/admins/search/solr/update";
        var insertUrl = "${contextPath}/admins/search/solr/save";
        var deleteUrl = "${contextPath}/admins/search/solr/delete";
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
                    {field:'id',title:'编号',width:100,align:'center'},
                    {field:'title',title:'标题',width:200,align:'center'},
                    {field:'content',title:'内容',width:500,align:'center'},
                    {field:'date',title:'创建日期',width:100,align:'center'}
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
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAddwindow({title:'新增'})">新增</a>|
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdate({title:'修改',readonlyFields:['departmentcode']});">修改</a>|
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRowData({id:'departmentcode'});">删除</a>
            </div>
            <div>
                <form  id='searchForm' action="" method="post">
                    关键字:
                    <input type="text" id="key" name="key"/>
                    日期:
                    <input type="text" id="date_start" name="date_start"/>-
                    <input type="text" id="date_end" name="date_end"/>
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
                    <td>标题:</td>
                    <td><input type="text" id="title" name="title" style="width:240px"/></td>
                <tr>
                    <td>内容:</td>
                    <td><textarea rows="10" cols="50" name="content"></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
