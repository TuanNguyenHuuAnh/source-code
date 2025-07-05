/*******************************************************************************
 * Class        ：JcaRoleForCompanyServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
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
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.dto.JcaRoleForCompanySearchDto;
import vn.com.unit.core.entity.JcaRoleForCompany;
import vn.com.unit.core.repository.JcaRoleForCompanyRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRoleForCompanyService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleForCompanyServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRoleForCompanyServiceImpl implements JcaRoleForCompanyService{
    @Autowired
    private JcaRoleForCompanyRepository jcaRoleForCompanyRepository;
    @Autowired
    ObjectMapper objectMapper;
    

    @Override
    public List<JcaRoleForCompanyDto> getRoleForCompanyDtoByCondition(JcaRoleForCompanySearchDto reqSearch, Pageable pageableAfterBuild) {
        return jcaRoleForCompanyRepository.getRoleForCompanyDtoByCondition(reqSearch, pageableAfterBuild).getContent();
    }


    @Override
    public int countRoleForCompanyDtoByCondition(JcaRoleForCompanySearchDto reqSearch) {
        return jcaRoleForCompanyRepository.countRoleForCompanyDtoByCondition(reqSearch);
    }


    @Override
    public JcaRoleForCompany saveJcaRoleForCompanyDto(JcaRoleForCompanyDto objectDto) {
        JcaRoleForCompany jcaRoleForCompany = objectMapper.convertValue(objectDto, JcaRoleForCompany.class);
//        jcaRoleForCompany.setId(objectDto.getRoleForCompanyId());
        this.saveJcaRoleForCompany(jcaRoleForCompany);
//        objectDto.setRoleForCompanyId(jcaRoleForCompany.getId());
        return jcaRoleForCompany;
    }


    /**
     * saveJcaRoleForCompany
     * @param jcaRoleForCompany
     * @return
     * @author ngannh
     */
    @Transactional(rollbackFor = Exception.class)
    private JcaRoleForCompany saveJcaRoleForCompany(JcaRoleForCompany jcaRoleForCompany) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = 0l;//jcaRoleForCompany.getId();
        if (null != id) {
            JcaRoleForCompany oldJcaRoleForCompany= jcaRoleForCompanyRepository.findOne(id);
            if (null != oldJcaRoleForCompany) {
                jcaRoleForCompany.setCreatedDate(oldJcaRoleForCompany.getCreatedDate());
                jcaRoleForCompany.setCreatedId(oldJcaRoleForCompany.getCreatedId());
//                jcaRoleForCompany.setUpdatedDate(sysDate);
//                jcaRoleForCompany.setUpdatedId(userId);
                if(jcaRoleForCompany.getIsAdmin() ==null) {
                    jcaRoleForCompany.setIsAdmin(false);
                }
                jcaRoleForCompany = jcaRoleForCompanyRepository.update(jcaRoleForCompany);

            }

        } else {
            jcaRoleForCompany.setCreatedDate(sysDate);
            jcaRoleForCompany.setCreatedId(userId);
//            jcaRoleForCompany.setUpdatedDate(sysDate);
//            jcaRoleForCompany.setUpdatedId(userId);
            if(jcaRoleForCompany.getIsAdmin() ==null) {
                jcaRoleForCompany.setIsAdmin(false);
            }
            jcaRoleForCompany = jcaRoleForCompanyRepository.create(jcaRoleForCompany);
        }
        return jcaRoleForCompany;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaRoleForCompanyService#getJcaRoleForCompanyById(java.lang.Long)
     */
    @Override
    public JcaRoleForCompany getJcaRoleForCompanyById(Long id) {

        return jcaRoleForCompanyRepository.findOne(id);
    }


    @Override
    public List<JcaRoleForCompanyDto> getJcaRoleForCompanyByRoleId(Long roleId, Long companyId) {
        return jcaRoleForCompanyRepository.getJcaRoleForCompanyByRoleId(roleId, companyId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllRoleByRoleId(Long roleId) {
        jcaRoleForCompanyRepository.deleteAllRoleByRoleId(roleId);
    }


    @Override
    public DbRepository<JcaRoleForCompany, Long> initRepo() {
        return jcaRoleForCompanyRepository;
    }


    @Override
    public JcaRoleForCompanyDto findJcaRoleForCompanyById(Long orgId, Long roleId, Long companyId) {
        return jcaRoleForCompanyRepository.findJcaRoleForCompanyById(orgId, roleId, companyId);
    }


    @Override
    public void deleteJcaRoleForCompanyById(Long orgId, Long roleId, Long companyId) {
        jcaRoleForCompanyRepository.deleteJcaRoleForCompanyById(orgId, roleId, companyId);
    }

}
