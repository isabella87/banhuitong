package com.bht.banhuitong.http.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.http.HttpClient;
import com.bht.banhuitong.http.Urls;
import com.bht.banhuitong.http.service.RpcService;

public class RpcServiceImpl implements RpcService {

	public Logger logger = Logger.getLogger(RpcServiceImpl.class);

	public List<Map<String, String>> getClientMgrTreeDatas(Map<String, String> paramMap) {
		return RpcDataServiceParser.getInstance().listValue("GET", Urls.CLIENT_MGR_TREE_URL, paramMap);
	}

	public List<Map<String, String>> getCrmMgrRelation(Map<String, String> paramMap) {
		String url = Urls.ROOT_URL + "/p2psrv/mgr/crm/" + paramMap.get("name") + "/infor";
		return RpcDataServiceParser.getInstance().listValue("GET", url, paramMap);
	}

	public List<Map<String, String>> getInvestors(Map<String, String> paramMap) {
		return RpcDataServiceParser.getInstance().listValue("GET", Urls.QUERY_INVESTORS_URL, paramMap);
	}

	/***************************************************************************
	 ******************* 项目操作部分 ****************
	 ***************************************************************************/

	/**
	 * 查询项目
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=]
	 * @return 符合条件记录
	 */
	public List<Map<String, String>> queryProjects(Map<String, String> paramMap) {

		return new ResultValue().listValue(HttpClient.getInstance().sendRequest("GET", Urls.LOAN_PROJS_URL, paramMap));

		// return RpcDataServiceParser.getInstance().listValue("GET",
		// Urls.LOAN_PROJS_URL, paramMap);

	}

	/**
	 * 通过ID 查询项目
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=], p-id
	 * @return 符合条件记录
	 */
	public String queryProjectById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.LOAN_PROJS_URL + "/" + id, paramMap);
	}

	/**
	 * 还款周期策略预览
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=], p-id
	 * @return 符合条件记录
	 */
	public String previewLoanBonus(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.LOAN_PROJS_URL + "/" + id + "/preview-bonus",
				paramMap);

	}

	/**
	 * 创建项目
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=]
	 * @return 符合条件记录
	 */
	public String createProject(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.LOAN_PROJS_URL, paramMap);
	}

	/**
	 * 更新项目
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=]
	 * @return 符合条件记录
	 */
	public String updateProject(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.LOAN_PROJS_URL + "/" + id, paramMap);
	}

	/**
	 * 项目审批
	 * 
	 * @param paramMap[flag、comment、pId],
	 *            id 项目id, action 审批操作
	 * @return 是否执行成功
	 */
	public String approvalAction(Map<String, String> paramMap, long id, String action) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.LOAN_PROJS_URL + "/" + id + "/" + action,
				paramMap);
	}

	/**********************************
	 * MGR PRJ
	 ****************************************/
	/**
	 * 创建担保机构 PRJ_GUARANREE_ORG
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=]
	 * @return 符合条件记录
	 */
	public String createPrjGuaranteeOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_PRJ_URL + "/" + id + "/guarantee-org",
				paramMap);
	}

	/**
	 * 创建担保机构 PRJ_GUARANREE_PERSON
	 * 
	 * @param paramMap[itemNoKey=,
	 *            startTime=, endTime=, status=]
	 * @return 符合条件记录
	 */
	public String createPrjGuaranteePerson(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_PRJ_URL + "/" + id + "/guarantee-person",
				paramMap);
	}

	/**
	 * 根据ID查询项目的所有借款担保。
	 * 
	 * @param pId
	 * @return 所有的借款担保。
	 */
	public String queryPrjGuaranteePersonByPId(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + id + "/guarantee-person",
				paramMap);
	}

	/**
	 * 根据ID查询项目的所有借款担保(机构)
	 * 
	 * @param pId
	 *            项目ID
	 * @return 所有的借款担保。
	 */
	public String queryPrjGuaranteeOrgByPId(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + id + "/guarantee-org",
				paramMap);
	}

	/**
	 * 删除指定的项目担保快照。（个人）
	 * 
	 * @param pId
	 *            项目ID
	 * @param pgId
	 *            借款担保的ID。
	 * @return 成功删除的记录数。
	 */
	public String deletePrjGuaranteePerson(Map<String, String> paramMap, long pId, long pgpId) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE",
				Urls.MGR_PRJ_URL + "/" + pId + "/guarantee-person" + "/" + pgpId, paramMap);
	}

	/**
	 * 删除指定的项目担保快照(机构)
	 * 
	 * @param pId
	 *            项目ID
	 * @param pgId
	 *            借款担保的ID。
	 * @return 成功删除的记录数。
	 */
	public String deletePrjGuaranteeOrg(Map<String, String> paramMap, long pId, long pgoId) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE",
				Urls.MGR_PRJ_URL + "/" + pId + "/guarantee-org" + "/" + pgoId, paramMap);
	}

	/**
	 * 创建项目经理快照
	 * 
	 * @param pId
	 * @param paramMap
	 * @return
	 */
	public String createPrjMgrOrg(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_PRJ_URL + "/" + pId + "/mgr-org",
				paramMap);
	}

	/**
	 * 创建项目借款企业快照
	 * 
	 * @param pId
	 * @param paramMap
	 * @return
	 */
	public String createPrjMgrPerson(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_PRJ_URL + "/" + pId + "/mgr-person",
				paramMap);
	}

	/**
	 * 查询项目借款人的快照信息
	 * 
	 * @param pId
	 * @return
	 */
	public String queryPrjMgrPersonByPId(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/mgr-person",
				paramMap);
	}

	/**
	 * 查询项目借款企业的快照信息
	 * 
	 * @param pId
	 * @return
	 */
	public String queryPrjMgrOrgByPId(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/mgr-org",
				paramMap);
	}

	/**
	 * 查询项目借款人的快照信息
	 * 
	 * @param pId
	 * @return
	 */
	public String deletePrjMgrPerson(Map<String, String> paramMap, long pId, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE",
				Urls.MGR_PRJ_URL + "/" + pId + "/mgr-person" + "/" + id, paramMap);
	}

	/**
	 * 查询项目借款企业的快照信息
	 * 
	 * @param pId
	 * @return
	 */
	public String deletePrjMgrOrg(Map<String, String> paramMap, long pId, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE",
				Urls.MGR_PRJ_URL + "/" + pId + "/mgr-org" + "/" + id, paramMap);
	}

	/**
	 * 查询项目评级信息。
	 * 
	 * @param pId
	 *            工程贷ID
	 * @return
	 */
	public String queryPrjRating(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/rating",
				paramMap);
	}

	/**
	 * 创建项目评级记录。
	 * <p>
	 * 此方法首先会删除指定工程贷的已存在的评级记录，然后创建新的评级记录。参数<code>prjRating</code>
	 * 的内容应当是文字表述，其他参数的值都是<code>0</code>到 <code>100</code>之间的整数，采用百分制。
	 * </p>
	 * 
	 * @param pId
	 *            供应贷ID。
	 * @param paramMap
	 *            [pId、prjRating、sEngineering、sPrjMgr、sCtor、sOwner、sGuarantee、sAll]。
	 * @return 成功创建的项目评分记录数。
	 */
	public String createPrjRating(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.MGR_PRJ_URL + "/" + pId + "/rating",
				paramMap);
	}

	/**
	 * 查询项目审核操作流水
	 * 
	 * @param pId
	 *            项目ID
	 * @return 该项目的所有操作流水，按照时间排序。
	 */
	public String queryAuditLine(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/actions",
				paramMap);
	}

	/**
	 * 更新项目保证金
	 * 
	 * @param pId
	 *            项目Id
	 * @param bondAmt
	 *            保证金金额
	 * @return 返回是否更新成功
	 */
	public String updateBondAmt(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.MGR_PRJ_URL + "/" + pId + "/bond", paramMap);
	}

	/**
	 * 查询项目的投标信息。
	 * 
	 * @param pId
	 *            项目ID
	 * @return 指定项目贷的所有投标信息。
	 */
	public String queryTenders(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/tenders",
				paramMap);
	}

	/**
	 * 查询某项目状态未知投标的所有JbId
	 * 
	 */
	public String queryUncheckedTenders(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET",
				Urls.MGR_PRJ_URL + "/" + pId + "/unchecked-tenders", paramMap);
	}

	/**
	 * 根据投资用户及投资订单号核查某项目的未回调投资业务后记录相关回调信息
	 * 
	 * @param jbId
	 * @return
	 */
	public String updateUncheckedTenders(Map<String, String> paramMap, long pId, long jbId) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.MGR_PRJ_URL + "/" + pId + "/update-unchecked-tender/" + jbId, paramMap);
	}

	/**
	 * 结清项目
	 * 
	 * @param pId
	 * @return
	 */
	public String completed(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.MGR_PRJ_URL + "/" + pId + "/completed",
				paramMap);
	}

	/**
	 * 查询项目的有效投资
	 * 
	 * @param pId
	 *            项目ID
	 * @param datepoint
	 *            查询时间点。
	 * @return 满足条件的有效投资，按照生效时间排序。
	 */
	public String queryInvests(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/invests",
				paramMap);
	}

	/**
	 * 查询项目的流标信息。
	 * 
	 * @param pId
	 *            项目ID
	 * @return 指定项目的所有流标信息。
	 */
	public String queryCancelTenders(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/cancel-tenders",
				paramMap);
	}

	/**
	 * 查询项目的放款信息。
	 * 
	 * @param pId
	 *            项目ID
	 * @return 指定项目的所有放款信息。
	 */
	public String queryLoans(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/loans", paramMap);
	}

	/**
	 * 查询指定项目投资人信息
	 * 
	 * @param pId
	 * @return 从ts_invest
	 *         中查询某项目的并且s_type为1的投资，根据投资人au_id分组；（返回信息包括，姓名/机构名称、身份证号/统一社会信用代码、手机、电子邮箱、出借金额）
	 */
	public String queryInvestors(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PRJ_URL + "/" + pId + "/investors",
				paramMap);
	}

	/**
	 * 设置原项目是否允许债权转让
	 * 
	 * @param pId
	 * @param paramMap
	 * @return true or false
	 */
	public String isBanCredit(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.MGR_PRJ_URL + "/" + pId + "/credit-out-status", paramMap);
	}

	/**
	 * 查询原项目是否允许债权转让
	 * 
	 * @param pId
	 * @return false or true
	 */
	public String getAllowCreditOut(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET",
				Urls.MGR_PRJ_URL + "/" + pId + "/credit-out-status", paramMap);
	}

	/******************************
	 * MGR PRJ AUTO CREDIT
	 *********************************/
	/**
	 * 取消项目自动债权转让
	 * 
	 * @param pId
	 *            项目id
	 * @return true
	 */
	public String cancelAutoCredit(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.AUTO_CREDIT_URL + "/" + pId, paramMap);
	}

	/**
	 * 查询项目自动债权转让状态及时间
	 * 
	 * @param pId
	 *            项目id
	 * @param paramMap
	 * @return prj_index中的auto_credit_date
	 */
	public String queryAutoCreditStatus(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.AUTO_CREDIT_URL + "/" + pId, paramMap);
	}

	/**
	 * 设置项目自动债权转让
	 * 
	 * @param pId项目id
	 * @return null
	 */
	public String setAutoCredit(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.AUTO_CREDIT_URL + "/" + pId, paramMap);
	}

	/***********************************
	 * MGR TRANS
	 *************************************/
	/**
	 * TODO:根据投资记录创建对应的撤销投资的记录
	 * 
	 * @param ttId
	 *            投资记录的ID
	 * @param remark
	 *            撤销投资的备注
	 * @return 成功创建的撤销投资的记录数
	 */
	public String withdrawTender(Map<String, String> paramMap, long ttId) {

		return RpcDataServiceParser.getInstance().stringValue("PUT",
				Urls.MGR_TRANS_URL + "/tenders/" + ttId + "/cancellation", paramMap);
	}

	/**
	 * 执行流标
	 *
	 * @param pinflag
	 *            是否检查密码标志
	 * @param pin
	 *            密码
	 * @param tctId
	 *            投标Id
	 * @return true or false
	 *
	 */
	public String cancelTender(Map<String, String> paramMap, long tctId) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.MGR_TRANS_URL + "/cancel-tender/" + tctId + "/execution", paramMap);
	}

	/*************************************
	 * TRANS
	 *************************************/
	/**
	 * 债权跟踪
	 * 
	 * @param tiId
	 * @return
	 */
	public String traceCredit(Map<String, String> paramMap, long tiId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.TRANS_URL + "/" + tiId + "/credit-trace",
				paramMap);
	}

	/*********************************************************************************/

	/**
	 * 账户
	 * 
	 * @param paramMap
	 * @return
	 */
	public String queryAccountBorrow(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.ACCOUNT_BORROW_URL + "/jx-completed",
				paramMap);
	}

	/**
	 * 账户
	 * 
	 * @param paramMap
	 * @return
	 */
	public String queryAccountBorrowByPId(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.LOAN_PROJS_URL + "/" + pId + "/financier",
				paramMap);
	}

	/**
	 * 更新实际借款人账户
	 * 
	 * @param paramMap
	 * @return
	 */
	public String updateFinancier(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.LOAN_PROJS_URL + "/" + pId + "/financier",
				paramMap);
	}

	/**
	 * 更新名义借款人和担保人账户
	 * 
	 * @param paramMap
	 * @return
	 */
	public String updateNominalAndBondsman(Map<String, String> paramMap, long pId) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.LOAN_PROJS_URL + "/" + pId + "/related-financier", paramMap);
	}

	/**
	 * 查询项目业主
	 * 
	 * @param paramMap
	 * @return JSON 字符串
	 */
	public String queryOwners(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.OWNER_URL, paramMap);
	}

	/**
	 * 通过ID 查询项目业主
	 * 
	 * @param paramMap
	 * @param id
	 * @return JSON字符串：该ID下项目业主信息
	 */
	public String queryOwnerById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.OWNER_URL + "/" + id, paramMap);
	}

	/**
	 * 修改项目业主信息
	 * 
	 * @param paramMap
	 * @param id
	 * @return 更新成功 该记录数据 失败 null
	 */
	public String updateOwner(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.OWNER_URL + "/" + id, paramMap);
	}

	/**
	 * 创建项目业主
	 * 
	 * @param paramMap
	 * @return 成功 该记录数据 失败 null
	 */
	public String createOwner(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.OWNER_URL, paramMap);
	}

	/**
	 * 删除项目业主
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功则返回该ID，否则返回0；记录不存在，也返回0。
	 */
	public String delOwner(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.OWNER_URL + "/" + id, paramMap);
	}

	/**
	 * 查询施工单位
	 * 
	 * @param paramMap
	 * @return JSON字符串
	 */
	public String queryCtorOrg(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.CTOR_ORG_URL, paramMap);

	}

	/**
	 * 通过ID 查询施工单位
	 * 
	 * @param paramMap
	 * @param id
	 * @return 存在返回该ID施工单位基础数据，不存在返回null
	 */
	public String queryCtorOrgById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.CTOR_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 创建施工单位
	 * 
	 * @param paramMap
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String createCtorOrg(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.CTOR_ORG_URL, paramMap);
	}

	/**
	 * 更新施工单位
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String updateCtorOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.CTOR_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 删除该施工单位
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功则返回该ID，失败返回0；记录不存在，也返回0。
	 */
	public String delCtorOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.CTOR_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 查询项目工程
	 * 
	 * @param paramMap
	 * @return 存在返回记录,不存在返回空字符串。
	 */
	public String queryEngineer(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.ENGINEER_URL, paramMap);
	}

	/**
	 * 通过ID 查询项目工程
	 * 
	 * @param paramMap
	 * @param id
	 * @return 存在返回该ID施工单位基础数据，不存在返回null
	 */
	public String queryEngineerById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.ENGINEER_URL + "/" + id, paramMap);
	}

	/**
	 * 创建项目工程
	 * 
	 * @param paramMap
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String createEngineer(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.ENGINEER_URL, paramMap);

	}

	/**
	 * 更新项目工程
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String updateEngineer(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.ENGINEER_URL + "/" + id, paramMap);

	}

	/**
	 * 删除项目工程
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String delEngineer(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.ENGINEER_URL + "/" + id, paramMap);

	}

	/**
	 * 查询担保机构
	 * 
	 * @param paramMap：创建时间（开始时间、结束时间）、关键字
	 * @return JSON 字符串
	 */
	public String queryGuaranteeOrg(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.GUARANTEE_ORG_URL, paramMap);
	}

	/**
	 * 通过id 查询担保机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return JSON 字符串：该ID担保机构的信息
	 */
	public String queryGuaranteeOrgById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.GUARANTEE_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 创建担保机构
	 * 
	 * @param paramMap
	 * @return
	 */
	public String createGuaranteeOrg(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.GUARANTEE_ORG_URL, paramMap);

	}

	/**
	 * 修改担保机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String updateGuaranteeOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.GUARANTEE_ORG_URL + "/" + id, paramMap);
	}

	/**
	 * 删除担保机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return 删除成功返回id，不成功返回0
	 */
	public String delGuaranteeOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.GUARANTEE_ORG_URL + "/" + id, paramMap);
	}

	/**
	 * 查询担保人
	 * 
	 * @param paramMap：创建时间（开始时间、结束时间）、关键字
	 * @return JSON 字符串
	 */
	public String queryGuaranteePerson(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.GUARANTEE_PERSON_URL, paramMap);
	}

	/**
	 * 通过id 查询担保人
	 * 
	 * @param paramMap
	 * @param id
	 * @return JSON 字符串：该ID担保机构的信息
	 */
	public String queryGuaranteePersonById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.GUARANTEE_PERSON_URL + "/" + id, paramMap);

	}

	/**
	 * 创建担保人
	 * 
	 * @param paramMap
	 * @return
	 */
	public String createGuaranteePerson(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.GUARANTEE_PERSON_URL, paramMap);

	}

	/**
	 * 修改担保人
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String updateGuaranteePerson(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.GUARANTEE_PERSON_URL + "/" + id, paramMap);
	}

	/**
	 * 删除担保人
	 * 
	 * @param paramMap
	 * @param id
	 * @return 删除成功返回id，不成功返回0
	 */
	public String delGuaranteePerson(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.GUARANTEE_PERSON_URL + "/" + id, paramMap);
	}

	/**
	 * 查询借款机构
	 * 
	 * @param paramMap
	 * @return 存在返回记录,不存在返回空字符串。
	 */
	public String queryMgrOrgs(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_ORG_URL, paramMap);
	}

	/**
	 * 通过ID 查询借款机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String queryMgrOrgById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 创建借款机构
	 * 
	 * @param paramMap
	 * @return 成功返回记录数据，失败返回null
	 */
	public String createMgrOrg(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_ORG_URL, paramMap);
	}

	/**
	 * 更新借款机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String updateMgrOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.MGR_ORG_URL + "/" + id, paramMap);
	}

	/**
	 * 删除借款机构
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功返回该记录ID，失败返回0
	 */
	public String delMgrOrg(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.MGR_ORG_URL + "/" + id, paramMap);

	}

	/**
	 * 查询借款人
	 * 
	 * @param paramMap
	 * @return
	 */
	public String queryMgrPerson(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PERSON_URL, paramMap);
	}

	/**
	 * 通过ID 查询借款人
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String queryMgrPersonById(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.MGR_PERSON_URL + "/" + id, paramMap);
	}

	/**
	 * 创建借款人
	 * 
	 * @param paramMap
	 * @return 成功返回该记录数据，失败返回null
	 */
	public String createMgrPerson(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.MGR_PERSON_URL, paramMap);
	}

	/**
	 * 更新借款人信息
	 * 
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public String updateMgrPerson(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.MGR_PERSON_URL + "/" + id, paramMap);
	}

	/**
	 * 删除借款人
	 * 
	 * @param paramMap
	 * @param id
	 * @return 成功返回该记录ID，失败返回0
	 */
	public String delMgrPerson(Map<String, String> paramMap, long id) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.MGR_PERSON_URL + "/" + id, paramMap);
	}

	/****************************************
	 * UPLOAD FILE
	 ***************************************************/

	public String upload(Map<String, String> paramMap, byte[] content) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.FILE_UPLOAD_URL + "/content2", paramMap);
	}

	/******************************************************************************************************/
	// 操作核实列表文件
	public String getFiles(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_FILES_URL, paramMap);
	}

	public String putFiles(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.UPLOAD_OR_DOWNLOAD_URL, paramMap);
	}

	public String postFiles(Map<String, String> paramMap, long fileId) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.UPLOAD_OR_DOWNLOAD_URL + "/" + fileId + "/content", paramMap);
	}

	public byte[] downloadFile(Map<String, String> paramMap, long fileId, String hash) {
		return RpcDataServiceParser.getInstance().byteValue("GET",
				Urls.UPLOAD_OR_DOWNLOAD_URL + "/" + fileId + "/" + hash, paramMap);
	}

	public String deleteFile(Map<String, String> paramMap, long fileId) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.UPLOAD_OR_DOWNLOAD_URL + "/" + fileId,
				paramMap);
	}

	public String login(Map<String, String> paramMap) {

		// return RpcDataServiceParser.getInstance().stringValue("POST", Urls.LOGIN_URL,
		// paramMap);

		return new ResultValue().stringValue(HttpClient.getInstance().sendRequest("POST", Urls.LOGIN_URL, paramMap));

	}

	/**
	 * 验证账号信息
	 * 
	 * @param paramMap[password]
	 * @return
	 */
	public String validateUser(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.VALIDATE_USER_URL, paramMap);
	}

	/**
	 * USER 获得账户列表
	 */
	public String getUsers(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_USERS_URL, paramMap);
	}

	/**
	 * USER 通过User Name 获取账号信息
	 */
	public String getUserByUserName(Map<String, String> paramMap, String userName) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_USERS_URL + "/" + userName, paramMap);
	}

	/**
	 * USER 通过User Name 编辑账号信息
	 */
	public String postUser(Map<String, String> paramMap, String userName) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.QUERY_USERS_URL + "/" + userName, paramMap);
	}

	/**
	 * USER DELETE USER BY USERNAME
	 */
	public String deleteUser(Map<String, String> paramMap, String userName) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.QUERY_USERS_URL + "/" + userName,
				paramMap);
	}

	/**
	 * USER RESET PASSWORD
	 */
	public String resetUserPassword(Map<String, String> paramMap, String userName) {

		return RpcDataServiceParser.getInstance().stringValue("POST",
				Urls.QUERY_USERS_URL + "/" + userName + "/reset-pwd", paramMap);
	}

	/**
	 * USER 通过User Name 获取账号 roles
	 */
	public String getRolesByUserName(Map<String, String> paramMap, String userName) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_USERS_URL + "/" + userName + "/roles",
				paramMap);
	}

	/**
	 * 创建新后台账号
	 * 
	 * @param paramMap：user-name、mobile、email、enable、roles
	 * @return
	 */
	public String putUsers(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.QUERY_USERS_URL, paramMap);
	}

	/**
	 * ROLES
	 * 
	 * @param paramMap
	 * @return jsonStr
	 */
	public String getRoles(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_ROLES_URL, paramMap);
	}

	/**
	 * ROLES
	 * 
	 * @param paramMap,
	 *            roleName
	 * @return jsonStr
	 */
	public String getRoleByRoleName(Map<String, String> paramMap, String roleName) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_ROLES_URL + "/" + roleName, paramMap);
	}

	/**
	 * ROLES
	 * 
	 * @param paramMap:
	 *            roleName、title、description、 enabled
	 * @return jsonStr
	 */
	public String putRole(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("PUT", Urls.QUERY_ROLES_URL, paramMap);
	}

	/**
	 * UPDATE ROLE BY ROLE NAME
	 * 
	 * @param paramMap
	 *            roleName
	 * @return jsonStr
	 */
	public String postRole(Map<String, String> paramMap, String roleName) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.QUERY_ROLES_URL + "/" + roleName, paramMap);
	}

	/**
	 * FIND ROLE PREMS BY ROLE NAME
	 * 
	 * @param paramMap
	 *            roleName
	 * @return jsonStr
	 */
	public String getPermsByRoleName(Map<String, String> paramMap, String roleName) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.QUERY_ROLES_URL + "/" + roleName + "/perms",
				paramMap);
	}

	/**
	 * ASSIGN PERMS TO ROLE
	 * 
	 * @param paramMap
	 *            roleName
	 * @return jsonStr
	 */
	public String assignPerms(Map<String, String> paramMap, String roleName) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.QUERY_ROLES_URL + "/" + roleName + "/perms",
				paramMap);
	}

	/**
	 * DELETE ROLE
	 * 
	 * @param paramMap
	 *            roleName
	 * @return jsonStr
	 */
	public String deleteRole(Map<String, String> paramMap, String roleName) {

		return RpcDataServiceParser.getInstance().stringValue("DELETE", Urls.QUERY_ROLES_URL + "/" + roleName,
				paramMap);
	}

	/**
	 * UPDATA PASSWORD
	 * 
	 * @param paramMap
	 * @return jsonStr
	 */
	public String changePassword(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("POST", Urls.UPDATE_PASSWORD_URL, paramMap);
	}

	public String getCurUser(Map<String, String> paramMap) {

		return RpcDataServiceParser.getInstance().stringValue("GET", Urls.CUR_USER_URL, paramMap);
	}

	public byte[] getCaptchaImage(Map<String, String> paramMap) {
		
		 return RpcDataServiceParser.getInstance().byteValue("GET",
		 Urls.CAPTCHA_IMAGE_URL, paramMap);

	}

	public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user-name", "admin");
		paramMap.put("password", "12345678");
		new RpcServiceImpl().getCaptchaImage(paramMap);
		new RpcServiceImpl().login(paramMap);
		List<Map<String, String>> list = new RpcServiceImpl().getClientMgrTreeDatas(paramMap);
		System.out.println("ok###" + list);
	}

}
