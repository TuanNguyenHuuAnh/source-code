/*******************************************************************************
 * Class        ：BannerLanguageService
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;

/**
 * BannerLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface BannerLanguageService {
    /**
     * save BannerLanguage
     *
     * @param bannerLanguage
     * @author hand
     */
    public void saveBannerLanguage(BannerLanguage bannerLanguage);

    /**
     * find BannerLanguagel By BannerId
     *
     * @param bid
     * @return BannerLanguage List
     * @author hand
     */
    public List<BannerLanguage> findBannerLanguageByBannerId(Long bid);

    /**
     * delete BannerLanguage by BannerId
     *
     * @param bannerId
     * @param deleteDate
     * @param deleteBy
     * @author hand
     */
    public void deleteByBannerId(Long bannerId, Date deleteDate, String deleteBy);

    /**
     * find BannerLanguage by id
     *
     * @param id
     * @return BannerLanguage
     * @author hand
     */
    public BannerLanguage findById(Long id);

    /**
     * findByBannerIdAndLangueCode
     *
     * @param bannerId
     * @param languageCode
     * @return BannerLanguage
     * @author hand
     */
    public BannerLanguageDto findByBannerIdAndLangueCode(Long bannerId);

	/**
	 * findByBannerIdAndLangueCode
	 *
	 * @param bannerId
	 * @param languageCode
	 * @return BannerLanguage
	 * @author hand
	 */
}
