package cn.jadeStones.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;
import cn.jadeStones.base.MsgWrapper;
import cn.jadeStones.base.RespMessage;

@Controller  
@RequestMapping("/test")  
public class TestController {
	
	@Autowired
	private TestService test;
	
	@RequestMapping("/aaa")  
    public String toIndex(HttpServletRequest request,Model model){  
		System.out.println(test.selectById());
		return "index"; 
	}
	
	@RequestMapping("/getJson")
	@ResponseBody
	public RespMessage<Test> getTestById(){
		return MsgWrapper.success(new Test(1,"name","desc"));
	}
}
