/*******************************************************************************
 * Class        JcaPositionServiceImpl
 * Created date 2020/12/02
 * Lasted date  2020/12/02
 * Author       MinhNV
 * Change log   2020/12/02 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.core.dto.JcaPositionSearchDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.repository.JcaPositionRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaPositionPathService;
import vn.com.unit.core.service.JcaPositionService;

/**
 * JcaPositionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaPositionServiceImpl implements JcaPositionService {

    @Autowired
    private JcaPositionRepository jcaPositionRepository;
    
    @Autowired
    private JcaPositionPathService jcaPositionPathService;

    @Autowired
    private ObjectMapper objectMapper;
    

    @Override
    public int countJcaPositionDtoByCondition(JcaPositionSearchDto positionsearchdto) {
        return jcaPositionRepository.countJcaPositionDtoByCondition(positionsearchdto);
    }

    @Override
    public List<JcaPositionDto> getJcaPositionDtoByCondition(JcaPositionSearchDto positionsearchdto, Pageable pagable) {
        return jcaPositionRepository.getJcaPositionDtoByCondition(positionsearchdto, pagable);
    }

    @Override
    public JcaPosition getPositionById(Long id) {
        return jcaPositionRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaPosition saveJcaPosition(JcaPosition jcaPosition) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaPosition.getId();
        if (null != id) {
            JcaPosition oldJcaPosition = jcaPositionRepository.findOne(id);
            if (null != oldJcaPosition) {
                jcaPosition.setCreatedDate(oldJcaPosition.getCreatedDate());
                jcaPosition.setCreatedId(oldJcaPosition.getCreatedId());
                jcaPosition.setUpdatedDate(sysDate);
                jcaPosition.setUpdatedId(userId);
                jcaPositionRepository.update(jcaPosition);
            }

        } else {
            jcaPosition.setCreatedDate(sysDate);
            jcaPosition.setCreatedId(userId);
            jcaPosition.setUpdatedDate(sysDate);
            jcaPosition.setUpdatedId(userId);
            jcaPositionRepository.create(jcaPosition);
        }
        return jcaPosition;
    }

    @Override
    public JcaPosition saveJcaPositionDto(JcaPositionDto jcaPositionDto) {
        
    	/** BEGIN save menu info */
    	JcaPosition jcaPosition = objectMapper.convertValue(jcaPositionDto, JcaPosition.class);
        jcaPosition.setId(jcaPositionDto.getPositionId());
        // save data
        jcaPosition = this.saveJcaPosition(jcaPosition);
    	jcaPositionDto.setPositionId(jcaPosition.getId());
        /** END */
        
        /** BEGIN save org path */
        // delete org path
        jcaPositionPathService.deletePositionPathByDescendantId(jcaPosition.getId());
        this.savePositionPath(jcaPositionDto);
        /** END */
        
        return jcaPosition;
    }

    @Override
    public JcaPositionDto getJcaPositionDtoById(Long id) {
        return jcaPositionRepository.getJcaPositionDtoById(id);
    }

    @Override
    public List<JcaPositionDto> getJcaPositionDtoToBuildTree() {
        return jcaPositionRepository.getJcaPositionDtoToBuildTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaPositionById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if (null != id) {
            JcaPosition jcaPosition = jcaPositionRepository.findOne(id);
            if (null != jcaPosition) {
                jcaPosition.setDeletedDate(sysDate);
                jcaPosition.setDeletedId(userId);
                jcaPositionRepository.update(jcaPosition);
            }

        }
    }
    
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void savePositionPath(JcaPositionDto jcaPositionDto) {
		// save position path

		List<JcaPositionDto> datas = this.getJcaPositionDtoToBuildTree();
		TreeBuilder<JcaPositionDto> builder = new TreeBuilder(datas);
		List<JcaPositionDto> listTree = builder.getParentBySub(jcaPositionDto);
		int depth = CommonConstant.NUMBER_ZERO;

		// save position path leaf
		JcaPositionPathDto jcaPositionPathLeafDto = new JcaPositionPathDto();
		jcaPositionPathLeafDto.setAncestorId(jcaPositionDto.getPositionId());
		jcaPositionPathLeafDto.setDescendantId(jcaPositionDto.getPositionId());
		jcaPositionPathLeafDto.setDepth(depth);
		jcaPositionPathService.saveJcaPositionPathDto(jcaPositionPathLeafDto);

		// save position path parent
		if (CommonCollectionUtil.isNotEmpty(listTree)) {
			// add leaf
			listTree.add(0, jcaPositionDto);
			// save path parent
			for (JcaPositionDto tree : listTree) {
				depth++;
				JcaPositionPathDto jcaPositionPathDto = new JcaPositionPathDto();
				jcaPositionPathDto.setAncestorId(tree.getPositionParentId());
				jcaPositionPathDto.setDescendantId(jcaPositionDto.getPositionId());
				jcaPositionPathDto.setDepth(depth);
				jcaPositionPathService.saveJcaPositionPathDto(jcaPositionPathDto);
			}
		} else {
			// save path root
			JcaPositionPathDto jcaPositionPathRootDto = new JcaPositionPathDto();
			jcaPositionPathRootDto.setAncestorId(jcaPositionDto.getPositionParentId());
			jcaPositionPathRootDto.setDescendantId(jcaPositionDto.getPositionId());
			jcaPositionPathRootDto.setDepth(CommonConstant.NUMBER_ONE);
			jcaPositionPathService.saveJcaPositionPathDto(jcaPositionPathRootDto);
		}
	}

}
