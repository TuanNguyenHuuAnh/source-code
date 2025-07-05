/*******************************************************************************
 * Class        :JcaEmailTemplateRepository
 * Created date :2020/12/23
 * Lasted date  :2020/12/23
 * Author       :SonND
 * Change log   :2020/12/23:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaEmailTemplateLangDto;
import vn.com.unit.core.entity.JcaEmailTemplateLang;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaEmailTemplateRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaEmailTemplateLangRepository extends DbRepository<JcaEmailTemplateLang, Long> {
    
    List<JcaEmailTemplateLangDto> getJcaEmailTemplateLangDtoByEmailTemplateId(@Param("emailTemplateId") Long emailTemplateId);
    
    JcaEmailTemplateLangDto getJcaEmailTemplateLangDtoByIdAndLangCode(@Param("emailTemplateId") Long emailTemplateId, @Param("langCode") String langCode );

    @Modifying
    void deleteJcaEmailTemplateLangDtosByEmailTemplateId(@Param("emailTemplateId")Long emailTemplateId,@Param("userLoginId") Long userLoginId,@Param("sysDate") Date sysDate);
   
}