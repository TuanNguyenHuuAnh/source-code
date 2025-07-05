/*******************************************************************************
 * Class        :CompanyRepository
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.core.repository.JcaCompanyRepository;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.CompanySearchDto;

/**
 * CompanyRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CompanyRepository extends JcaCompanyRepository {

	/**
     * countCompanyList
     * @param search
     * @return
     * @author HungHT
     */
    int countCompanyList(@Param("search") CompanySearchDto search);

	/**
     * getCompanyList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<CompanyDto> getCompanyList(@Param("search") CompanySearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    CompanyDto findById(@Param("id") Long id);

    /**
     * findByCondition
     * 
     * @param companyName
     * @param systemCode
     * @param excludeId
     * @return
     * @author HungHT
     */
    CompanyDto findByCondition(@Param("companyName") String companyName, @Param("systemCode") String systemCode, @Param("excludeId") Long excludeId);

    /**
     * getCompanyListByCompanyId
     * 
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getCompanyListByCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("companyAdmin") boolean companyAdmin, @Param("isPaging") boolean isPaging);
    
    /**
     * findByListCompanyId
     * @param companyIds
     * @param companyAdmin
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findByListCompanyId( @Param("companyIds") List<Long> companyIds,  @Param("companyAdmin") boolean companyAdmin);

	/**
	 * findListCompanyByUserRole
	 * 
	 * @param username
	 * @return list
	 * @author TuyenTD
	 */
	List<Select2Dto> findListCompanyByUserRole(@Param("user")String username);
	
	/**
	 * 
	 * findListCompanyDtoByListCompanyId
	 * @param listCompanyId
	 * @return
	 * @author taitt
	 */
	List<JcaCompany> findListCompanyByListCompanyId(@Param("companyIds")List<Long> companyIds,  @Param("companyAdmin") boolean companyAdmin);
	
//	/**
//	 * 
//	 * getAllCompany
//	 * @return
//	 * @author taitt
//	 */
//	List<ResAllCompanyDetailDto> getAllCompany(@Param("limitCompany")Integer limitCompany);
	
	/**
	 * @param companyId
	 * @return
	 */
	String findLanguageById(@Param("companyId") Long companyId);
	
}