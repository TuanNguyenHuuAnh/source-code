/*******************************************************************************
 * Class        ：CustomerTypeLanguageService
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.CustomerTypeLanguage;

/**
 * CustomerTypeLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public interface CustomerTypeLanguageService {

    /**
     * find CustomerTypeLanguage by typeId
     *
     * @param customerTypeId
     * @return List<CustomerTypeLanguage>
     * @author hand
     */
    public List<CustomerTypeLanguage> findByTypeId(Long customerTypeId);

    /**
     * find CustomerTypeLanguage by id
     * 
     * @return CustomerTypeLanguage
     * @author hand
     */
    public CustomerTypeLanguage findByid(Long id);

    /**
     * save entity CustomerTypeLanguage
     * 
     * @param entity
     * @author hand
     */
    public void saveCustomerTypeLanguage(CustomerTypeLanguage entity);

    /**
     * delete CustomerTypeLanguage by customer type id
     *
     * @param deleteDate
     * @param deleteBy
     * @param customerId
     * @author hand
     */
    public void deleteByTypeId(Date deleteDate, String deleteBy, Long typeId);

    /**
     * find CustomerTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */
    public CustomerTypeLanguage findByTypeIdAndLanguage(@Param("typeId") Long typeId,
            @Param("languageCode") String languageCode);
}
