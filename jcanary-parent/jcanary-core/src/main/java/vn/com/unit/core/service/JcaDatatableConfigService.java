/*******************************************************************************
 * Class        ：JcaDatatableConfigService
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.entity.JcaDatatableConfig;
import vn.com.unit.core.req.DatatableHeaderReq;

/**
 * JcaDatatableConfigService
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public interface JcaDatatableConfigService {

    /**
     * findConfigByRequest
     * 
     * @param requestConfig
     * @return
     * @author vinhlt
     */
    JcaDatatableConfig findConfigByRequest(DatatableHeaderReq requestConfig);

    /**
     * createOrUpdate
     * 
     * @param datatableHeaderReq
     * @author vinhlt
     * @return
     */
    int createOrUpdate(DatatableHeaderReq datatableHeaderReq);

    /**
     * <p>
     * Find config default by request.
     * </p>
     *
     * @param datatableHeaderReq
     *            type {@link DatatableHeaderReq}
     * @return {@link JcaDatatableConfig}
     * @author tantm
     */
    JcaDatatableConfig findConfigDefaultByRequest(DatatableHeaderReq datatableHeaderReq);

    public List<JcaDatatableDefaultConfigDto> getListJcaDatatableDefaultConfigDto(String functionCode, String langCode);
    
    public List<String> getListCoLumn(String functionCode, String langCode);
}
