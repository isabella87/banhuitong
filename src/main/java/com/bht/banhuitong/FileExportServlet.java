package com.bht.banhuitong;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bht.banhuitong.annotation.BusinessAnnotation;
import com.bht.banhuitong.fileUtils.Export2File;
import com.bht.banhuitong.filter.ModuleType;
import com.bht.banhuitong.server.impl.DbModelServiceImpl;
import com.bht.banhuitong.server.impl.PrjServiceImpl;

public class FileExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filename  ;

		// 根据模块类型和服务号来确定需要查询数据的服务
		String moduletype = req.getParameter("moduletype");
		String serviceno = req.getParameter("serviceno");

//		filename = new String(filename.getBytes("ISO8859-1"), "UTF-8"); // 解决get访问方式的中文乱码

		String filepath = getServletContext().getRealPath("/files");

		List<Map<String, String>> data = new ArrayList<Map<String, String>>(); // 从数据库查询数据

		Class<?> clazz = null;
		switch (Integer.valueOf(moduletype)) {
		case ModuleType.PRJ:
			clazz = PrjServiceImpl.class;
//			clazz.getInterfaces()
//			PrjService service = new PrjServiceImpl();
//			clazz = service.getClass();
			break;
		case ModuleType.DBMODEL:
			clazz = DbModelServiceImpl.class;
			break;
		}
		Map<String, String> args = new HashMap<String, String>();
		data = getServiceData(clazz, serviceno, args);
		String endName = "_"+moduletype+"_"+serviceno;
		
		filename = new Export2File().export(filepath,endName, "xls", data); // 以要求格式文件将数据保存在服务端

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

	@SuppressWarnings("unchecked")
	private List<Map<String, String>> getServiceData(Class<?> clazz, String serviceno, Map<String, String> args) {
		Class<?>[] clazzs = clazz.getInterfaces();
		
		Method[] methods = clazzs[0].getDeclaredMethods();
		if (methods != null) {
			for (Method method : methods) {
				BusinessAnnotation busiAnno = method.getAnnotation(BusinessAnnotation.class);
				if (busiAnno.serviceno() == Integer.valueOf(serviceno)) {
					try {
						return (List<Map<String, String>>) method.invoke(clazz.newInstance(), args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}