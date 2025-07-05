/*******************************************************************************
 * Class        RepositoryRepository
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.dto.RepositorySearchDto;
import vn.com.unit.ep2p.core.entity.Repository;
import vn.com.unit.ep2p.dto.RepositoryDto;

/**
 * RepositoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@org.springframework.stereotype.Repository(value = "assetRepositoryRepository")
public interface RepositoryRepository extends DbRepository<Repository, Long> {

    /**
     * Find repositoryDto by id
     * 
     * @param id type Long
     * @return RepositoryDto
     * @author KhoaNA
     */
    RepositoryDto findRepositoryDtoById(@Param("id") Long id);

    /**
     * Find repository list by id
     * 
     * @param actived type boolean
     * @return List<Repository>
     * @author KhoaNA
     */
    List<Repository> findRepositoryListByActive(@Param("active") int active);

    /**
     * count repository by condition
     * 
     * @param searchDto type RepositorySearchDto
     * @return int
     * @author KhoaNA
     */
    int countRepositoryDtoByCondition(@Param("searchDto") RepositorySearchDto searchDto);

    /**
     * find repositoryDto list by condition
     * 
     * @param offset     type int
     * @param sizeOfPage type int
     * @param searchDto  type RepositorySearchDto
     * @return List<RepositoryDto>
     * @author KhoaNA
     */
    List<RepositoryDto> findRepositoryDtoByCondition(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("searchDto") RepositorySearchDto searchDto);

    /**
     * find repository by code
     * 
     * @param name type String
     * @param id   type Long
     * @return Repository
     * @author KhoaNA
     */
    Repository findRepositoryByCode(@Param("code") String code, @Param("id") Long id);
}
