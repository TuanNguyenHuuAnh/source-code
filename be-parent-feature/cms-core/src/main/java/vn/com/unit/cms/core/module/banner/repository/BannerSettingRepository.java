package vn.com.unit.cms.core.module.banner.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingSearchDto;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;

/**
 * BannerSettingRepository
 *
 * @version 01-00
 * @since 01-00
 * @author longdch
 */
public interface BannerSettingRepository extends DbRepository<HomePageSetting, Long> {

	/**
	 * countByBannerSearchDto
	 *
	 * @param condition
	 * @return int
	 * @author longdch
	 */
	public int countByBannerSearchDto(@Param("searchDto") BannerSettingDto condition);

	/**
	 * findByBannerSearchDto
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param searchDto
	 * @return List<BannerSettingSearchDto>
	 * @author longdch
	 */

	public Page<BannerSettingSearchDto> findByBannerSearchDto(@Param("searchDto") BannerSettingDto searchDto,
			Pageable pageable);

	/**
	 * find BannerPage by GROUP_CODE
	 *
	 * @param bannerPage
	 * @return
	 * @author longdch
	 */
	public List<Select2Dto> findByBannerPage(@Param("bannerPage") String bannerPage, @Param("lang") String lang);

	public HomePageSetting findByCode(@Param("code") String code);

	public String getMaxCode(@Param("tableName") String tableName, @Param("prefix") String prefix);

	public HomePageSetting findDateInto(@Param("bannerPage") String bannerPage, @Param("bannerType") String bannerType,
			@Param("homgpageId") Long id, @Param("homgpageId") String channel);

}
