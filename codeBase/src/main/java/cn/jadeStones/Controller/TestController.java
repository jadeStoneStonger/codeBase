package cn.jadeStones.Controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;
import cn.jadeStones.base.cache.CacheOperate;
import cn.jadeStones.base.response.MsgWrapper;
import cn.jadeStones.base.response.RespMessage;

@RequestMapping("/test")  
@RestController
public class TestController {
	
	@Autowired
	private TestService test;
	@Autowired
	private CacheOperate cacheOperate;
	
	@RequestMapping("/aaa")  
    public String toIndex(HttpServletRequest request,Model model){  
		
		
		System.out.println(test.selectById());
		return "index"; 
	}
	
	@RequestMapping("/getJson")
	public RespMessage<Test> getTestById(){
		return MsgWrapper.success(new Test(1,"name","desc","aa"));
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)  
   public RespMessage<String> login(HttpServletRequest request, Model model){  
       String username=request.getParameter("name");  
       String password=request.getParameter("password");  
       if((username!=null && password!=null)){  
           UsernamePasswordToken token=new UsernamePasswordToken(username,password);  
           Subject subject= SecurityUtils.getSubject();  
           try{  
               subject.login(token);  
           }catch (AuthenticationException e){  
           }  
           if( subject.isAuthenticated()){  
               subject.logout();  
               return MsgWrapper.success();  
           }else {  
               return MsgWrapper.error(10002);  
           }  
       }  
       return MsgWrapper.error(10003);  
   }  

	@RequestMapping("/redis")
	@ResponseBody
	public RespMessage redis(){
		cacheOperate.setString("aa", "aa");
		String a = cacheOperate.getString("aa");
		return MsgWrapper.success(null);
	}
	
}