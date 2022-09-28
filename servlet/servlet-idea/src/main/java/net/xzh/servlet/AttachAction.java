package net.xzh.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流方式下载附件
 * 
 * @author Administrator
 * 
 */
public class AttachAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OutputStream o = resp.getOutputStream();
		byte b[] = new byte[1024];
		String webPath = req.getSession().getServletContext().getRealPath("");
		File fileLoad = new File(webPath + "/download", "1100.jpg");
		resp.setContentType("application/octet-stream;charset=UTF-8");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode("1100.jpg", "UTF-8"));
		FileInputStream in = new FileInputStream(fileLoad);
		int n = 0;
		while ((n = in.read(b)) != -1) {
			o.write(b, 0, n);
		}
		in.close();
		o.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
