/*******************************************************************************
 * Class        ：ProductCategoryEditValidator
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.entity.ProductCategory;
import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;

/**
 * ProductCategoryEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class ProductCategoryEditValidator implements Validator {

    /** productCategoryService */
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CustomerTypeService productTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCategoryEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // ProductCategoryEditDto
        ProductCategoryEditDto dto = (ProductCategoryEditDto) target;

        // check exists code of Product
        if (checkExistsProductCode(dto, errors)) {
            return;
        }

        // check exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }

        // check Exists CustomerType
        checkExistsCustomerType(dto.getCustomerTypeId(), errors);

    }

    /**
     * check Exists CustomerType
     *
     * @param typeId
     * @param errors
     * @author hand
     */
    private boolean checkExistsCustomerType(Long typeId, Errors errors) {
        if (null == typeId) {
            return false;
        }
        CustomerType productType = productTypeService.findById(typeId);
        if (null != productType.getDeleteDate()) {
            String[] errorArgs = new String[1];
            errorArgs[0] = productType.getName();
            errors.rejectValue("mCustomerTypeId", "message.error.customer.type.not.exists", errorArgs,
                    ConstantCore.EMPTY);
            return true;
        }
        return false;
    }

    /**
     * check exists code of Product
     *
     * @param dto
     *            ProductCategoryEditDto
     * @param errors
     * @author hand
     */
    private boolean checkExistsProductCode(ProductCategoryEditDto dto, Errors errors) {
        String code = dto.getCode();
        ProductCategory entity = productCategoryService.findByCode(code);
        if (entity != null) {
            Long id = dto.getId();
            Long idDb = entity.getId();

            // Check unique bankNo
            if (id == null || !id.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.product.category.code.existed", errorArgs,
                        ConstantCore.EMPTY);
                return true;
            }
        }

        return false;
    }

    /**
     * check exists alias
     *
     * @param dto
     *            ProductCategoryEditDto
     * @param errors
     */
    private boolean checkExistsAlias(ProductCategoryEditDto dto, Errors errors) {
        int numberError = 0;
        if (CollectionUtils.isNotEmpty(dto.getCategoryLanguageList())) {
            for (int i = 0, sz = dto.getCategoryLanguageList().size(); i < sz; i++) {
                ProductCategoryLanguageDto dtoLang = dto.getCategoryLanguageList().get(i);
                ProductCategoryLanguage productCategoryLanguage = productCategoryService.findByAliasAndCustomerId(dtoLang.getLinkAlias(), dtoLang.getLanguageCode(),
                        dto.getCustomerTypeId());
                if (!dtoLang.getLinkAlias().isEmpty() && productCategoryLanguage != null && !productCategoryLanguage.getProductCategoryId().equals(dto.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("categoryLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
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
