/*******************************************************************************
 * Class        PositionService
 * Created date 2020/12/01
 * Lasted date  2020/12/01
 * Author       MinhNV
 * Change log   2020/12/01 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.PositionAddReq;
import vn.com.unit.ep2p.dto.req.PositionUpdateReq;
import vn.com.unit.ep2p.dto.res.PositionInfoRes;

/**
 * PositionService
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface PositionService extends BaseRestService<ObjectDataRes<JcaPositionDto>, JcaPositionDto> {


    int countJcaPositionDtoByCondition(JcaPositionSearchDto jcaPositionSearchDto);

    List<JcaPositionDto> getJcaPositionDtoByCondition(JcaPositionSearchDto jcaPositionSearchDto, Pageable pagable);

    /**
     * <p>
     * create position
     * </p>
     * 
     * @param PositionAddReq
     * @return PositionInfoRes
     * @author MinhNV
     */
    PositionInfoRes create(PositionAddReq reqPositionAddDto) throws DetailException;

    /**
     * <p>
     * update position
     * </p>
     * 
     * @param PositionUpdateReq
     * @author MinhNV
     */
    void update(PositionUpdateReq reqPositionUpdateDto) throws DetailException;
    

    /**
     * <p>
     * get info an Position
     * </p>
     * 
     * @param id
     * @return PositionInfoRes
     * @author MinhNV
     */
    PositionInfoRes getPositionInfoById(Long id) throws DetailException;
    
   
    List<TreeObject<JcaPositionDto>> getListJcaPositionDto() throws DetailException;

}
