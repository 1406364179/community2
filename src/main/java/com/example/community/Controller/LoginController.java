package com.example.community.Controller;

import com.example.community.entity.User;
import com.example.community.service.UserService;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.example.community.until.CommunityConstant.*;

@Controller
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;


    @RequestMapping(path = "/register",method =    RequestMethod.GET)
    public String getRegisterPage(){
        return "/TestRegister";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map=userService.register(user);
        if(map==null||map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送一封邮件，请尽快激活！");
            model.addAttribute("target","/index");
            return "redirect:/test";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "redirect:/test";
        }
    }

    //http://localhost:8081/activation/userid/code 激活帐号链接构成
    @RequestMapping(path ="/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){
        int result=userService.activation(userId,code);
        if(result==ACTIVATION_SUCCESS){
            System.out.println("激活成功，你的帐号可以正常使用了");
            model.addAttribute("msg","激活成功，你的帐号可以正常使用了！");
            model.addAttribute("target","redirect:/test");
        }else if(result==ACTIVATION_REPEAT){
            System.out.println("无效操作，请勿重复激活");
            model.addAttribute("msg","无效操作，请勿重复激活！");
            model.addAttribute("target","redirect:/test");
        }else {
            System.out.println("激活失败，激活码不正确");
            model.addAttribute("msg","激活失败，激活码不正确！");
            model.addAttribute("target","redirect:/test");
        }
        return "redirect:/test";
    }

    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text=kaptchaProducer.createText();
        BufferedImage image=kaptchaProducer.createImage(text);

        //讲验证码存入session
        session.setAttribute("kaptcha",text);
//        System.out.println(text);
        //讲图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os=response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败："+e.getMessage());
        }
    }

    @RequestMapping(path = "/login",method =    RequestMethod.GET)
    public String getLoginPage(){
        return "/login";
    }

    @RequestMapping(path = "login",method = RequestMethod.POST)
    public String login(String username,String password,String code,boolean remeber
                        ,Model model,HttpSession session,HttpServletResponse response){
        String kaptcha= (String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(kaptcha)||StringUtils.isBlank(code)||!kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确");
//            System.out.println("验证码不正确"+code+" "+kaptcha);
            return "/login";
        }
        //检查帐号，密码
        int expiredSeconds=remeber?REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;
        Map<String,Object> map=userService.login(username,password,expiredSeconds);
        if (map.containsKey("ticket")){
            Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/test";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
//            System.out.println(map.get("usernameMsg"));
//            System.out.println(map.get("passwordMsg"));
            return "/login";
        }
    }
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/login";
    }
}
