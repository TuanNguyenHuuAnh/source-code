/*******************************************************************************
 * Class        ：JcaCompanyServiceImpl
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaCompanySearchDto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.core.repository.JcaCompanyRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaCompanyServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaCompanyServiceImpl implements JcaCompanyService {

    @Autowired
    private JcaCompanyRepository jcaCompanyRepository;
    @Autowired
    ObjectMapper objectMapper;

    public static final String COMPANY_IMAGE_FOLDER = "company_image/";

    @Override
    public int countCompanyByCondition(JcaCompanySearchDto companySearchDto) {
        return jcaCompanyRepository.countCompanyByCondition(companySearchDto);
    }

    @Override
    public List<JcaCompanyDto> getCompanyByCondition(JcaCompanySearchDto companySearchDto, Pageable pageable) {

        return jcaCompanyRepository.getCompanyByCondition(companySearchDto, pageable).getContent();
    }

    @Override
    public JcaCompany getCompanyById(Long companyId) {
        return jcaCompanyRepository.findOne(companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaCompany saveJcaCompany(JcaCompany jcaCompany) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaCompany.getId();
        if (null != id) {
            JcaCompany oldJcaCompany = jcaCompanyRepository.findOne(id);
            if (null != oldJcaCompany) {
                jcaCompany.setCreatedDate(oldJcaCompany.getCreatedDate());
                jcaCompany.setCreatedId(oldJcaCompany.getCreatedId());
                jcaCompany.setUpdatedDate(sysDate);
                jcaCompany.setUpdatedId(userId);
                jcaCompany = jcaCompanyRepository.update(jcaCompany);
            }

        } else {
            jcaCompany.setCreatedDate(sysDate);
            jcaCompany.setCreatedId(userId);
            jcaCompany.setUpdatedDate(sysDate);
            jcaCompany.setUpdatedId(userId);
            jcaCompany = jcaCompanyRepository.create(jcaCompany);
        }
        return jcaCompany;
    }

    @Override
    public JcaCompany saveJcaCompanyDto(JcaCompanyDto jcaCompanyDto) {
        
        JcaCompany jcaCompany = objectMapper.convertValue(jcaCompanyDto, JcaCompany.class);
        jcaCompany.setId(jcaCompanyDto.getCompanyId());
        this.saveJcaCompany(jcaCompany);
        jcaCompanyDto.setCompanyId(jcaCompany.getId());
        return jcaCompany;

    }

    @Override
    public JcaCompanyDto getJcaCompanyDtoById(Long id) {
        return jcaCompanyRepository.getJcaCompanyDtoById(id);
    }

    @Override
    public String getSystemCodeByCompanyId(Long companyId) {

        return getCompanyById(companyId).getSystemCode();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaCompanyById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if (null != id) {
            JcaCompany jcaCompany = this.findOne(id);
            if (null != jcaCompany) {
                jcaCompany.setDeletedDate(sysDate);
                jcaCompany.setDeletedId(userId);
                jcaCompany = this.update(jcaCompany);
            }
        }
    }

    @Override
    public int countJcaCompanyDtoBySystemCode(String systemCode) {
        return jcaCompanyRepository.countJcaCompanyDtoBySystemCode(systemCode);
    }

    @Override
    public int countJcaCompanyDtoBySystemName(String systemName, Long companyId) {
        return jcaCompanyRepository.countJcaCompanyDtoBySystemName(systemName, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.db.service.DbRepositoryService#initRepo()
     */
    @Override
    public DbRepository<JcaCompany, Long> initRepo() {
        return jcaCompanyRepository;
    }
}
