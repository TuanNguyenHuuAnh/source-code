/*******************************************************************************
 * Class        FormServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       NhanNV
 * Change log   2016/06/21 01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.core.efo.repository.EfoFormRepository;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;


/**
 * FormServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoFormServiceImpl implements EfoFormService {

    /** The efo form repository. */
    @Autowired
    private EfoFormRepository efoFormRepository;
    
    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;
    
    /** The jca company service. */
    @Autowired
    private JcaCompanyService jcaCompanyService;
    
    /** The jca item service. */
    @Autowired
    private JcaItemService jcaItemService;

     
    /**
     * Get Form by Form Id.
     *
     * @param id
     *            Id of Form
     * @return Form object
     * @author NhanNV
     */
    @Override
    public EfoForm getFormById(Long id) {
        return efoFormRepository.findOne(id);
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#getEfoFormDtoByCondition(vn.com.unit.core.dto.EfoFormSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override   
    public List<EfoFormDto> getEfoFormDtoByCondition(EfoFormSearchDto efoFormSearchDto, Pageable pageable) {
        String langCode = UserProfileUtils.getLanguage();
        Long accountId = UserProfileUtils.getAccountId();

        String dbType = "SQLSERVER"; // need get from config

        return efoFormRepository.getEfoFormDtoByCondition(efoFormSearchDto, pageable, langCode, accountId, dbType).getContent();
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#countEfoFormByCondition(vn.com.unit.core.dto.EfoFormSearchDto)
     */
    @Override    
    public int countEfoFormByCondition(EfoFormSearchDto efoFormSearchDto){
        Long accountId = UserProfileUtils.getAccountId();
        return efoFormRepository.countEfoFormByCondition(efoFormSearchDto, accountId);
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#getSelect2DtoWithEfoFormByCompanyId(java.lang.String, java.lang.Long)
     */
    @Override
    public List<Select2Dto> getSelect2DtoWithEfoFormByCompanyId(String keySearch, Long companyId) {
        Long accountId = UserProfileUtils.getAccountId();

        String dbType = "SQLSERVER"; // need get from config

        return efoFormRepository.getSelect2DtoWithEfoFormByCompanyId(companyId, keySearch,accountId, dbType);
    }
    
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#saveEfoForm(vn.com.unit.core.entity.EfoForm)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoForm saveEfoForm(EfoForm efoForm) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = efoForm.getId();
        if(null != id) {
            EfoForm oldEfoForm =  efoFormRepository.findOne(id);
            if (null !=oldEfoForm) {
                efoForm.setCreatedDate(oldEfoForm.getCreatedDate());
                efoForm.setCreatedId(oldEfoForm.getCreatedId());
                efoForm.setUpdatedDate(sysDate);
                efoForm.setUpdatedId(userId);
                efoFormRepository.update(efoForm);
            }
            
        }else {
            efoForm.setCreatedDate(sysDate);
            efoForm.setCreatedId(userId);
            efoForm.setUpdatedDate(sysDate);
            efoForm.setUpdatedId(userId);
            efoFormRepository.create(efoForm);
        }
        return efoForm;
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#saveEfoFormDto(vn.com.unit.core.dto.EfoFormDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoForm saveEfoFormDto(EfoFormDto efoFormDto) {
        EfoForm efoForm = objectMapper.convertValue(efoFormDto, EfoForm.class);
        efoForm.setId(efoFormDto.getFormId());
        
        // save data
        efoForm = this.saveEfoForm(efoForm);
        
        // update id
        efoFormDto.setFormId(efoForm.getId());
        return efoForm;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.EfoFormService#getEfoFormDtoById(java.lang.Long)
     */
    @Override
    public EfoFormDto getEfoFormDtoById(Long id) {
        return efoFormRepository.getEfoFormDtoById(id);
    }
    
    @Override
    public List<EfoFormDto> getEfoFormDtoByCompanyIdAndFileName(Long companyId,List<String> fileNameList) {
        return efoFormRepository.getEfoFormDtoByCompanyIdAndFileName(companyId, fileNameList);
    }
    
    @Override
    public EfoFormDto getEfoFormDtoByCompanyIdAndEfoFormName(Long companyId, String efoFormName, Long currentFormId) {
        return efoFormRepository.getEfoFormDtoByCompanyIdAndEfoFormName(companyId, efoFormName, null);
    }

    
    /**
     * <p>
     * Creates the item.
     * </p>
     *
     * @param itemCode
     *            type {@link String}
     * @param itemName
     *            type {@link String}
     * @param suffix
     *            type {@link String}
     * @param businessId
     *            type {@link Long}
     * @param description
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @param subType
     *            type {@link String}
     * @return {@link JcaItem}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaItem createItem(String itemCode, String itemName, String suffix, Long businessId, String description, Long companyId,
            String subType) throws DetailException {
        JcaItem item = null;
        String system = jcaCompanyService.getSystemCodeByCompanyId(companyId);
        String code = system.concat(CoreConstant.HASHTAG).concat(itemCode);
        String name = itemName.concat(suffix);
        try {
            int index = 0;
            do {
                index += 1;
                if (index > 10) {
                    throw new Exception();
                }
                // Check exists item
                Boolean isExists = jcaItemService.checkExitsItemByItemCodeAndCompanyId(code, companyId);
                if (isExists == null || !isExists) {
                    item = new JcaItem();
                    item.setFunctionCode(code);
                    item.setFunctionName(name);
                    item.setFunctionType("3");
                    item.setCompanyId(1L); //TODO item.setCompanyId(UserProfileUtils.getCompanyId());
                    item.setDescription(description);
//                    item.setBusinessId(businessId);
//                    item.setSubType(subType);
//                    item.setDisplayOrder(itemManagementService.getMaxDisplayOrder());
//                    item.setCreatedBy(UserProfileUtils.getUserNameLogin());
//                    item.setCreatedDate(comService.getSystemDateTime());
                    item.setCompanyId(companyId);
//                    item = itemRepository.save(item);
                    jcaItemService.saveJcaItem(item);
                    break;
                } else {
                    code = system.concat(CoreConstant.HASHTAG).concat(itemCode).concat(String.valueOf(index));
                    name = itemName.concat(String.valueOf(index)).concat(suffix);
                }
            } while (null == item);
        } catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301804_CORE_REGISTER_FORM_CREATE_FUNCTION_FAIL, new String[] { itemName },true);
        }
        return item;
    }
    
    @Override
    public Long findMaxDisplayOrderByCompanyId(Long companyId){
        return efoFormRepository.findMaxDisplayOrderByCompanyId(companyId) + 1;
    }
   
}
