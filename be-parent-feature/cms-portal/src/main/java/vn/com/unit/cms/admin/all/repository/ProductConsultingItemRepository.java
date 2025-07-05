package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ProductConsultingUpdateItemDto;
import vn.com.unit.cms.admin.all.entity.ProductConsultingUpdateItem;
import vn.com.unit.db.repository.DbRepository;

public interface ProductConsultingItemRepository extends DbRepository<ProductConsultingUpdateItem, Integer> {

	List<ProductConsultingUpdateItemDto> findByProductConsultingId(
			@Param("productConsultingId") Long productConsultingId);

}
