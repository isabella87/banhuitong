package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("account")
public interface AccountService extends RemoteService {

	List<Map<String, String>> getAccountInfo() throws Exception;
	
}
