/*******************************************************************************
 * Class        RepositoryRepository
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.RepositorySearchDto;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.repository.JcaRepositoryRepository;

/**
 * RepositoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface RepositoryRepository extends JcaRepositoryRepository {
    
    /**
     * Find repositoryDto by id
     * 
     * @param id
     *          type Long
     * @return RepositoryDto
     * @author KhoaNA
     */
    JcaRepositoryDto findRepositoryDtoById(@Param("id") Long id, @Param("langCode") String langCode);
    
    /**
     * Find repository list by id
     * 
     * @param actived
     *          type boolean
     * @return List<Repository>
     * @author KhoaNA
     */
    List<JcaRepository> findRepositoryListByActive(@Param("active") int active);
    
	/**
     * count repository by condition
     * @param searchDto
     *          type RepositorySearchDto
     * @return int
     * @author KhoaNA
     */
	int countRepositoryDtoByCondition(@Param("searchDto") RepositorySearchDto searchDto);
	
	/**
     * find repositoryDto list by condition
     * @param offset
     *          type int
     * @param sizeOfPage
     *          type int
     * @param searchDto
     *          type RepositorySearchDto
     * @return List<RepositoryDto>
     * @author KhoaNA
     */
	List<JcaRepositoryDto> findRepositoryDtoByCondition(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("searchDto") RepositorySearchDto searchDto);
	
	/**
     * find repository by code
     * @param name
     *          type String
     * @param id
     * 			type Long
     * @return Repository
     * @author KhoaNA
     */
	JcaRepository findRepositoryByCode(@Param("code") String code, @Param("id") Long id);
	
    /**
     * getAllRepositoryByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    List<JcaRepository> getAllRepositoryByCompanyId(@Param("companyId") Long companyId, @Param("repoId") Long repoId);
    
    /**
     * checkRepositoryHasStoreFile
     * @param repositoryId
     * @return result
     * @author TuyenTD
     */
	int checkRepositoryHasStoreFile(@Param("repositoryId")Long repositoryId);

	List<JcaRepository> getListRepositoryByCodeAndCompany(@Param("code") String code, @Param("companyId") Long companyId);
}
