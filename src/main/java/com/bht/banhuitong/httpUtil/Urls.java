package com.bht.banhuitong.httpUtil;

public class Urls {

	/**
	 * "http://www.banbank.com" "http://192.168.11.114"
	 */
	public static final String ROOT_URL = "http://localhost:8889";
	public static final String QUERY_INVESTORS_URL = ROOT_URL + "/p2psrv/mgr/crm/investors";
	public static final String QUERY_ENT_URL = ROOT_URL + "/p2psrv/mgr/prj/ent-projs";
	public static final String LOGIN_URL = ROOT_URL + "/p2psrv/security/signin";
//	public static final String LOGIN_URL = ROOT_URL + "/accsrv/account/sign-in";
	public static final String VALIDATE_USER_URL = ROOT_URL + "/p2psrv/security/validate-user";
	public static final String CAPTCHA_IMAGE_URL = ROOT_URL + "/p2psrv/security/captcha-image";
	public static final String CUR_USER_URL = ROOT_URL + "/p2psrv/security/user";
	public static final String CLIENT_MGR_TREE_URL = ROOT_URL + "/p2psrv/mgr/crm/relations";
	// 一般项目
	public static final String LOAN_PROJS_URL = ROOT_URL + "/p2psrv/mgr/prj/loan-projs";
	// baseData ba_owner
	public static final String OWNER_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-owners";
	// baseDate ba_ctor_org
	public static final String CTOR_ORG_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-ctors";
	// baseDate ba_prj_engineers
	public static final String ENGINEER_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-engineers";
	// baseDate ba_guarantee_org
	public static final String GUARANTEE_ORG_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-guarantee-orgs";
	// baseDate ba_guarantee_person
	public static final String GUARANTEE_PERSON_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-guarantee-persons";
	// baseDate ba_prj_mgr_orgs
	public static final String MGR_ORG_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-mgr-orgs";
	// baseDate ba_prj_orgs
	public static final String MGR_PERSON_URL = ROOT_URL + "/p2psrv/mgr/ba/prj-mgr-persons";
	// 获取全部文件
	public static final String QUERY_FILES_URL = ROOT_URL + "/p2psrv/mgr/files/list";
	// 下载文件、上传新文件、删除文件
	public static final String UPLOAD_OR_DOWNLOAD_URL = ROOT_URL + "/p2psrv/mgr/files";
	// USER
	public static final String QUERY_USERS_URL = ROOT_URL + "/p2psrv/mgr/sys-users";
	// ROLE
	public static final String QUERY_ROLES_URL = ROOT_URL + "/p2psrv/mgr/sys-roles";
	
	public static final String UPDATE_PASSWORD_URL = ROOT_URL + "/p2psrv/security/change-pwd";
	
	// MGR PRJ
	public static final String MGR_PRJ_URL = ROOT_URL + "/p2psrv/mgr/prj";
	// AUTO CREDIT
	public static final String AUTO_CREDIT_URL = ROOT_URL + "/p2psrv/mgr/prj/auto_credit";
	// MGR TRANS
	public static final String MGR_TRANS_URL = ROOT_URL + "/p2psrv/mgr/trans";
	// TRANS
	public static final String TRANS_URL = ROOT_URL + "/p2psrv/trans";
	// ACCOUNT BORROW
	public static final String ACCOUNT_BORROW_URL = ROOT_URL + "/p2psrv/mgr/account/borrows";
	
	//上传文件
	public static final String FILE_UPLOAD_URL = ROOT_URL + "/p2psrv/mgr/files/";
	
}
