/*******************************************************************************
 * Class        ：AuthorityAccountServiceImpl
 * Created date ：2021/03/07
 * Lasted date  ：2021/03/07
 * Author       ：Tan Tai
 * Change log   ：2021/03/07：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaOrganizationService;
import vn.com.unit.core.service.JcaPositionService;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.workflow.dto.AuthorityAccountDto;
import vn.com.unit.workflow.dto.JpmAuthorityDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.service.AuthorityAccountService;
import vn.com.unit.workflow.service.JpmAuthorityService;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;

/**
 * AuthorityAccountServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AuthorityAccountServiceImpl implements AuthorityAccountService{

	@Autowired
	private JpmAuthorityService jpmAuthorityService;
	
	@Autowired
	private JcaAccountService jcaAccountService;
	
	@Autowired
	private JpmButtonForStepDeployService jpmButtonForStepDeployService;
	
	@Autowired
	private JcaPositionService jcaPositionService;
	
	@Autowired
	private JcaOrganizationService jcaOrganizationService;
	
	
	@Override
    public List<Long> getAssigneeIdsByStepDeployId(Long stepDeployId) throws Exception{
		
    	List<JpmButtonForStepDeployDto> jpmButtonForStepDeloyList =  jpmButtonForStepDeployService.getButtonForStepDeployDtosByStepDeployId(stepDeployId);
    	
    	List<Long> permissionIds = DtsCollectionUtil.isNotEmpty(jpmButtonForStepDeloyList) ?  jpmButtonForStepDeloyList.stream().map(i -> i.getPermissionDeployId()).collect(Collectors.toList()) : null;

    	
    	List<JpmAuthorityDto> jpmAuthorityDtoList = jpmAuthorityService.getListJpmAuthorityDtoByPermissionIds(permissionIds);
    	List<Long> roleIds = DtsCollectionUtil.isNotEmpty(jpmAuthorityDtoList) ? jpmAuthorityDtoList.stream().map(i -> i.getRoleId()).collect(Collectors.toList()) : null;
    	
    	return jcaAccountService.getAccIdsByRoleIds(roleIds);
    }
	
	@Override
	public AuthorityAccountDto getInfoAccountById(Long accountId) {
		AuthorityAccountDto authorityAccountDto = new AuthorityAccountDto();
		
		JcaAccount acc= jcaAccountService.getAccountById(accountId);
		
		authorityAccountDto.setAccountAvatar(acc.getAvatar());
		authorityAccountDto.setAccountAvatarRepoId(acc.getAvatarRepoId());
		authorityAccountDto.setAccountCode(acc.getCode());
		authorityAccountDto.setAccountEmail(acc.getEmail());
		authorityAccountDto.setAccountFullName(acc.getFullname());
		authorityAccountDto.setAccountId(accountId);
		authorityAccountDto.setAccountName(acc.getUsername());
		
		return authorityAccountDto;
	}
	
	@Override
	public String getNamePositionById(Long positionId) {
		return jcaPositionService.getPositionById(positionId).getName();
	}
 
    @Override
    public String getNameOrgById(Long orgId) {
        JcaOrganization entity = jcaOrganizationService.getJcaOrganizationById(orgId);
        if (entity != null) {
            return entity.getName();
        }
        return null;
    }
}
