package net.xzh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 模拟ajax业务调用
 * 
 * @author Administrator
 * 
 */
public class LoginAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String loginName = "admin";
		if (req.getParameter("name") != null) {
			loginName = req.getParameter("name");
		}
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(loginName + System.currentTimeMillis() + "");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
