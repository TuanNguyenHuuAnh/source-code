/*******************************************************************************
 * Class        BranchService
 * Created date 2017/03/10
 * Lasted date  2017/03/10
 * Author       TranLTH
 * Change log   2017/03/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

//import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchSearchDto;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.cms.admin.all.jcanary.dto.BranchSearchDto;
//import vn.com.unit.common.dto.PageWrapper;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.dto.BranchSearchDto;

/**
 * BranchService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface BranchService {
    /**
     * searchBranchByCondition
     *
     * @param branchDto
     * @return List<BranchDto>
     * @author TranLTH
     */
    public List<BranchDto> searchBranchByCondition(BranchDto branchDto);

    /**
     * updateBranch
     *
     * @param branchDto
     * @author TranLTH
     * @throws Exception
     */
    public void addOrEditBranch(BranchDto branchDto) throws Exception;

    /**
     * deleteBranch
     *
     * @param branchId
     * @throws Exception
     * @author TranLTH
     */
    public void deleteBranch(Long branchId) throws Exception;

    /**
     * getBranchDto
     *
     * @param branchId
     * @return
     * @author TranLTH
     */
    public BranchDto getBranchDto(Long branchId);

    /**
     * search
     *
     * @param page
     * @param branchSearchDto
     * @return PageWrapper<BranchDto>
     * @author TranLTH
     */
    public PageWrapper<BranchDto> search(int page, BranchSearchDto branchSearchDto);

    /**
     * findByCode
     *
     * @param code
     * @return
     * @author TranLTH
     */
    public BranchDto findByCode(String code);

    /**
     * initScreenTypeList
     *
     * @param mav
     * @author TranLTH
     */
    public void initScreenTypeList(ModelAndView mav);

    /**
     * findBranchListByTypeAndCity
     * 
     * @param locationType
     * @param cityName
     * @param keyword
     * @return List<BranchDto>
     * @author hand
     */
//    public List<BranchDto> findBranchListByTypeAndCity(String locationType, String cityName, String keyword);
}
