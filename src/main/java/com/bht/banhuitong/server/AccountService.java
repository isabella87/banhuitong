package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.shared.annotation.BusinessAnnotation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/account")
public interface AccountService extends RemoteService {
	
	@BusinessAnnotation(serviceno=1,recordLog= true)
	List<Map<String, String>> getBgAccountInfo() throws Exception;
	
	@BusinessAnnotation(serviceno=2,recordLog= true)
	List<Map<String, String>> getAccountInfo() throws Exception;
	
}
