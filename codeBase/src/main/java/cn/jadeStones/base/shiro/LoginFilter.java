package cn.jadeStones.base.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jadeStones.base.response.RespMessage;
import cn.jadeStones.base.response.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class LoginFilter extends UserFilter {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	public final static String DEFAULT_AJAX_REQUEST_NAME="X-Requested-With";
	public final static String DEFAULT_AJAX_REQUEST_VALUE="XMLHttpRequest";
	public final static String DEFAULT_REQUEST_JSON=".json";
	public final static String DEFAULT_REQUEST_JSONP=".jsonp";
	public final static String DEFAULT_REQUEST_XML=".xml";
	private final static String DEFAULT_CALLBACK_NAME="callback";
	
	private final static String DEFAULT_RESPONSE_TIMEOUT="请登录!";
//	private final static String DEFAULT_RESPONSE_HTML=String.format("<script type=\"text/javascript\">alert('%s');window.location.href='../login.html';</script>",DEFAULT_RESPONSE_TIMEOUT);
//	private final static String DEFAULT_FORWARD_HTML="<script type=\"text/javascript\">window.location.href='./login';</script>";
	
    /**
     * This default implementation simply calls
     * {@link #saveRequestAndRedirectToLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse) saveRequestAndRedirectToLogin}
     * and then immediately returns <code>false</code>, thereby preventing the chain from continuing so the redirect may
     * execute.
     */
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String requestURI = getPathWithinApplication(request);
		boolean isLogin=requestURI.indexOf(getLoginUrl())>-1;
		if(!isLogin){
			saveRequestAndRedirect(request,response);
		}
		return isLogin;
    }
	
	private void saveRequestAndRedirect(ServletRequest request,ServletResponse response) throws Exception{
		saveRequestAndRedirect((HttpServletRequest)request,(HttpServletResponse)response);
	}
	
	private void saveRequestAndRedirect(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String ajaxHeader=request.getHeader(DEFAULT_AJAX_REQUEST_NAME);
		String url=request.getRequestURI().toLowerCase();
//		if(null!=ajaxHeader&&ajaxHeader.equalsIgnoreCase(DEFAULT_AJAX_REQUEST_VALUE)){
			if(url.indexOf("?")>0){ //去掉?后面的参数
				url=url.substring(0,url.indexOf("?"));
			}
			if(url.endsWith(DEFAULT_REQUEST_JSON) || isJsonRequest(request)){
				forwardResponseJson(request, response);
			}else if(url.endsWith(DEFAULT_REQUEST_JSONP)){
				forwardResponseJsonp(request, response);
			}else if(url.endsWith(DEFAULT_REQUEST_XML)){
				forwardResponseXml(request, response);
			}else{ 
				//判断是否是oauth2请求,否则返回局部界面
				String requestURI = getPathWithinApplication(request);
				if(requestURI.equals("/oauth2/authorize")) {
					saveRequestAndRedirectToLogin(request, response);
				}else{
					redirectToLogin(request, response);
				}
			}
//		}
	}
	
	@Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        redirectToLogin(request, response);
    }
	
	public static void forwardResponseJson(HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.info("response json forward . url : {}",request.getServletPath());
		response.setContentType("application/json;charset=UTF-8");
		String content=JSONObject.toJSONString(getTimeoutResponse());
		responseWrite(response, content);
	}
	
	public static void forwardResponseJsonp(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String callback=getReuqestCallback(request);
		logger.info("response jsonp forward . callback : {} , url:{}",callback,request.getServletPath());
		response.setContentType("application/javascript;charset=UTF-8");
		String content=String.format("%s(%s);"
				,StringUtils.isBlank(callback)?DEFAULT_CALLBACK_NAME:callback.trim()
				,JSON.toJSONString(getTimeoutResponse()));
		responseWrite(response, content);
	}
	
	public static void forwardResponseXml(HttpServletRequest request,HttpServletResponse response) throws IOException, JAXBException{
		logger.info("response xml forward . url:{}",request.getServletPath());
		response.setContentType("application/xml;charset=UTF-8");
		String content=convertToXml(getTimeoutResponse());
		responseWrite(response,content);
	}
	
	/**
	 * 生成超时实体
	 * @return
	 */
	private static RespMessage<String> getTimeoutResponse(){
		RespMessage<String> response=new RespMessage<String>();
		response.setMessage(DEFAULT_RESPONSE_TIMEOUT);
		response.setStatus(ResponseStatus.timeout);
		return response;
	}
	
	private static void responseWrite(HttpServletResponse response,String content) throws IOException{
		logger.debug("unauthz response content:{}",content);
		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 取参数callback
	 * @param url
	 * @return
	 */
	private static String getReuqestCallback(HttpServletRequest request){
		String url=request.getRequestURI();
		String callback=request.getParameter(DEFAULT_CALLBACK_NAME);
		if(StringUtils.isBlank(callback)){
			String startWith=String.format("%s=", DEFAULT_CALLBACK_NAME);
			int maxLength=startWith.length();
			if(url.indexOf("?")>0){
				String param=url.substring(url.indexOf("?")+1);
				String[] array=param.split("&");
				for (String string : array) {
					if(string.length()>maxLength&&string.substring(0,maxLength).equalsIgnoreCase(startWith)){
						callback=string.substring(maxLength);
						break;
					}
				}
			}
		}
		return callback;
	}
	
	/**
	 * 转换XML
	 * @param value
	 * @return
	 * @throws JAXBException
	 */
	private static String convertToXml(Object value) throws JAXBException{
		String result=null;
		JAXBContext context = JAXBContext.newInstance(value.getClass());
        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING,"utf-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(value, writer);
        result = writer.toString();
        return result;
	}
	
	
	private boolean isJsonRequest(HttpServletRequest req){
		Enumeration<String> values = req.getHeaders("Accept");
		boolean isJson = false;
		if (values != null) {
			while(values.hasMoreElements()){
				if(values.nextElement().indexOf("json") !=-1){
					isJson = true;
					break;
				}
			}
			
		}
		return isJson;
	}
}
