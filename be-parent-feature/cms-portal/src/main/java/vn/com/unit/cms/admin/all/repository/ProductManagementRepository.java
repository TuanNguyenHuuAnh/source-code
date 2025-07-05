/*******************************************************************************
 * Class        ：IntroduceManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryParentCodeDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryViewDto;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.cms.core.module.product.entity.Product;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface ProductManagementRepository extends DbRepository<Product, Long> {

    /**
     *
     * @param searchDto
     * @return
     * @author TaiTM
     */
    public int countList(@Param("searchDto") ProductSearchDto searchDto);

    /**
     * @param searchDto
     * @param pageable
     * @return
     * @author TaiTM
     */
    public Page<ProductLanguageSearchDto> findListSearch(@Param("searchDto") ProductSearchDto searchDto,
            Pageable pageable);
    
    public int inOrder(@Param("code") String code);
    
}
