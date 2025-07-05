/*******************************************************************************
 * Class        :CaManagementService
 * Created date :2019/08/26
 * Lasted date  :2019/08/26
 * Author       :HungHT
 * Change log   :2019/08/26:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.service.JcaCaManagementService;

/**
 * CaManagementService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CaManagementService extends JcaCaManagementService{   

	/**
     * getCaManagementList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    PageWrapper<JcaCaManagementDto> getCaManagementList(JcaCaManagementSearchDto search, int pageSize, int page);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    JcaCaManagementDto findById(Long id);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    JcaCaManagementDto findByProperties1(String properties1);

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    void initScreenDetail(ModelAndView mav, JcaCaManagementDto objectDto, Locale locale);

	/**
     * saveCaManagement
     * @param objectDto
     * @return
     * @author HungHT
	 * @throws Exception 
     */
    JcaCaManagement saveCaManagement(JcaCaManagementDto objectDto) throws Exception;

	/**
     * deleteCaManagement
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteCaManagement(Long id);
    
    /**
     * findByAccountId
     * @param accountId
     * @return CaManagementDto
     * @author KhuongTH
     */
    JcaCaManagementDto findByAccountId(Long accountId);
    
    /**
     * getDefaultByCompanyIḍ
     * @param companyId
     * @return CaManagementDto
     * @author KhuongTH
     */
    JcaCaManagementDto getDefaultByCompanyId(Long companyId);
}