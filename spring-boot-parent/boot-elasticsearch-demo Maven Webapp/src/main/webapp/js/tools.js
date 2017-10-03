var dataTree ;
$(function(){
    setCsrfToken("_csrf-form");
});

function setCsrfToken(formId) {
    var csrfToken = $('#' + formId).find('input[name="csrfToken"]').val();
    $(document).on('ajaxSend', function (elem, xhr, s) {
        if (s.type.toUpperCase() == 'POST') {
        	xhr.setRequestHeader('csrfToken', csrfToken);
        }else {
            s.url += (s.url.indexOf("?") == -1) ? "?" : "&";
            s.url += "csrfToken=" + csrfToken;
        }
    });
}
$(function(){
	
	var path=document.getElementById("base").href;
	$.post(path+"/menu/getMenuTopLever",{}, function(data) {
		dataTree=convertJson(data);
		if(dataTree.code==403){
			document.location=path+"/common/login"
		}
		InitLeftMenu(dataTree);
		tabClose();
		tabCloseEven();
	});
	
	
})

function convertJson(data){
	if(typeof data === 'string'){
		data = jQuery.parseJSON(data);
	}
	return data;
}


function InitLeftMenu(dataTree) {
	var path=document.getElementById("base").href;
	$("#nav" ).accordion();
    $.each(dataTree, function(i, n) {
    	$.ajax({
    		  type: 'POST',
    		  url: path+"/menu/getMenuChildren",
    		  data: {pid:n.id},
    		  dataType: "json",
    		  success: function(data) {
    	    		var children=convertJson(data);
    	    		var menulist ='';
    	    		menulist +='<ul>';
    	            $.each(children, function(j, o) {
    	            	menulist += '<li><div><a  ref="'+o.id+'" href="javascript:void(0);" rel="'+path + o.link + '" mark="'+o.menuName+'" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuName + '</span></a></div></li> ';
    	            })
    	    		menulist += '</ul>';

    	    		$('#nav').accordion('add', {
    	                title: n.menuName,
    	                content: menulist,
    	                iconCls: 'icon ' + n.icon
    	            });
    	    	}
    		  
    		});
    	
    });

    $("body").on("click",".easyui-accordion li a",function(){
		var tabTitle = $(this).children('.nav').text();
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = ""

		addTab(tabTitle,url,icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	})
	$("body").on("hover",".easyui-accordion li a",function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	var panels = $('#nav').accordion('panels');
	//var t = panels[0].panel('options').title;
    //$('#nav').accordion('select', t);
}

function getIcon(menuid){
	var icon = 'icon ';
	$.each(dataTree, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}

function tabCloseEven()
{
	
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})

	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('ϵͳ��ʾ','���û����~~','error');
			$.messager.alert('��ʾ��','����û����~~','warning');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			$.messager.alert('��ʾ��','��ͷ����ǰ��û����~~','warning');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}


function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}


//加载系统配置文件设置列表
function loadList(pageNumber, searchForm, datagridId, pageSize) {
	if (isEmpty(searchForm)) {
		searchForm = 'searchForm';
	}
	if (isEmpty(datagridId)) {
		datagridId = 'dataList';
	}
	var pager = $('#' + datagridId).datagrid('getPager');
	var rows = pager.pagination('options').pageSize;
	if (!isEmpty(pageSize)) {
		rows = pageSize;
		
	}
	var searchP = getFormJson(searchForm);

	$(searchP).attr('pageSize', rows);
	$(searchP).attr('currentPage', pageNumber);
	/*var url = $('#' + datagridId).datagrid("options").url;
	if (isEmpty(url)) {
		url = searchUrl;
	}*/
	$('#' + datagridId).datagrid('load',searchP);
	/*$.post(url, searchP, function(response) {
		response = convertJson(response);
		refreshDatagrid(datagridId, pageNumber, pageSize);
		$('#' + datagridId).datagrid('loadData', response);
	});*/
}

/**
 * 刷新DataGrid
 */
function refreshDatagrid(dgid,pageNumber,pageSize){
	$('#'+dgid).datagrid('options').pageNumber = pageNumber;
	if(isEmpty(pageSize)){
		$('#'+dgid).datagrid('getPager').pagination({pageNumber:pageNumber});
	}else{
		$('#'+dgid).datagrid('options').pageSize=pageSize;
		$('#'+dgid).datagrid('getPager').pagination({pageNumber:pageNumber,pageSize:pageSize});
	}
	
}

/**
 * 判断是否为空
 * @param exp
 * @returns {Boolean}
 */
function isEmpty(exp){
	var bl = false;
	if (typeof exp === "undefined" )
	{
		bl = true;
	}else if(typeof exp === "string" && !exp){
		bl = true;
	}
	
	return bl;
}

/**
 * 合并数组
 * @param result
 * @param src
 * @returns
 */
function extend(result,src){
		$(src).each(function(index,row){
			 $(result).attr(row.name,row.value);
		})
	   return result;
	}


function getFormJson(formId){
	var data = $("#"+formId).serializeArray();
	var result = {};
	extend(result,data);
	return result;
}

/**
 * 提示框
 * @returns
 */
var showBox = function (title,content,m_type,id){
	$.messager.alert(title,content,m_type,function(){
		if(!isEmpty(id)){
			$("#"+id).focus();
		}
	});
};

function closeWindow(id){
	$("#"+id).window("close");
}

function delError() {
	$('span[id$="Error"]').remove();
}

/**
 * 判断是否为方法
 * @param exp
 * @returns {Boolean}
 */
function isFunction(exp) {
	var bl = false;
	if (typeof (exp) == "function") {
		bl = true;
	}

	return bl;
}

/**
 * 显示新增界面
 * @param preHandler,afHandler,insertHandler,clearHandler,title,url
 * @return
 */
function showAddwindow(jsonParam){
	jsonParam=jsonParam||{};
	$('#addForm')[0].reset(); 
	$('#addwindow').removeAttr("disabled","disabled");
	$('#addwindow input').removeAttr("readonly");
	delError();
	jsonParam.title=isEmpty(jsonParam.title)?$('#addwindow').attr('title'):jsonParam.title;
	//新增前处理
	if(typeof jsonParam.preHandler  === "function") jsonParam.preHandler();
	
	initDlg('#addwindow').dialog({title:jsonParam.title,buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
               //确定按钮点击后的具体处理函数
				if (isFunction(jsonParam.insertHandler))
				{	
					jsonParam.insertHandler(jsonParam);
				}else{
					_insertHandler(jsonParam.url);
				}
				
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				//取消前处理
				if(typeof jsonParam.clearHandler  === "function") 
					jsonParam.clearHandler();
				else
					$('#addwindow').dialog('close');
			}
		}]});
	//新增后处理
	if(isFunction(jsonParam.afHandler)) jsonParam.afHandler(jsonParam);
	$('#addwindow').dialog('open');
}

var _insertHandler = function(url) {
	$('#addForm').form.url = url || insertUrl; //表单提交路径
	submitForm("addForm", $('#addForm').form.url, function(data) {
		data = convertJson(data);
		if (data.code == 126000) {
			$('#addForm').form('clear'); // 清空form
			$('#dataList').datagrid('clearSelections');//清空选择
			$('#addwindow').dialog('close');
			showBox("提示信息", "保存成功", 'info');
			var pageNumber = $('#dataList').datagrid('getPager').data(
					"pagination").options.pageNumber;
			loadList(pageNumber);
			$('#addwindow').dialog('close');
		} else {
			showError(data);
			//showBox("提示信息",data.result,'warning');
		}
	});
}

function showError(data) {
	var bl = false;
	var errors = data.error;
	if (!isEmpty(errors)) {
		var i = 0;
		$(errors).each(
				function(index, error) {
					$('#' + error.field + 'Error').remove();
					var field = $('#' + error.field);
					if (isEmpty(field.lenght) || field.lenght == 0) {
						if (i == 0) {
							showBox("提示信息", error.msg, 'warning');
						}
						i++;
					} else
						field.after("<span id='" + error.field + 'Error'
								+ "' style='color:red'>" + error.msg
								+ "</span>");
				});
		$('#' + errors[0].field + 'Error').focus();
		bl = true;
	}
	if (!isEmpty(data.message)) {
		showBox("提示信息", data.message, 'warning');
		bl = true;
	}
	return bl;
}

/**
 * 
 * @param url 删除的url
 * @param id  删除的条件
 */
function delRowData(jsonParam) {
	jsonParam = jsonParam || {};
	jsonParam.url = isEmpty(jsonParam.url) ? deleteUrl : jsonParam.url;
	var rows = $('#dataList').datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert('提示框', '请选择要删除的数据', 'warning');
	} else {
		var ids = "";
		for ( var i = 0; i < rows.length; i++) {
			ids += $(rows[i]).attr(jsonParam.id) + ",";
		}
		var _param = convertJson('{\"' + jsonParam.id + 's\":\"' + ids + '\"}');
		Confirm('是否删' + rows.length + '条数据！', function() {
			var _param = convertJson('{\"' + jsonParam.id + 's\":\"' + ids
					+ '\"}');
			$.post(jsonParam.url, _param, function(data) {
				data = convertJson(data);
				if (data.result == 'ok') {
					$.messager.alert('提示框', '删除成功', 'info');
					$('#dataList').datagrid("reload");
					$('#dataList').datagrid("clearChecked");
				} else {
					showError(data);
				}

			});
		});
	}
}

//确认框   
function Confirm(msg, control) {
	$.messager.confirm("确认", msg, function(r) {
		if (r) {
			control();
		}
	});
}

/**
 * jsonParam 中的参数有 updateUrl,updateHandler,preHandler,afHandler,readonlyFields
 * @param jsonParam
 * @return
 */
function showUpdate(jsonParam) {
	jsonParam = jsonParam || {};
	jsonParam.updateUrl = isEmpty(jsonParam.updateUrl) ? updateUrl
			: jsonParam.updateUrl;
	jsonParam.title = isEmpty(jsonParam.title) ? $('#addwindow').attr('title')
			: jsonParam.title;
	delError();
	var rows = $('#dataList').datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert('提示框', '请选择修改数据', 'warning');
		return;
	}
	if(rows.length!=1){
		$.messager.alert('提示框', '请选择一条修改数据', 'warning');
		return;
	}
	var data = rows[0];
	$(jsonParam).attr('data', data);
	//更新前处理
	if (isFunction(jsonParam.preHandler))
		jsonParam.preHandler(jsonParam);
	else
		setFormValue("addForm", data);
	setReadonly(jsonParam.readonlyFields);

	var validateName = $('#addForm').attr('updateValidate');
	initDlg('#addwindow').dialog( {
		title : jsonParam.title,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				//若form中绑定了检查方法，则先执行检查
				if(!isEmpty(validateName)){
						var fc = eval(validateName);
						var checkResult = fc.call();
						if(!checkResult){
							return checkResult;
						}
					}
				//确定按钮点击后的具体处理函数
			if (isFunction(jsonParam.updateHandler)) {
				jsonParam.updateHandler(jsonParam);
			} else{
				doEdit(jsonParam.updateUrl);
			}

		}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#addForm")[0].reset();
				$('#addwindow').dialog('close');
			}
		} ]
	});
	//新增后处理
	if (isFunction(jsonParam.afHandler))
		jsonParam.afHandler(jsonParam);
	$('#addwindow').dialog('open');

}

function setReadonly(readonlyFields) {
	if (!isEmpty(readonlyFields)) {
		$.each(readonlyFields, function(key, value) {
			$("#addForm").find('#' + value).attr('readonly', 'readonly');
			$("#addForm").find('#' + value).css('background-color','#CCCCCC');
		});
	}

}

function setFormValue(_data) {
	$.each(_data, function(key, value) {
		$('#' + key).val(value);
		$('[name=' + key + ']').val(value);
	});
}

function setFormValue(formId, _data) {
	$.each(_data, function(key, value) {
			_setValue($('#' + formId).find("#" + key), value)
			_setValue($('#' + formId).find('[name=' + key + ']'), value)
		});
}

function _setValue(_temp, value) {
	if (!isEmpty(_temp)) {
		_temp.val(value);
		if (!isEmpty(_temp.attr('constantId'))) {
			_temp.combobox('setValue', value);
		} else if (_temp.hasClass('easyui-datebox')) {
			_temp.datebox('setValue', value);
		}
	}
}

//修改系统配置文件设置
function doEdit(url) {
	if (url)
		$('#addForm').form.url = url; //表单提交路径
	submitForm("addForm", $('#addForm').form.url, function(data) {
		//eval('data='+data); 
			data = convertJson(data);
			if (data.result == "ok") {
				$('#addDlg').dialog('close');
				showBox("提示信息", "修改成功", 'info');
				var pageNumber = $('#dataList').datagrid('getPager').data(
						"pagination").options.pageNumber;
				loadList(pageNumber);
				$('#addwindow').dialog('close');
			} else {
				//showBox("提示信息",data.result,'warning');
			showError(data);
		}
	});
}


/**
 * 表单提交
 * 
 */
function submitForm(formId, url, handler) {
	// 判断当前操作表单提交action
	var t_handler = handler || function() {
	};
	
	var csrfToken = $('#_csrf-form').find('input[name="csrfToken"]').val();
	url += (url.indexOf("?") == -1) ? "?" : "&";
    url += "csrfToken=" + csrfToken;
    var cmitUrl = url;
	$('#' + formId).form('submit', {
		url : cmitUrl,
		onSubmit : function() { // 提交前的验证				
			//valiDateCss(true);//初始化验证框
		return $(this).form('validate');
	},
	success : function(data) {
		if (typeof t_handler === "function") {
			t_handler(data);
		}
	}
	});
}


//初始化添加dlg
function initDlg(dlgId) {
	$(dlgId).dialog( {
		title : "",
		closable : true,
		closed : true,
		modal : true,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
			}
		} ]
	});

	return $(dlgId);
}