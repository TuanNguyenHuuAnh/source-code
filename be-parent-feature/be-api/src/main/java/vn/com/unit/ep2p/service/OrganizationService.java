/*******************************************************************************
 * Class        ：OrganizationService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：SonND
 * Change log   ：2020/12/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.common.tree.JSTree;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.TreeNodeDto;
import vn.com.unit.ep2p.dto.req.OrganizationAddReq;
import vn.com.unit.ep2p.dto.req.OrganizationUpdateReq;
import vn.com.unit.ep2p.dto.res.OrganizationInfoRes;

/**
 * OrganizationService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface OrganizationService {


    /**
     * <p>
     * Gets the list org.
     * </p>
     *
     * @return the list org
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    List<TreeObject<OrganizationInfoRes>> getListOrg() throws DetailException;


  
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param organizationAddReq
     *            type {@link OrganizationAddReq}
     * @return {@link JcaOrganizationDto}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    JcaOrganizationDto create(OrganizationAddReq organizationAddReq) throws DetailException;


    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaOrganizationDto
     *            type {@link JcaOrganizationDto}
     * @return {@link JcaOrganizationDto}
     * @author sonnd
     */
    public JcaOrganizationDto save(JcaOrganizationDto jcaOrganizationDto);


    /**
     * <p>
     * Gets the organization info res by id.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @return the organization info res by id
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    public OrganizationInfoRes getOrganizationInfoResById(Long orgId) throws DetailException;


    /**
     * <p>
     * Gets the detail dto.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @return the detail dto
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    public  JcaOrganizationDto getDetailDto(Long orgId) throws DetailException;


    /**
     * <p>
     * Update.
     * </p>
     *
     * @param organizationUpdateReq
     *            type {@link OrganizationUpdateReq}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    void update(OrganizationUpdateReq organizationUpdateReq) throws DetailException;


    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    void delete(Long orgId) throws DetailException;
    
    /**
     * <p>
     * Get list tree node.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @return {@link List<TreeNodeDto>}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public List<TreeNodeDto> getTreeNodeList(Long orgId) throws DetailException;



    /**
     * getListOrgTreeCombobox
     * @return
     * @throws DetailException
     * @author taitt
     */
    List<JSTree>  getListOrgTreeCombobox() throws DetailException;
}
