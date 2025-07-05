/*******************************************************************************
 * Class        ：JcaDatatableConfigRepository
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.entity.JcaDatatableConfig;
import vn.com.unit.core.req.DatatableHeaderReq;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaDatatableConfigRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public interface JcaDatatableConfigRepository extends DbRepository<JcaDatatableConfig, Long> {

    /**
     * findConfigByRequest
     * 
     * @param requestConfig
     * @return
     * @author vinhlt
     */
    public JcaDatatableConfig findConfigByRequest(@Param("requestConfig") DatatableHeaderReq requestConfig);

    /**
     * updateConfigByRequest
     * @param requestConfig
     * @return
     * @author vinhlt
     */
    @Modifying
    public int updateConfigByRequest(@Param("requestConfig") DatatableHeaderReq requestConfig);

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
    public JcaDatatableConfig findConfigDefaultByRequest(@Param("requestConfig") DatatableHeaderReq requestConfig);
    
    public List<JcaDatatableDefaultConfigDto> findListJcaDatatableDefaultConfigDto(
            @Param("functionCode") String functionCode, @Param("langCode") String langCode);
    
    public List<String> findListColumn(@Param("functionCode") String functionCode, @Param("langCode") String langCode);
}
