package cn.jadeStones.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/index")  
public class LoginController {

	@RequestMapping("/aaa")  
    public String toIndex(HttpServletRequest request,Model model){  
        return "index"; 
	}
}
