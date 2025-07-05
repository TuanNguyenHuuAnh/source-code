/*******************************************************************************
 * Class        ：CustomerTypeLanguageServiceImpl
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.entity.CustomerTypeLanguage;
import vn.com.unit.cms.admin.all.repository.CustomerTypeLanguageRepository;
import vn.com.unit.cms.admin.all.service.CustomerTypeLanguageService;

/**
 * CustomerTypeLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CustomerTypeLanguageServiceImpl implements CustomerTypeLanguageService {
    @Autowired
    private CustomerTypeLanguageRepository typeLanguageRepository;

    /**
     * find CustomerTypeLanguageb by typeId
     *
     * @param customerTypeId
     * @return
     * @author hand
     */
    @Override
    public List<CustomerTypeLanguage> findByTypeId(Long customerTypeId) {
        return typeLanguageRepository.findByTypeId(customerTypeId);
    }

    /**
     * find CustomerTypeLanguage by id
     * 
     * @return CustomerTypeLanguage
     * @author hand
     */
    @Override
    public CustomerTypeLanguage findByid(Long id) {
        return typeLanguageRepository.findOne(id);
    }

    /**
     * save entity CustomerTypeLanguage
     * 
     * @param entity
     * @author hand
     */
    @Override
    @Transactional
    public void saveCustomerTypeLanguage(CustomerTypeLanguage entity) {
        typeLanguageRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByTypeId(Date deleteDate, String deleteBy, Long typeId) {
        typeLanguageRepository.deleteByTypeId(deleteDate, deleteBy, typeId);

    }

    /**
     * find CustomerTypeLanguage by typeId and languageCode
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author hand
     */
    @Override
    public CustomerTypeLanguage findByTypeIdAndLanguage(Long typeId, String languageCode) {
        return typeLanguageRepository.findByTypeIdAndLanguageCode(typeId, languageCode);
    }
}
