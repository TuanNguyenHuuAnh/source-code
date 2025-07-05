/*******************************************************************************
 * Class        ：BannerLanguageRepository
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * BannerLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface BannerLanguageRepository extends DbRepository<BannerLanguage, Long> {

    /**
     * find BannerLanguage List by BannerId
     *
     * @param bannerId
     * @return BannerLanguage List
     * @author hand
     */
    public List<BannerLanguage> findByBannerId(@Param("bannerId") Long bannerId);

    /**
     * delete BannerLanguage by BannerId
     *
     * @param bannerId
     * @param deleteBy
     * @author hand
     */
    @Modifying
    public void deleteByBannerId(@Param("bannerId") Long bannerId, @Param("deleteDate") Date deleteDate,
            @Param("deleteBy") String deleteBy);

    /**
     * findByBannerIdAndLangueCode
     *
     * @param bannerId
     * @param languageCode
     * @return BannerLanguage
     * @author hand
     */
    public BannerLanguageDto findByBannerIdAndLangueCode(@Param("bannerId") Long bannerId);
}
