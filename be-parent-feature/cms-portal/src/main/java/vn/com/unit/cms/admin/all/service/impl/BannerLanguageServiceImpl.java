/*******************************************************************************
 * Class        ：BannerLanguageServiceImpl
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.service.BannerLanguageService;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;
import vn.com.unit.cms.core.module.banner.repository.BannerLanguageRepository;

/**
 * BannerLanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BannerLanguageServiceImpl implements BannerLanguageService {

    @Autowired
    private BannerLanguageRepository bannerLanguageRepository;

    /**
     * save BannerLanguage
     *
     * @param bannerLanguage
     * @author hand
     */
    @Override
    @Transactional
    public void saveBannerLanguage(BannerLanguage bannerLanguage) {
        bannerLanguageRepository.save(bannerLanguage);
    }

    /**
     * find BannerLanguagel By BannerId
     *
     * @param bid
     * @return BannerLanguage List
     * @author hand
     */
    @Override
    public List<BannerLanguage> findBannerLanguageByBannerId(Long bid) {
        return bannerLanguageRepository.findByBannerId(bid);
    }

    /**
     * delete BannerLanguage by BannerId
     *
     * @param bannerId
     * @param deleteDate
     * @param deleteBy
     * @author hand
     */
    @Override
    @Transactional
    public void deleteByBannerId(Long bannerId, Date deleteDate, String deleteBy) {
        bannerLanguageRepository.deleteByBannerId(bannerId, deleteDate, deleteBy);
    }

    /**
     * find BannerLanguage by id
     *
     * @param id
     * @return BannerLanguage
     * @author hand
     */
    @Override
    public BannerLanguage findById(Long id) {
        return bannerLanguageRepository.findOne(id);
    }

    /**
     * findByBannerIdAndLangueCode
     *
     * @param bannerId
     * @param languageCode
     * @return BannerLanguage
     * @author hand
     */
    @Override
    public BannerLanguageDto findByBannerIdAndLangueCode(Long bannerId) {
        return bannerLanguageRepository.findByBannerIdAndLangueCode(bannerId);
    }

}
