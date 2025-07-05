/*******************************************************************************
 * Class        ：JcaEmailService
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.dto.EmailDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailSearchDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;

/**
 * JcaEmailService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaEmailService {
    
    public static final String TABLE_ALIAS_JCA_EMAIL= "email";

    /**
     * <p>
     * Send email.
     * </p>
     *
     * @param emailDto
     *            type {@link JcaEmailDto}
     * @sendEmailType sendEmailType==1 :(SEND DIRECT NO SAVE) sendEmailType==2 :(SEND DIRECT SAVE) sendEmailType==3 :(SEND BY BATCH)
     *                sendEmailType==null :(will get default value of system)
     * @return {@link EmailResultDto}
     * @author TrieuVD
     */
    public EmailResultDto sendEmail(JcaEmailDto emailDto);

    /**
     * <p>
     * Save jca email dto.
     * </p>
     *
     * @param jcaEmailDto
     *            type {@link JcaEmailDto}
     * @return {@link JcaEmailDto}
     * @author TrieuVD
     */
    public JcaEmailDto saveJcaEmailDto(JcaEmailDto jcaEmailDto);

    /**
     * <p>
     * Get jca email dto by search.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaEmailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaEmailDto>}
     */
    public List<JcaEmailDto> getJcaEmailDtoListBySearchDto(JcaEmailSearchDto searchDto, Pageable pageable);

    /**
     * <p>
     * Count jca email dto by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaEmailSearchDto}
     * @return {@link int}
     */
    public int countJcaEmailDtoBySearchDto(JcaEmailSearchDto searchDto);
    
    /**
     * <p>
     * Get jca email dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link List<JcaEmailDto>}
     */
    public JcaEmailDto getJcaEmailDtoById(Long id);
    
    public JcaEmailDto convertValue(JcaEmailTemplateDto templateDto);
    
    public String replaceParam(String content, Map<String, Object> mapData);

	/**
	 * @author VuNT
	 * @date 2021-10-20
	 * @description config send email AWS
	 */
	EmailResultDto sendEmailAws(JcaEmailDto emailDto, AmazonSimpleEmailService client) throws Exception;
}
