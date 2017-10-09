package com.mark.demo.security.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mark.demo.security.base.GenericController;
import com.mark.demo.security.session.RedisSessionManager;


@Path("/common")
public class LoginController extends GenericController
{
    private static Logger       logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private RedisSessionManager redisSessionManager;
    
    /**
     * 登陆页面导航
     * @param redirect
     * @param model
     * @return {@link String}
     * @throws UnsupportedEncodingException
     */
    @Path("/login")
    @Produces(MediaType.TEXT_HTML) 
    public Viewable loginForward(@Context HttpServletRequest request,@QueryParam("redirect") String redirect,@QueryParam("error")String error) throws UnsupportedEncodingException
    {
        /*if (StringUtils.isNotBlank(redirect))
        {// 判断是否有跳转的地址
   
            User onLine = OnLineUserUtils.getPrincipal();
            if (null != onLine)
            {// 如果当前是已登陆用户，就直接跳到用户后台
                return "redirect:/admins/index.ftl";
            }
          
            model.addAttribute("redirect", URLDecoder.decode(redirect, CharsetConst.CHARSET_UT));
        }*/
    	if(StringUtils.isNotEmpty(error)&&error.equals("code")){
    		request.setAttribute("msg", "验证码错误");
    		request.setAttribute("error", true);
    	}else{
    		request.setAttribute("msg","");
    	}
        return new Viewable("common/login");
    }
    
    
    
    /**
     * 登陆请求处理
     *
     * @param userInfo
     * @param request
     * @param captcha
     * @return
     */
    /*@RequestMapping("/login/submitlogin")
    @ResponseBody
    public JsonMessage login(User userInfo, HttpServletRequest request, String captcha)
    {
        JsonMessage msg = JsonMessageUtils.getJsonMessage(CommonConst.SUCCESS);
        msg.setObject("admins/indexes/index");
        return msg;
    }
    */
    
    /**
     * 用户退出
     *
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        // 清理自定义会话
        RedisSessionManager.clear(request, response);
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) currentUser.logout();
        return "redirect:/common/login";
    }*/
    
    /**
     * 判断用户是否登录，
     *
     * @return
     */
    /*@RequestMapping(value = "/islogin", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage isLogin()
    {
        JsonMessage jsonMessage = null;
        User userInfo = OnLineUserUtils.getPrincipal();
        if (userInfo != null)
        {
            jsonMessage = new JsonMessage(CommonConst.SUCCESS.getCode(),CommonConst.SUCCESS.getMessage());
        }
        return jsonMessage;
    }*/
}
