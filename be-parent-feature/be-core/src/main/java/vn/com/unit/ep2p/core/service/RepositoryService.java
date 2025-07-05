/*******************************************************************************
 * Class        RepositoryRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import java.io.File;
import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.dto.RepositorySearchDto;
import vn.com.unit.ep2p.core.entity.Repository;
import vn.com.unit.ep2p.dto.RepositoryDto;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.RepositoryDto;
//import vn.com.unit.jcanary.dto.RepositorySearchDto;
//import vn.com.unit.jcanary.entity.Repository;

/**
 * RepositoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface RepositoryService {

    /**
     * Find Repository List by active
     * 
     * @param active type boolean
     * @return List<Repository>
     * @author KhoaNA
     */
    public List<Repository> getRepositoryListByActive(int active);

    /**
     * get repositoryDto by id
     *
     * @param id type Long
     * @return RepositoryDto
     * @author KhoaNA
     */
    public RepositoryDto getRepositoryDtoById(Long id);

    /**
     * Get path by repository
     * 
     * @param fileOrFolderName type String
     * @param repositoryKey    (SystemConfig.REPO_*) type String
     * @return String
     * @author KhoaNA
     */
    public String getPathByRepository(String fileOrFolderName, String repositoryKey);

    /**
     * Get path by repository
     * 
     * @param repositoryKey (SystemConfig.REPO_*) type String
     * @return String
     * @author KhoaNA
     */
    public String getPathByRepository(String repositoryKey);

    /**
     * Get file by repository
     * 
     * @param fileName      type String
     * @param repositoryKey (SystemConfig.REPO_*) type String
     * @return String
     * @author KhoaNA
     */
    public File getFileByRepository(String fileName, String repositoryKey);

    /**
     * search repositoryDto
     *
     * @param searchDto type RepositorySearchDto
     * @return PageWrapper<RepositoryDto>
     * @author KhoaNA
     */
    public PageWrapper<RepositoryDto> searchByCondition(int page, RepositorySearchDto searchDto);

    /**
     * get repository by code
     *
     * @param code type String
     * @param id   type Long
     * @return Position
     * @author KhoaNA
     */
    public Repository getRepositoryByCode(String code, Long id);

    /**
     * save repositoryDto
     *
     * @param repositoryDto type RepositoryDto
     * @return
     * @author KhoaNA
     */
    public void saveRepositoryDto(RepositoryDto repositoryDto);

    /**
     * delete repository by id
     *
     * @param id type Long
     * @return
     * @author KhoaNA
     */
    public void deleteRepositoryById(Long id);

    /**
     * getAllRepository
     *
     * @return
     * @author phatvt
     */
    List<Repository> getAllRepository();
}
