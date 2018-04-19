package com.bht.banhuitong;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filename = req.getParameter("filename");

		filename = new String(filename.getBytes("ISO8859-1"), "UTF-8"); // 解决get访问方式的中文乱码

		String filepath = getServletContext().getRealPath("/files");

		File file = new File(filepath + File.separator + filename);

		// 下面代码从网上拉的，直接抄就行了
		FileInputStream fis = new FileInputStream(file);
		resp.addHeader("Content-Disposition", "attachment; filename=" + filename);

		ServletOutputStream out = resp.getOutputStream();
		resp.setBufferSize(32768);
		int bufSize = resp.getBufferSize();
		byte[] buffer = new byte[bufSize];
		BufferedInputStream bis = new BufferedInputStream(fis, bufSize);

		int bytes;
		while ((bytes = bis.read(buffer, 0, bufSize)) >= 0)
			out.write(buffer, 0, bytes);
		bis.close();
		fis.close();
		out.flush();
		out.close();
	}
}