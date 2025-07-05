/*******************************************************************************
 * Class        ：JcaAuthorityServiceImpl
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
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
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaAuthorityProcessSearchDto;
import vn.com.unit.core.dto.JcaAuthoritySearchDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.core.enumdef.FunctionType;
import vn.com.unit.core.repository.JcaAuthorityRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAuthorityService;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.utils.DtsStringUtil;

/**
 * JcaAuthorityServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAuthorityServiceImpl implements JcaAuthorityService {


    /** The jca authority repository. */
    @Autowired
    private JcaAuthorityRepository jcaAuthorityRepository;

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAuthorityService#getAuthorityDtoByCondition(vn.com.unit.core.dto.JcaAuthoritySearchDto,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public List<JcaAuthorityDto> getAuthorityDtoByCondition(JcaAuthoritySearchDto reqSearch, Pageable pageableAfterBuild) {
        return jcaAuthorityRepository.getAuthorityDtoByCondition(reqSearch, pageableAfterBuild).getContent();

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAuthorityService#countAuthorityDtoByCondition(vn.com.unit.core.dto.JcaAuthoritySearchDto,
     * java.util.List)
     */
    @Override
    public int countAuthorityDtoByCondition(JcaAuthoritySearchDto reqSearch) {
        return jcaAuthorityRepository.countAuthorityByCondition(reqSearch);

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAuthorityService#saveJcaAuthorityDto(vn.com.unit.core.dto.JcaAuthorityDto)
     */
    @Override
    public JcaAuthority saveJcaAuthorityDto(JcaAuthorityDto objectDto) {
        JcaAuthority jcaAuthority = objectMapper.convertValue(objectDto, JcaAuthority.class);
//        jcaAuthority.setId(objectDto.getAuthorityId());
        this.saveJcaAuthority(jcaAuthority);
//        objectDto.setAuthorityId(jcaAuthority.getId());
        return jcaAuthority;
    }

    /**
     * Save jca role for company.
     *
     * @param jcaAuthority
     *            the jca authority
     * @return the jca authority
     */
    @Transactional(rollbackFor = Exception.class)
    private JcaAuthority saveJcaAuthority(JcaAuthority jcaAuthority) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = 1l;//jcaAuthority.getId();
        if (null != id) {
            JcaAuthority oldJcaAuthority = jcaAuthorityRepository.findOne(id);
            if (null != oldJcaAuthority) {
                jcaAuthority.setCreatedDate(oldJcaAuthority.getCreatedDate());
                jcaAuthority.setCreatedId(oldJcaAuthority.getCreatedId());
                jcaAuthority = jcaAuthorityRepository.update(jcaAuthority);
            }

        } else {
            jcaAuthority.setCreatedDate(sysDate);
            jcaAuthority.setCreatedId(userId);
        }
        return jcaAuthority;
    }

    @Override
    public JcaAuthorityDto getJcaAuthorityDtoById(Long id) {
        return jcaAuthorityRepository.getJcaAuthorityDtoById(id);
    }

    @Override
    public JcaAuthority getJcaAuthorityById(Long id) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthorityDtoByRoleIdAndFunctionType(Long roleId, String functionType) {
        String funcType = FunctionType.TYPE1.getValue();
        if (DtsStringUtil.isNotBlank(functionType)) {
            funcType = functionType;
        }
        jcaAuthorityRepository.deleteAuthorityDtoByRoleIdAndFunctionType(roleId, funcType);
    }

    @Override
    public List<JcaAuthorityDto> getAuthorityByProcess(JcaAuthorityProcessSearchDto reqSearch, Pageable pageable) {
        return jcaAuthorityRepository.getAuthorityByProcess(reqSearch, pageable);
    }

    @Override
    public int countAuthorityByProcess(JcaAuthorityProcessSearchDto reqSearch) {
        return jcaAuthorityRepository.countAuthorityByProcess(reqSearch);
    }

    @Override
    public List<JcaAuthorityDto> getJcaRoleForItemByRoleId(Long roleId, Long companyId) {
        return jcaAuthorityRepository.getJcaRoleForItemByRoleId(roleId, companyId);
    }

    @Override
    public DbRepository<JcaAuthority, Long> initRepo() {
        return jcaAuthorityRepository;
    }

}
