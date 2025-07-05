/*******************************************************************************
 * Class        RepositoryServiceImpl
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.ConstantCore;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.core.dto.RepositorySearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.enumdef.RepositorySearchEnum;
import vn.com.unit.core.repository.RepositoryRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AbstractCommonService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.utils.ExecMessage;
import vn.com.unit.core.utils.FileUtil;
import vn.com.unit.core.utils.PasswordUtil;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.enumdef.FileProtocol;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * RepositoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JRepositoryServiceImpl implements JRepositoryService, AbstractCommonService {

	@Autowired
	private JcaRepositoryService jcaRepositoryService;

	/** repositoryRepository */
	@Autowired
	private RepositoryRepository repositoryRepository;

	/** systemConfig */
	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CommonService comService;

	/** MessageSource */
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<JcaRepository> getRepositoryListByActive(int active) {
		List<JcaRepository> repositoryList = repositoryRepository.findRepositoryListByActive(active);

		if (repositoryList == null) {
			repositoryList = new ArrayList<>();
		}

		return repositoryList;
	}

	@Override
	public String getPathByRepository(String fileOrFolderName, String repositoryKey) {
		String physicalPath = this.getPathByRepository(repositoryKey);
		return Paths.get(physicalPath, fileOrFolderName).toString();
	}

	@Override
	public File getFileByRepository(String fileName, String repositoryKey) {
		String physicalPath = this.getPathByRepository(repositoryKey);
		return Paths.get(physicalPath, fileName).toFile();
	}

	@Override
	public String getPathByRepository(String repositoryKey) {
		String repositoryIdStr = systemConfig.getConfig(repositoryKey);
		String physicalPath = systemConfig.getPhysicalPathById(repositoryIdStr, null);
		return physicalPath;
	}

    @SuppressWarnings("static-access")
    @Override
    public PageWrapper<JcaRepositoryDto> searchByCondition(int page, RepositorySearchDto searchDto, int pageSize)
            throws DetailException {
        // Init PageWrapper
        PageWrapper<JcaRepositoryDto> pageWrapper = new PageWrapper<JcaRepositoryDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaAccount.class,
                jcaRepositoryService.TABLE_ALIAS_JCA_REPOSITORY);

        List<String> values = searchDto.getFieldValues();
        /** init param search repository */
        if (CollectionUtils.isNotEmpty(searchDto.getFieldValues())) {
            searchDto.setStrFieldValues(String.join(",", searchDto.getFieldValues()));
            searchDto.setFieldValues(null);
        } else {
            searchDto.setFieldValues(null);
        }

        /** init param search repository */
//      MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaRepositorySearchDto reqSearch = this.buildJcaRepositorySearchDto(searchDto, values);
        searchDto.setFieldValues(values);

        int count = jcaRepositoryService.countListJcaRepositoryByCondition(reqSearch);
        List<JcaRepositoryDto> result = new ArrayList<JcaRepositoryDto>();
        if (count > 0) {
            result = jcaRepositoryService.getListJcaRepositoryDtoByCondition(reqSearch, pageableAfterBuild);
        }
        pageWrapper.setSizeOfPage(sizeOfPage);
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

	private JcaRepositorySearchDto buildJcaRepositorySearchDto(RepositorySearchDto commonSearch, List<String> values) {

		JcaRepositorySearchDto reqSearch = new JcaRepositorySearchDto();
//	        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : null;
		Long companyId = null != commonSearch.getCompanyId() ? Long.valueOf(commonSearch.getCompanyId())
				: null;
//	        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
		String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFieldSearch())
				? commonSearch.getFieldSearch()
				: DtsConstant.EMPTY;
//		List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues"))
//				? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("fieldValues"), ","))
//				: null;

		reqSearch.setCompanyId(companyId);
		reqSearch.setLangCode(UserProfileUtils.getLanguage());
		if (CommonCollectionUtil.isNotEmpty(values)) {
			for (String enumValue : values) {
				switch (RepositorySearchEnum.valueOf(enumValue)) {
				case CODE:
					reqSearch.setCode(keySearch);
					break;
				case NAME:
					reqSearch.setName(keySearch);
					break;

				case PHYSICAL_PATH:
					reqSearch.setPhysicalPath(keySearch);
					break;
	            case SUB_FOLDER_RULE:
	                reqSearch.setSubFolderPath(keySearch);
	                break;

				default:
					reqSearch.setCode(keySearch);
					reqSearch.setName(keySearch);
					reqSearch.setPhysicalPath(keySearch);
					reqSearch.setSubFolderPath(keySearch);
					break;
				}
			}
		} else {
			reqSearch.setCode(keySearch);
			reqSearch.setName(keySearch);
			reqSearch.setPhysicalPath(keySearch);
			reqSearch.setSubFolderPath(keySearch);
		}
		reqSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
		return reqSearch;

	}
//	private void setSearchParm(RepositorySearchDto searchDto) {
//		String searchValue = searchDto.getFieldSearch();
//		List<String> fieldSearchList = searchDto.getFieldValues();
//
//		if (!StringUtils.isEmpty(searchValue)) {
//			searchValue = searchValue.trim();
//		}
//
//		if (null != fieldSearchList && !fieldSearchList.isEmpty()) {
//			for (String fieldSearch : fieldSearchList) {
//				if (StringUtils.equals(fieldSearch, RepositorySearchEnum.CODE.name())) {
//					searchDto.setCode(searchValue);
//					continue;
//				}
//				if (StringUtils.equals(fieldSearch, RepositorySearchEnum.NAME.name())) {
//					searchDto.setName(searchValue);
//					continue;
//				}
//				if (StringUtils.equals(fieldSearch, RepositorySearchEnum.PHYSICAL_PATH.name())) {
//					searchDto.setPhysicalPath(searchValue);
//					continue;
//				}
//				if (StringUtils.equals(fieldSearch, RepositorySearchEnum.SUB_FOLDER_RULE.name())) {
//					searchDto.setSubFolderRule(searchValue);
//					continue;
//				}
//			}
//		} else {
//			searchDto.setCode(searchValue);
//			searchDto.setName(searchValue);
//			searchDto.setPhysicalPath(searchValue);
//			searchDto.setSubFolderRule(searchValue);
//		}
//		// Add company_id
//		//searchDto.setCompanyId(UserProfileUtils.getCompanyId());
////		searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
////		searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
//	}

	@Override
	public JcaRepositoryDto getRepositoryDtoById(Long id) {
		JcaRepositoryDto repositoryDto = new JcaRepositoryDto();
		if (null != id) {
		    String langCode = UserProfileUtils.getLanguage();
			repositoryDto = repositoryRepository.findRepositoryDtoById(id, langCode);
		} else {
			repositoryDto.setActived(true);
			repositoryDto.setFileProtocol(FileProtocol.LOCAL.getValue());
		}
		return repositoryDto;
	}

	@Override
	public JcaRepository getRepositoryByCode(String code, Long id) {
		JcaRepository result = repositoryRepository.findRepositoryByCode(code, id);
		return result;
	}

	@Override
	@Transactional
	public JcaRepository saveRepositoryDto(JcaRepositoryDto repositoryDto) throws DetailException {
//		try {
//			Long userId = UserProfileUtils.getAccountId();
//			Long id = repositoryDto.getId();
//			// update data jca_m_repository table
//			JcaRepository repository = null;
//			if (null != id) {
//				repository = repositoryRepository.findOne(id);
//				if (null == repository) {
//					throw new BusinessException("Not found Repository by id= " + id);
//				}
//				repository.setUpdatedId(userId);
//				repository.setUpdatedDate(comService.getSystemDateTime());
//			} else {
//				repository = new JcaRepository();
//				repository.setCreatedId(userId);
//				repository.setCreatedDate(comService.getSystemDateTime());
//			}
//
//			String code = repositoryDto.getCode().trim();
//			repository.setCode(code);
//
//			String name = repositoryDto.getName();
//			repository.setName(name);
//
//			String physicalPath = repositoryDto.getPhysicalPath();
//			repository.setPhysicalPath(physicalPath);
//
//			String subFolderRule = repositoryDto.getSubFolderRule();
//			repository.setSubFolderRule(subFolderRule);
//
//			String typeRepo = repositoryDto.getTypeRepo();
//			repository.setTypeRepo(typeRepo);
//
//			Boolean active = repositoryDto.getActive();
//			repository.setActive(active);
//
//			String description = repositoryDto.getDescription();
//			repository.setDescription(description);
//
//			repository.setCompanyId(repositoryDto.getCompanyId());
//
//			Long local = repositoryDto.getFileProtocol();
//			repository.setFileProtocol(FileProtocol.LOCAL.getValue());
//
//			String user = repositoryDto.getUsername();
//			repository.setUsername(user);
//
//			String password = repositoryDto.getPassword();
//			if (StringUtils.isNotBlank(password)
//					&& !CommonConstant.PASSWORD_ENCRYPT.equals(repositoryDto.getPassword())) {
//				repository.setPassword(PasswordUtil.encryptString(password));
//			}
//			id = repository.getId();
//			
//			if (null != id) {
//				repositoryRepository.update(repository);
//			} else {
//				repositoryRepository.create(repository);
//			}
//			
////			id = repository.getId();
////			repositoryDto.setId(id);
//
//			// Init repository
//			systemConfig.initRepository(repository.getId());
//		} catch (Exception ex) {
//			throw new SystemException(ex);
//		}
		return jcaRepositoryService.saveJcaRepositoryDto(repositoryDto);
	}

	@Override
	@Transactional
	public void deleteRepositoryById(Long id, Locale locale) {

		int store = repositoryRepository.checkRepositoryHasStoreFile(id);
		if (store == 1) {
			String message = messageSource.getMessage(ConstantCore.MESSAGE_FAILED_DELETE, null, locale);
			throw new SystemException(message);
		}
		Long userId = UserProfileUtils.getAccountId();
		Date systemDate = comService.getSystemDateTime();
		JcaRepository repository = repositoryRepository.findOne(id);
		if (null != id) {
			repository.setDeletedDate(systemDate);
			repository.setDeletedId(userId);

			try {
				repositoryRepository.update(repository);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	public List<JcaRepository> getAllRepository() {
		return (List<JcaRepository>) repositoryRepository.findAll();
	}

	/**
	 * getAllRepositoryByCompanyId
	 * 
	 * @param companyId
	 * @param repoId
	 * @return
	 * @author HungHT
	 */
	public List<JcaRepository> getAllRepositoryByCompanyId(Long companyId, Long repoId) {
		return repositoryRepository.getAllRepositoryByCompanyId(companyId, repoId);
	}

	@Override
	public boolean checkConnectToRepository(String path, String user, String password) {
		boolean result = true;
		try {
			String url = this.generateSmbPath(path);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, password);
			SmbFile dir = new SmbFile(url, auth);
			dir.connect();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public boolean checkConnectToSystemSettingRepository() {
		boolean result = true;

		String repositoryIdStr = systemConfig.getConfig(SystemConfig.REPO_UPLOADED_TEMP);

		JcaRepositoryDto repositoryDto = getRepositoryDtoById(Long.valueOf(repositoryIdStr));
		String user = repositoryDto != null ? repositoryDto.getUsername() : null;
		String password = repositoryDto != null && repositoryDto.getPassword() != null
				? PasswordUtil.decryptString(repositoryDto.getPassword())
				: repositoryDto.getPassword();
		String path = repositoryDto != null ? repositoryDto.getPhysicalPath() : null;

		try {
			String url = this.generateSmbPath(path);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, password);
			SmbFile dir = new SmbFile(url, auth);
			dir.connect();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * createDirectoryNotExists
	 * 
	 * @param path
	 * @param local
	 * @param user
	 * @param password
	 * @return
	 * @author HungHT
	 */
	public boolean createDirectoryNotExists(String path, boolean local, String user, String password) {
		boolean result = true;
		try {
			if (!local) {
				String url = this.generateSmbPath(path);
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, password);
				SmbFile sDir = new SmbFile(url, auth);
				if (!sDir.exists()) {
					sDir.mkdirs();
				}
			} else {
				FileUtil.createDirectoryNotExists(path);
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * writeFile
	 * 
	 * @param path
	 * @param bytes
	 * @param local
	 * @param user
	 * @param password
	 * @return
	 * @author HungHT
	 */
	public boolean writeFile(Path path, byte[] bytes, boolean local, String user, String password) {
		boolean result = true;
		try {
			if (!local) {
				String url = this.generateSmbPath(path.toString());
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, password);
				SmbFile sFile = new SmbFile(url, auth);
				if (sFile.exists()) {
					return false;
				}
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				sfos.write(bytes);
				sfos.close();
			} else {
				Files.write(path, bytes, StandardOpenOption.CREATE_NEW);
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * generateSmbPath
	 * 
	 * @param path
	 * @return
	 * @author HungHT
	 */
	public String generateSmbPath(String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, "\\*?&\\");
		List<String> paths = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			paths.add(tokenizer.nextToken());
		}

		StringBuilder url = new StringBuilder("smb://");
		for (int i = 0; i < paths.size(); i++) {
			url.append(paths.get(i));
			if (i < paths.size() - 1) {
				url.append("/");
			}
		}
		return url.toString();
	}

	/**
	 * uploadFileBySettingKey
	 * 
	 * @param file
	 * @param rename      (Not null: Rename when upload)
	 * @param key
	 * @param typeRule    (0: No sub folder rule, 1: Sub forlder rule before
	 *                    subFilePath, 2: Sub forlder rule after subFilePath)
	 * @param dateRule
	 * @param subFilePath
	 * @param companyId
	 * @param locale
	 * @return
	 * @author HungHT
	 */
	public FileResultDto uploadFileBySettingKey(MultipartFile file, String rename, String key, int typeRule,
			Date dateRule, String subFilePath, Long companyId, Locale locale) {
		FileResultDto result = new FileResultDto();
		result.setStatus(true);
		if (null != file) {
			// Get folder repository
			JcaRepository repo = systemConfig.getRepoByKey(key, companyId, dateRule);
			if (null == repo) {
				result.setStatus(false);
				result.setMessageCode("B103");
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), null, null, locale).getErrorDesc());
				return result;
			}

			// Check rule folder upload
			String subFilePathWithRule = null;
			switch (typeRule) {
			// 0: No sub folder rule
			case 0:
				subFilePathWithRule = Paths.get(subFilePath).toString();
				break;
			// 1: Sub forlder rule before subFilePath
			case 1:
				String subRule1 = repo.getSubFolderRule();
				if (StringUtils.isBlank(subRule1)) {
					subFilePathWithRule = Paths.get(subFilePath).toString();
				} else {
					subFilePathWithRule = subRule1.concat("/").concat(subFilePath);
				}
				break;
			// 2: Sub forlder rule after subFilePath
			case 2:
				String subRule2 = repo.getSubFolderRule();
				if (StringUtils.isBlank(subRule2)) {
					subFilePathWithRule = Paths.get(subFilePath).toString();
				} else {
					subFilePathWithRule = subFilePath.concat("/").concat(subRule2);
				}
				break;
			default:
				subFilePathWithRule = Paths.get(subFilePath).toString();
				break;
			}
			String urlUpload = Paths.get(repo.getPhysicalPath(), subFilePathWithRule).toString();
			String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());
			String nameFile = null;
			// Check rename file when upload
			if (StringUtils.isNotBlank(rename)) {
				nameFile = rename;
			} else {
				nameFile = FilenameUtils.getBaseName(file.getOriginalFilename());
			}
			String fullFileName = nameFile + extension;
			String filePath = null;
			Path path = null;
			byte[] bytes = null;
			boolean local = true;//FileProtocol.LOCAL.getValue().longValue() == repo.getFileProtocol().longValue();
			try {
				bytes = file.getBytes();
				// Check create directory if not exist
				if (!this.createDirectoryNotExists(urlUpload, local,
						repo.getUsername(), repo.getPassword())) {
					result.setStatus(false);
					result.setMessageCode("B104");
					result.setArgs(new String[] { urlUpload });
					result.setMessage(ExecMessage
							.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
					return result;
				}
				path = Paths.get(urlUpload, fullFileName);
				// Check upload file
				if (!this.writeFile(path, bytes, local,
						repo.getUsername(), repo.getPassword())) {
					throw new AppException(null, null);
				}
				filePath = subFilePathWithRule.concat("/").concat(nameFile).concat(extension).replaceAll("//", "/")
						.replaceAll("\\\\", "/");
			} catch (AppException e) {
				filePath = null;
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantCore.yyyyMMddHHmmssSSS);
					// set name file with date time when file name already exists
					fullFileName = nameFile + "_" + dateFormat.format(comService.getSystemDateTime()) + extension;
					path = Paths.get(urlUpload, fullFileName);
					// Check upload file
					if (!this.writeFile(path, bytes, local,
							repo.getUsername(), repo.getPassword())) {
						result.setStatus(false);
						result.setMessageCode("B105");
						result.setArgs(new String[] { path.toString() });
						result.setMessage(ExecMessage
								.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
								.getErrorDesc());
						return result;
					}
					filePath = subFilePathWithRule.concat("/").concat(fullFileName).replaceAll("//", "/")
							.replaceAll("\\\\", "/");
				} catch (Exception ex) {
					result.setStatus(false);
					result.setMessageCode("B105");
					result.setArgs(new String[] { file.getOriginalFilename() });
					result.setMessage(ExecMessage
							.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
					return result;
				}
			} catch (IOException e) {
				result.setStatus(false);
				result.setMessageCode("B105");
				result.setArgs(new String[] { file.getOriginalFilename() });
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
						.getErrorDesc());
				return result;
			}
			// Set result object
			if (StringUtils.isNotBlank(filePath)) {
				result.setStatus(true);
				result.setFilePath(filePath);
				result.setFileName(fullFileName);
				result.setRepositoryId(repo.getId());
			} else {
				result.setStatus(false);
				result.setMessageCode("B105");
				result.setArgs(new String[] { file.getOriginalFilename() });
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
						.getErrorDesc());
			}
		}
		return result;
	}

	@Override
	public FileResultDto uploadInputStreamBySettingKey(InputStream inputStream, String fileName, String key,
			int typeRule, Date dateRule, String subFilePath, Long companyId, Locale locale) {
		FileResultDto result = new FileResultDto();
		result.setStatus(true);
		if (null != inputStream) {
			// Get folder repository
			JcaRepository repo = systemConfig.getRepoByKey(key, companyId, dateRule);
			if (null == repo) {
				result.setStatus(false);
				result.setMessageCode("B103");
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), null, null, locale).getErrorDesc());
				return result;
			}

			// Check rule folder upload
			String subFilePathWithRule = null;
			switch (typeRule) {
			// 0: No sub folder rule
			case 0:
				subFilePathWithRule = Paths.get(subFilePath).toString();
				break;
			// 1: Sub forlder rule before subFilePath
			case 1:
				String subRule1 = repo.getSubFolderRule();
				if (StringUtils.isBlank(subRule1)) {
					subFilePathWithRule = Paths.get(subFilePath).toString();
				} else {
					subFilePathWithRule = subRule1.concat("/").concat(subFilePath);
				}
				break;
			// 2: Sub forlder rule after subFilePath
			case 2:
				String subRule2 = repo.getSubFolderRule();
				if (StringUtils.isBlank(subRule2)) {
					subFilePathWithRule = Paths.get(subFilePath).toString();
				} else {
					subFilePathWithRule = subFilePath.concat("/").concat(subRule2);
				}
				break;
			default:
				subFilePathWithRule = Paths.get(subFilePath).toString();
				break;
			}
			String urlUpload = Paths.get(repo.getPhysicalPath(), subFilePathWithRule).toString();
			String filePath = null;
			Path path = null;
			String fullFileName = null;
			byte[] bytes = null;
			boolean local = FileProtocol.LOCAL.getValue() != repo.getFileProtocol();
			try {
				bytes = IOUtils.toByteArray(inputStream);
				// Check create directory if not exist
				if (!this.createDirectoryNotExists(urlUpload, local,
						repo.getUsername(), repo.getPassword())) {
					result.setStatus(false);
					result.setMessageCode("B104");
					result.setArgs(new String[] { urlUpload });
					result.setMessage(ExecMessage
							.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
					return result;
				}
				fullFileName = fileName;
				path = Paths.get(urlUpload, fullFileName);
				// Check upload file
				if (!this.writeFile(path, bytes, local,
						repo.getUsername(), repo.getPassword())) {
					throw new AppException(null, null);
				}
				filePath = subFilePathWithRule.concat("/").concat(fileName).replaceAll("//", "/").replaceAll("\\\\",
						"/");
			} catch (AppException e) {
				filePath = null;
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantCore.yyyyMMddHHmmssSSS);
					// set name file with date time when file name already exists
					fullFileName = dateFormat.format(comService.getSystemDateTime()) + "_" + fileName;
					path = Paths.get(urlUpload, fullFileName);
					// Check upload file
					if (!this.writeFile(path, bytes, local,
							repo.getUsername(), repo.getPassword())) {
						result.setStatus(false);
						result.setMessageCode("B105");
						result.setArgs(new String[] { path.toString() });
						result.setMessage(ExecMessage
								.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
								.getErrorDesc());
						return result;
					}
					filePath = subFilePathWithRule.concat("/").concat(fullFileName).replaceAll("//", "/")
							.replaceAll("\\\\", "/");
				} catch (Exception ex) {
					result.setStatus(false);
					result.setMessageCode("B105");
					result.setArgs(new String[] { fileName });
					result.setMessage(ExecMessage
							.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
					return result;
				}
			} catch (IOException e) {
				result.setStatus(false);
				result.setMessageCode("B105");
				result.setArgs(new String[] { fileName });
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
						.getErrorDesc());
				return result;
			}
			// Set result object
			if (StringUtils.isNotBlank(filePath)) {
				result.setStatus(true);
				result.setFilePath(filePath);
				result.setFileName(fullFileName);
				result.setRepositoryId(repo.getId());
			} else {
				result.setStatus(false);
				result.setMessageCode("B105");
				result.setArgs(new String[] { fullFileName });
				result.setMessage(ExecMessage
						.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
						.getErrorDesc());
			}
		}
		return result;
	}

	@Override
	public FileResultDto saveFilePDF(String content, String filePath, String fileName, String key, Long companyId,
			Locale locale) {
		FileResultDto result = new FileResultDto();
		result.setStatus(true);
		Document document = new Document();
		SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantCore.yyyyMMddHHmmssSSS);
		// Get folder repository
		JcaRepository repo = systemConfig.getRepoByKey(key, companyId, null);
		if (null == repo) {
			result.setStatus(false);
			result.setMessageCode("B103");
			result.setMessage(ExecMessage.getErrorMessage(messageSource, result.getMessageCode(), null, null, locale)
					.getErrorDesc());
			return result;
		}
		if (!fileName.endsWith(ConstantCore.EXTENTION_PDF)) {
			fileName = fileName + ConstantCore.EXTENTION_PDF;
		}
		Path path = Paths.get(repo.getPhysicalPath(), filePath, fileName);
		String url = path.toString();
		boolean local = FileProtocol.LOCAL.getValue() != repo.getFileProtocol();
		try {
//        	if(repo.getLocal)
			if (local) {
				if (Files.exists(path)) {
					fileName = dateFormat.format(comService.getSystemDateTime()) + "_" + fileName;
					url = Paths.get(repo.getPhysicalPath(), filePath, fileName).toString();
				}
				File file = new File(url.toString());
				OutputStream out = new FileOutputStream(file);
				PdfWriter.getInstance(document, out);
				document.open();
				HTMLWorker htmlWorker = new HTMLWorker(document);
				htmlWorker.parse(new StringReader(content));
				document.close();
				out.close();
			} else {
				url = this.generateSmbPath(url.toString());
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, repo.getUsername(),
						repo.getPassword());
				SmbFile sFile = new SmbFile(url, auth);
				if (sFile.exists()) {
					fileName = dateFormat.format(comService.getSystemDateTime()) + "_" + fileName;
					url = Paths.get(repo.getPhysicalPath(), filePath, fileName).toString();
					sFile = new SmbFile(url, auth);
				}
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				PdfWriter.getInstance(document, sfos);
				document.open();
				HTMLWorker htmlWorker = new HTMLWorker(document);
				htmlWorker.parse(new StringReader(content));
				document.close();
				sfos.close();
			}
		} catch (Exception e) {
			result.setStatus(false);
			result.setMessageCode("B105");
			result.setArgs(new String[] { fileName });
			result.setMessage(
					ExecMessage.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
			return result;
		}

		// Set result object
		if (StringUtils.isNotBlank(filePath)) {
			result.setStatus(true);
			result.setFilePath(filePath);
			result.setFileName(fileName);
			result.setRepositoryId(repo.getId());
		} else {
			result.setStatus(false);
			result.setMessageCode("B105");
			result.setArgs(new String[] { fileName });
			result.setMessage(
					ExecMessage.getErrorMessage(messageSource, result.getMessageCode(), result.getArgs(), null, locale)
							.getErrorDesc());
		}

		return result;
	}

	@Override
	public vn.com.unit.common.service.JCommonService getCommonService() {
		return comService;
	}

	@Override
	public List<JcaRepository> getListRepositoryByCodeAndCompany(String code, Long companyId) {
		return repositoryRepository.getListRepositoryByCodeAndCompany(code, companyId);
	}

}
