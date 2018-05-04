package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.annotation.BusinessAnnotation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/files")
public interface FileService extends RemoteService {
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @param captchaCode
	 * @return
	 * @throws IllegalArgumentException
	 */
	@BusinessAnnotation(serviceno=1,recordLog= true)
	List<String> queryDirectoryOrFileList();
	
	/**
	 * 查询所有目录，key为目录，value为父目录
	 * @return
	 */
	Map<String,String> queryDirList();
	/**
	 * 删除某文件
	 * @param filePath 待删除的文件夹路径或者文件全路径
	 * @param isCascade 是否级联删除（该字段主要正对目录删除）。
	 * 				-	当值为true时，为级联删除，即删除该文件夹及文件夹下所有内容；
	 * 				-   当值为false时，只有该文件夹为空时才可以删除，否则不能删除；
	 * @return
	 */
	boolean deleteFile(String filePath,boolean  isCascade);
	
	/**
	 * dirs有序：按照根目录到子目录一次排开
	 * @param dirs
	 * @return
	 */
	boolean createDir(String[] dirs);
	
	/**
	 * 根据文件夹或该文件下一级所有文件路径。不含子文件夹。
	 * @param dirUrl
	 * @return
	 */
	Map<String, String> queryFileLinksByDir(String dirUrl);
}
