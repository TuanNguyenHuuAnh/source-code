/*******************************************************************************
 * Class        JcaItemServiceImpl
 * Created date 2020/12/07
 * Lasted date  2020/12/07
 * Author       MinhNV
 * Change log   2020/12/07 01-00 MinhNV create a new
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
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.repository.JcaItemRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaItemServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaItemServiceImpl implements JcaItemService {

    /** The item repository. */
    @Autowired
    private JcaItemRepository itemRepository;

    /** The object mapper. */
    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#countJcaItemDtoByCondition(vn.com.
     * unit.core.dto.JcaItemSearchDto)
     */
    @Override
    public int countJcaItemDtoByCondition(JcaItemSearchDto itemSearchDto) {
        return itemRepository.countJcaItemDtoByCondition(itemSearchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#getJcaItemDtoByCondition(vn.com.unit.
     * core.dto.JcaItemSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<JcaItemDto> getJcaItemDtoByCondition(JcaItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getJcaItemDtoByCondition(itemSearchDto, pageable).getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaItemService#getItemById(java.lang.Long)
     */
    @Override
    public JcaItem getItemById(Long id) {
        return itemRepository.findOne(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#saveJcaItem(vn.com.unit.core.entity.
     * JcaItem)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaItem saveJcaItem(JcaItem jcaItem) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaItem.getId();
        if (null != id) {

            JcaItem oldJcaItem = itemRepository.findOne(id);
            if (null != oldJcaItem) {
                jcaItem.setCreatedDate(oldJcaItem.getCreatedDate());
                jcaItem.setCreatedId(oldJcaItem.getCreatedId());
                jcaItem.setUpdatedDate(sysDate);
                jcaItem.setUpdatedId(userId);
                itemRepository.update(jcaItem);
            }

        } else {
            jcaItem.setCreatedDate(sysDate);
            jcaItem.setCreatedId(userId);
            jcaItem.setUpdatedDate(sysDate);
            jcaItem.setUpdatedId(userId);
            itemRepository.create(jcaItem);
        }
        return jcaItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#saveJcaItemDto(vn.com.unit.core.dto.
     * JcaItemDto)
     */
    @Override
    public JcaItem saveJcaItemDto(JcaItemDto jcaItemDto) {
        JcaItem jcaItem = objectMapper.convertValue(jcaItemDto, JcaItem.class);
        jcaItem.setId(jcaItemDto.getItemId());

        // save data
        jcaItem = this.saveJcaItem(jcaItem);

        // update id
        jcaItemDto.setItemId(jcaItem.getId());
        return jcaItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#getJcaItemDtoById(java.lang.Long)
     */
    @Override
    public JcaItemDto getJcaItemDtoById(Long id) {
        return itemRepository.getJcaItemDtoById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#checkExitsItemByItemCodeAndCompanyId(
     * java.lang.String, java.lang.Long)
     */
    @Override
    public Boolean checkExitsItemByItemCodeAndCompanyId(String itemCode, Long companyId) {
        return itemRepository.checkExitsItemByItemCodeAndCompanyId(itemCode, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#getItemIdByItemCodeAndCompanyId(java.
     * lang.String, java.lang.Long)
     */
    @Override
    public Long getItemIdByItemCodeAndCompanyId(String itemCode, Long companyId) {
        return itemRepository.getItemIdByItemCodeAndCompanyId(itemCode, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaItemService#deletedJcaItemById(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletedJcaItemById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if (null != id) {
            JcaItem jcaItem = itemRepository.findOne(id);
            if (null != jcaItem) {
                jcaItem.setDeletedDate(sysDate);
                jcaItem.setDeletedId(userId);
                itemRepository.update(jcaItem);
            }
        }
    }

    @Override
    public boolean checkRoleItem(String itemCode) {
        if (CommonStringUtil.isBlank(itemCode)) {
            return false;
        }
//        if (UserProfileUtils.hasRole(itemCode) || UserProfileUtils.hasRole(itemCode + ConstantCore.COLON_EDIT)
//                || UserProfileUtils.hasRole(itemCode + CoreConstant.COLON_DISP)
//                || UserProfileUtils.hasRole(itemCode + CoreConstant.COLON_DELETE)) {
        return true;
//        }
//        return false;
    }

    @Override
    public int countJcaItemDtoByFunctionCode(String functionCode) {
        return itemRepository.countJcaItemDtoByFunctionCode(functionCode);
    }

    @Override
    public DbRepository<JcaItem, Long> initRepo() {
        // TODO Auto-generated method stub
        return null;
    }

//    public PageWrapper<JcaItemDto> search(int page, int pageSize, ItemManagementSearchDto searchDto) throws DetailException {
//        // TODO Auto-generated method stub
//        return null;
//    }

}
