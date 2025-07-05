package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.HomepageSearchDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingDto;
import vn.com.unit.cms.admin.all.entity.HomepageSetting;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.entity.ConstantDisplay;

public interface HomepageRepository extends DbRepository<HomepageSetting, Long> {

	public int countBySearchCondition(@Param("homepageSearchDto") HomepageSearchDto homepageSearchDto);

	public List<HomepageSettingDto> findBySearchCondition(
			@Param("homepageSearchDto") HomepageSearchDto homepageSearchDto, @Param("pageSize") int pageSize,
			@Param("offset") int offset);

	public HomepageSettingDto findById(@Param("id") Long id);

	public List<JcaConstantDto> getConstantDisplay(@Param("type") String type);

	public List<JcaConstantDto> getConstantDisplayAll(@Param("type") String type);

	public JcaConstantDto getConstantDisplayById(@Param("type") String type, @Param("homePageId") Long Id);

	public HomepageSettingDto findByBannerPage(@Param("bannerPage") String bannerPage);

	public List<HomepageSettingDto> getAllHomepage();

	public Long countByBannerId(@Param("bannerId") Long bannerId);

	/**
	 * check id in database(delete_date is not null)
	 * 
	 * @param id
	 * @return
	 */
	public HomepageSetting findByIdEntity(@Param("id") Long id);

}
