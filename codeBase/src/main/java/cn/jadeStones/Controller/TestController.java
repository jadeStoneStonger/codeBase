package cn.jadeStones.Controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;
import cn.jadeStones.base.cache.CacheOperator;
import cn.jadeStones.base.response.MsgWrapper;
import cn.jadeStones.base.response.RespMessage;

@RequestMapping("/test")  
@Controller
public class TestController {
	
	@Autowired
	private TestService test;
	@Autowired
	private CacheOperator cacheOperate;
	
	@RequestMapping("/aaa")  
    public String toIndex(HttpServletRequest request,HttpServletResponse response) throws IOException{  
		System.out.println(test.selectById());
		System.out.println("asdasd");
		String data = "中国";
        OutputStream outputStream = response.getOutputStream();//获取OutputStream输出流
        response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码

        byte[] dataByteArr = data.getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
        outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
		
		return "redirect:/index.html"; 
	}
	
	private String goTo(String url){
		return "redirect:/" + url + ".html";
	}
	
	@RequestMapping("/getJson")
	@ResponseBody
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
               return MsgWrapper.success();  
           }else {  
               return MsgWrapper.error(10002);  
           }  
       }  
       return MsgWrapper.error(10003);  
   }  

	@RequestMapping("/redis")
	@ResponseBody
	public RespMessage<?> redis(){
		cacheOperate.setString("aa", "aa");
		String a = cacheOperate.getString("aa");
		return MsgWrapper.success(null);
	}
	

	@RequestMapping("/ehcacheAble")
	@ResponseBody
	public RespMessage<?> ehcacheAble(@RequestParam("ehCacheKey") String ehCacheKey){
		// 不能再同一个类中调用被注解缓存了的方法
		// @cacheable 这些缓存实际上还是aop 
		// 你要实现方法内部的调用 要在aop的配置上加上exposeProxy=true 
		// 然后使用的时候使用AopContext.currentProxy() 代替this
		return MsgWrapper.success(test.testEhcache(ehCacheKey));
	}
}