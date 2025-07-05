/*******************************************************************************
 * Class        ：ProductDetaiLanguageDto
 * Created date ：2017/06/15
 * Lasted date  ：2017/06/15
 * Author       ：hand
 * Change log   ：2017/06/15：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;

/**
 * ProductDetaiLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductDetaiLanguageDto {

    /** language code */
    private String languageCode;

    /** product detail list */
    @Valid
    private List<ProductDetailDto> productDetailList;

    /**
     * Get languageCode
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get productDetailList
     * @return List<ProductDetailDto>
     * @author hand
     */
    public List<ProductDetailDto> getProductDetailList() {
        return productDetailList;
    }

    /**
     * Set productDetailList
     * @param   productDetailList
     *          type List<ProductDetailDto>
     * @return
     * @author  hand
     */
    public void setProductDetailList(List<ProductDetailDto> productDetailList) {
        this.productDetailList = productDetailList;
    }
    
}
