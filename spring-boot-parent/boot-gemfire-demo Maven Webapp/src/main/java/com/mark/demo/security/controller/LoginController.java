package com.mark.demo.security.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mark.demo.security.base.GenericController;
import com.mark.demo.security.constant.CommonConst;
import com.mark.demo.security.entity.JsonMessage;
import com.mark.demo.security.entity.User;
import com.mark.demo.security.session.RedisSessionManager;
import com.mark.demo.security.utils.JsonMessageUtils;


@RequestMapping("/common")
@Controller
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
    @RequestMapping("/login")
    public String loginForward(String redirect, Model model) throws UnsupportedEncodingException
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
        return "common/login.ftl";
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
