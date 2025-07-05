// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementDto;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementSearchDto;
import vn.com.unit.ep2p.core.ers.entity.ErsQuestionInterview;
import vn.com.unit.ep2p.core.ers.repository.QuestionaireManagementRepository;
import vn.com.unit.ep2p.core.ers.service.QuestionaireManagementService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class QuestionaireManagementServiceImpl extends AbstractCommonService implements QuestionaireManagementService {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	QuestionaireManagementRepository questionaireManagementRepository;

	private static final Logger logger = LoggerFactory.getLogger(QuestionaireManagementServiceImpl.class);

	@Override
	public int countByCondition(QuestionaireManagementSearchDto searchDto) {
		return questionaireManagementRepository.countByCondition(searchDto);
	}

	@Override
	public List<QuestionaireManagementDto> searchByCondition(QuestionaireManagementSearchDto searchDto, Integer page,
			Integer size) {
		return questionaireManagementRepository.searchByCondition(searchDto, getOffset(page, size), size);
	}

	@Override
	public QuestionaireManagementDto findById(Long id) {
		ErsQuestionInterview entity = questionaireManagementRepository.findOne(id);

		QuestionaireManagementDto dto = mapper(entity, QuestionaireManagementDto.class);
		dto.setApplyForPosition(entity.getApplyForPosition());

		return dto;
	}

	@Override
	public QuestionaireManagementDto save(QuestionaireManagementDto dto) {

		ErsQuestionInterview entity = null;

		if (null == dto.getId()) {
			entity = mapper(dto, ErsQuestionInterview.class);
			entity.setCreatedBy(getUsernameAction()); // TODO only for dev => need fix get createBy from token
			entity.setCreatedDate(new Date());
		} else {
			entity = questionaireManagementRepository.findOne(dto.getId());
			entity.setUpdatedBy(getUsernameAction()); // TODO only for dev => need fix get createBy from token
			entity.setUpdatedDate(new Date());

			entity.setTypeQuestion(dto.getTypeQuestion());
			entity.setApplyForPosition(dto.getApplyForPosition());
			entity.setContent(dto.getContent());
			entity.setOrderOnForm(dto.getOrderOnForm());
			entity.setStatusItem(dto.getStatusItem());
		}

		entity = questionaireManagementRepository.save(entity);
		
		dto = mapper(entity, QuestionaireManagementDto.class);
		dto.setApplyForPosition(entity.getApplyForPosition());

		return dto;
	}

	@Override
	public void delele(Long id) {
		try {
			ErsQuestionInterview entity = questionaireManagementRepository.findOne(id);

			entity.setDeletedFlag(1);
			entity.setDeletedBy(getUsernameAction()); // TODO only for dev => need fix get createBy from token
			entity.setDeletedDate(new Date());

			questionaireManagementRepository.save(entity);
		} catch (Exception e) {
			logger.error("Questionaire management delete error [#20210412]", e);
			throw e;
		}
	}

}
