package net.xzh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//controller定义的方式 有两种类型三种实现
// 2种类型 BeanName 和 @contrller
// 3种实现： @Controller、implements Controller/HttpRequestHandler
@Component("/index2")
public class BaseNameController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("index2");
		modelAndView.addObject("ofid", "admin_" + System.currentTimeMillis());
		return modelAndView;
	}

}
