package com.bht.banhuitong.server.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.filter.ModuleType;
import com.bht.banhuitong.server.FileService;
import com.bht.banhuitong.shared.annotation.BusinessAnnotation;
import com.bht.banhuitong.shared.fileutils.Export2File;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class FileServiceImpl extends RemoteServiceServlet implements FileService {

	private final static Logger logger = Logger.getLogger(FileServiceImpl.class);

	private final String IS_DIR = "1";
	private final String IS_FILE = "0";
	/**
	 * 需要下载文件的根目录，客户端界面会以数的形式展示目录，并以列表的形式展示各个目录下的所有文件
	 */
	// private final String rootPath = getServletContext().getRealPath("/files");

	/**
	 * 遍历以files为根目录的目录及文件，遍历至最后一层将目录或者文件以以下的格式加入到list。 参数格式：“T,ParentName,Name”(其中:
	 * - T为是否为目录，是为1，不是为0； - ParentName为父节点的名称，根节点无父节点填0； - Name为目录名或者文件名)
	 */
	@Override
	public List<String> queryDirectoryOrFileList() {
		List<String> fileList = new ArrayList<String>();
		writeFileInfo(Configuration.rootPath, fileList);

		return fileList;
	}

	public void writeFileInfo(String path, List<String> list) {
		File file = new File(path);
		String parentName = file.getName();

		for (File dir : file.listFiles()) {
			if (dir.isDirectory()) {
				list.add(IS_DIR + "," + parentName + "," + dir.getName());
				writeFileInfo(dir.getAbsolutePath(), list);
			} else {
				list.add(IS_FILE + "," + parentName + "," + dir.getAbsolutePath());
			}
		}
	}

	/**
	 * 可以遍历删除目录和目录下的文件。实现了非级联删除
	 */
	@Override
	public boolean deleteFile(String filePath, boolean isCascade) {
		if (!filePath.contains(Configuration.rootPath)) {
			filePath = Configuration.rootPath + File.separator + filePath;
		}

		File file = new File(filePath);
		if (isCascade) {
			return delete(filePath);
		} else if (file.isDirectory() && file.listFiles().length > 0) {
			return false;
		}
		return file.delete();
	}

	/**
	 * 级联删除
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean delete(String filePath) {
		File file = new File(filePath);
		for (File dir : file.listFiles()) {
			if (dir.isDirectory()) {
				delete(dir.getAbsolutePath());
			} else {
				dir.delete();
			}
		}
		return file.delete();
	}

	@Override
	public boolean createDir(String[] dirs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dirs.length; i++) {
			sb.append(i != 0 ? File.separator : "").append(dirs[i]);
			logger.info("param is :" + dirs[i]);
		}
		File file = new File(sb.toString());

		return file.mkdir();
	}

	@Override
	public Map<String, String> queryFileLinksByDir(String dirUrl) {
		Map<String, String> fileLinks = new LinkedHashMap<String, String>();
		File files = new File(dirUrl);
		for (File file : files.listFiles()) {
			if (file.isFile()) {
				String absolutePath = file.getAbsolutePath();
				fileLinks.put(file.getName(), absolutePath.substring(Configuration.rootPath.length() + 1));
			}
		}
		return fileLinks;
	}

	@Override
	public Map<String, String> queryDirList() {
		Map<String, String> dirMap = new LinkedHashMap<String, String>();
		dirMap.put(Configuration.rootPath, "files" + "," + Configuration.rootPath + ",0");
		queryDirInfo(Configuration.rootPath, dirMap);

		return dirMap;
	}

	private void queryDirInfo(String path, Map<String, String> dirMap) {
		File file = new File(path);
		for (File dir : file.listFiles()) {
			if (dir.isDirectory()) {
				dirMap.put(dir.getAbsolutePath(),
						dir.getName() + "," + dir.getAbsolutePath() + "," + file.getAbsolutePath());
				queryDirInfo(dir.getAbsolutePath(), dirMap);
			}
		}
	}

	@Override
	public String writeToFile(String module, String serviceno, Map<String, String> paramMap)
			throws IOException, SerializationException {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>(); // 从数据库查询数据

		Class<?> clazz = null;
		switch (Integer.valueOf(module)) {
		case ModuleType.PRJ:
			clazz = PrjServiceImpl.class;
			break;
		case ModuleType.DBMODEL:
			clazz = DbModelServiceImpl.class;
			break;
		}
		data = getServiceData(clazz, serviceno, paramMap);
		String endName = "_" + module + "_" + serviceno;
		String filepath = Configuration.rootPath + File.separator + "module" + module + File.separator + "serviceno"
				+ serviceno;

		String fileAllPath = new Export2File().export(endName,filepath, endName, "xls", data); // 以要求格式文件将数据保存在服务端

		return fileAllPath.substring(Configuration.rootPath.length() + 1);
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
