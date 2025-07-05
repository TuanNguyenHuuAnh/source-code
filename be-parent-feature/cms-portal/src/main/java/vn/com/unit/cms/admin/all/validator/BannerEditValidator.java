/*******************************************************************************
 * Class        ：BannerEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.hdb.admin.dto.BannerEditDto;
//import vn.com.unit.hdb.admin.entity.Banner;
//import vn.com.unit.hdb.admin.service.BannerService;

/**
 * BannerEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class BannerEditValidator implements Validator {

    @Autowired
    private BannerService bannerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return BannerEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // BannerEditDto
        BannerEditDto dto = (BannerEditDto) target;

        // check code not no special characters and spaces
//        checkSpecialCharacters(dto.getCode(), errors);

        // check exists banner code
        if(dto.getId() == null) {
            checkExistsCode(dto, errors);
        }
    }

    /**
     * check code not no special characters and spaces
     *
     * @param code
     * @param errors
     * @author hand
     */
//    private void checkSpecialCharacters(String code, Errors errors) {
//        if (!code.matches("[a-zA-Z0-9-_/s.]+?[0-9]")) {
//            String[] errorArgs = new String[1];
//            errorArgs[0] = code;
//            errors.rejectValue("code", "data.validate.code", errorArgs, ConstantCore.EMPTY);
//        }
//    }

    /**
     * check exists banner code
     *
     * @param dto
     * @param errors
     * @author hand
     */
    private void checkExistsCode(BannerEditDto dto, Errors errors) {
        String code = dto.getCode();

        Banner entity = bannerService.findByCode(code);
        if (entity != null) {
            Long bannerId = dto.getId();
            Long idDb = entity.getId();

            // Check unique bankNo
            if (bannerId == null || !bannerId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.banner.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
}