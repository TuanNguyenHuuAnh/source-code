/*******************************************************************************
 * Class        ：ProductConsultingInforService
 * Created date ：2017/09/01
 * Lasted date  ：2017/09/01
 * Author       ：hand
 * Change log   ：2017/09/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.ProductConsultingCommentDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingInforSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingUpdateItemDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * ProductConsultingInforService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductConsultingInforService {

    /**
     * find ProductConsultingInfor List
     *
     * @param conditon
     * @param page
     * @return PageWrapper<ProductConsultingInforSearchDto>
     * @author hand
     */
    public PageWrapper<ProductConsultingInforSearchDto> findList(ProductConsultingInforSearchDto conditon, int page);

    /**
     * getProductConsultingInforDto
     *
     * @param id
     * @param languageCode
     * @return ProductConsultingInforSearchDto
     * @author hand
     */
    public ProductConsultingInforSearchDto getProductConsultingInforDto(Long id, Locale locale);
    
    /**
     * findListProductByCustomerId
     *
     * @param customerId
     * @param languageCode
     * @return productListJsonStr
     * @author hand
     */
    public String findProductListJsonByCustomerId(Long customerId, String languageCode);
    
    public void exportExcel(ProductConsultingInforSearchDto searchDto, HttpServletResponse res, Locale locale);
    
    public List<ProductConsultingUpdateItemDto> getUpdateHistory(Long ptoductConsultingId);
    
    public List<ProductConsultingCommentDto> getCommentOptions();
    
    public ProductConsultingInforSearchDto updateProductConsultingToProcessing(ProductConsultingInforSearchDto model, Long productConsutingId, Locale locale);
    
    public ProductConsultingInforSearchDto updateProductConsultingDone(ProductConsultingInforSearchDto model, Long productConsutingId, Locale locale);
    
    public ProductConsultingInforSearchDto updateProductConsultingReject(ProductConsultingInforSearchDto model, Long productConsutingId, Locale locale);
    
}
