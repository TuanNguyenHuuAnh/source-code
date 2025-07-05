/*******************************************************************************
 * Class        ：DiscountPaymentServiceImpl
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.DiscountPaymentTypeDto;
import vn.com.unit.cms.admin.all.dto.DiscountPaymentTypeLanguageDto;
import vn.com.unit.cms.admin.all.entity.DiscountPaymentType;
import vn.com.unit.cms.admin.all.entity.DiscountPaymentTypeLanguage;
import vn.com.unit.cms.admin.all.repository.DiscountPaymentTypeLanguageRepository;
import vn.com.unit.cms.admin.all.repository.DiscountPaymentTypeRepository;
import vn.com.unit.cms.admin.all.service.DiscountPaymentService;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.service.LanguageService;

/**
 * DiscountPaymentServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class DiscountPaymentServiceImpl implements DiscountPaymentService{
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    LanguageService languageService;
    
    @Autowired
    DiscountPaymentTypeRepository discountPaymentTypeRepository;
    
    @Autowired
    DiscountPaymentTypeLanguageRepository discountPaymentTypeLanguageRepository;
    

    @Override
    public DiscountPaymentTypeDto getDiscountPaymentTypeEditModel(Integer id) {
        return this.getPaymentTypeEditDto(id);
    }

    @Override
    public DiscountPaymentTypeDto initPaymentTypeAddModel() {
        DiscountPaymentTypeDto discountPaymentType = new DiscountPaymentTypeDto();
        Integer maxSort = discountPaymentTypeRepository.findMaxSort();
        discountPaymentType.setSortOrder(maxSort == null? 0: maxSort + 1);
        initInfoByLanguages(discountPaymentType);
        return discountPaymentType;
    }

    /**
     * @param discountPaymentType
     */
    private void initInfoByLanguages(DiscountPaymentTypeDto discountPaymentType) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        List<DiscountPaymentTypeLanguageDto> infoByLanguages = new ArrayList<DiscountPaymentTypeLanguageDto>();
        for(LanguageDto language : languageList){
            DiscountPaymentTypeLanguageDto discountPaymentTypeLanguageDto = initInfoByLanguage(language);
            infoByLanguages.add(discountPaymentTypeLanguageDto);
        }
        discountPaymentType.setInfoByLanguages(infoByLanguages);
    }

    /**
     * @param language
     * @return
     */
    private DiscountPaymentTypeLanguageDto initInfoByLanguage(LanguageDto language) {
        DiscountPaymentTypeLanguageDto discountPaymentTypeLanguageDto = new DiscountPaymentTypeLanguageDto();
        discountPaymentTypeLanguageDto.setLanguageCode(language.getCode());
        discountPaymentTypeLanguageDto.setLanguageDispName(language.getName());
        return discountPaymentTypeLanguageDto;
    }
    
//    /**
//     * TODO
//     * DUMP
//     * @param discountPaymentType
//     */
//    private void dumpInfoByLanguages(DiscountPaymentTypeDto discountPaymentType){
//        initInfoByLanguages(discountPaymentType);
//    }

    @Override
    public DiscountPaymentTypeDto saveAddPaymentType(DiscountPaymentTypeDto discountPaymentTypeDto) {
        
        DiscountPaymentType entity = discountPaymentTypeDto.createEntity();
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        entity.setCreateDate(new Date());
        entity.setCreateBy(UserProfileUtils.getUserNameLogin());
        entity = discountPaymentTypeRepository.save(entity);
        discountPaymentTypeDto.setId(entity.getId());
        saveLanguagesInfoPaymentType(discountPaymentTypeDto);
        return getPaymentTypeEditDto(entity.getId());
    }
    
    private DiscountPaymentTypeDto getPaymentTypeEditDto(Integer paymentTypeId){
        DiscountPaymentType entity = discountPaymentTypeRepository.findOne(paymentTypeId);
        List<DiscountPaymentTypeLanguage> infosByLanguage = discountPaymentTypeLanguageRepository.findByPaymentTypeId(paymentTypeId); 
        HashMap<String, DiscountPaymentTypeLanguage> infoByLanguageMap = new HashMap<>();
        for(DiscountPaymentTypeLanguage info : infosByLanguage){
            infoByLanguageMap.put(info.getLanguageCode(), info);
        }
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        List<DiscountPaymentTypeLanguageDto> infoByLanguageDtos = new ArrayList<>();
        for(LanguageDto languageDto : languageList){
            DiscountPaymentTypeLanguage infoByLanguage = infoByLanguageMap.get(languageDto.getCode());
            if(infoByLanguage != null){
                DiscountPaymentTypeLanguageDto infoByLanguageDto = new DiscountPaymentTypeLanguageDto(infoByLanguage);
                infoByLanguageDto.setLanguageDispName(languageDto.getName());
                infoByLanguageDtos.add(infoByLanguageDto);
            }else{
                DiscountPaymentTypeLanguageDto infoByLanguageDto = initInfoByLanguage(languageDto);
                infoByLanguageDto.setLanguageDispName(languageDto.getName());
                infoByLanguageDtos.add(infoByLanguageDto);
            }
        }
        DiscountPaymentTypeDto discountPaymentTypeDto = new DiscountPaymentTypeDto(entity);
        discountPaymentTypeDto.setInfoByLanguages(infoByLanguageDtos);
        return discountPaymentTypeDto;
    }

    /**
     * @param discountPaymentTypeDto
     */
    private void saveLanguagesInfoPaymentType(DiscountPaymentTypeDto discountPaymentTypeDto) {
        for(DiscountPaymentTypeLanguageDto languageInfoDto : discountPaymentTypeDto.getInfoByLanguages()){
            DiscountPaymentTypeLanguage languageInfoEntity = languageInfoDto.createEntity();
//            UserProfile userProfile = UserProfileUtils.getUserProfile();
            languageInfoEntity.setCreateBy(UserProfileUtils.getUserNameLogin());
            languageInfoEntity.setCreateDate(new Date());
            languageInfoEntity.setDiscountPaymentTypeId(discountPaymentTypeDto.getId());
            discountPaymentTypeLanguageRepository.save(languageInfoEntity);
        }
    }

    @Override
    public DiscountPaymentTypeDto saveUpdatePaymentType(DiscountPaymentTypeDto discountPaymentTypeDto) {
        DiscountPaymentType entity = discountPaymentTypeDto.createEntity();
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        entity.setUpdateDate(new Date());
        entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
        entity = discountPaymentTypeRepository.save(entity);
        discountPaymentTypeDto.setId(entity.getId());
        saveLanguagesInfoPaymentType(discountPaymentTypeDto);
        return getPaymentTypeEditDto(entity.getId());
    }

    @Override
    public int getPaymentCountByCode(String code) {
        return discountPaymentTypeRepository.countByCode(code);
    }

    @Override
    public PageWrapper<DiscountPaymentTypeDto> searchDiscountPaymentTypeList(int page, CommonSearchDto searchDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<DiscountPaymentTypeDto> pageListWrapper = new PageWrapper<>(1, sizeOfPage);
        pageListWrapper.setData(new ArrayList<>());
        return pageListWrapper;
    }
}
