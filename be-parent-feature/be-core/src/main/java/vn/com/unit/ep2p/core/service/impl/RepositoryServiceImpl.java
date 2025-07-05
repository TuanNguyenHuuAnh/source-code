/*******************************************************************************
 * Class        RepositoryServiceImpl
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.dto.RepositorySearchDto;
import vn.com.unit.ep2p.core.entity.Repository;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.repository.RepositoryRepository;
import vn.com.unit.ep2p.core.service.RepositoryService;
import vn.com.unit.ep2p.dto.RepositoryDto;
import vn.com.unit.ep2p.enumdef.RepositorySearchEnum;

//import vn.com.unit.exception.BusinessException;
//import vn.com.unit.exception.SystemException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.RepositoryDto;
//import vn.com.unit.jcanary.dto.RepositorySearchDto;
//import vn.com.unit.jcanary.entity.Repository;
//import vn.com.unit.jcanary.enumdef.RepositorySearchEnum;
//import vn.com.unit.jcanary.repository.RepositoryRepository;
//import vn.com.unit.jcanary.service.RepositoryService;

/**
 * RepositoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service(value = "coreRepositoryServiceImpl")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RepositoryServiceImpl implements RepositoryService {

    /** repositoryRepository */
    @Autowired
    private RepositoryRepository repositoryRepository;

    /** systemConfig */
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public List<Repository> getRepositoryListByActive(int active) {
        List<Repository> repositoryList = repositoryRepository.findRepositoryListByActive(active);

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

    @Override
    public PageWrapper<RepositoryDto> searchByCondition(int page, RepositorySearchDto searchDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<RepositoryDto> pageWrapper = new PageWrapper<RepositoryDto>(page, sizeOfPage);
        if (null == searchDto) {
            searchDto = new RepositorySearchDto();
        }
        // set SearchParm
        this.setSearchParm(searchDto);

        int count = repositoryRepository.countRepositoryDtoByCondition(searchDto);
        List<RepositoryDto> result = new ArrayList<RepositoryDto>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = repositoryRepository.findRepositoryDtoByCondition(startIndex, sizeOfPage, searchDto);
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private void setSearchParm(RepositorySearchDto searchDto) {
        String searchValue = searchDto.getFieldSearch();
        List<String> fieldSearchList = searchDto.getFieldValues();

        if (!StringUtils.isEmpty(searchValue)) {
            searchValue = searchValue.trim();
        }

        if (null != fieldSearchList && !fieldSearchList.isEmpty()) {
            for (String fieldSearch : fieldSearchList) {
                if (StringUtils.equals(fieldSearch, RepositorySearchEnum.CODE.name())) {
                    searchDto.setCode(searchValue);
                    continue;
                }
                if (StringUtils.equals(fieldSearch, RepositorySearchEnum.NAME.name())) {
                    searchDto.setName(searchValue);
                    continue;
                }
                if (StringUtils.equals(fieldSearch, RepositorySearchEnum.PHYSICAL_PATH.name())) {
                    searchDto.setPhysicalPath(searchValue);
                    continue;
                }
                if (StringUtils.equals(fieldSearch, RepositorySearchEnum.SUB_FOLDER_RULE.name())) {
                    searchDto.setSubFolderRule(searchValue);
                    continue;
                }
            }
        } else {
            searchDto.setCode(searchValue);
            searchDto.setName(searchValue);
            searchDto.setPhysicalPath(searchValue);
            searchDto.setSubFolderRule(searchValue);
        }
    }

    @Override
    public RepositoryDto getRepositoryDtoById(Long id) {
        RepositoryDto repositoryDto = new RepositoryDto();
        if (null != id) {
            repositoryDto = repositoryRepository.findRepositoryDtoById(id);
        }

        return repositoryDto;
    }

    @Override
    public Repository getRepositoryByCode(String code, Long id) {
        Repository result = repositoryRepository.findRepositoryByCode(code, id);
        return result;
    }

    @Override
    @Transactional
    public void saveRepositoryDto(RepositoryDto repositoryDto) {
        String userNameLogin = UserProfileUtils.getUserNameLogin();
        Long id = repositoryDto.getId();
        // update data jca_m_repository table
        Repository repository = null;
        if (null != id) {
            repository = repositoryRepository.findOne(id);
            if (null == repository) {
                throw new BusinessException("Not found Repository by id= " + id);
            }
            repository.setUpdatedBy(userNameLogin);
            repository.setUpdatedDate(new Date());
        } else {
            repository = new Repository();
            repository.setCreatedBy(userNameLogin);
            repository.setCreatedDate(new Date());
        }

        String code = repositoryDto.getCode().trim();
        repository.setCode(code);

        String name = repositoryDto.getName();
        repository.setName(name);

        String physicalPath = repositoryDto.getPhysicalPath();
        repository.setPhysicalPath(physicalPath);

        String subFolderRule = repositoryDto.getSubFolderRule();
        repository.setSubFolderRule(subFolderRule);

        String typeRepo = repositoryDto.getTypeRepo();
        repository.setTypeRepo(typeRepo);

        Boolean active = repositoryDto.getActive();
        repository.setActive(active);

        String description = repositoryDto.getDescription();
        repository.setDescription(description);

        try {
            repositoryRepository.save(repository);
            id = repository.getId();
            repositoryDto.setId(id);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteRepositoryById(Long id) {
        String usernameLogin = UserProfileUtils.getUserNameLogin();
        Date systemDate = new Date();

        Repository repository = repositoryRepository.findOne(id);
        if (null != id) {
            repository.setDeletedDate(systemDate);
            repository.setDeletedBy(usernameLogin);

            try {
                repositoryRepository.save(repository);
            } catch (Exception ex) {
                throw new SystemException(ex);
            }
        }
    }

    @Override
    public List<Repository> getAllRepository() {
        return (List<Repository>) repositoryRepository.findAll();
    }
}
