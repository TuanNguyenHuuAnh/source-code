package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.ECardService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.ecard.dto.ECardEditDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardSearchDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardSearchResultDto;
import vn.com.unit.cms.core.module.ecard.entity.ECard;
import vn.com.unit.cms.core.module.ecard.repository.ECardRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;

@Service
public class ECardServiceImpl implements ECardService {

    @Autowired
    private ECardRepository eCardRepository;

    @Autowired
    private CmsCommonService cmsCommonService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MessageSource msg;

    private static final String PREFIX = "ECT";
    private static final String TABLE = "M_ECARD";
    @Override
    public List<ECardSearchResultDto> getListByCondition(ECardSearchDto searchDto, Pageable pageable) {
    	searchDto.setChannel(UserProfileUtils.getChannel());
        return eCardRepository.findListSearch(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(ECardSearchDto searchDto) {
    	searchDto.setChannel(UserProfileUtils.getChannel());
        return eCardRepository.countList(searchDto);
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public CmsCommonService getCmsCommonService() {
        return cmsCommonService;
    }

    @Override
    public JcaDatatableConfigService getJcaDatatableConfigService() {
        return jcaDatatableConfigService;
    }

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(ECardSearchDto searchDto, Locale locale) {
        List<CommonSearchFilterDto> list = ECardService.super.initListSearchFilter(searchDto, locale);
        List<CommonSearchFilterDto> rs = new ArrayList<CommonSearchFilterDto>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonSearchFilterDto filter : list) {
                    rs.add(filter);
            }
        }
        return rs;
    }

    @Override
    public ECardEditDto getEditDtoById(Long id, Locale locale) {
        ECardEditDto resultDto = new ECardEditDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);
            resultDto = new ECardEditDto(1, 2, 3, 4, 5, 6, 7, 8);
            return resultDto;
        }

        ECard entity = eCardRepository.findOne(id);
        if (null != entity) {
            resultDto.setId(entity.getId());
            resultDto.setCode(entity.getCode());
            resultDto.setTitle(entity.getTitle());
            resultDto.setSort(entity.getSort());
            resultDto.setEnabled(entity.isEnabled());
            resultDto.setCustomerTypeId(entity.getCustomerTypeId());
            resultDto.setAgentName(entity.getAgentName());
            resultDto.setAgentType(entity.getAgentType());
            resultDto.setType(entity.getType());
            resultDto.setTypeName(entity.getTypeName());
            resultDto.setOffice(entity.getOffice());
            resultDto.setPhone(entity.getPhone());
            resultDto.setZalo(entity.getZalo());
            resultDto.setFacebook(entity.getFacebook());
            resultDto.setEmail(entity.getEmail());

            resultDto.setCreateBy(entity.getCreateBy());
            resultDto.setUpdateDate(entity.getUpdateDate());
            resultDto.setBackground(entity.getBackground());
            resultDto.setECardPhysicalImg(entity.getBackground());
        }

        return resultDto;
    }

    @Override
    public void saveOrUpdate(ECardEditDto editDto, Locale locale) throws Exception {
        ECard entity = new ECard();

        if (editDto.getId() == null) {
            entity.setCreateBy(UserProfileUtils.getUserNameLogin());
            entity.setCreateDate(new Date());
        } else {
            entity = eCardRepository.findOne(editDto.getId());

            entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
            entity.setUpdateDate(new Date());
        }

        if (StringUtils.isBlank(entity.getCode())) {
            // gen code
            entity.setCode(getNextCode(PREFIX, cmsCommonService.getMaxCode(TABLE, PREFIX)));
        }
        
        entity.setTitle(editDto.getTitle());
        entity.setSort(editDto.getSort());
        entity.setEnabled(editDto.isEnabled());
        entity.setAgentName(editDto.getAgentName());
        entity.setAgentType(editDto.getAgentType());
        entity.setType(editDto.getType());
        entity.setTypeName(editDto.getTypeName());
        entity.setOffice(editDto.getOffice());
        entity.setPhone(editDto.getPhone());
        entity.setZalo(editDto.getZalo());
        entity.setFacebook(editDto.getFacebook());
        entity.setEmail(editDto.getEmail());
        entity.setBackground(CmsUtils.moveTempToUploadFolder(editDto.getECardPhysicalImg(), Paths.get(AdminConstant.ECARD_EDITOR_FOLDER).toString()));
        entity.setLabel(editDto.getLabel());
        entity.setChannel(UserProfileUtils.getChannel());
        eCardRepository.save(entity);

        if (editDto.getId() == null) {
            editDto.setId(entity.getId());
        }

//        CmsUtils.moveTempSubFolderToUpload(CmsUtils.getPathTemp());
    }

    private String getNextCode(String prefix, String maxCode) {
        int number = 4;

        if (StringUtils.isNotBlank(prefix)) {
            SimpleDateFormat format = new SimpleDateFormat("YYMM");
            prefix = prefix + format.format(new Date()) + ".";
        }

        StringBuffer nextCode = new StringBuffer(prefix);
        if (maxCode == null) {
            return nextCode.append("0001").toString();
        }
        try {
            maxCode = maxCode.replaceAll(prefix.toString(), "");
            return nextCode.append(DtsStringUtil.leftPad(String.valueOf(Long.valueOf(maxCode) + 1), number, "0"))
                    .toString();
        } catch (Exception e) {
            return nextCode.append("0001").toString();
        }
    }
    public void deleteDataById(Long id) throws Exception {
        ECard entity = eCardRepository.findOne(id);
        if (entity != null) {
            entity.setDeleteBy(UserProfileUtils.getUserNameLogin());
            entity.setDeleteDate(new Date());

            eCardRepository.save(entity);
        }
    }

    @Override
    public List<ECardSearchResultDto> getListForSort(ECardSearchDto searchDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateSortAll(ECardSearchDto searchDto) {
        // TODO Auto-generated method stub

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
    public CommonSearchFilterUtils getCommonSearchFilterUtils() {
        return commonSearchFilterUtils;
    }

}
