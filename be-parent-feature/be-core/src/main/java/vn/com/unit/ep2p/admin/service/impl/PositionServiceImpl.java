/*******************************************************************************
 * Class        PositionServiceImpl
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.impl.JcaPositionServiceImpl;
import vn.com.unit.ep2p.admin.dto.PositionNode;
import vn.com.unit.ep2p.admin.dto.PositionSearchDto;
import vn.com.unit.ep2p.admin.enumdef.OrgType;
import vn.com.unit.ep2p.admin.enumdef.PositionSearchEnum;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.PositionRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.PositionService;
import vn.com.unit.ep2p.dto.CompanyDto;

/**
 * PositionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PositionServiceImpl extends JcaPositionServiceImpl implements PositionService, AbstractCommonService {
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private SystemConfig systemConfig;
	
    @Autowired
    private CompanyService companyService;
	
	@Autowired
    private CommonService comService;
	
	 @Autowired
	 private ObjectMapper objectMapper;
 
	@Override
	public JcaPositionDto getPositionDtoById(Long id) {
		JcaPositionDto positionDto = null;
        if( null != id ) {
        	positionDto = positionRepository.findPositionDtoById(id);
        }
                      
        return positionDto; 
	}

	@Override
	public JcaPosition getPositionByCode(String code, Long id) {
		JcaPosition result = positionRepository.findPositionByCode(code, id);
		return result;
	}

	@Override
	@Transactional
	public void savePositionDto(JcaPositionDto positionDto) {
		Long userNameLogin = UserProfileUtils.getAccountId();
        Long positionId = positionDto.getPositionId();
        // update data jca_m_position table 
        JcaPosition position = null;
        if( null != positionId ) {
        	position = positionRepository.findOne(positionId);
            if(null == position) {
                throw new BusinessException( "Not found Position by id= " + positionId);
            }
            position.setUpdatedId(userNameLogin);
            position.setUpdatedDate(comService.getSystemDateTime());
        }else{
        	position = new JcaPosition();
        	position.setCreatedId(userNameLogin);
        	position.setCreatedDate(comService.getSystemDateTime());
        }
        
        String code = positionDto.getCode().trim();
        position.setCode(code);
        
        String name = positionDto.getName();
        position.setName(name);
        
        String nameAbv = positionDto.getNameAbv();
        position.setNameAbv(nameAbv);
        
        Boolean active = positionDto.getActived();
        position.setActived(active);
        
        String description = positionDto.getDescription();
        position.setDescription(description);
        
//        String note = positionDto.getNote();
//        position.setNote(note);
        
        Long companyId = positionDto.getCompanyId();
        position.setCompanyId(companyId);
        
        try{
        	if (null != position.getUpdatedId()) {
                positionRepository.update(position);
        	} else {
            	positionRepository.create(position);
        	}
        	positionDto.setPositionId(position.getId());
            //update positionId account
            //accountService.updatePositionIdByPositionCode(positionId, position.getCode(), companyId);
            //accountOrgService.updatePositionIdByPositionCode(positionId, position.getCode(), companyId);
            
        }catch (Exception ex){
            throw new SystemException(ex);
        }
	}

	@Override
	public PageWrapper<JcaPositionDto> searchByCondition(int page, PositionSearchDto searchDto, int pageSize) {
		// Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaPositionDto> pageWrapper = new PageWrapper<JcaPositionDto>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);      
        pageWrapper.setSizeOfPage(sizeOfPage);
        if(null == searchDto) {
        	searchDto = new PositionSearchDto();
        }
        // set SearchParm
        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
        this.setSearchParm(searchDto);
//        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//		//searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        
        int count = positionRepository.countPositionDtoByCondition(searchDto);
        List<JcaPositionDto> result = new ArrayList<JcaPositionDto>();
        if( count > 0 ) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage-1) * sizeOfPage;
            result = positionRepository.findPositionDtoByCondition(startIndex, sizeOfPage, searchDto);
        }
        
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
	}
	
	private void setSearchParm(PositionSearchDto searchDto) {
		String searchValue = searchDto.getFieldSearch();
		List<String> fieldSearchList = searchDto.getFieldValues();
		
		if(!StringUtils.isEmpty(searchValue)) {
    		searchValue = searchValue.trim();
    	}
		
        if (null != fieldSearchList && !fieldSearchList.isEmpty()) {
        	for (String fieldSearch : fieldSearchList) {
        		if (StringUtils.equals(fieldSearch, PositionSearchEnum.CODE.name())) {
                	searchDto.setCode(searchValue);
                    continue;
                }
                if (StringUtils.equals(fieldSearch, PositionSearchEnum.NAME.name())) {
                	searchDto.setName(searchValue);
                    continue;
                }
                if (StringUtils.equals(fieldSearch, PositionSearchEnum.NAME_ABV.name())) {
                	searchDto.setNameAbv(searchValue);
                    continue;
                }                             
                if (StringUtils.equals(fieldSearch, PositionSearchEnum.DESCRIPTION.name())) {
                	searchDto.setDescription(searchValue);
                    continue;
                }
			}
        } else {
        	searchDto.setCode(searchValue);
        	searchDto.setName(searchValue);
        	searchDto.setNameAbv(searchValue);
        	searchDto.setDescription(searchValue);
        }
//        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
    }

	@Override
	@Transactional
	public void deletePositionById(Long id) {
		Long usernameLogin = UserProfileUtils.getAccountId();
		Date systemDate = comService.getSystemDateTime();
		
		JcaPosition position = positionRepository.findOne(id);
		if( null != id ) {
			position.setDeletedDate(systemDate);          
			position.setDeletedId(usernameLogin);
			
			try {
				positionRepository.update(position);
            } catch (Exception ex) {
                throw new SystemException(ex);
            } 
		}
	}

	@Override
	public List<JcaPositionDto> getPositionDtoList() {
		List<JcaPositionDto> positionDtoList = positionRepository.findPositionDtoList();
		
		if( positionDtoList == null ) {
			positionDtoList = new ArrayList<>();
		}
		
		return positionDtoList;
	}

	@Override
	public List<JcaPositionDto> findPositionDtoByCompany(Long companyId) {
		return positionRepository.findByCompany(companyId);
	}
	@Override
	public List<JcaPositionDto> getPositionDtoListByCompanyId(Long companyId) {
		List<JcaPositionDto> positionDtoList = positionRepository.findPositionDtoListByCompanyId(companyId);
		
		if( positionDtoList == null ) {
			positionDtoList = new ArrayList<>();
		}
		
		return positionDtoList;
	}

    @Override
    public JcaPosition getByCodeAndCompanyId(String code, Long companyId, Long positionId) {
        return positionRepository.getByCodeAndCompanyId(code, companyId, positionId);
    }

    @Override
    public List<Select2Dto> getSelect2DtoListByTermAndCompanyId(String keySearch, Long companyId, Boolean isAdmin, Boolean isPaging) {
        return positionRepository.findSelect2DtoByKeyAndCompanyId(keySearch, companyId, isAdmin, isPaging);
    }

	@Override
	public vn.com.unit.common.service.JCommonService getCommonService() {
		return comService;
	}

	@Override
	public PositionNode buildPosition(Long posId) {

        List<JcaPositionDto> jcaPosList = new ArrayList<>();
        if (null == posId) {
            // orgId = 1L;
//            try {
                jcaPosList.addAll(this.getJcaPositionDtoToBuildTree());
                
//            } catch (DetailException e) {
//                logger.error("Exception ", e);
//            }
        }else {
//            jcaPosList = getJcaOrganizationDtoChildByParentIdAndDepth(posId, 1L);
        }
        PositionNode root = buildOrgTreeModelNew(jcaPosList);

        return root;
    
	}
	
	private PositionNode buildOrgTreeModelNew(List<JcaPositionDto> listOrg) {
        Hashtable<Long, PositionNode> tableOrgModel = new Hashtable<>();

        for (int i = 0; i < listOrg.size(); i++) {
        	JcaPositionDto org = listOrg.get(i);

            List<PositionNode> listChildrenNode = new ArrayList<>();
            for (int j = 0; j < listOrg.size(); j++) {
            	JcaPositionDto subOrg = listOrg.get(j);
                if (org.getPositionId().equals(subOrg.getPositionParentId())) { //getParentOrgId
                	PositionNode subNode = tableOrgModel.get(subOrg.getPositionId());
                    if (subNode == null) {
                        subNode = new PositionNode();
                        subNode.setId(subOrg.getPositionId());
                        subNode.setText(subOrg.getName());
                        subNode.setIconCls(getOrgIcon(subOrg.getPositionType()));
                        subNode.setState(getOrgState(subOrg.getPositionLevel()));
                        tableOrgModel.put(subOrg.getPositionId(), subNode);
                    }
                    listChildrenNode.add(subNode);
                }
            }

            PositionNode node = tableOrgModel.get(org.getPositionId());
            if (node == null) {
                node = new PositionNode();
                node.setId(org.getPositionId());
                node.setText(org.getName());
                node.setIconCls(getOrgIcon(org.getPositionType()));
                node.setState(getOrgState(org.getPositionLevel()));
            }
            node.setChildren(listChildrenNode);
            tableOrgModel.remove(org.getPositionId());
            tableOrgModel.put(org.getPositionId(), node);

        }

        PositionNode root = null;
        for (JcaPositionDto orgTmp : listOrg) {
            if (orgTmp.getPositionLevel() == null || orgTmp.getPositionLevel().equals(0L)) {
                root = tableOrgModel.get(orgTmp.getPositionId());
                break;
            }
        }
        return root;
    }
	
	private String getOrgIcon(String orgType) {
        String result = "";
        if(orgType == null) {
            return result;
        }
        if (orgType.equals(OrgType.REGION.toString())) {
            result = "icon-org";
        } else if (orgType.equals(OrgType.BRANCH.toString())) {
            result = "icon-branch";
        } else if (orgType.equals(OrgType.SECTION.toString())) {
            result = "icon-section";
        } else if (orgType.equals(OrgType.TEAM.toString())) {
            result = "icon-team";
        } else {
            result = "icon-org";
        }
        return result;
    }

    private String getOrgState(Integer parentOgrID) {
        /*if (orgType.equals(OrgType.SECTION.toString())) {
            return "closed";
        } else {
            return "open";
        }*/
        return "open";
    }

	@Override
	public List<PositionNode> findPositionByParent(Long id) {
		List<PositionNode> listNode = positionRepository.findNodeByOrgParent(UserProfileUtils.getCompanyIdList(),
				UserProfileUtils.isCompanyAdmin(), id);
		for (PositionNode organizationNode : listNode) {
			organizationNode.setState(checkStateNode(organizationNode.getId()));
		}
		return listNode;
	}

	private String checkStateNode(Long nodeId) {
		return (nodeId != null && positionRepository.countByParentId(nodeId) > 0) ? "closed" : "open";

	}

	@Override
	public Integer getMaxPositionSort(Long positionParentId) {
		return positionRepository.getMaxPositionSort(positionParentId);
	}

    @Override
    public JcaPositionDto buildPosModel(long orgId, boolean editable) {
        JcaPositionDto positionDto = new JcaPositionDto();
        JcaPositionDto orgInfo = this.getJcaPositionDtoById(orgId);
        // For detail and edit organization
        if (editable) {
            if (orgInfo != null) {
                positionDto = objectMapper.convertValue(orgInfo, JcaPositionDto.class);
                // companyName
                CompanyDto companyDto = companyService.findById(orgInfo.getCompanyId());
                if (companyDto != null) {
                    positionDto.setCompanyName(companyDto.getName());
                }
            }
        } else {
            // For new organization
            positionDto.setPositionParentId(orgId);
            if (orgId != 1) {
                positionDto.setCompanyId(orgInfo.getCompanyId());
            }

        }
        
        return positionDto;
    }
}
