/*******************************************************************************
 * Class        ：BannerService
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.data.repository.query.Param;

//import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.cms.admin.all.dto.HomepageSearchDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingEditDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;
//import vn.com.unit.cms.admin.all.entity.Banner;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * BannerService
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
public interface HomepageService extends DocumentWorkflowCommonService<HomepageSettingEditDto, HomepageSettingEditDto> {

    /**
     * search homepage
     * 
     * @param page
     * @param homepageSearchDto
     * @return
     */
    public PageWrapper<HomepageSettingDto> search(int page, HomepageSearchDto homepageSearchDto, Locale locale);

    /**
     * get status list for searching
     * 
     * @return
     */
    public List<SelectItem> getStatusList();

    public HomepageSettingEditDto getHomepageEdit(Long homePageId, Locale locale, String businessCode);

    public List<Banner> findBannerByTypeAndDevice(String bannerType, boolean isMobile);

    public List<Banner> findBannerByTypeAndDeviceLanguage(String bannerType, boolean isMobile, String language,
            Integer status);

    public List<SelectItem> generateBannerSelectionList(List<Banner> findBannerById);

    public boolean doEdit(HomepageSettingEditDto homepageEditDto, Locale locale, HttpServletRequest request);

    /**
     * delete
     *
     * @param homepageId
     * @author TranLTH
     */
    public void delete(Long homepageId);

    /**
     * listBannerPage
     *
     * @return
     * @author TranLTH
     */
    public List<JcaConstantDto> listBannerPage();

    /**
     * approver
     *
     * @param homepageSettingEditDto
     * @author TranLTH
     */
    public void approver(HomepageSettingEditDto homepageSettingEditDto);

    public List<JcaConstantDto> listBannerPageOfEdit();

    public JcaConstantDto getBannerPageOfEditById(Long homePageId);

    public HomepageSettingDto getHomepageEditByBannerPage(String bannerPage);

    public List<HomepageSettingDto> getAllHomepage();

    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    public Long countByBannerId(Long bannerId);
}