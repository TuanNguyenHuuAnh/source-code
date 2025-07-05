/*******************************************************************************
 * Class        ：JcaConstantServiceImpl
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.sf.amateras.mirage.SqlManager;
import vn.com.unit.common.dto.ConstantDeleteDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.JcaGroupConstant;
import vn.com.unit.core.repository.JcaConstantRepository;
import vn.com.unit.core.repository.JcaGroupConstantRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.db.repository.DbRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * JcaConstantServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaConstantServiceImpl implements JcaConstantService {

	@Autowired
	private JcaConstantRepository jcaConstantRepository;

	@Autowired
	private JcaGroupConstantRepository jcaGroupConstantRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SqlManager sqlManager;

	@Override
	public int countJcaConstantByCondition(JcaConstantSearchDto jcaConstantSearchDto) {
		return jcaConstantRepository.countJcaConstantByCondition(jcaConstantSearchDto);
	}

	@Override
	public List<JcaConstantDto> getListJcaConstantDtoByCondition(JcaConstantSearchDto jcaConstantSearchDto,
			Pageable pageable) {
		return jcaConstantRepository.getListJcaConstantDtoByCondition(jcaConstantSearchDto, pageable).getContent();
	}

	@Override
	public boolean checkCodeExistsByGroupCodeAndKind(String code, String groupCode, String kind, Long langId) {
		return jcaConstantRepository.checkCodeExistsByGroupCodeAndKind(code, groupCode, kind, langId);
	}

	@Override
	public JcaConstant saveJcaConstant(JcaConstant jcaConstant,JcaConstantDto objectDto) {

		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

		String code = jcaConstant.getCode();
		String kind = jcaConstant.getKind();
		String groupCode = jcaConstant.getGroupCode();
		Long langId = jcaConstant.getLangId();

		boolean checkCodeExists = this.checkCodeExistsByGroupCodeAndKind(objectDto.getOldCode(), groupCode, objectDto.getOldKind(), langId);

		if (checkCodeExists) {
			JcaConstant entity = this.getJcaConstantByCodeAndGroupCodeAndKind(objectDto.getOldCode(), groupCode, objectDto.getOldKind(), langId);
			CommonObjectUtil.copyPropertiesNonNull(jcaConstant,entity);
			entity.setUpdatedDate(sysDate);
			entity.setUpdatedId(userId);
			jcaConstantRepository.updateData(entity, groupCode,  objectDto.getOldKind(), objectDto.getOldCode());
			return entity;
		} else {
			String id = jcaConstantRepository.findGroupId(groupCode);
			if (id == null) {
				id = jcaConstantRepository.findGroupIdMax();
			}

			jcaConstant.setGroupId(id);
			jcaConstant.setCreatedDate(sysDate);
			jcaConstant.setCreatedId(userId);
			jcaConstant.setUpdatedDate(sysDate);
			jcaConstant.setUpdatedId(userId);
			jcaConstantRepository.createData(jcaConstant);
			return jcaConstant;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaConstant saveJcaConstantDto(JcaConstantDto objectDto) {

		JcaConstant jcaConstant = objectMapper.convertValue(objectDto, JcaConstant.class);
		// save JcaConstant	
		return this.saveJcaConstant(jcaConstant,objectDto);

	}

	@Override
	public JcaConstantDto getJcaConstantDtoByCodeAndGroupCodeAndKind(String code, String groupCode, String kind,
			String langCode) {
		return jcaConstantRepository.getJcaConstantDtoByCodeAndGroupCodeAndKind(code, groupCode, kind, langCode);
	}

	private JcaConstant getJcaConstantByCodeAndGroupCodeAndKind(String code, String groupCode, String kind,
			Long langId) {
		return jcaConstantRepository.getJcaConstantByCodeAndGroupCodeAndKind(code, groupCode, kind, langId);
	}

	@Override
	public List<JcaConstantDto> getListJcaConstantDtoByGroupCodeAndLang(String groupCode, String langCode) {
		return jcaConstantRepository.getListJcaConstantDtoByGroupCodeAndLang(groupCode, langCode);
	}

	@Override
	public List<JcaConstantDto> getListJcaConstantDtoByGroupCodeAndKind(String groupCode, String kind,
			String langCode) {
		return jcaConstantRepository.getListJcaConstantByGroupCodeAndKind(groupCode, kind, langCode);
	}

	@Override
	public List<JcaConstantDto> getListJcaConstantDtoByKind(String kind, String langCode) {
		return jcaConstantRepository.getListJcaConstantByKind(kind, langCode);
	}

	@Override
	public List<JcaConstantDto> getListJcaConstantDtoByKindAndCode(String kind, String code, String langCode) {
		return jcaConstantRepository.getListJcaConstantByKindAndCode(kind, code, langCode);
	}

	@Override
	public List<Select2Dto> getAllType(String lang) {
		return jcaConstantRepository.getAllType(lang);
	}

	@Override
	public String validateDeleteConstantByCodeAndKind(String code, String groupCode, String kind) {
		String messageDelete = "";
		ConstantDeleteDto constantDeleteDto = new ConstantDeleteDto();
		constantDeleteDto.code = code;
		constantDeleteDto.groupCode = groupCode;
		constantDeleteDto.kind = kind;
		sqlManager.call("SP_CHECK_DELETE", constantDeleteDto);
		if (constantDeleteDto.status == 1) {
			messageDelete = constantDeleteDto.message;
		} else {
			doDeleteConstantByCodeAndKind(code,groupCode,kind);
		} 
		return messageDelete;
	}

	@Override
	public void doDeleteConstantByCodeAndKind(String code, String groupCode, String kind) {
		
		jcaConstantRepository.deleteConstantDisplay(code, groupCode, kind);

	}

	@Override
	public List<JcaConstant> getListJcaConstantDtoByCodeGroupAndKind(String code, String groupCode, String kind) {
		return jcaConstantRepository.getListJcaConstantDtoByCodeGroupAndKind(code, groupCode, kind);
	}

	@Override
	public JcaConstantDto getJcaConstantDtoByCodeGroupAndKind(String code, String groupCode, String kind) {
		return jcaConstantRepository.getJcaConstantDtoByCodeGroupAndKind(code, groupCode, kind);
	}

	@Override
	public JcaGroupConstantDto getAllJcaConstantDtoByGroupCode(String groupCode) {
		JcaGroupConstantDto groupDto = getJcaGroupConstantDtoByGroupCode(groupCode);
		List<JcaConstantDto> listDto = jcaConstantRepository.getListJcaConstantDtoByGroupCodePivot(groupCode);
		groupDto.setConstants(listDto);

		return groupDto;
	}

	@Override
	public JcaGroupConstantDto getJcaGroupConstantDtoByGroupCode(String groupCode) {
		JcaGroupConstantDto rs = jcaGroupConstantRepository.getJcaGroupConstantDtoByGroupCode(groupCode);
		return rs == null ? new JcaGroupConstantDto() : rs;
	}

	@Override
	public DbRepository<JcaConstant, Long> initRepo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaGroupConstant saveJcaGroupConstantDto(JcaGroupConstant objectDto) {

		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
		// JcaGroupConstantDto obj =
		// getJcaGroupConstantDtoByGroupCode(objectDto.getCode());
		JcaGroupConstantDto obj = getJcaGroupConstantDtoByGroupCodeAndLang(objectDto.getCode(),
				objectDto.getLangCode());

		if (ObjectUtils.isNotEmpty(obj) && StringUtils.isNotEmpty(obj.getCode())) {
			CommonObjectUtil.copyPropertiesNonNull(objectDto, obj);
			objectDto.setDisplayOrder(obj.getDisplayOrder());
			objectDto.setUpdatedDate(sysDate);
			objectDto.setUpdatedId(userId);
			jcaGroupConstantRepository.updateData(objectDto);
		} else {
			CommonObjectUtil.copyPropertiesNonNull(objectDto, obj);
			objectDto.setCreatedDate(sysDate);
			objectDto.setCreatedId(userId);
			jcaGroupConstantRepository.createData(objectDto);
		}
		return objectDto;

	}

	@Override
	public JcaGroupConstantDto getJcaGroupConstantDtoByGroupCodeAndLang(String groupCode, String langCode) {
		return jcaGroupConstantRepository.getJcaGroupConstantDtoByGroupCodeAndLang(groupCode, langCode);
	}
	
	@Override
	public List<JcaConstantDto> getListJcaConstantDisplayByType(String type) {
		return jcaConstantRepository.getListJcaConstantDisplayByType(type);
	}
	
}
