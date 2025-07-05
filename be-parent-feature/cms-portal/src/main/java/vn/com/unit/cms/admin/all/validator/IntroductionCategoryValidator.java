/*******************************************************************************
 * Class        ：IntroductionCategoryValidator
 * Created date ：2016/03/24
 * Lasted date  ：2016/03/24
 * Author       ：ThuyDTN
 * Change log   ：2016/02/20：01-00 ThuyDTN create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryLanguageDto;
import vn.com.unit.cms.admin.all.service.IntroductionCategoryService;

/**
 * IntroductionCategoryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author ThuyDTN
 */
@Component
public class IntroductionCategoryValidator implements Validator {
    
    @Autowired
    IntroductionCategoryService introductionCategoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return IntroductionCategoryDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IntroductionCategoryDto categoryDto = (IntroductionCategoryDto) target;
        validateLinkAlias(categoryDto, errors);
        
//        validateImageAndVideo(categoryDto, errors);
    }

//    private void validateImageAndVideo(IntroductionCategoryDto categoryDto, Errors errors) {
//        if ((!categoryDto.getImagePhysicalName().equals("") && !categoryDto.getBannerPhysicalVideo().equals(""))) {
//            errors.rejectValue("image.and.video", "introduction.category.image.and.video.error", null, ConstantCore.EMPTY);
//        }
//    }
    
    private void validateLinkAlias(IntroductionCategoryDto dto, Errors errors) {
        if (CollectionUtils.isNotEmpty(dto.getInfoByLanguages())) {
            for (int i = 0, sz = dto.getInfoByLanguages().size(); i < sz; i++) {
                IntroductionCategoryLanguageDto dtoLang = dto.getInfoByLanguages().get(i);

                int countSameAlias = introductionCategoryService.getCategoryCount(dtoLang.getLanguageCode(), dtoLang.getLinkAlias(), dtoLang.getId(), dto.getParentId());
                if (countSameAlias > 0) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("infoByLanguages[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);

                    if (dto.getIndexLangActive() == null) {
                        dto.setIndexLangActive(i);
                    }
                }
            }

        }
    }

}