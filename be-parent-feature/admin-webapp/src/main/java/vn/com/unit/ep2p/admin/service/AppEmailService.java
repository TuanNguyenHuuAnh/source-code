/*******************************************************************************
 * Class        MailService
 * Created date 2017/05/18
 * Lasted date  2017/05/18
 * Author       phunghn
 * Change log   2017/05/1801-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.ResponseEmailDto;
import vn.com.unit.ep2p.dto.EmailDto;
import vn.com.unit.ep2p.dto.EmailSearchDto;
import vn.com.unit.ep2p.dto.TemplateAttachDto;

/**
 * MailService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface AppEmailService {
	/**
	 * sendEmail
	 *
	 * @param require
	 *            : Subject && ReceiveAddress
	 * @send immediately : sendEmailType==2 or sendEmailType==1
	 * @sendEmailType sendEmailType==1 :(SEND DIRECT NO SAVE) sendEmailType==2
	 *                :(SEND DIRECT SAVE) sendEmailType==3 :(SEND BY BATCH)
	 *                sendEmailType==null :(will get default value of system)
	 * @return statusCode : status code send mail status : status send mail
	 *         message : Message exception
	 * @author phatvt
	 */
	ResponseEmailDto sendEmail(EmailDto mail);

	/**
	 * saveAttachFileEmail
	 *
	 * @param attachFile
	 * @author phatvt
	 */
	void saveAttachFileEmail(JcaAttachFileEmail attachFile);

	/**
	 * findAllEmail
	 *
	 * @return
	 * @author phatvt
	 */
	List<JcaEmail> findAllEmail();

	/**
	 * doSearch
	 *
	 * @param page
	 * @param searchDto
	 * @param pageSize
	 * @return
	 * @author phatvt
	 * @throws DetailException 
	 */
	PageWrapper<JcaEmailDto> doSearch(int page, EmailSearchDto searchDto, int pageSize) throws DetailException;

	/**
	 * getDetailEmail
	 *
	 * @param emailId
	 * @return
	 * @author phatvt
	 * @throws Exception
	 */
	JcaEmailDto getDetailEmail(Long emailId) throws Exception;

	/**
	 * getListFileAttach
	 *
	 * @param emailId
	 * @return
	 * @author phatvt
	 */
	List<JcaAttachFileEmail> getListFileAttach(Long emailId);

	/**
	 * getListEmail
	 *
	 * @param listStatus
	 * @return
	 * @author phatvt
	 */
	List<JcaEmail> getListEmail(List<String> listStatus);

	/**
	 * getContentFromTemplate
	 *
	 * @param model
	 * @param templateFile
	 * @return
	 * @author VinhLT
	 */
	String getContentFromTemplate(Map<String, Object> model, String templateFile);

	/**
	 * sendMailByTemplateId
	 *
	 * @param templateId
	 * 			type Long
	 * @param emailDto
	 * 			type EmailDto
	 * @param classObject
	 * 			type Class<T>
	 * @param itemObjectDto
	 * 			type Object
	 * @return ResponseEmailDto
	 * @author BaoHG
	 */
	/** classObject defined the class for the itemObjectDto */
	//public ResponseEmailDto sendMailByTemplateId(Long templateId, EmailDto emailDto, Class<T> classObject, Object itemObjectDto) throws Exception;
	
	/**
	 * totalSizeAttach
	 * @param emailUuid
	 * @return
	 * @author trieuvd
	 */
	public Long totalSizeAttach(String emailUuid);
	
	/**
	 * deleteAttachFileById
	 * @param Id
	 * @author trieuvd
	 */
	public void deleteAttachFileById(Long id);
	
	/**
	 * clearAttachFile
	 * @param emailUuid
	 * @author trieuvd
	 */
	public void clearAttachFile(String emailUuid);
	
	/**
	 * sendMailByTemplateIdAndJsonPram
	 * @param templateId
	 * @param emailDto 
	 *        - thong tin nguoi nhan: to, cc, bcc
	 *        - sendType: 1: Send direct (No archive), 2: Send direct (Archive), 3: Send by batch (Archive)
	 *        - companyId (null -> lay cong ty User)
	 *        - list attach (neu co)
	 * @param jsonParam chua thong tin param replace template
	 * @param templateAttach dinh kem template neu co 
	 * @return
	 * @author trieuvd
	 * @throws Exception 
	 */
	public ResponseEmailDto sendMailByTemplateIdAndJsonPram(Long templateId, EmailDto emailDto, String jsonParam, List<TemplateAttachDto> templateAttach, Locale locale, boolean isSendMail) throws Exception;
	
	/**
	 * sendMailByTemplateCodeAndJsonPram
	 * @param templateCode
	 * @param emailDto
	 * @param jsonParam
	 * @param templateAttach
	 * @param locale
	 * @param isSendMail
	 * @return
	 * @throws Exception
	 * @author trieuvd
	 */
	public ResponseEmailDto sendMailByTemplateCodeAndJsonPram(String templateCode, EmailDto emailDto, String jsonParam, List<TemplateAttachDto> templateAttach, Locale locale, boolean isSendMail) throws Exception;
	
	/**
	 * getListEmailByListStatusAndCompanyId
	 * @param listStatus
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<JcaEmail> getListEmailByListStatusAndCompanyId(List<String> listStatus, Long companyId);
}
