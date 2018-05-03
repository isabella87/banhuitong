package com.bht.banhuitong.server.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.server.FileService;
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
	 * 需要下载文件的更目录，客户端界面会以数的形式展示目录，并以列表的形式展示各个目录下的所有文件
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
		String path = Configuration.rootPath;
		for (String dir : dirs) {
			path += File.separator + dir;
		}
		File file = new File(path);

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
		dirMap.put("files", "0" + "," + Configuration.rootPath);

		queryDirInfo(Configuration.rootPath, dirMap);

		return dirMap;
	}

	private void queryDirInfo(String path, Map<String, String> dirMap) {
		File file = new File(path);
		for (File dir : file.listFiles()) {
			if (dir.isDirectory()) {
				dirMap.put(dir.getName(), file.getName() + "," + dir.getAbsolutePath());
				queryDirInfo(dir.getAbsolutePath(), dirMap);
			}
		}
	}

}
