<#assign base=request.contextPath />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http:/www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base id="base" href="${base}">  
<title>index</title> 

<link rel="stylesheet" type="text/css" href="${base}/js/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/js/jquery-easyui-1.5/themes/icon.css">
<script type="text/javascript" src="${base}/js/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript" src="${base}/js/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/js/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>  
</head>  
<body > 
	<div id="loginWin" class="easyui-window" title="登录" style="width:500px;height:228px;padding:5px;"  
   			minimizable="false" maximizable="false" resizable="false" collapsible="false">  
    	<div class="easyui-layout" fit="true">  
            <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">  
		        <form id="loginForm" action="${base}/common/login/submitlogin" method="post">  
		            <div style="padding:5px 0;">  
		                <label for="login">帐号:</label>  
		                <input type="text" name="userName" style="width:260px;"></input>  
		            </div>  
		            <div style="padding:5px 0;">  
		                <label for="password">密码:</label>  
		                <input type="password" name="password" style="width:260px;"></input>  
		            </div> 
		            <div style="padding:5px 0;">
		            	<label for="captcha">验证码:</label>
                        <input type="text" name="captcha" datatype="*" />
                        <img  class="captcha_img" src="${base}/captcha" alt=""/>
		            </div> 
		             <div style="padding:5px 0;text-align: center;color: red;" id="showMsg"></div>  
		        </form>  
            </div>  
            <div region="south" border="false" style="text-align:right;padding:5px 0;">  
                <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>  
                <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>  
            </div>  
	    </div>  
	</div>  
</body>  
<script type="text/javascript">       
	var base = document.getElementById("base").href;
	document.onkeydown = function(e){  
	    var event = e || window.event;    
	    var code = event.keyCode || event.which || event.charCode;  
	    if (code == 13) {  
	        login();  
	    }  
	}  
	$(function(){  
	    $("input[name='login']").focus(); 
	});  
	//点击验证码图片
    $(document).on("click",".captcha_img", function () {
        var url = base+"/captcha?t=" + Math.random();
        $(this).attr("src", url);
    });
	function cleardata(){  
	    $('#loginForm').form('clear');  
	}  
	function login(){  
	     if($("input[name='login']").val()=="" || $("input[name='password']").val()==""){  
	         $("#showMsg").html("用户名或密码为空，请输入");  
	         $("input[name='login']").focus();  
	    }else{  
	           $("#loginForm").submit();        
	    }   
	}  
</script>  
</html>  