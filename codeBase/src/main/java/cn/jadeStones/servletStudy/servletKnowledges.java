package cn.jadeStones.servletStudy;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet继承体系 
 * Servlet 
 * 		-- GenericServlet 1.已经重写了简单的init，destroy方法，只需重写service方法，
 * 						  2.实现了 ServletConfig，可以读取 ServletContext 信息（1 提供servlet与容器交互的能力，2重定向），以及此servlet初始化参数信息
 * 				-- HttpServlet 具体协议的servlet，实现了父类的service，分发不同的get。post等方法，这些方法需要重写，
 * @author jadeStones
 */
public class servletKnowledges extends HttpServlet {

	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 重写父类get方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String data = "";
        /**
         * ServletConfig对象中维护了ServletContext对象的引用，开发人员在编写servlet时，
         * 可以通过ServletConfig.getServletContext方法获得ServletContext对象。
         */
        ServletContext context = this.getServletConfig().getServletContext();//获得ServletContext对象
        context.setAttribute("data", data);  //将data存储到ServletContext对象中
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
