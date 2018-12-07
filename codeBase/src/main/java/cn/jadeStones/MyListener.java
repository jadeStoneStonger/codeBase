package cn.jadeStones;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.XmlWebApplicationContext;

public class MyListener extends XmlWebApplicationContext implements  ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("asdasd");		
		System.out.println("asdasd");	
		String location = sce.getServletContext().getInitParameter("contextConfigLocation");
		System.out.println(location);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("asdasd");		
		System.out.println("asdasd");		
		System.out.println("asdasd");		
// TODO Auto-generated method stub
		
	}

	
}
