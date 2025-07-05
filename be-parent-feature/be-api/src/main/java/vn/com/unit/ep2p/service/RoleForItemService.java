/*******************************************************************************
 * Class        ：RoleForItemService
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.RoleForItemInfoReq;
import vn.com.unit.ep2p.dto.req.RoleForItemAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForItemAddReq;
import vn.com.unit.ep2p.dto.res.RoleForItemInfoRes;

/**
 * RoleForItemService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface RoleForItemService{
    
    void saveRoleForItemAddReq(RoleForItemAddReq roleForItemAddReq) throws DetailException;

    void saveListRoleForItem(RoleForItemAddListReq roleForItemAddListReq) throws DetailException;
  
    /**
     * Gets the role for company info res by id.
     *
     * @param id
     *            the id
     * @return the role for company info res by id
     * @throws Exception
     *             the exception
     */ 
    RoleForItemInfoRes getRoleForItemInfoResByRoleId(Long roleId, Long companyId) throws DetailException;

    /**
     * authorityDtoListToAuthorityList.
     *
     * @param authorityDtoLst
     *            the authority dto lst
     * @return the list
     * @author ngannh
     */
    public List<JcaAuthorityDto> authorityDtoListToAuthorityList(Long roleId, List<RoleForItemInfoReq> listRoleForItemInfoReq) throws DetailException;


}
