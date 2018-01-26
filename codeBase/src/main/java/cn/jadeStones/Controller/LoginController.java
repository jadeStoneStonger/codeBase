package cn.jadeStones.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jadeStones.base.response.MsgWrapper;
import cn.jadeStones.base.response.RespMessage;

@Controller
public class LoginController {
	public final static String DEFAULT_PLATFORM_COOKIENAME="rs-platform-id";

	@RequestMapping("/index")
	public String index(HttpServletRequest request,HttpServletResponse response,
			@CookieValue(value=DEFAULT_PLATFORM_COOKIENAME, defaultValue="0") int platId){
		System.out.println("asdasd");
		return "redirect:/index.html";
	}
	
	@RequestMapping(value = "/login")  
    public RespMessage<String> login(HttpServletRequest request){  
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
       if(username == null && password == null){
           return MsgWrapper.error(10004);  
       }
       return MsgWrapper.error(10003);  
    } 
	
	@RequestMapping("/logout")
	public RespMessage<String> logout(
			HttpServletRequest request,
			HttpServletResponse res){
		//退出
		Subject subject =SecurityUtils.getSubject();
		subject.logout();
		//删除cookie
		removeCookie(request, res, DEFAULT_PLATFORM_COOKIENAME);
		//
		return MsgWrapper.success();
	}
	
	
	
	
	/**
	 * 删除Cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	private void removeCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){		
		Cookie[] cookies=request.getCookies();
		if(null!=cookies){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equalsIgnoreCase(cookieName)){
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
	
}
