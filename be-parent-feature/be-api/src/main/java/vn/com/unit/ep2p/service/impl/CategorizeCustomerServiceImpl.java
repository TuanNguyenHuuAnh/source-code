package vn.com.unit.ep2p.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.categorizeCustomer.dto.*;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.cms.core.module.categorizeCustomer.entity.*;
import vn.com.unit.cms.core.module.categorizeCustomer.repository.*;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.cms.core.module.categorizeCustomer.dto.CategorizeCustomerSearchDto;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.service.CategorizeCustomerService;
import vn.com.unit.ep2p.service.impl.CategorizeCustomerServiceImpl;

/**
 * @author  lmi.quan
 * SR16451 - Enhance Phrase 3 created date 07/06/2024 - Add 4 APIs 
 *           last updated 10/06/2024
 * 			
 */
@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class CategorizeCustomerServiceImpl  implements CategorizeCustomerService {
	@Autowired
	private CategorizeCustomerRepository actRepository;
	
	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String GET_LIST_CATEGORIZE_CUSTOMER = "RPT_ODS.ADP_SP_GET_LIST_CATEGORIZE_CUSTOMER";
	private static final String GET_DETAIL_CATEGORIZE_CUSTOMER = "RPT_ODS.ADP_SP_GET_DETAIL_CATEGORIZE_CUSTOMER";
    
	/**
    * Load list of categorized customers
    * @param number
    * @return
    */
   @Override
   public CmsCommonPagination<CategorizeCustomerDto> getAllCategorizeCustomer(CategorizeCustomerSearchDto searchDto){
	   CmsCommonPagination<CategorizeCustomerDto> rs = new CmsCommonPagination<CategorizeCustomerDto>();
	   CategorizeCustomerParamDto param = new CategorizeCustomerParamDto();
	   try {
		   param.agentCode=searchDto.getAgentCode();
		   param.proposalNo=searchDto.getProposalNo();
		   param.poName=searchDto.getPoName();
		   param.partnerCode=searchDto.getPartnerCode();
		   param.status=searchDto.getStatus();
		   //param.page=searchDto.getPage();
		   //param.pageSize=searchDto.getPageSize();
		   sqlManagerDb2Service.call(GET_LIST_CATEGORIZE_CUSTOMER, param);
		   rs.setTotalData(param.lstData.size());
		   rs.setData(param.lstData);
		   //rs.setTotalData(param.totalRows);
	   } catch (Exception e) {
		   log.error("Exception", e);
	   }
	   return rs;
   }
   
   /**
    * Get detail categorized customers
    * @param agentCode, proposalNo
    * @return
    */
   @Override
   public CategorizeCustomerDto getCategorizeCustomerDetail(CategorizeCustomerSearchDto searchDto ){
	   CmsCommonPagination<CategorizeCustomerDto> rs = new CmsCommonPagination<CategorizeCustomerDto>();
	   CategorizeCustomerDetailParamDto param = new CategorizeCustomerDetailParamDto();
	   try {
		   param.agentCode=searchDto.getAgentCode();
		   param.proposalNo=searchDto.getProposalNo();
		   sqlManagerDb2Service.call(GET_DETAIL_CATEGORIZE_CUSTOMER, param);
		   rs.setTotalData(param.lstData.size());
		   rs.setData(param.lstData);
	   } catch (Exception e) {
		   log.error("Exception", e);
	   }
	   if (rs.getData().size() > 0)
		   return rs.getData().get(0);
	   else {
		   return null;
	   }
   }
   
   /**
    * Submit detail categorized customers
    * @param agentCode, proposalNo, classifyClient, classifyBusiness, Agent
    * @return List<CategorizeCustomerDto>
    */
   @Override
   @Transactional
   public int submitCategorizeCustomer( submitDto sa){
	   int result = 0;
	   try {
		// Create new record when submit
		CategorizeCustomer activityEntity = new CategorizeCustomer();
		activityEntity.setProposalNo(sa.getProposalNo());
		activityEntity.setClientCode(sa.getClientCode());
		activityEntity.setBusinessCode(sa.getBusinessCode());
		activityEntity.setAgentCode(sa.getAgentCode());
		activityEntity.setCreatedBy(sa.getCreatedBy());
		activityEntity.setClientOther(sa.getClientOther());
		activityEntity.setBusinessOther(sa.getBusinessOther());
		activityEntity.setCreatedDate(new Date());
		// Save ACTIVITY to database
		activityEntity = actRepository.save(activityEntity);
		// Save data to database successfully
		result = 1;
	   } catch (Exception e) {
		   log.error("Exception: ", e);
		   result = -1;
	   }
	   return result;
   }
   
   /**
    * Get list agent
    * @param agentCode
    * @param orgCode
    * @return List<Db2AgentDto>
    */
   @Override
   public List<Db2AgentDto> getListAgent(String orgCode){
	   List<Db2AgentDto> rs = db2ApiService.getListAgent(UserProfileUtils.getFaceMask(), orgCode);
	   return rs;
   }
}
