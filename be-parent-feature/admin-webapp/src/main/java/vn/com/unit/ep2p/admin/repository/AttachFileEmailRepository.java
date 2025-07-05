/*******************************************************************************
 * Class        AttachFileEmailRepository
 * Created date 2018/08/19
 * Lasted date  2018/08/19
 * Author       phatvt
 * Change log   2018/08/1901-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.repository.JcaAttachFileEmailRepository;

/**
 * AttachFileEmailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Primary
public interface AttachFileEmailRepository extends JcaAttachFileEmailRepository{
    /**
     * updateEmailId
     *
     * @param emailId
     * @param uuidEmail
     * @author phatvt
     */
    @Modifying
    public void updateEmailId(@Param("emailId") Long emailId, @Param("uuidEmail") String uuidEmail, @Param("companyId") Long companyId);
    /**
     * getAttachFileWithEmailId
     *
     * @param emailId
     * @return
     * @author phatvt
     */
    public List<JcaAttachFileEmail> getAttachFileWithEmailId(@Param("emailId") Long emailId);
    /**
     * getAttachFileWithEmailUUID
     *
     * @param emailUuid
     * @return
     * @author phatvt
     */
    public List<JcaAttachFileEmail> getAttachFileWithEmailUUID(@Param("emailUuid") String emailUuid);
    
    /**
     * sumAttachFileSizeByCondition
     * @param emailUuid
     * @param emailId
     * @return
     * @author trieuvd
     */
    public Long sumAttachFileSizeByCondition(@Param("emailUuid") String emailUuid, @Param("emailId") Long emailId);
}
