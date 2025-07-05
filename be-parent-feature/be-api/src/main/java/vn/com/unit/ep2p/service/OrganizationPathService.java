/*******************************************************************************
 * Class        :OrganizationPathService
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.OrganizationPathInfoReq;
import vn.com.unit.ep2p.dto.res.OrganizationPathInfoRes;

/**
 * <p>
 * OrganizationPathService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface OrganizationPathService {

   
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param organizationPathInfoReq
     *            type {@link OrganizationPathInfoReq}
     * @return {@link JcaOrganizationPathDto}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    public JcaOrganizationPathDto create(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException;

    

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param organizationPathInfoReq
     *            type {@link OrganizationPathInfoReq}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    public void update(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException; 
    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaOrganizationPathDto
     *            type {@link JcaOrganizationPathDto}
     * @return {@link JcaOrganizationPathDto}
     * @author SonND
     */
    public JcaOrganizationPathDto save(JcaOrganizationPathDto jcaOrganizationPathDto);


    /**
     * <p>
     * Gets the detail dto by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @return the detail dto by descendant id
     * @author SonND
     */
    public JcaOrganizationPathDto getDetailDtoByDescendantId(Long descendantId);

   
    /**
     * <p>
     * Gets the detail dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the detail dto by id
     * @author SonND
     */
    public JcaOrganizationPathDto getDetailDtoById(Long id);
    
    /**
     * <p>
     * Gets the organization path info res by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the organization path info res by id
     * @author SonND
     */
    public OrganizationPathInfoRes getOrganizationPathInfoResById(Long id);
    
  
    /**
     * <p>
     * Gets the organization path info res by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @return the organization path info res by descendant id
     * @author SonND
     */
    public OrganizationPathInfoRes getOrganizationPathInfoResByDescendantId(Long descendantId);

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param organizationPathInfoReq
     *            type {@link OrganizationPathInfoReq}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    public void delete(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException; 
    
    
    /**
     * <p>
     * Delete organization path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    public void deleteOrganizationPathByDescendantId(Long descendantId) throws DetailException; 
}
