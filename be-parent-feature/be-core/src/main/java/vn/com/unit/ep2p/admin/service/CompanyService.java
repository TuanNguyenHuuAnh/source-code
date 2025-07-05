/*******************************************************************************
 * Class        :CompanyService
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.CompanySearchDto;

/**
 * CompanyService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CompanyService {   

	/**
     * getCompanyList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
	 * @throws DetailException 
     */
    PageWrapper<JcaCompanyDto> getCompanyList(CompanySearchDto search, int pageSize, int page) throws DetailException;

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    CompanyDto findById(Long id);

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    void initScreenDetail(ModelAndView mav, CompanyDto objectDto, Locale locale);

    /**
     * saveCompany
     * 
     * @param objectDto
     * @param locale
     * @return
     * @throws Exception
     * @author HungHT
     */
    ResultDto saveCompany(CompanyDto objectDto, Locale locale) throws Exception;

	/**
     * deleteCompany
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteCompany(Long id);

    /**
     * getCompanyListByCompanyId
     * 
     * @param term
     * @param companyId
     * @param companyAdmin
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getCompanyListByCompanyId(String term, Long companyId, boolean companyAdmin, boolean isPaging);
    
    /**
     * getSystemCodeByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    String getSystemCodeByCompanyId(Long companyId);
    
    /**
     * getLimitNumberUsers
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    Long getLimitNumberUsers(Long companyId);
    
    /**
     * getLimitNumberTransaction
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    Long getLimitNumberTransaction(Long companyId);
    
    /**
     * findBySystemCode
     * 
     * @param systemCode
     * @return
     * @author HungHT
     */
    CompanyDto findBySystemCode(String systemCode);
    
    /**
     * findByListCompanyId
     * @param companyId
     * @param companyAdmin
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findByListCompanyId(List<Long> companyIds, boolean companyAdmin);
    
    /**
     * findListCompanyByUserRole
     * 
     * @param username
     * @return list
     * @author TuyenTD
     */
    List<Select2Dto> findListCompanyByUserRole(String username);
    
    /**
     * 
     * findCompanyByListId
     * @param companyIds
     * @param companyAdmin
     * @return
     * @author taitt
     */
    List<JcaCompany> findCompanyByListId(List<Long> companyIds, boolean companyAdmin);
    
    /**
     * 
     * getAllCompany
     * @param companyIds
     * @param companyAdmin
     * @return
     * @author taitt
     */
//    List<ResAllCompanyDetailDto> getAllCompany(Integer limitCompany);
    
    /**
     * findAll
     * 
     * @return
     * @author HungHT
     */
    Iterable<JcaCompany> findAll();
    
    /**
     * @param companyId
     * @return
     * @throws SQLException
     */
    String getLanguageById(Long companyId) throws SQLException;
}