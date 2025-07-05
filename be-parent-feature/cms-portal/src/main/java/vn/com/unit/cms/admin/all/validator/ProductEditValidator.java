/*******************************************************************************
 * Class        ：ProductEditValidator
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.entity.Product;
import vn.com.unit.cms.admin.all.entity.ProductCategory;
import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.cms.admin.all.service.ProductService;

/**
 * ProductEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class ProductEditValidator implements Validator {

    /** productService */
    @Autowired
    ProductService productService;

    /** productTypeService */
    @Autowired
    CustomerTypeService customerTypeService;

    /** productCategoryService */
    @Autowired
    ProductCategoryService productCategoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // ProductEditDto
        ProductEditDto dto = (ProductEditDto) target;

        // check Product exists code
        if (checkExistsCode(dto, errors)) {
            return;
        }

        // check Product exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }

        // check exists CustomerType
        if (checkExistsCustomerType(dto.getTypeId(), errors)) {
            dto.setCategoryJsonHidden(null);
            return;
        }

        // check exists ProductCategory
        if (checkExistsProductCategory(dto.getCategoryId(), errors)) {
            dto.setCategoryJsonHidden(null);
            return;
        }
    }

    /**
     * check exists CustomerType
     *
     * @param typeId
     * @param errors
     * @author hand
     */
    private boolean checkExistsCustomerType(Long typeId, Errors errors) {
        if (null != typeId) {
            CustomerType productType = customerTypeService.findById(typeId);
            if (null != productType.getDeleteDate()) {
                String[] errorArgs = new String[1];
                errorArgs[0] = productType.getName();
                errors.rejectValue("typeId", "message.error.customer.type.not.exists", errorArgs, ConstantCore.EMPTY);
                return true;
            }
        }

        return false;
    }

    /**
     * check exists ProductCategory
     *
     * @param categoryId
     * @param errors
     * @author hand
     */
    private boolean checkExistsProductCategory(Long categoryId, Errors errors) {
        if (null != categoryId) {
            ProductCategory productCategory = productCategoryService.findById(categoryId);
            if (null != productCategory.getDeleteDate()) {
                String[] errorArgs = new String[1];
                errorArgs[0] = productCategory.getName();
                errors.rejectValue("categoryId", "message.error.product.category.not.exists", errorArgs,
                        ConstantCore.EMPTY);
                return true;
            }
        }

        return false;
    }

    /**
     * check Product exists code
     *
     * @param dto
     *            ProductEditDto
     * @param errors
     * @author hand
     */
    private boolean checkExistsCode(ProductEditDto dto, Errors errors) {
        String code = dto.getCode();
        Product entity = productService.findByCode(code);
        if (entity != null) {
            Long productId = dto.getId();
            Long idDb = entity.getId();

            // Check unique bankNo
            if (productId == null || !productId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.product.code.existed", errorArgs, ConstantCore.EMPTY);
                return true;
            }
        }
        return false;
    }

    /**
     * check exists alias
     *
     * @param dto
     *            ProductEditDto
     * @param errors
     * @author hand
     */
    private boolean checkExistsAlias(ProductEditDto dto, Errors errors) {
        int numberError = 0;
        if (CollectionUtils.isNotEmpty(dto.getProductLanguageList())) {
            for (int i = 0, sz = dto.getProductLanguageList().size(); i < sz; i++) {
                ProductLanguageDto dtoLang = dto.getProductLanguageList().get(i);
                ProductLanguage productLanguage = productService.findByAliasCustomerIdCategoryId(dtoLang.getLinkAlias(), dtoLang.getLanguageCode(), dto.getTypeId(), dto.getCategorySubId(), dto.getCategoryId());
                if (productLanguage != null && !productLanguage.getId().equals(dtoLang.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("productLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
                    numberError++;
                    
                    if (dto.getIndexLangActive() == null) {
                        dto.setIndexLangActive(i);
                    }
                }
            }

        }
        return numberError > 0;
    }
       
}
