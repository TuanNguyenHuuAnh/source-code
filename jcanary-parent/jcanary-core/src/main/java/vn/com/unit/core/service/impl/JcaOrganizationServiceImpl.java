/*******************************************************************************
 * Class        :JcaOrganizationServiceImpl
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.repository.JcaOrganizationRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaOrganizationPathService;
import vn.com.unit.core.service.JcaOrganizationService;

/**
 * JcaOrganizationServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaOrganizationServiceImpl implements JcaOrganizationService {

    @Autowired
    private JcaOrganizationRepository jcaOrganizationRepository;
    
    @Autowired
    private JcaOrganizationPathService jcaOrganizationPathService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JCommonService commonService;
    
    @Override
    public List<JcaOrganizationDto> getJcaOrganizationDto() {
        return jcaOrganizationRepository.getJcaOrganzitionDto();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaOrganization saveJcaOrganization(JcaOrganization jcaOrganization) {
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        Long id = jcaOrganization.getId();
        if (null != id) {
            JcaOrganization oldJcaOrganization = jcaOrganizationRepository.findOne(id);
            if (null != oldJcaOrganization) {
                jcaOrganization.setCreatedDate(oldJcaOrganization.getCreatedDate());
                jcaOrganization.setCreatedId(oldJcaOrganization.getCreatedId());
                jcaOrganization.setUpdatedDate(systemDate);
                jcaOrganization.setUpdatedId(userId);
                jcaOrganizationRepository.update(jcaOrganization);
            }
        } else {
            jcaOrganization.setCreatedDate(systemDate);
            jcaOrganization.setCreatedId(userId);
            jcaOrganization.setUpdatedDate(systemDate);
            jcaOrganization.setUpdatedId(userId);
            jcaOrganizationRepository.create(jcaOrganization);
        }
        return jcaOrganization;
    }

    @Override
    public JcaOrganization saveJcaOrganizationDto(JcaOrganizationDto jcaOrganizationDto) {
        
        /** BEGIN save org info */
    	JcaOrganization jcaOrganization = objectMapper.convertValue(jcaOrganizationDto, JcaOrganization.class);
        jcaOrganization.setId(jcaOrganizationDto.getOrgId());
        this.saveJcaOrganization(jcaOrganization);
        jcaOrganizationDto.setOrgId(jcaOrganization.getId());
        /** END */
        
        
        /** BEGIN save org path */
        // delete org path
        jcaOrganizationPathService.deleteOrganizationPathByDescendantId(jcaOrganization.getId());
        this.saveOrgPath(jcaOrganizationDto);
        /** END */
        
        return jcaOrganization;
    }

    @Override
    public JcaOrganizationDto getJcaOrganizationDtoById(Long id) {
        return jcaOrganizationRepository.getJcaOrganizationDtoById(id);
    }

    @Override
    public JcaOrganization getJcaOrganizationById(Long id) {
        return jcaOrganizationRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaOrganizationById(Long id) {
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if (null != id) {
            JcaOrganization jcaOrganization = jcaOrganizationRepository.findOne(id);
            if (null != jcaOrganization) {
                jcaOrganization.setDeletedDate(systemDate);
                jcaOrganization.setDeletedId(userId);
                jcaOrganizationRepository.update(jcaOrganization);
            }
        } 
    }

    @Override
    public int countJcaOrganizationDtoByOrgCode(String orgCode) {
        return jcaOrganizationRepository.countJcaOrganizationDtoByOrgCode(orgCode);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaOrganizationService#getJcaOrganizationDtoChildByParentIdAndDepth(java.lang.Long, java.util.List)
     */
    @Override
    public List<JcaOrganizationDto> getJcaOrganizationDtoChildByParentIdAndDepth(Long parentId, Long depth) {
        return jcaOrganizationRepository.getJcaOrganizationDtoChildByParentIdAndDepth(parentId, depth);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void saveOrgPath(JcaOrganizationDto jcaOrganizationDto) {
		Long noteId = jcaOrganizationDto.getOrgId();
		Long subParentNoteId = jcaOrganizationDto.getOrgParentId();
		List<JcaOrganizationDto> datas = this.getJcaOrganizationDto();
		TreeBuilder<JcaOrganizationDto> builder = new TreeBuilder(datas);
		List<JcaOrganizationDto> listTree = builder.getParentBySub(jcaOrganizationDto);

		int depth = CommonConstant.NUMBER_ZERO;
		// save org path leaf
		JcaOrganizationPathDto organizationPathLeafInfoReq = new JcaOrganizationPathDto();
		organizationPathLeafInfoReq.setAncestorId(noteId);
		organizationPathLeafInfoReq.setDescendantId(noteId);
		organizationPathLeafInfoReq.setDepth(depth);
		jcaOrganizationPathService.saveJcaOrganizationPathDto(organizationPathLeafInfoReq);

		// save organization path
		if (CommonCollectionUtil.isNotEmpty(listTree)) {
			// add leaf
			listTree.add(0, jcaOrganizationDto);
			// save path parent
			for (JcaOrganizationDto tree : listTree) {
				depth++;
				Long subParentNoteNextId = tree.getOrgParentId();
				JcaOrganizationPathDto organizationPathInfoReq = new JcaOrganizationPathDto();
				organizationPathInfoReq.setAncestorId(subParentNoteNextId);
				organizationPathInfoReq.setDescendantId(noteId);
				organizationPathInfoReq.setDepth(depth);
				jcaOrganizationPathService.saveJcaOrganizationPathDto(organizationPathInfoReq);
			}
		} else {
			// save path root
			JcaOrganizationPathDto organizationPathRootInfoReq = new JcaOrganizationPathDto();
			organizationPathRootInfoReq.setAncestorId(subParentNoteId);
			organizationPathRootInfoReq.setDescendantId(noteId);
			organizationPathRootInfoReq.setDepth(CommonConstant.NUMBER_ONE);
			jcaOrganizationPathService.saveJcaOrganizationPathDto(organizationPathRootInfoReq);
		}
	}
}
