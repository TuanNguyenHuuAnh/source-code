package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.dto.BranchEditDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchResultDto;
import vn.com.unit.cms.admin.all.entity.Branch;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.CmsBranchRepository;
import vn.com.unit.cms.admin.all.service.CmsBranchService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsBranchServiceImpl implements CmsBranchService {


    private static final Logger logger = LoggerFactory.getLogger(CmsBranchServiceImpl.class);

    @Autowired
    private Db2Service db2Service;

    @Autowired
    public CmsBranchRepository branchRepository;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private MessageSource msg;

    @Autowired
    private CmsCommonService comService;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
    private Select2DataService select2DataService;

    @Override
    public List<BranchSearchResultDto> getListByCondition(BranchSearchDto searchDto, Pageable pageable) {
        return branchRepository.findBranchList(searchDto, pageable, "VI").getContent();
    }

    @Override
    public int countListByCondition(BranchSearchDto searchDto) {
        return branchRepository.countBranch(searchDto);
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public CmsCommonService getCmsCommonService() {
        return comService;
    }

    @Override
    public JcaDatatableConfigService getJcaDatatableConfigService() {
        return jcaDatatableConfigService;
    }

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(BranchSearchDto searchDto, Locale locale) {

        List<CommonSearchFilterDto> list = CmsBranchService.super.initListSearchFilter(searchDto, locale);
        List<CommonSearchFilterDto> rs = new ArrayList<CommonSearchFilterDto>();

        List<Select2Dto> listDataType = select2DataService.getConstantData("BRANCH_TYPE", "BRANCH_TYPE", locale.toString());
        List<Select2Dto> listDataCity = db2Service.getCity();
        List<Select2Dto> listDataDistrict = db2Service.getDistrictByCity(null,null);
        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonSearchFilterDto filter : list) {
                if ("typeName".equals(filter.getField())) {
                    CommonSearchFilterDto branchType = commonSearchFilterUtils.createSelectCommonSearchFilterDto("typeName",
                            msg.getMessage("branch.type", null, locale), searchDto.getType(), listDataType);
                    rs.add(branchType);
                }
                else if ("city".equals(filter.getField())) {
                    CommonSearchFilterDto branchCity = commonSearchFilterUtils.createSelectCommonSearchFilterDto("city",
                            msg.getMessage("branch.city", null, locale), searchDto.getCityName(), listDataCity);
                    rs.add(branchCity);
                }
                else if ("district".equals(filter.getField())) {
                    CommonSearchFilterDto branchDistrict = commonSearchFilterUtils.createSelectCommonSearchFilterDto("district",
                            msg.getMessage("branch.district", null, locale), searchDto.getDistrictName(), listDataDistrict);
                    rs.add(branchDistrict);
                }
                else {
                    rs.add(filter);
                }
            }
        }


        return rs;
    }

    @Override
    public BranchEditDto getEditDtoById(Long id, Locale locale) {

        return getBranchEdit(id, locale);
    }

    @Override
    public void saveOrUpdate(BranchEditDto editDto, Locale locale) throws Exception {
        addOrEdit(editDto);

    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        Branch branch = branchRepository.findOne(id);
        deleteBranch(branch);

    }

    @Override
    public List<BranchSearchResultDto> getListForSort(BranchSearchDto searchDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateSortAll(BranchSearchDto searchDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getEnumColumnForExport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTemplateNameForExport(Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Select2Dto> getAlldistrict(String city, String lang) {
        return branchRepository.findDistrict(city, lang);
    }

    @Override
    public List<Select2Dto> getAllRegion(String lang) {
        return branchRepository.findRegion(lang);
    }

    @Override
    public BranchEditDto getBranchEdit(Long id, Locale locale) {
        BranchEditDto resultDto = new BranchEditDto();

        if (id == null) {
            return resultDto;
        }

        Branch branch = branchRepository.findOne(id);

        if (branch == null || branch.getDeleteDate() != null) {
            throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
        }
        if (branch != null) {
            resultDto.setName(branch.getName());
            resultDto.setId(branch.getId());
            resultDto.setType(branch.getType());
            resultDto.setCity(branch.getCity());
            resultDto.setDistrict(branch.getDistrict());
            resultDto.setAddress(branch.getAddress());
            resultDto.setLongtitude(branch.getLongtitude());
            resultDto.setLatitude(branch.getLatitude());
            resultDto.setPhone(branch.getPhone());
            resultDto.setEmail(branch.getEmail());
            resultDto.setFax(branch.getFax());
            resultDto.setNote(branch.getNote());
            resultDto.setWorkingHours(branch.getWorkingHours());
            resultDto.setActiveFlag(branch.getActiveFlag());
        }
        return resultDto;
    }

    @Override
    public Branch findByCode(String code) {

        return branchRepository.findByCode(code);
    }

    @Override
    public void addOrEdit(BranchEditDto branchEditDto) {

        String userName = UserProfileUtils.getUserNameLogin();

        createOrEditBranch(branchEditDto, userName);
    }

    private void createOrEditBranch(BranchEditDto branchEditDto, String userName) {

        Branch entity = new Branch();

        if (null != branchEditDto.getId()) {
            entity = branchRepository.findOne(branchEditDto.getId());

            if (null == entity) {
                throw new BusinessException("Not found Branch with id=" + branchEditDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }

        entity.setCode(branchEditDto.getCode());
        entity.setName(branchEditDto.getName());
        entity.setType(branchEditDto.getType());

        // GET NAME CITY
        String city = db2Service.getNameCity(branchEditDto.getCity());
        entity.setCityName(city);
        entity.setCity(branchEditDto.getCity());
        //GET NAME District
        String district = db2Service.getNameDistrict(branchEditDto.getDistrict());
        entity.setDistrictName(district);
        entity.setDistrict(branchEditDto.getDistrict());

        entity.setAddress(branchEditDto.getAddress());
        /*
         * entity.setLongtitude(branchEditDto.getLongtitude());
         * entity.setLatitude(branchEditDto.getLatitude());
         */
        entity.setLongtitude(StringUtils.isNotEmpty(branchEditDto.getLongtitude()) ? branchEditDto.getLongtitude() : null);
        entity.setLatitude(StringUtils.isNotEmpty(branchEditDto.getLongtitude()) ? branchEditDto.getLatitude() : null);
        entity.setPhone(branchEditDto.getPhone());
        entity.setEmail(branchEditDto.getEmail());
        entity.setFax(branchEditDto.getFax());
        entity.setNote(branchEditDto.getNote());
        entity.setWorkingHours(branchEditDto.getWorkingHours());
        entity.setActiveFlag(branchEditDto.getActiveFlag());
        try {
            branchRepository.save(entity);
        } catch (NullPointerException e) {
            logger.error("entity", e);
        }
        branchEditDto.setId(entity.getId());
    }

    @Override
    public List<Select2Dto> findByRegionAndLanguageCode(String languageCode) {
        return branchRepository.findByRegionAndLanguageCode(languageCode);
    }

    @Override
    @Transactional
    public void deleteBranch(Branch branch) {
        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        branch.setDeleteDate(new Date());
        branch.setDeleteBy(userName);
        branchRepository.save(branch);

    }

    @Override
    public CommonSearchFilterUtils getCommonSearchFilterUtils()  {
        return commonSearchFilterUtils;
    }
}
