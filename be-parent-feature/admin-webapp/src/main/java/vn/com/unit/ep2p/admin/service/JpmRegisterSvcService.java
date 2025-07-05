/*******************************************************************************
 * Class        :RegisterSvcService
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.ep2p.admin.dto.ResReportList;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;
import vn.com.unit.ep2p.dto.PPLRegisterSvcSearchDto;

/**
 * RegisterSvcService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface JpmRegisterSvcService {   
    
    /**
     * getRegisterSvcList
     * @param search
     * @return
     * @author HungHT
     */
    ResReportList getRegisterSvcList(PPLRegisterSvcSearchDto search) throws AppException;
    
    /**
     * registerSvc
     * @param register
     * @param locale
     * @return
     * @author HungHT
     */
    ResultDto registerSvc(PPLRegisterSvcSearchDto register, Locale locale) throws Exception;
    
    /**
     * getComponentListfromOZRepository
     * 
     * @param formId
     * @param formName
     * @param formFileName
     * @param formOzCategory
     * @param companyId
     * @return
     * @throws AppException
     * @author HungHT
     */
    List<EfoComponent> getComponentListfromOZRepository(Long formId, String formName, String formFileName, String formOzCategory, Long companyId) throws AppException;
    
    /**
     * saveComponent
     * 
     * @param componentList
     * @param formName
     * @throws AppException
     * @author HungHT
     */
    void saveComponent(List<EfoComponent> componentList, String formName) throws AppException ;
}