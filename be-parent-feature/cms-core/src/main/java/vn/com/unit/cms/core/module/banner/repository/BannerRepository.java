/*******************************************************************************
 * Class        ：BannerRepository
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageSearchDto;
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.dto.resp.BannerResp;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.db.repository.DbRepository;

/**
 * BannerRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface BannerRepository extends DbRepository<Banner, Long> {

	/**
	 * countByBannerSearchDto
	 *
	 * @param condition
	 * @return int
	 * @author hand
	 */
    public int countByBannerSearchDto(@Param("searchDto") BannerSearchDto condition);

	/**
	 * findByBannerSearchDto
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param searchDto
	 * @return List<BannerLanguageSearchDto>
	 * @author hand
	 */
    public Page<BannerLanguageSearchDto> findByBannerSearchDto(@Param("searchDto") BannerSearchDto searchDto,
            Pageable pageable);

	/**
	 * find Banner by code
	 *
	 * @param code
	 * @return Banner
	 * @author hand
	 */
	Banner findByCode(@Param("code") String code);

	public List<Banner> findByTypeAndDevice(@Param("bannerType") String bannerType,
			@Param("isMobile") Boolean isMobile);

	public List<BannerLanguageDto> findByTypeAndDeviceDto(@Param("bannerType") String bannerType,
			@Param("isMobile") Boolean isMobile);

	public String getMaxBannerCode();

	public List<Banner> findByTypeAndDeviceLanguage(@Param("bannerType") String bannerType,
			@Param("isMobile") Boolean isMobile, @Param("m_language_code") String mLanguageCode,
			@Param("status") Integer status);

	public List<BannerLanguageDto> findBannerLanguageByTypeAndDeviceLanguage(@Param("bannerType") String bannerType,
			@Param("isMobile") Boolean isMobile, @Param("m_language_code") String mLanguageCode,
			@Param("status") Integer status);

	public Long countByWebsite(@Param("homepageSettingId") Long homepageSettingId);

	public Long countByWebsiteMobile(@Param("homepageSettingId") Long homepageSettingId);

	public int countDependencies(@Param("bannerId") Long bannerId, @Param("lstStatus") List<Long> lstStatus);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 23/05/2019
	 * @param bannerId
	 * @param lstStatus
	 * 
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("bannerId") Long bannerId,
			@Param("lstStatus") List<Long> lstStatus);

	String getMaxCode(@Param("tableName") String tableName, @Param("prefix") String prefix);

    public Page<BannerResp> findListBannerResp(@Param("searchDto") BannerSearchDto searchDto,
    		Pageable pageable, @Param("modeView") Integer modeView, @Param("channel") String channel);
    
    public String getAnimationSlider(@Param("searchDto") BannerSearchDto searchDto, @Param("langCode") String langCode);
    
    public String getSlideTime(@Param("searchDto") BannerSearchDto searchDto, @Param("langCode") String langCode);
    
    public BannerResp getBannerVideo(@Param("searchDto") BannerSearchDto searchDto, @Param("langCode") String langCode);
    
    public BannerResp getBannerVideoHomepage(@Param("searchDto") BannerSearchDto searchDto);


}