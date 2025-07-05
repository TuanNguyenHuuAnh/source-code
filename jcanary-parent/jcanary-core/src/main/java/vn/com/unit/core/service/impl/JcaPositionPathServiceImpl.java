/*******************************************************************************
 * Class        :JcaPositionPathServiceImpl
 * Created date :2020/12/25
 * Lasted date  :2020/12/25
 * Author       :SonND
 * Change log   :2020/12/25 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.core.entity.JcaPositionPath;
import vn.com.unit.core.repository.JcaPositionPathRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaPositionPathService;
import vn.com.unit.core.service.JcaPositionService;

/**
 * JcaPositionPathServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaPositionPathServiceImpl implements JcaPositionPathService {

    @Autowired
    private JcaPositionPathRepository jcaPositionPathRepository;
    
    @Autowired
    private JcaPositionService jcaPositionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaPositionPath saveJcaPositionPath(JcaPositionPath jcaPositionPath) {
        Date systemDate = CommonDateUtil.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long ancestorId = jcaPositionPath.getAncestorId();
        Long descendantId = jcaPositionPath.getDescendantId();
        if (null != ancestorId && null != descendantId) {
            JcaPositionPath oldJcaPositionPath = this.getJcaPositionPathById(ancestorId, descendantId);
            if (null == oldJcaPositionPath) {
                jcaPositionPath.setCreatedDate(systemDate);
                jcaPositionPath.setCreatedId(userId);
                jcaPositionPathRepository.create(jcaPositionPath);
            }
        } 
        return jcaPositionPath;
    }

    @Override
    public JcaPositionPath getJcaPositionPathById(Long ancestorId, Long descendantId) {
        return jcaPositionPathRepository.findOne(ancestorId, descendantId);
    }

    @Override
    public JcaPositionPathDto getJcaPositionPathDtoByDescendantId(Long descendantId) {
        return jcaPositionPathRepository.getJcaPositionPathDtoByDescendantId(descendantId);
    }

    @Override
    public JcaPositionPath saveJcaPositionPathDto(JcaPositionPathDto jcaPositionPathDto) {
        JcaPositionPath jcaPositionPath = objectMapper.convertValue(jcaPositionPathDto, JcaPositionPath.class);
        this.saveJcaPositionPath(jcaPositionPath);
        return jcaPositionPath;
    }

    @Override
    public JcaPositionPathDto getJcaPositionPathDtoById(Long id) {
        return jcaPositionPathRepository.getJcaPositionPathDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePositionPathByDescendantId(Long descendantId) {
        jcaPositionPathRepository.deletePositionPathByDescendantId(descendantId);
        
    }

	@Override
	public void savePositionPath(JcaPositionDto jcaPositionDto) {
      //save position path
        if (jcaPositionDto.getPositionId() != null && jcaPositionDto.getPositionParentId() != null) {
            
            List<JcaPositionDto> datas = jcaPositionService.getJcaPositionDtoToBuildTree();
            TreeBuilder<JcaPositionDto> builder = new TreeBuilder<JcaPositionDto>(datas);
            List<JcaPositionDto> listTree = builder.getParentBySub(jcaPositionDto);
            int depth = CommonConstant.NUMBER_ZERO;
            
            // save position path leaf 
            JcaPositionPathDto jcaPositionPathLeafDto = new JcaPositionPathDto();
            jcaPositionPathLeafDto.setAncestorId(jcaPositionDto.getPositionId());
            jcaPositionPathLeafDto.setDescendantId(jcaPositionDto.getPositionId());
            jcaPositionPathLeafDto.setDepth(depth);
            this.saveJcaPositionPathDto(jcaPositionPathLeafDto);
            
            // save position path parent 
            if( CommonCollectionUtil.isNotEmpty(listTree)) {
             // add leaf
                listTree.add(0, jcaPositionDto);
                // save path parent
                for (JcaPositionDto tree : listTree) {
                    depth ++;
                    JcaPositionPathDto jcaPositionPathDto = new JcaPositionPathDto();
                    jcaPositionPathDto.setAncestorId(tree.getPositionParentId());
                    jcaPositionPathDto.setDescendantId(jcaPositionDto.getPositionId());
                    jcaPositionPathDto.setDepth(depth);
                    this.saveJcaPositionPathDto(jcaPositionPathDto);
                }
            }else {
                // save path root
                JcaPositionPathDto jcaPositionPathRootDto = new JcaPositionPathDto();
                jcaPositionPathRootDto.setAncestorId(jcaPositionDto.getPositionParentId());
                jcaPositionPathRootDto.setDescendantId(jcaPositionDto.getPositionId());
                jcaPositionPathRootDto.setDepth(CommonConstant.NUMBER_ONE);
                this.saveJcaPositionPathDto(jcaPositionPathRootDto);
            }
            
           
        } 
    }

}
