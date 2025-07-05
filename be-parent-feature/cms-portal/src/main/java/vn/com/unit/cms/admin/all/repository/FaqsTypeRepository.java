/*******************************************************************************
 * Class        ：FaqsTypeRepository
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.FaqsTypeDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.FaqsType;
import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * FaqsTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
public interface FaqsTypeRepository extends DbRepository<FaqsType, Long> {

	/**
	 * Find list category faqs parent
	 * 
	 * @param sizeOfPage
	 * @param sizeOfPage
	 * @param condition
	 * @return List<FaqsTypeDto>
	 * @author vinhnht
	 */
	public List<FaqsTypeSearchDto> findFaqsTypeList(@Param("offset") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") FaqsTypeSearchDto condition);

	/**
	 * count type category faqs
	 * 
	 * @param condition
	 * @return Integer
	 * @author vinhnht
	 */
	public Integer countFaqsType(@Param("searchCond") FaqsTypeSearchDto condition);

	/**
	 * Find all type category faqs
	 * 
	 * @param lang
	 * @return List<FaqsTypeDto>
	 * @author vinhnht
	 */
	public List<FaqsTypeDto> findAllFaqsType(@Param("lang") String lang);

	/**
	 * findAllFaqsTypeByCutomerId
	 *
	 * @param lang
	 * @param customerTypeId
	 * @author nhutnn
	 */
	public List<FaqsTypeDto> findAllFaqsTypeByCutomerId(@Param("lang") String lang,
			@Param("customerTypeId") Long customerTypeId);

	/**
	 * Find type category FAQs by code
	 * 
	 * @param code
	 * @return FaqsType
	 * @author vinhnht
	 */
	public FaqsType findFaqsTypeByCode(@Param("code") String code);

	/**
	 * get max sort
	 *
	 * @return
	 * @author hand
	 */
	Long findMaxSort();

	/**
	 * findFaqsTypeByAlias
	 *
	 * @param alias
	 * @return
	 * @author TranLTH
	 */
	public FaqsTypeEditDto findFaqsTypeByAlias(@Param("alias") String alias);

	/**
	 * findFaqsTypeByAlias
	 *
	 * @param alias
	 */
	public FaqsTypeLanguage findFaqsTypeLangByAlias(@Param("linkAlias") String linkAlias,
			@Param("languageCode") String languageCode, @Param("customerId") Long customerId);

	public String getMaxCode();

	public List<FaqsTypeSearchDto> findListForSort(@Param("languageCode") String languageCode,
			@Param("customerId") Long customerId);

	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

	public List<FaqsTypeEditDto> findAllFaqsTypeAndNotIn(@Param("languageCode") String languageCode,
			@Param("faqsTypeEditDto") FaqsTypeEditDto faqsTypeEditDto);

	List<FaqsType> findByTypeId(@Param("typeId") Long typeId);

}