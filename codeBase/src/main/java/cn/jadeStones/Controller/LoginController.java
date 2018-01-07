package cn.jadeStones.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jadeStones.Service.TestService;

@Controller  
@RequestMapping("/index")  
public class LoginController {
	
	@Autowired
	private TestService test;
	
	@RequestMapping("/aaa")  
    public String toIndex(HttpServletRequest request,Model model){  
		System.out.println(test.selectById());
		return "index"; 
	}
}
