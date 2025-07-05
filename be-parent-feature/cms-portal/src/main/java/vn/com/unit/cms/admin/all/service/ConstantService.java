/*******************************************************************************
 * Class        ：ConstantService
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.ConstantDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * ConstantService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface ConstantService {
    /**
     * initScreenConstantList
     *
     * @param mav
     * @author TranLTH
     */
    public void initScreenConstantList(ModelAndView mav); 
    /**
     * addOrEditConstant
     *
     * @param constantDto
     * @author TranLTH
     */
    public void addOrEditConstant(ConstantDto constantDto) ;
    /**
     * delete
     *
     * @param constantId
     * @author TranLTH
     */
    public void delete(Long constantId);
    /**
     * search
     *
     * @param page
     * @param constantDto
     * @return
     * @author TranLTH
     */
    public PageWrapper<ConstantDto> search(int page, ConstantDto constantDto);
    /**
     * findByType
     *
     * @param type
     * @return
     * @author TranLTH
     */
    public List<ConstantDto> findByType (String type, String languageCode);
    /**
     * getConstantDto
     *
     * @param constantId
     * @return
     * @author TranLTH
     */
    public ConstantDto getConstantDto(Long constantId);
}