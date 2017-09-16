package com.mark.demo.shiro.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mark.demo.shiro.base.GenericController;
import com.mark.demo.shiro.constant.CharsetConst;
import com.mark.demo.shiro.constant.CommonConst;
import com.mark.demo.shiro.entity.JsonMessage;
import com.mark.demo.shiro.entity.User;
import com.mark.demo.shiro.session.RedisSessionManager;
import com.mark.demo.shiro.utils.IPUtil;
import com.mark.demo.shiro.utils.JsonMessageUtils;
import com.mark.demo.shiro.utils.OnLineUserUtils;
import com.mark.demo.shiro.utils.StringUtils;


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
        if (StringUtils.isNotBlank(redirect))
        {// 判断是否有跳转的地址
   
            User onLine = OnLineUserUtils.getPrincipal();
            if (null != onLine)
            {// 如果当前是已登陆用户，就直接跳到用户后台
                return "redirect:/admins/index.ftl";
            }
          
            model.addAttribute("redirect", URLDecoder.decode(redirect, CharsetConst.CHARSET_UT));
        }
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
    @RequestMapping("/login/submitlogin")
    @ResponseBody
    public JsonMessage login(User userInfo, HttpServletRequest request, String captcha)
    {
        JsonMessage msg;
        User onLine = OnLineUserUtils.getPrincipal();
        if (null != onLine)
        {// 如果当前是已登陆用户，就直接跳到用户后台
            msg = JsonMessageUtils.getJsonMessage(CommonConst.SUCCESS);
            msg.setObject("admins/indexes/index");
            return msg;
        }
        if (userInfo.getUserName() == null || userInfo.getUserName().trim().equals(""))
        {
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("用户名不能为空");
            return msg;
        }
        if (userInfo.getPassword()== null || userInfo.getPassword().trim().equals(""))
        {
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("密码不能为空");
            return msg;
        }
        if (null != captcha)
        {
            // 判断验证码
            String sessionCaptcha = redisSessionManager.getString(request, RedisSessionManager.SessionKey.CAPTCHA);
            boolean valid = StringUtils.equalsIgnoreCase(captcha, sessionCaptcha);
            if (!valid)
            {
                msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
                msg.setMessage("验证码错误");
                return msg;
            }
        }
        AuthenticationToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword().toCharArray(), userInfo.isRememberMe(),
                IPUtil.getOriginalIpAddr(request));
        Subject currentUser = SecurityUtils.getSubject();
        try
        {
            currentUser.login(token);
        }
        catch (UnknownAccountException uae)
        {
            logger.error("UnknownAccountException", uae.getLocalizedMessage());
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("用户名不存在或密码错误");
            return msg;
        }
        catch (IncorrectCredentialsException ice)
        {
            logger.error("IncorrectCredentialsException", ice.getLocalizedMessage());
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("密码错误");
            return msg;
        }
        catch (LockedAccountException lae)
        {
            logger.error("LockedAccountException", lae.getLocalizedMessage());
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("账户被锁");
            return msg;
        }
        catch (AuthenticationException ae)
        {
            logger.error("AuthenticationException", ae.getLocalizedMessage());
            msg = JsonMessageUtils.getJsonMessage(CommonConst.FAIL);
            msg.setMessage("未知错误");
            return msg;
        }
        msg = JsonMessageUtils.getJsonMessage(CommonConst.SUCCESS);
        msg.setObject("admins/indexes/index");
        return msg;
    }
    
    
    /**
     * 用户退出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        // 清理自定义会话
        RedisSessionManager.clear(request, response);
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) currentUser.logout();
        return "redirect:/common/login";
    }
    
    /**
     * 判断用户是否登录，
     *
     * @return
     */
    @RequestMapping(value = "/islogin", method = RequestMethod.POST)
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
    }
}
