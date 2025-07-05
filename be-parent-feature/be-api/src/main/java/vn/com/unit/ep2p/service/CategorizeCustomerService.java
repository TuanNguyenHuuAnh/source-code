package vn.com.unit.ep2p.service;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesResponse;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.cms.core.module.categorizeCustomer.dto.*;

/**
 * @author  lmi.quan
 * SR16451 - Enhance Phrase 3 created date 07/06/2024 - Add 4 APIs 
 *           last updated 10/06/2024
 * 			
 */
public interface CategorizeCustomerService {
	/**
     * Load list of categorized customers
     * @param agentCode, proposalNo, partnerCode, status
     * @return List<CategorizeCustomerDto>
     */
    public CmsCommonPagination<CategorizeCustomerDto> getAllCategorizeCustomer(CategorizeCustomerSearchDto searchDto);
    
    /**
     * Get detail categorized customers
     * @param agentCode, proposalNo
     * @return List<CategorizeCustomerDetailDto>
     */
    public CategorizeCustomerDto getCategorizeCustomerDetail(CategorizeCustomerSearchDto searchDto );
    /**
     * Submit detail categorized customers
     * @param agentCode, proposalNo, classifyClient, classifyBusiness, Agent
     * @return List<CategorizeCustomerDto>
     */
    public int submitCategorizeCustomer(submitDto sa);
    
    /**
     * Get List Agent
     * @param orgCode
     * @return List<Db2AhgentDto>
     */
    public List<Db2AgentDto> getListAgent(String orgCode);
}
