/*******************************************************************************
 * Class        ：ProductConsultingInforRepository
 * Created date ：2017/09/01
 * Lasted date  ：2017/09/01
 * Author       ：hand
 * Change log   ：2017/09/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

//import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.cms.admin.all.dto.ExportProductConsultReportDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingInforSearchDto;
import vn.com.unit.cms.admin.all.entity.ProductConsultingInfor;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.db.repository.DbRepository;

/**
 * ProductConsultingInforRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductConsultingInforRepository extends DbRepository<ProductConsultingInfor, Long> {

    /**
     * findProductConsultingInforList
     *
     * @param startIndex
     * @param sizeOfPage
     * @param condition
     * @return List<ProductConsultingInforSearchDto>
     * @author hand
     */
    public List<ProductConsultingInforSearchDto> findProductConsultingInforList(@Param("offset") int startIndex,
            @Param("sizeOfPage") int sizeOfPage, @Param("searchCond") ProductConsultingInforSearchDto condition);

    /**
     * countProductConsultingInforList
     *
     * @param condition
     * @return count
     * @author hand
     */
    public Integer countProductConsultingInforList(@Param("searchCond") ProductConsultingInforSearchDto condition);

    /**
     * getProductConsultingInforDto
     *
     * @param id
     * @param languageCode
     * @return ProductConsultingInforSearchDto
     * @author hand
     */
    public ProductConsultingInforSearchDto findProductConsultingInforDto(@Param("id") Long id,
            @Param("languageCode") String languageCode);

    /**
     * findListProductByCustomerId
     *
     * @param customerId
     * @param languageCode
     * @return productListJsonStr
     * @author hand
     */
    public List<SelectItem> findProductSelectByCustomerId(@Param("customerId") Long customerId,
            @Param("languageCode") String languageCode);
    
	public List<ExportProductConsultReportDto> exportExcelWithCondition(
            @Param("searchCond") ProductConsultingInforSearchDto searchDto);

}
