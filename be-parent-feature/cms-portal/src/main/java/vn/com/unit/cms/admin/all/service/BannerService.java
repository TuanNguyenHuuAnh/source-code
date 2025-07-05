/*******************************************************************************
 * Class        ：BannerService
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageSearchDto;
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;

/**
 * BannerService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface BannerService
        extends CmsCommonSearchFillterService<BannerSearchDto, BannerLanguageSearchDto, BannerEditDto> {

    /**
     * find Banner by id
     *
     * @param bid
     * @return
     * @author hand
     */
    public Banner findBannerById(Long bid);

    /**
     * delete Banner by entity
     *
     * @param banner
     * @author hand
     */
    public void deleteBanner(Banner banner);

    /**
     * get BannerEdit
     *
     * @param bannerId
     * @return BannerEditDto
     * @author hand
     */
    public BannerEditDto getBannerEdit(Long bannerId, Locale locale);

    /**
     * addOrEdit
     *
     * @param bannerEdit
     * @author hand
     * @throws IOException
     * @throws Exception
     */
    public boolean addOrEdit(BannerEditDto bannerEdit, String requestToken, HttpServletRequest request, Locale locale)
            throws IOException, Exception;

    /**
     * initLanguageList
     *
     * @param mav
     * @author hand
     */
    public void initLanguageList(ModelAndView mav);

    /**
     * find Banner by code
     *
     * @param code
     * @return Banner
     * @author hand
     */
    public Banner findByCode(String code);

    /**
     * find Banner by id
     *
     * @param id
     * @return Banner
     * @author hand
     */
//    public Banner findById(Long id);

    /**
     * @param url
     * @param request
     * @param response
     */
    public boolean requestEditorDownload(String url, HttpServletRequest request, HttpServletResponse response);

    /**
     * findBannerByTypeAndDevice
     *
     * @param bannerType
     * @param isMobile
     * @return List<Banner>
     * @author hand
     */
//    public List<Banner> findBannerByTypeAndDevice(String bannerType, boolean isMobile);

    /**
     * generateBannerSelectionList
     *
     * @param findBannerById
     * @return List<SelectItem>
     * @author hand
     */
//    public List<SelectItem> generateBannerSelectionList(String bannerType, boolean isMobile);

    /**
     * getMaxBannerCode
     *
     * @return max of Banner code
     * @author TaiTM
     */
//    public String getMaxBannerCode();

//    public List<SelectItem> generateBannerSelectionListLanguage(String bannerType, boolean isMobile, String languageCode);

    List<BannerLanguageDto> findBannerLanguageByTypeAndDeviceLanguage(String bannerType, boolean isMobile,
            String languageCode, Integer status);

    public int countDependencies(Long bannerId, List<Long> lstStatus);

    /**
     * Get list dependencies of Product categories
     * 
     * @author tranlth - 23/05/219
     * @param bannerId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map<String, String>> listDependencies(Long bannerId, List<Long> lstStatus);

//    List<CommonSearchFilterDto> initListSearchFilter(BannerSearchDto searchDto, Locale locale);
}
