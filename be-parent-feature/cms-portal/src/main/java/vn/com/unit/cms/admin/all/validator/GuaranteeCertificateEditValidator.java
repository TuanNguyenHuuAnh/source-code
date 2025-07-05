/*******************************************************************************
 * Class        ：GuaranteeCertificateEditValidator
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateEditDto;
import vn.com.unit.cms.admin.all.entity.GuaranteeCertificate;
import vn.com.unit.cms.admin.all.service.GuaranteeCertificateService;

/**
 * GuaranteeCertificateEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

@Component
public class GuaranteeCertificateEditValidator implements Validator {

    @Autowired 
    GuaranteeCertificateService guaranteeCertificateService;

    @Override
    public boolean supports(Class<?> clazz) {
        return GuaranteeCertificateEditDto.class.equals(clazz) ;
    }

    @Override
    public void validate(Object target, Errors errors) {
      
        GuaranteeCertificateEditDto dto = (GuaranteeCertificateEditDto) target;
        
        //check exists guarantee certificate number
        checkExistsGuaranteeCertificateNumber(dto, errors);
    }

    /** checkExistsGuaranteeCertificateNumber
     *
     * @param dto
     * @param errors
     * @author hoangnp
     */
    private void checkExistsGuaranteeCertificateNumber(GuaranteeCertificateEditDto dto, Errors errors) {
        
        String certificateNumber = dto.getCertificateNumber();
        GuaranteeCertificate entity = guaranteeCertificateService.findCertificateNumber(certificateNumber);
        if(null != entity){
            Long guaranteeCertificateId = dto.getId();
            Long idDb = entity.getId();
            
            if (guaranteeCertificateId == null || !guaranteeCertificateId.equals(idDb)) {
            String[] errorArgs = new String[1];
            errorArgs[0] = certificateNumber;
            errors.rejectValue("certificateNumber", "message.error.guarantee.certificate.number.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
        
    }
}
