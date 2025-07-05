/*******************************************************************************
 * Class        :JcaCaManagementServiceImpl
 * Created date :2019/08/26
 * Lasted date  :2019/08/26
 * Author       :HungHT
 * Change log   :2019/08/26:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.enumdef.param.JcaCaManagementSearchEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.impl.JcaCaManagementServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.repository.CaManagementRepository;
import vn.com.unit.ep2p.admin.service.CaManagementService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.constant.CommonConstant;

/**
 * JcaCaManagementServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CaManagementServiceImpl extends JcaCaManagementServiceImpl implements CaManagementService {  

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CaManagementRepository caManagementRepository;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    CommonService comService;

	// Model mapper
	ModelMapper modelMapper = new ModelMapper();

    /**
     * getJcaCaManagementList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    public PageWrapper<JcaCaManagementDto> getCaManagementList(JcaCaManagementSearchDto search, int pageSize, int page) {
        List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaCaManagementDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        
        setSearchParm(search);
        
        int count = caManagementRepository.countCaManagementList(search);
        List<JcaCaManagementDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = caManagementRepository.getCaManagementList(search, startIndex, sizeOfPage);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    public JcaCaManagementDto findById(Long id) {
        return caManagementRepository.findById(id);
    }

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    public JcaCaManagementDto findByProperties1(String properties1) {
        return caManagementRepository.findByProperties1(properties1);
    }

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, JcaCaManagementDto objectDto, Locale locale) {
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        if(objectDto.getCompanyId()==null) {
            objectDto.setCompanyId(UserProfileUtils.getCompanyId()); 
        }
    }

	/**
     * saveJcaCaManagement
     * @param objectDto
     * @return
     * @author HungHT
	 * @throws Exception 
     */
    public JcaCaManagement saveCaManagement(JcaCaManagementDto objectDto) throws Exception {        
        JcaCaManagement objectResult = null;
		Date sysDate = comService.getSystemDateTime();
        Long user = UserProfileUtils.getAccountId();
        String password = objectDto.getCaPassword();
        JcaCaManagement objectSave = modelMapper.map(objectDto, JcaCaManagement.class);
        objectSave.setCaDefault(objectDto.isCaDefault());
        
        String passwordEncrypt = null;
        if (StringUtils.isNotBlank(password)){
            passwordEncrypt = JCanaryPasswordUtil.encryptString(password);
        }
        if (objectSave != null) {            
            if (objectSave.getId() == null) {
                objectSave.setCreatedId(user);
                objectSave.setCreatedDate(sysDate);
                objectSave.setCaPassword(passwordEncrypt);
            } else {
                JcaCaManagement objectCurrent = caManagementRepository.findOne(objectDto.getCaManagementId());
                if (!CommonConstant.PASSWORD_ENCRYPT.equals(password)) {
                    objectSave.setCaPassword(passwordEncrypt);
                }else {
                    objectSave.setCaPassword(objectCurrent.getCaPassword());
                }
                objectSave.setCreatedId(objectCurrent.getCreatedId());
                objectSave.setCreatedDate(objectCurrent.getCreatedDate());
                objectSave.setUpdatedId(user);
                objectSave.setUpdatedDate(sysDate);
            }
            objectResult = caManagementRepository.save(objectSave);
            //update caDefault
            if(objectResult.isCaDefault()) {
                caManagementRepository.updateDefaultByCompanyId(objectResult.getCompanyId(), objectResult.getId());
            }
        }
        return objectResult;
    }

	/**
     * deleteJcaCaManagement
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteCaManagement(Long id) {
		Date sysDate = comService.getSystemDateTime();
        Long user = UserProfileUtils.getAccountId();
        JcaCaManagement object = caManagementRepository.findOne(id);
        if(object!= null) {
            object.setDeletedId(user);
            object.setDeletedDate(sysDate);
            caManagementRepository.save(object);
        }
        return true;
    }

    /**
     * @author KhuongTH
     */
    @Override
    public JcaCaManagementDto findByAccountId(Long accountId) {
        return caManagementRepository.findByAccountId(accountId);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public JcaCaManagementDto getDefaultByCompanyId(Long companyId) {
        return caManagementRepository.getDefaultByCompanyId(companyId);
    }
    
    private void setSearchParm(JcaCaManagementSearchDto searchDto) {
        if (null == searchDto.getFieldValues()) {
            searchDto.setFieldValues(new ArrayList<String>());
        }

        if (searchDto.getFieldValues().isEmpty()) {
            searchDto.setCaName(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
            searchDto.setAccountName(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
            searchDto.setCaSlot(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
        } else {
            for (String field : searchDto.getFieldValues()) {
                if (StringUtils.equals(field, JcaCaManagementSearchEnum.CA_NAME.name())) {
                    searchDto.setCaName(searchDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, JcaCaManagementSearchEnum.ACCOUNT_NAME.name())) {
                    searchDto.setAccountName(searchDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, JcaCaManagementSearchEnum.CA_SLOT.name())) {
                    searchDto.setCaSlot(searchDto.getFieldSearch().trim());
                    continue;
                }
            }
        }

        if ( 0 == searchDto.getCompanyId() && CommonCollectionUtil.isEmpty(searchDto.getCompanyIdList()) ) {
        	List<Select2Dto> companyListSelect2 = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        	List<Long> companyList = companyListSelect2.stream().map(Select2Dto::getId).map(Long::valueOf).collect(Collectors.toList());
        	searchDto.setCompanyIdList(companyList);
        }

        // Add company_id
//        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
    }



}