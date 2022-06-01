package net.xzh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

//controller定义的方式 有两种类型三种实现
// 2种类型 BeanName 和 @contrller
// 3种实现： @Controller、implements Controller/HttpRequestHandler
@Component("/index3")
public class HandlerController implements HttpRequestHandler {
//	支持servlet参数
//	HttpServletRequest
//	HttpServletResponse
//	HttpSession
//	java.security.Principal
//	Locale
//	InputStream
//	OutputStream
//	Reader
//	Writer
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("index3");
        //设置返回页面
		response.getWriter().println("implements HttpRequestHandler");
//		response.sendRedirect("index1");//客户端重定向
//		request.getRequestDispatcher("index2").forward(request,response);//服务器跳转
		
		
	}
}
