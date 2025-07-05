/*******************************************************************************
 * Class        :EfoCategoryServiceImpl
 * Created date :2020/12/17
 * Lasted date  :2020/12/17
 * Author       :TrongNV
 * Change log   :2020/12/17 01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;
import vn.com.unit.ep2p.core.efo.repository.EfoCategoryRepository;
import vn.com.unit.ep2p.core.efo.service.EfoCategoryService;


/**
 * 
 * EfoCategoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoCategoryServiceImpl implements EfoCategoryService {
	
	@Autowired
	EfoCategoryRepository efoCategoryRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public int countCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto) {
		return efoCategoryRepository.countCategoryDtoByCondition(efoCategorySearchDto);
	}

    @Override
    public List<EfoCategoryDto> getCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto, Pageable pagable) {
        return efoCategoryRepository.getCategoryDtoByCondition(efoCategorySearchDto, pagable).getContent();
    }

	@Override
	public EfoCategory getCategoryById(Long id) {
		return efoCategoryRepository.findOne(id);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public EfoCategory saveEfoCategory(EfoCategory efoCategory) {
		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
		Long id = efoCategory.getId();
		if(null != id) {
		    EfoCategory oldEfoCategory =  efoCategoryRepository.findOne(id);
			if (null !=oldEfoCategory) {
			    efoCategory.setCreatedDate(oldEfoCategory.getCreatedDate());
			    efoCategory.setCreatedId(oldEfoCategory.getCreatedId());
			    efoCategory.setUpdatedDate(sysDate);
			    efoCategory.setUpdatedId(userId);
				efoCategoryRepository.update(efoCategory);
			}
			
		}else {
		    efoCategory.setCreatedDate(sysDate);
		    efoCategory.setCreatedId(userId);
		    efoCategory.setUpdatedDate(sysDate);
		    efoCategory.setUpdatedId(userId);
			efoCategoryRepository.create(efoCategory);
		}
		return efoCategory;
	}
	
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoCategory saveEfoCategoryDto(EfoCategoryDto efoCategoryDto) {
        EfoCategory efoCategory = objectMapper.convertValue(efoCategoryDto, EfoCategory.class);
        efoCategory.setId(efoCategoryDto.getCategoryId());
        //efoCategory.setName(efoCategoryDto.getCategoryName());
        
        // save data
        efoCategory = this.saveEfoCategory(efoCategory);
        
        // update id
        efoCategoryDto.setCategoryId(efoCategory.getId());
        return efoCategory;
    }

	@Override
    @Transactional(rollbackFor = Exception.class)
	public EfoCategoryDto getEfoCategoryDtoById(Long id) {
		return efoCategoryRepository.getEfoCategoryDtoById(id);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletedEfoCategoryById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != id) {
            EfoCategory efoCategory =  efoCategoryRepository.findOne(id);
            if (null !=efoCategory) {
                efoCategory.setDeletedDate(sysDate);
                efoCategory.setDeletedId(userId);
                efoCategoryRepository.update(efoCategory);
            }
            
        }
    }


}
