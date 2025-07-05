package vn.com.unit.cms.admin.all.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.admin.all.dto.DocumentManagementDto;
import vn.com.unit.cms.admin.all.entity.DocumentManagement;
import vn.com.unit.cms.admin.all.repository.DocumentManagementRepository;
import vn.com.unit.cms.admin.all.service.DocumentManagementService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.JcaRepositoryService;
import vn.com.unit.storage.utils.FileStorageUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentManagementServiceImpl
		extends DocumentWorkflowCommonServiceImpl<DocumentManagementDto, DocumentManagementDto>
		implements DocumentManagementService {

	@Autowired
	DocumentManagementRepository documentManagementRepository;

	@Autowired
	private JcaRepositoryService jcaRepositoryService;

	private static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;

	private List<DocumentManagementDto> generateDocumentTree(List<DocumentManagementDto> listDocumentManagement) {
		List<DocumentManagementDto> documentManagementTree = new LinkedList<DocumentManagementDto>();

		documentManagementTree.add(listDocumentManagement.get(0));
		listDocumentManagement.remove(0);

		int i = 0;
		while (listDocumentManagement.size() > 0) {

			DocumentManagementDto parent = documentManagementTree.get(i);

			Long parentId = parent.getId();

			int position = 0;

			for (int j = 0; j < listDocumentManagement.size(); j++) {

				DocumentManagementDto child = listDocumentManagement.get(j);

				if (child.getParentId().equals(parentId)) {
					position++;
					documentManagementTree.add(i + position, child);
					listDocumentManagement.remove(j);
					j--;
				}
			}

			if (++i >= documentManagementTree.size()) {
				break;
			}
		}

		return documentManagementTree;
	}

	@Override
	public PageWrapper<DocumentManagementDto> search(int page, DocumentManagementDto searchDto, String lang) {
		int sizeOfPage = MAX_PAGE_SIZE; // systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<DocumentManagementDto> pageWrapper = new PageWrapper<DocumentManagementDto>(page, sizeOfPage);

		int startIndex = (page - 1) * sizeOfPage;

		List<DocumentManagementDto> listDocumentManagement = new LinkedList<DocumentManagementDto>();
		List<DocumentManagementDto> result = new LinkedList<DocumentManagementDto>();

		int count = documentManagementRepository.count(searchDto, lang);

		if (count > 0) {
			listDocumentManagement = documentManagementRepository.search(startIndex, sizeOfPage, searchDto, lang);

			result = generateDocumentTree(listDocumentManagement);

		}

		pageWrapper.setDataAndCount(result, count);

		return pageWrapper;
	}

	@Override
	public void delete(Long id) throws Exception {
		DocumentManagement documentManagement = documentManagementRepository.findOne(id);

		if (documentManagement.getParentId().equals(0L)) {
			String type = documentManagement.getIsFolder().equals(1) ? " folder" : "file";
			throw new Exception("Can't delete root " + type);
		}

		documentManagement.setDeletedId(Long.valueOf(getUserActionId()));
		documentManagement.setDeletedDate(new Date());

		documentManagementRepository.save(documentManagement);
	}
	
	@Override
	public DocumentManagement findOne(Long id) {
		DocumentManagement documentManagement = documentManagementRepository.findOne(id);

		return documentManagement;
	} 

	@Override
	public void createFolder(Long parentId, String name) throws Exception {
		String code = String.valueOf(UUID.randomUUID());

//		String msgExistFolderError = "Folder has been exist";
//
//		DocumentManagement currentDocumentManagement = documentManagementRepository
//				.getDocumentManagementByNameAndParentId(name, parentId);
//
//		if (null != currentDocumentManagement) {
//			throw new Exception(msgExistFolderError);
//		}

		String parentPath = documentManagementRepository.getPathById(parentId);

		String folderPath = "";

		if (CommonStringUtil.isNotBlank(parentPath)) {
			JcaRepositoryDto repository = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN",
					UserProfileUtils.getCompanyId());
//			JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repository.getId());

			folderPath = repository.getPhysicalPath() + "/" + parentPath + "/" + code;
			folderPath = folderPath.replace("//", "/");

			File file = new File(folderPath);

			if (!file.exists()) {
				FileStorageUtils.createDirectoryNotExists(folderPath);
				// file.mkdir();
			}
//			else {
//				throw new Exception(msgExistFolderError);
//			}
		}

		// SAVE INTO DATABASE

		DocumentManagement documentManagement = new DocumentManagement();

		// TODO: RANDOM UUID FOR PHYSICAL NAME
		documentManagement.setCode(code); // need random uuid for physical name
		documentManagement.setName(name);

		documentManagement.setIsFolder(1);

		documentManagement.setParentId(parentId);

		documentManagement.setCreatedId(Long.valueOf(getUserActionId()));
		documentManagement.setCreatedDate(new Date());

		documentManagement.setCompanyId(UserProfileUtils.getCompanyId());

		documentManagementRepository.save(documentManagement);
	}

	@Override
	public String getDocumentManagementPhysicalPathById(Long id) {
		String relativePath = documentManagementRepository.getPathById(id);

		JcaRepositoryDto repository = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN",
				UserProfileUtils.getCompanyId());

		String rs = repository.getPhysicalPath() + "/" + relativePath;

		return rs.replace("//", "/");
	}

	@Override
	public void uploadFile(Long parentId, MultipartFile multipartFile) throws Exception {
		String code = String.valueOf(UUID.randomUUID());
		String name = multipartFile.getOriginalFilename();

//		String msgExistFileError = "File has been exist";
//		DocumentManagement currentDocumentManagement = documentManagementRepository
//				.getDocumentManagementByCodeAndParentId(name, parentId);
//
//		if (null != currentDocumentManagement) {
//			throw new Exception(msgExistFileError);
//		}

		String parentPath = documentManagementRepository.getPathById(parentId);

		String folderPath = "";

		if (CommonStringUtil.isNotBlank(parentPath)) {
			JcaRepositoryDto repository = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN",
					UserProfileUtils.getCompanyId());
//			JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDtoById(repository.getId());

			folderPath = repository.getPhysicalPath() + "/" + parentPath;
			folderPath = folderPath.replace("//", "/");

			File file = new File(folderPath);

			if (!file.exists()) {
				FileStorageUtils.createDirectoryNotExists(folderPath);
				// file.mkdir();
			}
		}

		byte[] bytes = multipartFile.getBytes();

		Path path = Paths.get(folderPath, code);
		Files.write(path, bytes);

		// SAVE INTO DATABASE

		DocumentManagement documentManagement = new DocumentManagement();

		// TODO: RANDOM UUID FOR PHYSICAL NAME
		documentManagement.setCode(code); // need random uuid for physical name
		documentManagement.setName(name);

		documentManagement.setIsFolder(0);

		documentManagement.setParentId(parentId);

		documentManagement.setCreatedId(Long.valueOf(getUserActionId()));
		documentManagement.setCreatedDate(new Date());

		documentManagement.setCompanyId(UserProfileUtils.getCompanyId());

		documentManagementRepository.save(documentManagement);
	}

	@Override
	public DocumentManagementDto getEdit(Long id, String customerAlias, Locale locale) {

		if (null == id) {
			return new DocumentManagementDto();
		}

		DocumentManagement documentManagement = documentManagementRepository.findOne(id);

		ObjectMapper mapper = new ObjectMapper();

		return mapper.convertValue(documentManagement, DocumentManagementDto.class);
	}
}
