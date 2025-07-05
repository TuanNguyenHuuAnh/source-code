/**
 * 
 */
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.ServiceDetailDto;
import vn.com.unit.cms.admin.all.dto.ServiceDto;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageDto;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageSearchDto;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.dto.LanguageDto;

/**
 * @author tungns <tungns@unit.com.vn>
 *
 */
public interface ServiceService {

	/**
	 * @param page
	 * @param typeSearch
	 * @return
	 */
	public PageWrapper<ServiceLanguageSearchDto> findAll(int page, ServiceLanguageSearchDto serviceSearchDto, String langCode);

	/**
	 * @param id
	 * @param upperCase
	 * @return
	 */
	public ServiceLanguageSearchDto getServiceLanguageDto(Long id, String langCode);

	/**
	 * @param id
	 * @return
	 */
	public ServiceDto getServiceDto(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<ServiceLanguageDto> getServiceLanguageDtoList(Long id);

	/**
	 * @param code
	 * @return
	 */
	public ServiceDto getServiceByCode(String code);

	/**
	 * @param termDto
	 * @throws IOException 
	 */
	public void submitObject(ServiceDto serviceDto, String langCode, String requestToken) throws IOException;

	/**
	 * @param id
	 */
	public void deleteObject(Long id);

	public List<CustomerTypeDto> getCustomerTypeList(Locale locale);

    /**
     * @param serviceEdit
     * @return
     */
    public ServiceDto addNewDetail(ServiceDto serviceEdit, List<LanguageDto> languageList);

    /**
     * @param imageUrl
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean requestDownloadImage(String imageUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    
    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    /**
     * @param id
     * @param upperCase
     * @return
     */
    public List<ServiceDetailDto> getServiceDetailList(Long id, String langCode);

    /**
     * @param detailID
     * @return
     */
    public void deleteServiceDetail(Long detailID);
}
