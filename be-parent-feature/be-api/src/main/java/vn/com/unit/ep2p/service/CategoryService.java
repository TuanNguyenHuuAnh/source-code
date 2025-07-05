/*******************************************************************************
 * Class        ：CategoryService
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：TrongNV
 * Change log   ：2020/12/17：01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CategoryAddReq;
import vn.com.unit.ep2p.dto.req.CategoryUpdateReq;
import vn.com.unit.ep2p.dto.res.CategoryInfoRes;

/**
 * <p>
 * CategoryService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface CategoryService extends BaseRestService<ObjectDataRes<EfoCategoryDto>, EfoCategoryDto>{
    
    /**
     * <p>
     * Count category dto by condition.
     * </p>
     *
     * @param efoCategorySearchDto
     *            type {@link EfoCategorySearchDto}
     * @return {@link int}
     * @author taitt
     */
    int countCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto);

    /**
     * <p>
     * Get category dto by condition.
     * </p>
     *
     * @param categorySearchDto
     *            type {@link EfoCategorySearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<EfoCategoryDto>}
     * @author taitt
     */
    List<EfoCategoryDto> getCategoryDtoByCondition(EfoCategorySearchDto categorySearchDto,Pageable pageable);

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param reqCategoryUpdateDto
     *            type {@link CategoryUpdateReq}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    void update(CategoryUpdateReq reqCategoryUpdateDto) throws Exception;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param reqCategoryAddDto
     *            type {@link CategoryAddReq}
     * @return {@link CategoryInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    CategoryInfoRes create(CategoryAddReq reqCategoryAddDto) throws Exception;


    /**
     * <p>
     * Get category info res by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link CategoryInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    CategoryInfoRes getCategoryInfoResById(Long id) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();
}
