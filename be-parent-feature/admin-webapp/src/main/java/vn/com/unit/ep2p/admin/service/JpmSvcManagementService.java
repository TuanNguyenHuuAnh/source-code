/*******************************************************************************
 * Class        :SvcManagementService
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementSearchDto;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;

/**
 * SvcManagementService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface JpmSvcManagementService  extends EfoFormService{   

	/**
     * getSvcManagementList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
	 * @throws DetailException 
     */
    PageWrapper<SvcManagementDto> getSvcManagementList(SvcManagementSearchDto search, int pageSize, int page, String lang) throws DetailException;

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    SvcManagementDto findById(Long id, String lang);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    SvcManagementDto findByProperties1(String properties1);

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
	 * @throws AppException 
     */
    void initScreenDetail(ModelAndView mav, SvcManagementDto objectDto, Locale locale) throws AppException;

	/**
     * saveSvcManagement
     * @param objectDto
     * @param locale
     * @return
     * @author HungHT
     */
    ResultDto saveSvcManagement(SvcManagementDto objectDto, Locale locale) throws Exception;

	/**
     * deleteSvcManagement
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteSvcManagement(Long id);
    
    /**
     * getBusinessList
     * 
     * @param keySearch
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getBusinessList(String keySearch, Long companyId, boolean isPaging);
    

    /**
     * getOZFormFileName
     * 
     * @param svcManagementDto
     * @return
     * @author HungHT
     * @throws AppException 
     */
    List<Select2Dto> getOZFormFileName(SvcManagementDto svcManagementDto) throws AppException;
}