/*******************************************************************************
 * Class        LanguageService
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       NhanNV
 * Change log   2017/02/1601-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;

// TODO: Auto-generated Javadoc
/**
 * LanguageService.
 *
 * @author NhanNV
 * @version 01-00
 * @since 01-00
 */
public interface LanguageService {

    /**
     * find all Language.
     *
     * @author NhanNV
     * @return Language List
     */
    public List<Language> findAllActive();
    
    /**
     * get data language for select box .
     *
     * @author NhanNV
     * @return LanguageDto List
     */
    public List<LanguageDto> getLanguageDtoList();
    
    /**
     * findByCode.
     *
     * @author HungHT
     * @param lang the lang
     * @return the language dto
     */
    public LanguageDto findByCode(String lang);
    
    /**
     * findByCode.
     *
     * @author HungHT
     * @param companyId the company id
     * @param code the code
     * @return the language dto
     */
    public LanguageDto findByCompanyIdAndCode(Long companyId, String code);
    
    /**
     * getList.
     *
     * @return the list
     */
    List<LanguageDto> getList();
    
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the language dto
     */
    public List<LanguageDto> getListByCompanyId(Long companyId);


    
}
