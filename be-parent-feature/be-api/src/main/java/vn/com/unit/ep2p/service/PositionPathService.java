/*******************************************************************************
 * Class        :PositionPathService
 * Created date :2020/12/25
 * Lasted date  :2020/12/25
 * Author       :SonND
 * Change log   :2020/12/25:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.PositionPathInfoReq;
import vn.com.unit.ep2p.dto.res.PositionPathInfoRes;

/**
 * <p>
 * PositionPathService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface PositionPathService {

    public JcaPositionPathDto create(JcaPositionPathDto jcaPositionPathDto) throws DetailException;

    public void update(JcaPositionPathDto jcaPositionPathDto) throws DetailException; 
   
    public JcaPositionPathDto save(JcaPositionPathDto jcaPositionPathDto);

    public JcaPositionPathDto getDetailDtoByDescendantId(Long descendantId);

    public JcaPositionPathDto getDetailDtoById(Long id);
    
    public PositionPathInfoRes getPositionPathInfoResById(Long id);
    
    public JcaPositionPathDto getJcaPositionPathDtoByDescendantId(Long descendantId);

    public void delete(PositionPathInfoReq positionPathInfoReq) throws DetailException; 
    
    public void deletePositionPathByDescendantId(Long descendantId);
}
