package com.bht.banhuitong;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<?> items = null;

		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		}

		Iterator<?> iter = items.iterator();

		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();

			String savePath = getServletContext().getRealPath("/files");

			String filename = item.getName();

			File file = new File(savePath, filename.substring(filename.lastIndexOf(File.separator) + 1));

			try {
				item.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
