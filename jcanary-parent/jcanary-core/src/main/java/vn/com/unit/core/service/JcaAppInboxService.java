/*******************************************************************************
 * Class        ：JcaAppInboxService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.entity.JcaAppInbox;

/**
 * JcaAppInboxService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAppInboxService {
    
    /** The Constant TABLE_ALIAS_JCA_APP_INBOX. */
    static final String TABLE_ALIAS_JCA_APP_INBOX = "app_inbox";
    
    /**
     * <p>
     * Save jca app inbox dto.
     * </p>
     *
     * @param appInboxDto
     *            type {@link JcaAppInboxDto}
     * @return {@link JcaAppInboxDto}
     * @author TrieuVD
     */
    public JcaAppInboxDto saveJcaAppInboxDto (JcaAppInboxDto appInboxDto);
    
    /**
     * <p>
     * Save jca app inbox.
     * </p>
     *
     * @param appInbox
     *            type {@link JcaAppInbox}
     * @return {@link JcaAppInbox}
     * @author SonND
     */
    public JcaAppInbox saveJcaAppInbox (JcaAppInbox appInbox);
    
    /**
     * <p>
     * Count jca app inbox.
     * </p>
     *
     * @return {@link int}
     * @author SonND
     */
    public int countJcaAppInbox();
    
    /**
     * <p>
     * Get jca app inbox dto.
     * </p>
     *
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaAppInboxDto>}
     * @author SonND
     */
    public List<JcaAppInboxDto> getJcaAppInboxDto(Pageable pageable);
    
    /**
     * <p>
     * Get jca app inbox dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @return {@link JcaAppInboxDto}
     * @author SonND
     */
    public JcaAppInboxDto getJcaAppInboxDtoByAppInboxId(Long appInboxId);
    
    /**
     * <p>
     * Delete jca app inbox dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @author SonND
     */
    public void deleteJcaAppInboxDtoByAppInboxId(Long appInboxId);
    
    /**
     * <p>
     * Delete all jca app inbox dto.
     * </p>
     *
     * @author SonND
     */
    public void deleteAllJcaAppInboxDto();
    
    /**
     * <p>
     * Read jca app inbox by id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @param readFlag
     *            type {@link boolean}
     * @author SonND
     */
    public void readJcaAppInboxById(Long appInboxId, boolean readFlag);
    
    /**
     * <p>
     * Read all jca app inbox.
     * </p>
     *
     * @param readFlag
     *            type {@link boolean}
     * @author SonND
     */
    public void readAllJcaAppInbox(boolean readFlag);
}
