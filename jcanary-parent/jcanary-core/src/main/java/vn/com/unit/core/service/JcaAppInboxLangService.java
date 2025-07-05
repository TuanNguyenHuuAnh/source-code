/*******************************************************************************
 * Class        ：JcaAppInboxLangService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import vn.com.unit.core.dto.JcaAppInboxLangDto;
import vn.com.unit.core.entity.JcaAppInboxLang;

/**
 * JcaAppInboxLangService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaAppInboxLangService {
    
    /**
     * <p>
     * Save jca app inbox lang dto.
     * </p>
     *
     * @param jcaAppInboxLangDto
     *            type {@link JcaAppInboxLangDto}
     * @return {@link JcaAppInboxLangDto}
     * @author SonND
     */
    public JcaAppInboxLangDto saveJcaAppInboxLangDto(JcaAppInboxLangDto jcaAppInboxLangDto);
    
    /**
     * <p>
     * Save jca app inbox lang.
     * </p>
     *
     * @param jcaAppInboxLang
     *            type {@link JcaAppInboxLang}
     * @return {@link JcaAppInboxLang}
     * @author SonND
     */
    public JcaAppInboxLang saveJcaAppInboxLang (JcaAppInboxLang jcaAppInboxLang);
    
    /**
     * <p>
     * Get jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @return {@link JcaAppInboxLangDto}
     * @author SonND
     */
    public JcaAppInboxLangDto getJcaAppInboxLangDtoByAppInboxId(Long appInboxId);
    
    /**
     * <p>
     * Delete jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @author SonND
     */
    public void deleteJcaAppInboxLangDtoByAppInboxId(Long appInboxId);
    
    /**
     * <p>
     * Delete all jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @author SonND
     */
    public void deleteAllJcaAppInboxLangDtoByAppInboxId(Long userId);
}
