/*******************************************************************************
 * Class        ：BannerRepository
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：hand
 * Change log   ：2017/02/16：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ContactBookingUpdateItemDto;
import vn.com.unit.cms.admin.all.entity.ContactBookingUpdateItem;
import vn.com.unit.db.repository.DbRepository;

/**
 * BannerRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ContactBookingUpdateItemRepository extends DbRepository<ContactBookingUpdateItem, Integer> {

	List<ContactBookingUpdateItemDto> findByBookingId(@Param("bookingId")Long  bookingId);

}