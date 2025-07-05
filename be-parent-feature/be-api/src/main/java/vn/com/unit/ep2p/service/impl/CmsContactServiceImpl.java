package vn.com.unit.ep2p.service.impl;

//import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import java.util.UUID;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.module.contact.dto.CmsContactDto;
import vn.com.unit.cms.core.module.contact.entity.CmsContact;
import vn.com.unit.cms.core.module.contact.repository.CmsContactRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.AttachFileEmailDto;
import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAttachFileEmailDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAttachFileEmailService;
//import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.constant.UrlConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.FileUtil;
//import vn.com.unit.ep2p.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.service.CmsContactService;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.JcaRepositoryService;
import vn.com.unit.storage.utils.FileStorageUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsContactServiceImpl implements CmsContactService {

	@Autowired
	private JcaEmailService jcaEmailService;

	@Autowired
	private CmsContactRepository cmsContactRepository;

	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private JcaRepositoryService jcaRepositoryService;

	@Autowired
	private SlaEmailTemplateService slaEmailTemplateService;
	@Autowired
	private JcaEmailTemplateService jcaEmailTemplateService;

	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private JcaAttachFileEmailService jcaAttachFileEmailService;
	
	private static final String CONTACT_EMAIL = "CONTACT_EMAIL";

	private Logger logger = LoggerFactory.getLogger(CmsContactServiceImpl.class);

	private EmailResultDto sendMail(CmsContactDto dto) throws IOException, DetailException {
		List<String> emailTo = new ArrayList<>();
		List<String> emailCc = new ArrayList<>();
		emailCc.add("hopthudaily@dai-ichi-life.com.vn");
		final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, null);

		// emailTo.add(jcaSystemConfigService.getValueByKey(dto.getTitle(),
		// UserProfileUtils.getCompanyId()));
		emailTo.add(jcaSystemConfigService.getValueByKey(dto.getTitle(), 2L));
		logger.error("NEED GET COMPANY ID FROM USER PROFILE UTILS WHEN FE DEVELOP");

		JcaEmailDto jcaEmailDto = new JcaEmailDto();
		jcaEmailDto.setSenderAddress(senderAddress);
		jcaEmailDto.setContentType("text/html; charset=utf-8");
		jcaEmailDto.setToString(String.join(",", emailTo));

		jcaEmailDto.setToAddress(emailTo);

		jcaEmailDto.setCcAddress(emailCc);

		jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());

		Map<String, Object> paramEmail = new HashMap<>();
		paramEmail.put("name", dto.getName());
		paramEmail.put("address", dto.getAddress());
		paramEmail.put("phone", dto.getPhone());
		paramEmail.put("agentCode", dto.getAgentCode());
		paramEmail.put("email", dto.getEmail());
		paramEmail.put("content", dto.getContent());
		// get template email
		JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(CONTACT_EMAIL);

		if (templateDto == null) {
			throw new DetailException(String.format("Template not found: %s", CONTACT_EMAIL));
		}

		List<AttachFileEmailDto> attachFile = new ArrayList<>();

		JcaRepositoryDto repository = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN",
				UserProfileUtils.getCompanyId());
		if (dto.getFile() != null) {
			String image ="";
			String imageBase64 = "";
			for (MultipartFile multipartFile : dto.getFile()) {
				JcaAttachFileEmailDto attachFileEmailDto = new JcaAttachFileEmailDto();
				attachFileEmailDto.setFileName(
						FileUtil.deAccent(multipartFile.getOriginalFilename()).toLowerCase().replace(" ", "-"));
				attachFileEmailDto.setContentType(multipartFile.getContentType());
				attachFileEmailDto.setFileByte(multipartFile.getBytes());
				attachFileEmailDto.setFileSize(multipartFile.getSize());
				attachFileEmailDto.setFileType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
				attachFileEmailDto.setUuidEmail(dto.getUuid());
				jcaAttachFileEmailService.saveJcaAttachFileEmailDto(attachFileEmailDto);
				attachFile.add(attachFileEmailDto);
				imageBase64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());
				String pathOut = Paths.get(repository.getPhysicalPath(), attachFileEmailDto.getUuidEmail(), attachFileEmailDto.getFileName()).toString();
				image = "<p><a href='"+dto.getPatch()+"'>"+attachFileEmailDto.getFileName()+"</a></>";
			}
			jcaEmailDto.setNumberAttachFile(1);
			jcaEmailDto.setUuidEmail(dto.getUuid());
		}
		String content = slaEmailTemplateService.replaceParam(templateDto.getTemplateContent(), paramEmail);
		jcaEmailDto.setEmailContent(content);

		jcaEmailDto.setSubject("D-Success - " + dto.getTitleName());

		jcaEmailDto.setAttachFile(attachFile);

		return jcaEmailService.sendEmail(jcaEmailDto);
	}

	@Override
	public CmsContactDto create(CmsContactDto dto, HttpServletRequest request) throws IOException {
		if (dto.getFile() != null) {
			UUID emailUuidAa = UUID.randomUUID();
			for (MultipartFile multipartFile : dto.getFile()) {
				String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
				if (!"jpeg".equals(extension) && !"jpg".equals(extension) 
						&& !"png".equals(extension) && !"heic".equals(extension) 
						&& !"jfif".equals(extension) && !"pdf".equals(extension)) {
		            throw new IOException("File không hợp lệ.");
				}
				JcaRepositoryDto repository = jcaRepositoryService.getJcaRepositoryDto("REPO_TEMP",
						UserProfileUtils.getCompanyId());

				String folderPath = repository.getPhysicalPath() + "/" + emailUuidAa.toString();
				folderPath = folderPath.replace("//", "/");

				FileStorageUtils.createDirectoryNotExists(folderPath);

				byte[] bytes = multipartFile.getBytes();

				Path path = Paths.get(folderPath, multipartFile.getOriginalFilename().toLowerCase());
				Files.write(path, bytes);
				dto.setPatch(CmsUtils.getBaseUrl(request).replace(":80", "")+ AppApiConstant.API_V1 + AppApiConstant.API_APP + "/downloadFile"
	                    + UrlConstant.FILE_NAME + "CONTACT/" + emailUuidAa.toString()+"/" + multipartFile.getOriginalFilename());
			}
			dto.setUuid(emailUuidAa.toString());
		}
		dto.setCreatedId(1L);
		logger.error("NEED GET USER ID FROM USER PROFILE UTILS WHEN FE DEVELOP");

		dto.setCreatedDate(new Date());

		ModelMapper modelMapper = new ModelMapper();
		CmsContact cmsContact = modelMapper.map(dto, CmsContact.class);
		cmsContact.setSendMail(0);

		try {
			EmailResultDto emailResultDto = sendMail(dto);

			if (!emailResultDto.isStatus()) {
				throw new BusinessException("Can't send email");
			} else {
				cmsContact.setSendMail(1);
			}
		} catch (Exception e) {
			logger.error("vn.com.unit.ep2p.service.impl.CmsContactServiceImpl.create", e);
			throw new BusinessException("Can't send email");
		}

		CmsContact cmsContactResult = cmsContactRepository.save(cmsContact);

		return modelMapper.map(cmsContactResult, CmsContactDto.class);
	}

}
