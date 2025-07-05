/*******************************************************************************
 * Class        RepositoryRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.core.dto.RepositorySearchDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * RepositoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JRepositoryService {
	
    /**
     * Find Repository List by active
     * 
     * @param active
     * 			type boolean
     * @return List<Repository>
     * @author KhoaNA
     */
    public List<JcaRepository> getRepositoryListByActive(int active);
    
    /**
     * get repositoryDto by id
     *
     * @param  id
     * 			type Long
     * @return RepositoryDto
     * @author KhoaNA
     */
    public JcaRepositoryDto getRepositoryDtoById(Long id);
    
    /**
     * Get path by repository
     * 
     * @param fileOrFolderName
     * 			type String
     * @param repositoryKey (SystemConfig.REPO_*)
     * 			type String
     * @return String
     * @author KhoaNA
     */
    public String getPathByRepository(String fileOrFolderName, String repositoryKey);
    
    /**
     * Get path by repository
     * 
     * @param repositoryKey (SystemConfig.REPO_*)
     * 			type String
     * @return String
     * @author KhoaNA
     */
    public String getPathByRepository(String repositoryKey);

    /**
     * Get file by repository
     * 
     * @param fileName
     * 			type String
     * @param repositoryKey (SystemConfig.REPO_*)
     * 			type String
     * @return String
     * @author KhoaNA
     */
    public File getFileByRepository(String fileName, String repositoryKey);
    
    /**
     * search repositoryDto
     *
     * @param  searchDto
     * 			type RepositorySearchDto
     * @param  page
     * 			type int
     * @param  pageSize
     * 			type int
     * @return PageWrapper<RepositoryDto>
     * @author KhoaNA
     * @throws DetailException 
     */
    public PageWrapper<JcaRepositoryDto> searchByCondition(int page, RepositorySearchDto searchDto, int pageSize) throws DetailException;
    
    /**
     * get repository by code
     *
     * @param  code
     * 			type String
     * @param  id
     * 			type Long
     * @return Position
     * @author KhoaNA
     */
    public JcaRepository getRepositoryByCode(String code, Long id);
    
    /**
     * save repositoryDto
     *
     * @param  repositoryDto
     * 			type RepositoryDto
     * @return
     * @author KhoaNA
     * @throws DetailException 
     */
    public JcaRepository saveRepositoryDto(JcaRepositoryDto repositoryDto) throws DetailException;
    
    /**
     * delete repository by id
     *
     * @param  id
     * 			type Long
     * @return
     * @author KhoaNA
     */
    public void deleteRepositoryById(Long id,Locale locale);

    /** getAllRepository
     *
     * @return
     * @author phatvt
     */
    List<JcaRepository> getAllRepository();
    
    /**
     * getAllRepositoryByCompanyId
     * 
     * @param companyId
     * @param repoId
     * @return
     * @author HungHT
     */
    List<JcaRepository> getAllRepositoryByCompanyId(Long companyId, Long repoId);
    
    /**
     * checkConnectToRepository
     * @param path
     * @param user
     * @param password
     * @return result
     * @author TuyenTD
     */
    boolean checkConnectToRepository(String path, String user, String password);
    
    /**
     * checkConnectToSystemSettingRepository
     * @return result
     * @author TuyenTD
     */
    boolean checkConnectToSystemSettingRepository();
    
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
    boolean createDirectoryNotExists(String path, boolean local, String user, String password);
    
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
    boolean writeFile(Path path, byte[] bytes, boolean local, String user, String password);
    
    /**
     * generateSmbPath
     * 
     * @param path
     * @return
     * @author HungHT
     */
    String generateSmbPath(String path);
    
    /**
     * uploadFileBySettingKey
     * @param file
     * @param rename (Not null: Rename when upload)
     * @param key
     * @param typeRule (0: No sub folder rule, 1: Sub forlder rule before subFilePath, 2: Sub forlder rule after subFilePath)
     * @param dateRule
     * @param subFilePath
     * @param companyId
     * @param locale
     * @return
     * @author HungHT
     */
    FileResultDto uploadFileBySettingKey(MultipartFile file, String rename, String key, int typeRule, Date dateRule, String subFilePath, Long companyId,
            Locale locale);
    
    
    /**
     * uploadFileBySettingKey
     * @param inputStream
     * @param fileName (Not null: include extention)
     * @param key
     * @param typeRule (0: No sub folder rule, 1: Sub forlder rule before subFilePath, 2: Sub forlder rule after subFilePath)
     * @param dateRule
     * @param subFilePath
     * @param companyId
     * @param locale
     * @return
     * @author trieuvd
     */
    FileResultDto uploadInputStreamBySettingKey(InputStream inputStream, String fileName, String key, int typeRule, Date dateRule, String subFilePath, Long companyId,
            Locale locale);
    
    /**
     * saveFilePDF
     * @param content
     * @param filePath
     * @param fileName
     * @param key
     * @param companyId
     * @param locale
     * @return
     * @author trieuvd
     */
    FileResultDto saveFilePDF(String content, String filePath, String fileName, String key, Long companyId, Locale locale);

	public List<JcaRepository> getListRepositoryByCodeAndCompany(String code, Long companyId);
}
